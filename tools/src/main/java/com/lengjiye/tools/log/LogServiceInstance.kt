package com.lengjiye.tools.log

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.lifecycle.MutableLiveData

class LogServiceInstance {

    var logMessage = MutableLiveData<String>()

    companion object {
        var singleton = Instance.holder
    }

    private object Instance {
        val holder = LogServiceInstance()
    }

    fun start(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(context)) {
                context.startService(Intent(context, LogService::class.java))
            } else {
                //若没有权限，提示获取.
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                Toast.makeText(context, "需要取得权限以使用悬浮窗", Toast.LENGTH_SHORT).show()
                context.startActivity(intent)
            }
        } else {
            //SDK在23以下，不用管.
            context.startService(Intent(context, LogService::class.java))
        }
    }

    fun stop(context: Context) {
        context.stopService(Intent(context, LogService::class.java))
    }

    fun setMessage(message: String) {
        logMessage.value = message
    }
}