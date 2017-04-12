/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.fixture;

import java.io.File;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.command.FixtureCommand;
import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.test.CommonTest;
import com.tactfactory.harmony.utils.TactFileUtils;

@RunWith(Parameterized.class)
/**
 * Test class for Fixtures generation and loading.
 */
public class FixtureGlobalTest extends CommonTest {
    /** Fixture path. */
    private static final String FIXTURE_PATH = ANDROID_SRC_PATH + "%s/fixture/%s";

    public FixtureGlobalTest (ApplicationMetadata currentMetadata)
            throws Exception {
        super(currentMetadata);
    }

    @Override
    public void setUpBeforeNewParameter() throws Exception {
        super.setUpBeforeNewParameter();

        final File dirfixtures = new File("fixtures/");
        TactFileUtils.deleteRecursive(dirfixtures);

        this.initAll();
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

    @Parameters
    public static Collection<Object[]> getParameters() {
        return CommonTest.getParameters();
    }

    /**
     * Initialize the tests.
     */
    private void initAll() {
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
                new String[] {"--format=xml", "--force=true"},
                null);


        final OrmCommand command =
                (OrmCommand) Harmony.getInstance().getCommand(
                        OrmCommand.class);
        command.generateMetas();

        parsedMetadata = ApplicationMetadata.INSTANCE;
    }

    /**
     * Tests if XML Fixtures have really been loaded.
     */
    @Test
    public final void hasFixturesXml() {
        // Copy fixture files
        this.copyFixturesXml();
        CommonTest.getHarmony().findAndExecute(
                FixtureCommand.FIXTURE_LOAD, new String[] {}, null);

        CommonTest.hasFindFile(String.format(
                    FIXTURE_PATH,
                    this.currentMetadata.getProjectNameSpace(),
                    "FixtureBase.java"));
        CommonTest.hasFindFile(String.format(
                    FIXTURE_PATH,
                    this.currentMetadata.getProjectNameSpace(),
                    "DataManager.java"));
        for (EntityMetadata entity : this.currentMetadata.getEntities().values()) {
            if (!entity.getFields().isEmpty() && !entity.isInternal()) {
                CommonTest.hasFindFile(String.format(
                        "android/app/src/main/assets/test/%s.xml",
                        entity.getName()));
                CommonTest.hasFindFile(String.format(
                        "android/app/src/main/assets/app/%s.xml",
                        entity.getName()));
                CommonTest.hasFindFile(String.format(
                        "android/app/src/main/assets/debug/%s.xml",
                        entity.getName()));
                CommonTest.hasFindFile(String.format(
                        FIXTURE_PATH,
                        this.currentMetadata.getProjectNameSpace(),
                        String.format(
                                "%sDataLoader.java",
                                entity.getName())));
            }
        }
    }

    /**
     * Tests if YML Fixtures have really been loaded.
     */
    //@Test
    public final void hasFixturesYml() {
        //Purge & init
        CommonTest.getHarmony().findAndExecute(
                FixtureCommand.FIXTURE_PURGE, new String[] {}, null);
        CommonTest.getHarmony().findAndExecute(
                FixtureCommand.FIXTURE_INIT,
                new String[] {"--format=yml", "--force=true"},
                null);

        // Copy fixture files
        this.copyFixturesYml();
        CommonTest.getHarmony().findAndExecute(
                FixtureCommand.FIXTURE_LOAD, new String[] {}, null);

        CommonTest.hasFindFile(String.format(
                FIXTURE_PATH,
                this.currentMetadata.getProjectNameSpace(),
                "FixtureBase.java"));
        CommonTest.hasFindFile(String.format(
                    FIXTURE_PATH,
                    this.currentMetadata.getProjectNameSpace(),
                    "DataManager.java"));

        for (EntityMetadata entity : this.currentMetadata.getEntities().values()) {
            if (!entity.getFields().isEmpty() && !entity.isInternal()) {
                CommonTest.hasFindFile(String.format(
                        "android/app/src/main/assets/test/%s.yml",
                        entity.getName()));
                CommonTest.hasFindFile(String.format(
                        "android/app/src/main/assets/app/%s.yml",
                        entity.getName()));
                CommonTest.hasFindFile(String.format(
                        "android/app/src/main/assets/debug/%s.yml",
                        entity.getName()));
                CommonTest.hasFindFile(String.format(
                        FIXTURE_PATH,
                        this.currentMetadata.getProjectNameSpace(),
                        String.format(
                                "%sDataLoader.java",
                                entity.getName())));
            }
        }
    }

    /**
     * Copy XML fixtures in test project.
     */
    protected final void copyFixturesXml() {
        final String pathNameSpace =
                this.currentMetadata.getProjectNameSpace().replace(
                        '.', '/');

        String srcDir =
                String.format("%s/tact-core/resources/%s/%s/%s/",
                        Harmony.getBundlePath(),
                        pathNameSpace,
                        "fixture",
                        "xml");

        final String destDir =
                String.format("%s/%s/",
                        Harmony.getProjectAndroidPath(),
                        "assets");

        // FileUtils.copyDirectory(new File(srcDir), new File(destDir));
        TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
        //destDir = String.format("fixtures/test/");
        //TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
    }

    /**
     * Copy YML fixtures in test project.
     */
    protected final void copyFixturesYml() {
        final String pathNameSpace =
                this.currentMetadata.getProjectNameSpace().replace(
                        '.', '/');

        String srcDir =
                String.format("%s/tact-core/resources/%s/%s/%s/",
                        Harmony.getBundlePath(),
                        pathNameSpace,
                        "fixture",
                        "yml");

        final String destDir =
                String.format("%s/%s/",
                        Harmony.getProjectAndroidPath(),
                        "assets");

        // FileUtils.copyDirectory(new File(srcDir), new File(destDir));
        TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
    }
}
