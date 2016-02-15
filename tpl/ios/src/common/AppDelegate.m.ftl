<@header?interpret />

#import "AppDelegate.h"
#import "${project_name?cap_first}SQLiteOpenHelper.h"

@implementation AppDelegate

- (BOOL) application:(UIApplication *) application didFinishLaunchingWithOptions:(NSDictionary *) launchOptions {

    [[${project_name?cap_first}SQLiteOpenHelper get${project_name?cap_first}SQLiteOpenHelperBase] getDatabase];

    return YES;
}

@end