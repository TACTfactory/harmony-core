<@header?interpret />

#import <Foundation/Foundation.h>

typedef void(^ContentObserverCallback) (void);

@interface ContentObserver : NSObject

@property (strong, nonatomic) NSString* uri;
@property (copy) ContentObserverCallback callback;

- (id) initWithCallback: (ContentObserverCallback) callback;
- (void) notifyUpdate;

@end