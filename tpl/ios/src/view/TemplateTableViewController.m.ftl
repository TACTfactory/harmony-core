<#assign curr = entities[current_entity] />
<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "${curr.name?cap_first}TableViewController.h"
#import "${curr.name?cap_first}SQLiteAdapter.h"
<#if curr.showAction>
#import "${curr.name?cap_first}ShowViewController.h"
</#if>
<#if curr.createAction>
#import "${curr.name?cap_first}CreateViewController.h"
</#if>

static NSString *CELL_IDENTIFIER = @"${curr.name?lower_case}Identifier";

@interface ${curr.name?cap_first}TableViewController () {
    @private
    NSMutableArray *${curr.name?lower_case}Array;
}

@end

@implementation ${curr.name?cap_first}TableViewController

- (void) viewDidLoad {
    [super viewDidLoad];

    self.navigationItem.title = @"${curr.name?cap_first}";
    <#if curr.createAction>
    UIBarButtonItem *createButton = [[UIBarButtonItem alloc] initWithTitle:@"Add"
                                                                     style:UIBarButtonItemStylePlain
                                                                    target:self
                                                                    action:@selector(onClickCreate)];
    self.navigationItem.rightBarButtonItem = createButton;
    </#if>

    [self loadData];
}

- (void) viewDidAppear:(BOOL) animated {
    [super viewDidAppear:animated];

    [self loadData];
}

- (void) loadData {
    self->${curr.name?lower_case}Array = [NSMutableArray arrayWithArray:[[${curr.name?cap_first}SQLiteAdapter new] getAll]];

    dispatch_async(dispatch_get_main_queue(), ^{
        [self.${curr.name?lower_case}TableView reloadData];
    });
}

- (NSInteger) tableView:(UITableView *) tableView numberOfRowsInSection:(NSInteger) section {
    return self->${curr.name?lower_case}Array.count;
}

- (UITableViewCell *) tableView:(UITableView *) tableView cellForRowAtIndexPath:(NSIndexPath *) indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CELL_IDENTIFIER];

    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleSubtitle reuseIdentifier:CELL_IDENTIFIER];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
    }

    ${curr.name?cap_first} *current = [self->${curr.name?lower_case}Array objectAtIndex:indexPath.item];

    cell.textLabel.text = [NSString stringWithFormat:@"%d", current.id];

    return cell;
}
<#if curr.showAction>

- (void) tableView:(UITableView *) tableView didSelectRowAtIndexPath:(NSIndexPath *) indexPath {
    ${curr.name?cap_first}ShowViewController *showViewController = [${curr.name?cap_first}ShowViewController new];
    showViewController.model = (${curr.name?cap_first} *) [self->${curr.name?lower_case}Array objectAtIndex:indexPath.item];

    [self.navigationController pushViewController:showViewController animated:true];
}
</#if>

<#if curr.deleteAction>
- (void) tableView:(UITableView *) tableView
commitEditingStyle:(UITableViewCellEditingStyle) editingStyle
 forRowAtIndexPath:(NSIndexPath *) indexPath {

    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [[${curr.name?cap_first}SQLiteAdapter new] remove:(${curr.name?cap_first} *) [self->${curr.name?lower_case}Array objectAtIndex:indexPath.item]];
        [self->${curr.name?lower_case}Array removeObjectAtIndex:indexPath.item];
        [self.${curr.name?lower_case}TableView reloadData];
    }
}

</#if>
<#if curr.createAction>
- (void) onClickCreate {
    ${curr.name?cap_first}CreateViewController *createViewController = [${curr.name?cap_first}CreateViewController new];

    [self.navigationController pushViewController:createViewController animated:true];
}
</#if>

@end