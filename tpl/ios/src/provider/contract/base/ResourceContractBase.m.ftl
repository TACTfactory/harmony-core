<#assign sync=false />
<#list entities?values as entity>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>
<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "ResourceContract.h"
#import "ResourceContractBase.h"
#import "DateUtils.h"

static NSString *TABLE_NAME;
static NSString *COL_ID;
static NSString *ALIASED_COL_ID;
static NSString *COL_PATH;
static NSString *ALIASED_COL_PATH;
<#if sync>
static NSString *COL_SERVERID;
static NSString *ALIASED_COL_SERVERID;
static NSString *COL_SYNC_DTAG;
static NSString *ALIASED_COL_SYNC_DTAG;
static NSString *COL_SYNC_UDATE;
static NSString *ALIASED_COL_SYNC_UDATE;
static NSString *COL_UUID;
static NSString *ALIASED_COL_UUID;
</#if>
static NSString *COL_DISCRIMINATORCOLUMN;
static NSString *ALIASED_COL_DISCRIMINATORCOLUMN;

/** ${project_name?cap_first} contract base.
 *
 * This class is regenerated. DO NOT MODIFY.
 */
@implementation ResourceContractBase

+ (void) initialize {
    TABLE_NAME = @"Resource";
    COL_ID = @"id";
    ALIASED_COL_ID = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_ID];
    COL_PATH =@"path";
    ALIASED_COL_PATH = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_PATH];
    <#if sync>
    COL_SERVERID = @"serverId";
    ALIASED_COL_SERVERID = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_SERVERID];
    COL_SYNC_DTAG = @"sync_dTag";
    ALIASED_COL_SYNC_DTAG = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_SYNC_DTAG];
    COL_SYNC_UDATE = @"sync_uDate";
    ALIASED_COL_SYNC_UDATE = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_SYNC_UDATE];
    COL_UUID = @"uuid";
    ALIASED_COL_UUID = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_UUID];
    </#if>
    COL_DISCRIMINATORCOLUMN = @"inheritance_type";
    ALIASED_COL_DISCRIMINATORCOLUMN = [NSString stringWithFormat:@"%@.%@", TABLE_NAME, COL_DISCRIMINATORCOLUMN];
}

/** id. */
+ (NSString *) COL_ID {
    return COL_ID;
}

/** Alias. */
+ (NSString *) ALIASED_COL_ID {
    return ALIASED_COL_ID;
}

/** path. */
+ (NSString *) COL_PATH {
    return COL_PATH;
}

/** Alias. */
+ (NSString *) ALIASED_COL_PATH {
    return ALIASED_COL_PATH;
}

<#if sync>
/** serverId. */
+ (NSString *) COL_SERVERID {
    return COL_SERVERID;
}

/** Alias. */
+ (NSString *) ALIASED_COL_SERVERID {
    return ALIASED_COL_SERVERID;
}

/** sync_dtag. */
+ (NSString *) COL_SYNC_DTAG {
    return COL_SYNC_DTAG;
}

/** Alias. */
+ (NSString *) ALIASED_COL_SYNC_DTAG {
    return ALIASED_COL_SYNC_DTAG;
}

/** sync_uDate. */
+ (NSString *) COL_SYNC_UDATE {
    return COL_SYNC_UDATE;
}

/** Alias. */
+ (NSString *) ALIASED_COL_SYNC_UDATE {
    return ALIASED_COL_SYNC_UDATE;
}

+ (NSString *) COL_UUID {
    return COL_UUID;
}

+ (NSString *) ALIASED_COL_UUID {
    return ALIASED_COL_UUID;
}
</#if>
+ (NSString *) COL_DISCRIMINATORCOLUMN {
    return COL_DISCRIMINATORCOLUMN;
}

+ (NSString *) ALIASED_COL_DISCRIMINATORCOLUMN {
    return ALIASED_COL_DISCRIMINATORCOLUMN;
}

/** Table name of SQLite database. */
+ (NSString*) TABLE_NAME {
    return TABLE_NAME;
}

/** Global Fields. */
+ (NSArray*) COLS{
    return [NSArray arrayWithObjects:
            ResourceContract.COL_ID,
            ResourceContract.COL_PATH,<#if sync>
            ResourceContract.COL_SERVERID,
            ResourceContract.COL_SYNC_DTAG,
            ResourceContract.COL_SYNC_UDATE,
            ResourceContract.COL_UUID,</#if>
            nil];
}

/** Global Fields. */
+ (NSArray*) ALIASED_COLS {
    return [NSArray arrayWithObjects:
            ResourceContract.ALIASED_COL_ID,
            ResourceContract.ALIASED_COL_PATH,<#if sync>
            ResourceContract.ALIASED_COL_SERVERID,
            ResourceContract.ALIASED_COL_SYNC_DTAG,
            ResourceContract.ALIASED_COL_SYNC_UDATE,
            ResourceContract.ALIASED_COL_UUID,</#if>
            nil];
}

+ (NSDictionary *) itemToContentValues:(EntityResourceBase *) item {
    NSMutableDictionary *result = [NSMutableDictionary dictionary];
    [result setObject:[NSNumber numberWithInt:item.id] forKey:ResourceContract.COL_ID];

    if (item.path != nil) {
        [result setObject:item.path forKey:ResourceContract.COL_PATH];
    }
    <#if sync>

    if (item.serverId != nil) {
        [result setObject:item.serverId forKey:ResourceContract.COL_SERVERID];
    } else {
        [result setObject:[NSNull null] forKey:ResourceContract.COL_SERVERID];
    }

    [result setObject:[NSNumber numberWithBool:item.sync_dTag] forKey:ResourceContract.COL_SYNC_DTAG];

    if (item.sync_uDate != nil) {
        [result setObject:[DateUtils dateToISOString:item.sync_uDate] forKey:ResourceContract.COL_SYNC_UDATE];
    } else {
        [result setObject:[NSNull null] forKey:ResourceContract.COL_SYNC_UDATE];
    }

    if (item.uuid != nil) {
        [result setObject:item.uuid forKey:ResourceContract.COL_UUID];
    } else {
        [result setObject:[NSNull null] forKey:ResourceContract.COL_UUID];
    }
    </#if>

    return result;
}

+ (EntityResourceBase *) cursorToItem:(Cursor *) cursor {
    EntityResourceBase *result = [EntityResourceBase new];
    [self cursorToItem:cursor withResource:result];

    return result;
}

+ (void) cursorToItem:(Cursor *)cursor withResource:(EntityResourceBase *) result {
    int index;

    index = [cursor columnIndexForName:ResourceContract.COL_ID];
    [result setId:[cursor intForColumnIndex:index]];

    index = [cursor columnIndexForName:ResourceContract.COL_PATH];
    [result setPath:[cursor stringForColumnIndex:index]];
    <#if sync>

    index = [cursor columnIndexForName:ResourceContract.COL_SERVERID];
    if (![cursor columnIndexIsNull:index]) {
        [result setServerId:[NSNumber numberWithInt:[cursor intForColumnIndex:index]]];
    }

    index = [cursor columnIndexForName:ResourceContract.COL_SYNC_DTAG];
    [result setSync_dtag:[cursor intForColumnIndex:index] == 1];

    index = [cursor columnIndexForName:ResourceContract.COL_SYNC_UDATE];

    if (![cursor columnIndexIsNull:index]) {
        NSDate *dtSync_uDate = [DateUtils isoStringToDate:[cursor stringForColumnIndex:index]];
        if (dtSync_uDate != nil) {
            [result setSync_uDate:dtSync_uDate];
        } else {
            [result setSync_uDate:[NSDate date]];
        }
    }

    index = [cursor columnIndexForName:ResourceContract.COL_UUID];
    if (![cursor columnIndexIsNull:index]) {
        [result setUuid:[cursor stringForColumnIndex:index]];
    }
    </#if>
}

+ (NSArray *) cursorToItems:(Cursor *) cursor {
    NSMutableArray *result = [NSMutableArray array];
    EntityResourceBase *item;

    while ([cursor next]) {
        item = [ResourceContract cursorToItem:cursor];
        [result addObject:item];
    }

    return result;
}

+ (EntityResourceBase *) cursorToItemLight:(Cursor *) cursor {
        EntityResourceBase *result = [EntityResourceBase new];

        [ResourceContract cursorToItemLight:cursor withItem:result];

        return result;
}

+ (void) cursorToItemLight:(Cursor *) cursor withItem:(EntityResourceBase *) result {
    if ([cursor next]) {
       int index = [cursor columnIndexForName:ResourceContract.COL_PATH];
        [result setPath:[cursor stringForColumnIndex:index]];
     }
}

@end


