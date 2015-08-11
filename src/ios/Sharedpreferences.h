#import <Foundation/Foundation.h>
#import <Cordova/CDV.h>
#import <Cordova/CDVPlugin.h>

@interface Sharedpreferences : CDVPlugin
{
    NSDictionary *notificationMessage;
    BOOL    isInline;
    NSString *notificationCallbackId;
    NSString *callback;
    
    BOOL ready;
}

@property (nonatomic, copy) NSString *callbackId;
@property (nonatomic, copy) NSString *notificationCallbackId;
@property (nonatomic, copy) NSString *callback;

- (void)put:(CDVInvokedUrlCommand*)command;
+ (void)addSharedConfigEntryFromNative:(NSString*)configKey value:(NSString*)configValue;
- (void)get:(CDVInvokedUrlCommand*)command;


@end