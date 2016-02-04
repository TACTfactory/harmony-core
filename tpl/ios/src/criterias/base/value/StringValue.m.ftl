<@header?interpret />

#import "StringValue.h"

@implementation StringValue {
    /** List of values. */
    NSString *value;
}

- (id) initWithValue:(NSString *) initValue {
    if (self = [super init]) {
        self->value = initValue;
    }

    return self;
}

- (void) setValue:(NSString *) newValue {
    self->value = newValue;
}

- (NSString *) getValue {
    return self->value;
}

- (NSString *) toSQLiteString {
    return self->value;
}

- (NSString *) toSQLiteSelection {
    NSString *result;
    
    if (self->value == nil) {
        result = @"";
    } else {
        result = @"?";
    }

    return result;
}

- (void) toSQLiteSelectionArgs:(NSMutableArray *) array {
    if (self->value != nil) {
        [array addObject:self->value];
    }
}

@end