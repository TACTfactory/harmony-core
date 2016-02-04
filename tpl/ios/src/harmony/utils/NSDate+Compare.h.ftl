<@header?interpret />

#import <Foundation/Foundation.h>

@interface NSDate (Compare)

/**
 * Check if the date is after or equal to given date.
 * @param date The NSDate
 * @return true if it is
 */
- (bool) isAfterOrEqualTo:(NSDate *) date;

/**
 * Check if the date is before or equal to given date.
 * @param date The NSDate
 * @return true if it is
 */
- (bool) isBeforeOrEqualTo:(NSDate *) date;

/**
 * Check if the date is after the given date.
 * @param date The NSDate
 * @return true if it is
 */
- (bool) isAfter:(NSDate *) date;

/**
 * Check if the date is before the given date.
 * @param date The NSDate
 * @return true if it is
 */
- (bool) isBefore:(NSDate *) date;

@end