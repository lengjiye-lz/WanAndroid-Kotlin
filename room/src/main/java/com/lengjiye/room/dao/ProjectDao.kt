package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.*

@Dao
interface ProjectDao {
    @Query("SELECT * FROM share")
    fun queryAll(): List<ProjectEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(shareEntities: List<ProjectEntity>)

    @Delete
    fun deleteAll(shareEntities: List<ProjectEntity>)
}