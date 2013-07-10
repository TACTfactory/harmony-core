<#assign curr = entities[current_entity] />
package ${curr.test_namespace}.base;

import android.test.suitebuilder.annotation.SmallTest;

import ${curr.namespace}.data.${curr.name}SQLiteAdapter;
import ${curr.namespace}.entity.${curr.name};

<#if dataLoader?? && dataLoader>
import ${fixture_namespace}.${curr.name?cap_first}DataLoader;
</#if>

import java.util.ArrayList;

import ${data_namespace}.${project_name?cap_first}SQLiteOpenHelper;
import ${curr.test_namespace}.utils.*;
import android.content.Context;
import junit.framework.Assert;

/** ${curr.name} database test abstract class <br/>
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony. 
 * You should edit ${curr.name}TestDB class instead of this one or you will lose all your modifications.</i></b>
 */
public abstract class ${curr.name}TestDBBase extends TestDBBase {
	protected Context ctx;

	protected ${curr.name}SQLiteAdapter adapter;

	protected ${curr.name} entity;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.ctx = this.getMockContext();
		
		this.adapter = new ${curr.name}SQLiteAdapter(this.ctx);
		this.adapter.open();
		
		<#if dataLoader?? && dataLoader>		
		ArrayList<${curr.name?cap_first}> entities = new ArrayList<${curr.name?cap_first}>(${curr.name?cap_first}DataLoader.getInstance(this.ctx).items.values());
		if (entities.size()>0){
			this.entity = entities.get(TestUtils.generateRandomInt(0,entities.size()-1));
		}
		</#if>
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		this.adapter.close();

		super.tearDown();
	}
	
	/** Test case Create Entity */
	@SmallTest
	public void testCreate() {
		int result = -1;
		if (this.entity != null) {
			${curr.name?cap_first} ${curr.name?uncap_first} = ${curr.name?cap_first}Utils.generateRandom(this.ctx);
	
			result = (int)this.adapter.insert(${curr.name?uncap_first});
	
			Assert.assertTrue(result >= 0);
		}
	}
	
	/** Test case Read Entity */
	@SmallTest
	public void testRead() {
		${curr.name?cap_first} result = null;
		if (this.entity != null) {
			result = this.adapter.getByID(this.entity.getId()); // TODO Generate by @Id annotation
			
			${curr.name?cap_first}Utils.equals(result, this.entity); 
		}
	}
	
	/** Test case Update Entity */
	@SmallTest
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
	@SmallTest
	public void testDelete() {
		int result = -1; 
		if (this.entity != null) {
			result = (int)this.adapter.remove(this.entity.getId());
			Assert.assertTrue(result >= 0);
		}
	}
}
