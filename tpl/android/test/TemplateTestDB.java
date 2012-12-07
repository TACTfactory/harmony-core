package ${local_namespace};

import com.tactfactory.mda.test.demact.data.${name}Adapter;
import com.tactfactory.mda.test.demact.entity.${name};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${name} DataBase Test
 * 
 */
public class ${name}TestDB extends AndroidTestCase {
	private Context ctx;
	private ${name}Adapter adapter;
	private SQLiteDatabase db;
	private ${name} entity;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getContext();
		this.adapter = new ${name}Adapter(this.ctx);
		this.entity = new ${name}();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/** Test case Create Entity */
	public void create() {
		int result = -1;

		this.db = this.adapter.open();
		this.db.beginTransaction();
		try {
			result = (int) this.adapter.insert(this.entity);

			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
			this.adapter.close();
		}

		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Read Entity */
	public void read() {
		int result = -1;

		this.db = this.adapter.open();
		this.db.beginTransaction();
		try {
			this.entity = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation
			if (this.entity != null)
				result = 0;

			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
			this.adapter.close();
		}
		
		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Update Entity */
	public void update() {
		// TODO on all fields
		//this.model.setXxxx(newValue);
		
		int result = -1;

		this.db = this.adapter.open();
		this.db.beginTransaction();
		try {
			result = this.adapter.update(this.entity);

			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
			this.adapter.close();
		}
		
		Assert.assertTrue(result >= 0);

		// TODO on all fields
		//Assert.assertEquals(newValue, this.model.getXxxx()) 
	}
	
	/** Test case Update Entity */
	public void delete() {
		int result = -1;

		this.db = this.adapter.open();
		this.db.beginTransaction();
		try {
			result = this.adapter.remove(this.entity.getId());

			this.db.setTransactionSuccessful();
		} finally {
			this.db.endTransaction();
			this.adapter.close();
		}
		
		Assert.assertTrue(result >= 0);
	}
}
