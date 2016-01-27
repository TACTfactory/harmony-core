<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name}TestDBBase.h"

@interface ${curr.name}TestDB : ${curr.name}TestDBBase

@end
