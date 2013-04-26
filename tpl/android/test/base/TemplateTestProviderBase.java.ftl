<#assign curr = entities[current_entity] />
package ${curr.test_namespace}.base;

import ${project_namespace}.provider.${project_name?cap_first}Provider;

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

import ${fixture_namespace}.${curr.name?cap_first}DataLoader;
import ${fixture_namespace}.DataLoader;

import java.util.ArrayList;
import ${curr.test_namespace}.utils.*;

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
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first});
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
		
			${curr.name}Utils.equals(this.entity, result);
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
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				${curr.name?uncap_first}.setId(this.entity.getId());
			
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first});
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
			${curr.name} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);

			try {
				ContentValues values = this.adapter.itemToContentValues(${curr.name?uncap_first});
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
}
