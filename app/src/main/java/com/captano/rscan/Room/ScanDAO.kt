package com.captano.rscan.Room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ScanDAO {
    @Query("SELECT * FROM scan_table ORDER BY time DESC")
    fun getAllScans(): LiveData<List<ScanModel>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertScan(scanModel: ScanModel)

    @Delete
    fun deleteScan(scanModel: ScanModel)

    @Update
    fun updateScan(scanModel: ScanModel)
}