<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name?cap_first}ProviderUtils.h"

/**
 * ${curr.name?cap_first} Provider Utils.
 *
 * This class is an utility class for wrapping provider calls.
 * Feel free to modify it, add new methods to it, etc.
 */
@implementation ${curr.name?cap_first}ProviderUtils

@end
