/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.fixture.test;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.fixture.command.FixtureCommand;
import com.tactfactory.mda.rest.command.RestCommand;
import com.tactfactory.mda.sync.command.SyncCommand;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.FileUtils;

public class FixtureGlobalTest extends CommonTest{
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.initAllTests();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void initAllTests() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		this.makeEntities();
		this.harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		this.harmony.findAndExecute(OrmCommand.GENERATE_CRUD, new String[]{}, null);
		this.harmony.findAndExecute(RestCommand.GENERATE_ADAPTERS, new String[]{}, null);
		this.harmony.findAndExecute(SyncCommand.GENERATE_SERVICE, new String[]{}, null);
		this.harmony.findAndExecute(FixtureCommand.FIXTURE_INIT, new String[]{"--format=xml"}, null);
		
		// Copy fixture files
		this.copyFixturesXml();
		this.harmony.findAndExecute(FixtureCommand.FIXTURE_LOAD, new String[]{}, null);
		this.hasFixturesXml();		
		
		this.harmony.findAndExecute(FixtureCommand.FIXTURE_PURGE, new String[]{}, null);
		this.harmony.findAndExecute(FixtureCommand.FIXTURE_INIT, new String[]{}, null);
		this.copyFixturesYml();
		this.harmony.findAndExecute(FixtureCommand.FIXTURE_LOAD, new String[]{}, null);
		this.hasFixturesYml();	
	}
	
	//@Test
	public void all() {
		this.initAllTests();	
	}
	
	@Test
	public void hasFixtureLoaders() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/fixture/UserDataLoader.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/fixture/CommentDataLoader.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/fixture/PostDataLoader.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/fixture/ViewComponentDataLoader.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/fixture/FixtureBase.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/fixture/DataManager.java");
	}
	
	@Test
	public void hasFixturesXml() {
		this.hasFindFile("android/assets/app/User.xml");
		this.hasFindFile("android/assets/app/Comment.xml");
		this.hasFindFile("android/assets/app/Post.xml");
		this.hasFindFile("android/assets/app/ViewComponent.xml");
		
		this.hasFindFile("android/assets/test/User.xml");
		this.hasFindFile("android/assets/test/Comment.xml");
		this.hasFindFile("android/assets/test/Post.xml");
		this.hasFindFile("android/assets/test/ViewComponent.xml");
	}
	
	@Test
	public void hasFixturesYml() {
		this.hasFindFile("android/assets/app/User.yml");
		this.hasFindFile("android/assets/app/Comment.yml");
		this.hasFindFile("android/assets/app/Post.yml");
		this.hasFindFile("android/assets/app/ViewComponent.yml");
		
		this.hasFindFile("android/assets/test/User.yml");
		this.hasFindFile("android/assets/test/Comment.yml");
		this.hasFindFile("android/assets/test/Post.yml");
		this.hasFindFile("android/assets/test/ViewComponent.yml");
	}
	
	protected void copyFixturesXml() {
		String pathNameSpace = Harmony.metas.projectNameSpace.replaceAll("\\.", "/");
		String srcDir = String.format("src/%s/%s/%s/", pathNameSpace, "fixture", "xml");
		String destDir = String.format("fixtures/app/");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir),new File(destDir));
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		destDir = String.format("fixtures/test/");
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if(new File(destDir+"Post.xml").exists())
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
	}
	
	protected void copyFixturesYml() {
		String pathNameSpace = Harmony.metas.projectNameSpace.replaceAll("\\.", "/");
		String srcDir = String.format("src/%s/%s/%s/", pathNameSpace, "fixture", "yml");
		String destDir = String.format("fixtures/app/");
		System.out.println(destDir);
		
		// FileUtils.copyDirectory(new File(srcDir),new File(destDir));
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		destDir = String.format("fixtures/test/");
		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if(new File(destDir+"Post.yml").exists())
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
	}
}
