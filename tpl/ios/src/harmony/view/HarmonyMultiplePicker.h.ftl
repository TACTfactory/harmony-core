<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <UIKit/UIKit.h>

@interface HarmonyPicker : UITableViewController

- (instancetype) initWithData:(NSArray *) pickerData withCallback:(void(^)(NSArray *result)) callback;

@end
