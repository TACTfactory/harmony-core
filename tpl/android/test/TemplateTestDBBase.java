<#assign curr = entities[current_entity] />
<#function getZeroRelationsEntities>
	<#assign ret = [] />
	<#list entities?values as entity>
		<#if (entity.fields?size!=0 && entity.relations?size==0)>
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
package ${curr.test_namespace};

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

<#assign relatedOrderedEntities = [] />
<#list orderedEntities as entityName>
	<#if entityName==curr.name>
		<#break />
	<#else>
		<#assign relatedOrderedEntities = relatedOrderedEntities + [entityName] />
import ${fixture_namespace}.${entityName}DataLoader;
	</#if>
</#list>
import ${fixture_namespace}.${curr.name}DataLoader;
<#if (relatedOrderedEntities?size>0)>
import ${fixture_namespace}.DataManager;
</#if>

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestDBBase extends AndroidTestCase {
	private Context ctx;

	private ${curr.name}SQLiteAdapter adapter;

	private SQLiteDatabase db;
	private ${curr.name} entity;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getContext();
		
		this.adapter = new ${curr.name}SQLiteAdapter(this.ctx);
		this.db = this.adapter.open();
		this.db.beginTransaction();
		
		<#if (relatedOrderedEntities?size>0)>
		DataManager manager = new DataManager(this.ctx, this.db);
		</#if>
		<#list relatedOrderedEntities as entityName>
		${entityName}DataLoader ${entityName?uncap_first}Loader = new ${entityName}DataLoader(this.ctx);
		${entityName?uncap_first}Loader.getModelFixtures();
		${entityName?uncap_first}Loader.load(manager);
		</#list>
		${curr.name}DataLoader ${curr.name?uncap_first}Loader = new ${curr.name}DataLoader(this.ctx);
		${curr.name?uncap_first}Loader.getModelFixtures();
		
		
		this.entity = (${curr.name})${curr.name?uncap_first}Loader.${curr.name?uncap_first}s.values().toArray()[0];
		
		
		this.entity.setId((int) this.adapter.insert(this.entity));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		
		this.db.endTransaction();
		this.adapter.close();
	}
	
	/** Test case Create Entity */
	public void testCreate() {
		int result = -1;

		result = this.entity.getId();

		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Read Entity */
	public void testRead() {
		${curr.name?cap_first} result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation 
		
		<#list curr.fields as field>
			<#if !field.internal>
				<#if field.type=="int" || field.type=="integer" || field.type=="long" || field.type=="double" || field.type=="zipcode" || field.type=="ean">
		Assert.assertTrue(result.get${field.name?cap_first}()==this.entity.get${field.name?cap_first}());
				<#elseif field.type=="boolean">
		Assert.assertTrue(result.is${field.name?cap_first}()==this.entity.is${field.name?cap_first}());		
				<#else>
		Assert.assertTrue(result.get${field.name?cap_first}().equals(this.entity.get${field.name?cap_first}()));
				</#if>
			</#if>
		</#list>
	}
	
	/** Test case Update Entity */
	public void testUpdate() {
		// TODO on all fields
		//this.model.setXxxx(newValue);
		
		int result = -1;

		result = this.adapter.update(this.entity);

		Assert.assertTrue(result >= 0);

		// TODO on all fields
		//Assert.assertEquals(newValue, this.model.getXxxx()) 
	}
	
	/** Test case Update Entity */
	public void testDelete() {
		int result = -1;

		result = this.adapter.remove(this.entity.getId());
		
		Assert.assertTrue(result >= 0);
	}
}
