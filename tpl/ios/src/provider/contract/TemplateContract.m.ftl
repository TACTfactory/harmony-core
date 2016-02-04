<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name?cap_first}Contract.h"

@implementation ${curr.name?cap_first}Contract

@end