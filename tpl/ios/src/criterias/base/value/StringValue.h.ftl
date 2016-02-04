<@header?interpret />

#import "CriteriaValue.h"

@interface StringValue : CriteriaValue

/**
 * Contructor.
 * @param value The value of this string.
 */
- (id) initWithValue:(NSString *) value;

/**
 * Set the value of this StringValue.
 * @param value The new value
 */
- (void) setValue:(NSString *) value;

/**
 * Get this StringValue's value.
 * @return the value
 */
- (NSString *) getValue;

@end