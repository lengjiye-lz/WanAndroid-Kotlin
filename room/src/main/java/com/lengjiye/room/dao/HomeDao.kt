package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.HomeEntity

@Dao
interface HomeDao {

    @Query("SELECT * FROM home")
    fun queryAll(): List<HomeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(homeEntities: List<HomeEntity>)

    @Delete
    fun deleteAll(homeEntities: List<HomeEntity>)
}