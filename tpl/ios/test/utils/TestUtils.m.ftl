<@header?interpret />
#import "TestUtils.h"

@implementation TestUtils

+ (int) generateRandomInt:(int) minValue withMaxValue:(int) maxValue {
    return minValue + arc4random_uniform(maxValue - minValue + 1);
}

+ (NSString *) jsonToString:(NSDictionary *) json {
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:json options:NSJSONWritingPrettyPrinted error:nil];

    return [[NSString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
}

@end