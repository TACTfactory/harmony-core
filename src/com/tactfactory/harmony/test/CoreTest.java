/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.harmony.command.GeneralCommand;
import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;

/**
 * Test class for the Harmony core.
 */
public class CoreTest extends CommonTest {

	/**
	 * Initialization.
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
	}

	@Before
	@Override
	public final void setUp() throws Exception {
		super.setUp();
	}

	@After
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Tests the plugin loading.
	 */
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

	/**
	 * List all the available commands.
	 */
	@Test
	public final void list() {
		System.out.println("\nTest List bundle/command");
		System.out.println("########################################"
				 + "#######################################");

		CommonTest.getHarmony().findAndExecute(GeneralCommand.LIST, null, null);
	}

	/**
	 * Display Harmony help.
	 */
	@Test
	public final void help() {
		System.out.println("\nTest Help");
		System.out.println("#######################################"
				 + "########################################");

		CommonTest.getHarmony().findAndExecute(GeneralCommand.HELP, null, null);
	}
}
