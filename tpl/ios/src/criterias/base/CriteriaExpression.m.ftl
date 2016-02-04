<@header?interpret />

#import "CriteriaExpression.h"
#import "StringValue.h"

/**
 * CriteriaExpression.
 * An array of Criterion and CriteriaExpression. Used for db requests.
 * A CriteriaExpression represents the WHERE clause of a request.
 * @param T The entity type
 */
@implementation CriteriaExpression {
    /** Expression GroupType. */
    GroupType type;

    /** Array of ICriteria. */
    NSMutableArray *criteria;
}

/** KEY constant for serializing a criteria expression inside a bundle. */
const NSString *PARCELABLE = @"CriteriaExpression";

- (id) init {
    if (self = [super init]) {
        self->criteria = [NSMutableArray array];
    }

    return self;
}

- (id) initWithType:(GroupType)initType {
    if (self = [self init]) {
        self->type = initType;
    }

    return self;
}

- (NSString *) toSQLiteString {
    NSMutableString *result = [NSMutableString stringWithString:@"("];

    for (int i = 0; i < self->criteria.count; i++) {
        Criteria *crit = self->criteria[i];
        
        [result appendString:[crit toSQLiteString]];
        
        if (i != self->criteria.count - 1) {
            [result appendString:@" "];
            [result appendString:[CriteriaExpression typeName:self->type]];
            [result appendString:@" "];
        }
    }

    [result appendString:@")"];

    return result;
}

- (NSString *) toSQLiteSelection {
    NSMutableString *result = nil;

    if (self->criteria.count > 0) {
        result = [NSMutableString stringWithString:@"("];

        for (int i = 0; i < self->criteria.count; i++) {
            Criteria *crit = self->criteria[i];

            [result appendString:[crit toSQLiteSelection]];

            if (i != self->criteria.count - 1) {
                [result appendString:@" "];
                [result appendString:[CriteriaExpression typeName:self->type]];
                [result appendString:@" "];
            }
        }

        [result appendString:@")"];
    }

    return result;
}

- (void) toSQLiteSelectionArgs:(NSMutableArray *) array {
    for (int i = 0; i < self->criteria.count; i++) {
        Criteria *crit = self->criteria[i];
        [crit toSQLiteSelectionArgs:array];
    }
}

- (NSArray *) toSQLiteSelectionArgs {
    NSMutableArray *result = nil;

    if (self->criteria.count > 0) {
        result = [NSMutableArray array];
        [self toSQLiteSelectionArgs:result];
    }

    return result;
}


- (bool) addWithCriteria:(Criteria *) crit {
    bool result;

    if (![self->criteria containsObject:crit]) {
        [self->criteria addObject:crit];
        result = true;
    } else {
        result = false;
    }

    return result;
}

- (bool) addWithKey:(NSString *) key withValue:(NSString *) value withType:(Type) criteriaType {
    Criterion *criterion = [Criterion new];
    [criterion setKey:key];
    [criterion addValue:[[StringValue alloc] initWithValue:value]];
    [criterion setType:criteriaType];

    return [self addWithCriteria:criterion];
}

- (bool) addWithKey:(NSString *) key withValue:(NSString *) value {
    return [self addWithKey:key withValue:value withType:EQUALS];
}

+ (NSDictionary *) typeNames {
    static NSDictionary *names = nil;
    static dispatch_once_t onceToken;

    dispatch_once(&onceToken, ^{
        names = @{
                  /** Equals "=". */
                  @(AND) : @"AND",
                  /** Greater than ">". */
                  @(OR) : @"OR"
                  };
    });

    return names;
}

+ (NSString *) typeName:(GroupType) type {
    return [CriteriaExpression typeNames][@(type)];
}

- (bool) isEmpty {
    return self->criteria.count == 0;
}

@end

