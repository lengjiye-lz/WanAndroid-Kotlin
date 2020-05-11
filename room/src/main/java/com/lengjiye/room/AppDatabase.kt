package com.lengjiye.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lengjiye.room.dao.HomeDao
import com.lengjiye.room.entity.HomeEntity

@Database(entities = [HomeEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun homeDao(): HomeDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                .allowMainThreadQueries()
//                .addMigrations()
                .build()
                .also { instance = it }
        }
    }
}