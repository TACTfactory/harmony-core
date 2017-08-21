<@header?interpret />

#import "AppDelegate.h"
#import "${project_name?cap_first}SQLiteOpenHelper.h"

@implementation AppDelegate

- (BOOL) application:(UIApplication *) application didFinishLaunchingWithOptions:(NSDictionary *) launchOptions {

    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];

    self->navigationController = [UINavigationController new];
    self.homeViewController = [[HomeViewController alloc] initWithNibName:@"HomeViewController" bundle:nil];
    [self->navigationController pushViewController:self.homeViewController animated:true];
    [self.window addSubview:self->navigationController.view];
    [self.window setRootViewController:self->navigationController];

    [[UINavigationBar appearance] setTranslucent:false];

    [self.window makeKeyAndVisible];

    [[${project_name?cap_first}SQLiteOpenHelper get${project_name?cap_first}SQLiteOpenHelperBase] getDatabase];

    return YES;
}

@end