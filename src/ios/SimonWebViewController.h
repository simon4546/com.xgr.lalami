//
//  SimonWebViewController.h
//  拉拉蜜
//
//  Created by simon on 15-1-23.
//
//

#import <Foundation/Foundation.h>

@interface SimonWebViewController : UIViewController<UIWebViewDelegate>{
    UIWebView *webView;
    UIActivityIndicatorView *activityIndicator;
}
@property (strong, nonatomic) NSString *urlString;
@end
