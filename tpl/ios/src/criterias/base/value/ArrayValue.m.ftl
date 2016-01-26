<@header?interpret />

#import "ArrayValue.h"

@implementation ArrayValue

- (id) init {
    if ([super init]) {
        self->values = [NSMutableArray new];
    }

    return self;
}

- (NSString *) toSQLiteString {
    NSMutableString *valuesString = [NSMutableString stringWithString:@"("];
    NSString *delimiter = @"";

    for (NSString *value in self->values) {
        [valuesString appendString:delimiter];
        [valuesString appendString:value];
        delimiter = @", ";
    }

    [valuesString appendString:@")"];

    return valuesString;
}

- (NSString *) toSQLiteSelection {
    NSMutableString *valuesString = [NSMutableString stringWithString:@"("];

    for (int i = 0; i < self->values.count; i++) {
        [valuesString appendString:@"?"];

        if (i != self->values.count - 1) {
            [valuesString appendString:@", "];
        }
    }

    [valuesString appendString:@")"];

    return valuesString;
}

- (void) toSQLiteSelectionArgs:(NSMutableArray *) array {
    [array addObjectsFromArray:self->values];
}

- (NSArray *) getValues {
    return self->values;
}

- (void) addValue:(NSString *) value {
    [self->values addObject:value];
}

@end
