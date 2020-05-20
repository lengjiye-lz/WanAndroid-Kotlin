package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.SystemTreeEntity

@Dao
interface SystemTreeDao {
    @Query("SELECT * FROM system_tree")
    fun queryAll(): List<SystemTreeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(treeEntities: List<SystemTreeEntity>)

    @Delete
    fun deleteAll(treeEntities: List<SystemTreeEntity>)
}