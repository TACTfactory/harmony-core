<@header?interpret />

#import "CustomValue.h"

@implementation CustomValue {
    @private
    /** The customSql. */
    NSString *customSql;
    /** The criteria. */
    CriteriaExpression *criteria;
}

- (id) initWithCustomSql:(NSString *) customSqlToSet {
    if (self = [super init]) {
        self = [self initWithCustomSql:customSqlToSet withCriteriaExpression:nil];
    }

    return self;
}

- (id) initWithCustomSql:(NSString *) customSqlToSet withCriteriaExpression:(CriteriaExpression *) criteriaToSet {
    if (self = [super init]) {
        self->customSql = customSqlToSet;
        self->criteria = criteriaToSet;
    }

    return self;
}

- (NSString *) getCustomSql {
    return self->customSql;
}

- (void) setCustomSql:(NSString *) customSqlToSet {
    self->customSql = customSqlToSet;
}

- (CriteriaExpression *) getCriteria {
    return self->criteria;
}

- (void) setCriteria:(CriteriaExpression *) criteriaToSet {
    self->criteria = criteriaToSet;
}

- (NSString *) toSQLiteString {
    NSString *result = [NSString stringWithFormat:@"(%@", self->customSql];

    if (self->criteria != nil) {
        result = [result stringByAppendingString:[NSString stringWithFormat:@" WHERE %@",
                                                  self->criteria.toSQLiteString]];
    }

    result = [result stringByAppendingString:@")"];

    return result;
}

- (NSString *) toSQLiteSelection {
    NSString *result = [NSString stringWithFormat:@"(%@", self->customSql];

    if (self->criteria != nil) {
        result = [result stringByAppendingString:[NSString stringWithFormat:@" WHERE %@",
                                                  self->criteria.toSQLiteSelection]];
    }

    result = [result stringByAppendingString:@")"];

    return result;
}

- (void) toSQLiteSelectionArgs:(NSMutableArray *) array {
    [self->criteria toSQLiteSelectionArgs:array];
}

@end