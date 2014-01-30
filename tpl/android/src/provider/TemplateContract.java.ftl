<@header?interpret />
package ${project_namespace}.provider;

import ${project_namespace}.provider.base.${project_name?cap_first}ContractBase;

/** ${project_name?cap_first} contract. */
public final class ${project_name?cap_first}Contract extends ${project_name?cap_first}ContractBase {
	<#list entities?values as curr>
		<#if (curr.fields?size > 0 || curr.inheritance??)>
	public final class ${curr.name} extends ${project_name?cap_first}ContractBase.${curr.name}Base {

	}
		</#if>
	</#list>
}
