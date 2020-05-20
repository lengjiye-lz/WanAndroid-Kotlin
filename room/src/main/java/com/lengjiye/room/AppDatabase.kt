package com.lengjiye.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lengjiye.room.dao.*
import com.lengjiye.room.entity.*

@Database(
    entities = [HomeEntity::class, HomeBannerEntity::class, ShareEntity::class, SystemEntity::class,
        ProjectEntity::class, SystemTreeEntity::class, ProjectTreeEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun homeDao(): HomeDao
    abstract fun homeBannerDao(): HomeBannerDao
    abstract fun shareDao(): ShareDao
    abstract fun systemDao(): SystemDao
    abstract fun projectDao(): ProjectDao
    abstract fun systemTreeDao(): SystemTreeDao
    abstract fun projectTreeDao(): ProjectTreeDao

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