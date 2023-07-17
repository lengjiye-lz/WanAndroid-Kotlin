package com.lengjiye.code

import android.util.Log
import com.lengjiye.tools.log.logE
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


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
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class ExampleInstrumentedIApp {
    var list = arrayListOf<String>()

    @Test
    fun useAppContext() {
        quickSort()
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
        val array = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        array.forEach {
            logE("forEach:$it")
            if (it == "2") return@forEachTest
            logE("forEach:$it")

            for (i in 1..4) {
                logE("for:$i")
                if (i == 2) {
                    break
                }
                logE("for:$i")
            }
        }
        logE("forEach:")
    }

    /**
     * for循环测试
     */
    private fun forTest() {
        loop@ for (i in 1..4) {
            loop1@ for (j in 1..5) {
                logE("for:$j")
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
            logE("第$index 个是$element")
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
            logE("第$i 个元素")
        }
    }

    /**
     * repeat测试
     * 代替 简单的 for 循环  比如 for(i in 1..4)
     */
    private fun repeatTest() {
        repeat(4) {
            logE("第个元素")
        }
    }

    /**
     * while循环和 java while 一样
     */
    private fun whileTest() {
        var i = 0
        while (i < 10) {
            logE("第$i 个元素")
            i++
        }
    }

    @Test
    fun arrayTest() {
        val array = Array(10) { num -> num * 10 }
        array.forEach {
            logE("it:$it")
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
//            logE("test", "en:key:${en.key} value:${en.value}")
//        }
//        // 遍历 key
//        for (key in map.keys) {
//            logE("test", "key:$key")
//        }
//        // 遍历 value
//        for (value in map.values) {
//            logE("test", "value:$value")
//        }
//        testList()
        val flow1 =
            mutableListOf<Int>().apply {
                add(1)
                add(2)
                add(3)
                add(4)
                add(5)
            }.asFlow()


        val flow2 =
            mutableListOf<String>().apply {
                add("A")
                add("B")
                add("C")
                add("D")
                add("E")
            }.asFlow()
        val flow3 = mutableListOf<Flow<Any>>().apply {
            add(flow1)
            add(flow2)
        }.asFlow()

        runBlocking {
            Log.e("lz", "it:${11111}")
            flowOf(flow1, flow2).flattenMerge(1).collect {
                Log.e("lz", "flattenMerge:${it}")
            }

            flow3.flattenConcat().collect {
                Log.e("lz", "flattenConcat:${it}")
            }
            Log.e("lz", "it:${22222}")
        }

    }

    fun testList() {

        Thread(Runnable {
            for (i in 1..200) {
                logE("add list $i")
                list.add("a$i")
            }
        }).start()

        Thread(Runnable {
            Thread.sleep(1)
            for (i in 0..100) {
                logE("list${list[i]}")
            }
        }).start()
    }

    /**
     * 归并排序
     */
    fun testGuiBing() {
        val array = arrayOf(8, 4, 2, 5, 6, 3, 1, 7, 9, 0)
        val temp = arrayOfNulls<Int>(array.size)
        merges(array, 0, array.size - 1, temp)
        logE("array:${Arrays.toString(array)}")
    }

    private fun merges(array: Array<Int>, left: Int, right: Int, temp: Array<Int?>) {
        if (left < right) {
            val mid = (left + right) / 2
            merges(array, left, mid, temp)
            merges(array, mid + 1, right, temp)
            merge(array, left, mid, right, temp)
        }
    }


    private fun merge(array: Array<Int>, left: Int, mid: Int, right: Int, temp: Array<Int?>) {
        var i = left
        var j = mid + 1
        var t = 0
        while (i <= mid && j <= right) {
            if (array[i] < array[j]) {
                temp[t] = array[i]
                i += 1
                t += 1
            } else {
                temp[t] = array[j]
                j += 1
                t += 1
            }
        }
        while (i <= mid) {
            temp[t] = array[i]
            i += 1
            t += 1
        }

        while (j <= right) {
            temp[t] = array[j]
            j += 1
            t += 1
        }

        t = 0
        var tempLeft = left
        while (tempLeft <= right) {
            array[tempLeft] = temp[t] ?: 0
            t += 1
            tempLeft += 1
        }
    }

    private fun quickSort() {
        val array = arrayOf(10, 3, 5, 7, 4, 3, 8, 9, 0, 1, 2)
        quickSort(array, 0, array.size - 1)
        logE("array:${Arrays.toString(array)}")
    }

    private fun quickSort(array: Array<Int>, low: Int, high: Int) {
        if (low >= high) {
            return
        }
        val pivot = partition(array, low, high)
        quickSort(array, low, pivot - 1)

        quickSort(array, pivot + 1, high)

    }

    private fun partition(array: Array<Int>, low: Int, high: Int): Int {
        var lowt = low
        var hight = high
        val pivot = array[lowt]
        while (lowt < hight) {
            while (lowt < hight && array[hight] >= pivot) {
                hight--
            }
            array[lowt] = array[hight]
            while (lowt < hight && array[lowt] <= pivot) {
                lowt++
            }
            array[hight] = array[lowt]
        }
        array[lowt] = pivot
        return lowt
    }


}

/**
 *                         _0_
 *                       _oo0oo_
 *                      o8888888o
 *                      88" . "88
 *                      (| -_- |)
 *                      0\  =  /0
 *                    ___/`---'\___
 *                  .' \\|     |// '.
 *                 / \\|||  :  ||| * \
 *                / _||||| -:- |||||- \
 *               |   | \\\  -  /// |   |
 *               | \_|  ''\---/''  |_/ |
 *               \  .-\__  '-'  ___/-. /
 *             ___'. .'  /--.--\  `. .'___
 *          ."" '<  `.___\_<|>_/___.' >' "".
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /
 *     =====`-.____`.___ \_____/___.-`___.-'=====
 *                       `=---='
 ******************************************************
 *     ¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥¥
 *         €€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€€
 *               $$$$$$$$$$$$$$$$$$$$$$$
 *                   BUDDHA_BLESS_YOU
 *                      AWAY_FROM
 *                         BUG
 */
