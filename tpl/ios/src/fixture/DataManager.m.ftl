<@header?interpret />

#import "DataManager.h"
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
#import "${entity.name?cap_first}SQLiteAdapter.h"
    </#if>
</#list>

<#list entities?values as entity>
    <#if (((entity.fields?size>0) || (entity.inheritance??)) && !(entity.internal))>
static ${entity.name?cap_first}SQLiteAdapter *${entity.name?upper_case};
    </#if>
</#list>

@implementation DataManager

- (id) init {
    if (self = [super init]) {
        ${entity.name?upper_case} = [${entity.name?cap_first}SQLiteAdapter new]
    }
}

- (int) persist:(id) item {
    int result = -1;

<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
    if ([item isKindOfClass:[${entity.name?cap_first} class]]) {
        result = (int) [${entity.name?upper_case} insert:item];
    }

    </#if>
</#list>
    return result;
}

- (void) remove:(id) item {
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
    if ([item isKindOfClass:[${entity.name?cap_first} class]]) {
        [${entity.name?upper_case} remove:item];
    }

    </#if>
</#list>
}

@end