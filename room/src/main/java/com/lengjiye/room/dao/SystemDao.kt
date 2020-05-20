package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.room.entity.ShareEntity
import com.lengjiye.room.entity.SystemEntity

@Dao
interface SystemDao {
    @Query("SELECT * FROM share")
    fun queryAll(): List<SystemEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(shareEntities: List<SystemEntity>)

    @Delete
    fun deleteAll(shareEntities: List<SystemEntity>)
}