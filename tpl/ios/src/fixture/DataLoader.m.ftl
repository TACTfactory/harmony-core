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
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
    [self->loaders addObject:[${entity.name?cap_first}DataLoader get${entity.name?cap_first}DataLoader]];
    </#if>
</#list>
}

- (void) loadData {
    DataManager *dataManager = [DataManager new];

    for (FixtureBase *dataLoader in self->loaders) {
        [dataLoader loadModel];
        [dataLoader load:dataManager];
    }

    hasFixturesBeenLoaded = true;
}

-(void) clean {
    for (FixtureBase *dataLoader in self->loaders) {
        [dataLoader clear];
    }
}

+ (bool) hasFixturesBeenLoaded {
    return hasFixturesBeenLoaded;
}

@end