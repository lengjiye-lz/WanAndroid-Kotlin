package com.lengjiye.base.application;

import android.content.Context;
import com.lengjiye.base.inter.IApp;

public class MasterApplication implements IApp {

    private IApp iApp;

    private static class Instance {
        private static MasterApplication master = new MasterApplication();
    }

    public static MasterApplication getInstance() {
        return Instance.master;
    }

    public void setIApp(IApp iApp) {
        this.iApp = iApp;
    }

    @Override
    public Context getApplication() {
        if (iApp == null) {
            return null;
        }
        return iApp.getApplication();
    }

    @Override
    public String getApplicationId() {
        if (iApp == null) {
            return null;
        }
        return iApp.getApplicationId();
    }

    @Override
    public int getVersionCode() {
        if (iApp == null) {
            return 0;
        }
        return iApp.getVersionCode();
    }

    @Override
    public String getVersionName() {
        if (iApp == null) {
            return null;
        }
        return iApp.getVersionName();
    }

    @Override
    public Boolean isDebug() {
        if (iApp == null) {
            return null;
        }
        return iApp.isDebug();
    }

    @Override
    public String getBuildType() {
        if (iApp == null) {
            return null;
        }
        return iApp.getBuildType();
    }
}
