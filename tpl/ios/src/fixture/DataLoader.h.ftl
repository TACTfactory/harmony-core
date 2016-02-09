<#include utilityPath + "all_imports.ftl" />
<#assign orderedEntities = MetadataUtils.orderEntitiesByRelation() />
<@header?interpret />

#import <Foundation/Foundation.h>

@interface DataLoader : NSObject {
    @private
    NSMutableArray *loaders;
}

/**
 * Check if the fixture has been loaded.
 * @return true if loaded
 */
+ (bool) hasFixturesBeenLoaded;

/**
 * Load the data.
 */
- (void) loadData:(int) mode;

/**
 * Clean the data.
 */
- (void) clean;

/**
 * Get the int define for the MODE_APP.
 * return int MODE_APP
 */
+ (int) MODE_APP;

/**
 * Get the int define for the MODE_DEBUG.
 * return int MODE_DEBUG
 */
+ (int) MODE_DEBUG;

/**
 * Get the int define for the MODE_TEST.
 * return int MODE_TEST
 */
+ (int) MODE_TEST;

@end