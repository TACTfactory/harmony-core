<@header?interpret />

#import "ImageUtils.h"
#import "Config.h"
#import "AppDelegate.h"
#import "EntityResourceBase.h"
#import <AssetsLibrary/AssetsLibrary.h>

@implementation ImageUtils

+ (NSString *) IMAGE_KEY_JSON {
    return @"file";
}

+ (NSString*) getImageUrl:(NSString*) path {
    NSString *result = path;

    if ([self checkPathImageURL:path] && ![path hasPrefix:@"http"]) {
        NSString *config = IMAGE_URL_PREFIX_PROD;

        result = [NSString stringWithFormat:@"%@%@", config, path];
    }

    return result;
}

+ (bool)checkPathImageURL:(NSString *) path {
    bool result = true;

    if ([path hasPrefix:@"assets"]) {
        result = false;
    }

    return result;
}

+ (NSString *) getImageUri:(NSString *) path {
    NSString *result = path;

    if (![self checkPathImageURL:path]) {
        result = [NSString stringWithFormat:@"file://%@", path];
    } else {
        result = [self getImageUrl:path];
    }

    return path;
}

+ (void) getImageWithUri:(EntityResourceBase *) image withCallback:(void(^)(UIImage *)) callback {
    NSString *path = image.path;

    if (![self checkPathImageURL:path]) {
        ALAssetsLibraryAssetForURLResultBlock resultBlock = ^(ALAsset *asset) {
            ALAssetRepresentation *representation = [asset defaultRepresentation];
            CGImageRef reference = [representation fullResolutionImage];

            if (reference) {
                callback([UIImage imageWithCGImage:reference]);
            }
        };

        ALAssetsLibraryAccessFailureBlock failureblock = ^(NSError *error) {
            NSLog(@"error %@", [error localizedDescription]);
            callback(nil);
        };

        ALAssetsLibrary *assetLibrary = [ALAssetsLibrary new];
        [assetLibrary assetForURL:[NSURL URLWithString:path] resultBlock:resultBlock failureBlock:failureblock];
    }
}

+ (void) getImageData:(NSString*) file withMaxSize:(int) maxSize withCallback:(void(^)(NSData*)) callback {
    ALAssetsLibraryAssetForURLResultBlock resultBlock = ^(ALAsset *asset) {
        ALAssetRepresentation *representation = [asset defaultRepresentation];
        CGImageRef imageRef = [representation fullResolutionImage];
        if (imageRef) {
            UIImage *bitmap = [UIImage imageWithCGImage:imageRef];
            bitmap = [self imageWithImage:bitmap maxSize:maxSize];

            NSData *bitmapData = nil;

            if ([[file pathExtension].lowercaseString isEqualToString:@"png"]
                || [[file pathExtension].lowercaseString hasSuffix:@"png"]) {
                bitmapData = UIImagePNGRepresentation(bitmap);
            } else {
                bitmapData = UIImageJPEGRepresentation(bitmap, 0.85f);
            }

            if (callback != nil) {
                callback(bitmapData);
            }
        }
    };

    ALAssetsLibraryAccessFailureBlock failureblock = ^(NSError *error) {
        NSLog(@"error %@", [error localizedDescription]);
    };

    ALAssetsLibrary *assetLibrary = [ALAssetsLibrary new];
    [assetLibrary assetForURL:[NSURL URLWithString:file] resultBlock:resultBlock failureBlock:failureblock];
    
}

+ (UIImage *) imageWithImage:(UIImage *) image maxSize:(int) maxSize {
    CGSize newSize;
    int width = image.size.width;
    int height = image.size.height;

    if (width > maxSize || height > maxSize) {
        if (width > height) {
            height = (height * maxSize) / width;
            width = maxSize;
        } else {
            width = (width * maxSize) / height;
            height = maxSize;
        }
    }

    newSize = CGSizeMake(width, height);

    UIGraphicsBeginImageContext(newSize);
    [image drawInRect:CGRectMake(0, 0, newSize.width, newSize.height)];
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();

    return newImage;
}

+ (NSString *) getFilenameFromAssetPath:(NSString *) assetPath {
    NSString *result = nil;

    if (assetPath != nil) {
        NSURL *fileUrl = [NSURL URLWithString:assetPath];
        NSURLComponents *urlComponents = [NSURLComponents componentsWithURL:fileUrl resolvingAgainstBaseURL:false];
        NSArray *queryItems = urlComponents.queryItems;
        NSString *paramId = [self valueForKey:@"id" fromQueryItems:queryItems];
        NSString *paramExt = [self valueForKey:@"ext" fromQueryItems:queryItems];

        result = [NSString stringWithFormat:@"%@.%@", paramId, [paramExt lowercaseString]];
    }

    return result;
}

+ (NSString *) valueForKey:(NSString *) key fromQueryItems:(NSArray*) queryItems {
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"name=%@", key];
    NSURLQueryItem *queryItem = [[queryItems filteredArrayUsingPredicate:predicate] firstObject];

    return queryItem.value;
}

@end
