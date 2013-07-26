<#assign utilityPath = "/tpl/android/ftl_methods/" />
<#import utilityPath + "general_methods.ftl" as Utils />
<#import utilityPath + "inheritance_methods.ftl" as InheritanceUtils />
<#function importRelatedSQLiteAdapters entity>
	<#assign result = ""/>
	<#assign import_array = [entity.name] />
	<#list entity.relations as relation>
		<#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
			<#assign import_array = import_array + [relation.relation.targetEntity] />
			<#if relation.relation.type == "ManyToMany">
				<#assign result = result + "import ${data_namespace}.${relation.relation.joinTable}SQLiteAdapter;\n" />
			<#else>
				<#assign result = result + "import ${data_namespace}.${relation.relation.targetEntity}SQLiteAdapter;\n" />
			</#if>
		</#if>
	</#list>
	<#return result />
</#function>

<#function importRelatedEntities entity>
	<#assign result = ""/>
	<#if entity.internal == "true">
		<#assign import_array = [] />
	<#else>
		<#assign import_array = [entity.name] />
	</#if>
	<#list entity.relations as relation>
		<#if !relation.internal>
			<#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
				<#assign import_array = import_array + [relation.relation.targetEntity] />
			</#if>
		</#if>
	</#list>
	<#list import_array as import>
		<#assign result = result + "import ${entity.namespace}.entity.${import};" />
		<#if import_has_next>
			<#assign result = result + "\n" />
		</#if>
	</#list>
	<#return result />
</#function>

<#function importToManyRelatedEntities entity>
	<#assign result = ""/>
	<#assign import_array = [entity.name] />
	<#list entity.relations as relation>
		<#if !relation.internal && (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany") >
			<#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
				<#assign import_array = import_array + [relation.relation.targetEntity] />
			</#if>
		</#if>
	</#list>
	<#list import_array as import>
		<#assign result = result + "import ${entity.namespace}.entity.${import};" />
		<#if import_has_next>
			<#assign result = result + "\n" />
		</#if>
	</#list>
	<#return result />
</#function>

<#function importRelatedEnums entity>
	<#assign result = ""/>
	<#assign import_array = [entity.name] />
	<#list entity.fields?values as field>
		<#if field.harmony_type?lower_case == "enum">
			<#if (!Utils.isInArray(import_array, field.type)) >
				<#assign import_array = import_array + [field.type] />
				<#assign enumClass = enums[field.type] />
				<#assign result = result + "import ${entity_namespace}.${InheritanceUtils.getCompleteNamespace(enumClass)};\n" />
			</#if>
		</#if>
	</#list>
	<#return result />
</#function>

<#function importRelatedProviderUtils entity>
	<#assign result = ""/>
	<#assign import_array = [entity.name] />
	<#list entity.relations as field>
		<#if (!Utils.isInArray(import_array, field.type) && !field.internal) >
			<#assign import_array = import_array + [field.type] />
			<#assign result = result + "import ${project_namespace}.provider.utils.${field.relation.targetEntity?cap_first}ProviderUtils;\n" />
		</#if>
	</#list>
	<#return result />
</#function>
