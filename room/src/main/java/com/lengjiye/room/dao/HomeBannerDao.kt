package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.HomeBannerEntity
import com.lengjiye.room.entity.HomeEntity

@Dao
interface HomeBannerDao {
    @Query("SELECT * FROM home_banner where id=:id")
    fun query(id: Int): HomeBannerEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(homeEntity: HomeBannerEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(homeEntity: HomeBannerEntity)
}