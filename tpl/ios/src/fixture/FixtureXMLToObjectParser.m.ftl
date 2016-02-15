<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "FixtureXMLToObjectParser.h"
#import "ParseValue.h"

@implementation FixtureXMLToObjectParser {
    @private
    NSMutableArray *listOfElements;
    NSString *parentElementName;
    NSString *childElementName;
}

- (NSArray *) getItems {
    return self->items;
}

- (NSDictionary *) getItemsWithKey {
    return self->itemsWithKey;
}

- (id) parseXMLAtURL:(NSURL *) url toObject:(NSString *) className parseError:(NSError *) error {
    self->items = [NSMutableArray new];
    self->itemsWithKey = [NSMutableDictionary new];
    self->entityName = className;
    self->listOfElements = [NSMutableArray new];

    NSXMLParser *parser = [[NSXMLParser alloc] initWithContentsOfURL:url];
    [parser setDelegate:self];
    [parser parse];

    if ([parser parserError] && error) {
        error = [parser parserError];
    }

    return self;
}

- (void) parser:(NSXMLParser *) parser
didStartElement:(NSString *) elementName
   namespaceURI:(NSString *) namespaceURI
  qualifiedName:(NSString *) qualifiedName
     attributes:(nonnull NSDictionary<NSString *, NSString * > *) attributeDict {

    if ([elementName isEqualToString:self->entityName]) {
        self->entity = [NSClassFromString(self->entityName) new];
        [self->itemsWithKey setObject:self->entity forKey:[attributeDict valueForKey:@"id"]];
    } else {
        // If the current element is a list.
        if (self->currentNodeName != nil && ![self->currentNodeName isEqualToString:@"list"]) {
            self->parentElementName = self->currentNodeName;
            self->childElementName = [elementName copy];
        }

        self->currentNodeName = [elementName copy];
        self->currentNodeContent = [NSMutableString new];
    }
}

- (void) parser:(NSXMLParser *) parser
  didEndElement:(NSString *) elementName
   namespaceURI:(NSString *) namespaceURI
  qualifiedName:(NSString *) qualifiedName {

    if ([elementName isEqualToString:self->entityName]) {
        [items addObject:self->entity];
        self->entity = nil;
    } else {
        // If the item is in a list, add it to a temporary list.
        if (self->childElementName != nil && self->childElementName == elementName) {
            [self->listOfElements addObject:self->currentNodeContent];

            self->currentNodeContent = nil;
            self->currentNodeName = nil;
        // If the previous list isn't empty and is finish, put it in the object.
        } else if (self->parentElementName != nil && [elementName isEqualToString:self->parentElementName]
                   && self->listOfElements != nil && self->listOfElements.count > 0) {
            [self->entity setValue:[self->listOfElements copy] forKey:self->parentElementName];
            self->parentElementName = nil;
            self->childElementName = nil;
            [self->listOfElements removeAllObjects];
        // Insert the current element in object.
        } else if ([elementName isEqualToString:self->currentNodeName]) {
            ParseValue *parseValue = [[ParseValue alloc] initWithString:self->currentNodeContent];

            [self->entity setValue:parseValue forKey:elementName];

            self->currentNodeContent = nil;
            self->currentNodeName = nil;
        }
    }
}

- (void) parser:(NSXMLParser *) parser foundCharacters:(NSString *) string {
    [self->currentNodeContent appendString:string];
}

@end