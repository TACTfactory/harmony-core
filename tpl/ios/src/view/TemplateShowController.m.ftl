<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

#import "${curr.name?cap_first}ShowViewController.h"
<#if curr.editAction>
#import "${curr.name?cap_first}EditViewController.h"
</#if>
#import "DateUtils.h"
<#assign allRelations = curr.relations />
<#if (singleTabInheritance && curr.inheritance.superclass??) && entities[curr.inheritance.superclass.name]??>
    <#assign allRelations = allRelations + entities[curr.inheritance.superclass.name].relations />
</#if>
<#list allRelations as relation>
#import "${relation.relation.targetEntity?cap_first}.h"
</#list>

@interface ${curr.name?cap_first}ShowViewController ()

@end

@implementation ${curr.name?cap_first}ShowViewController

- (void) viewDidLoad {
    [super viewDidLoad];

    self.navigationItem.title = [NSString stringWithFormat:@"%d", self.model.id];
    <#if curr.editAction>
    UIBarButtonItem *editButton = [[UIBarButtonItem alloc] initWithTitle:@"Edit"
                                                                   style:UIBarButtonItemStylePlain
                                                                  target:self
                                                                  action:@selector(onClickEdit)];
    self.navigationItem.rightBarButtonItem = editButton;
    </#if>

    [self loadData];
}

- (void) loadData {
    if (self.model != nil) {
    <#list fields?values as field>
        <#if (!field.internal && !field.hidden)>
            <#if (field.harmony_type?lower_case == "boolean")>
        [self.${field.name}Switch setOn:self.model.${field.name}];
            <#else>
${AdapterUtils.loadDataShowFieldAdapter(field, 2)}
            </#if>
        </#if>
    </#list>
    }
}

- (void) onClickEdit {
    ${curr.name?cap_first}EditViewController *editViewController = [${curr.name?cap_first}EditViewController new];
    editViewController.model = self.model;
    editViewController.delegate = self;
    [self.navigationController pushViewController:editViewController animated:true];
}

- (void) refresh${curr.name?cap_first}:(${curr.name?cap_first} *) item {
    self.model = item;
    [self loadData];
}

@end