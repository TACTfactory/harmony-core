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
	<#assign contractClass = "${field.owner?cap_first}Contract.${field.owner?cap_first}" />
	<#list columnNames as name>
		<#if aliased>
			<#assign result = result + [contractClass + ".ALIASED_" + NamingUtils.alias(name)] />
		<#else>
			<#assign result = result + [contractClass + "." + NamingUtils.alias(name)] />
		</#if>
	</#list>
	<#return result />
</#function>

<#function getFieldsDeclarations field>
	<#assign fieldsNames = ContractUtils.getColumnsNames(field) />
	<#assign result = "" />
	<#list fieldsNames as fieldName>
		<#assign result = result + "		/** ${fieldName}. */\n" />
		<#assign result = result + "		public static final String ${NamingUtils.alias(fieldName)} =\n" />
		<#assign result = result + "				\"${field.columnName}\";\n"/>
		<#assign result = result + "		/** Alias. */\n" />
		<#assign result = result + "		public static final String ALIASED_${NamingUtils.alias(fieldName)} =\n" />
		<#assign result = result + "				" />
		<#if !field.columnResult>
			<#assign result = result + "${field.owner?cap_first}Contract.${field.owner}.TABLE_NAME + \".\" + " />
		</#if>
		<#assign result = result + "${NamingUtils.alias(fieldName)};\n\n"/>
	</#list>
	<#return result />
</#function>
