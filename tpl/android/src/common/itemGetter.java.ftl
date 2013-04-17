	/**
	 * @return the ${property}
	 */
	public ${property_type} <#if property_type=="boolean">is<#else>get</#if>${property?cap_first}() {
	     return this.${property};
	}
