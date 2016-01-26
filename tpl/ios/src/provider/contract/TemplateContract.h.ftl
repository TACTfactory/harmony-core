<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name?cap_first}ContractBase.h"

/** ${project_name?cap_first} ${curr.name} entity contract. */
@interface ${curr.name?cap_first}Contract : ${curr.name?cap_first}ContractBase

@end
