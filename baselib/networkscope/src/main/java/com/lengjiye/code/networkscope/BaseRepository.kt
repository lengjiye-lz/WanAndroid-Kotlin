package com.lengjiye.code.networkscope

import com.lengjiye.code.exception.Api1Exception
import com.lengjiye.tools.log.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 */
object BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> BaseHttpResult<T>?): Result<T> {
        val baseHttpResult = try {
            call()
        } catch (e: Exception) {
            e.printStackTrace()
            log("e:${e.message}")
            return Result.Error(Api1Exception(404, "网络错误"))
        }

        return executeResponse(baseHttpResult, {
            baseHttpResult?.data
        }, {
            baseHttpResult?.errorMsg
        })
    }

    private suspend fun <T : Any> executeResponse(
        baseHttpResult: BaseHttpResult<T>?,
        successScope: (suspend CoroutineScope.() -> Unit)? = null,
        fileScope: (suspend CoroutineScope.() -> Unit)? = null
    ): Result<T> {
        return coroutineScope {
            when (baseHttpResult?.errorCode) {
                0 -> {
                    successScope?.let { it() }
                    Result.Success(baseHttpResult.data)
                }
                else -> {
                    fileScope?.let { it() }
                    Result.Error(Api1Exception(baseHttpResult?.errorCode, baseHttpResult?.errorMsg))
                }
            }
        }
    }
}