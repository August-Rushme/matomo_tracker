package com.example.matomo_tracker.traffic;

import com.example.matomo_tracker.base.ApplicationHolder;
import com.example.matomo_tracker.traffic.sdk.Tracker;
import com.example.matomo_tracker.traffic.sdk.extra.TrackHelper;
import com.example.matomo_tracker.traffic.sdk.extra.TrafficApplication;

import java.util.UUID;

public class TrafficUtils {

    public static final String API_URL = "https://gl.ewdcloud.com/traffic.php"; //固定地址，不变

    public static Tracker getTracker() {
        return ((TrafficApplication) ApplicationHolder.getApplication()).getTracker();
    }
    
    
    
    /**
     * 进入页面
     *
     * @param: path 页面路径，以"/"分隔
     */
    public static void screen(String title) {
        try {
            Tracker tracker = getTracker();

          //TODO:配置
            tracker.setUserId("");
            title = title == null ? "" : title;
            TrackHelper.track().screen("").title(title).with(tracker);
            tracker.dispatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入稿件
     *
     * @param path 页面路径，以"/"分隔
     */
    public static void screen(String title, String path) {
        try {
            Tracker tracker = getTracker();
            //TODO:配置
            tracker.setUserId("");
            path = path == null ? "" : path;
            TrackHelper.track().screen(path).title(title).with(tracker);
            tracker.dispatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 触发事件
     *
     * @param category 分类
     * @param action   事件名称
     */
    public static void event(String category, String action) {
        try {
            Tracker tracker = getTracker();
            //TODO:配置
            tracker.setUserId("");
            TrackHelper.track().event(category, action).with(tracker);
            tracker.dispatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 进入页面
     * <p>
     * 传
     */
    public static void setUserId() {
        try {
            Tracker tracker = getTracker();
            try {
//                String deviceId = PhoneUtils.getIMEI() + "0";
                String deviceId = UUID.randomUUID().toString();
                if (deviceId.length() > 16){
                    deviceId = deviceId.substring(0,16);
                }
                tracker.setVisitorId(deviceId);

            } catch (Throwable e) {
                e.printStackTrace();
                tracker.setVisitorId("1234567891234567");

            }
            //TODO:配置
            tracker.setUserId("");
            tracker.dispatch();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


}
