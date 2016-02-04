<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name?cap_first}TestUtils.h"

@implementation ${curr.name?cap_first}TestUtils

@end