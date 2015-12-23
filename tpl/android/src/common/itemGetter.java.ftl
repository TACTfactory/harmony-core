    /**
     * @return the ${property}
     */
    public ${property_type} <#if property_type?lower_case=="boolean">is<#else>get</#if>${property?cap_first}() {
         return this.${property};
    }

