<#include utilityPath + "all_imports.ftl" />

<@header?interpret />

#import "ContentResolver.h"
<#list entities?values as entity>
    <#if ((entity.fields?size>0) && !(entity.internal))>
#import "${entity.name?cap_first}Contract.h"
    </#if>
</#list>

@implementation ContentResolver

<#list entities?values as entity>
    <#if ((entity.fields?size>0) && !(entity.internal))>
NSString * const ${entity.name?upper_case}_URI= @"${entity.name?lower_case}Uri";
    </#if>
</#list>

+ (id) getContentResolver {
    static ContentResolver *instance = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        instance = [self new];
    });
    
    return instance;
}

- (void) notifyUri:(NSString *) uri {
    @try {<#list entities?values as entity> <#if ((entity.fields?size>0) && !(entity.internal) && (entity_index == 0))>
        if ([uri isEqual:${entity.name}Contract.TABLE_NAME]) {
            self.${entity.name?uncap_first}Uri = 1;
        }<#elseif ((entity.fields?size>0) && !(entity.internal))> else if ([uri isEqual:${entity.name}Contract.TABLE_NAME]) {
            self.${entity.name?uncap_first}Uri = 1;
        }</#if></#list>
    } @catch (NSException *e) {
        NSLog(@"%@", e);
    }
}

@end