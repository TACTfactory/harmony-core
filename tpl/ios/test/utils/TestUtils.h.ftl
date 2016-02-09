<@header?interpret />

#import <Foundation/Foundation.h>

@interface TestUtils : NSObject

+ (int) generateRandomInt:(int) minValue withMaxValue:(int) maxValue;

+ (NSString *) jsonToString:(NSDictionary *) json;

@end