<@header?interpret />

#import <Foundation/Foundation.h>

@interface ContentResolver : NSObject

/** URIs to watch. */
<#list entities?values as entity>
    <#if ((entity.fields?size>0) && !(entity.internal))>
FOUNDATION_EXPORT NSString * const ${entity.name?upper_case}_URI;
    </#if>
</#list>

/** Properties observable. */
<#list entities?values as entity>
    <#if ((entity.fields?size>0) && !(entity.internal))>
@property int ${entity.name?uncap_first}Uri;
    </#if>
</#list>

@property NSMutableDictionary* observers;

/**
 * Get the content resolver.
 * @return The ContentResolver
 */
+ (id) getContentResolver;

/**
 * Notify the uri.
 * @param uri The uri to notify
 */
- (void) notifyUri:(NSString*) uri;

@end