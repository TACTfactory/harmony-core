<#include utilityPath + "all_imports.ftl" />
<#assign sync = curr.options.sync?? />
<#assign isRecursiveJoinTable = (curr.internal) && (!curr.relations[1]??) && (curr.relations[0].relation.targetEntity == entities[curr.relations[0].relation.targetEntity].fields[curr.relations[0].relation.inversedBy].relation.targetEntity) />
<#assign hasDateTime=false />
<#assign hasTime=false />
<#assign hasDate=false />
<#assign hasInternalFields = false />
<#list (curr_relations) as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />

<#assign isTopMostSuperClass = (curr.inheritance?? && (!curr.inheritance.superclass?? || !entities[curr.inheritance.superclass.name]??)) />
<@header?interpret />

<#if (InheritanceUtils.isExtended(curr))>
#import "DatabaseUtils.h"
</#if>
 <#list (curr.relations) as relation><#if relation.relation.resource >
#import "ResourceContract.m"
</#if></#list>
#import "${curr.name}SQLiteAdapterBase.h"
${ImportUtils.importRelatedSQLiteAdapters(curr, true, false)}
${ImportUtils.importRelatedContracts(curr, true, true)}
<#if hasDate || hasTime || hasDateTime>#import "DateUtils.h"</#if>
<#if (curr.internal)>
    <#assign extendType = "Void" />
<#else>
    <#assign extendType = curr.name />
</#if>
<#if sync>
    <#assign extend="SyncSQLiteAdapterBase<" +extendType+ ">" />
<#else>
    <#assign extend="SQLiteAdapter<" +extendType+ ">" />
</#if>
<#if curr.internal>
#import "CriteriaExpression.h"
#import "Criterion.h"
#import "SelectValue.h"
</#if>

/** ${curr.name} adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ${curr.name}Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
@implementation ${curr.name}SQLiteAdapterBase
<#if (InheritanceUtils.isExtended(curr))>

- (id) init {
    if (self = [super init]) {
        self.motherAdapter = [${curr.inheritance.superclass.name?cap_first}SQLiteAdapter new];
    }

    return self;
}
</#if>

<#if (singleTabInheritance && !isTopMostSuperClass)>
+ (NSString *) getSchemaColumns {
    NSMutableString *result = [NSMutableString new];

<#list curr_fields as field>
    <#if (!field.columnResult && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
        <#assign fieldNames = ContractUtils.getFieldsNames(field) />
        <#list fieldNames as fieldName>
    <#if (lastLine??)>${lastLine?replace("{COMMA}", ",")}</#if>
            <#if (field.relation?? && field.relation.field_ref?size > 1)>
                <#if field.nullable>
                    <#assign lastLine="[result appendFormat:@\" %@" + field.relation.field_ref[fieldName_index].schema?replace("NOT NULL", "") + "{COMMA}\", " + fieldName + "];" />
                <#else>
                    <#assign lastLine="[result appendFormat:@\" %@" + field.relation.field_ref[fieldName_index].schema + "{COMMA}\", " + fieldName + "];" />
                </#if>
            <#else>
                    <#assign lastLine="[result appendFormat:@\" %@" + field.schema  + "{COMMA}\", " + fieldName + "];" />
            </#if>
        </#list>
    </#if>
</#list>
    <#if (lastLine??)>${lastLine?replace("{COMMA}", "")}</#if>
    <#list curr.inheritance.subclasses as subclass>[result appendString:[${subclass.name}SQLiteAdapter getSchemaColumns]]<#if subclass_has_next> + \n"</#if></#list>

    return result;
}

+ (NSString *) getSchemaConstraints {
    NSMutableString *result = [NSMutableString new];
    <#if (curr.relations??)>
    <#list (curr.relations) as relation>
        <#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
        <#assign fieldNames = ContractUtils.getFieldsNames(relation) />
        <#list fieldNames as fieldName>
        <#assign refId = relation.relation.field_ref[fieldName_index] />
        <#if (lastRelation??)>${lastRelation}</#if>
            <#assign lastRelation="[result appendFormat:@\",FOREIGN KEY(%@) REFERENCES %@ (%@)\",
         ${fieldName},
         ${ContractUtils.getContractTableName(entities[relation.relation.targetEntity])},
         ${ContractUtils.getFieldsNames(refId)[0]}];">
        </#list>
        </#if>
    </#list>
        <#if (lastRelation??)>${lastRelation}</#if>
    </#if>
    <#if (!((singleTabInheritance && curr.inheritance.superclass??) && entities[curr.inheritance.superclass.name]??) && curr_ids?size>1)>
        [result appendFormat:@", PRIMARY KEY (<#list curr_ids as id><#assign fieldNames = ContractUtils.getFieldsNames(id) /><#list fieldNames as fieldName>%@<#if (id_has_next || fieldName_has_next)> , </#if></#list></#list>)",
        <#list fieldNames as fieldName>${fieldName}<#if (id_has_next || fieldName_has_next)>, </#if></#list>];
    </#if>
    <#if (joinedInheritance)>
        [result appendFormat:@", FOREIGN KEY (%@) REFERENCES %@(%@) ON DELETE CASCADE",
        <#list (curr.relations) as relation>
        <#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
        <#assign fieldNames = ContractUtils.getFieldsNames(relation) />
        <#list fieldNames as fieldName>
        <#assign refId = relation.relation.field_ref[fieldName_index] />
            ${fieldName},
            ${ContractUtils.getContractTableName(entities[relation.relation.targetEntity])},
            ${ContractUtils.getFieldsNames(refId)[0]}];>
        </#list>
        </#if>
    </#list>"
    </#if>

    <#list curr_fields as field>
        <#if (field.unique?? && field.unique)>
        [result appendFormat:@", UNIQUE(%@)", <#list fieldNames as fieldName>%@</#list>];
        </#if>
    </#list>

    return result;
}

<#elseif curr.resource>

+ (NSString *) getSchemaColumns {
    NSMutableString *result = [NSMutableString new];
 <#list curr_fields as field>
    <#if (!field.columnResult && !field.id && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
        <#assign fieldNames = ContractUtils.getFieldsNames(field) />
        <#list fieldNames as fieldName>
    <#if (lastLine??)>${lastLine?replace("{COMMA}", ",")}</#if>
            <#if (field.relation?? && field.relation.field_ref?size > 1)>
                <#if field.nullable>
                    <#assign lastLine="\" %@" + field.relation.field_ref[fieldName_index].schema?replace("NOT NULL", "") + "{COMMA}\", " + fieldName + "];" />
                <#else>
                    <#assign lastLine="\" %@" + field.relation.field_ref[fieldName_index].schema + "{COMMA}\", " + fieldName + "];" />
                </#if>
            <#else>
                <#assign lastLine="[result appendFormat:@\" %@" + field.schema  + "{COMMA}\", " + fieldName + "];"/>
            </#if>
        </#list>
    </#if>
</#list>
    ${lastLine?replace("{COMMA}", "")}
    <#if (curr.inheritance??)><#list curr.inheritance.subclasses as subclass>[result appendString:[${subclass.name}SQLiteAdapter getSchemaColumns]]<#if subclass_has_next> + \n</#if></#list></#if>
    return result;
}

+ (NSString *) getSchemaConstraints {
    NSMutableString *result = [NSMutableString new];
    <#if (curr.relations??)>
    <#list (curr.relations) as relation>
        <#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
        <#assign fieldNames = ContractUtils.getFieldsNames(relation) />
        <#list fieldNames as fieldName>
        <#assign refId = relation.relation.field_ref[fieldName_index] />
        <#if (lastRelation??)>${lastRelation}</#if>
            <#assign lastRelation="[result appendFormat:@\",FOREIGN KEY(%@) REFERENCES %@ (%@)\",
            ${fieldName},
            ${ContractUtils.getContractTableName(entities[relation.relation.targetEntity])},
            ${ContractUtils.getFieldsNames(refId)[0]}];">
        </#list>
        </#if>
    </#list>
        <#if (lastRelation??)>${lastRelation}</#if>
    </#if>

    return result;
}

<#else>
+ (NSString *) getSchema {
    NSMutableString *result = [NSMutableString new];

    [result appendFormat:@"CREATE TABLE %@ (", ${ContractUtils.getContractTableName(curr)}];
 <#list curr_fields as field>
    <#if (!field.columnResult && (!field.relation?? || (field.relation.type!="OneToMany" && field.relation.type!="ManyToMany")))>
        <#assign fieldNames = ContractUtils.getFieldsNames(field) />
        <#list fieldNames as fieldName>
    <#if (lastLine??)>${lastLine?replace("{COMMA}", ",")}</#if>
            <#if (field.relation?? && field.relation.field_ref?size > 1)>
                <#if field.nullable>
                    <#assign lastLine="[result appendFormat:@\" %@" + field.relation.field_ref[fieldName_index].schema?replace("NOT NULL", "") + "{COMMA}\", " + fieldName + "];"/>
                <#else>
                    <#assign lastLine="[result appendFormat:@\" %@" + field.relation.field_ref[fieldName_index].schema + "{COMMA}\"," + fieldName + "];"/>
                </#if>
            <#else>
                <#assign lastLine="[result appendFormat:@\" %@" + field.schema + "{COMMA}\", " + fieldName + "];" />
            </#if>
        </#list>
    </#if>
</#list>
    ${lastLine?replace("{COMMA}", "")}<#if (singleTabInheritance && isTopMostSuperClass)>
    [result appendFormat:@",%@ ${curr.inheritance.discriminatorColumn.schema}", ${ContractUtils.getContractCol(curr.inheritance.discriminatorColumn)}];</#if>
        <#if (singleTabInheritance)>
            <#list curr.inheritance.subclasses as subclass>
    [result appendFormat:@", %@", [${subclass.name}SQLiteAdapter getSchemaColumns]];
            </#list>
        </#if>
        <#if (singleTabInheritance)>
            <#list curr.inheritance.subclasses as subclass>
                <#if (subclass.relations?size > 0)>
    [result appendFormat:@", %@", [${subclass.name}SQLiteAdapter getSchemaConstraints]];
                </#if>
            </#list>
        </#if>
<#if (curr.relations??)>
    <#list (curr.relations) as relation>
        <#if (relation.relation.type=="OneToOne" || relation.relation.type=="ManyToOne")>
        <#assign fieldNames = ContractUtils.getFieldsNames(relation) />
        <#list fieldNames as fieldName>
        <#assign refId = relation.relation.field_ref[fieldName_index] />
        <#if (relation.relation.resource)><#assign idCol = "[ResourceContract COL_ID]" /><#else><#assign idCol = ContractUtils.getFieldsNames(refId)[0] /></#if>
    <#if (lastRelation??)>${lastRelation}</#if>
            <#assign lastRelation="[result appendFormat:@\",FOREIGN KEY(%@) REFERENCES %@ (%@)" + "\",\n"
        + "     " + fieldName + ", " + ContractUtils.getContractTableName(entities[relation.relation.targetEntity]) + ", " + idCol + "];">
        </#list>
        </#if>
    </#list>
    <#if (lastRelation??)>${lastRelation}</#if>
</#if>
<#if (!((singleTabInheritance && curr.inheritance.superclass??) && entities[curr.inheritance.superclass.name]??) && curr_ids?size>1)>
    [result appendFormat:@", PRIMARY KEY (<#list curr_ids as id><#assign fieldNames = ContractUtils.getFieldsNames(id) /><#list fieldNames as fieldName>%@<#if (id_has_next || fieldName_has_next)> , </#if></#list></#list>)", <#list curr_ids as id><#assign fieldNames = ContractUtils.getFieldsNames(id) /><#list fieldNames as fieldName>${fieldName}<#if (id_has_next || fieldName_has_next)> , </#if></#list></#list>];
</#if>
<#if (joinedInheritance)>
    [result appendFormat:@", FOREIGN KEY (%@) REFERENCES %@(%@) ON DELETE CASCADE", ${ContractUtils.getContractCol(entities[curr.inheritance.superclass.name].ids[0])},
    ${ContractUtils.getContractTableName(curr.inheritance.superclass)}, ${ContractUtils.getContractCol(entities[curr.inheritance.superclass.name].ids[0])}];
</#if>
<#list curr_fields as field>
    <#if (field.unique?? && field.unique)>
    [result appendFormat:@", UNIQUE(%@)", ${ContractUtils.getContractCol(field)}];
    </#if>
</#list>
    [result appendString:@");"];

    return result;

}
</#if>

- (NSString *) getJoinedTableName {
    return ${curr.name}Contract.TABLE_NAME;
}

- (NSString *) getTableName {
    return ${curr.name}Contract.TABLE_NAME;
}

- (NSArray *) getCols {
    return ${ContractUtils.getContractCols(curr, true)};
}
<#if (!curr.internal)>
    <#if (curr_relations??)>
        <#list (curr_relations) as relation>
            <#if (relation.relation.type=="ManyToOne" || relation.relation.type=="OneToOne")>
- (Cursor *) getBy${relation.name?cap_first}:(int) ${relation.name?lower_case}Id
                withProjection:(NSArray *) projection
             withWhereClause:(NSString *) whereClause
               withWhereArgs:(NSArray *) whereArgs
                 withOrderBy:(NSString *) orderBy {
    Cursor *result = nil;

    NSString *idSelection = [NSString stringWithFormat:@"<#list ContractUtils.getFieldsNames(relation) as relName>%@ = ?<#if relName_has_next> AND </#if></#list>" ,<#list ContractUtils.getFieldsNames(relation) as relName>${relName}<#if relName_has_next>,</#if></#list>];

    NSMutableArray *idSelectionArgs = [NSMutableArray arrayWithObject:[NSNumber numberWithInt:${relation.name?lower_case}Id]];

    if (whereClause != nil && whereClause.length > 0) {
        whereClause = [NSString stringWithFormat:@"%@ AND %@", idSelection, whereClause];
        [idSelectionArgs addObjectsFromArray:whereArgs];
        whereArgs = idSelectionArgs;
    } else {
        whereClause = idSelection;
        whereArgs = idSelectionArgs;
    }

    result = [self query:projection
         withWhereClause:whereClause
           withWhereArgs:whereArgs
             withGroupBy:nil
              withHaving:nil
             withOrderBy:orderBy];

    return result;
}
            </#if>
        </#list>
    </#if>

- (NSArray *) cursorToItems:(Cursor *) cursor {
    return [${curr.name}Contract cursorToItems:cursor];
}

- (NSDictionary *) itemToContentValues:(id) item {
    return [${curr.name}Contract itemToContentValues:item];
}

- (${curr.name} *) getByID<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list> {
    ${curr.name} *result = [self query<#list curr_ids as id><#if id_index != 0>with${id.name?cap_first}</#if>:${id.name}<#if id_has_next> </#if></#list>];
 <#if (curr_ids?size>0)>
        <#list (curr_relations) as relation>
            <#if (!relation.internal)>
                <#if (relation.relation.type=="OneToMany")>

    ${relation.relation.targetEntity}SQLiteAdapter *${relation.name?uncap_first}Adapter = [${relation.relation.targetEntity}SQLiteAdapter new];

    Cursor *${relation.name?uncap_first}Cursor = [${relation.name?uncap_first}Adapter
            getBy${relation.relation.mappedBy?cap_first}:result.id
            withProjection:${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)}
           withWhereClause:nil
             withWhereArgs:nil
               withOrderBy:nil];

    [result set${relation.name?cap_first}:[self cursorToItems:${relation.name?uncap_first}Cursor]];

    [${relation.name?uncap_first}Cursor close];

                <#elseif relation.relation.type=="ManyToMany">
    ${relation.relation.joinTable?cap_first}SQLiteAdapter *${relation.relation.joinTable?uncap_first}Adapter = [${relation.relation.joinTable?cap_first}SQLiteAdapter new];

    Cursor *${relation.relation.joinTable?uncap_first}Cursor = [${relation.relation.joinTable?uncap_first}Adapter
            getBy${relation.relation.mappedBy?cap_first}:result.id
            withProjection:${curr.name?cap_first}.ALIASED_COLS
           withWhereClause:nil
             withWhereArgs:nil
               withOrderBy:nil];

    [result set${relation.name?cap_first}:[self cursorToItems:${relation.relation.joinTable?uncap_first}Cursor]];

    [${relation.relation.joinTable?uncap_first}Cursor close];

                <#else>

    if ([result ${relation.name?uncap_first}] != nil) {
        ${relation.relation.targetEntity}SQLiteAdapter *${relation.name?uncap_first}Adapter = [${relation.relation.targetEntity}SQLiteAdapter new];

        [result set${relation.name?cap_first}:[${relation.name?uncap_first}Adapter getByID<#list entities[relation.relation.targetEntity].ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:[[result ${relation.name?uncap_first}]${id.name}]</#list>]];
    }

                </#if>
            </#if>
        </#list>
    return result;
}

<#if (InheritanceUtils.isExtended(curr))>
- (Cursor *) getAllCursor {
    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?", ${curr.inheritance.superclass.name?cap_first}Contract.COL_DISCRIMINATORCOLUMN];

    NSArray *whereArgs = [NSArray arrayWithObject:${curr.name}Contract.DISCRIMINATOR_IDENTIFIER];

    return [self query:[self getCols]
       withWhereClause:whereClause
         withWhereArgs:whereArgs
           withGroupBy:nil
            withHaving:nil
           withOrderBy:nil];
}

</#if>
- (long long) insert:(${curr.name} *) item {
#ifdef DEBUG
    NSLog(@"Insert DB(%@)", ${curr.name}Contract.TABLE_NAME);
#endif

    NSMutableDictionary *values = [[${ContractUtils.getContractItemToContentValues(curr)}:item] mutableCopy];
    <#list curr_ids as id>
        <#if id.strategy == "IDENTITY">
            <#assign fieldNames = ContractUtils.getFieldsNames(id) />
            <#list fieldNames as fieldName>

    [values removeObjectForKey:${fieldName}];
            </#list>
        </#if>
    </#list>

    <#if (singleTabInheritance && !isTopMostSuperClass)>
    long long insertResult = [[self motherAdapter] insert:nil withValues:values];
    <#else>
    long long insertResult;

    if ([values count] != 0) {
        <#if !InheritanceUtils.isExtended(curr)>insertResult = </#if>[self insert:nil withValues:values];
    } else {
        <#if !InheritanceUtils.isExtended(curr)>insertResult = </#if>[self insert:${ContractUtils.getFieldsNames(curr_ids[0])[0]} withValues:values];
    }

    <#list curr_ids as id><#if id.strategy == "IDENTITY">
    [item setId:(int) insertResult];
    </#if></#list></#if>

    <#list (ViewUtils.getAllRelations(curr)) as relation>
        <#if (relation.relation.type=="ManyToMany")>
    if (item.${relation.name} != nil) {
        ${relation.relation.joinTable}SQLiteAdapter *${relation.name}Adapter = [${relation.relation.joinTable}SQLiteAdapter new];

        for (${relation.relation.targetEntity?cap_first} *i : item.${relation.name}) {
            [${relation.name?uncap_first}Adapter insert:insertResult with:i.id];
        }
    }
        </#if>
    </#list>

    return insertResult;
}

- (bool) insertOrUpdate:(${curr.name} *) item {
    int result = 0;

    if (([self getByID<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:item.${id.name}</#list>]) != nil) {
        // Item already exists => update it
        result = [self update:item];
    } else {
        // Item doesn't exist => create it
        long long id = [self insert:item];

        if (id != 0) {
            result = 1;
        }
    }

    return result;
    <#else>
    throw new NotImplementedException("An entity with no ID can't implement this method.");
    </#if>
}

- (bool) update:(${curr.name} *) item{
    <#if (singleTabInheritance && !isTopMostSuperClass)>bool result = false; </#if>
    <#if (curr_ids?size>0)>
#ifdef DEBUG
    NSLog(@"Update DB(%@)", ${ContractUtils.getContractTableName(curr)});
#endif

    <#if (singleTabInheritance && !isTopMostSuperClass)>
    NSMutableDictionary *values = [NSMutableDictionary dictionaryWithDictionary:
                                   [${curr.name?cap_first}Contract itemToContentValues:item]];

    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?<#list curr_ids as id> AND %@ = ?</#list>", <#list curr_ids as id>${curr.inheritance.superclass.name?cap_first}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>, ${curr.inheritance.superclass.name?cap_first}Contract.COL_DISCRIMINATORCOLUMN];

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}item.${id.name?uncap_first}]<#else>item.${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, ${curr.name}Contract.DISCRIMINATOR_IDENTIFIER, nil];

    [[self motherAdapter] update:values withWhereClause:whereClause withWhereArgs:whereArgs];

    if ([self update:values withWhereClause:whereClause withWhereArgs:whereArgs] == 1) {
        result = true;
    }

    return result;
        <#else>
    NSDictionary *values = [${curr.name?cap_first}Contract itemToContentValues:item];

    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.name?cap_first}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];

    NSArray *whereArgs = [NSArray <#if (curr_ids?size>1)>arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}item.${id.name?uncap_first}]<#else>item.${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, nil];<#else>arrayWithObject:@(<#list curr_ids as id>[item ${id.name?uncap_first}]<#if id_has_next>, </#if></#list>)];</#if>

    return [self update:values withWhereClause:whereClause withWhereArgs:whereArgs];
        </#if>
    </#if>
}

- (bool) removeBy${curr_ids[0].name?cap_first}<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list> {
    <#if (singleTabInheritance && !isTopMostSuperClass)>
    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?<#list curr_ids as id> AND %@ = ?</#list>", <#list curr_ids as id>${curr.inheritance.superclass.name?cap_first}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>, ${curr.inheritance.superclass.name?cap_first}Contract.COL_DISCRIMINATORCOLUMN];

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}${id.name?uncap_first}]<#else>${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, ${curr.name}Contract.DISCRIMINATOR_IDENTIFIER, nil];

    return [self delete:whereClause withWhereArgs:whereArgs];
    <#else>
    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.name}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}${id.name?uncap_first}]<#else>${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, nil];

    return [self delete:whereClause withWhereArgs:whereArgs];
    </#if>
}

- (bool) remove:(${curr.name} *) item{
    return [self removeBy${curr_ids[0].name?cap_first}<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:item.${id.name}</#list>];
}


- (${curr.name} *) query<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list> {
    ${curr.name} *result = nil;

<#if (singleTabInheritance && !isTopMostSuperClass)>
    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?<#list curr_ids as id> AND %@ = ?</#list>", <#list curr_ids as id>${curr.inheritance.superclass.name?cap_first}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>,</#if></#list>, ${curr.inheritance.superclass.name?cap_first}Contract.COL_DISCRIMINATORCOLUMN];

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}${id.name?uncap_first}]<#else>${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, ${curr.name}Contract.DISCRIMINATOR_IDENTIFIER, nil];

    Cursor *cursor = [self query:${curr.name}Contract.ALIASED_COLS
                 withWhereClause:whereClause
                   withWhereArgs:whereArgs
                     withGroupBy:nil
                      withHaving:nil
                     withOrderBy:nil];
<#else>
    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.name}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}${id.name?uncap_first}]<#else>${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, nil];

    Cursor *cursor = [self query:${curr.name}Contract.ALIASED_COLS
                 withWhereClause:whereClause
                   withWhereArgs:whereArgs
                     withGroupBy:nil
                      withHaving:nil
                     withOrderBy:nil];
</#if>

    NSArray *query = [self cursorToItems:cursor];

    [cursor close];

    if (query && query.count > 0) {
        result = query[0];
    }

    return result;
}

- (UpdateBatch*) getUpdateBatch:(${curr.name}*)item {
<#if (singleTabInheritance && !isTopMostSuperClass)>
    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.inheritance.superclass.name?cap_first}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];
<#else>
    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.name}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];
</#if>

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}item.${id.name?uncap_first}]<#else>item.${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, nil];

    return [super getUpdateBatch:item withWhereClause:whereClause withWhereArgs:whereArgs];
}

- (DeleteBatch*) getDeleteBatch:(${curr.name}*)item {
<#if (singleTabInheritance && !isTopMostSuperClass)>
    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.inheritance.superclass.name?cap_first}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];
<#else>
    NSString *whereClause = [NSString stringWithFormat:@"<#list curr_ids as id>%@ = ?<#if id_has_next> AND </#if></#list>", <#list curr_ids as id>${curr.name}Contract.ALIASED_COL_${id.name?upper_case}<#if id_has_next>, </#if></#list>];
</#if>

    NSArray *whereArgs = [NSArray arrayWithObjects:<#list curr_ids as id><#if id.harmony_type != "string">${FieldsUtils.generateFieldContentType("item", id)}item.${id.name?uncap_first}]<#else>item.${id.name?uncap_first}</#if><#if id_has_next>, </#if></#list>, nil];

    return [super getDeleteBatch:item withWhereClause:whereClause withWhereArgs:whereArgs];
}
<#else> // TODO != internal_curr
</#if>

<#if (curr.internal)>
    <#assign leftRelation = curr.relations[0] />
    <#if isRecursiveJoinTable>
        <#assign rightRelation = curr.relations[0] />
    <#else>
        <#assign rightRelation = curr.relations[1] />
    </#if>
    <#assign rightRelationFieldsNames = ContractUtils.getFieldsNames(rightRelation) />
    <#assign leftRelationFieldsNames = ContractUtils.getFieldsNames(leftRelation) />
-(long long) insert:<#list leftRelation.relation.field_ref as refField>(${FieldsUtils.getObjectiveType(refField)}) ${leftRelation.name?uncap_first}${refField.name?cap_first} with:</#list><#list rightRelation.relation.field_ref as refField>(${FieldsUtils.getObjectiveType(refField)}) ${rightRelation.name?uncap_first}${refField.name?cap_first}<#if refField_has_next>with:</#if></#list> {

        NSMutableDictionary *values = [NSMutableDictionary new];
        <#list leftRelation.relation.field_ref as refField>
        [values setValue:[NSString stringWithFormat:@"%d", ${leftRelation.name?uncap_first}${refField.name?cap_first}] forKey:${leftRelationFieldsNames[refField_index]}];
        </#list>
        <#list rightRelation.relation.field_ref as refField>
        [values setValue:[NSString stringWithFormat:@"%d", ${rightRelation.name?uncap_first}${refField.name?cap_first}] forKey:${rightRelationFieldsNames[refField_index]}];
        </#list>

        return [self insert:nil withValues:values];
    }

    <#list 1..2 as i>
- (Cursor *) getBy${leftRelation.name?cap_first}:<#list leftRelation.relation.field_ref as refField>(${FieldsUtils.getObjectiveType(refField)}) ${leftRelation.name?uncap_first}${refField.name?cap_first} with</#list>
            Projection:(NSArray *) projection
       withWhereClause:(NSString *) whereClause
         withWhereArgs:(NSArray *) whereArgs
           withOrderBy:(NSString *) orderBy {

        Cursor *result = nil;

        CriteriaExpression *crit = [[CriteriaExpression alloc] initWithType:AND];
        <#list leftRelation.relation.field_ref as refField>
        [crit addWithKey:${leftRelationFieldsNames[refField_index]} withValue:[NSString stringWithFormat:@"%d", ${leftRelation.name?uncap_first}${refField.name?cap_first}] withType:EQUALS];
        </#list>

        SelectValue *value = [SelectValue new];
        [value setRefKey:<#list rightRelation.relation.field_ref as refField>${rightRelationFieldsNames[refField_index]}<#if refField_has_next> + " || '::dirtyHack::' ||" + </#if></#list>];
        [value setRefTable:${ContractUtils.getContractTableName(curr)}];
        [value setCriteria:crit];

        CriteriaExpression *${rightRelation.relation.targetEntity?lower_case}Crit = [[CriteriaExpression alloc] initWithType:AND];
        Criterion *${rightRelation.relation.targetEntity?lower_case}SelectCrit = [Criterion new];
        [${rightRelation.relation.targetEntity?lower_case}SelectCrit setKey:<#list entities[rightRelation.relation.targetEntity].ids as id>${ContractUtils.getContractCol(id, true)}<#if id_has_next>+ " || '::dirtyHack::' || " + </#if></#list>];
        [${rightRelation.relation.targetEntity?lower_case}SelectCrit setType:IN];
        [${rightRelation.relation.targetEntity?lower_case}SelectCrit addValue:value];
        [${rightRelation.relation.targetEntity?lower_case}Crit add:${rightRelation.relation.targetEntity?lower_case}SelectCrit];

        if (whereClause != nil && whereClause.length > 0) {
            whereClause = [NSString stringWithFormat:@"%@ AND %@", whereClause, ${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelection];
            whereArgs = [whereArgs arrayByAddingObjectsFromArray:${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelectionArgs];
        } else {
            whereClause = ${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelection;
            whereArgs = ${rightRelation.relation.targetEntity?lower_case}Crit.toSQLiteSelectionArgs;
        }

        result = [self query:${ContractUtils.getContractTableName(entities[rightRelation.relation.targetEntity])}
              withProjection:projection
             withWhereClause:whereClause
               withWhereArgs:whereArgs
                 withGroupBy:nil
                  withHaving:nil
                 withOrderBy:orderBy);

        return result;
    }

    <#if isRecursiveJoinTable>
        <#assign leftRelation = curr.relations[0] />
    <#else>
        <#assign leftRelation = curr.relations[1] />
    </#if>
    <#assign rightRelation = curr.relations[0] />

    <#assign rightRelationFieldsNames = ContractUtils.getFieldsNames(rightRelation) />
    <#assign leftRelationFieldsNames = ContractUtils.getFieldsNames(leftRelation) />
    </#list>
</#if>

<#if sync>
- (${curr.name} *) getByServerID:(NSNumber *) serverId {
    ${curr.name} *result = nil;

    if (serverId != nil) {
        Cursor *cursor = [self query:self.getCols
                     withWhereClause:[NSString stringWithFormat:@"%@ = ?", <#if (InheritanceUtils.isExtended(curr))>${curr.inheritance.superclass.name?cap_first}<#else>${curr.name}</#if>Contract.ALIASED_COL_SERVERID]
                       withWhereArgs:[NSArray arrayWithObjects:serverId, nil]
                         withGroupBy:nil
                          withHaving:nil
                         withOrderBy:nil];

        NSArray *query = [self cursorToItems:cursor];
        [cursor close];

        if (query && query.count > 0) {
            result = query[0];
        }
    }

    return result;
}

- (void) completeEntityRelationsServerId:(${curr.name} *) item {
    <#if InheritanceUtils.isExtended(curr)>
    [self.motherAdapter completeEntityRelationsServerId:item];

    </#if>
    <#list (curr_relations) as relation>
        <#if !relation.internal>
            <#if relation.relation.type == "ManyToMany">
    //TODO ManyToMany
            <#elseif relation.relation.type == "OneToMany">
    ${relation.relation.targetEntity}SQLiteAdapter *${relation.name}Adapter = [${relation.relation.targetEntity}SQLiteAdapter new];

    Cursor *${relation.name}Cursor = [${relation.name}Adapter getBy${relation.relation.mappedBy?cap_first}:item.id
            withProjection:${ContractUtils.getContractCols(entities[relation.relation.targetEntity], true)}
         withWhereClause:nil
           withWhereArgs:nil
             withOrderBy:nil];

    NSArray *${relation.name} = [self cursorToItems:${relation.name}Cursor];

    [${relation.name}Cursor close];

    item.${relation.name?uncap_first} = ${relation.name};
            <#else>

    if (item.${relation.name?uncap_first} != nil) {
        ${relation.relation.targetEntity}SQLiteAdapter *${relation.name}Adapter = [${relation.relation.targetEntity}SQLiteAdapter new];

        item.${relation.name?uncap_first} = [${relation.name}Adapter getByID:item.${relation.name?uncap_first}.id];
    }
            </#if>
        </#if>
    </#list>
}

    <#if curr.inheritance?? && (curr.inheritance.subclasses?? && curr.inheritance.subclasses?size > 0)>
- (NSArray *) getAllForSync {
    NSArray *result;

    Cursor *cursor = [self query:self.getCols
                 withWhereClause:[NSString stringWithFormat:@"%@ IS NULL", ${curr.name}Contract.ALIASED_COL_DISCRIMINATORCOLUMN]
                   withWhereArgs:[NSArray arrayWithObjects:nil]
                     withGroupBy:nil
                      withHaving:nil
                     withOrderBy:nil];

    result = [self cursorToItems:cursor];

    return result;
}

    </#if>
</#if>
@end
