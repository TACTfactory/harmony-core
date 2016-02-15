<#include utilityPath + "all_imports.ftl" />
<#assign hasDate = MetadataUtils.hasDate(curr) />
<#assign hasTime = MetadataUtils.hasTime(curr) />
<#assign hasDateTime = MetadataUtils.hasDateTime(curr) />
<@header?interpret />
<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>

#import <Foundation/Foundation.h>
#import "Cursor.h"
#import "EntityResourceBase.h"

/** Resource contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
@interface ResourceContractBase : NSObject

/** id. */
+ (NSString *) COL_ID;

/** Alias. */
+ (NSString *) ALIASED_COL_ID;

/** path. */
+ (NSString *) COL_PATH;

/** Alias. */
+ (NSString *) ALIASED_COL_PATH;

<#if sync>
/** serverId. */
+ (NSString *) COL_SERVERID;

/** Alias. */
+ (NSString *) ALIASED_COL_SERVERID;

/** sync_dtag. */
+ (NSString *) COL_SYNC_DTAG;

/** Alias. */
+ (NSString *) ALIASED_COL_SYNC_DTAG;

/** sync_uDate. */
+ (NSString *) COL_SYNC_UDATE;

/** Alias. */
+ (NSString *) ALIASED_COL_SYNC_UDATE;

+ (NSString *) COL_UUID;

+ (NSString *) ALIASED_COL_UUID;

</#if>
+ (NSString *) COL_DISCRIMINATORCOLUMN;

+ (NSString *) ALIASED_COL_DISCRIMINATORCOLUMN;

/** Table name of SQLite database. */
+ (NSString*) TABLE_NAME;

/** Global Fields. */
+ (NSArray *) COLS;

/** Global Fields. */
+ (NSArray *) ALIASED_COLS;

+ (NSDictionary *) itemToContentValues:(EntityResourceBase *) item;

+ (EntityResourceBase *) cursorToItem:(Cursor *) cursor;

+ (void) cursorToItem:(Cursor *)cursor withResource:(EntityResourceBase *) result;

+ (NSArray *) cursorToItems:(Cursor *) cursor;

+ (EntityResourceBase *) cursorToItemLight:(Cursor *) cursor;

+ (void) cursorToItemLight:(Cursor *) cursor withItem:(EntityResourceBase *) result;

@end
