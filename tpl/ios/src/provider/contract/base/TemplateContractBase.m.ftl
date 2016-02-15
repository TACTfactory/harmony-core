<#include utilityPath + "all_imports.ftl" />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<@header?interpret />

<#if (InheritanceUtils.isExtended(curr))>
#import "${curr.inheritance.superclass.name?cap_first}Contract.h"
</#if>
#import "${curr.name}Contract.h"
#import "${curr.name}ContractBase.h"
<#if hasDate || hasTime || hasDateTime>
#import "DateUtils.h"
</#if>
<#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />

static NSString* TABLE_NAME;
<#list curr_fields as field><#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>${ContractUtils.getFieldsStaticDeclarations(field, curr)}</#if></#list>
<#if (singleTabInheritance && isTopMostSuperClass)>
static NSString* ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
static NSString* ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
</#if>
<#if (singleTabInheritance && !isTopMostSuperClass)>
static NSString* DISCRIMINATOR_IDENTIFIER;
</#if>
/** ${project_name?cap_first} contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
@implementation ${curr.name}ContractBase

+ (void) initialize {
    TABLE_NAME = <#if singleTabInheritance && !isTopMostSuperClass>${curr.inheritance.superclass.name?cap_first}Contract.TABLE_NAME<#else>@"<#if curr.tableName??>${curr.tableName}<#else>${curr.name}</#if>"</#if>;
<#list curr_fields as field><#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>${ContractUtils.getContractFieldInit(field, curr)}</#if></#list>
<#if (singleTabInheritance && !isTopMostSuperClass)>
    DISCRIMINATOR_IDENTIFIER = @"${curr.inheritance.discriminatorIdentifier}";
</#if>
<#if (singleTabInheritance && isTopMostSuperClass)>
    ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = @"${curr.inheritance.discriminatorColumn.columnName}";
    ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} = [NSString stringWithFormat:@"%@.%@", ${curr.name}Contract.TABLE_NAME, ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)}];
</#if>}

<#if (curr.fields?size > 0 || curr.inheritance??)>
        <#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />
        <#assign hasInternalFields = false /><#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<#list curr_fields as field>
<#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>${ContractUtils.getFieldsGetter(field, curr)}
</#if>
</#list>
/** Table name of SQLite database. */
+ (NSString *) TABLE_NAME {
    return TABLE_NAME;
}

<#if (singleTabInheritance && !isTopMostSuperClass)>
+ (NSString *) DISCRIMINATOR_IDENTIFIER {
    return DISCRIMINATOR_IDENTIFIER;
}

</#if>
<#if (singleTabInheritance && isTopMostSuperClass)>
+ (NSString *) ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} {
    return ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
}

+ (NSString*) ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} {
    return ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)} ;
}
</#if>
/** Global Fields. */
+ (NSArray *) COLS {
    return [NSArray arrayWithObjects:
    <#assign wholeFields = curr_fields />
    <#if singleTabInheritance && !isTopMostSuperClass>
        <#assign wholeFields = wholeFields + curr.inheritance.superclass.fields?values />
    </#if>
    <#list wholeFields as field>
        <#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>
            <#assign fieldNames = ContractUtils.getFieldsNames(field) />
            <#list fieldNames as name>
            ${name},
            </#list>
        </#if>
    </#list>
            nil];
}

/** Global Fields. */
+ (NSArray *) ALIASED_COLS {
    return [NSArray arrayWithObjects:
    <#list ViewUtils.getAllFields(curr)?values as field>
        <#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>
            <#assign fieldNames = ContractUtils.getFieldsNames(field, true) />
            <#list fieldNames as name>
            ${name},
            </#list>
        </#if>
    </#list>
            nil];
}
    <#if !curr.internal>

+ (NSDictionary *) itemToContentValues:(${curr.name} *) item {
    NSMutableDictionary *result = [NSMutableDictionary dictionary];

    <#if (singleTabInheritance && !isTopMostSuperClass)>
    [result addEntriesFromDictionary:[${curr.inheritance.superclass.name?cap_first}Contract itemToContentValues:item]];

    [result setObject:${curr.name?cap_first}Contract.DISCRIMINATOR_IDENTIFIER
               forKey:${curr.inheritance.superclass.name?cap_first}Contract.COL_DISCRIMINATORCOLUMN];

    </#if>
    <#list curr_fields as field>
${AdapterUtils.itemToContentValuesFieldAdapter("item", field, 2)}
    </#list>
    return result;
}

+ (${curr.name} *) cursorToItem:(Cursor *) cursor {
    ${curr.name}* result = [${curr.name} new];
    [self cursorToItem:cursor with${curr.name?cap_first}:result];

    return result;
}

+ (void) cursorToItem:(Cursor *)cursor with${curr.name?cap_first}:(${curr.name} *) result {
    int index;

        <#if (InheritanceUtils.isExtended(curr))>
    [${curr.inheritance.superclass.name?cap_first}Contract cursorToItem:cursor with${curr.inheritance.superclass.name?cap_first}:result];
        </#if>
        <#list curr_fields as field>
    ${AdapterUtils.cursorToItemFieldAdapter("result", field, 4)}
        </#list>
}

+ (NSArray *) cursorToItems:(Cursor *) cursor {
    NSMutableArray *result = [NSMutableArray array];
    ${curr.name} *item;

    while ([cursor next]) {
        item = [${curr.name?cap_first}Contract cursorToItem:cursor];
        [result addObject:item];
    }

    return result;
}
    </#if>
</#if>

@end


