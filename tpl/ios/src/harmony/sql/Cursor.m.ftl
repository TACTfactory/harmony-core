<@header?interpret />

#import "Cursor.h"

@implementation Cursor {
    FMResultSet *fmResult;
    FMDatabaseQueue *fmDatabaseQueue;
}

- (id) initWithFMResultSet:(FMResultSet *) resultSet andFMDatabaseQueue:(FMDatabaseQueue *) queue {
    if (self = [super init]) {
        self->fmResult = resultSet;
        self->fmDatabaseQueue = queue;
    }
    
    return self;
}

- (FMResultSet *) getFMResultSet {
    return self->fmResult;
}

- (FMDatabaseQueue *) getFMDatabaseQueue {
    return self->fmDatabaseQueue;
}

#pragma mark - Column methods.

- (int) columnCount {
    return [self->fmResult columnCount];
}

- (int) columnIndexForName:(NSString *) columnName {
    return [self->fmResult columnIndexForName:columnName];
}

- (NSString *) columnNameForIndex:(int) columnIdx {
    return [self->fmResult columnNameForIndex:columnIdx];
}

- (int) intForColumn:(NSString *) columnName {
    return [self->fmResult intForColumn:columnName];
}

- (int) intForColumnIndex:(int) columnIdx {
    return [self->fmResult intForColumnIndex: columnIdx];
}

- (long) longForColumn:(NSString *) columnName {
    return [self->fmResult longForColumn:columnName];
}

- (long) longForColumnIndex:(int) columnIdx {
    return [self->fmResult longForColumnIndex:columnIdx];
}

- (long long int) longLongIntForColumn:(NSString *) columnName {
    return [self->fmResult longLongIntForColumn:columnName];
}

- (long long int) longLongIntForColumnIndex:(int) columnIdx {
    return [self->fmResult longLongIntForColumnIndex:columnIdx];
}

- (bool) boolForColumn:(NSString *) columnName {
    return [self->fmResult boolForColumn:columnName];
}

- (bool) boolForColumnIndex:(int) columnIdx {
    return [self->fmResult boolForColumnIndex:columnIdx];
}

- (double) doubleForColumn:(NSString *) columnName {
    return [self->fmResult doubleForColumn:columnName];
}

- (double) doubleForColumnIndex:(int) columnIdx {
    return [self->fmResult doubleForColumnIndex:columnIdx];
}

- (NSString *) stringForColumn:(NSString *) columnName {
    return [self->fmResult stringForColumn:columnName];
}

- (NSString *) stringForColumnIndex:(int) columnIdx {
    return [self->fmResult stringForColumnIndex:columnIdx];
}

- (NSDate *) dateForColumn:(NSString *) columnName {
    return [self->fmResult dateForColumn:columnName];
}

- (NSDate *) dateForColumnIndex:(int) columnIdx {
    return [self->fmResult dateForColumnIndex:columnIdx];
}

- (NSData *) dataForColumn:(NSString *) columnName {
    return [self->fmResult dataForColumn:columnName];
}

- (NSData *) dataForColumnIndex:(int) columnIdx {
    return [self->fmResult dataForColumnIndex:columnIdx];
}

- (bool) columnIndexIsNull:(int) columnIdx {
    return [self->fmResult columnIndexIsNull:columnIdx];
}

- (bool) columnIsNull:(NSString *) columnName {
    return [self->fmResult columnIsNull:columnName];
}

#pragma mark - Other methods.

- (bool) next {
    return [self->fmResult next];
}

- (void) close {
    [self->fmResult close];
    [self->fmDatabaseQueue close];
}

@end