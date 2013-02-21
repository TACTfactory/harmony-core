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

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

/**
 *
 */
public abstract class CommonTest {
	protected static Harmony harmony; 

	/**
	 * @throws java.lang.Exception
	 */
	public static void setUpBefore() throws Exception {
		// Base configs
		ConsoleUtils.ansi  = false;
		ConsoleUtils.quiet = false;
		ConsoleUtils.debug = true;
		
		// Project test config
		ApplicationMetadata.INSTANCE.name = "demact";
		ApplicationMetadata.INSTANCE.projectNameSpace 
				= "com/tactfactory/mda/test/demact";
		
		if (ApplicationMetadata.androidSdkPath == null 
				|| ApplicationMetadata.androidSdkPath.isEmpty()) {
			final String localProp = 
					String.format("%s/%s/%s",
							Harmony.PATH_PROJECT, 
							Harmony.projectFolder, 
							"local.properties");
			
			ApplicationMetadata.androidSdkPath = 
					Harmony.getSdkDirFromPropertiesFile(localProp);
			if (ApplicationMetadata.androidSdkPath == null) {
				ApplicationMetadata.androidSdkPath =
						"/opt/android-sdk-linux_86/";
			}
		}
		
		harmony = new Harmony();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
		
	}
	
	protected static void makeEntities() {
		final String pathNameSpace = 
				ApplicationMetadata.INSTANCE.projectNameSpace.replaceAll("\\.",
						"/");
		final String srcDir = 
				String.format("src/%s/%s/", 
						pathNameSpace, 
						"entity");
		final String destDir = 
				String.format("%s/android/src/%s/%s/", 
						Harmony.PATH_PROJECT, 
						pathNameSpace, 
						"entity");

		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir),new File(destDir));
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.java").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}
	
	protected static void hasFindFile(final String fileName) {
		final File file = 
				new File(
					String.format("%s/%s",
						Harmony.PATH_PROJECT, 
						fileName));

		assertTrue(file.exists());
	}
}
