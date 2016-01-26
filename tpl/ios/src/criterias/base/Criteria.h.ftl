<@header?interpret />

#import <Foundation/Foundation.h>

/**
 * Interface for storing criterias in criterias.
 */
@interface Criteria : NSObject

/**
 * Convert the criteria to a SQLite String.
 * @return The SQLite String representation of the criteria. ex : <br />
 * "(price > 15.0)"
 */
- (NSString *) toSQLiteString;

/**
 * Convert the criteria to a SQLite Selection String.
 * @return The SQLite Selection String representation of the criteria.<br />
 * ex : "(price > ?)"
 */
- (NSString *) toSQLiteSelection;

/**
 * Convert the criteria to a SQLite Selection Args array.
 * @param array The SQLite SelectionArgs array of String . ex : <br />
 * ["15"]
 */
- (void) toSQLiteSelectionArgs:(NSMutableArray *) array;

@end