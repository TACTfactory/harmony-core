package ${test_namespace};

import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${name} Web Service Test
 * 
 * @see android.app.Fragment
 */
public class ${name}TestWS extends AndroidTestCase {
	private Context ctx;
	private ${name} model;
	private ${name}WS web;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getContext();
		this.web = new ${name}WS(this.ctx);
		
		this.model = new ${name}();
		// TODO initial values of test
		//this.model.setXxxx();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/* (non-Javadoc)
	 * @see ${localnamespace}.${name}Ws#login(Account)
	 */
	/*public void authentificate() {		
		int result = this.ws.login(this.me);
		Assert.assertEquals(0, result);
	}*/
	
	/** Test case Create Entity */
	public void create() {
		int result = this.web.create(this.model);
		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Read Entity */
	public void read() {
		int result = this.web.get(this.model.getId()); // TODO Generate by @Id annotation
		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Update Entity */
	public void update() {
		// TODO on all fields
		//this.model.setXxxx(newValue);
		
		int result = this.web.update(this.model);
		Assert.assertTrue(result >= 0);
		
		result = this.web.read(this.model.getId());
		// TODO on all fields
		//Assert.assertEquals(newValue, this.model.getXxxx()) 
	}
	
	/** Test case Update Entity */
	public void delete() {
		int result = this.web.delete(this.model);
		
		result = this.web.read(this.model.getId());
		Assert.assertTrue(result < 0);
	}

}
