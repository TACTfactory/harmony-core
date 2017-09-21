<#function getColumnsNames field>
	<#assign result = [] />
	<#if field.relation??>
		<#list entities[field.relation.targetEntity].ids as id>
			<#assign result = result + [field.name + "_" + id.name] />
		</#list>
	<#else>
		<#assign result = result + [field.name] />
	</#if>
	<#return result />
</#function>

<#function getFieldsNames field aliased=false>
	<#assign columnNames = ContractUtils.getColumnsNames(field) />
	<#assign result = [] />
	<#list columnNames as name>
		<#if aliased>
			<#assign result = result + [ContractUtils.getContractClass(entities[field.owner]) + ".ALIASED_" + NamingUtils.alias(name)] />
		<#else>
			<#assign result = result + [ContractUtils.getContractClass(entities[field.owner]) + "." + NamingUtils.alias(name)] />
		</#if>
	</#list>
	<#return result />
</#function>

<#function getFieldsDeclarations field entity>
	<#assign fieldsNames = ContractUtils.getColumnsNames(field) />
	<#assign result = "" />
	<#list fieldsNames as fieldName>
		<#assign result = result + "		/** ${fieldName}. */\n" />
		<#assign result = result + "		public static final String ${NamingUtils.alias(fieldName)} =\n" />
		<#if field.relation??>
			<#assign result = result + "				\"${field.columnName}_${field.relation.field_ref[fieldName_index].name}\";\n"/>
		<#else>
			<#assign result = result + "				\"${field.columnName}\";\n"/>
		</#if>
		<#assign result = result + "		/** Alias. */\n" />
		<#assign result = result + "		public static final String ALIASED_${NamingUtils.alias(fieldName)} =\n" />
		<#assign result = result + "				" />
		<#if !field.columnResult>
			<#assign result = result + "${entity.name?cap_first}Contract.${entity.name?cap_first}.TABLE_NAME + \".\" + " />
		</#if>
		<#assign result = result + "${NamingUtils.alias(fieldName)};\n\n"/>
	</#list>
	<#return result />
</#function>

<#function getContractCols entity aliased=false>
	<#if aliased>
		<#return ContractUtils.getContractClass(entity) + ".ALIASED_COLS" />
	<#else>
		<#return ContractUtils.getContractClass(entity) + ".COLS" />
	</#if>
</#function>

<#function getContractTableName entity>
	<#return ContractUtils.getContractClass(entity) + ".TABLE_NAME" />
</#function>

<#function getContractCol field aliased=false>
	<#if aliased>
		<#return ContractUtils.getContractClass(entities[field.owner]) + ".ALIASED_COL_${field.name?upper_case}" />
	<#else>
		<#return ContractUtils.getContractClass(entities[field.owner]) + ".COL_${field.name?upper_case}" />
	</#if>
</#function>

<#function getContractClass entity>
	<#return "${entity.name}Contract" />
</#function>

<#function getContractItemToContentValues entity>
	<#return ContractUtils.getContractClass(entity) + ".itemToContentValues" />
</#function>

<#function getContractCursorToItem entity>
	<#return ContractUtils.getContractClass(entity) + ".cursorToItem" />
</#function>
