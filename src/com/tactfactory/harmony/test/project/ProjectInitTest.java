/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.project;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.HarmonyContext;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.generator.ProjectGenerator;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.test.CommonTest;

/**
 * Test class for project initialization.
 */
@RunWith(Parameterized.class)
public class ProjectInitTest extends CommonTest {

    public ProjectInitTest(ApplicationMetadata currentMetadata) throws Exception {
        super(currentMetadata);
    }
    /**
     * Initialization of the test.
     * @throws Exception if something bad happened.
     */
    @BeforeClass
    public static void setUpBefore() {
        //CommonTest.setUpBefore();
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
        super.setUpBeforeNewParameter();

        //Force clean all files
        CommonTest.cleanAndroidFolder(false);
    }

    /**
     * Test the initialization of the android project.
     */
    @Test
     public final void initAndroid() {
        System.out.println("\nTest Project init Android");
        System.out.println(SHARP_DELIMITOR);

        // Generate Project
        CommonTest.getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);

        CommonTest.hasFindFile("android/app/src/main/AndroidManifest.xml");
        CommonTest.hasFindFile("android/build.gradle");
        CommonTest.hasFindFile("android/app/build.gradle");
        //CommonTest.hasFindFile("android/lint.xml");
        //this.isFindFile("android/local.properties");
        CommonTest.hasFindFile("android/app/proguard-project.txt");

        CommonTest.hasFindFile("android/app/src/main/");
        CommonTest.hasFindFile(ANDROID_SRC_PATH);
        CommonTest.hasFindFile(ANDROID_SRC_PATH
                + this.currentMetadata.getProjectNameSpace().replace('.', '/'));
        CommonTest.hasFindFile(ANDROID_SRC_PATH
                + this.currentMetadata.getProjectNameSpace().replace('.', '/')
                + "/entity");
        //this.isFindFile("android/res/");

        CommonTest.hasFindFile("android/app/libs");
        //CommonTest.hasFindFile("android/libs/android-support-v4.jar");
        CommonTest.hasFindFile("android/app/libs/core-annotations.jar");

        CommonTest.hasFindFile(ANDROID_RES_PATH);
        CommonTest.hasFindFile(ANDROID_RES_PATH + "values");
        CommonTest.hasFindFile(ANDROID_RES_PATH + "values/configs.xml");
        CommonTest.hasFindFile(ANDROID_RES_PATH + "values/strings.xml");

        System.out.println("\nTest Update SDK Path");
        System.out.println(SHARP_DELIMITOR);

        String currentSdkPath = ApplicationMetadata.getAndroidSdkPath();
        String newSdkPath = "test-sdkpath/";
        ApplicationMetadata.setAndroidSdkPath(newSdkPath);
        ProjectGenerator.updateSDKPath();

        final String localProp =
                String.format("%s/%s",
                        Harmony.getProjectAndroidPath(),
                        "local.properties");
        assertEquals(HarmonyContext.getSdkDirFromPropertiesFile(localProp), newSdkPath);

        ApplicationMetadata.setAndroidSdkPath(currentSdkPath);
        ProjectGenerator.updateSDKPath();
    }

    /**
     * Test the initialization of the iPhone project.
     */
    /*@Ignore
    @Test
    public final void initIphone() {
        System.out.println("\nTest Project init iphone");
        System.out.println(SHARP_DELIMITOR);

        CommonTest.getHarmony().findAndExecute(
                ProjectCommand.INIT_IOS, null, null);

        // TODO add asserts (for folder/file exist..)
    }*/

    /**
     * Test the initialization of the RIM project.
     */
    /*@Ignore
    @Test
    public final void initRim() {
        System.out.println("\nTest Project init RIM");
        System.out.println(SHARP_DELIMITOR);

        CommonTest.getHarmony().findAndExecute(
                ProjectCommand.INIT_RIM, null, null);

        // TODO add asserts (for folder/file exist..)
    }*/

    /**
     * Test the initialization of the Windows Phone project.
     */
    /*@Ignore
    @Test
    public final void initWindows() {
        System.out.println("\nTest Project init Windows Phone");
        System.out.println(SHARP_DELIMITOR);


        CommonTest.getHarmony().findAndExecute(ProjectCommand.INIT_WINDOWS,
                                    null,
                                    null);

        // TODO add asserts (for folder/file exist..)
    }*/

    @Parameters
    public static Collection<Object[]> getParameters() {
        return CommonTest.getParameters();
    }
}
