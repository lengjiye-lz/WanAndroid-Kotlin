package com.lengjiye.code.baseparameter.application;

import android.app.Application;
import android.content.Context;

import com.lengjiye.code.baseparameter.manager.ActivityLifecycleCallback;
import com.lengjiye.code.baseparameter.inter.IApp;

public class MasterApplication implements IApp {

    private IApp iApp;

    @Override
    public Context applicationContext() {
        if (iApp == null) {
            return null;
        }
        return iApp.applicationContext();
    }

    @Override
    public Application application() {
        if (iApp == null) {
            return null;
        }
        return iApp.application();
    }

    @Override
    public String applicationId() {
        if (iApp == null) {
            return null;
        }
        return iApp.applicationId();
    }

    @Override
    public int versionCode() {
        if (iApp == null) {
            return 0;
        }
        return iApp.versionCode();
    }

    @Override
    public String versionName() {
        if (iApp == null) {
            return null;
        }
        return iApp.versionName();
    }

    @Override
    public Boolean isDebug() {
        if (iApp == null) {
            return null;
        }
        return iApp.isDebug();
    }

    @Override
    public String buildType() {
        if (iApp == null) {
            return null;
        }
        return iApp.buildType();
    }

    @Override
    public String baseUrl() {
        if (iApp == null) {
            return null;
        }
        return iApp.baseUrl();
    }

    @Override
    public void logout() {
        if (iApp == null) {
            return;
        }
        iApp.logout();
    }

    public void setIApp(IApp iApp) {
        this.iApp = iApp;
        // 注册activity生命周期
        iApp.application().registerActivityLifecycleCallbacks(new ActivityLifecycleCallback());
    }

    public static MasterApplication getInstance() {
        return Instance.masterApplication;
    }

    private static class Instance {
        private static MasterApplication masterApplication = new MasterApplication();
    }

}
