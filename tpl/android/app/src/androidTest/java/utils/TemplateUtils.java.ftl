<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.test_namespace}.utils;

import ${curr.test_namespace}.utils.base.${curr.name?cap_first}UtilsBase;

public abstract class ${curr.name?cap_first}Utils extends ${curr.name?cap_first}UtilsBase {

}
