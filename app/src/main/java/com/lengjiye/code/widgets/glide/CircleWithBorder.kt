package com.lengjiye.code.widgets.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

/**
 * 带边框的圆形图片
 *
 * strokeWidth 边框宽度
 * strokeColor 边框颜色
 */
class CircleWithBorder(private val strokeWidth: Int?, private val strokeColor: Int?) : BitmapTransformation() {
    private val VERSION = 1
    private val ID = "com.lengjiye.code.widgets.glide.CircleWithBorder.$VERSION"
    private val ID_BYTES = ID.toByteArray(Key.CHARSET)


    private val mPaint = Paint(Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)

    // Bitmap doesn't implement equals, so == and .equals are equivalent here.
    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap? {
        return circleWithBorder(pool, toTransform, outWidth, outHeight)
    }

    override fun equals(other: Any?): Boolean {
        return other is CircleWithBorder
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    private fun circleWithBorder(pool: BitmapPool, inBitmap: Bitmap, destWidth: Int, destHeight: Int): Bitmap {
        val stroke = strokeWidth ?: 2
        val bitmap = TransformationUtils.circleCrop(pool, inBitmap, destWidth, destHeight)
        val destMinEdge = destWidth.coerceAtMost(destHeight)
        val radius = destMinEdge / 2f

        mPaint.color = strokeColor ?: android.R.color.white
        mPaint.strokeWidth = stroke.toFloat()
        mPaint.style = Paint.Style.STROKE

        TransformationUtils.getBitmapDrawableLock().lock()
        try {
            val canvas = Canvas(bitmap)
            // Draw a circle
            canvas.drawCircle(radius, radius, radius - stroke / 2, mPaint)
            canvas.setBitmap(null)
        } finally {
            TransformationUtils.getBitmapDrawableLock().unlock()
        }

        return bitmap
    }
}