package com.lengjiye.code.widgets

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
        setChanged()
        notifyObservers()
    }

}