<#assign curr = entities[current_entity] />
<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import <UIKit/UIKit.h>

@interface ${curr.name?cap_first}TableViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (strong, nonatomic) IBOutlet UITableView *${curr.name?lower_case}TableView;

@end