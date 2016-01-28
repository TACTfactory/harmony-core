<@header?interpret />

#import "DataManager.h"
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
#import "${entity.name?cap_first}SQLiteAdapter.h"
    </#if>
</#list>

@implementation DataManager

- (int) persist:(id) item {
    int result = -1;

<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
    if ([item isKindOfClass:[${entity.name?cap_first} class]]) {
        ${entity.name?cap_first}SQLiteAdapter *${entity.name?uncap_first}SQLiteAdapter = [${entity.name?cap_first}SQLiteAdapter new];
        result = (int) [${entity.name?uncap_first}SQLiteAdapter insert:item];
    }

    </#if>
</#list>
    return result;
}

- (void) remove:(id) item {
<#list entities?values as entity>
    <#if (((entity.fields?size>0)  || (entity.inheritance??)) && !(entity.internal))>
    if ([item isKindOfClass:[${entity.name?cap_first} class]]) {
        ${entity.name?cap_first}SQLiteAdapter *${entity.name?uncap_first}SQLiteAdapter = [${entity.name?cap_first}SQLiteAdapter new];
        [${entity.name?uncap_first}SQLiteAdapter remove:item];
    }

    </#if>
</#list>
}

@end