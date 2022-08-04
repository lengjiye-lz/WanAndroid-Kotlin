package com.lengjiye.room.entity.converter

import androidx.annotation.Keep
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lengjiye.room.entity.SystemTreeBean
@Keep
class TreeEntityConverters {
    @TypeConverter
    fun stringToObject(value: String): List<SystemTreeBean> {
        val listType = object : TypeToken<List<SystemTreeBean>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun objectToString(list: List<SystemTreeBean>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}