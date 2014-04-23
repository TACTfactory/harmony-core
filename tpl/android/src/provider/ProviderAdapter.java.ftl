<@header?interpret />
package ${local_namespace};

import ${local_namespace}.base.ProviderAdapterBase;

import android.content.Context;

/**
 * ProviderAdapter<T>. 
 *
 * Feel free to add your custom generic methods here.
 *
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapter<T> extends ProviderAdapterBase<T> {

	/**
	 * Provider Adapter Base constructor.
	 *
	 * @param context The context.
	 */
	public ProviderAdapter(final Context context) {
		super(context);
	}
}
