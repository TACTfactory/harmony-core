<@header?interpret />

#import "AsyncTask.h"

@implementation AsyncTask

+ (id) newWithPreExecute:(void (^)(AsyncTask *)) onPreExecute
      withDoInBackground:(void (^)(AsyncTask *)) doInBackground
         withPostExecute:(void (^)(AsyncTask *, bool)) onPostExecute {

    AsyncTask *result = [AsyncTask new];
    result->preExecute = onPreExecute;
    result->inBackground = doInBackground;
    result->postExecute = onPostExecute;

    return result;
}

- (void) onPreExecute {
    dispatch_async(dispatch_get_main_queue(), ^{
        self->preExecute(self);
    });
}

- (void) doInBackground {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0), ^{
        self->inBackground(self);
    });
}

- (void) onPostExecute:(bool) result {
    dispatch_async(dispatch_get_main_queue(), ^{
        self->postExecute(self, result);
    });
}

- (void) execute {
    if (self->preExecute != nil) {
        [self onPreExecute];
    } else {
        [self doInBackground];
    }
}

@end