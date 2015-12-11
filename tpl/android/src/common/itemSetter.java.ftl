    /**
     * @param value the ${property} to set
     */
<#if (entity_resource)>
    <#if property?lower_case=="path"|| property?lower_case == "localpath" || property?lower_case == "resourceid">@Override</#if>
</#if>
    public void set${property?cap_first}(final ${property_type} value) {
         this.${property} = value;
    }
