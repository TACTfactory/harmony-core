<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

#import <UIKit/UIKit.h>
#import "${curr.name?cap_first}.h"

@interface ${curr.name?cap_first}ShowViewController : UIViewController

@property (strong, nonatomic) ${curr.name?cap_first} *model;

<#list fields?values as field>
    <#if (!field.internal && !field.hidden)>
        <#if (field.harmony_type?lower_case == "boolean")>
@property (strong, nonatomic) IBOutlet UISwitch *${field.name}Switch;
        <#else>
@property (strong, nonatomic) IBOutlet UILabel *${field.name}Label;
        </#if>
    </#if>
</#list>

@end