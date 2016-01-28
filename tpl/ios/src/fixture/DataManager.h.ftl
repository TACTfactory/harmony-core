<@header?interpret />
#import <Foundation/Foundation.h>

@interface DataManager : NSObject

/**
 * Persist item.
 * @param item The item
 * @return int
 */
- (int) persist:(id) item;

/**
 * Removes an object instance.
 *
 * A removed object will be removed from the database as a result of <br />
 * the flush operation.
 *
 * @param object $object The object instance to remove.
 */
- (void) remove:(id) item;

@end