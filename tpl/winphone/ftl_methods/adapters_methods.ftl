<#function itemToContentValuesFieldAdapter objectName field indentLevel = 0>
	<#assign t = Utils.getIndentString(indentLevel) />
	<#assign result = "" />
	<#if (!field.internal && field.writable)>
		<#assign fieldNames = ContractUtils.getFieldsNames(field) />
		<#if (!field.relation??)>
			<#if (MetadataUtils.isPrimitive(field))>
				<#assign result = result + "${t}result.put(${fieldNames[0]},\n" />
				<#assign result = result + "${t}	${FieldsUtils.generateStringGetter(\"item\", field)});\n\n"/>
			<#else>
				<#assign result = result + "${t}if (${FieldsUtils.generateCompleteGetter(\"item\", field)} != null) {\n" />
				<#assign result = result + "${t}	result.put(${fieldNames[0]},\n"/>
				<#assign result = result + "${t}		${FieldsUtils.generateStringGetter(\"item\", field)});\n"/>
				<#if (field.nullable)>
					<#assign result = result + "${t}} else {\n"/>
					<#assign result = result + "${t}	result.put(${fieldNames[0]}, (String) null);\n"/>
				</#if>
				<#assign result = result + "${t}}\n\n"/>
			</#if>
		<#else>
			<#assign targetEntity = entities[field.relation.targetEntity] />
			<#if (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
				<#assign result = result + "${t}if (${FieldsUtils.generateCompleteGetter(\"item\", field)} != null) {\n"/>
				<#list targetEntity.ids as id>
					<#assign result = result + "${t}	result.put(${fieldNames[id_index]},\n"/>
					<#assign result = result + "${t}		item.get${field.name?cap_first}().get${id.name?cap_first}());\n"/>
				</#list>
				<#if (field.nullable)>
					<#assign result = result + "${t}} else {\n"/>
					<#list targetEntity.ids as id>
						<#assign result = result + "${t}	result.put(${fieldNames[id_index]}, (String) null);\n"/>
					</#list>
				</#if>
				<#assign result = result + "${t}}\n\n"/>
			</#if>
		</#if>
	</#if>
	<#return result />
</#function>

<#function fixtureConstantsFieldAdapter field indentLevel = 0>
	<#assign tab = Utils.getIndentString(indentLevel) />
	<#assign result = "" />
	<#if (!field.internal)>
		<#assign result = result + "${tab}/** Constant field for ${field.name}. */\n"/>		
		<#assign result = result + "${tab}private static final String ${NamingUtils.fixtureAlias(field)} = \"${field.name?uncap_first}\";\n"/>		
	</#if>
	<#return result />
</#function>


<#function cursorToItemFieldAdapter objectName field indentLevel = 0>
	<#assign tab = Utils.getIndentString(indentLevel) />
	<#assign result = "" />
	<#assign fieldNames = ContractUtils.getFieldsNames(field) />
	<#if (!field.internal && !(field.relation?? && (field.relation.type=="ManyToMany" || field.relation.type=="OneToMany")))>
		<#assign localTab="" />
		<#if (!field.relation??)>
			<#assign result = result + "${tab}index = cursor.getColumnIndexOrThrow(${fieldNames[0]});\n"/>
			<#if (field.nullable)>
				<#assign result = result + "${tab}if (!cursor.isNull(index)) {\n"/><#assign localTab="\t" />
			</#if>
			<#if (field.type?lower_case == "datetime") >
				<#if ((field.harmony_type == "date") || (field.harmony_type == "datetime") || (field.harmony_type == "time"))>

					<#assign result = result + "${tab}${localTab}final DateTime dt${field.name?cap_first} =\n"/>
					<#assign result = result + "${tab}		DateUtils.formatISOStringToDateTime(\n"/>
					<#assign result = result + "${tab}				cursor.getString(index));\n"/>
					<#assign result = result + "${tab}${localTab}if (dt${field.name?cap_first} != null) {\n"/>
					<#assign result = result + "${tab}${localTab}		result.set${field.name?cap_first}(\n"/>
					<#assign result = result + "${tab}				dt${field.name?cap_first});\n"/>
					<#assign result = result + "${tab}${localTab}} else {\n"/>
					<#assign result = result + "${tab}${localTab}	result.set${field.name?cap_first}(new DateTime());\n"/>
					<#assign result = result + "${tab}${localTab}}\n"/>
				</#if>
			<#elseif (field.type?lower_case == "boolean")>
				<#assign result = result + "${tab}${localTab}result.set${field.name?cap_first}(\n"/>
				<#assign result = result + "${tab}		cursor.getInt(index) == 1);\n"/>
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
			<#if (field.nullable?? && field.nullable)>
				<#assign result = result + "${tab}}\n"/>
			</#if>
		<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			<#assign result = result + "${tab}if (result.get${field.name?cap_first}() == null) {\n" />
			<#assign result = result + "${tab}${localTab}	final ${field.type} ${field.name} = new ${field.type}();\n"/>
				<#list entities[field.relation.targetEntity].ids as id>
					<#assign result = result + "${tab}${localTab}	index = cursor.getColumnIndexOrThrow(${fieldNames[id_index]});\n"/>
					<#if (field.nullable)>
						<#assign result = result + "${tab}${localTab}	if (!cursor.isNull(index)) {\n"/><#assign localTab="\t" />
					</#if>
					<#assign result = result + "${tab}${localTab}	${field.name}.set${id.name?cap_first}(${AdapterUtils.getCursorGet(id)}index));\n"/>
					<#if (field.nullable?? && field.nullable)>
						<#assign result = result + "${tab}${localTab}	}\n"/>
					</#if>
				</#list>
			<#assign result = result + "${tab}${localTab}	result.set${field.name?cap_first}(${field.name});\n"/>
			<#assign result = result + "${tab}}\n" />
		</#if>
	</#if>
	<#if (result?length > 0)><#assign result = result + "\n" /></#if>
	<#return result />
</#function>

<#function xmlExtractFieldAdapter objectName field curr indentLevel = 0>
	<#assign result = "" />
	<#assign tab = "\n" + Utils.getIndentString(indentLevel) />
	<#if (!field.internal)>
		<#assign result = result + "${tab}this.currentFieldName = ${NamingUtils.fixtureAlias(field)};" />
		<#assign result = result + "${tab}String ${NamingUtils.fixtureParsedAlias(field)} = element.getChildText(${NamingUtils.fixtureAlias(field)});" />
		<#assign result = result + "${tab}if (${NamingUtils.fixtureParsedAlias(field)} != null) {" />
		<#if !field.relation??>
			<#if field.type=="int" || field.type=="integer" || field.type=="zipcode" || field.type=="ean">
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(Integer.parseInt(${NamingUtils.fixtureParsedAlias(field)}));"/>
			<#elseif (field.type?lower_case=="float")>
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(Float.parseFloat(${NamingUtils.fixtureParsedAlias(field)}));"/>
			<#elseif (field.type?lower_case=="double")>
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(Double.parseDouble(${NamingUtils.fixtureParsedAlias(field)}));"/>
			<#elseif (field.type?lower_case=="datetime")>
					<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(" />
					<#assign result = result + "${tab}		DateUtils.formatXMLStringToDateTime(" />
					<#assign result = result + "${tab}				${NamingUtils.fixtureParsedAlias(field)}));" />
			<#elseif field.type=="boolean">
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(" />
				<#assign result = result + "${tab}		Boolean.parseBoolean(${NamingUtils.fixtureParsedAlias(field)}));" />
			<#elseif field.type?lower_case=="string">
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(${NamingUtils.fixtureParsedAlias(field)});" />
			<#elseif (field.type?lower_case == "short")>
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(" />
				<#assign result = result + "${tab}		Integer.valueOf(${NamingUtils.fixtureParsedAlias(field)}).shortValue());" />
			<#elseif (field.type?lower_case == "char" || field.type?lower_case == "character")>
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(${NamingUtils.fixtureParsedAlias(field)}.charAt(0));" />
			<#elseif (field.type?lower_case == "byte")>
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(" />
				<#assign result = result + "${tab}		Integer.valueOf(${NamingUtils.fixtureParsedAlias(field)}).byteValue());" />
			<#elseif (field.harmony_type == "enum")>
				<#assign enumType = enums[field.type] />
				<#if (enumType.id??)>
					<#assign idEnum = enumType.fields[enumType.id] />
					<#if (idEnum.type?lower_case == "int" || idEnum.type?lower_case == "integer") >
						<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(${field.type}.fromValue(" />
						<#assign result = result + "${tab}				Integer.parseInt(${NamingUtils.fixtureParsedAlias(field)})));" />
					<#else>
						<#assign result = result + "${tab}${objectName}.set${field.name?cap_first}(${field.type}.fromValue(" />
						<#assign result = result + "${tab}				${NamingUtils.fixtureParsedAlias(field)}));" />
					</#if>
				<#else>
					<#assign result = result + "${tab}${objectName}.set${field.name?cap_first}(${field.type}.valueOf(${NamingUtils.fixtureParsedAlias(field)}));" />
				</#if>
			</#if>
		<#else>
			<#if (field.relation.type=="OneToOne")>
				<#assign result = result + "${tab}	${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = "/>
				<#assign result = result + "${tab}		${field.relation.targetEntity?cap_first}DataLoader.getInstance(" />
				<#assign result = result + "${tab}				this.ctx).getModelFixture(" />
				<#assign result = result + "${tab}						${NamingUtils.fixtureParsedAlias(field)}); "/>
				<#assign result = result + "${tab}if (${field.relation.targetEntity?uncap_first} != null) { "/>
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(" />
				<#assign result = result + "${tab}					${field.relation.targetEntity?uncap_first});" />
				<#if field.relation.inversedBy??>
					<#assign invField = MetadataUtils.getInversingField(field) />
					<#assign result = result + "${tab}	${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(" />
					<#assign result = result + "${tab}					${objectName});" />
				</#if>
			<#assign result = result + "${tab}}" />
			<#elseif (field.relation.type=="ManyToOne")>
				<#assign result = result + "${tab}	${field.relation.targetEntity?cap_first} ${field.relation.targetEntity?uncap_first} = ${field.relation.targetEntity?cap_first}DataLoader.getInstance(this.ctx)" />
				<#assign result = result + "${tab}				.getModelFixture(${NamingUtils.fixtureParsedAlias(field)});" />
				<#assign result = result + "${tab}	if (${field.relation.targetEntity?uncap_first} != null) {" />
				<#assign result = result + "${tab}		${objectName}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first});" />
				<#if field.relation.inversedBy??>
					<#assign invField = MetadataUtils.getInversingField(field) />
					<#assign result = result + "${tab}		ArrayList<${curr.name?cap_first}> ${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s =" />
					<#assign result = result + "${tab}			${field.relation.targetEntity?uncap_first}.get${invField.name?cap_first}();" />
					<#assign result = result + "${tab}		if (${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s == null) {" />
					<#assign result = result + "${tab}			${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s =" />
					<#assign result = result + "${tab}				new ArrayList<${curr.name?cap_first}>();" />
					<#assign result = result + "${tab}		}" />
					<#assign result = result + "${tab}		${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s.add(${objectName});" />
					<#assign result = result + "${tab}		${field.relation.targetEntity?uncap_first}.set${invField.name?cap_first}(${field.relation.targetEntity?uncap_first}${curr.name?cap_first}s);" />
				</#if>
			<#assign result = result + "${tab}	}" />
			<#else>
				<#assign result = result + "${tab}	ArrayList<${field.relation.targetEntity?cap_first}> ${field.relation.targetEntity?uncap_first}s =" />
				<#assign result = result + "${tab}		new ArrayList<${field.relation.targetEntity?cap_first}>();" />
				<#assign result = result + "${tab}	List<Element> ${field.relation.targetEntity?uncap_first}sMap =" />
				<#assign result = result + "${tab}		element.getChild(${NamingUtils.fixtureAlias(field)}).getChildren();" />
				<#assign result = result + "${tab}	${field.relation.targetEntity?cap_first}DataLoader ${field.relation.targetEntity?uncap_first}DataLoader = " />
				<#assign result = result + "${tab}			${field.relation.targetEntity?cap_first}DataLoader.getInstance(this.ctx);\n" />
				<#assign result = result + "${tab}	for (Element ${field.relation.targetEntity?uncap_first}Name : ${field.relation.targetEntity?uncap_first}sMap) {" />
				<#assign result = result + "${tab}		if (${field.relation.targetEntity?uncap_first}DataLoader.items.containsKey(" />
				<#assign result = result + "${tab}				${field.relation.targetEntity?uncap_first}Name.getText())) {" />
				<#assign result = result + "${tab}			${field.relation.targetEntity?uncap_first}s.add(" />
				<#assign result = result + "${tab}				${field.relation.targetEntity?uncap_first}DataLoader.getModelFixture(" />
				<#assign result = result + "${tab}								${field.relation.targetEntity?uncap_first}Name.getText()));" />
				<#assign result = result + "${tab}		}" />
				<#assign result = result + "${tab}	}" />
				<#assign result = result + "${tab}	${objectName}.set${field.name?cap_first}(${field.relation.targetEntity?uncap_first}s);" />
			</#if>
		</#if>
		<#assign result = result + "${tab}}\n" />
	</#if>
	<#return result />
</#function>

<#function validateDataFieldAdapter field indentLevel = 0>
	<#assign result = "" />
	<#assign tab = "\n" + Utils.getIndentString(indentLevel) />
	<#if !field.internal && !field.hidden && (field.type?lower_case != "boolean" && field.harmony_type?lower_case != "enum")&& field.writable>
		<#if !field.nullable>
			<#if !field.relation??>
				<#if field.type?lower_case == "datetime">
					<#if (field.harmony_type) == "datetime">
						<#assign result = result + "${tab}if (this.${field.name}View.getDateTime() == null) {" />
					<#else>
						<#assign result = result + "${tab}if (this.${field.name}View.get${field.harmony_type?cap_first}() == null) {" />
					</#if>
				<#else>
						<#assign result = result + "${tab}if (Strings.isNullOrEmpty(" />
						<#assign result = result + "${tab}			this.${field.name}View.getText().toString().trim())) {" />
				</#if>
			<#else>
				<#if ((field.relation.type == "ManyToOne") || (field.relation.type == "OneToOne"))>
					<#assign result = result + "${tab}if (this.${field.name}Adapter.getSelectedItem() == null) {" />
				<#else>
					<#assign result = result + "${tab}if (this.${field.name}Adapter.getCheckedItems().isEmpty()) {" />
				</#if>
			</#if>
			<#assign result = result + "${tab}	error = R.string.${field.owner?lower_case}_${field.name?lower_case}_invalid_field_error;" />
			<#assign result = result + "${tab}}" />
		</#if>
	</#if>
	<#return result />
</#function>

<#function saveDataFieldAdapter field indentLevel = 0>
	<#assign result = "" />
	<#assign tab = "\n" + Utils.getIndentString(indentLevel) />
	<#if !field.internal && !field.hidden && field.writable>
		<#if !field.relation??>
			<#assign result = result + "${tab}${ViewUtils.setSaver(field)}\n" />
		<#elseif field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
			<#assign result = result + "${tab}this.model.set${field.name?cap_first}(this.${field.name}Adapter.getSelectedItem());\n" />
		<#else>
			<#assign result = result + "${tab}this.model.set${field.name?cap_first}(this.${field.name}Adapter.getCheckedItems());\n" />
		</#if>
	</#if>
	<#return result/>
</#function>

<#function loadDataCreateFieldAdapter field indentLevel = 0>
	<#assign result = "" />
	<#assign tab = "\n" + Utils.getIndentString(indentLevel) />
	<#if !field.internal && !field.hidden && field.writable>
			<#if !field.relation??>
				<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float") && (field.type!="long") && (field.type!="short") && (field.type!="double") && (field.type != "char") && (field.type != "byte")>
					<#if (field.type=="Boolean")>
		<#assign result = result + "${tab}if (this.model.is${field.name?cap_first}() != null) {" />					
					<#else>
		<#assign result = result + "${tab}if (this.model.get${field.name?cap_first}() != null) {" />
					</#if>
					<#if field.type?lower_case=="datetime">
						<#if field.harmony_type=="datetime">
		<#assign result = result + "${tab}	this.${field.name}View.setDateTime(this.model.get${field.name?cap_first}());" />
						<#elseif (field.harmony_type=="date")>
		<#assign result = result + "${tab}	this.${field.name}View.setDate(this.model.get${field.name?cap_first}());" />
						<#elseif (field.harmony_type=="time")>
		<#assign result = result + "${tab}	this.${field.name}View.setTime(this.model.get${field.name?cap_first}());" />
						</#if>
					<#else>
		<#assign result = result + "${tab}	${ViewUtils.setLoader(field)}" />
					</#if>
		<#assign result = result + "${tab}}" />
				<#else>
		<#assign result = result + "${tab}${ViewUtils.setLoader(field)}" />
				</#if>
			</#if>
		</#if>
	<#return result/>
</#function>

<#function loadDataShowFieldAdapter field indentLevel = 0>
	<#assign result = "" />
	<#assign tab = "\n" + Utils.getIndentString(indentLevel) />
	<#if (!field.internal && !field.hidden)>
			<#if (!field.relation??)>
		    	<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float") && (field.type!="long") && (field.type!="short") && (field.type!="double") && (field.type != "char") && (field.type != "byte")>
					<#if (field.type == "Boolean")>
		<#assign result = result + "${tab}if (this.model.is${field.name?cap_first}() != null) {" />
					<#else>
		<#assign result = result + "${tab}if (this.model.get${field.name?cap_first}() != null) {" />
					</#if>
					<#if (field.type?lower_case == "datetime")>
						<#if (field.harmony_type == "datetime")>
		<#assign result = result + "${tab}	this.${field.name}View.setText(" />
		<#assign result = result + "${tab}			DateUtils.formatDateTimeToString(" />
		<#assign result = result + "${tab}					this.model.get${field.name?cap_first}()));" />
						</#if>
						<#if (field.harmony_type == "date")>
		<#assign result = result + "${tab}	this.${field.name}View.setText(" />
		<#assign result = result + "${tab}			DateUtils.formatDateToString(" />
		<#assign result = result + "${tab}					this.model.get${field.name?cap_first}()));" />
						</#if>
						<#if (field.harmony_type == "time")>
		<#assign result = result + "${tab}	this.${field.name}View.setText(" />
		<#assign result = result + "${tab}			DateUtils.formatTimeToString(" />
		<#assign result = result + "${tab}					this.model.get${field.name?cap_first}()));" />
						</#if>
					<#elseif (field.harmony_type?lower_case == "enum")>
		<#assign result = result + "${tab}	this.${field.name}View.setText(this.model.get${field.name?cap_first}().toString());" />
					<#else>
		<#assign result = result + "${tab}	${ViewUtils.setLoader(field)}" />
					</#if>
		<#assign result = result + "${tab}}" />
				<#else>
		<#assign result = result + "${tab}${ViewUtils.setLoader(field)}" />
				</#if>
			<#else>
		<#assign result = result + "${tab}if (this.model.get${field.name?cap_first}() != null) {" />
				<#if (field.relation.type=="OneToOne" || field.relation.type=="ManyToOne")>
		<#assign result = result + "${tab}	this.${field.name}View.setText(" />
		<#assign result = result + "${tab}			String.valueOf(this.model.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}()));" />
				<#else>
		<#assign result = result + "${tab}	String ${field.name}Value = \"\";" />
		<#assign result = result + "${tab}	for (${field.relation.targetEntity} item : this.model.get${field.name?cap_first}()) {" />
		<#assign result = result + "${tab}		${field.name}Value += item.get${entities[field.relation.targetEntity].ids[0].name?cap_first}() + \",\";" />
		<#assign result = result + "${tab}	}" />
		<#assign result = result + "${tab}	this.${field.name}View.setText(${field.name}Value);" />
				</#if>
		<#assign result = result + "${tab}}" />
			</#if>
		</#if>
	<#return result/>
</#function>

<#function populateViewHolderFieldAdapter field indentLevel = 0>
	<#assign result = "" />
	<#assign tab = "\n" + Utils.getIndentString(indentLevel) />
	<#if (!field.internal && !field.hidden)>
		<#if (!field.relation??)>
			<#if (field.type!="int") && (field.type!="boolean") && (field.type!="long") && (field.type!="ean") && (field.type!="zipcode") && (field.type!="float") && (field.type!="long") && (field.type!="short") && (field.type!="double") && (field.type != "char") && (field.type != "byte")>
				<#if (field.type == "Boolean")>
					<#assign result = result + "${tab}if (model.is${field.name?cap_first}() != null) {" />
				<#else>
					<#assign result = result + "${tab}if (model.get${field.name?cap_first}() != null) {" />
				</#if>
				<#assign result = result + "${tab}	${ViewUtils.setAdapterLoader(field)}" />
				<#assign result = result + "${tab}}" />
			<#else>
				<#assign result = result + "${tab}${ViewUtils.setAdapterLoader(field)}" />
			</#if>
		<#elseif (field.relation.type=="OneToOne" | field.relation.type=="ManyToOne")>
			<#assign result = result + "${tab}if (model.get${field.name?cap_first}() != null) {" />
			<#assign result = result + "${tab}	${field.name}View.setText(" />
			<#assign result = result + "${tab}			String.valueOf(model.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}()));" />
			<#assign result = result + "${tab}}" />
		</#if>
	</#if>
	<#return result/>
</#function>

<#function getCursorGet field>
	<#if (field.type?lower_case == "integer" || field.type?lower_case == "int")>
		<#assign result = "cursor.getInt(" />
	<#else>
		<#assign result = "cursor.getString(" />
	</#if>
	<#return result />
</#function>
