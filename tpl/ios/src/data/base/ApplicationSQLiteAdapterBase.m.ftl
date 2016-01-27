<@header?interpret />

#import "SQLiteAdapterBase.h"
#import "ContentResolver.h"

@implementation SQLiteAdapterBase

- (id) init {
    self = [super init];
    if (self) {
        self->mBaseHelper = [${project_name?cap_first}SQLiteOpenHelper get${project_name?cap_first}SQLiteOpenHelperBase];
    }

    return self;
}

- (void) close {
    //[self->mBaseHelper close];
}

- (NSString *) getTableName {
    return nil;
}

- (NSString *) getJoinedTableName {
    return nil;
}

+ (NSString *) getSchema {
    return nil;
}

- (NSArray *) getCols {
    return nil;
}

- (NSArray *) cursorToItems:(Cursor *) cursor {
    return [NSArray array];
}

- (NSDictionary *) itemToContentValues:(id) item {
    return [NSDictionary dictionary];
}

- (NSArray *) getAll {
    __block NSArray *result = nil;

    NSString *query = [NSString stringWithFormat:@"SELECT %@ FROM %@",
                       [[self getCols] componentsJoinedByString:@", "],
                       [self getJoinedTableName]];
    
    __block FMDatabaseQueue *queue = [self->mBaseHelper getQueue];

    [queue inDatabase:^(FMDatabase *db) {
        Cursor *cursor;
        FMResultSet *rs = [db executeQuery:query];

        cursor = [[Cursor alloc] initWithFMResultSet:rs andFMDatabaseQueue:queue];

        result = [self cursorToItems:cursor];

        [cursor close];
    }];

    return result;
}

- (Cursor *) query:(NSString *) tables
    withProjection:(NSArray *) projection
   withWhereClause:(NSString *) whereClause
     withWhereArgs:(NSArray *) whereArgs
       withGroupBy:(NSString *) groupBy
        withHaving:(NSString *) having
       withOrderBy:(NSString *) orderBy {

    __block FMResultSet *resultSet = nil;
    __block FMDatabaseQueue *queue = [self->mBaseHelper getQueue];

    [queue inDatabase:^(FMDatabase *db) {
        NSString* sqlWhere = @"";

        if (whereClause) {
            sqlWhere = [NSString stringWithFormat:@"WHERE %@", whereClause];
        }

        NSString* query = [NSString stringWithFormat:@"SELECT %@ FROM %@ %@",
                           [projection componentsJoinedByString:@", "],
                           tables,
                           sqlWhere];

        if (groupBy) {
            query = [NSString stringWithFormat:@"%@ GROUP BY %@", query, groupBy];
        }

        if (having) {
            query = [NSString stringWithFormat:@"%@ HAVING %@", query, having];
        }

        if (orderBy) {
            query = [NSString stringWithFormat:@"%@ ORDER BY %@", query, orderBy];
        }

        resultSet = [db executeQuery:query withArgumentsInArray:whereArgs];
    }];

    Cursor *result = [[Cursor alloc] initWithFMResultSet:resultSet andFMDatabaseQueue:queue];

    return result;
}

- (Cursor *) query:(NSArray *)projection
   withWhereClause:(NSString *)whereClause
     withWhereArgs:(NSArray *)whereArgs
       withGroupBy:(NSString *)groupBy
        withHaving:(NSString *)having
       withOrderBy:(NSString *)orderBy {

    return [self query:[self getJoinedTableName]
        withProjection:projection
       withWhereClause:whereClause
         withWhereArgs:whereArgs
           withGroupBy:groupBy
            withHaving:having
           withOrderBy:orderBy];
}

- (long long) insert:(NSString *) nullColumnHack withValues:(NSDictionary *) values {

    __block long long result = -1;

#ifdef DEBUG
    NSLog(@"Insert DB(%@)", self.getTableName);
#endif
    
    [[self->mBaseHelper getQueue] inDatabase:^(FMDatabase *db) {
        NSArray *keys = [values allKeys];
        NSMutableArray *prefixedKeys = [NSMutableArray array];

        [keys enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
            [prefixedKeys addObject:[NSString stringWithFormat:@":%@",obj]];
        }];

        NSString *query = [NSString stringWithFormat:@"INSERT INTO %@ (%@) VALUES (%@)",
                           [self getTableName],
                           [keys componentsJoinedByString:@", "],
                           [prefixedKeys componentsJoinedByString:@", "]];

        bool insert = [db executeUpdate:query withParameterDictionary:values];

        if (insert) {
            result = [db lastInsertRowId];
        }
    }];

    [self notifyContentResolver];

    return result;
}

- (int) update:(NSDictionary *) values withWhereClause:(NSString *) whereClause withWhereArgs:(NSArray *) whereArgs {

#ifdef DEBUG
    NSLog(@"Update DB(%@)", self.getTableName);
#endif

    __block int result = -1;

    [[self->mBaseHelper getQueue] inDatabase:^(FMDatabase *db) {
        NSMutableArray *arrayUpdate = [[NSMutableArray alloc] init];

        for (id key in values) {
            [arrayUpdate addObject:[NSString stringWithFormat:@"%@ = ?", key]];
        }

        NSString *sqlWhere = nil;

        if (whereClause) {
            sqlWhere = [NSString stringWithFormat:@"WHERE %@", whereClause];
        }

        NSString *query = [NSString stringWithFormat:@"UPDATE %@ SET %@ %@",
                           [self getTableName],
                           [arrayUpdate componentsJoinedByString:@", "],
                           sqlWhere];

        NSMutableArray *args = [[values allValues] mutableCopy];
        [args addObjectsFromArray:whereArgs];

        if ([db executeUpdate:query withArgumentsInArray:args]) {
            result = [db changes];
        }
    }];

    [self notifyContentResolver];

    return result;
}

- (int) delete:(NSString *) whereClause withWhereArgs:(NSArray *) whereArgs {
    __block int result = -1;

    [[self->mBaseHelper getQueue] inDatabase:^(FMDatabase *db) {
        NSString *sqlWhere = nil;

        if (whereClause) {
            sqlWhere = [NSString stringWithFormat:@"WHERE %@", whereClause];
        }

        NSString *query = [NSString stringWithFormat:@"DELETE FROM %@ %@",
                           [self getTableName],
                           sqlWhere];

        if ([db executeUpdate:query withArgumentsInArray:whereArgs]) {
            result = [db changes];
        }
    }];

    [self notifyContentResolver];

    return result;
}

- (InsertBatch *) getInsertBatch:(id) item {
    NSMutableDictionary *values = (NSMutableDictionary *) [self itemToContentValues:item];
    [values removeObjectForKey:@"id"];

    return [[InsertBatch alloc] initWithItemValues:values
                                     withTableName:[self getTableName]
                                   withWhereClause:nil
                                     withWhereArgs:nil];
}

- (UpdateBatch *) getUpdateBatch:(id) item
                 withWhereClause:(NSString *) whereClause
                   withWhereArgs:(NSArray *) whereArgs {

    return [[UpdateBatch alloc] initWithItemValues:[self itemToContentValues:item]
                                     withTableName:[self getTableName]
                                   withWhereClause:whereClause
                                     withWhereArgs:whereArgs];
}

- (DeleteBatch *) getDeleteBatch:(id) item
                 withWhereClause:(NSString *) whereClause
                   withWhereArgs:(NSArray *) whereArgs {    

    return [[DeleteBatch alloc] initWithItemValues:nil
                                     withTableName:[self getTableName]
                                   withWhereClause:whereClause
                                     withWhereArgs:whereArgs];
}

- (void) executeBatch:(BatchProvider *) batch {

#ifdef DEBUG
    NSLog(@"Execute batch");
#endif

    __block NSMutableDictionary *notifyUris = [NSMutableDictionary dictionary];

    [[self->mBaseHelper getQueue] inTransaction:^(FMDatabase *db, BOOL *rollback) {
        for (ItemBatch *item in [batch getQueue]) {
            if ([db executeUpdate:item.getQuery withArgumentsInArray:item.getArgs]) {
                if ([notifyUris objectForKey:item.tableName] == nil) {
                    [notifyUris setObject:item.tableName forKey:item.tableName];
                }
            }
        }
    }];

    for (NSString *uri in [notifyUris allValues]) {
        [[ContentResolver getContentResolver] notifyUri:uri];
    }
}

- (void) notifyContentResolver {
    [[ContentResolver getContentResolver] notifyUri:self.getTableName];
}

- (long long) insert:(id) item {
    return -1;
}

- (bool) update:(id) item {
    return nil;
}

- (bool) remove:(id) item {
    return nil;
}

- (id) getByServerID:(NSNumber *) serverId {
    return nil;
}

- (UpdateBatch *) getUpdateBatch:(id) item {
    return nil;
}

- (DeleteBatch *) getDeleteBatch:(id) item {
    return nil;
}

@end