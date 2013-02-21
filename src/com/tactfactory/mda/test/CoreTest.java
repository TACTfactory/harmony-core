/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.mda.command.GeneralCommand;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;

public class CoreTest extends CommonTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public final void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public final void loadPlugins() {
		Assert.assertTrue(!CommonTest.getHarmony().getCommands().isEmpty());
		
		Assert.assertNotNull(
				CommonTest.getHarmony().getCommand(GeneralCommand.class));
		Assert.assertNotNull(
				CommonTest.getHarmony().getCommand(ProjectCommand.class));
		Assert.assertNotNull(
				CommonTest.getHarmony().getCommand(OrmCommand.class));
	}
	
	@Test
	public final void list() {
		System.out.println("\nTest List bundle/command");
		System.out.println("########################################"
				 + "#######################################");
		
		CommonTest.getHarmony().findAndExecute(GeneralCommand.LIST, null, null);
	} 
	
	@Test
	public final void help() {
		System.out.println("\nTest Help");
		System.out.println("#######################################"
				 + "########################################");
		
		CommonTest.getHarmony().findAndExecute(GeneralCommand.HELP, null, null);
	}
}
