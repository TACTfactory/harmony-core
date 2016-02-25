<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign fields = ViewUtils.getAllFields(curr) />
<@header?interpret />

#import "${curr.name?cap_first}EditViewController.h"
#import "DateUtils.h"
#import "HarmonyPicker.h"
#import "HarmonyDatePicker.h"
#import "HarmonyMultiplePicker.h"
#import "EnumUtils.h"
<#list curr.relations as relation>
    <#if relation.relation.targetEntity != curr.name>
#import "${relation.relation.targetEntity?cap_first}.h"
#import "${relation.relation.targetEntity?cap_first}SQLiteAdapter.h"
    </#if>
</#list>
#import "${curr.name?cap_first}SQLiteAdapter.h"

@interface ${curr.name?cap_first}EditViewController () {
    @private
    <#list curr.relations as relation>
        <#if relation.relation.targetEntity != curr.name>
    ${relation.relation.targetEntity?cap_first}SQLiteAdapter *${relation.relation.targetEntity?lower_case}SQLiteAdapter;
        </#if>
    </#list>
    ${curr.name?cap_first}SQLiteAdapter *${curr.name?lower_case}SQLiteAdapter;
}

@end

@implementation ${curr.name?cap_first}EditViewController

- (void) viewDidLoad {
    [super viewDidLoad];

    self.navigationItem.title = [NSString stringWithFormat:@"%d", self.model.id];
    UIBarButtonItem *saveButton = [[UIBarButtonItem alloc] initWithTitle:@"Save"
                                                                   style:UIBarButtonItemStylePlain
                                                                  target:self
                                                                  action:@selector(onClickSave)];
    self.navigationItem.rightBarButtonItem = saveButton;

    <#list curr.relations as relation>
    self->${relation.relation.targetEntity?lower_case}SQLiteAdapter = [${relation.relation.targetEntity?cap_first}SQLiteAdapter new];
    </#list>
    self->${curr.name?lower_case}SQLiteAdapter = [${curr.name?cap_first}SQLiteAdapter new];

    [self loadData];
}

- (void) loadData {
    if (self.model != nil) {
    <#list fields?values as field>
        <#if (!field.internal && !field.hidden)>
            <#if (field.harmony_type?lower_case == "boolean")>
        [self.${field.name}Switch setOn:self.model.${field.name}];
            <#else>
${AdapterUtils.loadDataCreateFieldAdapter(field, 2)}
            </#if>
            <#if (field.harmony_type?lower_case == "relation")>
                <#if (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
        NSMutableArray *${field.name}Ids = [NSMutableArray new];
        NSArray *${field.name}s = [self->${field.relation.targetEntity?lower_case}SQLiteAdapter getAll];

        for (${field.relation.targetEntity?cap_first} *item in ${field.name}s) {
            [${field.name}Ids addObject:[NSString stringWithFormat:@"%d", item.id]];
        }

        [HarmonyPicker bindPicker:${field.name}Ids withTextField:self.${field.name}TextField];
                <#else>
        self.${field.name}TextField.delegate = true;
                </#if>
            <#elseif (field.harmony_type?lower_case == "datetime")>
        [HarmonyDatePicker bindPicker:UIDatePickerModeDateAndTime withTextField:self.${field.name}TextField];
            <#elseif (field.harmony_type?lower_case == "time")>
        [HarmonyDatePicker bindPicker:UIDatePickerModeTime withTextField:self.${field.name}TextField];
            <#elseif (field.harmony_type?lower_case == "date")>
        [HarmonyDatePicker bindPicker:UIDatePickerModeDate withTextField:self.${field.name}TextField];
            <#elseif (field.harmony_type?lower_case == "enum")>
        [HarmonyPicker bindPicker:[[EnumUtils get${field.name?cap_first}Values] allKeys] withTextField:self.${field.name}TextField];
            </#if>
        </#if>
    </#list>
    }
}

- (void) textFieldDidBeginEditing:(UITextField *) textField {
    <#list fields?values as field>
        <#if (!field.internal && !field.hidden)>
            <#if (field.harmony_type?lower_case == "relation")>
                <#if (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")>
    if (textField == self.${field.name}TextField) {
        NSMutableArray *${field.name}Ids = [NSMutableArray new];
        NSArray *${field.name}s = [self->${field.relation.targetEntity?lower_case}SQLiteAdapter getAll];

        for (${field.relation.targetEntity?cap_first} *item in ${field.name}s) {
            [${field.name}Ids addObject:[NSString stringWithFormat:@"%d", item.id]];
        }

        HarmonyMultiplePicker *${field.name}Picker = [[HarmonyMultiplePicker alloc] initWithData:${field.name}Ids
                                                                                    withCallback:^(NSArray *result) {
            self.${field.name}TextField.text [result componentsJoinedByString:@","];
            NSMutableArray *resultToSet = [NSMutableArray new];

            for (NSString *itemId in result) {
                [resultToSet addObject:[self->${field.relation.targetEntity?lower_case}SQLiteAdapter getByID:[itemId intValue]]];
            }

            self.model.${field.name} = resultToSet;
        }];

        [self.navigationController pushViewController:${field.name}Picker animated:true];

        [textField resignFirstResponder];
    }

                </#if>
            </#if>
        </#if>
    </#list>
}

- (void) onClickSave {
    if ([self validateData]) {
    <#list fields?values as field>
        <#if (!field.internal && !field.hidden)>
            <#if (field.harmony_type?lower_case == "relation")>
                <#if (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
        self.model.${field.name} = [self->${field.relation.targetEntity?lower_case}SQLiteAdapter getByID:[self.${field.name}TextField.text intValue]];
                </#if>
            <#else>
                <#if (field.harmony_type?lower_case == "boolean")>
        self.model.${field.name} = self.${field.name}Switch.isOn;
                <#elseif (field.harmony_type?lower_case == "enum")>
        self.model.${field.name} = [[[EnumUtils get${field.name?cap_first}Values] valueForKey:self.${field.name}TextField.text] intValue];
                <#elseif (field.harmony_type?lower_case == "datetime") || (field.harmony_type?lower_case == "date") || (field.harmony_type?lower_case == "time")>
        self.model.${field.name} = [DateUtils isoStringToDate:self.${field.name}TextField.text];
                <#elseif (field.harmony_type?lower_case == "int") || (field.harmony_type?lower_case == "integer")>
        self.model.${field.name} = [self.${field.name}TextField.text intValue];
                <#elseif (field.harmony_type?lower_case == "short")>
        self.model.${field.name} = (short) [self.${field.name}TextField.text intValue];
                <#elseif (field.harmony_type?lower_case == "byte") || (field.harmony_type?lower_case == "char") || (field.harmony_type?lower_case == "character")>
        self.model.${field.name} = [self.${field.name}TextField.text UTF8String][0];
                <#else>
        self.model.${field.name} = self.${field.name}TextField.text;
                </#if>
            </#if>
        </#if>
    </#list>

        [self->${curr.name?lower_case}SQLiteAdapter update:self.model];

        [self.delegate refresh${curr.name?cap_first}:self.model];

        [self.navigationController popViewControllerAnimated:true];
    }
}

- (bool) validateData {
    bool result = true;
    NSString *error = nil;

<#list fields?values as field>${AdapterUtils.validateDataFieldAdapter(field, 0)}</#list>

    if (error != nil) {
        result = false;

        [[[UIAlertView alloc] initWithTitle:@"Error"
                                    message:error
                                   delegate:nil
                          cancelButtonTitle:@"ok"
                          otherButtonTitles:nil] show];
    }

    return result;
}

@end