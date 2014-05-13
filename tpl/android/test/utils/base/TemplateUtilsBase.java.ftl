<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign inherited = false />
<#if ((joinedInheritance || singleTabInheritance) && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??) >
	<#assign inherited = true />
</#if>
<@header?interpret />
package ${curr.test_namespace}.utils.base;

import android.content.Context;
import junit.framework.Assert;
import ${curr.namespace}.entity.${curr.name};

<#if (inherited)>import ${entity_namespace}.${curr.inheritance.superclass.name};</#if>

import ${project_namespace}.test.utils.TestUtils;
<#assign importList = [] />
<#list curr.relations as relation>
	<#if !relation.internal>
		<#if !Utils.isInArray(importList, relation.relation.targetEntity)>
<#if (dataLoader?? && dataLoader) || (relation.relation.type == "ManyToMany" || relation.relation.type == "OneToMany")>import ${curr.namespace}.entity.${relation.relation.targetEntity?cap_first};</#if>
<#if (dataLoader?? && dataLoader)><#list InheritanceUtils.getAllChildren(entities[relation.relation.targetEntity]) as child>import ${fixture_namespace}.${child.name?cap_first}DataLoader;
</#list><#else>import ${project_namespace}.test.utils.${relation.relation.targetEntity}Utils;</#if>
			<#assign importList = importList + [relation.relation.targetEntity] />
		</#if>
	</#if>
</#list>
<#if (InheritanceUtils.isExtended(curr))>import ${project_namespace}.test.utils.${curr.inheritance.superclass.name}Utils;</#if>
<#list curr.fields?values as field>
	<#if field.harmony_type?lower_case == "enum">
		<#assign enumClass = enums[field.enum.targetEnum] />
import ${entity_namespace}.${InheritanceUtils.getCompleteNamespace(enumClass)};
	</#if>
</#list>
<#if (importList?size > 0 && ((dataLoader?? && dataLoader) || MetadataUtils.hasToManyRelations(curr)))>
import java.util.ArrayList;
</#if>

public abstract class ${curr.name?cap_first}UtilsBase {

	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 *
	 * @return The randomly generated entity
	 */
	public static ${curr.name?cap_first} generateRandom(Context ctx){
		${curr.name?cap_first} ${curr.name?uncap_first} = new ${curr.name?cap_first}();
		<#if (inherited)>
		${curr.inheritance.superclass.name?cap_first} ${curr.inheritance.superclass.name?uncap_first} = ${curr.inheritance.superclass.name?cap_first}Utils.generateRandom(ctx);
			<#list entities[curr.inheritance.superclass.name].fields?values as field>
				<#if !field.internal>
		${curr.name?uncap_first}.set${field.name?cap_first}(${curr.inheritance.superclass.name?uncap_first}.<#if FieldsUtils.getJavaType(field)?lower_case == "boolean">is<#else>get</#if>${field.name?cap_first}());
				</#if>
			</#list>
		</#if>

		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if field.harmony_type?lower_case != "relation">
					<#switch FieldsUtils.getJavaType(field)?lower_case>
						<#case "string">
		${curr.name?uncap_first}.set${field.name?cap_first}("${field.name?uncap_first}_"+TestUtils.generateRandomString(10));
							<#break />
						<#case "int">
						<#case "integer">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomInt(0,100)<#if (field.id?? && field.id)> + 1</#if>);
							<#break />
						<#case "boolean">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomBool());
							<#break />
						<#case "double">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDouble(0,100));
							<#break />
						<#case "float">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomFloat(0,100));
							<#break />
						<#case "short">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomShort());
							<#break />
						<#case "char">
						<#case "character">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomChar());
							<#break />
						<#case "byte">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomByte());
							<#break />
						<#case "datetime">
							<#if field.harmony_type?lower_case=="date">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDate());
							<#elseif field.harmony_type?lower_case=="time">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomTime());
							<#elseif field.harmony_type?lower_case=="datetime">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDateTime());
							</#if>
							<#break />
						<#case "enum">
		${curr.name?uncap_first}.set${field.name?cap_first}(${field.enum.targetEnum}.values()[TestUtils.generateRandomInt(0,${field.enum.targetEnum}.values().length)]);
							<#break />
						<#default>
		//TODO : Manage field type : ${field.harmony_type} / ${FieldsUtils.getJavaType(field)}
					</#switch>
				<#else>
					<#if dataLoader?? && dataLoader>
		ArrayList<${field.relation.targetEntity?cap_first}> ${field.name?uncap_first}s =
			new ArrayList<${field.relation.targetEntity?cap_first}>();
		<#list InheritanceUtils.getAllChildren(entities[field.relation.targetEntity]) as child>
		${field.name?uncap_first}s.addAll(${child.name?cap_first}DataLoader.getInstance(ctx).getMap().values());
		</#list>
						<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		if (!${field.name?uncap_first}s.isEmpty()) {
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first}s.get(TestUtils.generateRandomInt(0, ${field.name?uncap_first}s.size())));
		}
						<#else>
		ArrayList<${field.relation.targetEntity?cap_first}> related${field.name?cap_first}s = new ArrayList<${field.relation.targetEntity?cap_first}>();
		if (!${field.name?uncap_first}s.isEmpty()) {
			related${field.name?cap_first}s.add(${field.name?uncap_first}s.get(TestUtils.generateRandomInt(0, ${field.name?uncap_first}s.size())));
			${curr.name?uncap_first}.set${field.name?cap_first}(related${field.name?cap_first}s);
		}
						</#if>
					<#else>
						<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		${curr.name?uncap_first}.set${field.name?cap_first}(${field.relation.targetEntity}Utils.generateRandom(ctx));
						<#else>
		ArrayList<${field.relation.targetEntity?cap_first}> related${field.name?cap_first}s = new ArrayList<${field.relation.targetEntity?cap_first}>();
		related${field.name?cap_first}s.add(${field.relation.targetEntity}Utils.generateRandom(ctx));
		${curr.name?uncap_first}.set${field.name?cap_first}(related${field.name?cap_first}s);
						</#if>
					</#if>
				</#if>
			</#if>
		</#list>

		return ${curr.name?uncap_first};
	}

	public static boolean equals(${curr.name?cap_first} ${curr.name?uncap_first}1,
			${curr.name?cap_first} ${curr.name?uncap_first}2){
		return equals(${curr.name?uncap_first}1, ${curr.name?uncap_first}2, true);
	}
	
	public static boolean equals(${curr.name?cap_first} ${curr.name?uncap_first}1,
			${curr.name?cap_first} ${curr.name?uncap_first}2,
			boolean checkRecursiveId){
		<#if curr.inheritance?? && curr.inheritance.superclass?? && entities[curr.inheritance.superclass.name]??>
		boolean ret = ${curr.inheritance.superclass.name}Utils.equals(${curr.name?uncap_first}1, ${curr.name?uncap_first}2);
		<#else>
		boolean ret = true;
		</#if>
		Assert.assertNotNull(${curr.name?uncap_first}1);
		Assert.assertNotNull(${curr.name?uncap_first}2);
		if (${curr.name?uncap_first}1!=null && ${curr.name?uncap_first}2 !=null){
		<#list curr.fields?values as field>
			<#if !field.internal && !field.columnResult>
				<#if field.harmony_type?lower_case != "relation">
					<#switch FieldsUtils.getJavaType(field)?lower_case>
						<#case "int">
						<#case "string">
						<#case "integer">
						<#case "float">
						<#case "long">
						<#case "double">
						<#case "float">
						<#case "datetime">
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
							<#break />
						<#case "boolean">
			Assert.assertEquals(${curr.name?uncap_first}1.is${field.name?cap_first}(), ${curr.name?uncap_first}2.is${field.name?cap_first}());
							<#break />
						<#default>
			//TODO : Manage field type : ${field.harmony_type} / ${FieldsUtils.getJavaType(field)}
					</#switch>
				<#else>
			if (${curr.name?uncap_first}1.get${field.name?cap_first}() != null
					&& ${curr.name?uncap_first}2.get${field.name?cap_first}() != null) {
					<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
				if (checkRecursiveId) {
					Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}(),
							${curr.name?uncap_first}2.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}());
				}
					<#else>
				Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().size(),
					${curr.name?uncap_first}2.get${field.name?cap_first}().size());
				if (checkRecursiveId) {
					for (${field.relation.targetEntity} ${field.name?uncap_first}1 : ${curr.name?uncap_first}1.get${field.name?cap_first}()) {
						boolean found = false;
						for (${field.relation.targetEntity} ${field.name?uncap_first}2 : ${curr.name?uncap_first}2.get${field.name?cap_first}()) {
							<#assign target = entities[field.relation.targetEntity] />
							if (<#list IdsUtils.getAllIdsGetters(target) as refId>${field.name?uncap_first}1${refId}<#if MetadataUtils.isPrimitive(target.ids[refId_index])> == <#else>.equals(</#if>${field.name?uncap_first}2${refId}<#if !MetadataUtils.isPrimitive(target.ids[refId_index])>)</#if><#if refId_has_next>
								&& </#if></#list>) {
								found = true;
							}
						}
						Assert.assertTrue(
								String.format(
										"Couldn't find associated ${field.name} (<#list target.ids as id>${id.name} = %s<#if id_has_next>, </#if></#list>) in ${curr.name} (<#list curr_ids as id>${id.name} = %s<#if id_has_next>,</#if></#list>)",
										<#list IdsUtils.getAllIdsGetters(target) as id>${field.name?uncap_first}1${id},
										</#list><#list IdsUtils.getAllIdsGetters(curr) as id>${curr.name?uncap_first}1${id}<#if id_has_next>,
										</#if></#list>),
								found);
					}
				}
					</#if>
			}
				</#if>
			</#if>
		</#list>
		}

		return ret;
	}
}

