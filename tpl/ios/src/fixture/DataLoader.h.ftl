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
- (void) loadData;

/**
 * Clean the data.
 */
- (void) clean;

@end