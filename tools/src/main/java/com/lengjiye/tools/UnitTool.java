package com.lengjiye.tools;

import android.util.TypedValue;

import com.lengjiye.base.application.MasterApplication;

/**
 * dp转换
 */
public class UnitTool {

    private UnitTool() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics());
    }

    public static int dip2px(float dipValue) {
        return (int) (dipValue * MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int px2dp(float pxValue) {
        return (int) (pxValue / MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int px2dip(float pxValue) {
        return (int) (pxValue / MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics().density + 0.5f);
    }

    public static int px2sp(float pxValue) {
        return (int) (pxValue / MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    public static float px2sp(int size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size,
                MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics());
    }

    public static int sp2px(float spValue) {
        return (int) (spValue * MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }

    public static float sp2px(int size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size,
                MasterApplication.getInstance().applicationContext().getResources().getDisplayMetrics());
    }
}
