package com.lengjiye.code

import androidx.test.runner.AndroidJUnit4
import com.lengjiye.tools.log.log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KotlinCoroutineTest {

    @Test
    fun testMain() {
        runBlocking {
            flow {
                for (i in 1..10) {
                    emit(i)
                    log("emit:$i")
                    delay(100)
                }
            }.produceIn(this)
        }












//
//            listOf(1, 3, 5, 7, 9).asFlow().collect {
//                print(it)
//            }
//
//            flow {
//                emit(1)
//                emit(2)
//                emit(3)
//                emit(4)
//            }.collect {
//                print(it)
//            }

//            productor().collect {
//                delay(100)
//                println("custom $it")
//            }
    }

    // 间隔100ms 输出一个数据
    suspend fun productor() = channelFlow<Int> {
        for (i in 1..10) {
            delay(100)
            send(i)
            println("produce $i")
        }
    }

    fun <T> Flow<T>.merge(other: Flow<T>): Flow<T> = channelFlow {
        // collect from one coroutine and send it
        launch {
            collect { send(it) }
        }
        // collect and send from this coroutine, too, concurrently
        other.collect { send(it) }
    }


}