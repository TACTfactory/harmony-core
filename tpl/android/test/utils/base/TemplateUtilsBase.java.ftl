<#function isInArray array val>
	<#list array as val_ref>
		<#if val_ref==val>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#assign curr = entities[current_entity] />
package ${curr.test_namespace}.utils.base;

import android.content.Context;
import junit.framework.Assert;
import ${curr.namespace}.entity.${curr.name};
import ${curr.test_namespace}.utils.*;
<#assign importList = [] />
<#list curr.relations as relation>
	<#if !relation.internal>
		<#if !isInArray(importList, relation.relation.targetEntity)>
import ${curr.namespace}.entity.${relation.relation.targetEntity?cap_first};
import ${fixture_namespace}.${relation.relation.targetEntity?cap_first}DataLoader;
			<#assign importList = importList + [relation.relation.targetEntity] />
		</#if>
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
		
		<#list curr.fields as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type=="string" || field.type=="text" || field.type=="phone" || field.type=="login" || field.type=="password">
		${curr.name?uncap_first}.set${field.name?cap_first}("${field.name?uncap_first}_"+TestUtils.generateRandomString(10));
					<#elseif field.type=="int" || field.type=="zipcode" || field.type=="ean">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomInt(0,100));
					<#elseif field.type=="boolean">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomBool());
					<#elseif field.type=="double">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDouble(0,100));
					<#elseif field.type=="float">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomFloat(0,100));
					<#elseif field.type=="date">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDate());
					<#elseif field.type=="time">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomTime());
					<#elseif field.type=="datetime">
		${curr.name?uncap_first}.set${field.name?cap_first}(TestUtils.generateRandomDateTime());
					<#else>
						<#if (field.columnDefinition?lower_case=="integer" || field.columnDefinition?lower_case=="int")>
		${curr.name?uncap_first}.set${field.name?cap_first}(${curr.name}.${field.type}.fromValue(TestUtils.generateRandomInt(0,100)));
						<#else>
		${curr.name?uncap_first}.set${field.name?cap_first}(${curr.name}.${field.type}.fromValue(TestUtils.generateRandomString(10)));		
						</#if>
					</#if>
				<#else>
		ArrayList<${field.relation.targetEntity?cap_first}> ${field.name?uncap_first}s = 
			new ArrayList<${field.relation.targetEntity?cap_first}>(${field.relation.targetEntity?cap_first}DataLoader.getInstance(ctx).items.values());
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
		<#list curr.fields as field>
			<#if !field.internal>
				<#if !field.relation??>
					<#if field.type=="int" || field.type=="integer" || field.type=="long" || field.type=="double" || field.type=="float" || field.type=="zipcode" || field.type=="ean">
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
					<#elseif field.type=="boolean">
			Assert.assertEquals(${curr.name?uncap_first}1.is${field.name?cap_first}(), ${curr.name?uncap_first}2.is${field.name?cap_first}());		
					<#elseif field.type=="date" || field.type=="time" || field.type=="datetime">
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
					<#else>
			Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}(), ${curr.name?uncap_first}2.get${field.name?cap_first}());
					</#if>
				<#else>
			if (${curr.name?uncap_first}1.get${field.name?cap_first}() != null 
					&& ${curr.name?uncap_first}2.get${field.name?cap_first}() != null) {
					<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
				Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().getId(),
						${curr.name?uncap_first}2.get${field.name?cap_first}().getId());

					<#else>
				Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().size(), 
					${curr.name?uncap_first}2.get${field.name?cap_first}().size());
				for (int i=0;i<${curr.name?uncap_first}1.get${field.name?cap_first}().size();i++){
					Assert.assertEquals(${curr.name?uncap_first}1.get${field.name?cap_first}().get(i).getId(),
								${curr.name?uncap_first}2.get${field.name?cap_first}().get(i).getId());
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

