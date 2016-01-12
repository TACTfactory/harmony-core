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

    protected String path;

    <#if !sync>
    private int id;
    </#if>

    <#if (sync) || (rest)>
    private String localPath;
    </#if>

    <#if !sync>
    @Override
    public int getId() {
         return this.id;
    }

    @Override
    public void setId(final int value) {
         this.id = value;
    }
    </#if>

    <#if (sync) || (rest)>
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