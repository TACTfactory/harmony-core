<#include utilityPath + "all_imports.ftl" />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<@header?interpret />

#import <Foundation/Foundation.h>
${ImportUtils.importRelatedEntities(curr, false)}
#import "Cursor.h"

/** ${project_name?cap_first} contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
@interface ${curr.name?cap_first}ContractBase : NSObject

<#if (curr.fields?size > 0 || curr.inheritance??)>
        <#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />
        <#assign hasInternalFields = false /><#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<#if (singleTabInheritance && !isTopMostSuperClass)>
/** Identifier for inheritance. */
+ (NSString*) DISCRIMINATOR_IDENTIFIER;

</#if>
/** Table name of SQLite database. */
+ (NSString*) TABLE_NAME;
<#list curr_fields as field><#if !field.relation?? || (field.relation.type != "ManyToMany" && field.relation.type != "OneToMany")>${ContractUtils.getFieldsDeclarationsHeader(field, curr)}
</#if></#list>
<#if (singleTabInheritance && isTopMostSuperClass)>
    /** Discriminator column. */
+ (NSString*) ${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
    /** Alias. */
+ (NSString*) ALIASED_${NamingUtils.alias(curr.inheritance.discriminatorColumn.name)};
</#if>

/** Global Fields. */
+ (NSArray*) COLS;
/** Global Fields. */
+ (NSArray*) ALIASED_COLS;

<#if !curr.internal>
/**
 * Converts a ${curr.name} into a content values.
 *
 * @param item The ${curr.name} to convert
 *
 * @return The content values
 */
+ (NSDictionary *) itemToContentValues:(${curr.name} *)item;

/**
 * Converts a Cursor into a ${curr.name}.
 *
 * @param cursor The cursor to convert
 *
 * @return The extracted ${curr.name} 
 */
+ (${curr.name?cap_first} *) cursorToItem:(Cursor *) cursor;

/**
 * Convert Cursor of database to ${curr.name} entity.
 * @param cursor Cursor object
 * @param result ${curr.name} entity
 */
+ (void) cursorToItem:(Cursor *)cursor with${curr.name?cap_first}:(${curr.name} *)result;

/**
 * Convert Cursor of database to Array of ${curr.name} entity.
 * @param cursor Cursor object
 * @return Array of ${curr.name} entity
 */
+ (NSArray *) cursorToItems:(Cursor *)cursor;
    </#if>
</#if>

@end
