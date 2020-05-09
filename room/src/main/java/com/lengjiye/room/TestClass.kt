package com.lengjiye.room

import android.content.Context

class TestClass {

    fun text(context: Context) {
        AppDatabase.getInstance(context).homeDao()
    }
}