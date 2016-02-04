<@header?interpret />

#import <Foundation/Foundation.h>

typedef void(^ContentObserverCallback) (void);

@interface ContentObserver : NSObject

@property (strong, nonatomic) NSString* uri;
@property (copy) ContentObserverCallback callback;

/**
 * Init Content observer with callback.
 * @param callback ContentObserverCallback
 * @return contentObserver
 */
- (id) initWithCallback:(ContentObserverCallback) callback;

/**
 * Notify on update.
 */
- (void) notifyUpdate;

@end