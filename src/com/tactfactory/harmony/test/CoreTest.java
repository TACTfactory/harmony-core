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
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Test class for the Harmony core.
 */
public class CoreTest extends CommonTest {

	/**
	 * Initialization.
	 * @throws Exception if something bad happened.
	 */
	@BeforeClass
	public static void setUpBefore() {
		CommonTest.setUpBefore();
	}

	@Before
	@Override
	public final void setUp() {
		super.setUp();
	}

	@After
	@Override
	public final void tearDown() {
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
	    ConsoleUtils.display("\nTest List bundle/command\n" + SHARP_DELIMITOR);

		CommonTest.getHarmony().findAndExecute(GeneralCommand.LIST, null, null);
	}

	/**
	 * Display Harmony help.
	 */
	@Test
	public final void help() {
	    ConsoleUtils.display("\nTest Help\n" + SHARP_DELIMITOR);

		CommonTest.getHarmony().findAndExecute(GeneralCommand.HELP, null, null);
	}
}
