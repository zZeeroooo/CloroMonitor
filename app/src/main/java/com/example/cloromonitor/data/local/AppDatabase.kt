package com.example.cloromonitor.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cloromonitor.data.model.PuntoMedicion

@Database(entities = [PuntoMedicion::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun puntoDao(): PuntoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cloromonitor.db"
                ).build().also { INSTANCE = it }
            }
    }
}