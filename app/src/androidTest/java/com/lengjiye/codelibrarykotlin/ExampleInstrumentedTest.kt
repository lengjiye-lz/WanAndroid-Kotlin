package com.lengjiye.codelibrarykotlin

import android.util.Log
import androidx.test.runner.AndroidJUnit4

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
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        whileTest()
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
            Log.e("lz", "forEach:$it")
            if (it == "2") return@forEachTest
            Log.e("lz", "forEach:$it")

            for (i in 1..4) {
                Log.e("lz", "for:$i")
                if (i == 2) {
                    break
                }
                Log.e("lz", "for:$i")
            }
        }
        Log.e("lz", "forEach:")
    }

    /**
     * for循环测试
     */
    private fun forTest() {
        loop@ for (i in 1..4) {
            loop1@ for (j in 1..5) {
                Log.e("lz", "for:$j")
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
            Log.e("lz", "第$index 个是$element")
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
            Log.e("lz", "第$i 个元素")
        }
    }

    /**
     * repeat测试
     * 代替 简单的 for 循环  比如 for(i in 1..4)
     */
    private fun repeatTest() {
        repeat(4) {
            Log.e("lz", "第个元素")
        }
    }

    /**
     * while循环和 java while 一样
     */
    private fun whileTest() {
        var i = 0
        while (i < 10) {
            Log.e("lz", "第$i 个元素")
            i++
        }
    }


}
