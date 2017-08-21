<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "EnumUtils.h"

<#list enums?values as enum>
#import "${enum.name?cap_first}.h"
</#list>

@implementation EnumUtils

<#list enums?values as enum>
+ (NSDictionary *) get${enum.name?cap_first}Values {
    NSMutableDictionary *list = [NSMutableDictionary new];

<#list enum.values?keys as key>
    [list setValue:[NSNumber numberWithInt:${key}] forKey:@"${key}"];
</#list>

    return list;
}
</#list>

@end
