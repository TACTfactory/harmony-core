<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "HarmonyMultiplePicker.h"

@interface HarmonyMultiplePicker () {
    @private
    NSArray *data
    void (^resultCallback)(NSArray *result);
    NSMutableArray *selectedElement;
}

@end

@implementation HarmonyMultiplePicker

- (instancetype) initWithData:(NSArray *) pickerData withCallback:(void(^)(NSArray *result)) callback;
    if (self = [super init]) {

        self->data = pickerData;
        self->resultCallback = callback;
    }

    return self;
}

- (void) viewDidLoad {
    [super viewDidLoad];

    self->selectedElement = [NSMutableArray new];
    [self.tableView setAllowMultipleSelection:true];

    UIBarButtonItem *doneButton = [[UIBarButtonItem alloc] initWithTitle:@"Done"
                                                                   style:UIBarButtonItemStyleDone
                                                                  target:self
                                                                  action:@selector(onClickDone)];

    self.navigationItem.rightBarButtonItem = doneButton;
}

- (NSInteger) tableView:(UITableView *) tableView cellForRowAtIndexPath:(NSIndexPath *) indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"checkmarkCell"];

    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"checkmarkCell"];
    }

    cell.textLabel.text = self->data[indexPath.item];

    if ([self->selectedElement containsObject:self->data[indexPath.item]]) {
        cell.accessoryType = UITableViewCellAccessoryCheckmark;
    } else {
        cell.accessoryType = UITableViewCellAccessoryNone;
    }

    return cell;
}

- (void) tableView:(UITableView *) tableView didSelectRowAtIndexPath:(NSIndexPath *) indexPath {
    [self->selectedElement addObject:self->data[indexPath.item]];
    [tableView cellForRowAtIndexPath:indexPath].accessoryType = UITableViewCellAccessoryCheckmark;
}

- (void) tableView:(UITableView *) tableView didDeselectRowAtIndexPath:(NSIndexPath *) indexPath {
    [self->selectedElement removeObject:self->data[indexPath.item]];
    [tableView cellForRowAtIndexPath:indexPath].accessoryType = UITableViewCellAccessoryNone;
}

- (void) onClickDone {
    self->resultCallback(selectedElement);
    [self.navigationController popViewControllerAnimated:true];
}

@end