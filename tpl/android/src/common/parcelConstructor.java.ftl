<#assign curr = entities[current_entity] />
	public ${curr.name}(Parcel in) {
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">
		this.set${field.name?cap_first}(in.readInt());
					<#elseif field.type == "String">
		this.set${field.name?cap_first}(in.readString());
					<#elseif field.type?lower_case == "datetime">
		this.set${field.name?cap_first}(new DateTime(in.readString()));
					</#if>
				<#else>
					<#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
		this.set${field.name?cap_first}((${field.type}) in.readParcelable(${field.type}.class.getClassLoader()));
					<#else>
		int nb${field.name?cap_first} = in.readInt();
		if (nb${field.name?cap_first} > -1) {
			ArrayList<${field.relation.targetEntity}> items = new ArrayList<${field.relation.targetEntity}>();
			for (int i = 0; i < nb${field.name?cap_first}; i++) {
				items.add((${field.relation.targetEntity}) in.readParcelable(${field.relation.targetEntity}.class.getClassLoader()));
			}
			this.set${field.name?cap_first}(items);
		}
					</#if>
				</#if>
			</#if>
		</#list>
	}
