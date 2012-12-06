package ${localnamespace};

import android.content.Context;
import android.test.AndroidTestCase;

import junit.framework.Assert;

/** ${name} DataBase Test
 * 
 */
public class ${name}TestDB extends AndroidTestCase {
	private Context ctx;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getContext();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
}
