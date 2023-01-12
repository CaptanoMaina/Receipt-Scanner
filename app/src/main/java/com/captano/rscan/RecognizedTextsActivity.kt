package com.captano.rscan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.captano.rscan.Room.ScanModel
import com.captano.rscan.UI.ReceiptDialog

class RecognizedTextsActivity : AppCompatActivity(), RecognizedTextsAdapter.CustOnClickListener {

    lateinit var androidViewModel:RecognizedTextViewModel
    lateinit var recyclerview_scanned :RecyclerView
    lateinit var recognizedTextsAdapter: RecognizedTextsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recognized_texts)

        recyclerview_scanned = findViewById(R.id.recyclerview_recognized)
        setUpAdapter()
        setUpViewModel()
    }

    private fun setUpAdapter(){
        recognizedTextsAdapter = RecognizedTextsAdapter(this,null)
        recyclerview_scanned.adapter = recognizedTextsAdapter
    }

    private fun setUpViewModel() {
        androidViewModel = ViewModelProvider(this).get(RecognizedTextViewModel::class.java)
        androidViewModel.allTextLiveData.observe(this, Observer { listRetrievedFromDatabase ->

            //todo refresh my list

            recognizedTextsAdapter.setList(listRetrievedFromDatabase)

        })

    }

    override fun onClick(scanModel: ScanModel) {
        val receiptDialog = ReceiptDialog(scanModel.text)
        receiptDialog.show(supportFragmentManager, null)
    }


}