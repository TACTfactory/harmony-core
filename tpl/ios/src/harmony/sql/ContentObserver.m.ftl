<@header?interpret />

#import "ContentObserver.h"

@implementation ContentObserver

- (id) initWithCallback:(ContentObserverCallback) callback {
    if (self = [super init]) {
        self.callback = callback;
    }
    
    return self;
}

- (void) notifyUpdate {
    if (self.callback) {
        self.callback();
    }
}

@end