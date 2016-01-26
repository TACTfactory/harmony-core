<@header?interpret />

#import "FMResultSet.h"
#import "FMDatabaseQueue.h"

@interface Cursor : NSObject

/**
 * Init the cursor.
 * @param resultSet The FMResultSet
 * @param queue The FMDatabaseQueue
 * @return the Cursor
 */
- (id) initWithFMResultSet:(FMResultSet *) resultSet andFMDatabaseQueue:(FMDatabaseQueue *) queue;

/**
 * Get the FMResultSet.
 * @return the FMResultSet
 */
- (FMResultSet *) getFMResultSet;

/**
 * Get the FMDatabaseQueue.
 * @return the FMDatabaseQueue
 */
- (FMDatabaseQueue *) getFMDatabaseQueue;

/**
 * How many columns in result set
 * @return Integer value of the number of columns.
 */
- (int) columnCount;

/**
 * Column index for column name
 * @param columnName `NSString` value of the name of the column.
 * @return Zero-based index for column.
 */
- (int) columnIndexForName:(NSString *) columnName;

/**
 * Column name for column index
 * @param columnIdx Zero-based index for column.
 * @return columnName `NSString` value of the name of the column.
 */
- (NSString *) columnNameForIndex:(int) columnIdx;

/**
 * Result set integer value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `int` value of the result set's column.
 */
- (int) intForColumn:(NSString *) columnName;

/**
 * Result set integer value for column.
 * @param columnIdx Zero-based index for column.
 * @return `int` value of the result set's column.
 */
- (int) intForColumnIndex:(int) columnIdx;

/**
 * Result set `long` value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `long` value of the result set's column.
 */
- (long) longForColumn:(NSString *) columnName;

/**
 * Result set long value for column.
 * @param columnIdx Zero-based index for column.
 * @return `long` value of the result set's column.
 */
- (long) longForColumnIndex:(int) columnIdx;

/**
 * Result set `long long int` value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `long long int` value of the result set's column.
 */
- (long long int) longLongIntForColumn:(NSString *) columnName;

/**
 * Result set `long long int` value for column.
 * @param columnIdx Zero-based index for column.
 * @return `long long int` value of the result set's column.
 */
- (long long int) longLongIntForColumnIndex:(int) columnIdx;

/**
 * Result set `bool` value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `bool` value of the result set's column.
 */
- (bool) boolForColumn:(NSString *) columnName;

/**
 * Result set `bool` value for column.
 * @param columnIdx Zero-based index for column.
 * @return `bool` value of the result set's column.
 */
- (bool) boolForColumnIndex:(int) columnIdx;

/**
 * Is the column `NULL`?
 * @param columnIdx Zero-based index for column.
 * @return `true` if column is `NULL`; `false` if not `NULL`.
 */
- (bool) columnIndexIsNull:(int) columnIdx;

/**
 * Is the column `NULL`?
 * @param columnName `NSString` value of the name of the column.
 * @return `true` if column is `NULL`; `false` if not `NULL`.
 */
- (bool) columnIsNull:(NSString *) columnName;

/**
 * Result set `NSString` value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `NSString` value of the result set's column.
 */
- (NSString *) stringForColumn:(NSString *) columnName;

/**
 * Result set `NSString` value for column.
 * @param columnIdx Zero-based index for column.
 * @return `NSString` value of the result set's column.
 */
- (NSString *) stringForColumnIndex:(int) columnIdx;

/**
 * Result set `double` value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `double` value of the result set's column.
 */
- (double) doubleForColumn:(NSString *) columnName;

/**
 * Result set `double` value for column.
 * @param columnIdx Zero-based index for column.
 * @return `double` value of the result set's column.
 */
- (double) doubleForColumnIndex:(int) columnIdx;

/**
 * Result set `NSDate` value for column.
 * @param columnName `NSString` value of the name of the column.
 * @return `NSDate` value of the result set's column.
 */
- (NSDate *) dateForColumn:(NSString *) columnName;

/**
 * Result set `NSDate` value for column.
 * @param columnIdx Zero-based index for column.
 * @return `NSDate` value of the result set's column.
 */
- (NSDate *) dateForColumnIndex:(int) columnIdx;

/**
 * Result set `NSData` value for column.
 * This is useful when storing binary data in table (such as image or the like).
 * @param columnName `NSString` value of the name of the column.
 * @return `NSData` value of the result set's column.
 */
- (NSData *) dataForColumn:(NSString *) columnName;

/**
 * Result set `NSData` value for column.
 * @param columnIdx Zero-based index for column.
 * @return `NSData` value of the result set's column.
 */
- (NSData *) dataForColumnIndex:(int) columnIdx;

/**
 * Retrieve next row for result set.
 * You must always invoke `next` before attempting to access the values returned in a query.
 * @return `true` if row successfully retrieved; `false` if end of result set reached
 */
- (bool) next;

/**
 * Close the cursor.
 */
- (void) close;

@end