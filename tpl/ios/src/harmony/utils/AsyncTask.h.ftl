<@header?interpret />

#import <Foundation/Foundation.h>

@interface AsyncTask : NSObject {
    @protected
    void (^ preExecute)(AsyncTask *);
    void (^ inBackground)(AsyncTask *);
    void (^ postExecute)(AsyncTask *, bool);
}

/**
 * Init the AsyncTask.
 * @param preExecute
 * @param inBackground
 * @param postExecute
 * @return AsyncTask
 */
+ (id) newWithPreExecute:(void (^)(AsyncTask *)) preExecute
      withDoInBackground:(void (^)(AsyncTask *)) inBackground
         withPostExecute:(void (^)(AsyncTask *, bool)) postExecute;

/**
 * Launch onPreExecute.
 */
- (void) onPreExecute;

/**
 * Launch doInBackground.
 */
- (void) doInBackground;

/**
 * Launch onPostExecute.
 */
- (void) onPostExecute:(bool) result;

/**
 * Execute AsyncTask.
 */
- (void) execute;

@end