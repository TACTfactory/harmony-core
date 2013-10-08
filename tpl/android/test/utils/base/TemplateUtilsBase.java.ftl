<#include utilityPath + "all_imports.ftl" />
<#assign curr = entities[current_entity] />
<#assign inherited = false />
<#if (curr.extends?? && entities[curr.extends]??)>
	<#assign inherited = true />
</#if>
<@header?interpret />
package ${curr.test_namespace}.utils.base;

import android.content.Context;
import junit.framework.Assert;
import ${curr.namespace}.entity.${curr.name};
import ${curr.test_namespace}.utils.*;

<#if (inherited)>import ${entity_namespace}.${curr.extends};</#if>

<#assign importList = [] />
<#list curr.relations as relation>
	<#if !relation.internal>
		<#if !Utils.isInArray(importList, relation.relation.targetEntity)>
import ${curr.namespace}.entity.${relation.relation.targetEntity?cap_first};
import ${fixture_namespace}.${relation.relation.targetEntity?cap_first}DataLoader;
			<#assign importList = importList + [relation.relation.targetEntity] />
		</#if>
	</#if>
</#list>
<#list curr.fields?values as field>
	<#if field.harmony_type?lower_case == "enum">
		<#assign enumClass = enums[field.type] />
import ${entity_namespace}.${InheritanceUtils.getCompleteNamespace(enumClass)};
	</#if>
</#list>
<#if (importList?size > 0)>
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
		${curr.extends?cap_first} ${curr.extends?uncap_first} = ${curr.extends?cap_first}Utils.generateRandom(ctx);
			<#list entities[curr.extends].fields?values as field>
		${curr.name?uncap_first}.set${field.name?cap_first}(${curr.extends?uncap_first}.<#if field.type?lower_case == "boolean">is<#else>get</#if>${field.name?cap_first}());
			</#list>
		</#if>

		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type?lower_case=="string">
		${curr.name?uncap_first}.set${field.name?cap_first}("${field.name?uncap_first}_"+TestUtils.generateRandomString(10));
					<#elseif (field.type?lower_case == "int" || field.type?lower_case == "integer")>
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomInt(0,100)<#if (field.id?? && field.id)> + 1</#if>);
					<#elseif field.type?lower_case=="boolean">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomBool());
					<#elseif field.type?lower_case=="double">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDouble(0,100));
					<#elseif field.type?lower_case=="float">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomFloat(0,100));
					<#elseif field.type?lower_case=="short">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomShort());
					<#elseif field.type?lower_case=="char" || field.type?lower_case=="character">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomChar());
					<#elseif field.type?lower_case=="byte">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomByte());
					<#elseif field.type?lower_case=="datetime">
						<#if field.harmony_type?lower_case=="date">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDate());
						<#elseif field.harmony_type?lower_case=="time">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomTime());
						<#elseif field.harmony_type?lower_case=="datetime">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDateTime());
						</#if>
					<#elseif (field.harmony_type?lower_case == "enum")>
		${curr.name?uncap_first}.set${field.name?cap_first}(${field.type}.values()[TestUtils.generateRandomInt(0,${field.type}.values().length)]);
					</#if>
				<#else>
		ArrayList<${field.relation.targetEntity?cap_first}> ${field.name?uncap_first}s =
			new ArrayList<${field.relation.targetEntity?cap_first}>(${field.relation.targetEntity?cap_first}DataLoader.getInstance(ctx).getMap().values());
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
				</#if>
			</#if>
		</#list>

		return ${curr.name?uncap_first};
	}

	public static boolean equals(${curr.name?cap_first} ${curr.name?uncap_first}1, ${curr.name?cap_first} ${curr.name?uncap_first}2){
		boolean ret = true;
		Assert.assertNotNull(${curr.name?uncap_first}1);
		Assert.assertNotNull(${curr.name?uncap_first}2);
		if (${curr.name?uncap_first}1!=null && ${curr.name?uncap_first}2 !=null){
		<#list curr.fields?values as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type?lower_case=="int" || field.type?lower_case=="integer" || field.type?lower_case=="long" || field.type?lower_case=="double" || field.type?lower_case=="float" || field.type?lower_case=="zipcode" || field.type?lower_case=="ean">
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
					<#elseif field.type?lower_case=="boolean">
			Assert.assertEquals(${curr.name?uncap_first}1.is${field.name?cap_first}(), ${curr.name?uncap_first}2.is${field.name?cap_first}());
					<#elseif field.type?lower_case=="date" || field.type?lower_case=="time" || field.type?lower_case=="datetime">
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
					<#else>
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
					</#if>
				<#else>
			if (${curr.name?uncap_first}1.get${field.name?cap_first}() != null
					&& ${curr.name?uncap_first}2.get${field.name?cap_first}() != null) {
					<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
				Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}(),
						${curr.name?uncap_first}2.get${field.name?cap_first}().get${entities[field.relation.targetEntity].ids[0].name?cap_first}());

					<#else>
				Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().size(),
					${curr.name?uncap_first}2.get${field.name?cap_first}().size());
				for (int i=0;i<${curr.name?uncap_first}1.get${field.name?cap_first}().size();i++){
					Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().get(i).get${entities[field.relation.targetEntity].ids[0].name?cap_first}(),
								${curr.name?uncap_first}2.get${field.name?cap_first}().get(i).get${entities[field.relation.targetEntity].ids[0].name?cap_first}());
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

