package com.example.matomo_tracker.base;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class ApplicationHolder {

    private static Application application = null;

    private static Map<String, Object> runtimeVariables = new HashMap<>(); //执行参数，目前看就是全局变量啦，我也搞不懂为啥全局变量要放这里

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        ApplicationHolder.application = application;
    }

    public static Context getContext() {
        return application;
    }

    public static <T> T getRuntimeVariable(String name) {
        synchronized (ApplicationHolder.class) {
            return (T) runtimeVariables.get(name);
        }
    }

    public static void setRuntimeVariable(String name, Object value) {
        synchronized (ApplicationHolder.class) {
            runtimeVariables.put(name, value);
        }
    }

}
