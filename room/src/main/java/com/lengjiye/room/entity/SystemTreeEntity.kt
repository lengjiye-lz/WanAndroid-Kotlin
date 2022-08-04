package com.lengjiye.room.entity

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
    @SerializedName("children")
    var childrens: List<SystemTreeBean>? = null

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

    override fun hashCode(): Int {
        var result = autoId
        result = 31 * result + (childrens?.hashCode() ?: 0)
        result = 31 * result + courseId
        result = 31 * result + id
        result = 31 * result + name.hashCode()
        result = 31 * result + order
        result = 31 * result + parentChapterId
        result = 31 * result + userControlSetTop.hashCode()
        result = 31 * result + visible
        return result
    }
}

class SystemTreeBean {
    var autoId: Int = 0
    var childrens: List<SystemTreeBean>? = null
    var courseId: Int = 0
    var id: Int = 0
    var name: String = ""
    var order: Int = 0
    var parentChapterId: Int = 0
    var userControlSetTop: Boolean = false
    var visible: Int = 0
}