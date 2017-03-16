<#assign sync=false />
<#assign rest=false />

<#list entities?values as entity>
    <#if entity.options.rest??>
        <#assign rest=true />
    </#if>
    <#if entity.options.sync??>
        <#assign sync=true />
    </#if>
</#list>

<@header?interpret />
package ${entity_namespace}.base;

import java.io.Serializable;

import org.joda.time.DateTime;

public interface Resource {
    <#if !sync>
    /**
     * @return the id
     */
    int getId();

    /**
     * @param value the id to set
     */
    void setId(final int value);
    </#if>

    /**
     * @return the path
     */
    String getPath();

    /**
     * @param value the path to set
     */
    void setPath(final String value);
}