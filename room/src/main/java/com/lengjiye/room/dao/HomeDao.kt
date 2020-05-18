package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.HomeEntity

@Dao
interface HomeDao {

    @Query("SELECT * FROM home")
    fun queryAll(): List<HomeEntity>?

    @Query("SELECT * FROM home where id=:id")
    fun query(id: Int): List<HomeEntity>?

    @Insert
    fun insert(homeEntity: HomeEntity)

    @Insert
    fun insert(homeEntities: List<HomeEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(homeEntity: HomeEntity)

    @Delete
    fun delete(homeEntity: HomeEntity)

    @Delete
    fun deleteAll(homeEntities: List<HomeEntity>)
}