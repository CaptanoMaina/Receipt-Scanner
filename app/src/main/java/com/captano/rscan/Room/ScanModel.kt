package com.captano.rscan.Room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_table")
class ScanModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var text: String,
    val time: Long

    )