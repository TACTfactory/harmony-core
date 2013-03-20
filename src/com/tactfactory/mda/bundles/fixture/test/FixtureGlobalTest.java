/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.fixture.test;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.bundles.fixture.command.FixtureCommand;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

/**
 * Test class for Fixtures generation and loading.
 */
public class FixtureGlobalTest extends CommonTest {
	/** Fixture path. */
	private static final String FIXTURE_PATH = 
			"android/src/com/tactfactory/mda/test/demact/fixture/";
	
	/**
	 * @throws java.lang.Exception 
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
		initAll();
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
	 * Initialize the tests.
	 */
	private static void initAll() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("######################################" 
				+ "#########################################");
		
		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_ENTITIES, new String[] {}, null);
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_CRUD, new String[] {}, null);
		getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_INIT,
				new String[] {"--format=xml"},
				null);
	}
	
	/**
	 * Tests if loaders are generated.
	 */
	@Test
	public final void hasFixtureLoaders() {
		CommonTest.hasFindFile(FIXTURE_PATH + "UserDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "CommentDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "PostDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "ViewComponentDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "FixtureBase.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "DataManager.java");
	}
	
	/**
	 * Tests if XML Fixtures have really been loaded.
	 */
	@Test
	public final void hasFixturesXml() {
		// Copy fixture files
		copyFixturesXml();
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_LOAD, new String[] {}, null);
				
		CommonTest.hasFindFile("android/assets/app/User.xml");
		CommonTest.hasFindFile("android/assets/app/Comment.xml");
		CommonTest.hasFindFile("android/assets/app/Post.xml");
		CommonTest.hasFindFile("android/assets/app/ViewComponent.xml");
		
		CommonTest.hasFindFile("android/assets/test/User.xml");
		CommonTest.hasFindFile("android/assets/test/Comment.xml");
		CommonTest.hasFindFile("android/assets/test/Post.xml");
		CommonTest.hasFindFile("android/assets/test/ViewComponent.xml");
	}
	
	/**
	 * Tests if YML Fixtures have really been loaded.
	 */
	@Test
	public final void hasFixturesYml() {
		//Purge & init
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_PURGE, new String[] {}, null);
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_INIT, new String[] {}, null);
		
		// Copy fixture files
		copyFixturesYml();
		CommonTest.getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_LOAD, new String[] {}, null);
		
		CommonTest.hasFindFile("android/assets/app/User.yml");
		CommonTest.hasFindFile("android/assets/app/Comment.yml");
		CommonTest.hasFindFile("android/assets/app/Post.yml");
		CommonTest.hasFindFile("android/assets/app/ViewComponent.yml");
		
		CommonTest.hasFindFile("android/assets/test/User.yml");
		CommonTest.hasFindFile("android/assets/test/Comment.yml");
		CommonTest.hasFindFile("android/assets/test/Post.yml");
		CommonTest.hasFindFile("android/assets/test/ViewComponent.yml");
	}

	/**
	 * Copy XML fixtures in test project.
	 */
	protected static final void copyFixturesXml() {
		final String pathNameSpace = 
				ApplicationMetadata.INSTANCE.getProjectNameSpace().replaceAll(
						"\\.", "/");
		final String srcDir = 
				Harmony.getHarmonyPath() + String.format("/src/%s/%s/%s",
						pathNameSpace,
						"fixture",
						"xml");

		String destDir = String.format("fixtures/app/");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		destDir = String.format("fixtures/test/");
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.xml").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}
	
	/**
	 * Copy YML fixtures in test project.
	 */
	protected static final void copyFixturesYml() {
		final String pathNameSpace = 
				ApplicationMetadata.INSTANCE.getProjectNameSpace().replaceAll(
						"\\.", "/");
		final String srcDir =  
				Harmony.getHarmonyPath() + String.format("/src/%s/%s/%s",
					pathNameSpace,
					"fixture",
					"yml");

		String destDir = String.format("fixtures/app/");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		destDir = String.format("fixtures/test/");
		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.yml").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}
}
