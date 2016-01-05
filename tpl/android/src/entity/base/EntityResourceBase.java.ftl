<@header?interpret />
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

package ${entity_namespace}.base;

<#if rest>
import ${entity_namespace}.base.RestResource;
<#else>
import ${entity_namespace}.base.Resource;
</#if>

public class EntityResourceBase <#if sync> extends EntityBase </#if>implements <#if rest==true> RestResource<#else>Resource</#if> {

    private String path;

    <#if rest>
    private Integer resourceId;

    private String localPath;

    @Override
    public Integer getResourceId() {
         return this.resourceId;
    }

    @Override
    public void setResourceId(final Integer value) {
         this.resourceId = value;
    }

    @Override
    public String getLocalPath() {
         return this.localPath;
    }

    @Override
    public void setLocalPath(final String value) {
         this.localPath = value;
    }
    </#if>

    @Override
    public String getPath() {
         return this.path;
    }

    @Override
    public void setPath(final String value) {
         this.path = value;
    }

}