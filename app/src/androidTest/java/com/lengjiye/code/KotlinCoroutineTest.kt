package com.lengjiye.code

import androidx.test.runner.AndroidJUnit4
import com.lengjiye.tools.log.LogTool
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KotlinCoroutineTest {

    @Test
    fun testMain() {
        LogTool.de("主线程id：${Thread.currentThread().id}")
        LogTool.de("执行结束：${Thread.currentThread().id}")
    }




}