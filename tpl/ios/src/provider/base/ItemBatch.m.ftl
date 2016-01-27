<@header?interpret />

#import "ItemBatch.h"

@implementation ItemBatch

- (id) initWithItemValues:(NSDictionary *) initValues
            withTableName:(NSString *) initTableName
          withWhereClause:(NSString *) initWhereClause
            withWhereArgs:(NSArray *) initWhereArgs {
    
    if (self = [super init]) {
        self->values = initValues;
        self->_tableName = initTableName;
        self->whereClause = initWhereClause;
        self->whereArgs = initWhereArgs;
    }
    
    return self;
}

- (NSString *) getQuery {
    return nil;
}

- (NSArray *) getArgs {
    NSMutableArray *args = nil;
    
    if (values != nil && values.count > 0) {
        args = [[values allValues] mutableCopy];
    }
        
    if (whereArgs != nil && whereArgs.count > 0) {
        if (args == nil) {
            args = [NSMutableArray arrayWithArray:whereArgs];
        } else {
            [args addObjectsFromArray:whereArgs];
        }
    }
    
    return args;
}

@end
