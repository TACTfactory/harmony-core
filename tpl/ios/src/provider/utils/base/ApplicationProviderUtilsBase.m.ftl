<@header?interpret />

#import "ProviderUtilsBase.h"

@implementation ProviderUtilsBase

- (void) executeBatch:(BatchProvider *) batch {
    SQLiteAdapter *adapter = [SQLiteAdapter new];
    [adapter executeBatch:batch];
}

- (id) getByServerId:(NSNumber *) serverId {
    id result = nil;
    
    result = [sqlAdapter getByServerID:serverId];
    
    return result;
}

@end
