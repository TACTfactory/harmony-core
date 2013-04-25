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

import ${project_namespace}.provider.${project_name?cap_first}Provider;
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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestProviderBase extends AndroidTestCase {
	protected Context ctx;

	protected ${curr.name}SQLiteAdapter adapter;

	protected SQLiteDatabase db;
	protected ${curr.name} entity;
	protected ContentResolver provider;

	private ArrayList<${curr.name}> entities;

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
		
		this.db.setTransactionSuccessful();
		this.db.endTransaction();
		this.adapter.close();		
		
		this.entities = new ArrayList<${curr.name?cap_first}>(${curr.name?cap_first}DataLoader.getInstance(this.ctx).items.values());
		if (this.entities.size()>0){
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
		
		this.provider = this.getContext().getContentResolver();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		
		this.db = this.adapter.open();
		this.db.beginTransaction();
		${project_name?cap_first}SQLiteOpenHelper.clearDatabase(this.db);
		this.db.setTransactionSuccessful();
		this.db.endTransaction();
		this.adapter.close();
	}
	
	/** Test case Create Entity */
	public void testCreate() {
		Uri result = null;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = this.generateRandom();

			try {
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				values.remove(${curr.name}SQLiteAdapter.COL_ID);
				result = this.provider.insert(${project_name?cap_first}Provider.${curr.name?upper_case}_URI, values);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertNotNull(result);
			Assert.assertTrue(Integer.valueOf(result.getEncodedPath().substring(result.getEncodedPath().lastIndexOf("/")+1)) > 0);
		}
	}
	
	/** Test case Read Entity */
	public void testRead() {
		${curr.name} result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(${project_name?cap_first}Provider.${curr.name?upper_case}_URI + "/" + this.entity.getId()), this.adapter.getCols(), null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			${curr.name}TestProviderBase.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	public void testReadAll() {
		ArrayList<${curr.name}> result = null;
		try {
			Cursor c = this.provider.query(${project_name?cap_first}Provider.${curr.name?upper_case}_URI, this.adapter.getCols(), null, null, null);
			result = this.adapter.cursorToItems(c);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertNotNull(result);
		if (result != null) {
			Assert.assertEquals(result.size(), this.entities.size());
		}
	}
	
	/** Test case Update Entity */
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = this.generateRandom();

			try {
				${curr.name?uncap_first}.setId(this.entity.getId());
			
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				result = this.provider.update(
					Uri.parse(${project_name?cap_first}Provider.${curr.name?upper_case}_URI 
						+ "/" 
						+ ${curr.name?uncap_first}.getId()), 
					values, 
					null, 
					null);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertTrue(result >= 0);
		}
	}

	/** Test case UpdateAll Entity */
	public void testUpdateAll() {
		int result = -1;
		if (this.entities.size() > 0) {
			${curr.name} ${curr.name?uncap_first} = this.generateRandom();

			try {
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				values.remove(${curr.name}SQLiteAdapter.COL_ID);
				<#list curr.fields as field>
					<#if field.unique?? && field.unique>
				values.remove(${curr.name}SQLiteAdapter.COL_${field.name?upper_case});
					</#if>
				</#list>
			
				result = this.provider.update(${project_name?cap_first}Provider.${curr.name?upper_case}_URI, values, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertEquals(result, this.entities.size());
		}
	}
	
	/** Test case Delete Entity */
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			try {
				result = this.provider.delete(Uri.parse(${project_name?cap_first}Provider.${curr.name?upper_case}_URI + "/" + this.entity.getId()), null, null);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(result >= 0);
		}

	}
	
	/** Test case DeleteAll Entity */
	public void testDeleteAll() {
		int result = -1;
		if (this.entities.size() > 0) {

			try {
				result = this.provider.delete(${project_name?cap_first}Provider.${curr.name?upper_case}_URI, null, null);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertEquals(result, this.entities.size());
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
		ArrayList<${field.relation.targetEntity?cap_first}> ${field.name?uncap_first}s = ${field.name?uncap_first}Adapter.cursorToItems(this.provider.query(${project_name?cap_first}Provider.${field.relation.targetEntity?upper_case}_URI, ${field.name?uncap_first}Adapter.getCols(), null, null, null));//= ${field.name?uncap_first}Adapter.getAll();
		if (!${field.name?uncap_first}s.isEmpty()) {
			${curr.name?uncap_first}.set${field.name?cap_first}(${field.name?uncap_first}s.get(TestUtils.generateRandomInt(0, ${field.name?uncap_first}s.size())));
		}
					<#else>
		${field.relation.targetEntity?cap_first}SQLiteAdapter ${field.name?uncap_first}Adapter = new ${field.relation.targetEntity?cap_first}SQLiteAdapter(this.ctx);
		ArrayList<${field.relation.targetEntity?cap_first}> all${field.name?cap_first}s = ${field.name?uncap_first}Adapter.cursorToItems(this.provider.query(${project_name?cap_first}Provider.${field.relation.targetEntity?upper_case}_URI, ${field.name?uncap_first}Adapter.getCols(), null, null, null));//= ${field.name?uncap_first}Adapter.getAll();
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
