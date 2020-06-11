package com.lengjiye.room.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.lengjiye.room.entity.converter.TreeEntityConverters
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
@Keep
@Entity(tableName = "system_tree")
@TypeConverters(TreeEntityConverters::class)
@Parcelize
open class SystemTreeEntity : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var autoId: Int = 0

    @IgnoredOnParcel
    var children: List<SystemTreeEntity>? = null

    @IgnoredOnParcel
    var courseId: Int = 0

    @IgnoredOnParcel
    var id: Int = 0

    @IgnoredOnParcel
    var name: String = ""

    @IgnoredOnParcel
    var order: Int = 0

    @IgnoredOnParcel
    var parentChapterId: Int = 0

    @IgnoredOnParcel
    var userControlSetTop: Boolean = false

    @IgnoredOnParcel
    var visible: Int = 0


    override fun equals(other: Any?): Boolean {
        if (other is SystemTreeEntity) {
            return id == other.id
        }
        return super.equals(other)
    }
}