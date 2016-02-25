<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

#import <UIKit/UIKit.h>
#import "${curr.name?cap_first}.h"

@protocol ${curr.name?cap_first}Delegate <NSObject>

- (void) refresh${curr.name?cap_first}:(${curr.name?cap_first} *) item;

@end

@interface ${curr.name?cap_first}EditViewController : UIViewController <UITextFieldDelegate>

@property (strong, nonatomic) ${curr.name?cap_first} *model;

<#list fields?values as field>
    <#if (!field.internal && !field.hidden)>
        <#if (field.harmony_type?lower_case == "boolean")>
@property (strong, nonatomic) IBOutlet UISwitch *${field.name}Switch;
        <#else>
@property (strong, nonatomic) IBOutlet UITextField *${field.name}TextField;
        </#if>
    </#if>
</#list>

@property (nonatomic, assign) id delegate;

@end