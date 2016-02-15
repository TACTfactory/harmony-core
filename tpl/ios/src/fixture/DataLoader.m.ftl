<#include utilityPath + "all_imports.ftl" />
<#assign orderedEntities = MetadataUtils.orderEntitiesByRelation() />
<@header?interpret />

#import "DataLoader.h"
#import "DataManager.h"
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
#import "${entity.name?cap_first}DataLoader.h"
    </#if>
</#list>

@implementation DataLoader

static bool hasFixturesBeenLoaded = false;

- (id) init {
    if (self = [super init]) {
        self->loaders = [NSMutableArray array];
        [self initializeLoaders];
    }

    return self;
}

- (void) initializeLoaders {

<#list orderedEntities as entityName>
    <#assign entity = entities[entityName] />
    <#if (!(entity.internal) && (entity.fields?size>0 || entity.inheritance??))>
    [self->loaders addObject:[${entity.name?cap_first}DataLoader get${entity.name?cap_first}DataLoader]];
    </#if>
</#list>
}

- (void) loadData:(int) modes {
    DataManager *dataManager = [DataManager new];

    for (FixtureBase *dataLoader in self->loaders) {
        if ([self isType:modes withMode:[DataLoader MODE_APP]]) {
            [dataLoader getModelFixtures:[DataLoader MODE_APP]];
        }

        if ([self isType:modes withMode:[DataLoader MODE_DEBUG]]) {
            [dataLoader getModelFixtures:[DataLoader MODE_DEBUG]];
        }

        if ([self isType:modes withMode:[DataLoader MODE_TEST]]) {
            [dataLoader getModelFixtures:[DataLoader MODE_TEST]];
        }

        [dataLoader load:dataManager];
    }

    hasFixturesBeenLoaded = true;
}

- (bool) isType:(int) modes withMode:(int) mode {
    bool result = false;

    if ((modes & mode) == mode) {
        result = true;
    }

    return result;
}

-(void) clean {
    for (FixtureBase *dataLoader in self->loaders) {
        [dataLoader clear];
    }
}

+ (bool) hasFixturesBeenLoaded {
    return hasFixturesBeenLoaded;
}

+ (int) MODE_APP {
    return (int)strtol("0001", NULL, 2);
}

+ (int) MODE_DEBUG {
    return (int)strtol("0010", NULL, 2);
}

+ (int) MODE_TEST {
    return (int)strtol("0100", NULL, 2);
}

@end