/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * Database tests generator.
 *
 */
public class TestDBGenerator extends BaseGenerator {
	/** Local name space. */
	private String localNameSpace;
	
	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception 
	 */
	public TestDBGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}
	
	/**
	 * Generate all tests.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Repository test...");
		
		this.initTestAndroid();
	
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (!cm.isInternal() && !cm.getFields().isEmpty()) {
				this.localNameSpace =
						this.getAdapter().getNameSpace(
								cm, this.getAdapter().getTest());
				this.getDatamodel().put(TagConstant.CURRENT_ENTITY,
						cm.getName());
				this.generate();
			}
		}
		this.makeSourceTest("utils/TestUtils.java", "utils/TestUtils.java", false);
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
