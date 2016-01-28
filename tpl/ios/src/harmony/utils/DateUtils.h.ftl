<@header?interpret />

#import <Foundation/Foundation.h>

@interface DateUtils : NSObject

/**
 * Change NSString in NSDate with the format : "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ".
 * @param the NSString to format
 * @return NSDate
 */
+ (NSDate *) isoStringToDate:(NSString *) isoString;

/**
 * Change NSDate in NSString with the format : "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ".
 * @param the NSDate to format
 * @return NSString
 */
+ (NSString *) dateToISOString:(NSDate *) date;

/**
 * Change NSDate in NSString with default format.
 * @param the NSDate to format
 * @return NSString
 */
+ (NSString *) dateToString:(NSDate *) date;

/**
 * Change NSString in NSDate with the format : "yyyy-MM-dd HH:mm".
 * @param the NSString to format
 * @return NSDate
 */
+ (NSDate *) fixtureStringToDate:(NSString *) fixtureString;

/**
 * Change NSString in NSDate with the format : "yyyy-MM-dd".
 * @param the NSString to format
 * @return NSDate
 */
+ (NSDate *) fixtureStringToDateTime:(NSString *) fixtureString;

@end