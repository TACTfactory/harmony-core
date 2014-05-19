<#assign curr = entities[current_entity] />
<@header?interpret />
package ${project_namespace}.provider.utils;



import ${project_namespace}.provider.utils.base.${curr.name?cap_first}ProviderUtilsBase;

/**
 * ${curr.name?cap_first} Provider Utils.
 *
 * This class is an utility class for wrapping provider calls.
 * Feel free to modify it, add new methods to it, etc.
 */
public class ${curr.name?cap_first}ProviderUtils
	extends ${curr.name?cap_first}ProviderUtilsBase {

	/**
	 * Constructor.
	 * @param context The context
	 */
	public ${curr.name?cap_first}ProviderUtils(android.content.Context context) {
		super(context);
	}

}
