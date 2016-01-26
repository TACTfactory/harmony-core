<@header?interpret />

#import "Criteria.h"
#import "Criterion.h"

typedef NS_ENUM(NSUInteger, GroupType) {
    AND = 0,
    OR = 1
};

/**
 * CriteriaExpression.
 * An array of Criterion and CriteriaExpression. Used for db requests.
 * A CriteriaExpression represents the WHERE clause of a request.
 * @param T The entity type
 */
@interface CriteriaExpression : Criteria

    /**
     * Constructor.
     * @param type The Criteria's GroupType
     */
- (id) initWithType:(GroupType) type;

    /**
     * Converts the criteria expression to a Selection args String array.
     * @return The String[] of selection args
     */
- (NSArray *) toSQLiteSelectionArgs;


    /**
     * Adds a criterion of form : (key TYPE value).
     * @param crit The criterion to add
     * @return True if the criterion is valid and doesn't exists yet
     */
- (bool) addWithCriteria:(Criteria *) crit;

    /**
     * Add a criterion of form : (key TYPE value).
     * @param key The db column
     * @param value The value
     * @param type The type of criterion (can be Equals, Superior, etc.)
     * @return True if the criterion is valid and doesn't exists yet
     */
- (bool) addWithKey:(NSString *) key withValue:(NSString *) value withType:(Type) type;

    /**
     * Add a criterion of form : (key EQUALS value).
     * @param key The db column
     * @param value The value
     * @return True if the criterion is valid and doesn't exists yet
     */
- (bool) addWithKey:(NSString *) key withValue:(NSString *) value;

    /**
     * Checks if the List is empty.
     * @return true if the List is empty, false otherwise
     */
- (bool) isEmpty;

@end
