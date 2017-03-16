<#assign curr = entities[current_entity] />
<@header?interpret />
package ${project_namespace}.provider;

import ${project_namespace}.provider.base.${curr.name?cap_first}ProviderAdapterBase;
import ${project_namespace}.provider.base.${project_name?cap_first}ProviderBase;

/**
 * ${curr.name?cap_first}ProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ${curr.name?cap_first}ProviderAdapter
                    extends ${curr.name?cap_first}ProviderAdapterBase {

    /**
     * Constructor.
     * @param provider Android application provider of ${project_name?cap_first}
     */
    public ${curr.name?cap_first}ProviderAdapter(
            final ${project_name?cap_first}ProviderBase provider) {
        super(provider);
    }
}

