<#assign curr = entities[current_entity] />
<@header?interpret />
package ${project_namespace}.provider.utils;

import android.content.Context;

import ${project_namespace}.provider.utils.base.${curr.name?cap_first}ProviderUtilsBase;

/**
 * ${curr.name?cap_first} Provider Utils.
 */
public class ${curr.name?cap_first}ProviderUtils 
	extends ${curr.name?cap_first}ProviderUtilsBase {

	/**
	 * Constructor.
	 * @param context The context
	 */
	public ${curr.name?cap_first}ProviderUtils(Context context) {
		super(context);
	}

}
