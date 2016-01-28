<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<#assign hasLocaleTime = MetadataUtils.hasLocaleTime(curr) />
<@header?interpret />

#import "${curr.name}DataLoader.h"
#import "${curr.name}.h"
#import "DateUtils.h"

static NSString *FILE_NAME = @"${curr.name}";

@implementation ${curr.name}DataLoader

+ (instancetype) get${curr.name}DataLoader {
    static dispatch_once_t pred;
    static id shared = nil;

    dispatch_once(&pred, ^{
        shared = [[super alloc] init];
    });

    return shared;
}

- (void) loadModel {
}

- (NSString *) getFixtureFileName {
    return FILE_NAME;
}

- (void) load:(DataManager *) dataManager {
    for(id key in self->items) {
        ${curr.name} *item = [self->items objectForKey:key];
        item.${curr_ids[0].name} = [dataManager persist:item];
    }
}

@end
