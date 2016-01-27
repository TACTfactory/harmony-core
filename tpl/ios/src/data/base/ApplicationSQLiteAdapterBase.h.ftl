    <@header?interpret />

#import <Foundation/Foundation.h>
#import "FMDatabase.h"
#import "FMResultSet.h"
#import "${project_name?cap_first}SQLiteOpenHelper.h"
#import "BatchProvider.h"
#import "UpdateBatch.h"
#import "InsertBatch.h"
#import "DeleteBatch.h"
#import "Cursor.h"


/**
 * This is the base SQLiteAdapter. DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This is the base class for all basic operations for your sqlite adapters.
 *
 * @param <T> Entity type of this adapter.
 */
@interface SQLiteAdapterBase : NSObject {
    
@protected
    /* Database. */
    FMDatabase *mDatabase;
    /** Open Helper. */
    ${project_name?cap_first}SQLiteOpenHelper *mBaseHelper;
}

/**
 * Init the SQLiteAdapterBase
 * @return SQLiteAdapterBase
 */
- (id) init;

/**
 * Notify the content resolver.
 */
- (void) notifyContentResolver;

/** Close database. */
- (void) close;

/**
 * Get the table Name.
 * @return the table name
 */
- (NSString *) getTableName;

/**
 * Get the joined table name used in DB for your Account entity
 * and its parents.
 * @return A String showing the joined table name
 */
- (NSString *) getJoinedTableName;

/**
 * Generate Entity Table Schema.
 * @return "SQL query : CREATE TABLE..."
 */
+ (NSString *) getSchema;

/**
 * Get the table's columns.
 * @return array of cols
 */
- (NSArray *) getCols;

/**
 * Get all entities from the DB.
 * @return A cursor pointing to all entities
 */
- (NSArray *) getAll;

/**
* Send a query to the DB.
* @param tables from clause
* @param projection Columns to work with
* @param whereClause WHERE clause for SQL
* @param whereArgs WHERE arguments for SQL
* @param groupBy GROUP BY argument for SQL
* @param having HAVING clause
* @param orderBy ORDER BY clause
* @return A cursor pointing to the result of the query
*/
- (Cursor *) query:(NSString *) tables
    withProjection:(NSArray *) projection
   withWhereClause:(NSString *) whereClause
     withWhereArgs:(NSArray *) whereArgs
       withGroupBy:(NSString *) groupBy
        withHaving:(NSString *) having
       withOrderBy:(NSString *) orderBy;

/**
 * Send a query to the DB.
 * @param projection Columns to work with
 * @param whereClause WHERE clause for SQL
 * @param whereArgs WHERE arguments for SQL
 * @param groupBy GROUP BY argument for SQL
 * @param having HAVING clause
 * @param orderBy ORDER BY clause
 * @return A cursor pointing to the result of the query
 */
- (Cursor *) query:(NSArray *) projection
   withWhereClause:(NSString *) whereClause
     withWhereArgs:(NSArray *) whereArgs
       withGroupBy:(NSString *) groupBy
        withHaving:(NSString *) having
       withOrderBy:(NSString *) orderBy;

/**
 * Insert a new entity into the DB.
 * @param nullColumnHack nullColumnHack
 * @param item The ContentValues to insert
 * @return the id of the inserted entity
 */
- (long long) insert:(NSString *) nullColumnHack withValues:(NSDictionary *) values;

/**
 * Updates the entities from the DB matching with the query.
 * @param item The ContentValues to be updated
 * @param whereClause WHERE clause for SQL
 * @param whereArgs WHERE arguments for SQL
 * @return How many tokens updated
 */
- (int) update:(NSDictionary *) values withWhereClause:(NSString *) whereClause withWhereArgs:(NSArray *) whereArgs;

/**
 * Delete the entities matching with query from the DB.
 * @param whereClause WHERE clause for SQL
 * @param whereArgs WHERE arguments for SQL
 * @return how many token deleted
 */
- (int) delete:(NSString *) whereClause withWhereArgs:(NSArray *) whereArgs;

/**
 * Convert Cursor of database to Array of entity.
 * @param cursor Cursor object
 * @return Array of entity
 */
- (NSArray *) cursorToItems:(Cursor *) cursor;

/**
 * Converts into a content values.
 *
 * @param item The item to convert
 *
 * @return The content values
 */
- (NSDictionary *) itemToContentValues:(id) item;

/**
 * Get the insert batch.
 * @param item Id of the batch
 * @return the Insert Batch
 */
- (InsertBatch *) getInsertBatch:(id) item;

/**
 * Get the update batch.
 * @param item Id of the batch
 * @return the Update Batch
 */
- (UpdateBatch *) getUpdateBatch:(id) item;

/**
 * Get the update batch.
 * @param item Id of the batch
 * @param whereClause the where clause
 * @param whereArgs the where args
 * @return the Update Batch
 */
- (UpdateBatch *) getUpdateBatch:(id) item
                 withWhereClause:(NSString *) whereClause
                   withWhereArgs:(NSArray *) whereArgs;

/**
 * Get the delete batch.
 * @param item Id of the batch
 * @return the Delete Batch
 */
- (DeleteBatch *) getDeleteBatch:(id) item;

/**
 * Get the delete batch.
 * @param item Id of the batch
 * @param whereClause the where clause
 * @param whereArgs the where args
 * @return the Delete Batch
 */
- (DeleteBatch *) getDeleteBatch:(id) item
                 withWhereClause:(NSString *) whereClause
                   withWhereArgs:(NSArray *) whereArgs;

/**
 * Execute the batch.
 * @param batch The batch provider
 */
- (void) executeBatch:(BatchProvider *) batch;

/**
 * Get the item with server id.
 * @param serverId the serverId
 * @return id the item
 */
- (id) getByServerID:(NSNumber *) serverId;

/**
 * Insert the item into database.
 * @param item The item
 * @return long long Id of item
 */
- (long long) insert:(id) item;

/**
 * Update the item.
 * @param id The item
 * @return bool true if ok
 */
- (bool) update:(id) item;

/**
 * Remove the item.
 * @param id The item
 * @return bool true if ok
 */
- (bool) remove:(id) item;

@end