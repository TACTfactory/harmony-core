<#assign curr = entities[current_entity] />
package ${curr.test_namespace}.base;

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

import ${fixture_namespace}.${curr.name?cap_first}DataLoader;
import ${fixture_namespace}.DataLoader;

import java.util.ArrayList;

import ${data_namespace}.${project_name?cap_first}SQLiteOpenHelper;
import ${curr.test_namespace}.utils.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestDBBase extends /*TestDBBase*/ AndroidTestCase {
	protected Context ctx;

	protected ${curr.name}SQLiteAdapter adapter;

	protected SQLiteDatabase db;
	protected ${curr.name} entity;
	protected DataLoader dataLoader;

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
		
		this.dataLoader = new DataLoader(this.ctx);
		this.dataLoader.clean();
		this.dataLoader.loadData(this.db, DataLoader.MODE_APP | DataLoader.MODE_DEBUG | DataLoader.MODE_TEST);
		
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
		this.dataLoader.clean();
		this.adapter.close();
	}
	
	/** Test case Create Entity */
	public void testCreate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name?cap_first} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);
			
	
			result = (int)this.adapter.insert(${curr.name?uncap_first});
	
			Assert.assertTrue(result >= 0);
		}
	}
	
	/** Test case Read Entity */
	public void testRead() {
		${curr.name?cap_first} result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation
			
			${curr.name?cap_first}Utils.equals(result, this.entity); 
		}
	}
	
	/** Test case Update Entity */
	public void testUpdate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name?cap_first} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);
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
}
