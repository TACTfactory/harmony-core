<#assign curr = entities[current_entity] />
	public ${curr.name}(Parcel in) {
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">
		this.${field.name} = in.readInt();
					<#elseif field.type == "String">
		this.${field.name} = in.readString();
					</#if>
				<#else>

				</#if>
			</#if>
		</#list>
	}
