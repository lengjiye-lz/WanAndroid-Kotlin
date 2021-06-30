package com.lengjiye.code.todo.serve

import com.lengjiye.code.constant.NetWorkParams
import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.todo.bean.TodoBean
import com.lengjiye.network.BaseHttpResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

/**
 * @Author: lz
 * @Date: 2020-05-25
 * @Description:
 */
interface TodoServe {

    @GET(ServerApi.TODO_LIST)
    fun getTodoList(@Path(NetWorkParams.PAGE) page: Int, @QueryMap map: Map<String, String>): Flow<BaseHttpResult<TodoBean>>

    @POST(ServerApi.ADD_TODO)
    @FormUrlEncoded
    fun addTodo(@FieldMap todo: Map<String, String>): Flow<BaseHttpResult<String>>

    @POST(ServerApi.UPDATE_TODO)
    @FormUrlEncoded
    fun updateTodo(@Path(NetWorkParams.ID) id: Int, @FieldMap todo: Map<String, String>): Flow<BaseHttpResult<String>>

    @POST(ServerApi.UPDATE_DONE_TODO)
    @FormUrlEncoded
    fun updateDoneTodo(@Path(NetWorkParams.ID) id: Int, @Path(NetWorkParams.STATUS) status: Int): Flow<BaseHttpResult<String>>

    @POST(ServerApi.DELETE_TODO)
    @FormUrlEncoded
    fun deleteTodo(@Path(NetWorkParams.ID) id: Int): Flow<BaseHttpResult<String>>
}