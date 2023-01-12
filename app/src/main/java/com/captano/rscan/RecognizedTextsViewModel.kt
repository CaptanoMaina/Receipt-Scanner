package com.captano.rscan

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.captano.rscan.Room.ScanDAO
import com.captano.rscan.Room.ScanDatabase
import com.captano.rscan.Room.ScanModel
import kotlinx.coroutines.*

class RecognizedTextViewModel(application: Application) : AndroidViewModel(application) {
    var database : ScanDAO = ScanDatabase(application).scanDAO()

    var allTextLiveData: LiveData<List<ScanModel>?> =database.getAllScans()
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun InsertPicToDB(context: Context, scanModel:ScanModel){
        scope.launch() {
            database.insertScan(scanModel)
            withContext(Dispatchers.Main) {
//                showToast( //TODO RETURN THIS MESSAGE TO THE CALLER
                Toast.makeText(context, "Saved to the database!", Toast.LENGTH_SHORT).show()

            }
        }
    }


}
