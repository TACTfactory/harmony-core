/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.bundles;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.generator.BundleGenerator;
import com.tactfactory.harmony.platform.BundleAdapter;
import com.tactfactory.harmony.test.CommonTest;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Test class for Empty bundle generation.
 */
public class EmptyBundleGenerationTest extends CommonTest {
	/** Generated Test Bundle name. */
	private static final String BUNDLE_NAME = "Test";
	/** Generated Test Bundle owner. */
	private static final String BUNDLE_OWNER = "Name";
	/** Generated Test Bundle namespace. */
	private static final String BUNDLE_NAMESPACE =
			"com.tactfactory.harmony.bundles.namespace";

	/** Generated Test Bundle path. */
	private static final String BUNDLE_PATH = String.format(
			"%s/%s-%s/src/%s",
			Harmony.getBundlePath(),
			BUNDLE_OWNER.toLowerCase(),
			BUNDLE_NAME.toLowerCase(),
			BUNDLE_NAMESPACE.replace('.', '/'));
	/**
	 * Initialization.
	 * @throws Exception if something bad happends.
	 */
	@BeforeClass
	public static void setUpBefore() throws RuntimeException {
		
	}

	@Before
	@Override
	public final void setUp() throws RuntimeException {
		super.setUp();
		
		// Delete bundle folder if exists
		
		
		this.deleteBundle();
		
		this.initAll();
	}

	@After
	@Override
	public final void tearDown() throws RuntimeException {
		super.tearDown();
		
		this.deleteBundle();
	}

	/**
	 * Launch bundle generation.
	 */
	public final void initAll() {
		try {
			final BundleGenerator generator = 
					new BundleGenerator(new BundleAdapter());
			generator.generateBundleFiles(
					BUNDLE_OWNER,
					BUNDLE_NAME,
					BUNDLE_NAMESPACE);

		} catch (Exception e) {
			Assert.assertNull(e.getMessage(), e);
		}
	}

	/**
	 * Test if all files have been generated.
	 */
	@Test
	public final void testBundleGeneration() {
		EmptyBundleGenerationTest.hasFindFile(
				"annotation/" + BUNDLE_NAME + ".java");
		EmptyBundleGenerationTest.hasFindFile(
				"generator/" + BUNDLE_NAME + "Generator.java");
		EmptyBundleGenerationTest.hasFindFile(
				"parser/" + BUNDLE_NAME + "Parser.java");
		EmptyBundleGenerationTest.hasFindFile(
				"meta/" + BUNDLE_NAME + "Metadata.java");
		EmptyBundleGenerationTest.hasFindFile(
		        "command/" + BUNDLE_NAME + "Command.java");
		EmptyBundleGenerationTest.hasFindFile(
		        "platform/" + BUNDLE_NAME + "Adapter.java");
		EmptyBundleGenerationTest.hasFindFile(
		        "platform/android/" + BUNDLE_NAME + "AdapterAndroid.java");
		EmptyBundleGenerationTest.hasFindFile(
		        "platform/ios/" + BUNDLE_NAME + "AdapterIos.java");
		EmptyBundleGenerationTest.hasFindFile(
				"platform/winphone/" + BUNDLE_NAME + "AdapterWinphone.java");

	}

	/**
	 * Test if file exists.
	 * @param fileName The file name
	 */
	protected static void hasFindFile(final String fileName) {
		final File file =
				new File(
					String.format("%s/%s",
							BUNDLE_PATH,
						fileName));

		assertTrue(file.getAbsolutePath() + " doesn't exist", file.exists());
	}

	private void deleteBundle() {
	    File bundleTest = new File(String.format("%s/%s-%s",
                Harmony.getBundlePath(),
                BUNDLE_OWNER.toLowerCase(),
                BUNDLE_NAME.toLowerCase()));
	    
	    if (bundleTest.exists()) {
            TactFileUtils.deleteRecursive(bundleTest);
        }
	}
}
