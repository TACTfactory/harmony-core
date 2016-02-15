<#include utilityPath + "all_imports.ftl" />
<@header?interpret />

#import "ParseValue.h"

@interface ParseValue()

@property (strong, nonatomic) NSString *stringHolder;

@end

@implementation ParseValue

- (instancetype) initWithCharactersNoCopy:(unichar *) characters
                                   length:(NSUInteger) length
                             freeWhenDone:(BOOL) freeBuffer {
    self = [super init];

    if (self) {
        self.stringHolder = [[NSString alloc]
                             initWithCharactersNoCopy:characters
                             length:length
                             freeWhenDone:freeBuffer];
    }

    return self;
}

- (NSUInteger) length {
    return self.stringHolder.length;
}

- (unichar) characterAtIndex:(NSUInteger) index {
    return [self.stringHolder characterAtIndex:index];
}

- (char) charValue {
    const char *c = [self UTF8String];

    return c[0];
}

- (unsigned char) unsignedCharValue {
    return (const unsigned char *)[self charValue];
}

- (short) shortValue {
    return (short)[self intValue];
}

- (unsigned long long) unsignedLongLongValue {
    return [self longLongValue];
}

@end