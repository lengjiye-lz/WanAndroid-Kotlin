package com.lengjiye.room.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lengjiye.room.entity.TagEntity

class TagEntityConverters {
    @TypeConverter
    fun stringToObject(value: String): List<TagEntity> {
        val listType = object : TypeToken<List<TagEntity>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<TagEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}