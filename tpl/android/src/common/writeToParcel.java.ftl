<#assign curr = entities[current_entity] />	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">
		dest.writeInt(this.get${field.name?cap_first}());
					<#elseif field.type == "String">
		dest.writeString(this.get${field.name?cap_first}());
					<#elseif field.type?lower_case == "datetime">
		if (this.get${field.name?cap_first}() != null) {
			dest.writeString(this.get${field.name?cap_first}().toString());
		} else {
			dest.writeString(null);
		}
					</#if>
				<#else>
					<#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
		dest.writeParcelable(this.get${field.name?cap_first}(), flags);
					<#else>
		if (this.get${field.name?cap_first}() != null) {
			dest.writeInt(this.get${field.name?cap_first}().size());
			for (${field.relation.targetEntity?cap_first} item : this.get${field.name?cap_first}()) {
				dest.writeParcelable(item, flags);
			}
		} else {
			dest.writeInt(-1);
		}
					</#if>
				</#if>
			</#if>
		</#list>
	}
