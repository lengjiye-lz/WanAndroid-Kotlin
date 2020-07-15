package com.lengjiye.code

import androidx.test.runner.AndroidJUnit4
import com.lengjiye.tools.log.LogTool
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KotlinCoroutineTest {

    @Test
    fun testMain() {
        LogTool.log("主线程id：${Thread.currentThread().id}")
        LogTool.log("执行结束：${Thread.currentThread().id}")
    }




}