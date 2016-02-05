<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <Foundation/Foundation.h>

@interface FixtureXMLToObjectParser : NSObject<NSXMLParserDelegate> {
    @private
    NSString *entityName;
    NSMutableArray *items;
    NSObject *entity;
    NSString *currentNodeName;
    NSMutableString *currentNodeContent;
    NSMutableDictionary *itemsWithKey;
}

/**
 * Get the list of item parse.
 * @return list of item
 */
- (NSArray *) getItems;

/**
 * Get the dictionary of item with their xml id as key.
 * @return dictionary of item with id as key
 */
- (NSDictionary *) getItemsWithKey;

/**
 * Parse a XML with the file url and set it in an object. Throw an NSError if problem when parsing.
 * @param url NSURL of the file
 * @param className The name of the class of the entity
 * @param error NSError
 *
 */
- (id) parseXMLAtURL:(NSURL *) url toObject:(NSString *) className parseError:(NSError *) error;

@end