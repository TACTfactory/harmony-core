<@header?interpret />

#import "Criterion.h"

@implementation Criterion {

    /** Criteria key. */
    NSString* key;

    /** Criteria value. */
    CriteriaValue *value;

    /** Criteria Type. */
    Type type;
}

- (NSString *) toSQLiteString {
    return [NSString stringWithFormat:@"(%@ %@ %@)",
            self->key,
            [Criterion typeName:self->type],
            [self->value toSQLiteString]];
}

- (NSString *) toSQLiteSelection {
    return [NSString stringWithFormat:@"(%@ %@ %@)",
            self->key,
            [Criterion typeName:self->type],
            [self->value toSQLiteSelection]];
}

- (void) toSQLiteSelectionArgs:(NSMutableArray *) array {
    [self->value toSQLiteSelectionArgs:array];
}

- (NSString *) getKey {
    return self->key;
}

- (CriteriaValue *) getValue; {
    return self->value;
}

- (void) setKey:(NSString *) newKey {
    self->key = newKey;
}

- (void) setDateTimeKey:(NSString *) key withOptions:(NSArray *) options {
    
}

- (void) setDateKey:(NSString *) key withOptions:(NSArray *) options {
    
}

- (void) setTimeKey:(NSString *) key withOptions:(NSArray *) options {
    
}

- (void) setStrtfKey:(NSString *) format withKey:(NSString *) key withOptions:(NSArray *) options; {
    
}

- (void) setMethodKey:(NSString *) methodName withOptions:(NSArray *) options {
    
}

- (void) addValue:(CriteriaValue *) newValue {
    self->value = newValue;
}

- (void) setType:(Type) newType {
    self->type = newType;
}

- (Type) getType {
    return self->type;
}

- (BOOL) isEqual:(id) obj {
    bool result = true;

    if (obj == nil) {
        result = false;
    } else if (self.class != [obj class]) {
        result = false;
    }

    if (result) {
        Criterion *other = (Criterion*) obj;
        
        if (self->key == nil) {
            if (other->key != nil) {
                result = false;
            }

        } else if (![self->key isEqual:other->key]) {
            result = false;
        } else if (self->type != other->type) {
            result = false;
        } else if (self->value == nil) {
            if (other->value != nil) {
                result = false;
            }

        } else if (![self->value isEqual:other->value]) {
            result = false;
        }
    }

    return result;
}

+ (NSDictionary *) typeNames {
    static NSDictionary *names = nil;
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        names = @{
                  /** Equals "=". */
                  @(EQUALS) : @"=",
                  /** Greater than ">". */
                  @(SUPERIOR) : @">",
                  /** Smaller than "<". */
                  @(INFERIOR) : @"<",
                  /** Inferior or equal "<=". */
                  @(INFERIOR_EQUALS) : @"<=",
                  /** Superior or equals ">=". */
                  @(SUPERIOR_EQUALS) : @">=",
                  /** Like "LIKE". */
                  @(LIKE) : @"LIKE",
                  /** IN "IN" (May be used with ArrayValue or SelectValue). */
                  @(IN) : @"IN",
                  /** NOT IN "NOT IN" (May be used with ArrayValue or SelectValue). */
                  @(NOT_IN) : @"NOT IN",
                  /** IS "IS". */
                  @(IS) : @"IS",
                  /** IS NULL. WARNING : Use null Value */
                  @(IS_NULL) : @"IS NULL",
                  /** IS NOT NULL. WARNING : Use null Value */
                  @(IS_NOT_NULL) : @"IS NOT NULL",
                  /** Different. <>*/
                  @(DIFFERENT) : @"<>"
                  };
    });

    return names;
}

+ (NSString *) typeName:(Type) type {
    return [Criterion typeNames][@(type)];
}

@end
