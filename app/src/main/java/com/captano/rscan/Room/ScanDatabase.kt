package com.captano.rscan.Room

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = arrayOf(ScanModel::class), version = 1,exportSchema = false)
abstract class ScanDatabase : RoomDatabase() {

    abstract fun scanDAO() : ScanDAO

    companion object{
        @Volatile
        private var instance : ScanDatabase?=null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ScanDatabase::class.java,
            "scan_database.db"
        ).build()
        }
    }
