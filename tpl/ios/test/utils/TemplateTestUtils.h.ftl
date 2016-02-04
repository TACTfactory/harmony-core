<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name?cap_first}TestUtilsBase.h"

@interface ${curr.name?cap_first}TestUtils : ${curr.name?cap_first}TestUtilsBase

@end
