<#if property != "serverId" && property != "sync_uDate" && property != "sync_dTag" && property != "uuid">
     /**
     * Get the ${property?cap_first}.
     * @return the ${property}
     */
    public <#if property_type=="STRING">String<#else>${property_type}</#if> <#if property_type?lower_case=="boolean">is<#else>get</#if>${property?cap_first}() {
         return this.${property};
    }</#if>