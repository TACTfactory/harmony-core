<@header?interpret />

#import <Foundation/Foundation.h>

@interface Config : NSObject

/** Host API for dev env. */
+ (NSString *) REST_URL_HOST_DEV;
/** Host API for prod env. */
+ (NSString *) REST_URL_HOST_PROD;

/** Url API for dev env. */
+ (NSString *) REST_URL_DEV;
/** Url API for prod env. */
+ (NSString *) REST_URL_PROD;

/** Url Image for dev env. */
+ (NSString *) IMAGE_URL_PREFIX_DEV;
/** Url Image for prod env. */
+ (NSString *) IMAGE_URL_PREFIX_PROD;

/** Url Image for dev env. */
+ (NSString *) URL_SCHEME_DEV;
/** Url Image for prod env. */
+ (NSString *) URL_SCHEME_PROD;

/** Url Path for dev env. */
+ (NSString *) URL_PATH_DEV;
/** Url Path for prod env. */
+ (NSString *) URL_PATH_PROD;

@end
