<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "HarmonyDatePicker.h"
#import "DateUtils.h"

@interface HarmonyDatePicker () {
    @private
    UITextField *textField;
}

@end

@implementation HarmonyDatePicker


+ (void) bindPicker:(UIDatePickerMode) datePickerMode withTextField:(UITextField *) textField {
    HarmonyDatePicker *datePicker = [[HarmonyDatePicker alloc] initWithType:datePickerMode withTextField:textField];

    [datePicker addTarget:datePicker action:@selector(onChangeDate) forControlEvents:UIControlEventValueChanged];
    [datePicker prepareDatePicker];
}

- (instancetype) initWithType:(UIDatePickerMode) datePickerMode withTextField:(UITextField *) currentTextField {
    if (self = [super init]) {

        self.datePickerMode = datePickerMode;

        self->textField = currentTextField;
    }

    return self;
}

- (void) prepareDatePicker {
    UIToolbar *toolbar = [UIToolbar new];
    toolbar.frame = CGRectMake(0, 75, 180, 35);

    UIBarButtonItem *flexibleSpaceLeft = [[UIBarButtonItem alloc]
                                          initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace
                                          target:nil
                                          action:nil];
    UIBarButtonItem *doneButton = [[UIBarButtonItem alloc] initWithTitle:@"Done"
                                                                   style:UIBarButtonItemStyleDone
                                                                  target:self
                                                                  action:@selector(onClickDone)];

    [toolbar setItems:[NSArray arrayWithObjects:flexibleSpaceLeft, doneButton, nil]];

    self->textField.inputView = self;
    self->textField.inputAccessoryView = toolbar;
}

- (void) onClickDone {
    [self->textField resignFirstResponder];
}

- (void) onChangeDate {
    self->textField.text = [DateUtils dateToISOString:self.date];
}

@end