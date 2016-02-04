<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "${curr.name}TestDBBase.h"
#import "${curr.name}TestUtils.h"

<#if dataLoader?? && dataLoader>
    <#list InheritanceUtils.getAllChildren(curr) as child>
//#import "${child.name?cap_first}DataLoader.h"
    </#list>
</#if>


@implementation ${curr.name}TestDBBase

- (void)setUp {
    [super setUp];

    self.sqlAdapter = [${curr.name?cap_first}SQLiteAdapter new];
    self.entity = [${curr.name?cap_first}TestUtils generateRandom];
    <#list InheritanceUtils.getAllChildren(curr) as child>
    //self.nbEntities += [[${curr.name?cap_first}DataLoader get${curr.name?cap_first}DataLoader] getItems].count;
    </#list>
}

- (void)tearDown {
    [super tearDown];
}

- (void)testCreate {
    long long result = -1;

    if (self.entity) {
        ${curr.name?cap_first}* ${curr.name?uncap_first} = [${curr.name?cap_first}TestUtils generateRandom];

        result = [self.sqlAdapter insert:${curr.name?uncap_first}];

        XCTAssertTrue(result >= 0, @"${curr.name?cap_first} wasn't inserted correctly.");
    }
}

- (void)testRead {
    ${curr.name?cap_first}* result = nil;

    if (self.entity) {
        int itemId = [self.sqlAdapter insert:self.entity];
		self.entity.id = itemId;
		
        result = [self.sqlAdapter getByID:self.entity.<#list curr_ids as id>${id.name?uncap_first}<#if id_has_next>,</#if></#list>];
        [${curr.name?cap_first}TestUtils equals:self.entity
                withCompare:result];
    }
}

<#if curr.options.sync??>
- (void)testReadByServerId {
    ${curr.name?cap_first}* result = nil;
    
    if (self.entity) {
        int itemId = [self.sqlAdapter insert:self.entity];
        self.entity.id = itemId;
        
        result = [self.sqlAdapter getByServerID:self.entity.serverId];
        [${curr.name?cap_first}TestUtils equals:self.entity
                        withCompare:result];
    }
}

</#if>
- (void) testUpdate {
    long long result = -1;

    if (self.entity) {
        ${curr.name?cap_first}* ${curr.name?uncap_first} = [${curr.name?cap_first}TestUtils generateRandom];
        [${curr.name?uncap_first} setId:self.entity.id];

        result = [self.sqlAdapter update:${curr.name?uncap_first}];
        
        XCTAssertTrue(result >= 0, @"${curr.name?cap_first} wasn't updated correctly.");
    }

}

- (void) testDelete {
    long long result = -1;

    if (self.entity) {
        result = [self.sqlAdapter removeById:self.entity.<#list curr_ids as id>${id.name?uncap_first}<#if id_has_next>,</#if></#list>];
        
        XCTAssertTrue(result >= 0, @"${curr.name?cap_first} wasn't deleted correctly.");
    }
}

- (void) testAll {
    int result = [[self.sqlAdapter getAll] count];
    int expectedSize = self.nbEntities;
    
    XCTAssertEqual(expectedSize, result, @"${curr.name?cap_first} wasn't readed correctly.");
}

@end

