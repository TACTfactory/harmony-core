<@header?interpret />

#import <Foundation/Foundation.h>
#import "Config.h"

@implementation Config

+ (NSString *) REST_URL_HOST_DEV { return @"127.0.0.1"; }
+ (NSString *) REST_URL_HOST_PROD { return @"127.0.0.1"; }

+ (NSString *) REST_URL_DEV { return @"http://127.0.0.1:80/app_dev.php/api/"; }
+ (NSString *) REST_URL_PROD { return @"http://127.0.0.1:80/api/"; }

+ (NSString *) IMAGE_URL_PREFIX_DEV { return @"http://127.0.0.1"; }
+ (NSString *) IMAGE_URL_PREFIX_PROD { return @"http://127.0.0.1"; }

+ (NSString *) URL_SCHEME_DEV { return @"http"; }
+ (NSString *) URL_SCHEME_PROD { return @"http"; }

+ (NSString *) URL_PATH_DEV { return @"app_dev.php/api/"; }
+ (NSString *) URL_PATH_PROD { return @"api/"; }

@end