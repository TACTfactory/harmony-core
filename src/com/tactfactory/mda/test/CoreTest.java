/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test;

import org.junit.*;

import com.tactfactory.mda.command.*;

public class CoreTest extends CommonTest {
	
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
		Assert.assertTrue(!this.harmony.getCommands().isEmpty());
		
		Assert.assertNotNull(this.harmony.getCommand(GeneralCommand.class));
		Assert.assertNotNull(this.harmony.getCommand(ProjectCommand.class));
		Assert.assertNotNull(this.harmony.getCommand(OrmCommand.class));
	}
	
	@Test
	public void list() {
		System.out.println("\nTest List bundle/command");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(GeneralCommand.LIST, null, null);
	} 
	
	@Test
	public void help() {
		System.out.println("\nTest Help");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(GeneralCommand.HELP, null, null);
	}
}
