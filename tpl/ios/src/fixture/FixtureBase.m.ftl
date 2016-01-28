<#assign fixtureType = options["fixture"].type />
<@header?interpret />

#import "FixtureBase.h"
#import "DataLoader.h"
#import "FixtureXMLToObjectParser.h"

@implementation FixtureBase

- (id) init {
    if (self = [super init]) {
        self->items = [NSDictionary dictionary];
    }

    return self;
}

- (void) getModelFixtures:(int) mode {
    NSString *fixtureDirectoryPath;

    if (mode == MODE_APP) {
        fixtureDirectoryPath = @"app";
    } else if (mode == MODE_DEBUG) {
        fixtureDirectoryPath = @"debug";
    } else if (mode == MODE_TEST) {
        fixtureDirectoryPath = @"test";
    }

    NSString *fileName = [self getFixtureFileName];
    NSString *path = [[NSBundle mainBundle] pathForResource:fileName ofType:@"xml"];

    if (path != nil) {
        NSURL *fixtureUrl = [NSURL fileURLWithPath:path];

        FixtureXMLToObjectParser *xmlParser = [[FixtureXMLToObjectParser alloc]
                                               parseXMLAtURL:fixtureUrl
                                               toObject:fileName
                                               parseError:nil];

        self->items = [xmlParser getItemsWithKey];
    } else {
        NSLog(@"Fixture error : The filename %@ wasn't found in assets.", fileName);
    }
}

- (void) loadModel {
}

- (void) load:(DataManager *) dataManager {
}

- (NSString *) getFixtureFileName {
    return nil;
}

- (NSDictionary *) getItems {
    return self->items;
}

- (void) clear {
    self->items = [NSDictionary dictionary];
}

@end