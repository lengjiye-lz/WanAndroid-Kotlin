package com.lengjiye.tools;

import android.util.Log;

/**
 * 类描述: 日志工具类
 * 功能：1.release版本没有日志输出
 * 2.日志显示类名，方法名，代码行数等信息
 * 3.捕捉到崩溃日志保存在本地，每天一个文件，方便查看
 * 创建人: lz
 * 创建时间: 2016/11/22
 * 修改备注:
 */
public class LogTool {

    public static final String SEPARATOR = ",";
    public static final String TAG_NAME = "WanAndroid";

    private LogTool() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * @param message
     */
    public static void v(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.v(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            Log.d(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            Log.i(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.i(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.w(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            Log.e(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * @param message
     */
    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.e(tag, getLogInfo(stackTraceElement) + message);
        }
    }

    /**
     * Get default tag name
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        String tag = stringArray[0];
        return TAG_NAME + "-" + tag;
    }

    /**
     * Get default tag name
     */
    public static String getDefaultTag() {
        return TAG_NAME;
    }

    /**
     * get stack info
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // class name
        String fileName = stackTraceElement.getFileName();
        // code line
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append(""
                + fileName).append(SEPARATOR);
        logInfoStringBuilder.append("line:");
        logInfoStringBuilder.append(lineNumber);
        logInfoStringBuilder.append("] ");
        return logInfoStringBuilder.toString();
    }
}