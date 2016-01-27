<@header?interpret />

#import <Foundation/Foundation.h>

@interface ItemBatch : NSObject {
    @protected
    NSDictionary *values;
    NSString *whereClause;
    NSArray *whereArgs;
}

@property NSString *tableName;

/**
 * Init the item batch with the values, table name, where clause, where args.
 * @param values The values
 * @param tableName The tableName
 * @param whereClause The whereClause
 * @param whereArgs The whereArgs
 * @return ItemBatch
 */
- (id) initWithItemValues:(NSDictionary *) values
            withTableName:(NSString *) tableName
          withWhereClause:(NSString *) whereClause
            withWhereArgs:(NSArray *) whereArgs;

/**
 * Get the query.
 * @return query
 */
- (NSString *) getQuery;

/**
 * Get the args.
 * @return args
 */
- (NSArray *) getArgs;

@end