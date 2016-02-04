/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.common;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.command.CommonCommand;
import com.tactfactory.harmony.command.FixtureCommand;
import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.test.CommonTest;


@RunWith(Parameterized.class)
/**
 * Entities and Adapters generation tests.
 */
public class GenerateStaticViewTest extends CommonTest {

    /** Path of view path. */
    private static final String VIEW_PATH = "android/src/%s/view/%s/%s%s.java";
    /** Path for layout path. */
    private static final String LAYOUT_PATH = "android/res/layout/%s_%s.xml";
    /** Test package name. */
    private static final String TEST_PACKAGE_NAME = "test.test2";
    /** Test view name. */
    private static final String TEST_VIEW_NAME = "ViewTest";

    public GenerateStaticViewTest(ApplicationMetadata currentMetadata)
            throws Exception {
        super(currentMetadata);
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

        this.initAll();
    }

    /**
     * Initialize everything for the test.
     */
    private void initAll() {
        System.out.println("\nTest Orm generate entity");
        System.out.println(
                "########################################"
                 + "######################################");

        getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
        makeEntities();
        getHarmony().findAndExecute(OrmCommand.GENERATE_ENTITIES,
                new String[] {},
                null);
        getHarmony().findAndExecute(OrmCommand.GENERATE_CRUD,
                new String[] {},
                null);
        getHarmony().findAndExecute(FixtureCommand.FIXTURE_INIT,
                new String[] {},
                null);

        getHarmony().findAndExecute(CommonCommand.GENERATE_STATIC,
                new String[] {
                    "--name=" + TEST_VIEW_NAME,
                    "--package=" + TEST_PACKAGE_NAME},
                null);

        final CommonCommand command =
                (CommonCommand) Harmony.getInstance().getCommand(
                        CommonCommand.class);

        command.generateMetas();

        parsedMetadata = ApplicationMetadata.INSTANCE;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return CommonTest.getParameters();
    }

    /**
     * Tests the existence of the various generated files.
     */
    @Test
    public final void testFiles() {
        CommonTest.hasFindFile(String.format(
                VIEW_PATH,
                this.currentMetadata.getProjectNameSpace(),
                TEST_PACKAGE_NAME.replace('.', '/'),
                TEST_VIEW_NAME,
                "Activity"));

        CommonTest.hasFindFile(String.format(
                VIEW_PATH,
                this.currentMetadata.getProjectNameSpace(),
                TEST_PACKAGE_NAME.replace('.', '/'),
                TEST_VIEW_NAME,
                "Fragment"));


        CommonTest.hasFindFile(String.format(
                LAYOUT_PATH,
                "fragment",
                TEST_VIEW_NAME.toLowerCase()));

        CommonTest.hasFindFile(String.format(
                LAYOUT_PATH,
                "activity",
                TEST_VIEW_NAME.toLowerCase()));
    }
}
