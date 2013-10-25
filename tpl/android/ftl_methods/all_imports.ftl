<#import utilityPath + "general_methods.ftl" as Utils />
<#import utilityPath + "methods.ftl" as m />
<#import utilityPath + "naming_methods.ftl" as NamingUtils />
<#import utilityPath + "metadata_methods.ftl" as MetadataUtils />
<#import utilityPath + "inheritance_methods.ftl" as InheritanceUtils />
<#import utilityPath + "import_methods.ftl" as ImportUtils />
<#import utilityPath + "fields_methods.ftl" as FieldsUtils />
<#import utilityPath + "view_methods.ftl" as ViewUtils />
<#import utilityPath + "adapters_methods.ftl" as AdapterUtils />

<#if current_entity??>
	<#assign curr = entities[current_entity] />
	<#assign singleTabInheritance = (curr.inheritance?? && curr.inheritance.inheritanceType?? && curr.inheritance.inheritanceType == "SingleTab") />
	<#assign joinedInheritance = (curr.inheritance?? && curr.inheritance.superclass?? && !singleTabInheritance) />
	<#if joinedInheritance>
		<#assign curr_ids = entities[curr.inheritance.superclass.name].ids />
		<#assign curr_fields = curr.fields?values + entities[curr.inheritance.superclass.name].ids />
	<#else>
		<#assign curr_ids = curr.ids />
		<#assign curr_fields = curr.fields?values />
	</#if>
</#if>
