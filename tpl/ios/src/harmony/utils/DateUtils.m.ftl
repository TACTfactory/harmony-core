<@header?interpret />

#import "DateUtils.h"

static NSString *TIME_AMPM_PATTERN = @"hh:mm a";
static NSString *TIME_24H_PATTERN = @"hh:mm";
static NSString *REGEX_XML_DATE = @"([0-9][0-9][0-9][0-9])-([0-9][0-9])-([0-9][0-9])";
static NSString *REGEX_XML_TIME = @"([Tt]|[ \\t]+)([0-9][0-9]?):([0-9][0-9]):([0-9][0-9])(\\.[0-9]*)?";
static NSString *REGEX_XML_TIMEZONE = @"(Z|[-+][0-9][0-9]:[0-9][0-9]";

@implementation DateUtils

+ (NSDate *) isoStringToDate:(NSString *) isoString {
    NSDateFormatter *dateFormatter = [NSDateFormatter new];
    [dateFormatter setDateFormat:@"yyyy-MM-dd'T'HH:mm:ss.SSSSSSZZZZZ"];
    NSDate *date = [dateFormatter dateFromString:isoString];

    return date;
}

+ (NSString *) dateToISOString:(NSDate *) date {
    NSDateFormatter *dateFormatter = [NSDateFormatter new];
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

+ (NSDate *) isoStringToTime:(NSString *) isoString {
    return nil;
}

+ (NSDate *) formatXMLStringToDateTime:(NSString *) fixtureString {
    NSDate *result = nil;

    NSDateComponents *components = [NSDateComponents new];
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSRegularExpression *regexTimezone = [NSRegularExpression regularExpressionWithPattern:REGEX_XML_TIMEZONE
                                                                                   options:0
                                                                                     error:nil];

    NSTextCheckingResult *matchTimeZone = [regexTimezone firstMatchInString:fixtureString
                                                                    options:0
                                                                      range:NSMakeRange(0, fixtureString.length)];

    if (match != nil) {
        [components setTimeZone:[NSTimeZone timeZoneWithName:[fixtureString
                                                              substringWithRange:[matchTimeZone rangeAtIndex:1]]]];
    } else {
        [components setTimeZone:[NSTimeZone defaultTimeZone]];
    }

    NSRegularExpression *regexDate = [NSRegularExpression regularExpressionWithPattern:REGEX_XML_DATE
                                                                               options:0
                                                                                 error:nil];

    NSRegularExpression *regexTime = [NSRegularExpression regularExpressionWithPattern:REGEX_XML_TIME
                                                                               options:0
                                                                                 error:nil];

    NSTextCheckingResult *matchDate = [regexDate firstMatchInString:fixtureString
                                                            options:0
                                                              range:NSMakeRange(0, fixtureString.length)];

    if (matchDate != nil) {
        [components setYear:[[fixtureString substringWithRange:[matchDate rangeAtIndex:1]] intValue]];
        [components setMonth:[[fixtureString substringWithRange:[matchDate rangeAtIndex:2]] intValue]];
        [components setSecond:[[fixtureString substringWithRange:[matchDate rangeAtIndex:3]] intValue]];
    } else {
        NSDateComponents *currentDate = [calendar components:NSCalendarUnitYear|NSCalendarUnitMonth|NSCalendarUnitDay
                                                    fromDate:[NSDate date]];

        [components setYear:[currentDate year]];
        [components setMonth:[currentDate month]];
        [components setDay:[currentDate day]];
    }

    NSTextCheckingResult *matchTime = [regexTime firstMatchInString:fixtureString
                                                            options:0
                                                              range:NSMakeRange(0, fixtureString.length)];

    if (matchTime != nil) {
        [components setHour:[[fixtureString substringWithRange:[matchTime rangeAtIndex:2]] intValue]];
        [components setMinute:[[fixtureString substringWithRange:[matchTime rangeAtIndex:3]] intValue]];
        [components setSecond:[[fixtureString substringWithRange:[matchTime rangeAtIndex:4]] intValue]];

        NSString *millisString = [fixtureString substringWithRange:[matchTime rangeAtIndex:5]];
        NSInteger millis;

        if (millisString != nil) {
            millis = [[millisString substringFromIndex:1] integerValue];
        } else {
            millis = 0;
        }

        [components setNanosecond:millis];
    } else {
        NSDateComponents *currentTime =
        [calendar components:NSCalendarUnitHour|NSCalendarUnitMinute|NSCalendarUnitSecond|NSCalendarUnitNanosecond
                    fromDate:[NSDate date]];

        [components setHour:[currentTime hour]];
        [components setMinute:[currentTime minute]];
        [components setSecond:[currentTime second]];
        [components setNanosecond:[currentTime nanosecond]];
    }

    result = [calendar dateFromComponents:components];

    return result;
}

@end
