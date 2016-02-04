<@header?interpret />

#import <Foundation/Foundation.h>
#import "CriteriaValue.h"
#import "CriteriaExpression.h"

@interface SelectValue : CriteriaValue {
    @private
    /** The table referenced by this SelectValue. */
    NSString *refTable;
    /** The field in refTable that will match this criteria's key. */
    NSString *refKey;
    /** A criteria of the refTable type. */
    CriteriaExpression *criteria;
}

/**
 * Get the refTable.
 * @return the refTable
 */
- (NSString *) getRefTable;

/**
 * Set the refTable.
 * @param thing The refTable
 */
- (void) setRefTable:(NSString *) refTable;

/**
 * Get the refKey.
 * @return the refKey
 */
- (NSString *) getRefKey;

/**
 * Set the refKey.
 * @param thing The refKey
 */
- (void) setRefKey:(NSString *) refKey;

/**
 * Get the criteria.
 * @return the criteria
 */
- (CriteriaExpression *) getCriteria;

/**
 * Set the criteria.
 * @param thing The criteria
 */
- (void) setCriteria:(CriteriaExpression *) criteria;

@end