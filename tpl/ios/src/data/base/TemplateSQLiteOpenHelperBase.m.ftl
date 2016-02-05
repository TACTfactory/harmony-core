<#include utilityPath + "all_imports.ftl" />
<#function callLoader entity>
    <#assign ret="//Load "+entity.name+" fixtures\r        " />
    <#assign ret=ret+entity.name?cap_first+"DataLoader "+entity.name?uncap_first+"Loader = new "+entity.name?cap_first+"DataLoader(this.ctx);\r        " />
    <#assign ret=ret+entity.name?uncap_first+"Loader.getModelFixtures("+entity.name?cap_first+"DataLoader.MODE_BASE);\r        " />
    <#assign ret=ret+entity.name?uncap_first+"Loader.load(manager);\r\r" />
    <#return ret />
</#function>
<@header?interpret />

<#if dataLoader>#import "${project_name?cap_first}SQLiteOpenHelper.h"</#if>
<#list entities?values as entity>
    <#if (entity.fields?size>0 || entity.inheritance?? || entity.inheritance??)>
#import "${entity.name?cap_first}SQLiteAdapter.h"
#import "${entity.name?cap_first}Contract.h"
    </#if>
</#list>
<#if dataLoader>
#import "DataLoader.h"
</#if>
#import "FMDatabaseAdditions.h"

@implementation ${project_name?cap_first}SQLiteOpenHelperBase

@synthesize fmDatabaseQueue;

+ (instancetype) get${project_name?cap_first}SQLiteOpenHelperBase {
    static dispatch_once_t pred;
    static id shared = nil;
    dispatch_once(&pred, ^{
        shared = [[super alloc] initWithName:DB_NAME andVersion:DB_VERSION];
    });

    return shared;
}

NSString *const DB_NAME = @"database.sqlite";
int const DB_VERSION = 1;
static NSObject *lock;

+ (void) initialize {
    lock = [NSObject new];
}

- (id) initWithName:(NSString *) name andVersion:(int) version {
    self = [super init];

    if (self) {
        NSString *docsPath = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES)[0];
        DB_PATH = [docsPath stringByAppendingPathComponent:DB_NAME];

        //If we load an existing database with a file, we don't need to create the schema.
        self->databaseExistInAssets = [[NSBundle mainBundle] pathForResource:@"database" ofType:@"sqlite"] != nil;

        //[self getDatabase];
    }

    return self;
}

- (void) clearDataBase:(FMDatabase *) db {
    if ([db open]) {
        <#list entities?values as entity>
            <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??))>
        [db executeUpdate:[NSString stringWithFormat:@"DELETE FROM %@", ${entity.name?cap_first}Contract.TABLE_NAME]];
            </#if>
        </#list>
        [db close];
    }
}

- (void) onCreate:(FMDatabase *) db{
    if (!self->databaseExistInAssets) {
        if ([db open]) {
            //[db setUserVersion:DB_VERSION];
            // Create Schema
    <#list entities?values as entity>
        <#if ((entity.fields?size>0 || InheritanceUtils.isExtended(entity)) && !(entity.inheritance?? && entity.inheritance.inheritanceType?? && entity.inheritance.inheritanceType == "SingleTable" && InheritanceUtils.isExtended(entity)))>
            [db executeUpdate:[${entity.name}SQLiteAdapter getSchema]];
        </#if>
    </#list>

            [db executeUpdate:@"PRAGMA foreign_keys = ON;"];
            [db close];
        }

        if (!isUnitTest) {
            [self loadData:db];
        }

    } else {
        [self copyDataBase];
    }
}

- (void) onUpgrade:(FMDatabase *) db andOldVersion:(int) oldVersion andNewVersion:(int) newVersion {
}

- (void) onDowngrade:(FMDatabase *) db andOldVersion:(int) oldVersion andNewVersion:(int) newVersion {
    if (oldVersion == 0) {
        [NSException raise:@"Invalid version value" format:@"Version is invalid"];
    }
}

- (void) loadData:(FMDatabase *) db {
<#if dataLoader>
    DataLoader *dataLoader = [DataLoader new];
    [dataLoader clean];

    [dataLoader loadData:MODE_APP];
#if DEBUG
    [dataLoader loadData:MODE_DEBUG];
#endif
</#if>
}

- (void) createDataBase {
    if (self->databaseExistInAssets && ![self checkDataBase]) {
        [self copyDataBase];
    }
}

- (bool) checkDataBase {
    return [[NSFileManager defaultManager] fileExistsAtPath:DB_PATH];
}

- (void) copyDataBase{
    //Using NSFileManager we can perform many file system operations.
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;

    bool success = [fileManager fileExistsAtPath:DB_PATH];

    if (!success) {
        NSString *defaultDBPath = [[NSBundle mainBundle] pathForResource:@"database" ofType:@"sqlite"];
        success = [fileManager copyItemAtPath:defaultDBPath toPath:DB_PATH error:&error];

        if (!success)
            NSAssert1(0, @"Failed to create writable database file with message '%@'.", [error localizedDescription]);
    }
}

- (FMDatabase *) getDatabase {
    bool exist = [self checkDataBase];

    // Create or open Database
    FMDatabase *db = [FMDatabase databaseWithPath:DB_PATH];

    if (!exist){
        [self onCreate:db];
    } else {
        [db open];

        int version = [db userVersion];

        if (version != DB_VERSION) {
            if (version > DB_VERSION) {
                [self onDowngrade:db andOldVersion:version andNewVersion:DB_VERSION];
            } else {
                [self onUpgrade:db andOldVersion:version andNewVersion:DB_VERSION];
            }

            [db setUserVersion:DB_VERSION];
        }

        [db close];
    }

    return db;
}

- (FMDatabaseQueue *) getQueue {
    return [FMDatabaseQueue databaseQueueWithPath:DB_PATH];
}

@end