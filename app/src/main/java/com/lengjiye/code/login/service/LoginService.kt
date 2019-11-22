package com.lengjiye.code.login.service

import com.lengjiye.code.constant.ServerConstants
import com.lengjiye.code.me.bean.UserBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @Author: lz
 * @Date: 2019-11-05
 * @Description:
 */
interface LoginService {

    @POST(ServerConstants.USER_LOGIN)
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<BaseHttpResult<UserBean>>


    @POST(ServerConstants.USER_REGISTER)
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") rePassword: String): Observable<BaseHttpResult<UserBean>>

    @GET(ServerConstants.USER_LOGOUT)
    fun logout(): Observable<BaseHttpResult<String>>
}