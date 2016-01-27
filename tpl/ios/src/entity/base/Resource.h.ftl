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


@protocol Resource <NSObject>

    <#if !sync>
@property (nonatomic) int id;
    </#if>
@property (nonatomic, strong) NSString *path;

@end