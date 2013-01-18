<#assign curr = entities[current_entity] />
package ${curr.test_namespace};


import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

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
		this.entity = new ${curr.name}();
		
		this.db = this.adapter.open();
		this.db.beginTransaction();
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
		int result = -1;

		this.entity = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation
		if (this.entity != null)
			result = 0;
		
		Assert.assertTrue(result >= 0);
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
