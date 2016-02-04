<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "TestDBBase.h"
#import "${curr.name}SQLiteAdapter.h"

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
@interface ${curr.name}TestDBBase : TestDBBase

@property ${curr.name?cap_first}SQLiteAdapter* sqlAdapter;
@property ${curr.name?cap_first}* entity;
@property NSArray* entities;
@property int nbEntities;

/** Test case Create Entity */
- (void)testCreate;
/** Test case Read Entity */
- (void)testRead;
/** Test case Update Entity */
- (void) testUpdate;
/** Test case Delete Entity */
- (void) testDelete;
/** Test the get all method. */
- (void) testAll;

@end