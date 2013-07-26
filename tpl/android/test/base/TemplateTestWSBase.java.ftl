<#assign curr = entities[current_entity] />
<@header?interpret />
package ${curr.test_namespace}.base;

import ${curr.test_namespace}.*;


import ${curr.namespace}.data.${curr.name}WebServiceClientAdapter;
import ${curr.namespace}.entity.${curr.name};

import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${curr.name} Web Service Test
 * 
 * @see android.app.Fragment
 */
public class ${curr.name}TestWSBase extends /*TestDBBase*/ AndroidTestCase {
	private Context ctx;
	private ${curr.name} model;
	private ${curr.name}WebServiceClientAdapter web;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getContext();
		this.web = new ${curr.name}WebServiceClientAdapter(this.ctx);
		
		this.model = new ${curr.name}();
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
	 * @see ${curr.name}Ws#login(Account)
	 */
	/*public void authentificate() {		
		int result = this.ws.login(this.me);
		Assert.assertEquals(0, result);
	}*/
	
	/** Test case Create Entity */
	public void create() {
		int result = this.web.insert(this.model);
		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Read Entity */
	public void read() {
		int result = this.web.get(this.model); // TODO Generate by @Id annotation
		Assert.assertTrue(result >= 0);
	}
	
	/** Test case Update Entity */
	public void update() {
		// TODO on all fields
		//this.model.setXxxx(newValue);
		
		int result = this.web.update(this.model);
		Assert.assertTrue(result >= 0);
		
		result = this.web.get(this.model);
		// TODO on all fields
		//Assert.assertEquals(newValue, this.model.getXxxx()) 
	}
	
	/** Test case Update Entity */
	public void delete() {
		int result = this.web.delete(this.model);
		
		result = this.web.get(this.model);
		Assert.assertTrue(result < 0);
	}

}
