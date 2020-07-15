package com.lengjiye.code.utils

import com.lengjiye.tools.log.logE
import java.io.PrintWriter
import java.io.StringWriter

/**
 * 崩溃日志捕捉
 */
class CrashHandlerUtils : Thread.UncaughtExceptionHandler {

    private var handler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(p0: Thread, p1: Throwable) {
        logE("程序出现异常了:Thread:${p0.name} \nerrorMessage:${p1.message}")

        val stackTraceInfo = getStackTraceInfo(p1)
        logE("StackTraceInfo:$stackTraceInfo")

        // 为了防止其他第三方收集不到错误信息  比如友盟
        handler.uncaughtException(p0, p1)
    }


    private fun getStackTraceInfo(t: Throwable): String {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        t.printStackTrace(printWriter)
        printWriter.close()
        return writer.toString()
    }
}