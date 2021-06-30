package com.lengjiye.code.login.serve

import com.lengjiye.code.constant.ServerApi
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.network.BaseHttpResult
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
interface LoginServe {

    @POST(ServerApi.USER_LOGIN)
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Flow<BaseHttpResult<UserBean>>


    @POST(ServerApi.USER_REGISTER)
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") rePassword: String): Flow<BaseHttpResult<UserBean>>

    @GET(ServerApi.USER_LOGOUT)
    fun logout(): Flow<BaseHttpResult<String>>
}