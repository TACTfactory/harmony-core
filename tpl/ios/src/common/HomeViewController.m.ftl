<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "HomeViewController.h"
<#if (entities??)>
    <#list entities?values as entity>
        <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
#import "${entity.name?cap_first}TableViewController.h"
        </#if>
    </#list>
</#if>

@interface HomeViewController ()

@end

@implementation HomeViewController

- (void) viewDidLoad {
    [super viewDidLoad];
}

- (IBAction) onClickButton:(id) sender {
    UIViewController *controller = nil;

    switch ([sender tag]) {
    <#assign tag = 1 />
    <#if (entities??)>
        <#list entities?values as entity>
            <#if (entity.fields?? && (entity.fields?size>0 || entity.inheritance??) && !entity.internal && entity.listAction)>
        case ${tag}:
            controller = [${entity.name?cap_first}TableViewController new];
            break;
            <#assign tag = tag + 1 />
            </#if>
        </#list>
    </#if>

        default:
            break;
    }

    if (controller != nil) {
        [self.navigationController pushViewController:controller animated:true];
    }
}

@end