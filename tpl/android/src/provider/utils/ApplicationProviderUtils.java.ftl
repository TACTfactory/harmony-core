<@header?interpret />
package ${project_namespace}.provider.utils;

import ${project_namespace}.provider.utils.base.ProviderUtilsBase;



/**
 * Generic Proxy class for the provider calls.
 *
 * Feel free to modify it and your own generic methods in it.
 *
 * @param <T>	 The entity type
 */
public abstract class ProviderUtils<T> extends ProviderUtilsBase<T> {

	/**
	 * Constructor.
	 * @param context android.content.Context
	 */
	public ProviderUtils(android.content.Context context) {
		super(context);
	}
}

