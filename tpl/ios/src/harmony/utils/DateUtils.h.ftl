<@header?interpret />

#import <Foundation/Foundation.h>

@interface DateUtils : NSObject

+ (NSDate *) isoStringToDate:(NSString *)isoString;
+ (NSString *) dateToISOString:(NSDate *)date;
+ (NSString *) dateToString:(NSDate *) date;

@end