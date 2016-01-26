<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<#assign hasLocaleTime = MetadataUtils.hasLocaleTime(curr) />
<@header?interpret />
#import "FixtureBase.h"

@interface ${curr.name}DataLoader : FixtureBase

/**
 * Get the ${curr.name}DataLoader.
 * @return ${curr.name}DataLoader
 */
+ (instancetype) get${curr.name}DataLoader;

@end