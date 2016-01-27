<@header?interpret />

#import "ContractBase.h"

@implementation ContractBase

+ (NSString *) TABLE_NAME {
    return nil;
}

+ (NSArray *) COLS {
    return nil;
}

+ (NSArray *) ALIASED_COLS {
    return nil;
}

+ (NSDictionary *) itemToContentValues:(id) item {
    return nil;
}

+ (id) cursorToItem:(FMResultSet *) cursor {
    return 0;
}

+ (void) cursorToItem:(FMResultSet *) cursor withItem:(id) result {
    
}

+ (NSArray *) cursorToItems:(FMResultSet *) cursor {
    return nil;
}

@end