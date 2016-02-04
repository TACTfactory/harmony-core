<@header?interpret />

#import "NSDate+Compare.h"

@implementation NSDate (Compare)

- (bool) isAfterOrEqualTo:(NSDate *) date {
    return !([self compare:date] == NSOrderedAscending);
}

- (bool) isBeforeOrEqualTo:(NSDate *) date {
    return !([self compare:date] == NSOrderedDescending);
}

- (bool) isAfter:(NSDate *) date {
    return ([self compare:date] == NSOrderedDescending);
}

- (bool) isBefore:(NSDate *) date {
    return ([self compare:date] == NSOrderedAscending);
}

@end