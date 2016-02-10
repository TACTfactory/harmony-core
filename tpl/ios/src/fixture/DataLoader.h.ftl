<#include utilityPath + "all_imports.ftl" />
<#assign orderedEntities = MetadataUtils.orderEntitiesByRelation() />
<@header?interpret />

#import <Foundation/Foundation.h>

static int MODE_TEST = 1;
static int MODE_APP = 2;
static int MODE_DEBUG = 3;

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

@end