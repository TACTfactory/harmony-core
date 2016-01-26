<@header?interpret />

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "EntityResourceBase.h"

@interface ImageUtils : NSObject

+ (NSString *) IMAGE_KEY_JSON;

/**
 * Get a readable uri for a given image path.
 * @param context
 * @param path
 * @return
 */
+ (NSString *) getImageUri:(NSString *) path;

/**
 * Get a full http url for a given image path.
 * @param context
 * @param path
 * @return
 */
+ (NSString *) getImageUrl:(NSString *) path;

/**
 * Check if the image path is not an url from the server.
 * @param path Path of the image
 * @return true if the path is an url
 */
+ (bool) checkPathImageURL:(NSString*) path;

+ (void) getImageWithUri:(EntityResourceBase *) path withCallback:(void(^)(UIImage *)) callback;

+ (void) getImageData:(NSString *) file withMaxSize:(int) maxSize withCallback:(void(^)(NSData *)) callback;

+ (UIImage *) imageWithImage:(UIImage *) image maxSize:(int) maxSize;

+ (NSString *) getFilenameFromAssetPath:(NSString *) assetPath;

@end