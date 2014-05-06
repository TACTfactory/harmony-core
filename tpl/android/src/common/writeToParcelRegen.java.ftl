<#assign curr = entities[current_entity] />	/**
	 * This stub of code is regenerated. DO NOT MODIFY.
	 * 
	 * @param dest Destination parcel
	 * @param flags flags
	 */
	public void writeToParcelRegen(Parcel dest, int flags) {
		if (this.parcelableParents == null) {
			this.parcelableParents = new ArrayList<Parcelable>();
		}
		if (!this.parcelableParents.contains(this)) {
			this.parcelableParents.add(this);
		}
		<#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
		super.writeToParcelRegen(dest, flags);
		</#if>
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type == "int" || field.type == "Integer">
		dest.writeInt(this.get${field.name?cap_first}());
					<#elseif field.type?lower_case == "boolean">

		if (this.is${field.name?cap_first}()) {
			dest.writeInt(1);
		} else {
			dest.writeInt(0);
		}
					<#elseif field.type == "String">
		dest.writeString(this.get${field.name?cap_first}());
					<#elseif field.type?lower_case == "byte">
		dest.writeByte(this.get${field.name?cap_first}());
					<#elseif field.type?lower_case == "char">
		dest.writeString(String.valueOf(this.get${field.name?cap_first}()));
					<#elseif field.type?lower_case == "short">
		dest.writeInt(this.get${field.name?cap_first}());
					<#elseif field.type?lower_case == "character">
		if (this.get${field.name?cap_first}() != null) {
			dest.writeInt(1);
			dest.writeString(String.valueOf(this.get${field.name?cap_first}()));
		} else {
			dest.writeInt(0);
		}			
					<#elseif field.type?lower_case == "datetime">
		if (this.get${field.name?cap_first}() != null) {
			dest.writeInt(1);
			dest.writeString(ISODateTimeFormat.dateTime().print(
					this.get${field.name?cap_first}()));
		} else {
			dest.writeInt(0);
		}
					<#elseif (field.harmony_type?lower_case == "enum")>		

		if (this.get${field.name?cap_first}() != null) {
			dest.writeInt(1);
						<#assign enumType = enums[field.type] />
						<#if enumType.id??>
							<#assign idEnum = enumType.fields[enumType.id] />
							<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
			dest.writeInt(this.get${field.name?cap_first}().getValue());
							<#else>
			dest.writeString(this.get${field.name?cap_first}().getValue());
							</#if>
						<#else>
			dest.writeString(this.get${field.name?cap_first}().name());
						</#if>
		} else {
			dest.writeInt(0);
		}
					</#if>
				<#else>
					<#if field.relation.type == "OneToOne" || field.relation.type == "ManyToOne">
		if (<#if field.nullable>this.get${field.name?cap_first}() != null
					&& </#if>(!this.parcelableParents.contains(this.get${field.name?cap_first}()))) {
			this.get${field.name?cap_first}().writeToParcel(this.parcelableParents, dest, flags);
		} else {
			dest.writeParcelable(null, flags);
		}
					<#else>

		if (this.get${field.name?cap_first}() != null) {
			dest.writeInt(this.get${field.name?cap_first}().size());
			for (${field.relation.targetEntity?cap_first} item : this.get${field.name?cap_first}()) {
				if (!this.parcelableParents.contains(item)) {
					item.writeToParcel(this.parcelableParents, dest, flags);
				} else {
					dest.writeParcelable(null, flags);
				}
			}
		} else {
			dest.writeInt(-1);
		}
					</#if>
				</#if>
			</#if>
		</#list>	
		this.parcelableParents = null;	
	}
