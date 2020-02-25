package com.lengjiye.code.utils

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
object GlideUtil {

    fun loadImage(context: Context, url: String?, imageView: ImageView) {
        with(context).load(url).apply(getOptions()).into(imageView)
    }

    fun loadFile(context: Context, file: File?, imageView: ImageView) {
        with(context).load(file).apply(getOptions()).into(imageView)
    }

    fun loadRes(context: Context, res: Int?, imageView: ImageView) {
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
        with(context).load(url).apply(getOptions()).transform(
            RoundedCornersTransformation(
                ResTool.getDimens(dimenRes), RoundedCornersTransformation.CORNER_NONE,
                RoundedCornersTransformation.FIT_CENTER
            )
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
                Glide.with(context as Activity)
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

