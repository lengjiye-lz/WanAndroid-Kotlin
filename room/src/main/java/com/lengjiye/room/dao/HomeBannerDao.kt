package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity

@Dao
interface HomeBannerDao {
    @Query("SELECT * FROM home_banner")
    fun queryAll(): List<HomeBannerEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(homeBannerEntities: List<HomeBannerEntity>)

    @Delete
    fun deleteAll(homeBannerEntities: List<HomeBannerEntity>)
}