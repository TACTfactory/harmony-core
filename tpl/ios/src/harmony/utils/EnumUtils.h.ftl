<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <Foundation/Foundation.h>

@interface EnumUtils : NSObject

<#list enums?values as enum>
+ (NSDictionary *) get${enum.name?cap_first}Values;
</#list>

@end