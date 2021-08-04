//
//  TrackerTool.h
//  TrafficClassSDK
//
//  Created by ZhouChenglong on 2020/12/7.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface TrackerTool : NSObject

/**
 设置 sitId
 -必须提前设置，否则初始化不成功
 */
+ (void)setSitId:(NSString *)sitId;
/**
 传递userId
 */
+ (void)setUserId:(NSString *)userId;
/**
 页面列表事件
 */
+ (void)addTrackView:(NSArray<NSString *> *)views;
+ (void)addTrackView:(NSArray<NSString *> *)views url:(NSURL * _Nullable)url;
/**
按钮点击事件
*/
+ (void)trackerEventCategory:(NSString *)category action:(NSString *)action;
+ (void)trackerEventCategory:(NSString *)category action:(NSString *)action url:(NSURL * _Nullable)url;
+ (void)trackerEventCategory:(NSString *)category action:(NSString *)action name:(NSString * _Nullable)name number:(NSNumber * _Nullable)number url:(NSURL * _Nullable)url;


@end

NS_ASSUME_NONNULL_END
