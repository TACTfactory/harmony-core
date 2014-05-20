<@header?interpret />
package ${project_namespace}.provider;

import ${project_namespace}.provider.base.ProviderAdapterBase;
import ${project_namespace}.provider.base.${project_name?cap_first}ProviderBase;
import ${project_namespace}.data.base.SQLiteAdapterBase;

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
    public ProviderAdapter(
                final ${project_name?cap_first}ProviderBase provider,
                final SQLiteAdapterBase<T> adapter) {
        super(provider, adapter);
    }
}
