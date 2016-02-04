<#include utilityPath + "all_imports.ftl" />
<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>

<@header?interpret />

#import "EntityResourceBase.h"
#import "SQLiteAdapter.h"
<#if sync>#import "SyncSQLiteAdapterBase.h"</#if>

/** Resource adapter database abstract class. <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project<br/>
 * with Harmony.<br />
 * You should edit ResourceAdapter class instead of this<br/>
 * one or you will lose all your modifications.</i></b>
 */
@interface ResourceSQLiteAdapterBase : <#if sync>SyncSQLiteAdapterBase<#else>SQLiteAdapter</#if>

- (NSString *) getJoinedTableName;

- (NSString *) getTableName;

- (NSArray *) getCols;

+ (NSString*) getSchema;

- (NSDictionary *) itemToContentValues:(id) item;

- (NSArray *) cursorToItems:(Cursor *) cursor;

+ (EntityResourceBase *) cursorToItem:(Cursor *) cursor;

+ (void) cursorToItem:(Cursor*) cursor withResource:(EntityResourceBase *) result;

- (EntityResourceBase *) getByID:(int) id;

- (NSArray *) getAll;

- (long long) insert:(EntityResourceBase *) item;

- (bool) insertOrUpdate:(EntityResourceBase *) item;

- (bool) update:(EntityResourceBase *)item;

- (bool) removeById:(int) id;

- (bool) remove:(EntityResourceBase *) item;

- (EntityResourceBase *) query:(int) id;
<#if sync>

- (void) completeEntityRelationsServerId:(EntityResourceBase *)item;

- (NSArray *) getAllForSync;
</#if>

@end
