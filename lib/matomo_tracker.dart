
import 'dart:async';

import 'package:flutter/services.dart';

class MatomoTracker {
  static const MethodChannel _channel =
      const MethodChannel('matomo_tracker');
  static Future<String> initializeTracker(String siteId) async {
    Map<String, dynamic> args ={};
    args.putIfAbsent('siteId', () => siteId);
    final String result = await _channel.invokeMethod('initializeTracker', args);
    return result;
  }
 // title ["首页","列表"]
  static Future<String> trackScreen(String title, String urlStr, String userId) async {
    Map<String, dynamic> args ={};
    args.putIfAbsent('title', () => title);
    args.putIfAbsent('urlStr', () => urlStr);
    args.putIfAbsent('userId', () => userId);
    final String result = await _channel.invokeMethod('trackScreen', args);
    return result;
  }
  // category "首页/导航栏/搜索" acrion "搜索"
  static Future<String> trackEvent(String category, String action, String userId) async {
    Map<String, dynamic> args ={};
    args.putIfAbsent('category', () => category);
    args.putIfAbsent('action', () => action);
    args.putIfAbsent('userId', () => userId);
    final String result = await _channel.invokeMethod('trackEvent', args);
    return result;
  }

  static Future<String> trackSet(String userId) async {
    Map<String, dynamic> args ={};
    args.putIfAbsent('userId', () => userId);
    final String result = await _channel.invokeMethod('trackSet', args);
    return result;
  }


}
