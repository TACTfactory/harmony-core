<@header?interpret />
#import <Foundation/Foundation.h>

@interface DataManager : NSObject

/**
 * Persist item.
 * @param item The item
 * @return int
 */
- (int) persist:(id) item;

@end