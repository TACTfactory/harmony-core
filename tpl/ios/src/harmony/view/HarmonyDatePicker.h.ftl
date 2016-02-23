<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <UIKit/UIKit.h>

@interface HarmonyDatePicker : UIDatePicker

- (instancetype) initWithType:(UIDatePickerMode) datePickerMode withTextField:(UITextField *) currentTextField;

@end
