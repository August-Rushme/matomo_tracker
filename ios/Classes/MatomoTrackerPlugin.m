#import "MatomoTrackerPlugin.h"

#import <TrafficClassSDK/TrafficClassSDK.h>
#import "ECUUIDTool.h"

@implementation MatomoTrackerPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  FlutterMethodChannel* channel = [FlutterMethodChannel
      methodChannelWithName:@"matomo_tracker"
            binaryMessenger:[registrar messenger]];
  MatomoTrackerPlugin* instance = [[MatomoTrackerPlugin alloc] init];
  [registrar addMethodCallDelegate:instance channel:channel];
}

- (void)handleMethodCall:(FlutterMethodCall*)call result:(FlutterResult)result {
  if ([@"initializeTracker" isEqualToString:call.method]) {
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary:call.arguments];
        NSString *siteId = [dic objectForKey:@"siteId"];
        NSString *userId = [dic objectForKey:@"userId"];
        [TrackerTool setSitId:siteId];
        //NSString *uuidStr = [NSString stringWithFormat:@"UUID/%@",[ECUUIDTool getUUIDInKeychain]];
        //[TrackerTool addTrackView:@[uuidStr]];
        if (userId != nil && userId.length > 0) {
          [TrackerTool setUserId:userId];
        }
    } else if ([@"trackScreen" isEqualToString:call.method]){
    //
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary:call.arguments];
        NSString *title = [dic objectForKey:@"title"];
        NSString *urlStr = [dic objectForKey:@"urlStr"];
        NSString *userId = [dic objectForKey:@"userId"];
        NSArray *views = [NSArray arrayWithObjects:title, nil];
        [TrackerTool addTrackView:views url:[NSURL URLWithString:urlStr]];

    } else if ([@"trackEvent" isEqualToString:call.method]){
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary:call.arguments];
        NSString *category = [dic objectForKey:@"category"];
        NSString *action = [dic objectForKey:@"action"];
        NSString *userId = [dic objectForKey:@"userId"];
        [TrackerTool trackerEventCategory:category action:action];

    } else if ([@"trackSet" isEqualToString:call.method]){
        NSDictionary *dic = [NSDictionary dictionaryWithDictionary:call.arguments];
        NSString *userId = [dic objectForKey:@"userId"];
        if (userId != nil && userId.length > 0) {
          [TrackerTool setUserId:userId];
        }
    } else {
      result(FlutterMethodNotImplemented);
    }
}

@end
