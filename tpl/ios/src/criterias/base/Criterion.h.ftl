<@header?interpret />

#import <UIKit/UIKit.h>
#import "Criteria.h"
#import "CriteriaValue.h"

typedef NS_ENUM(NSUInteger, Type) {
    /** Equals "=". */
    EQUALS = 0,
    /** Greater than ">". */
    SUPERIOR = 1,
    /** Smaller than "<". */
    INFERIOR = 2,
    /** Inferior or equal "<=". */
    INFERIOR_EQUALS = 3,
    /** Superior or equals ">=". */
    SUPERIOR_EQUALS = 4,
    /** Like "LIKE". */
    LIKE = 5,
    /** IN "IN" (May be used with ArrayValue or SelectValue). */
    IN = 6,
    /** NOT IN "NOT IN" (May be used with ArrayValue or SelectValue). */
    NOT_IN = 7,
    /** IS "IS". */
    IS = 8,
    /** IS NULL. WARNING : Use null Value */
    IS_NULL = 9,
    /** IS NOT NULL. WARNING : Use null Value */
    IS_NOT_NULL = 10,
    /** Different. <>*/
    DIFFERENT = 11
};

/** Criteria. Criteria used for some db requests.
 *
 * A criteria reprensents a condition (ie. "id = 2").
 * It is composed of:
 * - a key (ie. "id")
 * - a Type (ie. "EQUALS")
 * - a value (ie. 2)
 */
@interface Criterion : Criteria

/**
 * Get the Criteria key.
 * @return The Criteria's key
 */
- (NSString *) getKey;

/**
 * Get the Criteria value.
 * @return The Criteria's value
 */
- (CriteriaValue *) getValue;

/**
 * Set the Criteria key.
 * @param key The new key to set
 */
- (void) setKey:(NSString *) key;

/**
 * Set the Criteria key with a datetime function.
 *
 * @param key The new key to set
 * @param options The options of the datetime function
 */
- (void) setDateTimeKey:(NSString *) key withOptions:(NSArray *) options;

/**
 * Set the Criteria key with a date function.
 *
 * @param key The new key to set
 * @param options The options of the date function
 */
- (void) setDateKey:(NSString *) key withOptions:(NSArray *) options;

/**
 * Set the Criteria key with a time function.
 *
 * @param key The new key to set
 * @param options The options of the time function
 */
- (void) setTimeKey:(NSString *) key withOptions:(NSArray *) options;

/**
 * Set the Criteria key with a strtf function.
 *
 * @param format The strtf format
 * @param key The new key to set
 * @param options The options of the strtf function
 */
- (void) setStrtfKey:(NSString *) format withKey:(NSString *) key withOptions:(NSArray *) options;

/**
 * Set the Criteria key as a sqlite method.
 *
 * @param methodName the sqlite method name
 * @param options The options of the sqlite method
 */
- (void) setMethodKey:(NSString *) methodName withOptions:(NSArray *) options;


/**
 * Set the Criteria value.
 * @param value The new value to set
 */
- (void) addValue:(CriteriaValue *) value;

/**
 * Set the Criteria Type.
 * @param type The new Type to set
 */
- (void) setType:(Type) type;

/**
 * Get the Criteria Type.
 * @return The Criteria's type
 */
- (Type) getType;

@end
