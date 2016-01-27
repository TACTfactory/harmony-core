<@header?interpret />

#import <Foundation/Foundation.h>
#import "ItemBatch.h"

@interface BatchProvider : NSObject {
    @private
    NSMutableArray *queue;
}

/**
 * Add the item to batch.
 * @param item ItemBatch
 */
- (void) addToBatch:(ItemBatch *) item;

/**
 * Get the queue.
 * @return NSArray
 */
- (NSArray*) getQueue;

@end