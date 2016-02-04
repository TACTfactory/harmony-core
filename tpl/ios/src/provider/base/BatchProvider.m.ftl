<@header?interpret />

#import "BatchProvider.h"

@implementation BatchProvider

- (id) init {
    if (self = [super init]) {
        self->queue = [NSMutableArray array];
    }
    
    return self;
}

- (void) addToBatch:(ItemBatch *) item {
    [self->queue addObject:item];
}

- (NSArray*) getQueue {
    return self->queue;
}

@end