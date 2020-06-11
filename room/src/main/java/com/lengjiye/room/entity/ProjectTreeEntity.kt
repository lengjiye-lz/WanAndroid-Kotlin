package com.lengjiye.room.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lengjiye.room.entity.converter.TreeEntityConverters
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
@Keep
@Entity(tableName = "project_tree")
@TypeConverters(TreeEntityConverters::class)
@Parcelize
class ProjectTreeEntity : SystemTreeEntity() {

}