<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <UIKit/UIKit.h>
#import "HomeViewController.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate> {
    @public
    UINavigationController *navigationController;
}

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) HomeViewController *homeViewController;

@end
