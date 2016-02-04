<#include utilityPath + "all_imports.ftl" />
<#assign fields = ViewUtils.getAllFields(curr)?values + curr_fields />
<#assign relations = ViewUtils.getAllRelations(curr) />
<#assign relation_array = [] />
<#assign hasRelations = false />
<#assign hasInternalFields = false />
<#assign inherited = false />
<#list relations as relation><#if (relation.internal)><#assign hasInternalFields = true /></#if></#list>
<@header?interpret />

#import "ProviderUtils.h"
#import "${curr.name?cap_first}.h"
#import "${curr.name?cap_first}SQLiteAdapter.h"

/**
 * ${curr.name?cap_first} Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
@interface ${curr.name?cap_first}ProviderUtilsBase : ProviderUtils {
    @protected
    ${curr.name?cap_first}SQLiteAdapter *adapter;
}

/**
 * Constructor.
 */
-(id) init;

/**
 * Query the DB.
 * @param item ${curr.name?cap_first}
 * @return ${curr.name?cap_first}
 */
- (${curr.name?cap_first} *)query:(${curr.name?cap_first} *) item;

/**
 * Query the DB with id.
 * @param int Id of ${curr.name?cap_first}
 * @return ${curr.name?cap_first}
 */
-(${curr.name?cap_first} *)queryWithId<#list curr_ids as id><#if id_index != 0> with${id.name?cap_first}</#if>:(${FieldsUtils.convertToObjectiveType(id)}<#if !id.primitive>*</#if>) ${id.name}</#list>;

/**
 * Query the DB to get the entities filtered by criteria.
 * @param expression The criteria expression defining the selection and selection args
 * @return NSArray<${curr.name?cap_first}>
 */
- (NSArray *) queryWithExpression:(CriteriaExpression *) expression;

/**
 * Query the DB and get all ${curr.name?cap_first}.
 *
 * @return NSArray<${curr.name?cap_first}>
 */
- (NSArray *) queryAll;
<#list relations as relation>
<#if (!relation.internal)>

<#if (relation.relation.type == "ManyToOne" || relation.relation.type == "OneToOne")>
/**
 * Get associate ${relation.name?cap_first}.
 * @param the ${curr.name}
 * @return ${relation.name?cap_first}
 */
- (${relation.relation.targetEntity?cap_first} *) getAssociate${relation.name?cap_first}:(${curr.name} *) item;
<#elseif (relation.relation.type == "OneToMany")>
/**
 *Get associates ${relation.name?cap_first}.
 * @param item ${curr.name}
 * @return NSArray
 */
- (NSArray *) getAssociate${relation.name?cap_first}:(${curr.name} *) item;</#if>

</#if>
</#list>

/**
 * Send the item of type ${curr.name?cap_first} to the content provider of the application.
 * @param item The ${curr.name?cap_first}
 * @return a long long
 */
- (long long) insert:(${curr.name?cap_first} *) item;

/**
 * Delete from DB.
 * @param item ${curr.name?cap_first}
 * @return number of row affected
 */
- (int) delete:(${curr.name?cap_first} *) item;

/**
 * Updates the DB.
 * @param item ${curr.name?cap_first}
 * @return number of rows updated
 */
- (int) update:(${curr.name?cap_first} *) item;

@end
