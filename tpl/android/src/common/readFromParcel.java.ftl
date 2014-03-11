<#assign curr = entities[current_entity] />	/**
	 * Regenerated Parcel Constructor. 
	 *
	 * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
	 *
	 * @param parc The parcel to read from
	 */
	public void readFromParcel(Parcel parc) {
		<#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
		super.readFromParcel(parc);
		</#if>
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
		if (parc.readInt() == 1) {
			this.set${field.name?cap_first}(new DateTime(parc.readString()));
		}
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
			this.set${field.name?cap_first}(${field.type}.valueOf(parc.readString()));
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
