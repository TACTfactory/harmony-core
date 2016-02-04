<@header?interpret />

#import "DateUtils.h"

@implementation DateUtils

+ (NSDate *) isoStringToDate:(NSString *)isoString{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ"];
    NSDate *date = [dateFormatter dateFromString:isoString];
    
    return date;
}

+ (NSString *) dateToISOString:(NSDate *)date{
    NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
    [dateFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ"];
    NSString *isoString = [dateFormatter stringFromDate:date];
    
    return isoString;
}

+ (NSString *) dateToString:(NSDate *) date {
    
    NSString *dateString = [NSDateFormatter
                            localizedStringFromDate:date
                            dateStyle:NSDateFormatterMediumStyle
                            timeStyle:NSDateFormatterMediumStyle];
    return dateString;
}

+ (NSDate *) isoStringToTime:(NSString *)isoString {
    return nil;
}
@end
