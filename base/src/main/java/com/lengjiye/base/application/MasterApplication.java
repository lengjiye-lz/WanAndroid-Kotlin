package com.lengjiye.base.application;

import android.app.Application;
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
    public Context getApplicationContext() {
        if (iApp != null) {
            return iApp.getApplicationContext();
        }
        return null;
    }

    @Override
    public Application getApplication() {
        if (iApp != null) {
            iApp.getApplication();
        }
        return null;
    }
}
