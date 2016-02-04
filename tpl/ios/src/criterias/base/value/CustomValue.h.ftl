<@header?interpret />

#import "CriteriaValue.h"
#import "CriteriaExpression.h"

/** A CustomValue is used in a criteria.
 * (Use only with IN type criterias)
 * Example : SELECT * FROM table1 WHERE id IN (
 *          SELECT refKey FROM refTable WHERE criteria); 
 */
@interface CustomValue : CriteriaValue

/**
 * Init the custom value with sql.
 * @param customSql The string of the request
 * @return id CustomValue
 */
- (id) initWithCustomSql:(NSString *) customSql;

/**
 * Init the custom value with sql.
 * @param customSql The string of the request
 * @param criteria CriteriaExpression
 * @return id CustomValue
 */
- (id) initWithCustomSql:(NSString *) customSql withCriteriaExpression:(CriteriaExpression *) criteria;

/**
 * Get the customSql.
 * @return the customSql
 */
- (NSString *) getCustomSql;

/**
 * Set the thing.
 * @param thing The Thing
 */
- (void) setCustomSql:(NSString *) customSql;

/**
 * Get the customSql.
 * @return the customSql
 */
- (CriteriaExpression *) getCriteria;

/**
 * Set the CriteriaExpression.
 * @param thing The CriteriaExpression
 */
- (void) setCriteria:(CriteriaExpression *) criteria;

@end