
<@header?interpret />

#import "${project_name?cap_first}SQLiteOpenHelperBase.h"


/**
 * This class makes it easy for ContentProvider implementations to defer <br />
 * opening and upgrading the database until first use, to avoid blocking <br />
 * application startup with long-running database upgrades.
 */
@interface ${project_name?cap_first}SQLiteOpenHelper : ${project_name?cap_first}SQLiteOpenHelperBase

@end
