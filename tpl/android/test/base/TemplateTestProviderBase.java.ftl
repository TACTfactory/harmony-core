<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.test_namespace}.base;

import android.test.suitebuilder.annotation.SmallTest;

import ${project_namespace}.provider.${curr.name?cap_first}ProviderAdapter;

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

<#if dataLoader?? && dataLoader>
import ${fixture_namespace}.${curr.name?cap_first}DataLoader;
</#if>

import java.util.ArrayList;
import ${curr.test_namespace}.utils.*;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestProviderBase extends TestDBBase {
	protected Context ctx;

	protected ${curr.name}SQLiteAdapter adapter;

	protected ${curr.name} entity;
	protected ContentResolver provider;

	protected ArrayList<${curr.name}> entities;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getMockContext();
		
		this.adapter = new ${curr.name}SQLiteAdapter(this.ctx);
		
		<#if dataLoader?? && dataLoader>
		this.entities = new ArrayList<${curr.name?cap_first}>(${curr.name?cap_first}DataLoader.getInstance(this.ctx).getMap().values());
		if (this.entities.size()>0) {
			this.entity = this.entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
		</#if>		
		this.provider = this.getMockContext().getContentResolver();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		Uri result = null;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				values.remove(${curr.name}SQLiteAdapter.COL_ID);
				result = this.provider.insert(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, values);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertNotNull(result);
			Assert.assertTrue(Integer.valueOf(result.getEncodedPath().substring(result.getEncodedPath().lastIndexOf("/")+1)) > 0);
		}
	}
	
	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		${curr.name} result = null;

		if (this.entity != null) {
			try {
				Cursor c = this.provider.query(Uri.parse(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI + "/" + this.entity.getId()), null, null, null, null);
				c.moveToFirst();
				result = this.adapter.cursorToItem(c);
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			${curr.name}Utils.equals(this.entity, result);
		}
	}

	/** Test case ReadAll Entity */
	@SmallTest
	public void testReadAll() {
		ArrayList<${curr.name}> result = null;
		try {
			Cursor c = this.provider.query(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, this.adapter.getCols(), null, null, null);
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
	@SmallTest
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				${curr.name?uncap_first}.setId(this.entity.getId());
			
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				result = this.provider.update(
					Uri.parse(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI 
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
	@SmallTest
	public void testUpdateAll() {
		int result = -1;
		if (this.entities.size() > 0) {
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first}<#list curr.relations as relation><#if relation.relation.type=="ManyToOne" && relation.internal>, 0</#if></#list>);
				values.remove(${curr.name}SQLiteAdapter.COL_ID);
				<#list curr.fields?values as field>
					<#if field.unique?? && field.unique>
				values.remove(${curr.name}SQLiteAdapter.COL_${field.name?upper_case});
					</#if>
				</#list>
			
				result = this.provider.update(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, values, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertEquals(result, this.entities.size());
		}
	}
	
	/** Test case Delete Entity */
	@SmallTest
	public void testDelete() {
		int result = -1;
		if (this.entity != null) {
			try {
				result = this.provider.delete(Uri.parse(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI + "/" + this.entity.getId()), null, null);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			Assert.assertTrue(result >= 0);
		}

	}
	
	/** Test case DeleteAll Entity */
	@SmallTest
	public void testDeleteAll() {
		int result = -1;
		if (this.entities.size() > 0) {

			try {
				result = this.provider.delete(${curr.name?cap_first}ProviderAdapter.${curr.name?upper_case}_URI, null, null);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			Assert.assertEquals(result, this.entities.size());
		}
	}
}
