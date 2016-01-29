<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fixtureType = options["fixture"].type />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<#assign hasLocaleTime = MetadataUtils.hasLocaleTime(curr) />
<@header?interpret />

#import "${curr.name}.h"
#import "DateUtils.h"
${ImportUtils.importRelatedLoaders(curr, true)}

static NSString *FILE_NAME = @"${curr.name}";

@implementation ${curr.name}DataLoader

+ (instancetype) get${curr.name}DataLoader {
    static dispatch_once_t pred;
    static id shared = nil;

    dispatch_once(&pred, ^{
        shared = [super new];
    });

    return shared;
}

- (void) loadModel {
}

- (NSString *) getFixtureFileName {
    return FILE_NAME;
}

- (void) load:(DataManager *) dataManager {
    for(id key in self->items) {
        ${curr.name} *item = [self extractItem:[self->items objectForKey:key]];
        item.${curr_ids[0].name} = [dataManager persist:item];
    }
}

- (${curr.name} *) extractItem:(${curr.name} *) item {
    ${curr.name} *result = item;

<#list curr_fields as field>
    <#if (FieldsUtils.getObjectiveType(field)?lower_case == "datetime")>
    if ([item.${field.name} isKindOfClass:[NSString class]]) {
        if (((NSString *) item.${field.name}).length == 10) {
            result.${field.name} = [DateUtils fixtureStringToDate:(NSString *) item.${field.name}];
        } else {
            result.${field.name} = [DateUtils isoStringToDate:(NSString *) item.${field.name}];
        }
    }

    <#elseif (field.relation??)>
    if (item.${field.name} != nil) {
        result.${field.name} = [[[${field.relation.targetEntity}DataLoader get${field.relation.targetEntity}DataLoader] getItems] objectForKey:item.${field.name}];
    }

    </#if>
</#list>
<#if (InheritanceUtils.isExtended(curr))>
    [[${curr.inheritance.superclass.name?cap_first}DataLoader get${curr.inheritance.superclass.name?cap_first}DataLoader] extractItem:item];

</#if>
    return result;
}


@end
