<#assign curr = entities[current_entity] />
<@header?interpret />
package ${project_namespace}.provider.contract;

import ${project_namespace}.provider.contract.base.${curr.name?cap_first}ContractBase;

/** ${project_name?cap_first} ${curr.name} entity contract. */
public final class ${curr.name}Contract extends ${curr.name}ContractBase {
	public final class ${curr.name} extends ${curr.name?cap_first}ContractBase.${curr.name}Base {

	}
}
