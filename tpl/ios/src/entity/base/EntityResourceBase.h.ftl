<@header?interpret />
<#assign sync=false />
<#assign rest=false />
<#list entities?values as entity>
    <#if entity.options.rest??>
        <#assign rest=true />
    </#if>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>

#import <Foundation/Foundation.h>
<#if sync>
#import "EntityBase.h"
</#if>

<#if rest>
#import "RestResource.h"
<#else>
#import "Resource.h"
</#if>

@interface EntityResourceBase : <#if sync>EntityBase<#else>NSObject</#if> <<#if rest==true>RestResource<#else>Resource</#if>>

@property (nonatomic, strong) NSString *path;
    <#if !sync>
@property (nonatomic) int id;
    </#if>
    <#if (sync) || (rest)>
@property (nonatomic, strong) NSString *localPath;
    </#if>

@end