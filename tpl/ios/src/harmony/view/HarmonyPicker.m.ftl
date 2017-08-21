<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "HarmonyPicker.h"

@interface HarmonyPicker () {
    @private
    NSArray *data;
    UITextField *textField;
}

@end

@implementation HarmonyPicker

+ (void) bindPicker:(NSArray *) pickerData withTextField:(UITextField *) textField {
    HarmonyPicker *picker = [[HarmonyPicker alloc] initWithData:pickerData withTextField:textField];

    picker.autoresizingMask = UIViewAutoresizingFlexibleWidth;

    [picker prepareDatePicker];
}

- (instancetype) initWithData:(NSArray *) pickerData withTextField:(UITextField *) currentTextField {
    if (self = [super init]) {

        self->textField = currentTextField;
        self->data = pickerData;
    }

    return self;
}

- (void) prepareDatePicker {
    [self setDelegate:self];
    [self setDataSource:self];

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

- (NSInteger) pickerView:(UIPickerView *) pickerView numberOfRowsInComponent:(NSInteger) component {
    return self->data.count;
}

- (NSInteger) numberOfComponentsInPickerView:(UIPickerView *) pickerView {
    return 1;
}

- (NSString *) pickerView:(UIPickerView *) pickerView titleForRow:(NSInteger) row forComponent:(NSInteger) component {
    return self->data[row];
}

- (void) pickerView:(UIPickerView *) pickerView didSelectRow:(NSInteger) row inComponent:(NSInteger) component {
    self->textField.text = self->data[row];
}

- (void) onClickDone {
    [self->textField resignFirstResponder];
}

@end