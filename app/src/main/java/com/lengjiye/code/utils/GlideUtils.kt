package com.lengjiye.code.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.*
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.lengjiye.code.R
import com.lengjiye.code.widgets.glide.CircleWithBorder
import com.lengjiye.code.widgets.glide.RoundedCornersTransformation
import com.lengjiye.tools.ResTool
import java.io.File

/**
 * Glide 使用帮助类
 *
 * context: Context  最好不要使用application
 */
object GlideUtils {

    fun loadImage(context: Context, url: String?, imageView: ImageView) {
        with(context).load(url).apply(getOptions()).into(imageView)
    }

    fun loadImage(context: Context, url: String?, target: Target<Bitmap>) {
        with(context).asBitmap().load(url).apply(getOptions()).into(target)
    }

    fun loadImage(context: Context, file: File?, imageView: ImageView) {
        with(context).load(file).apply(getOptions()).into(imageView)
    }

    fun loadImage(context: Context, res: Int?, imageView: ImageView) {
        with(context).load(res).apply(getOptions()).into(imageView)
    }

    /**
     * 圆角
     */
    fun loadRoundedImage(context: Context, url: String?, dimenRes: Int, imageView: ImageView) {
        with(context).load(url).apply(getOptions()).transform(RoundedCorners(ResTool.getDimens(dimenRes))).into(imageView)
    }

    /**
     * 圆角
     */
    fun loadRoundedCornersImage(context: Context, url: String?, dimenRes: Int, imageView: ImageView) {
        loadRoundedCornersImage(context, url, dimenRes, RoundedCornersTransformation.CORNER_ALL, imageView)
    }

    /**
     * 圆角
     * @param url
     * @param dimenRes 圆角大小
     * @param cornerType 圆角方位
     * @param imageView
     */
    fun loadRoundedCornersImage(context: Context, url: String?, dimenRes: Int, cornerType: Int, imageView: ImageView) {
        loadRoundedCornersImage(context, url, dimenRes, cornerType, RoundedCornersTransformation.FIT_CENTER, imageView)
    }

    /**
     * 圆角
     * @param url
     * @param dimenRes 圆角大小
     * @param cornerType 圆角方位
     * @param scaleType imageView的方向
     * @param imageView
     */
    fun loadRoundedCornersImage(context: Context, url: String?, dimenRes: Int, cornerType: Int, scaleType: Int, imageView: ImageView) {
        with(context).load(url).apply(getOptions()).transform(
            RoundedCornersTransformation(ResTool.getDimens(dimenRes), cornerType, scaleType)
        ).into(imageView)
    }

    /**
     * 圆角带边框
     * @param url
     * @param dimenRes 圆角大小
     * @param imageView
     */
    fun loadRoundedCornersStrokeImage(context: Context, url: String?, dimenRes: Int, imageView: ImageView) {
        loadRoundedCornersStrokeImage(
            context,
            url,
            dimenRes,
            RoundedCornersTransformation.CORNER_ALL,
            RoundedCornersTransformation.FIT_CENTER,
            true,
            ResTool.getDimens(R.dimen.d_1),
            ResTool.getColor(R.color.c_99),
            imageView
        )
    }

    /**
     * 圆角
     * @param context activity/fragment 最好传 绑定生命周期，有利于回收
     * @param url
     * @param dimenRes 圆角大小
     * @param cornerType 圆角方位
     * @param scaleType imageView的方向
     * @param isShowStroke 是否显示圆角
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @param imageView
     */
    fun loadRoundedCornersStrokeImage(
        context: Context,
        url: String?,
        dimenRes: Int,
        cornerType: Int,
        scaleType: Int,
        isShowStroke: Boolean,
        strokeWidth: Int,
        strokeColor: Int,
        imageView: ImageView
    ) {
        with(context).load(url).apply(getOptions()).transform(
            RoundedCornersTransformation(ResTool.getDimens(dimenRes), cornerType, scaleType, isShowStroke, strokeWidth, strokeColor)
        ).into(imageView)
    }

    /**
     * 圆形
     */
    fun loadCircleImage(context: Context, url: String?, imageView: ImageView) {
        with(context).load(url).apply(getOptions()).transform(CircleCrop()).into(imageView)
    }

    /**
     * 带边框的圆形
     */
    fun loadCircleWithBorderImage(context: Context, url: String?, imageView: ImageView) {
        loadCircleWithBorderImage(context, url, R.dimen.d_2, R.color.c_99, imageView)
    }


    /**
     * 带边框的圆形
     */
    fun loadCircleWithBorderImage(context: Context, url: String?, strokeWidth: Int?, strokeColor: Int?, imageView: ImageView) {
        with(context).load(url).apply(getOptions()).transform(CircleWithBorder(ResTool.getDimens(strokeWidth ?: 2), ResTool.getColor(strokeColor ?: R.color.white))).into(imageView)
    }

    /**
     * 设置加载中和加载失败的图片
     */
    private fun getOptions(): RequestOptions {
        return RequestOptions().centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
    }

    private fun with(context: Context): RequestManager {
        return when (context) {
            is Activity -> {
                Glide.with(context)
            }
            is Fragment -> {
                Glide.with(context as Fragment)
            }
            else -> {
                Glide.with(context)
            }
        }
    }
}

