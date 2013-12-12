<#assign curr = entities[current_entity] />
<@header?interpret />
package ${project_namespace}.criterias;

import ${project_namespace}.criterias.base.CriteriasBase;
import ${project_namespace}.criterias.base.Criteria;

<#if !curr.internal>import ${entity_namespace}.${curr.name?cap_first};</#if>

/**
 * ${curr.name?cap_first}Criterias.
 *
 * This class can help you forge requests for your ${curr.name} Entity.
 * For more details, see CriteriasBase.
 */
public class ${curr.name?cap_first}Criterias extends CriteriasBase<#if !curr.internal><${curr.name?cap_first}></#if> {
	/** String to parcel ${curr.name?uncap_first}Criteria. */
	public static final String PARCELABLE =
			"${curr.name?uncap_first}CriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ${curr.name?cap_first}Criterias(final GroupType type) {
		super(type);
	}
}
