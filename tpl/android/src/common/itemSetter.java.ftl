    /**
     * Set the ${property?cap_first}.
     * @param value the ${property} to set
     */
    public void set${property?cap_first}(final <#if property_type=="STRING">String<#else>${property_type}</#if> value) {
         this.${property} = value;
    }
