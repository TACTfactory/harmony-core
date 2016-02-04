<@header?interpret />

#import <Foundation/Foundation.h>

@interface DatabaseUtils : NSObject

/**
 * Extract the content values for this entity.
 * (in case of joined inheritance)
 *
 * @param from The content values containing all the values
 *    (superclasses + children)
 * @param columnsToExtract The columns to extract from the values
 * @return the content values of this entity
 */
+(NSMutableDictionary*) extractContentValues:(NSMutableDictionary*) from
                        withColumnsToExtract:(NSArray*) columnsToExtract;

@end