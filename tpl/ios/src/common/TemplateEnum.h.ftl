<#include utilityPath + "all_imports.ftl" />
<@header?interpret />
<#assign curr = enums[current_entity] />

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSUInteger, ${curr.name}) {
<#list curr.values?keys as key>
    <#if (lastLine??)>${lastLine?replace("{COMMA}", ",")}</#if>
        <#assign lastLine="${key} = ${curr.values[key]}{COMMA}"/>
</#list>
    <#if (lastLine??)>${lastLine?replace("{COMMA}", "")}</#if>
};