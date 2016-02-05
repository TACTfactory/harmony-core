<#include utilityPath + "all_imports.ftl" />
<#assign fields = ViewUtils.getAllFields(curr)?values + curr_fields />
<#assign relations = ViewUtils.getAllRelations(curr) />
<#assign relation_array = [] />
<#assign hasRelations = false />
<#assign hasInternalFields = false />
<#assign inherited = false />
<#list relations as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<@header?interpret />

#import "${curr.name?cap_first}ProviderUtilsBase.h"
${ImportUtils.importRelatedSQLiteAdapters(curr, false)}
#import "${curr.name?cap_first}Contract.h"
<#list relations as relation>
    <#if (relation.relation.type == "ManyToMany")>
#import "${relation.relation.joinTable?cap_first}Contract.h"
    </#if>
#import "${relation.relation.targetEntity?cap_first}Contract.h"
</#list>

/**
 * ${curr.name?cap_first} Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
@implementation ${curr.name?cap_first}ProviderUtilsBase

-(id) init {
    if (self = [super init]) {
        self->adapter = [${curr.name?cap_first}SQLiteAdapter new];
    }

    return self;
}

- (${curr.name?cap_first} *) query:(${curr.name?cap_first} *) item {
    return [self queryWithId<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:item.${id.name}</#list>];
}

-(${curr.name?cap_first} *) queryWithId<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive> *</#if>) ${id.name}</#list> {
    ${curr.name?cap_first} *result = [self->adapter query<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:${id.name}</#list>];

    if (result) {
        <#list relations as relation>
            <#if (!relation.internal)>
                <#if (relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne")>

        if (result.${relation.name?uncap_first}) {
            result.${relation.name?uncap_first} = [self getAssociate${relation.name?cap_first}:result];
        }
                <#elseif (relation.relation.type == "OneToMany")>

        result.${relation.name?uncap_first} = [self getAssociate${relation.name?cap_first}:result];
                </#if>
            </#if>
        </#list>
    }

    return result;
}

- (NSArray *) queryWithExpression:(CriteriaExpression *) expression {
    NSArray *result = nil;

    Cursor *cursor = [self->adapter query:${curr.name?cap_first}Contract.ALIASED_COLS
                          withWhereClause:expression.toSQLiteSelection
                            withWhereArgs:expression.toSQLiteSelectionArgs
                              withGroupBy:nil
                               withHaving:nil
                              withOrderBy:nil];

    result = [${curr.name?cap_first}Contract cursorToItems:cursor];

    [cursor close];

    return result;
}

- (NSArray *) queryAll {
    NSArray *result = nil;

    <#if (InheritanceUtils.isExtended(curr))>
    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?", ${curr.inheritance.superclass.name?cap_first}Contract.COL_DISCRIMINATORCOLUMN];

    NSArray *whereArgs = [NSArray arrayWithObject:${curr.name}Contract.DISCRIMINATOR_IDENTIFIER];

    Cursor *cursor = [self->adapter query:${curr.name?cap_first}Contract.ALIASED_COLS
                          withWhereClause:whereClause
                            withWhereArgs:whereArgs
                              withGroupBy:nil
                               withHaving:nil
                              withOrderBy:nil];
    <#else>
    Cursor *cursor = [self->adapter query:${curr.name?cap_first}Contract.ALIASED_COLS
                          withWhereClause:nil
                            withWhereArgs:nil
                              withGroupBy:nil
                               withHaving:nil
                              withOrderBy:nil];
    </#if>

    result = [${curr.name?cap_first}Contract cursorToItems:cursor];

    [cursor close];

    return result;
}

    <#list relations as relation>
        <#if (!relation.internal)>
            <#if (relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne")>
-(${relation.relation.targetEntity?cap_first} *) getAssociate${relation.name?cap_first}:(${curr.name} *) item {
    ${relation.relation.targetEntity?cap_first} *result;

    ${relation.relation.targetEntity?cap_first}SQLiteAdapter *${relation.relation.targetEntity?uncap_first}Adapter = [${relation.relation.targetEntity?cap_first}SQLiteAdapter new];

    result = [${relation.relation.targetEntity?uncap_first}Adapter getByID:<#list entities[relation.relation.targetEntity].ids as id><#if id_index != 0> with${id.name?cap_first}:</#if>item.${relation.name?uncap_first}.${id.name?uncap_first}</#list>];

    return result;
}
            <#elseif (relation.relation.type == "OneToMany")>
-(NSArray *) getAssociate${relation.name?cap_first}:(${curr.name} *) item {
    NSMutableArray *result = [NSMutableArray array];

    ${relation.relation.targetEntity?cap_first}SQLiteAdapter *${relation.relation.targetEntity?uncap_first}Adapter = [${relation.relation.targetEntity?cap_first}SQLiteAdapter new];

    Cursor *cursor${relation.relation.targetEntity?cap_first} = <#list curr_ids as id>[${relation.relation.targetEntity?uncap_first}Adapter getBy${relation.relation.mappedBy?cap_first}:item.${id.name}</#list>
                                               withProjection:${relation.relation.targetEntity?cap_first}Contract.ALIASED_COLS
                                              withWhereClause:nil
                                                withWhereArgs:nil
                                                   withOrderBy:nil];

    result = [NSMutableArray arrayWithArray:[${relation.relation.targetEntity?cap_first}Contract cursorToItems:cursor${relation.relation.targetEntity?cap_first}]];

    [cursor${relation.relation.targetEntity?cap_first} close];

    return result;
}
            </#if>

        </#if>
    </#list>

- (int) delete:(${curr.name?cap_first} *) item {
    int result = -1;

    result = [self->adapter remove:item];

    return result;
}

- (int) update:(${curr.name?cap_first} *) item {
    int result = -1;

    result = [self->adapter update:item];

    return result;
}

- (long long) insert:(${curr.name?cap_first}*) item {
    long long result = [self->adapter insert:item];

    <#list curr_ids as id>
        <#if FieldsUtils.getObjectiveType(id)=="int">
    item.${id.name?uncap_first} = (int) result;
        </#if>
    </#list>

    return result;
}
<#if curr.options.sync??>

- (${curr.name?cap_first} *) getByServerId:(NSNumber*) serverId {
    ${curr.name?cap_first}* result;

    result = [self->adapter getByServerID:serverId];

    return result;
}
</#if>

@end
