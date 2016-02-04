<@header?interpret />

#import "DeleteBatch.h"

@implementation DeleteBatch

- (NSString *) getQuery {
    NSString* sqlWhere = nil;
    
    if (self->whereClause) {
        sqlWhere = [NSString stringWithFormat:@"WHERE %@", self->whereClause];
    }
    
    return [NSString stringWithFormat:@"DELETE FROM %@ %@", self.tableName, sqlWhere];
}

@end