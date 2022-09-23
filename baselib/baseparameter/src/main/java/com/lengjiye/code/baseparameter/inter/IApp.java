package com.lengjiye.code.baseparameter.inter;

import android.app.Application;
import android.content.Context;

public interface IApp {

    Context applicationContext();

    Application application();

    String applicationId();

    int versionCode();

    String versionName();

    Boolean isDebug();

    String buildType();

    String baseUrl();

    void logout();
}
