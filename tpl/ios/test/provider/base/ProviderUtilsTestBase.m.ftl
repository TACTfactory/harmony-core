<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "ProviderUtilsTestBase.h"
#import "${project_name?cap_first}SQLiteOpenHelper.h"
#import "AppDelegate.h"
<#if dataLoader?? && dataLoader>
#import "DataLoader.h"
</#if>

@implementation ProviderUtilsTestBase
- (void)setUp {
    [super setUp];
    [self initDatabase];
}

- (void)tearDown {
    [super tearDown];
}

- (void)initDatabase {
    bool success;
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;
    NSString *cacheDir = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES)[0];
    NSString *dbPath = [NSString stringWithFormat:@"%@%@%@", cacheDir, DB_NAME, @".test"];
    NSString *backupDBPath = [NSString stringWithFormat:@"%@%@", cacheDir, DB_NAME];

    success = [fileManager fileExistsAtPath:dbPath];
    if (!success || ![DataLoader hasFixturesBeenLoaded]) {
        BusyPandaSQLiteOpenHelper* helper = [BusyPandaSQLiteOpenHelper getBusyPandaSQLiteOpenHelperBase];

        FMDatabase* db = [helper getDatabase];
        [helper clearDataBase:db];

        DataLoader *dataLoader = [DataLoader new];
        [dataLoader clean];
        [dataLoader loadData];

        // The backup database does not exist, so copy the default to the appropriate location.
        success = [fileManager copyItemAtPath:dbPath
                                       toPath:backupDBPath
                                        error:&error];
    } else {
        [fileManager removeItemAtPath:dbPath
                                error:&error];

        success = [fileManager copyItemAtPath:backupDBPath
                                       toPath:dbPath
                                        error:&error];

    }
}

- (void)testCreate {
    //TODO Test Code
}

- (void)testRead {
    //TODO Test Code
}

- (void) testUpdate {
    //TODO Test Code
}

- (void) testDelete {
    //TODO Test Code
}

- (void) testAll {
    //TODO Test Code
}

@end