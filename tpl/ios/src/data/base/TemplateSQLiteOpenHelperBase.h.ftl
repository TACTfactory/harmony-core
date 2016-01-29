<#include utilityPath + "all_imports.ftl" />
<#function callLoader entity>
    <#assign ret="//Load "+entity.name+" fixtures\r        " />
    <#assign ret=ret+entity.name?cap_first+"DataLoader "+entity.name?uncap_first+"Loader = new "+entity.name?cap_first+"DataLoader(this.ctx);\r        " />
    <#assign ret=ret+entity.name?uncap_first+"Loader.getModelFixtures("+entity.name?cap_first+"DataLoader.MODE_BASE);\r        " />
    <#assign ret=ret+entity.name?uncap_first+"Loader.load(manager);\r\r" />
    <#return ret />
</#function>
<@header?interpret />

#import <Foundation/Foundation.h>
#import "FMDatabaseQueue.h"

/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 * @see android.database.sqlite.SQLiteOpenHelper
 */
@interface ${project_name?cap_first}SQLiteOpenHelperBase : NSObject {
    @public
    bool isUnitTest;

    @private
    bool databaseExistInAssets;
    NSString* DB_PATH;
}

@property (strong) FMDatabaseQueue *fmDatabaseQueue;

/** Database name. */
extern NSString* const DB_NAME;
/** Database version. */
extern int const DB_VERSION;

/**
 * Get the ${project_name?cap_first}SQLiteOpenHelperBase.
 * return ${project_name?cap_first}SQLiteOpenHelperBase
 */
+ (instancetype) get${project_name?cap_first}SQLiteOpenHelperBase;

/**
 * Init the ${project_name?cap_first}SQLiteOpenHelperBase.
 * @param name The Database name
 * @param version The Database version
 * @return the ${project_name?cap_first}SQLiteOpenHelperBase
 */

- (id) initWithName:(NSString *) name andVersion:(int) version;

/**
 * Clear the database given in parameters.
 * @param db The database to clear
 */
- (void) clearDataBase:(FMDatabase *) db;

/**
 * On create of the database.
 * @param db The FMDatabase
 */
- (void) onCreate:(FMDatabase *) db;

/**
 * On upgrade of the database.
 * @param db The FMDatabase
 * @param oldVersion Old version of the db
 * @param newVersion New version of the db
 */
- (void) onUpgrade:(FMDatabase *) db andOldVersion:(int) oldVersion andNewVersion:(int) newVersion;
/**
 * Loads data from the fixture files.
 * @param db The database to populate with fixtures
 */
- (void) loadData:(FMDatabase *) db;

/**
 * Create the database.
 */
- (void) createDataBase;

/**
 * Check if the database already exist to avoid re-copying the file each
 * time you open the application.
 *
 * @return true if it exists, false if it doesn't
 */
- (bool) checkDataBase;

/**
 * Copies your database from your local assets-folder to the just created
 * empty database in the system folder, from where it can be accessed and
 * handled. This is done by transfering bytestream.
 * @throws IOException if error has occured while copying files
 * */
- (void) copyDataBase;

/**
 * Get the FMDatabase.
 * @return FMDatabase
 */
- (FMDatabase *) getDatabase;

/**
 * Get the FMDatabaseQueue.
 * @return FMDatabaseQueue
 */
- (FMDatabaseQueue *) getQueue;

@end
