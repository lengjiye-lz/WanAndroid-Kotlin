package com.lengjiye.codelibrarykotlin

import android.util.Log
import androidx.test.runner.AndroidJUnit4
import com.lengjiye.tools.LogTool

import org.junit.Test
import org.junit.runner.RunWith


/**
 * ......................我佛慈悲.....................
 * ......................_oo0oo_.....................
 * .....................o8888888o....................
 * .....................88" . "88....................
 * .....................(| -_- |)....................
 * .....................0\  =  /0....................
 * ...................___/`---'\___..................
 * ..................' \\|     |// '.................
 * ................./ \\|||  :  |||// \..............
 * .............../ _||||| -卍-|||||- \..............
 * ..............|   | \\\  -  /// |   |.............
 * ..............| \_|  ''\---/''  |_/ |.............
 * ..............\  .-\__  '-'  ___/-. /.............
 * ............___'. .'  /--.--\  `. .'___...........
 * .........."" '<  `.___\_<|>_/___.' >' ""..........
 * ........| | :  `- \`.;`\ _ /`;.`/ - ` : | |.......
 * ........\  \ `_.   \_ __\ /__ _/   .-` /  /.......
 * ....=====`-.____`.___ \_____/___.-`___.-'=====....
 * ......................`=---='.....................
 * ..................佛祖开光 ,永无BUG.................
 */
@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedIApp {
    var list = arrayListOf<String>()

    @Test
    fun useAppContext() {
        testList()
    }

    /**
     *
     * 跳出循环方法
     * break 和continue 只能用于循环中, forEach 是方法所以不能使用
     * 只能使用 return, 使用 return 实现 break 的效果可以使用标签,规则 loop@ 调用 return@loop 或者直接 return@forEach
     * 标签使程序调到指定的地方开始执行
     * 在方法中return可以直接使用方法名当做标签  break 和continue 不行
     * 循环中 return 直接返退出当前方法 使用标签无效
     * 在双层循环中 break 是退出当前循环执行上一层循环 标签如果加载第一次和 return 效果一样
     * 在双层循环中continue 是跳出当前循环,继续执行下一个循环 标签加在地一层和直接使用 break 效果一样
     *
     * foreach 测试
     *
     * 作用于集合对象，String等，用来迭代集合对象中的每个元素
     */
    private fun forEachTest() {
        var array = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        array.forEach {
            LogTool.e("lz", "forEach:$it")
            if (it == "2") return@forEachTest
            Log.e("lz", "forEach:$it")

            for (i in 1..4) {
                LogTool.e("lz", "for:$i")
                if (i == 2) {
                    break
                }
                Log.e("lz", "for:$i")
            }
        }
        LogTool.e("lz", "forEach:")
    }

    /**
     * for循环测试
     */
    private fun forTest() {
        loop@ for (i in 1..4) {
            loop1@ for (j in 1..5) {
                LogTool.e("lz", "for:$j")
                if (j == 1) {
                    continue@loop
                }
            }
        }
    }

    /**
     * for 循环测试
     *
     * 带下标的循环
     */
    private fun forTest1() {
        val list = listOf<String>("泰国", "新加坡", "印度尼西亚")
        for ((index: Int, element: String) in list.withIndex()) {
            LogTool.e("lz", "第$index 个是$element")
        }
    }

    /**
     * for循环
     *
     * 只取下标
     */
    private fun forTest2() {
        val list = listOf<String>("泰国", "新加坡", "印度尼西亚")
        for (i in list.indices) {
            LogTool.e("lz", "第$i 个元素")
        }
    }

    /**
     * repeat测试
     * 代替 简单的 for 循环  比如 for(i in 1..4)
     */
    private fun repeatTest() {
        repeat(4) {
            LogTool.e("lz", "第个元素")
        }
    }

    /**
     * while循环和 java while 一样
     */
    private fun whileTest() {
        var i = 0
        while (i < 10) {
            LogTool.e("lz", "第$i 个元素")
            i++
        }
    }

    @Test
    fun arrayTest() {
        val array = Array(10) { num -> num * 10 }
        array.forEach {
            LogTool.e("lz", "it:$it")
        }
    }

    @Test
    fun testFor() {
        val i = 0
        when (i) {
            // 单个条件判断
            1 -> {

            }
            // 多个条件判断
            2, 3, 4 -> {

            }
            // range 判断
            in 5..7 -> {

            }
            !in 8..10 -> {

            }
            else -> {

            }
        }
    }

    /**
     * 数组测试
     */
    @Test
    fun testArray() {
//        // 创建一个不可变的 map 集合
//        val map = mapOf("1" to "sdcsd1", "2" to "sdcsd2", "3" to "sdcsd3")
//        // 创建一个可变的 MutableMap 集合
//        val mutableMap = mutableMapOf(1 to "sdc1", 2 to "sdc2", 3 to "sdc3")
//        // 创建一个可变的 HashMap 集合
//        val hashMap = hashMapOf(1 to "sdc1", 2 to "sdc2", 3 to "sdc3")
//        // 判断所有的元素 key>2, value.length > 2则返回 true,否则返回false
//        mutableMap.all { it.key > 2 && it.value.length > 2 }
//
//        // 遍历
//        for (en in map.entries) {
//            LogTool.e("test", "en:key:${en.key} value:${en.value}")
//        }
//        // 遍历 key
//        for (key in map.keys) {
//            LogTool.e("test", "key:$key")
//        }
//        // 遍历 value
//        for (value in map.values) {
//            LogTool.e("test", "value:$value")
//        }
        testList()
    }

    fun testList() {

        Thread(Runnable {
            for (i in 1..200) {
                LogTool.e("add list $i")
                list.add("a$i")
            }
        }).start()

        Thread(Runnable {
            Thread.sleep(1)
            for (i in 0..100) {
                LogTool.e("list${list[i]}")
            }
        }).start()
    }


}
