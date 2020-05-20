package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity
import com.lengjiye.room.entity.ShareEntity

@Dao
interface ShareDao {
    @Query("SELECT * FROM share")
    fun queryAll(): List<ShareEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(shareEntities: List<ShareEntity>)

    @Delete
    fun deleteAll(shareEntities: List<ShareEntity>)
}