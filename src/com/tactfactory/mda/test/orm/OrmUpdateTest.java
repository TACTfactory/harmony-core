/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.orm;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.FileUtils;

public class OrmUpdateTest extends CommonTest {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		this.harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		String pathNameSpace = Harmony.projectNameSpace.replace(".", "//");
		String destDir = String.format("%s/android/src/%s", Harmony.pathProject, pathNameSpace);
		String srcDir = String.format("src/%s", pathNameSpace);
		
		FileUtils.copyDirectory(new File(srcDir),new File(destDir));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		
		// File dirproj = new File(String.format("%s/android", Harmony.pathProject));
		//TODO : enable !! FileUtils.deleteRecursive(dirproj);
	}
	
	@Test
	public void all() {
		
	}
}
