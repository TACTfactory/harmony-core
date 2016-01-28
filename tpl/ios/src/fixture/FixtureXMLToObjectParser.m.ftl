<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "FixtureXMLToObjectParser.h"

@implementation FixtureXMLToObjectParser

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
    } else if ([elementName isEqualToString:self->currentNodeName]) {
        [self->entity setValue:self->currentNodeContent forKey:elementName];

        self->currentNodeContent = nil;
        self->currentNodeName = nil;
    }
}

- (void) parser:(NSXMLParser *) parser foundCharacters:(NSString *) string {
    [self->currentNodeContent appendString:string];
}

@end