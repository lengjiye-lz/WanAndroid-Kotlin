package com.lengjiye.room.dao

import androidx.room.*
import com.lengjiye.room.entity.ProjectTreeEntity
import com.lengjiye.room.entity.SystemTreeEntity

@Dao
interface ProjectTreeDao {

    @Query("SELECT * FROM project_tree")
    fun queryAll(): List<ProjectTreeEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(treeEntities: List<ProjectTreeEntity>)

    @Delete
    fun deleteAll(treeEntities: List<ProjectTreeEntity>)
}