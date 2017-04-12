<@header?interpret />
package ${project_namespace}.provider;

import ${project_namespace}.provider.base.ResourceProviderAdapterBase;
import ${project_namespace}.provider.base.${project_name?cap_first}ProviderBase;

/**
 * ResourceProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ResourceProviderAdapter
                    extends ResourceProviderAdapterBase {

    /**
     * Constructor.
     * @param provider Android application provider of ${project_name?cap_first}
     */
    public ResourceProviderAdapter(
            final ${project_name?cap_first}ProviderBase provider) {
        super(provider);
    }
}

