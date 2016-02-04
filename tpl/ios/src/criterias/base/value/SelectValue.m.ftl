<@header?interpret />

#import "SelectValue.h"

@implementation SelectValue

- (NSString *) getRefTable {
    return self->refTable;
}

- (void) setRefTable:(NSString *) refTableToSet {
    self->refTable = refTableToSet;
}

- (NSString *) getRefKey {
    return self->refKey;
}

- (void) setRefKey:(NSString *) refKeyToSet {
    self->refKey = refKeyToSet;
}

- (CriteriaExpression *) getCriteria {
    return self->criteria;
}

- (void) setCriteria:(CriteriaExpression *) crit {
    self->criteria = crit;
}

- (NSString *) toSQLiteString {
    return [NSString stringWithFormat:@"(SELECT %@ FROM %@ WHERE %@)",
            self->refKey,
            self->refTable,
            [self->criteria toSQLiteString]];
}

- (NSString *) toSQLiteSelection {
    return [NSString stringWithFormat:@"(SELECT %@ FROM %@ WHERE %@)",
            self->refKey,
            self->refTable,
            [self->criteria toSQLiteSelection]];
}

- (void) toSQLiteSelectionArgs:(NSMutableArray *) array {
    [self->criteria toSQLiteSelectionArgs:array];
}

@end