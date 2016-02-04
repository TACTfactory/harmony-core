<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name?cap_first}ProviderUtilsBase.h"

/**
 * ${curr.name?cap_first} Provider Utils.
 *
 * This class is an utility class for wrapping provider calls.
 * Feel free to modify it, add new methods to it, etc.
 */
@interface ${curr.name?cap_first}ProviderUtils : ${curr.name?cap_first}ProviderUtilsBase

@end
