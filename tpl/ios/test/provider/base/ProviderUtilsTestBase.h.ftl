<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <XCTest/XCTest.h>

@interface ProviderUtilsTestBase : XCTestCase

@property id providerUtils;
@property id entity;
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
