<@header?interpret />

#import "InsertBatch.h"

@implementation InsertBatch

- (NSString *) getQuery {
    NSArray *keys = [self->values allKeys];
    NSMutableArray *prefixedKeys = [NSMutableArray array];

    [keys enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        [prefixedKeys addObject:[NSString stringWithFormat:@":%@",obj]];
    }];

    NSString *query = [NSString stringWithFormat:@"INSERT INTO %@ (%@) VALUES (%@)",
                       self.tableName,
                       [keys componentsJoinedByString:@", "],
                       [prefixedKeys componentsJoinedByString:@", "]];

    return query;
}

@end