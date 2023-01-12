package com.captano.rscan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.captano.rscan.Room.ScanModel
import java.text.SimpleDateFormat
import java.util.*

class RecognizedTextsAdapter(private val onClickListener: CustOnClickListener, textsList: List<ScanModel>?) : RecyclerView.Adapter<RecognizedTextsAdapter.ViewHolder>() {

    private var texts = textsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recognized, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textViewdate.text= dateConveter(texts?.get(position)?.time)
        holder.textViewcontent.text = texts?.get(position)?.text
        holder.itemView.setOnClickListener {
            onClickListener.onClick(texts!![position])
        }
    }

    interface CustOnClickListener{
        fun onClick(scanModel: ScanModel)
    }

    fun setList(textsUpdate: List<ScanModel>?){
        texts = textsUpdate
        notifyDataSetChanged()  // to be changed or suppressed
    }

    override fun getItemCount() = if(texts.isNullOrEmpty())  0 else texts!!.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewdate: TextView = itemView.findViewById(R.id.textview_date)
        val textViewcontent: TextView = itemView.findViewById(R.id.textview_main_content)
    }

    private fun dateConveter(dateTime: Long?):String{
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US)
        return format.format(dateTime)
    }
}



