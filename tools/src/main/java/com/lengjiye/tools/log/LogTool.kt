package com.lengjiye.tools.log

import android.os.Environment
import android.util.Log
import com.lengjiye.code.baseparameter.application.MasterApplication
import com.lengjiye.tools.BuildConfig
import com.lengjiye.tools.DateTimeTool
import com.lengjiye.tools.FileTool
import com.lengjiye.tools.log.LogServiceInstance.Companion.singleton
import com.lengjiye.tools.log.LogTool.logEnable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*

/**
 * 类描述: 日志工具类
 * 功能：1.release版本没有日志输出
 * 2.日志显示类名，方法名，代码行数等信息
 * 3.捕捉到崩溃日志保存在本地，每天一个文件，方便查看
 * 创建人: lz
 * 创建时间: 2016/11/22
 * 修改备注:
 */
object LogTool {
    private const val SEPARATOR = ","
    private var filePath = ""
    private var path = "/log"
    private var fileName = ""

    /**
     * Get default tag name
     */
    const val defaultTag = "WanAndroid"

    /**
     * 是否打印日志，全局设置
     * 默认debug模式下打印
     */
    var logEnable: Boolean = false

    /**
     * Get default tag name
     */
    fun getDefaultTag(stackTraceElement: StackTraceElement): String {
        val fileName = stackTraceElement.fileName
        val stringArray = fileName.split("\\.").toTypedArray()
        val tag = stringArray[0]
        return "$defaultTag-$tag"
    }

    init {
        getFilePath()
    }

    /**
     * get stack info
     */
    fun getLogInfo(stackTraceElement: StackTraceElement): String {
        val logInfoStringBuilder = StringBuilder()
        // class name
        val fileName = stackTraceElement.fileName
        // code line
        val lineNumber = stackTraceElement.lineNumber
        logInfoStringBuilder.append("[").append(fileName).append(SEPARATOR)
        logInfoStringBuilder.append("line:")
        logInfoStringBuilder.append(lineNumber)
        logInfoStringBuilder.append("] ")
        return logInfoStringBuilder.toString()
    }

    /**
     * 在悬浮窗上显示log
     */
    fun showLog(tag: String, content: String) {
        val timeTag = "${DateTimeTool.getCurrDateTimeStr()} $tag"
        writeTxtToFile("$timeTag:$content")
        singleton.setMessage("$timeTag:$content")
    }

    private fun getFilePath() {
        filePath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            MasterApplication.getInstance().applicationContext().getExternalFilesDir(null)?.path + path
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            MasterApplication.getInstance().applicationContext().filesDir.path + path
        }

        val dir = FileTool.createOrExistsDir(filePath)
        if (dir) {
            fileName = DateTimeTool.getFormatDay(DateTimeTool.getCurrDate()) + ".txt"

            val file = FileTool.createOrExistsFile(filePath + File.separator + fileName)
            if (!file) {
                return
            }
        }
    }

    private fun writeTxtToFile(string: String) {
        val content = string + "\r\n"
        getFilePath()
        val file = File(filePath + File.separator + fileName)
        GlobalScope.launch {
            var raf: FileOutputStream? = null
            var out: OutputStreamWriter? = null
            try {
                raf = FileOutputStream(file, true)
                out = OutputStreamWriter(raf, "GBK")
                out?.write(content)
            } catch (e: Exception) {
                log("写入文件报错:${e.message}")
            } finally {
                out?.close()
                raf?.close()
            }
        }
    }
}

/**
 * @param tag
 * @param message
 */
fun logV(message: String, tag: String = LogTool.defaultTag) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[3]
        val mess = LogTool.getLogInfo(stackTraceElement) + message
        Log.v(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logD(message: String, tag: String = LogTool.defaultTag) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[3]
        val mess = LogTool.getLogInfo(stackTraceElement) + message
        Log.d(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logI(message: String, tag: String = LogTool.defaultTag) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[3]
        val mess = LogTool.getLogInfo(stackTraceElement) + message
        Log.i(tag, mess)
        LogTool.showLog(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logW(message: String, tag: String = LogTool.defaultTag) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[3]
        val mess = LogTool.getLogInfo(stackTraceElement) + message
        Log.w(tag, mess)
        LogTool.showLog(tag, mess)
    }
}

/**
 * @param tag
 * @param message
 */
fun logE(message: String, tag: String = LogTool.defaultTag) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[3]
        val mess = LogTool.getLogInfo(stackTraceElement) + message
        Log.e(tag, mess)
        LogTool.showLog(tag, mess)
    }
}

/**
 * 平时调试使用
 *
 * @param message
 */
fun log(message: String) {
    if (BuildConfig.DEBUG || logEnable) {
        val stackTraceElement = Thread.currentThread().stackTrace[3]
        val mess = LogTool.getLogInfo(stackTraceElement) + message
        val tag = "ceshi"
        Log.e(tag, mess)
        LogTool.showLog(tag, mess)
    }
}


