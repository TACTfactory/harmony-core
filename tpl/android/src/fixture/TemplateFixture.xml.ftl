<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity]>
<?xml version="1.0" encoding="UTF-8"?>
<list>
	<!--
	<${curr.name?cap_first} id="">
		<#list ViewUtils.getAllFields(curr)?values as field>
			<#if !field.internal>
		<${field.name}></${field.name}>
			</#if>
		</#list>
	</${curr.name?cap_first}>
	-->
</list>
