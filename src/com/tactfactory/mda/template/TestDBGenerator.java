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

public class TestDBGenerator extends BaseGenerator {
	private String localNameSpace;
	
	public TestDBGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Repository test...");
		
		this.initTestAndroid();
	
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (!cm.internal && !cm.fields.isEmpty()) {
				this.localNameSpace =
						this.adapter.getNameSpace(cm, this.adapter.getTest());
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.generate();
			}
		}
		this.makeSourceTest("TestUtils.java", "TestUtils.java", true);
	}
	
	/**  
	 * Generate DataBase Test  
	 */ 
	private void generate() {
		// Info
				ConsoleUtils.display(">>> Generate Repository test for " 
							+ this.datamodel.get(TagConstant.CURRENT_ENTITY));
		
		try {			
			this.makeSourceTest(
					"base/TemplateTestDBBase.java", 
					"base/%sTestDBBase.java",
					true);
			
			this.makeSourceTest(
					"TemplateTestDB.java", 
					"%sTestDB.java",
					false);

		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param template Template path file. 
	 * <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceTest(final String template, 
			final String filename,
			final boolean override) {
		final String fullFilePath = String.format("%s%s/%s",
						this.adapter.getTestPath(),
						PackageUtils.extractPath(
								String.format("%s/%s",
										this.adapter.getSource(),
										this.localNameSpace)).toLowerCase(),
						String.format(filename,
								this.datamodel.get(
										TagConstant.CURRENT_ENTITY)));
		
		final String fullTemplatePath = String.format("%s%s",
					this.adapter.getTemplateTestsPath(),
					template);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**
	 * Initialize Test Android Project folders and files
	 * @return success of Test Android project initialization
	 */
	public final boolean initTestAndroid() {
		ConsoleUtils.display("> Init Test Project Google Android");

		boolean result = false;

		try {
			if (new TestProjectGenerator(this.adapter).makeProject()) {
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
