<#assign fixtureType = options["fixture"].type />
<@header?interpret />

#import <Foundation/Foundation.h>
#import "DataManager.h"

@interface FixtureBase : NSObject {
    @protected
    NSMutableDictionary *items;
}

/**
 * Load the model.
 */
- (void) loadModel;

/**
 * Load the dataManager.
 * @param dataManager The Datamanager to load
 */
- (void) load:(DataManager *) dataManager;

/**
 * Get the items.
 * @return items
 */
- (NSMutableDictionary *) getItems;

/**
 * Clear the fixture base.
 */
- (void) clear;

@end