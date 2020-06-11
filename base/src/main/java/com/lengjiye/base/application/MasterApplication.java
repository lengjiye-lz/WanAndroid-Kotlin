package com.lengjiye.base.application;

import android.content.Context;

import com.lengjiye.base.inter.IApp;

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
    }

    public static MasterApplication getInstance() {
        return Instance.masterApplication;
    }

    private static class Instance {
        private static MasterApplication masterApplication = new MasterApplication();
    }

}
