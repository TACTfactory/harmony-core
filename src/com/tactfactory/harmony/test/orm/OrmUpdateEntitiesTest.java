/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.orm;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.test.CommonTest;

@RunWith(Parameterized.class)
/**
 * Entities updates test class.
 *
 */
public class OrmUpdateEntitiesTest extends CommonTest {


	public OrmUpdateEntitiesTest(ApplicationMetadata currentMetadata)
	        throws Exception {
		super(currentMetadata);
	}

	@Override
	public void setUpBeforeNewParameter() throws Exception {
		super.setUpBeforeNewParameter();
		
		this.initAll();
	}

	@Before
	@Override
	public final void setUp() throws RuntimeException {
		super.setUp();
	}

	@After
	@Override
	public final void tearDown() throws RuntimeException {
		super.tearDown();

		//TODO : enable !! FileUtils.deleteRecursive(dirproj);
	}

	/**
	 * Generate the entities.
	 */
	private void initAll() {
		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(OrmCommand.GENERATE_ENTITIES,
				new String[] {},
				null);
		// The real test
		getHarmony().findAndExecute(OrmCommand.GENERATE_ENTITIES,
				new String[] {},
				null);
	}
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return CommonTest.getParameters();
	}
}
