/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.test.CommonTest;

public class OrmUpdateEntitiesTest extends CommonTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
		initAll();
	}
	
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
		
		// File dirproj = new File(String.format("%s/android", Harmony.PATH_PROJECT));
		//TODO : enable !! FileUtils.deleteRecursive(dirproj);
	}
	
	private static void initAll() {
		harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		// The real test
		harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
	}
	
	@Test
	public void all() {
		
	}
}
