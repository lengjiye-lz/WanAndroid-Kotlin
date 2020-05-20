package com.lengjiye.room.entity.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lengjiye.room.entity.SystemTreeEntity

class TreeEntityConverters {
    @TypeConverter
    fun stringToObject(value: String): List<SystemTreeEntity> {
        val listType = object : TypeToken<List<SystemTreeEntity>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<SystemTreeEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}