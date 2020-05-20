package com.lengjiye.room.entity

import androidx.room.*
import com.lengjiye.room.entity.converter.TagEntityConverters

@Entity(tableName = "share")
@TypeConverters(TagEntityConverters::class)
class ShareEntity : HomeEntity() {

    companion object{
        fun toShareEntity(homeEntity: HomeEntity) :ShareEntity{
            return ShareEntity().apply {
                apkLink = homeEntity.apkLink
                link = homeEntity.link
                autoId = homeEntity.autoId
                id = homeEntity.id
                audit = homeEntity.audit
                author = homeEntity.author
                chapterId = homeEntity.chapterId
                chapterName = homeEntity.chapterName
                collect = homeEntity.collect
                courseId = homeEntity.courseId
                desc = homeEntity.desc
                envelopePic = homeEntity.envelopePic
                fresh = homeEntity.fresh
                niceDate = homeEntity.niceDate
                niceShareDate = homeEntity.niceShareDate
                origin = homeEntity.origin
                prefix = homeEntity.prefix
                projectLink = homeEntity.projectLink
                publishTime = homeEntity.publishTime
                shareDate = homeEntity.shareDate
                shareUser = homeEntity.shareUser
                superChapterId = homeEntity.superChapterId
                superChapterName = homeEntity.superChapterName
                tags = homeEntity.tags
                title = homeEntity.title
                type = homeEntity.type
                userId = homeEntity.userId
                visible = homeEntity.visible
                originId = homeEntity.originId
                zan = homeEntity.zan
            }
        }
    }
}