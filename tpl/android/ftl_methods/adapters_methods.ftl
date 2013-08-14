<#function itemToContentValuesFieldAdapter objectName field indentLevel = 0>
	<#assign t = Utils.getIndentString(indentLevel) />
	<#assign result = "" />
	<#if (!field.internal)>
		<#if (!field.relation??)>
			<#if (MetadataUtils.isPrimitive(field))>
		<#assign result = result + "${t}result.put(${NamingUtils.alias(field.name)},\n" />
		<#assign result = result + "${t}	${FieldsUtils.generateStringGetter(\"item\", field)});\n\n"/>
			<#else>
	<#assign result = result + "${t}if (${FieldsUtils.generateCompleteGetter(\"item\", field)} != null) {\n" />
	<#assign result = result + "${t}	result.put(${NamingUtils.alias(field.name)},\n"/>
	<#assign result = result + "${t}		${FieldsUtils.generateStringGetter(\"item\", field)});\n"/>
	<#assign result = result + "${t}}\n\n"/>
			</#if>
		<#else>
			<#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
	<#assign result = result + "${t}if (${FieldsUtils.generateCompleteGetter(\"item\", field)} != null) {\n"/>
	<#assign result = result + "${t}	result.put(${NamingUtils.alias(field.name)},\n"/>
	<#assign result = result + "${t}		item.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}());\n"/>
	<#assign result = result + "${t}}\n\n"/>
			</#if>
		</#if>
	</#if>
	<#return result />
</#function>


<#function cursorToItemFieldAdapter objectName field indentLevel = 0>
	<#assign tab = Utils.getIndentString(indentLevel) />
	<#assign result = "" />
	<#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
		<#assign localTab="" />
		<#assign result = result + "${tab}index = cursor.getColumnIndexOrThrow(${NamingUtils.alias(field.name)});\n"/>
		<#if (field.nullable)>
			<#assign result = result + "${tab}if (!cursor.isNull(index)) {\n"/><#assign localTab="\t" />
		</#if>
		<#if (!field.relation??)>
			<#if (field.type?lower_case == "datetime") >
				<#if ((field.harmony_type == "date") || (field.harmony_type == "datetime") || (field.harmony_type == "time"))>

					<#if field.is_locale>
						<#assign result = result + "${tab}${localTab}final DateTime dt${field.name?cap_first} =\n"/>
						<#assign result = result + "${tab}		DateUtils.formatLocalISOStringToDateTime(\n"/>
						<#assign result = result + "${tab}				cursor.getString(index));\n"/>
					<#else>
						<#assign result = result + "${tab}${localTab}final DateTime dt${field.name?cap_first} =\n"/>
						<#assign result = result + "${tab}		DateUtils.formatISOStringToDateTime(\n"/>
						<#assign result = result + "${tab}				cursor.getString(index));\n"/>
					</#if>
					<#assign result = result + "${tab}${localTab}if (dt${field.name?cap_first} != null) {\n"/>
					<#assign result = result + "${tab}${localTab}		result.set${field.name?cap_first}(\n"/>
					<#assign result = result + "${tab}				dt${field.name?cap_first});\n"/>
					<#assign result = result + "${tab}${localTab}} else {\n"/>
					<#assign result = result + "${tab}${localTab}	result.set${field.name?cap_first}(new DateTime());\n"/>
					<#assign result = result + "${tab}${localTab}}\n"/>
				</#if>
			<#elseif (field.type?lower_case == "boolean")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getString(index).equals(String.valueOf(true)));\n"/>
			<#elseif (field.type?lower_case == "int" || field.type?lower_case == "integer" || field.type == "ean" || field.type == "zipcode")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getInt(index));\n"/>
			<#elseif (field.type?lower_case == "float")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getFloat(index));\n"/>
			<#elseif (field.type?lower_case == "double")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getDouble(index));\n"/>
			<#elseif (field.type?lower_case == "long")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getLong(index));\n"/>
			<#elseif (field.type?lower_case == "short")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getShort(index));\n"/>
			<#elseif (field.type?lower_case == "char" || field.type?lower_case == "character")>
				<#assign result = result + "${tab}String ${field.name?uncap_first}DB = cursor.getString(index);\n"/>
				<#assign result = result + "${tab}if (${field.name?uncap_first}DB != null\n"/>
				<#assign result = result + "${tab}	&& ${field.name?uncap_first}DB.length() > 0) {\n"/>
				<#assign result = result + "${tab}	${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		${field.name?uncap_first}DB.charAt(0));\n"/>
				<#assign result = result + "${tab}}\n"/>
			<#elseif (field.type?lower_case == "byte")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(Byte.valueOf(\n"/>
				<#assign result = result + "${tab}		cursor.getString(index)));\n"/>
			<#elseif (field.type?lower_case == "string")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getString(index));\n"/>
			<#elseif (field.harmony_type?lower_case == "enum")>
				<#assign enumType = enums[field.type] />
				<#if enumType.id??>
					<#assign idEnum = enumType.fields[enumType.id] />
					<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
						<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
						<#assign result = result + "${tab}	${field.type}.fromValue(cursor.getInt(index)));\n"/>
					<#else>
						<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
						<#assign result = result + "${tab}	${field.type}.fromValue(cursor.getString(index)));\n"/>
					</#if>
				<#else>
					<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
					<#assign result = result + "${tab}	${field.type}.valueOf(cursor.getString(index)));\n"/>

				</#if>
			</#if>
		<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			<#assign result = result + "${tab}${localTab}final ${field.type} ${field.name} = new ${field.type}();\n"/>
			<#assign result = result + "${tab}${localTab}${field.name}.set${entities[field.relation.targetEntity].ids[0].name?cap_first}(cursor.getInt(index));\n"/>
			<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(${field.name});\n"/>
		</#if>
		<#if (field.nullable?? && field.nullable)>
			<#assign result = result + "${tab}}\n"/>
		</#if>
	</#if>
	<#if (result?length > 0)><#assign result = result + "\n" /></#if>
	<#return result />
</#function>



<#function cursorToItemFieldAdapter objectName field indentLevel = 0>
