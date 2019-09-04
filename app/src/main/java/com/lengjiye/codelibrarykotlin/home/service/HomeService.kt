package com.lengjiye.codelibrarykotlin.home.service

import com.lengjiye.codelibrarykotlin.home.HomeBean
import com.lengjiye.network.BaseHttpResult
import io.reactivex.Observable
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HomeService {

    @POST
    @FormUrlEncoded
    fun getsdc(): Observable<BaseHttpResult<HomeBean>>

}