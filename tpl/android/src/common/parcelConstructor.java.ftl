<#assign curr = entities[current_entity] />
	public ${curr.name}(Parcel in) {
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">
		this.${field.name} = in.readInt();
					<#elseif field.type == "String">
		this.${field.name} = in.readString();
					<#elseif field.type?lower_case == "datetime">
		this.${field.name} = new DateTime(in.readString());
					</#if>
				<#else>
					<#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
		this.set${field.name?cap_first}((${field.type}) in.readParcelable(${field.type}.class.getClassLoader()));
					</#if>
				</#if>
			</#if>
		</#list>
	}
