<@header?interpret />

#import "UpdateBatch.h"

@implementation UpdateBatch

- (NSString *) getQuery {
    NSMutableArray *arrayUpdate = [NSMutableArray new];

    for (id key in self->values) {
        [arrayUpdate addObject:[NSString stringWithFormat:@"%@ = ?", key]];
    }

    NSString* sqlWhere = nil;

    if (self->whereClause) {
        sqlWhere = [NSString stringWithFormat:@"WHERE %@", self->whereClause];
    }

    return [NSString stringWithFormat:@"UPDATE %@ SET %@ %@",
            self.tableName,
            [arrayUpdate componentsJoinedByString:@", "],
            sqlWhere];
}

@end