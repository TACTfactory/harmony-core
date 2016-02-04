<@header?interpret />

#import "CriteriaValue.h"

@interface ArrayValue : CriteriaValue {
    @private
    NSMutableArray *values;
}

/**
 * Get the values.
 * @return NSArray array
 */
- (NSArray *) getValues;

/**
 * Add the value.
 * @param value The value
 */
- (void) addValue:(NSString *) value;

@end
