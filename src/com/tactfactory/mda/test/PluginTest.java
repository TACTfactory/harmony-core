package com.tactfactory.mda.test;

import org.junit.*;

import com.tactfactory.mda.command.*;

public class PluginTest extends CommonTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void loadPlugins() {
		Assert.assertTrue(!this.harmony.bootstrap.isEmpty());
		
		Assert.assertNotNull(this.harmony.bootstrap.get(GeneralCommand.class));
		Assert.assertNotNull(this.harmony.bootstrap.get(ProjectCommand.class));
		Assert.assertNotNull(this.harmony.bootstrap.get(OrmCommand.class));
	}
	
	@Test
	public void list() {
		System.out.println("Test List bundle/command");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(GeneralCommand.LIST, null, null);
	} 
	
	@Test
	public void help() {
		System.out.println("Test Help");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(GeneralCommand.HELP, null, null);
	}
}
