<@header?interpret />

#import <Foundation/Foundation.h>
#import "CriteriaExpression.h"
#import "SQLiteAdapter.h"
#import "BatchProvider.h"

/**
 * Generic Proxy class for the provider calls.
 */
@interface ProviderUtilsBase : NSObject {
    @protected
    SQLiteAdapter *sqlAdapter;
}

/**
 * Execute the batch.
 * @param batch BatchProvider
 */
- (void) executeBatch:(BatchProvider*) batch;

/**
 * Get item by server id.
 * @param serverId The serverId of the item
 * @return the item
 */
- (id) getByServerId:(NSNumber*) serverId;
@end