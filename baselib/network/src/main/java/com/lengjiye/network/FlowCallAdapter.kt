package com.lengjiye.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.*
import java.lang.reflect.Type

class ResponseCallAdapter<T>(
        private val responseType: Type
) : CallAdapter<T, Flow<Response<T>>> {
    override fun adapt(call: Call<T>): Flow<Response<T>> {
        return flow { emit(call.awaitResponse()) }
    }

    override fun responseType() = responseType
}


class BodyCallAdapter<T : Any>(private val responseType: Type) : CallAdapter<T, Flow<T>> {
    override fun adapt(call: Call<T>): Flow<T> {
        return flow { emit(call.await()) }
    }

    override fun responseType() = responseType
}
