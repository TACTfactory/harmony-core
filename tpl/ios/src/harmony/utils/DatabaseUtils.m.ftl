<@header?interpret />

#import "DatabaseUtils.h"

@implementation DatabaseUtils

+ (NSMutableDictionary*) extractContentValues:(NSMutableDictionary*) from
                    withColumnsToExtract:(NSArray*) columnsToExtract {
    NSMutableDictionary* result = [NSMutableDictionary dictionary];
    
    for (id colName in columnsToExtract) {
        if ([from objectForKey:colName]) {
            [DatabaseUtils transfer:from withTo:result withColName:colName withKeep:NO];
        }
    }
    
    return result;
}

/**
 * Transfer a column from a contentvalue to another one.
 *
 * @param from The source content value
 * @param to The destination contentvalue
 * @param colName The name of the column to transfer
 * @param keep if false, delete it from the old contentvalue
 */
+ (void) transfer:(NSMutableDictionary*) from
           withTo:(NSMutableDictionary*) to
      withColName:(NSString*) colName
         withKeep:(BOOL) keep {
    
    NSObject* fromObject = [from objectForKey:colName];
    
    [to setObject:fromObject forKey:colName];
    
    if (!keep) {
        [from removeObjectForKey:colName];
    }
}

@end