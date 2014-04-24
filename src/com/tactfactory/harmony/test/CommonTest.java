/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.base.Strings;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.ProjectDiscover;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.test.factory.DemactFactory;
import com.tactfactory.harmony.test.factory.ManagementFactory;
import com.tactfactory.harmony.test.factory.TracScanFactory;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Common class for all the test.
 */
public abstract class CommonTest {
	
	/** Current tested metadata. */
	protected final ApplicationMetadata currentMetadata;
	
	/** Old metadata. */
	protected static ApplicationMetadata oldMetadata;

	/** Parsed metadata. */
	protected static ApplicationMetadata parsedMetadata;
	
	/** Console delimiter for tests. */
	protected static final String SHARP_DELIMITOR =
			  "#################"
			 + "#################"
			 + "#################"
			 + "#################"
			 + "#########";

	/** Harmony instance. */
	protected static Harmony harmony;
	
	/**
	 * Constructor for parameterized tests.
	 * The parameters sent to this should be all the metadata of the different
	 * project we want to test. (ie. Demact, Tracscan, etc.)
	 * 
	 * @param currentMetadata The metadata of the project
	 * @throws Exception 
	 */
	public CommonTest(ApplicationMetadata currentMetadata) throws Exception {
			this.currentMetadata = currentMetadata;
			if (!this.currentMetadata.equals(oldMetadata)) {
				this.setUpBeforeNewParameter();
			}
			CommonTest.oldMetadata = this.currentMetadata;
	}
	
	/**
	 * Empty constructor.
	 */
	public CommonTest() {
		this.currentMetadata = null;
	}

	/**
	 * This method is called before every new parameter is tested by JUnit.
	 * @throws Exception 
	 */
	public void setUpBeforeNewParameter() throws Exception {
		// Base configs
		ConsoleUtils.setAnsi(false);
		ConsoleUtils.setQuiet(false);
		ConsoleUtils.setDebug(true);

		// Clean folder
		CommonTest.cleanAndroidFolder();

		//ApplicationMetadata.reinit();
		
		// Project test config
		ApplicationMetadata.INSTANCE.setName(
				this.currentMetadata.getName());
		
		ApplicationMetadata.INSTANCE.setProjectNameSpace(
				this.currentMetadata.getProjectNameSpace());

		harmony = Harmony.getInstance();

		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			final String localProp =
					String.format("%s/%s",
							Harmony.getProjectAndroidPath(),
							"local.properties");

			ApplicationMetadata.setAndroidSdkPath(
					ProjectDiscover.getSdkDirFromPropertiesFile(localProp));

			if (ApplicationMetadata.getAndroidSdkPath() == null) {
				ApplicationMetadata.setAndroidSdkPath(
						"/opt/android-sdk-linux_86/");
			}
		}
	}
	
	/**
	 * Add logger to common test life-cycle.
	 */
	@Rule
	public final TestRule watcher = new TestWatcher() {
		protected void starting(final Description description) {
			System.out.println(SHARP_DELIMITOR 
					+ "\n# Starting test: " + description.getMethodName() 
					+ "\n" + SHARP_DELIMITOR);
		}
		
		@Override
		protected void failed(final Throwable e,
				final Description description) {
			
		}
		
		@Override
		protected void succeeded(final Description description) {
			System.out.println("So good !");
		}
		
		@Override
		protected void finished(final Description description) {
			System.out.println(SHARP_DELIMITOR 
					+ "\n# Finishing test: " + description.getMethodName() 
					+ "\n" + SHARP_DELIMITOR + "\n");
		}
	};
	

	/**
	 * Initialization.
	 * 
	 * (This method is still here for compatibility purpose with the other
	 * bundles...)
	 * 
	 * @throws Exception if something bad happens
	 * 
	 * @deprecated Use setUpBeforeNewParameter instead.
	 */
	@Deprecated
	public static void setUpBefore() throws RuntimeException {
		// Base configs
		ConsoleUtils.setAnsi(false);
		ConsoleUtils.setQuiet(false);
		ConsoleUtils.setDebug(true);

		// Project test config
		ApplicationMetadata.INSTANCE.setName("demact");
		ApplicationMetadata.INSTANCE.setProjectNameSpace(
				"com/tactfactory/harmony/test/demact");

		harmony = Harmony.getInstance();

		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			final String localProp =
					String.format("%s/%s",
							Harmony.getProjectAndroidPath(),
							"local.properties");

			ApplicationMetadata.setAndroidSdkPath(
					ProjectDiscover.getSdkDirFromPropertiesFile(localProp));

			if (ApplicationMetadata.getAndroidSdkPath() == null) {
				ApplicationMetadata.setAndroidSdkPath(
						"/opt/android-sdk-linux_86/");
			}
		}
	}

	/**
	 * Initialization.
	 * @throws Exception if something bad happends.
	 */
	public void setUp() throws RuntimeException {

	}

	/**
	 * Test clean.
	 * @throws Exception if something bad happends.
	 */
	public void tearDown() throws RuntimeException {
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

		final String srcDir = 
				String.format("%s/tact-core/resources/%s/%s/",
						Harmony.getBundlePath(),
						pathNameSpace, 
						"entity");
		
		final String destDir = 
				String.format("%s/src/%s/%s/", 
						Harmony.getProjectAndroidPath(), 
						pathNameSpace, 
						"entity");

		System.out.println(destDir);

		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.java").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}

	/**
	 * Test if file exists.
	 * @param fileName The file name
	 */
	protected static void hasFindFile(final String fileName) {
		final File file =
				new File(
					String.format("%s/%s",
						Harmony.getProjectPath(),
						fileName));
		ConsoleUtils.display("Testing existence of " + file.getAbsolutePath());
		assertTrue(file.getAbsolutePath() + " does not exist", file.exists());
	}
	
	/**
	 * Clean the /android/ folder. (But keeps the libs folder)
	 */
	protected static void cleanAndroidFolder() {
		CommonTest.cleanAndroidFolder(true);
	}
	
	/**
	 * Clean the /android/ folder.  
	 * @param keepLibs True if you want to keep the libs folder.
	 * 				(Use this if you don't want to redownload the various git
	 *               libraries of your test project / bundle.)
	 */
	protected static void cleanAndroidFolder(boolean keepLibs) {
		ConsoleUtils.display(
				  "################################  "
				+ "Clean Android Folder !! "
				+ "################################");
		final File dirproj = new File(Harmony.getProjectAndroidPath());
		if (keepLibs) { 
			ConsoleUtils.display("Keep libraries !");
			File[] files = dirproj.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						file.delete();
					} else {
						if (!file.getName().equals("libs")) {
							TactFileUtils.deleteRecursive(file);
						}
					}
				}
			}
		} else {
			TactFileUtils.deleteRecursive(dirproj);
		}
	}
	
	/**
	 * JUnit Parameters method.
	 * This should return the various application metadata associated 
	 * to your various test projects. (ie. Demact, Tracscan, etc.)
	 * 
	 * @return The collection of application metadatas.
	 */
	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> result = new ArrayList<Object[]>();
		
		result.add(new ApplicationMetadata[] {
				TracScanFactory.generateTestMetadata()
		});
		
		result.add(new ApplicationMetadata[] {
				DemactFactory.generateTestMetadata()
		});
		
		result.add(new ApplicationMetadata[] {
			ManagementFactory.generateTestMetadata()	
		});
		
		return result;
	}
}
