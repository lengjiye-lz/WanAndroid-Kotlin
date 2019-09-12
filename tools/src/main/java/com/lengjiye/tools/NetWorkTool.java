package com.lengjiye.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lengjiye.base.application.MasterApplication;

/**
 * 网络相关
 */
public class NetWorkTool {

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetWorkConnected() {
        ConnectivityManager mConnectivityManager =
                (ConnectivityManager) MasterApplication.getInstance().applicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}
