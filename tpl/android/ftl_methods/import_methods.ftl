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
	<#assign import_array = [entity.name] />
	<#list entity.relations as relation>
		<#if !relation.internal>
			<#if (!Utils.isInArray(import_array, relation.relation.targetEntity))>
				<#assign import_array = import_array + [relation.relation.targetEntity] />
				<#assign result = result + "import ${entity.namespace}.entity.${relation.relation.targetEntity};\n" />
			</#if>
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
		<#if (!Utils.isInArray(import_array, field.type)) >
			<#assign import_array = import_array + [field.type] />
			<#assign result = result + "import ${project_namespace}.provider.utils.${field.relation.targetEntity?cap_first}ProviderUtils;\n" />
		</#if>
	</#list>
	<#return result />
</#function>
