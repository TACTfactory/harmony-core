/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.project;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.FileUtils;

/**
 *
 */
public class ProjectInitTest extends CommonTest {

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
		
		File dirproj = new File(String.format("%s/android", Harmony.pathProject));
		FileUtils.deleteRecursive(dirproj);
	}
	
	@Test
 	public void initAndroid() {
		System.out.println("Test Project init Android");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		
		this.isFindFile("android/AndroidManifest.xml");
		this.isFindFile("android/build.xml");
		this.isFindFile("android/lint.xml");
		//this.isFindFile("android/local.properties");
		this.isFindFile("android/proguard-project.txt");
		this.isFindFile("android/project.properties");
		
		this.isFindFile("android/src");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/entity");
		//this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/HomeActivity.java");
		//this.isFindFile("android/res/");
		
		this.isFindFile("android/libs");
		this.isFindFile("android/libs/android-support-v4.jar");
		this.isFindFile("android/libs/Harmony.jar");
		this.isFindFile("android/libs/joda-time-2.1.jar");
		
		this.isFindFile("android/res");
		this.isFindFile("android/res/values");
		this.isFindFile("android/res/values/configs.xml");
		this.isFindFile("android/res/values/strings.xml");
	}

	@Ignore
	@Test
	public void initIphone() {
		System.out.println("Test Project init iphone");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_IOS, null, null);
		
		// TODO add asserts (for folder/file exist..)
	}
	
	@Ignore
	@Test
	public void initRim() {
		System.out.println("Test Project init RIM");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_RIM, null, null);
		
		// TODO add asserts (for folder/file exist..)
	}
	
	@Ignore
	@Test
	public void initWinphone() {
		System.out.println("Test Project init Windows Phone");
		System.out.println("###############################################################################");
		
		
		this.harmony.findAndExecute(ProjectCommand.INIT_WINPHONE, null, null);
		
		// TODO add asserts (for folder/file exist..)
	}

}
