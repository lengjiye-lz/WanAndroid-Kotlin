package com.lengjiye.base.inter;

import android.content.Context;

public interface IApp {

    Context getApplication();

    String getApplicationId();

    int getVersionCode();

    String getVersionName();

    Boolean isDebug();

    String getBuildType();
}
