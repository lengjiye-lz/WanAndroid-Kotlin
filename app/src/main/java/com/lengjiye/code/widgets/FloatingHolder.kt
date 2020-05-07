package com.lengjiye.code.widgets

import com.lengjiye.tools.log.LogTool
import java.util.*

class FloatingHolder : Observable() {

    companion object {
        var singleton = Instance.holder
    }

    private object Instance {
        val holder = FloatingHolder()
    }

    /**
     * 通知观察者
     */
    fun update() {
        LogTool.e("lz", "update")
        setChanged()
        notifyObservers()
    }

}