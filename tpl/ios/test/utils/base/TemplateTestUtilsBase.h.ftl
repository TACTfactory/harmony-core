<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<@header?interpret />

#import <Foundation/Foundation.h>

#import "TestUtils.h"
#import "${curr.name}.h"

@interface ${curr.name}TestUtilsBase : TestUtils

/**
 * Generate a random ${curr.name?uncap_first}1
 *
 * @return The randomly generated ${curr.name?uncap_first}1
 */
+ (${curr.name?cap_first}*) generateRandom;

/**
 * Check if entity and compare is equals.
 *
 * @return result boolean
 */
+ (BOOL) equals:(${curr.name?cap_first}*) entity
    withCompare:(${curr.name?cap_first}*) compare;

@end