#import "Message.h"

@implementation Message

@synthesize callbackId;
@synthesize notificationCallbackId;
@synthesize callback;



- (void)browser:(CDVInvokedUrlCommand*)command;
{
    self.callbackId = command.callbackId;
    NSString *url=[command.arguments objectAtIndex:0];
    if(url != nil){
        Class viewControllerClass = NSClassFromString(@"SimonWebViewController");
        SimonWebViewController* webview = [[viewControllerClass alloc] init];
        webview.urlString=url;
        UINavigationController *navigationController = [[UINavigationController alloc] initWithRootViewController:webview];
        
        //[webview setDelegate:self];
        [self.viewController presentViewController:navigationController animated:YES completion:nil];
    }else{
        [self successWithMessage:@"WRONG PARAMETERS"];
    }
}
- (void)dismissModalViewController:(CDVInvokedUrlCommand *)command {
    NSDictionary *options = [[command arguments] objectAtIndex:0];
    
    BOOL animated = (BOOL)[options objectForKey:@"animated"];
    
    [self.viewController dismissViewControllerAnimated:animated  completion:^(void){
        // Code
    }];
    
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:result callbackId:[command callbackId]];
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