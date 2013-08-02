<#assign curr = entities[current_entity] />	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">
		dest.writeInt(this.${field.name});
					<#elseif field.type == "String">
		dest.writeString(this.${field.name});
					</#if>
				<#else>
				</#if>
			</#if>
		</#list>
	}
