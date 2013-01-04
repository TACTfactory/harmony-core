/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test;

import static org.junit.Assert.assertTrue;

import java.io.File;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.utils.FileUtils;

/**
 *
 */
public abstract class CommonTest {
	protected Harmony harmony;
	//protected AndroidAdapter adapterTargetAdapter = new AndroidAdapter(); //TODO transform to 

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		// Base configs
		ConsoleUtils.ansi  = false;
		ConsoleUtils.quiet = false;
		Harmony.debug = true;
		
		// Project test config
		Harmony.metas.projectName = "demact";
		Harmony.metas.projectNameSpace = "com/tactfactory/mda/test/demact";
		Harmony.androidSdkPath = "/tmp/";
		//Harmony.pathProject = "app";
		//Harmony.pathTemplate = "../tpl";
		
		this.harmony = new Harmony();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
		
	}
	
	protected void makeEntities() {
		String pathNameSpace = Harmony.metas.projectNameSpace.replaceAll("\\.", "/");
		String srcDir = String.format("src/%s/%s/", pathNameSpace, "entity");
		String destDir = String.format("%s/android/src/%s/%s/", Harmony.pathProject, pathNameSpace, "entity");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir),new File(destDir));
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if(new File(destDir+"Post.java").exists())
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
	}
	
	
	protected void hasFindFile(String fileName) {
		File file = new File(String.format("%s/%s", Harmony.pathProject, fileName));
		assertTrue(file.exists());
	}
}
