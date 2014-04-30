/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

import java.io.File;

/**
 * Tests generator.
 *
 */
public class TestGenerator extends BaseGenerator {
	/** Local name space. */
	private String localNameSpace;

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public TestGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate all tests.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Repository test...");

		//this.initTestAndroid();
		this.getDatamodel().put("dataLoader",
				this.isDataLoaderAlreadyGenerated());

		for (final EntityMetadata cm
				: this.getAppMetas().getEntities().values()) {
			if (!cm.isInternal() && cm.hasFields()) {
				this.localNameSpace =
						this.getAdapter().getNameSpace(
								cm, this.getAdapter().getTest());
				this.getDatamodel().put(TagConstant.CURRENT_ENTITY,
						cm.getName());
				this.generate();
			}
		}
		this.makeSourceTest("utils/TestUtils.java",
				"utils/TestUtils.java",
				false);
		
		this.makeSourceTest("base/TestContextMock.java",
				"base/TestContextMock.java",
				true);

		this.makeSourceTest("base/TestDBBase.java",
				"base/TestDBBase.java",
				true);

		this.makeSourceTest("base/TestServiceBase.java",
				"base/TestServiceBase.java",
				true);

		this.makeSourceTest("base/TestCriteriaBase.java",
				"base/TestCriteriaBase.java",
				true);
		
		this.makeSourceTest("TestCriteria.java",
				"TestCriteria.java",
				false);
	}

	/**
	 * Generate DataBase Test.
	 */
	private void generate() {
		// Info
				ConsoleUtils.display(">>> Generate Repository test for "
							+ this.getDatamodel().get(
									TagConstant.CURRENT_ENTITY));

		try {
			this.makeSourceTest(
					"base/TemplateTestParcelableBase.java",
					"base/%sTestParcelableBase.java",
					true);
			
			this.makeSourceTest(
					"TemplateTestParcelable.java",
					"%sTestParcelable.java",
					false);
			
			this.makeSourceTest(
					"base/TemplateTestDBBase.java",
					"base/%sTestDBBase.java",
					true);

			this.makeSourceTest(
					"TemplateTestDB.java",
					"%sTestDB.java",
					false);

			this.makeSourceTest(
					"utils/base/TemplateUtilsBase.java",
					"utils/base/%sUtilsBase.java",
					true);

			this.makeSourceTest(
					"utils/TemplateUtils.java",
					"utils/%sUtils.java",
					false);

		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Check if the fixture dataloader class has already been generated.
	 * @return True if it already exists.
	 */
	private boolean isDataLoaderAlreadyGenerated() {
		final String dataLoaderPath = this.getAdapter().getSourcePath()
				+ this.getAppMetas().getProjectNameSpace()
				+ "/" + this.getAdapter().getFixture() + "/"
				+ "DataLoader.java";


		return new File(dataLoaderPath).exists();
	}

	/**
	 * Make Java Source Code.
	 *
	 * @param template Template path file.
	 * <br/>For list activity is "TemplateListActivity.java"
	 * @param filename Destination file name
	 * @param override True if must overwrite file.
	 */
	private void makeSourceTest(final String template,
			final String filename,
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getTestPath(),
						PackageUtils.extractPath(
								String.format("%s/%s",
										this.getAdapter().getSource(),
										this.localNameSpace)).toLowerCase(),
						String.format(filename,
								this.getDatamodel().get(
										TagConstant.CURRENT_ENTITY)));

		final String fullTemplatePath = String.format("%s%s",
					this.getAdapter().getTemplateTestsPath(),
					template);

		super.makeSource(fullTemplatePath, fullFilePath, override);
	}

	/**
	 * Initialize Test Android Project folders and files.
	 * @return success of Test Android project initialization
	 */
	public final boolean initTestAndroid() {
		ConsoleUtils.display("> Init Test Project Google Android");

		boolean result = false;

		try {
			if (new TestProjectGenerator(this.getAdapter()).makeProject()) {
				ConsoleUtils.displayDebug("Init Test Android Project Success!");

				result = true;
			} else {
				ConsoleUtils.displayError(
						new Exception("Init Test Android Project Fail!"));
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
		return result;
	}
}
