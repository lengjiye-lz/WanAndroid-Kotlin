package com.lengjiye.tools.log;

import android.util.Log;

import com.lengjiye.tools.BuildConfig;

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

    private static final String SEPARATOR = ",";
    private static final String TAG_NAME = "WanAndroid";

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
            String mess = getLogInfo(stackTraceElement) + message;
            Log.v(tag, mess);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String mess = getLogInfo(stackTraceElement) + message;
            Log.v(tag, mess);
        }
    }

    /**
     * @param message
     */
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            String mess = getLogInfo(stackTraceElement) + message;
            Log.d(tag, mess);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String mess = getLogInfo(stackTraceElement) + message;
            Log.d(tag, mess);
        }
    }

    /**
     * @param message
     */
    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            String mess = getLogInfo(stackTraceElement) + message;
            Log.i(tag, mess);
            showLog(tag, mess);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String mess = getLogInfo(stackTraceElement) + message;
            Log.i(tag, mess);
            showLog(tag, mess);
        }
    }

    /**
     * @param message
     */
    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            String mess = getLogInfo(stackTraceElement) + message;
            Log.w(tag, mess);
            showLog(tag, mess);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String mess = getLogInfo(stackTraceElement) + message;
            Log.w(tag, mess);
            showLog(tag, mess);
        }
    }

    /**
     * @param message
     */
    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag();
            String mess = getLogInfo(stackTraceElement) + message;
            Log.e(tag, mess);
            showLog(tag, mess);
        }
    }

    /**
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String mess = getLogInfo(stackTraceElement) + message;
            Log.e(tag, mess);
            showLog(tag, mess);
        }
    }

    /**
     * Get default tag name
     */
    public static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String[] stringArray = fileName.split("\\.");
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

        logInfoStringBuilder.append("[").append(fileName).append(SEPARATOR);
        logInfoStringBuilder.append("line:");
        logInfoStringBuilder.append(lineNumber);
        logInfoStringBuilder.append("] ");
        return logInfoStringBuilder.toString();
    }

    /**
     * 在悬浮窗上显示log
     */
    private static void showLog(String tag, String content) {
        if (!LogServiceInstance.Companion.isShow()) {
            return;
        }
        synchronized (LogTool.class) {
            LogServiceInstance.Companion.getSingleton().setMessage(tag + ":" + content);
        }
    }
}