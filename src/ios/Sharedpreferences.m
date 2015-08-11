#import "Sharedpreferences.h"

@implementation Sharedpreferences

@synthesize callbackId;
@synthesize notificationCallbackId;
@synthesize callback;

+ (void)addSharedConfigEntryFromNative:(NSString*)configKey value:(NSString*)configValue;
{
    NSString *configKeyString = (NSString *)configKey;
    
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];  //load NSUserDefaults
    [prefs setValue:configValue forKey:configKeyString];
    
    [prefs synchronize];
}

- (void)put:(CDVInvokedUrlCommand*)command;
{
    self.callbackId = command.callbackId;
    NSString *key=[command.arguments objectAtIndex:0];
    NSString *value=[command.arguments objectAtIndex:1];
    if(key != nil && value!=nil){
        NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];  //load NSUserDefaults
        [prefs setValue:value forKey:key];
        [prefs synchronize];
        [self successWithMessage:@"OK"];
    }else{
        [self successWithMessage:@"WRONG PARAMETERS"];
    }
    /*NSMutableDictionary* options = [command.arguments objectAtIndex:0];
     
     id configKey = [options objectForKey:@"configKey"];
     id configValue = [options objectForKey:@"value"];
     
     if ([configKey isKindOfClass:[NSString class]] && [configValue isKindOfClass:[NSString class]]) {
     NSString *configKeyString = (NSString *)configKey;
     
     NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];  //load NSUserDefaults
     [prefs setValue:configValue forKey:configKeyString];
     
     [prefs synchronize];
     
     [self successWithMessage:@"OK"];
     }else{
     [self successWithMessage:@"WRONG PARAMETERS"];
     }*/
}

- (void)get:(CDVInvokedUrlCommand*)command;
{
    self.callbackId = command.callbackId;
    id storedConfigObj = nil;
    NSString* storedConfig = @"";
    NSString *key=[command.arguments objectAtIndex:0];
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];  //load NSUserDefaults
    storedConfigObj = [prefs objectForKey:key];
    if(storedConfigObj != nil){
        storedConfig = (NSString *)storedConfigObj;
        [self successWithMessage:storedConfig];
    }else{
        [self failWithMessage:@"" withError:nil];
    }
    
    /*
     NSMutableDictionary* options = [command.arguments objectAtIndex:0];
     
     id configKey = [options objectForKey:@"configKey"];
     
     if ([configKey isKindOfClass:[NSString class]]) {
     NSString *configKeyString = (NSString *)configKey;
     
     NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];  //load NSUserDefaults
     storedConfigObj = [prefs objectForKey:configKeyString];
     if(storedConfigObj != nil){
     storedConfig = (NSString *)storedConfigObj;
     }
     }
     */
    
    
}

-(void)successWithMessage:(NSString *)message
{
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:message];
    
    [self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
}

-(void)failWithMessage:(NSString *)message withError:(NSError *)error
{
    NSString        *errorMessage = (error) ? [NSString stringWithFormat:@"%@ - %@", message, [error localizedDescription]] : message;
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errorMessage];
    
    [self.commandDelegate sendPluginResult:commandResult callbackId:self.callbackId];
}

@end