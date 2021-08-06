package com.example.matomo_tracker;

import android.app.Application;

import androidx.annotation.NonNull;

import com.example.matomo_tracker.base.ApplicationHolder;
import com.example.matomo_tracker.traffic.sdk.TrackMe;
import com.example.matomo_tracker.traffic.sdk.Tracker;
import com.example.matomo_tracker.traffic.sdk.TrackerBuilder;
import com.example.matomo_tracker.traffic.sdk.Traffic;
import com.example.matomo_tracker.traffic.sdk.extra.DownloadTracker;
import com.example.matomo_tracker.traffic.sdk.extra.TrackHelper;
import com.example.matomo_tracker.traffic.sdk.extra.TrafficApplication;

import java.util.Map;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** MatomoTrackerPlugin */
public class MatomoTrackerPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Application _application;
  private Tracker _trafficTracker;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "matomo_tracker");
    channel.setMethodCallHandler(this);
    _application = (Application) flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {

    if (call.method.equals("initializeTracker")) {
      Map<String, String> args = (Map<String, String>) call.arguments;
      String siteId = args.get("siteId");

      System.out.println("initializeTracker"+siteId);
      initTraffic(siteId);

    }else if(call.method.equals("trackScreen")){
      Map<String, String> args = (Map<String, String>) call.arguments;
      String title = args.get("title");
      String urlStr = args.get("urlStr");
      String userId = args.get("userId");

      screen(title,urlStr,userId);
    }else if(call.method.equals("trackEvent")){
      Map<String, String> args = (Map<String, String>) call.arguments;
      String category = args.get("category");
      String action = args.get("action");
      String userId = args.get("userId");
      event(category, action, userId);

    }else if(call.method.equals("trackSet")){
      Map<String, String> args = (Map<String, String>) call.arguments;
      String userId = args.get("userId");
      if (userId != null && userId.length() > 0)
      {
        setUserId(userId);
      }
    }
  }

  public Traffic getTraffic() {
    return Traffic.getInstance(_application);
  }
  private synchronized Tracker getTracker() {
    if (_trafficTracker == null) _trafficTracker = onCreateTrackerConfig().build(getTraffic());
    return _trafficTracker;
  }
  /**
   * 配置埋点统计
   */
  private void initTraffic(String siteId) {
    Tracker tracker = getTracker();
    TrackHelper.track()
            .download()
            .identifier(new DownloadTracker.Extra.ApkChecksum(_application))
            .with(tracker);
    tracker.addTrackingCallback((TrackMe trackMe) -> {
//      LogUtils.i(TAG, "Tracker.Callback.onTrack(" + String.valueOf(trackMe) + ")");
      return trackMe;
    });
  }
  /**
   * 进入页面
   *
   * @param title 标题
   * @param urlStr  页面路径，以"/"分隔
   */
  private void screen(String title, String urlStr, String userId) {
    try {
      Tracker tracker = getTracker();
      TrackHelper.track()
              .screen(urlStr.length()> 0 ? urlStr : "")
              .title(title)
              .with(tracker);
      tracker.dispatch();
    } catch (Throwable e){
      e.printStackTrace();
    }
  }
  /**
   * 触发事件
   *
   * @param category 分类
   * @param action   事件名称
   */
  private void event(String category, String action, String userId) {
    try {
      Tracker tracker = getTracker();
      TrackHelper.track()
              .event(category, action)
              .with(tracker);
      tracker.dispatch();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  /**
   * 配置id
   */
  public void setUserId(String userId) {
    try {
      Tracker tracker = getTracker();
      tracker.setUserId(userId);
    } catch (Throwable e){
      e.printStackTrace();
    }
  }

  // 294
  public TrackerBuilder onCreateTrackerConfig() {
    return TrackerBuilder.createDefault(294);
  }
  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
