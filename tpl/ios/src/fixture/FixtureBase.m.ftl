<#assign fixtureType = options["fixture"].type />
<@header?interpret />
#import "FixtureBase.h"

@implementation FixtureBase

- (id) init {
    if (self = [super init]) {
        self->items = [NSMutableDictionary dictionary];
    }

    return self;
}

-(void) loadModel {

}

- (void) load:(DataManager *) dataManager {

}

- (NSMutableDictionary *) getItems {
    return self->items;
}

-(void) clear {
    [self->items removeAllObjects];
}

@end