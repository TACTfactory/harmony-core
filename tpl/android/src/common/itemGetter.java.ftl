    /**
     * @return the ${property}
     */
<#if (entity_resource)>
    <#if property?lower_case=="path" || property?lower_case == "localpath" || property?lower_case == "resourceid">@Override</#if>
</#if>    public ${property_type} <#if property_type?lower_case=="boolean">is<#else>get</#if>${property?cap_first}() {
         return this.${property};
    }

