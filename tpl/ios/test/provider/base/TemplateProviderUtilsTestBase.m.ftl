<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "${curr.name}ProviderUtilsTestBase.h"
#import "${curr.name}TestUtils.h"
<#if dataLoader?? && dataLoader>
    <#list InheritanceUtils.getAllChildren(curr) as child>
#import "${child.name?cap_first}DataLoader.h"
    </#list>
</#if>

@implementation ${curr.name}ProviderUtilsTestBase

- (void)setUp {
    [super setUp];

    self.providerUtils = [${curr.name?cap_first}ProviderUtils new];
    self.entity = [${curr.name?cap_first}TestUtils generateRandom];
    <#list InheritanceUtils.getAllChildren(curr) as child>
    self.nbEntities += [[${curr.name?cap_first}DataLoader get${curr.name?cap_first}DataLoader] getItems].count;
    </#list>
}

- (void)tearDown {
    [super tearDown];
}

- (void)testCreate {
    long long result = -1;

    if (self.entity) {
        ${curr.name?cap_first}* ${curr.name?uncap_first} = [${curr.name?cap_first}TestUtils generateRandom];

        result = [self.providerUtils insert:${curr.name?uncap_first}];

        XCTAssertTrue(result >= 0, @"${curr.name?cap_first} wasn't inserted correctly.");
    }
}

- (void)testRead {
    ${curr.name?cap_first}* result = nil;

    if (self.entity) {
        int itemId = [self.providerUtils insert:self.entity];
        ((${curr.name?cap_first}*)self.entity).id = itemId;

        result = [self.providerUtils queryWithId:itemId];
        [${curr.name?cap_first}TestUtils equals:self.entity withCompare:result];
    }
}

<#if curr.options.sync??>
- (void)testReadByServerId {
    ${curr.name?cap_first}* result = nil;

    if (self.entity) {
        int itemId = [self.providerUtils insert:self.entity];
        ((${curr.name?cap_first}*)self.entity).id = itemId;

        result = [((${curr.name?cap_first}ProviderUtils*)self.providerUtils) getByServerId:((${curr.name?cap_first}*)self.entity).serverId];
        [${curr.name?cap_first}TestUtils equals:self.entity withCompare:result];
    }
}

</#if>
- (void) testUpdate {
    long long result = -1;

    if (self.entity) {
        ${curr.name?cap_first}* ${curr.name?uncap_first} = [${curr.name?cap_first}TestUtils generateRandom];
        [${curr.name?uncap_first} setId:((${curr.name?cap_first}*)self.entity).id];

        result = [((${curr.name?cap_first}ProviderUtils*)self.providerUtils) update:${curr.name?uncap_first}];

        XCTAssertTrue(result >= 0, @"${curr.name?cap_first} wasn't updated correctly.");
    }

}

- (void) testDelete {
    long long result = -1;

    if (self.entity) {
        result = [((${curr.name?cap_first}ProviderUtils*)self.providerUtils) delete:((${curr.name?cap_first}*)self.entity)];

        XCTAssertTrue(result >= 0, @"${curr.name?cap_first} wasn't deleted correctly.");
    }
}

- (void) testAll {
    int result = [[self.providerUtils queryAll] count];
    int expectedSize = self.nbEntities;

    XCTAssertEqual(expectedSize, result, @"${curr.name?cap_first} wasn't readed correctly.");
}

@end

