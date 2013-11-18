<#assign curr = entities[current_entity] />
	/**
	 * Parcel Constructor.
	 *
	 * @param in The parcel to read from
	 */
	public ${curr.name}(Parcel parc) {
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">					
		this.set${field.name?cap_first}(parc.readInt());
					<#elseif field.type?lower_case == "boolean">
		this.set${field.name?cap_first}(parc.readInt() == 1);
					<#elseif field.type == "String">
		this.set${field.name?cap_first}(parc.readString());
					<#elseif field.type?lower_case == "datetime">
		this.set${field.name?cap_first}(new DateTime(parc.readString()));
					<#elseif (field.harmony_type?lower_case == "enum")>
		int ${field.name}Bool = parc.readInt();
		if (${field.name}Bool == 1) {
						<#assign enumType = enums[field.type] />
						<#if enumType.id??>
							<#assign idEnum = enumType.fields[enumType.id] />
							<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
			this.set${field.name?cap_first}(${field.type}.fromValue(parc.readInt()));
							<#else>
			this.set${field.name?cap_first}(${field.type}.fromValue(parc.readString()));
							</#if>
						<#else>
			this.set${field.name?cap_first}(${field.type}.fromValue(parc.readString()));
						</#if>
		}
					</#if>
				<#else>
					<#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
		this.set${field.name?cap_first}((${field.type}) parc.readParcelable(${field.type}.class.getClassLoader()));
					<#else>
		int nb${field.name?cap_first} = parc.readInt();
		if (nb${field.name?cap_first} > -1) {
			ArrayList<${field.relation.targetEntity}> items =
				new ArrayList<${field.relation.targetEntity}>();
			for (int i = 0; i < nb${field.name?cap_first}; i++) {
				items.add((${field.relation.targetEntity}) parc.readParcelable(
						${field.relation.targetEntity}.class.getClassLoader()));
			}
			this.set${field.name?cap_first}(items);
		}
					</#if>
				</#if>
			</#if>
		</#list>
	}
