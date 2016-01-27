<#assign curr = entities[current_entity] />
<@header?interpret />

#import "${curr.name}SQLiteAdapterBase.h"


/**
 * ${curr.name} adapter database class. 
 * This class will help you access your database to do any basic operation you
 * need. 
 * Feel free to modify it, override, add more methods etc.
 */
@interface ${curr.name}SQLiteAdapter : ${curr.name}SQLiteAdapterBase

@end