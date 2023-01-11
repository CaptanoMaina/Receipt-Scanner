package com.captano.rscan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.captano.rscan.Room.ScanDatabase
import com.captano.rscan.Room.ScanModel

class RecognizedTextViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ScanDatabase(application).scanDAO()

    val allText: LiveData<List<ScanModel>> = database.getAllScans()
}
