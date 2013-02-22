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

import com.tactfactory.mda.bundles.fixture.command.FixtureCommand;
import com.tactfactory.mda.bundles.rest.command.RestCommand;
import com.tactfactory.mda.bundles.sync.command.SyncCommand;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

public class FixtureGlobalTest extends CommonTest {
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
	}
	
	private static void initAll() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("######################################" +
				"#########################################");
		
		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_ENTITIES, new String[] {}, null);
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_CRUD, new String[] {}, null);
		getHarmony().findAndExecute(
				RestCommand.GENERATE_ADAPTERS, new String[] {}, null);
		getHarmony().findAndExecute(
				SyncCommand.GENERATE_SERVICE, new String[] {}, null);
		getHarmony().findAndExecute(
				FixtureCommand.FIXTURE_INIT,
				new String[] {"--format=xml"},
				null);
	}
	
	//@Test
	public void all() {
			
	}
	
	@Test
	public void hasFixtureLoaders() {
		CommonTest.hasFindFile(FIXTURE_PATH + "UserDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "CommentDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "PostDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "ViewComponentDataLoader.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "FixtureBase.java");
		CommonTest.hasFindFile(FIXTURE_PATH + "DataManager.java");
	}
	
	@Test
	public void hasFixturesXml() {
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
	
	@Test
	public void hasFixturesYml() {
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

	
	protected static void copyFixturesXml() {
		final String pathNameSpace = 
				ApplicationMetadata.INSTANCE.projectNameSpace.replaceAll(
						"\\.", "/");
		final String srcDir = String.format("src/%s/%s/%s/",
				pathNameSpace,
				"fixture",
				"xml");

		String destDir = String.format("fixtures/app/");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		destDir = String.format("fixtures/test/");
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.xml").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}
	
	protected static void copyFixturesYml() {
		final String pathNameSpace = 
				ApplicationMetadata.INSTANCE.projectNameSpace.replaceAll(
						"\\.", "/");
		final String srcDir = String.format("src/%s/%s/%s/",
				pathNameSpace,
				"fixture",
				"yml");

		String destDir = String.format("fixtures/app/");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir), new File(destDir));
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		destDir = String.format("fixtures/test/");
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.yml").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}
}
