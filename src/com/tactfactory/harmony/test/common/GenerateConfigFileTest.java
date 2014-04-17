/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.common;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ConfigMetadata;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.template.ConfigGenerator;
import com.tactfactory.harmony.test.CommonTest;


/**
 * Entities and Adapters generation tests.
 */
@RunWith(Parameterized.class)
public class GenerateConfigFileTest extends CommonTest {
	
	public GenerateConfigFileTest(ApplicationMetadata currentMetadata)
	        throws Exception {
		super(currentMetadata);
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
	}
	
	@Override
	public void setUpBeforeNewParameter() throws Exception {
		super.cleanAndroidFolder(false);
		super.setUpBeforeNewParameter();
		
		this.initAll();
	}

	/**
	 * Initialize everything for the test.
	 * @throws Exception 
	 */
	private void initAll() throws Exception {
		System.out.println("\nTest Config generator");
		System.out.println(SHARP_DELIMITOR);
		
		ConfigMetadata.addConfiguration("test", "testvalue");
		new ConfigGenerator(new AndroidAdapter()).generateConfigFile();
	}
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return CommonTest.getParameters();
	}

	/**
	 * Tests the existence of the various generated files.
	 */
	@Test
	public final void testFiles() {
		CommonTest.hasFindFile("android/res/values/configs.xml");
		//TODO check configs.xml content
	}
}
