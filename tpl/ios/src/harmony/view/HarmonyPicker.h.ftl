<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <UIKit/UIKit.h>

@interface HarmonyPicker : UIPickerView <UIPickerViewDataSource, UIPickerViewDelegate>

+ (void) bindPicker:(NSArray *) pickerData withTextField:(UITextField *) textField;

@end
