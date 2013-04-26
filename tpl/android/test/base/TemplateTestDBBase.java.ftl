<#assign curr = entities[current_entity] />
<#function hasOnlyRecursiveRelations entity>
	<#list entity.relations as relation>
		<#if relation.relation.targetEntity!=entity.name> 
			<#return false>
		</#if>
	</#list>
	<#return true>
</#function>
<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if (entity.fields?size!=0 && hasOnlyRecursiveRelations(entity))>
			<#assign ret = ret + [entity.name]>
		</#if>
	</#list>
	<#return ret />
</#function>
<#function isInArray array val>
	<#list array as val_ref>
		<#if val_ref==val>
			<#return true />
		</#if>
	</#list>
	<#return false />
</#function>
<#function isOnlyDependantOf entity entity_list>
	<#list entity.relations as rel>
		<#if rel.relation.type=="ManyToOne">
			<#if !isInArray(entity_list, rel.relation.targetEntity)>
				<#return false />
			</#if>
		</#if>	
	</#list>
	<#return true />
</#function>
<#function orderEntitiesByRelation>
	<#assign ret = getZeroRelationsEntities() />
	<#assign maxLoop = entities?size />
	<#list 1..maxLoop as i>
		<#list entities?values as entity>	
			<#if (entity.fields?size>0)>
				<#if !isInArray(ret, entity.name)>
					<#if isOnlyDependantOf(entity, ret)>
						<#assign ret = ret + [entity.name] />
					</#if>
				</#if>
			</#if>
		</#list>
	</#list>
	<#return ret>
</#function>
<#assign orderedEntities = orderEntitiesByRelation() />
package ${curr.test_namespace}.base;

import ${curr.test_namespace}.*;

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

<#assign importList = [] />
<#list curr.relations as relation>
	<#if !relation.internal>
		<#if !isInArray(importList, relation.relation.targetEntity)>
import ${curr.namespace}.entity.${relation.relation.targetEntity?cap_first};
import ${data_namespace}.${relation.relation.targetEntity?cap_first}SQLiteAdapter;
			<#assign importList = importList + [relation.relation.targetEntity] />
		</#if>
	</#if>
</#list>


import ${fixture_namespace}.${curr.name?cap_first}DataLoader;
import ${fixture_namespace}.DataLoader;

import java.util.ArrayList;

import ${data_namespace}.${project_name?cap_first}SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestDBBase extends AndroidTestCase {
	protected Context ctx;

	protected ${curr.name}SQLiteAdapter adapter;

	protected SQLiteDatabase db;
	protected ${curr.name} entity;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getContext();
		
		this.adapter = new ${curr.name}SQLiteAdapter(this.ctx);
		this.db = this.adapter.open();
		${project_name?cap_first}SQLiteOpenHelper.clearDatabase(this.db);
		this.db.beginTransaction();
		
		DataLoader dataLoader = new DataLoader(this.ctx);
		dataLoader.loadData(this.db, DataLoader.MODE_APP | DataLoader.MODE_DEBUG | DataLoader.MODE_TEST);
		
		
		ArrayList<${curr.name?cap_first}> entities = new ArrayList<${curr.name?cap_first}>(${curr.name?cap_first}DataLoader.getInstance(this.ctx).items.values());
		if (entities.size()>0){
			this.entity = entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		
		this.db.endTransaction();
		${project_name?cap_first}SQLiteOpenHelper.clearDatabase(this.db);
		this.adapter.close();
	}
	
	/** Test case Create Entity */
	public void testCreate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name?cap_first} ${curr.name?uncap_first} = this.generateRandom();
			
	
			result = (int)this.adapter.insert(${curr.name?uncap_first});
	
			Assert.assertTrue(result >= 0);
		}
	}
	
	/** Test case Read Entity */
	public void testRead() {
		${curr.name?cap_first} result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation
			
			equals(result, this.entity); 
		}
	}
	
	/** Test case Update Entity */
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name?cap_first} ${curr.name?uncap_first} = generateRandom();
			${curr.name?uncap_first}.setId(this.entity.getId()); // TODO Generate by @Id annotation 
		
			result = (int)this.adapter.update(${curr.name?uncap_first});
			
			Assert.assertTrue(result >= 0);
		}
	}
	
	/** Test case Update Entity */
	public void testDelete() {
		int result = -1; 
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId());
			Assert.assertTrue(result >= 0);
		}
	}
	
	// If you have enums, you may have to override this method to generate the random enums values
	/**
	 * Generate a random entity
	 * 
	 * @return The randomly generated entity
	 */
	protected ${curr.name?cap_first} generateRandom(){
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
						<#if (field.columnDefinition=="integer" || field.columnDefinition=="int")>
		${curr.name?uncap_first}.set${field.name?cap_first}(${curr.name}.${field.type}.fromValue(TestUtils.generateRandomInt(0,100)));
						<#else>
		${curr.name?uncap_first}.set${field.name?cap_first}(${curr.name}.${field.type}.fromValue(TestUtils.generateRandomString()));		
						</#if>
					</#if>
				<#else>
					<#if field.relation.type=="OneToOne" || field.relation.type=="ManyToOne">
		${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.name?uncap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.ctx);
		${field.name?uncap_first}Adapter.open(this.db);
		ArrayList<${field.relation.targetEntity?cap_first}> ${field.name?uncap_first}s = ${field.name?uncap_first}Adapter.getAll();
		if (!${field.name?uncap_first}s.isEmpty()) {
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first}s.get(TestUtils.generateRandomInt(0, ${field.name?uncap_first}s.size())));
		}
					<#else>
		${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.name?uncap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.ctx);
		${field.name?uncap_first}Adapter.open(this.db);
		ArrayList<${field.relation.targetEntity?cap_first}> all${field.name?cap_first}s = ${field.name?uncap_first}Adapter.getAll();
		ArrayList<${field.relation.targetEntity?cap_first}> ${field.name?uncap_first}s = new ArrayList<${field.relation.targetEntity?cap_first}>();
		if (!all${field.name?cap_first}s.isEmpty()) {
			${field.name?uncap_first}s.add(all${field.name?cap_first}s.get(TestUtils.generateRandomInt(0, ${field.name?uncap_first}s.size())));
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first}s);
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
