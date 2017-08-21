<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <UIKit/UIKit.h>

@interface HarmonyDatePicker : UIDatePicker

+ (void) bindPicker:(UIDatePickerMode) datePickerMode withTextField:(UITextField *) textField;

@end
