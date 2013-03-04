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

import com.google.common.base.Strings;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

/**
 *
 */
public abstract class CommonTest {
	/** Console delimiter for tests. */
	protected static final String SHARP_DELIMITOR = 
			  "#################" 
			 + "#################" 
			 + "#################" 
			 + "#################" 
			 + "#########";
	
	/** Harmony instance. */
	private static Harmony harmony; 

	/**
	 * Initialization.
	 * @throws Exception 
	 */
	public static void setUpBefore() throws Exception {
		// Base configs
		ConsoleUtils.setAnsi(false);
		ConsoleUtils.setQuiet(false);
		ConsoleUtils.setDebug(true);
		
		// Project test config
		ApplicationMetadata.INSTANCE.setName("demact");
		ApplicationMetadata.INSTANCE.setProjectNameSpace(
				"com/tactfactory/mda/test/demact");
		
		harmony = Harmony.getInstance();
		
		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			final String localProp = 
					String.format("%s/%s/%s",
							Harmony.PATH_PROJECT, 
							Harmony.getProjectFolder(), 
							"local.properties");

			ApplicationMetadata.setAndroidSdkPath(
					//"/home/micky/Applications/eclipse/android-sdk");
					Harmony.getSdkDirFromPropertiesFile(localProp));
			if (ApplicationMetadata.getAndroidSdkPath() == null) {
				ApplicationMetadata.setAndroidSdkPath(
						"/opt/android-sdk-linux_86/");
			}
		}
	}

	/**
	 * Initialization.
	 * @throws Exception 
	 */
	public void setUp() throws Exception {
		
	}
	
	/**
	 * Test clean.
	 * @throws Exception 
	 */
	public void tearDown() throws Exception {
		
	}
	
	/** Get Harmony instance.
	 * @return The Harmony instance
	 */
	public static Harmony getHarmony() {
		return harmony;
	}
	
	/**
	 * Copy the test entities in the test project. 
	 */
	protected static void makeEntities() {
		final String pathNameSpace = 
				ApplicationMetadata.INSTANCE.getProjectNameSpace()
					.replaceAll("\\.", "/");

		String srcDir = 
				String.format(Harmony.PATH_HARMONY+"/src/%s/%s/", 
						pathNameSpace, 
						"entity");
		String destDir = 
				String.format("%s/android/src/%s/%s/", 
						Harmony.PATH_PROJECT, 
						pathNameSpace, 
						"entity");

		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.java").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
		
		// Copy artefact for agregate CI result
		final String libPath = "harmony.jar";
		srcDir = 
				String.format("%s/vendor/tact-core/%s", 
						Harmony.PATH_BASE, 
						libPath);
		destDir = 
				String.format("%s/android/test/libs/%s", 
						Harmony.PATH_PROJECT, 
						libPath);
		TactFileUtils.copyfile(new File(srcDir), new File(destDir));
	}
	
	/**
	 * Test if file exists.
	 * @param fileName The file name
	 */
	protected static void hasFindFile(final String fileName) {
		final File file = 
				new File(
					String.format("%s/%s",
						Harmony.PATH_PROJECT, 
						fileName));

		assertTrue(file.exists());
	}
}
