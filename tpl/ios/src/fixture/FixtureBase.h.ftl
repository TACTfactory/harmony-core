<#assign fixtureType = options["fixture"].type />
<@header?interpret />

#import <Foundation/Foundation.h>
#import "DataManager.h"

@interface FixtureBase : NSObject {
    @protected
    NSDictionary *items;
}

/**
 * Load the fixtures for the current model.
 * @param mode Mode
 */
- (void) getModelFixtures:(int) mode;

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
 * Returns the fixture file name.
 * @return the fixture file name
 */
- (NSString *) getFixtureFileName;

/**
 * Get the items.
 * @return items
 */
- (NSDictionary *) getItems;

/**
 * Clear the fixture base.
 */
- (void) clear;

@end