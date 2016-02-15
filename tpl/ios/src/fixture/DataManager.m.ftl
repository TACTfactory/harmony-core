<@header?interpret />

#import "DataManager.h"
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
#import "${entity.name?cap_first}SQLiteAdapter.h"
    </#if>
</#list>

<#list entities?values as entity>
    <#if (((entity.fields?size>0) || (entity.inheritance??)) && !(entity.internal))>
static NSString *${entity.name?upper_case};
    </#if>
</#list>

@implementation DataManager {
    @private
    NSMutableDictionary *adapters;
}

- (id) init {
    if (self = [super init]) {
<#list entities?values as entity>
    <#if (((entity.fields?size>0) || (entity.inheritance??)) && !(entity.internal))>
        ${entity.name?upper_case} = @"${entity.name?cap_first}";
    </#if>
</#list>

    self->adapters = [NSMutableDictionary new];
<#list entities?values as entity>
    <#if (((entity.fields?size>0) || (entity.inheritance??)) && !(entity.internal))>
        [self->adapters setObject:[${entity.name?cap_first}SQLiteAdapter new] forKey:${entity.name?upper_case}];
    </#if>
</#list>

    }

    return self;
}

- (int) persist:(id) item {
    int result = -1;

    SQLiteAdapterBase *adapter = [self getRepository:item];

    result = (int) [adapter insert:item];

    return result;
}

- (void) remove:(id) item {
    SQLiteAdapterBase *adapter = [self getRepository:item];

    [adapter remove:item];
}

- (SQLiteAdapterBase *) getRepository:(id) item {
    NSString *className = NSStringFromClass([item class]);

    return [self->adapters valueForKey:className];
}

@end