<#include utilityPath + "all_imports.ftl" />
<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>

<@header?interpret />

#import "ResourceSQLiteAdapterBase.h"
#import "ResourceContract.h"
#import "CriteriaExpression.h"
<#list entities?values as entity>
    <#if entity.resource>
#import "${entity.name?cap_first}SQLiteAdapter.h"
    </#if>
</#list>

/** Resource adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ResourceAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
@implementation ResourceSQLiteAdapterBase

- (NSString *) getJoinedTableName {
    return ResourceContract.TABLE_NAME;
}

- (NSString *) getTableName {
    return ResourceContract.TABLE_NAME;
}

- (NSArray *) getCols {
    return ResourceContract.ALIASED_COLS;
}

+ (NSString*) getSchema {
    return [NSString stringWithFormat:@"CREATE TABLE %@ ("
         " %@ INTEGER PRIMARY KEY AUTOINCREMENT,"
         " %@ VARCHAR NOT NULL,"
         <#if sync>
         " %@ integer,"
         " %@ boolean NOT NULL,"
         " %@ datetime"
         ",%@ VARCHAR,"
         </#if>
         " %@ VARCHAR"
        <#list entities?values as entity>
            <#if ((entity.resource) && (entity.fields?size>1))>
         + ", %@"
            </#if>
        </#list>
         ");",

        ResourceContract.TABLE_NAME,
        ResourceContract.COL_ID,
        ResourceContract.COL_PATH,
        <#if sync>
        ResourceContract.COL_SERVERID,
        ResourceContract.COL_SYNC_DTAG,
        ResourceContract.COL_SYNC_UDATE,
        ResourceContract.COL_UUID,
        </#if>
        ResourceContract.COL_DISCRIMINATORCOLUMN
        <#list entities?values as entity>
            <#if ((entity.resource) && (entity.fields?size>1))>
         + "," [${entity.name?cap_first}SQLiteAdapter getSchemaColumns]
            </#if>
        </#list>
    ];
}

- (NSDictionary *) itemToContentValues:(id) item {
    return [ResourceContract itemToContentValues:item];
}

- (NSArray *) cursorToItems:(Cursor *) cursor {
    return [ResourceContract cursorToItems:cursor];
}

+ (EntityResourceBase *) cursorToItem:(Cursor *) cursor {
    return [ResourceContract cursorToItem:cursor];
}

+ (void) cursorToItem:(Cursor*) cursor withResource:(EntityResourceBase *) result {
    return [ResourceContract cursorToItem:cursor withResource:result];
}

- (EntityResourceBase *) getByID:(int) id {
    EntityResourceBase *result = [self query:id];

    return result;
}

- (NSArray *) getAll {
    NSArray *result;

    Cursor *cursor = [self query:self.getCols
                 withWhereClause:nil
                   withWhereArgs:nil
                     withGroupBy:nil
                      withHaving:nil
                     withOrderBy:nil];

    result = [self cursorToItems:cursor];

    return result;
}

- (long long) insert:(EntityResourceBase *) item {
#ifdef DEBUG
    NSLog(@"Insert DB(%@)", ResourceContract.TABLE_NAME);
#endif

    NSMutableDictionary* values = [[ResourceContract itemToContentValues:item] mutableCopy];

    [values removeObjectForKey:ResourceContract.COL_ID];

    int insertResult;

    if ([values count] != 0) {
        insertResult = (int) [self insert:nil withValues:values];
    } else {
        insertResult = (int) [self insert:ResourceContract.COL_ID withValues:values];
    }

    [item setId:insertResult];

    return insertResult;
}

- (bool) insertOrUpdate:(EntityResourceBase *) item {
    int result = 0;
    if (([self getByID :item.id]) != nil) {
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
}

- (bool) update:(EntityResourceBase *)item{

#ifdef DEBUG
    NSLog(@"Update DB(%@)", ResourceContract.TABLE_NAME);
#endif

    NSDictionary *values = [ResourceContract itemToContentValues:item];

    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?", ResourceContract.ALIASED_COL_ID];

    NSArray *whereArgs = [NSArray arrayWithObject:@([item id])];

    return [self update:values withWhereClause:whereClause withWhereArgs:whereArgs];
}

- (bool) removeById:(int) id {
#ifdef DEBUG
    //NSLog(@"Delete DB(%@) id : %d", ResourceContract.TABLE_NAME, id);
#endif
    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?", ResourceContract.ALIASED_COL_ID];

    NSArray *whereArgs = [NSArray arrayWithObjects:[NSNumber numberWithInt:id], nil];

    return [self delete:whereClause withWhereArgs:whereArgs];
}

- (bool) remove:(EntityResourceBase *) item{
    return [self removeById :item.id];
}

- (EntityResourceBase *) query:(int) id {
    EntityResourceBase *result = nil;
    NSString *whereClause = [NSString stringWithFormat:@"%@ = ?", ResourceContract.ALIASED_COL_ID];

    NSArray *whereArgs = [NSArray arrayWithObjects:[NSNumber numberWithInt:id], nil];

    Cursor *cursor = [self query:ResourceContract.ALIASED_COLS
                 withWhereClause:whereClause
                   withWhereArgs:whereArgs
                     withGroupBy:nil
                      withHaving:nil
                     withOrderBy:nil];

    NSArray *query = [self cursorToItems:cursor];

    [cursor close];

    if (query && query.count > 0) {
        result = query[0];
    }

    return result;
}
<#if sync>

- (void) completeEntityRelationsServerId:(EntityResourceBase *) item {
}

- (NSArray *) getAllForSync {
    NSArray *result;

    CriteriaExpression *criteriaExpression = [[CriteriaExpression alloc] initWithType:AND];
    [criteriaExpression addWithKey:ResourceContract.ALIASED_COL_DISCRIMINATORCOLUMN withValue:nil withType:IS_NULL];

    Cursor *cursor = [self query:self.getCols
                 withWhereClause:criteriaExpression.toSQLiteSelection
                   withWhereArgs:criteriaExpression.toSQLiteSelectionArgs
                     withGroupBy:nil
                      withHaving:nil
                     withOrderBy:nil];

    result = [self cursorToItems:cursor];

    return result;
}
</#if>

@end
