package com.lengjiye.network;


import com.lengjiye.base.application.MasterApplication;
import com.lengjiye.tools.LogTool;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SignInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originRequest = chain.request();

        Request.Builder requestBuilder = originRequest.newBuilder();
        // 可以添加公共请求参数
        Request request = requestBuilder.build();
        Response response = chain.proceed(request);
        ResponseBody body = response.body();
        if (body != null) {
            MediaType mediaType = body.contentType();
            LogTool.d("mediaType=" + mediaType);
            String content = body.string();
            if ("debug".equals(MasterApplication.getInstance().buildType())) {
                LogTool.e("#############################################################");
                LogTool.e("request.url()=" + request.url());
                LogTool.e("response.body()=" + content);
                LogTool.e("#############################################################");
            }
            return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
        }
        return response;
    }
}
