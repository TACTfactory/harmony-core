package com.tactfactory.harmony.test.bundles;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.plateforme.AndroidAdapter;
import com.tactfactory.harmony.template.BundleGenerator;
import com.tactfactory.harmony.test.CommonTest;
import com.tactfactory.harmony.utils.TactFileUtils;

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
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();

	}

	@Before
	@Override
	public final void setUp() throws Exception {
		super.setUp();
		// Delete bundle folder if exists
		File existingFolder = new File(String.format("%s/%s-%s",
						Harmony.getBundlePath(),
						BUNDLE_OWNER.toLowerCase(),
						BUNDLE_NAME.toLowerCase()));
		if (existingFolder.exists()) {
			TactFileUtils.deleteRecursive(existingFolder);
		}
		this.initAll();
	}

	@After
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Launch bundle generation.
	 */
	public final void initAll() {
		try {
			BundleGenerator generator = new BundleGenerator(new AndroidAdapter());
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
				"template/" + BUNDLE_NAME + "Generator.java");
		EmptyBundleGenerationTest.hasFindFile(
				"parser/" + BUNDLE_NAME + "Parser.java");
		EmptyBundleGenerationTest.hasFindFile(
				"meta/" + BUNDLE_NAME + "Metadata.java");
		EmptyBundleGenerationTest.hasFindFile(
				"command/" + BUNDLE_NAME + "Command.java");

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

}
