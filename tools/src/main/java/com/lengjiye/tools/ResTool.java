package com.lengjiye.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.lengjiye.base.application.MasterApplication;

/**
 * 获取资源文件
 */
public class ResTool {

    public static String getString(int resId) {
        return MasterApplication.getInstance().applicationContext().getString(resId);
    }

    public static String getString(Context context, int resId) {
        return context.getApplicationContext().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return MasterApplication.getInstance().applicationContext().getString(resId, formatArgs);
    }

    public static String getString(Context context, int resId, Object... formatArgs) {
        return context.getApplicationContext().getString(resId, formatArgs);
    }

    public static String getStringFormat(int resId, Object... formatArgs) {
        return String.format(MasterApplication.getInstance().applicationContext().getResources().getString(resId), formatArgs);
    }

    public static String getStringFormat(Context context, int resId, Object... formatArgs) {
        return String.format(context.getApplicationContext().getResources().getString(resId), formatArgs);
    }

    public static int getColor(int resId) {
        return ContextCompat.getColor(MasterApplication.getInstance().applicationContext(), resId);
    }

    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context.getApplicationContext(), resId);
    }

    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(MasterApplication.getInstance().applicationContext(), resId);
    }

    public static Drawable getDrawable(Context context, int resId) {
        return ContextCompat.getDrawable(context.getApplicationContext(), resId);
    }

    public static int getDimens(int resId) {
        return MasterApplication.getInstance().applicationContext().getResources().getDimensionPixelSize(resId);
    }

    public static int getDimens(Context context, int resId) {
        return context.getApplicationContext().getResources().getDimensionPixelSize(resId);
    }
}
