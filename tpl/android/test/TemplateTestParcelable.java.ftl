<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.test_namespace};

import ${curr.test_namespace}.base.${curr.name}TestParcelableBase;

/** ${curr.name} database test class */
public class ${curr.name}TestParcelable extends ${curr.name}TestParcelableBase {

}
