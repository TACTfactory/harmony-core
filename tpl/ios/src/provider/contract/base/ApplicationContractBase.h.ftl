<@header?interpret />

#import <Foundation/Foundation.h>

#import "Cursor.h"

/**
 * Generic Contract class for the provider calls.
 */
@interface ContractBase : NSObject

/** Table name. */
+ (NSString *) TABLE_NAME;
/** COLS. */
+ (NSArray *) COLS;
/** ALIASED_COLS. */
+ (NSArray *) ALIASED_COLS;

/**
 * Converts into a content values.
 *
 * @param item to convert
 *
 * @return The content values
 */
+ (NSDictionary *) itemToContentValues:(id) item;

/**
 * Converts a Cursor.
 *
 * @param cursor The cursor to convert
 *
 * @return The extracted item 
 */
+ (id) cursorToItem:(Cursor *) cursor;

/**
 * Convert Cursor of database to entity.
 * @param cursor Cursor object
 * @param result entity
 */
+ (void) cursorToItem:(Cursor *) cursor withItem:(id) result;

/**
 * Convert Cursor of database to Array of entity.
 * @param cursor Cursor object
 * @return Array of entity
 */
+ (NSArray *) cursorToItems:(Cursor *) cursor;

@end