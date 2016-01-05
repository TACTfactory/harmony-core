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
    <#if rest>
    /**
     * @return the resourceId
     */
    Integer getResourceId();

    /**
     * @param value the resourceId to set
     */
    void setResourceId(final Integer value);
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