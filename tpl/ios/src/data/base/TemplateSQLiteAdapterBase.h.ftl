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


#import "SQLiteAdapter.h"
<#if sync>#import "SyncSQLiteAdapterBase.h"</#if>
${ImportUtils.importRelatedEntities(curr, false)}
<#if (InheritanceUtils.isExtended(curr))>
#import "${curr.inheritance.superclass.name}SQLiteAdapter.h"
</#if>

/** ${curr.name} adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ${curr.name}Adapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
@interface ${curr.name}SQLiteAdapterBase : <#if sync>SyncSQLiteAdapterBase<#else>SQLiteAdapter</#if>

<#if (InheritanceUtils.isExtended(curr))>
    /** Mother Adapter. */
    @property (nonatomic) ${curr.inheritance.superclass.name}SQLiteAdapter *motherAdapter;
</#if>

/**
 * Get the joined table name used in DB for your ${curr.name} entity
 * and its parents.
 * @return A String showing the joined table name
 */
- (NSString *) getJoinedTableName;

<#if (singleTabInheritance && !isTopMostSuperClass) || (curr.resource)>
/**
 * Get the columns Schema.
 * @return NSString of schema columns
 */
+ (NSString *) getSchemaColumns;

/**
 * Get the constraints Schema.
 * @return NSString of schema constraints
 */
+ (NSString *) getSchemaConstraints;

<#else>
/**
 * Generate Entity Table Schema.
 * @return "SQL query : CREATE TABLE..."
 */
+ (NSString *) getSchema;
</#if>

    <#if (curr_relations??)>
        <#list (curr_relations) as relation>
            <#if (relation.relation.type=="ManyToOne" || relation.relation.type=="OneToOne")>

/**
 * Find & read ${curr.name} by ${relation.name?lower_case}.
 * @param ${relation.name?lower_case}Id
 * @param orderBy Order by string (can be null)
 * @return List of ${curr.name} entities
 */
- (Cursor *) getBy${relation.name?cap_first}:(int) ${relation.name?lower_case}Id
              withProjection:(NSArray *) projection
             withWhereClause:(NSString *) whereClause
               withWhereArgs:(NSArray *) whereArgs
                 withOrderBy:(NSString *) orderBy;
            </#if>

        </#list>
    </#if>

<#if !curr.internal>//// CRUD Entity ////
/**
 * Find & read ${curr.name} by id in database.
 *
 * @param id Identify of ${curr.name}
 * @return ${curr.name} entity
 */
- (${curr.name} *) getByID<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list>;

/**
 * Insert a ${curr.name} entity into database.
 *
 * @param item The ${curr.name} entity to persist
 * @return Id of the ${curr.name} entity
 */
- (long long) insert:(${curr.name} *) item;

/**
 * Either insert or update a ${curr.name} entity into database whether.
 * it already exists or not.
 *
 * @param item The ${curr.name} entity to persist
 * @return 1 if everything went well, 0 otherwise
 */
- (bool) insertOrUpdate:(${curr.name} *) item;

/**
 * Update a ${curr.name} entity into database.
 *
 * @param item The ${curr.name} entity to persist
 * @return count of updated entities
 */
- (bool) update:(${curr.name} *) item;

/**
 * Delete a ${curr.name} entity of database.
 *
 * @param id id
 * @return count of updated entities
 */
- (bool) removeBy${curr_ids[0].name?cap_first}<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list>;

/**
 * Deletes the given entity.
 * @param ${curr.name} The entity to delete
 * @return count of updated entities
 */
- (bool) remove:(${curr.name} *) item;

/**
 * Query the DB to find a Account entity.
 *
 * @param id The id of the entity to get from the DB
 *
 * @return The cursor pointing to the query's result
 */
- (${curr.name} *) query<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list>;
</#if>
@end
