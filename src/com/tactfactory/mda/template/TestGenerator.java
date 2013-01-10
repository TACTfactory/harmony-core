/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.util.Map;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.PackageUtils;

public class TestGenerator extends BaseGenerator {
	protected String localNameSpace;
	protected Map<String, Object> entities;
	
	public TestGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void generateAll() {
		ConsoleUtils.display(">> Generate Repository test...");
		
		this.initTestAndroid();
		
		
		for(ClassMetadata cm : this.appMetas.entities.values()){
			//this.datamodel = (HashMap<String, Object>) cm.toMap(this.adapter);
			this.localNameSpace = this.adapter.getNameSpace(cm, this.adapter.getTest());
			this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
			this.generate();
		}
	}
	
	/**  
	 * Generate DataBase Test  
	 */ 
	private void generate() {
		// Info
				ConsoleUtils.display(">>> Generate Repository test for " +  this.datamodel.get(TagConstant.CURRENT_ENTITY));
		
		try {			
			this.makeSourceTest(
					"TemplateTestDBBase.java", 
					"%sTestDBBase.java",
					true);
			
			this.makeSourceTest(
					"TemplateTestDB.java", 
					"%sTestDB.java",
					false);

		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceTest(String template, String filename, boolean override) {
		String fullFilePath = String.format("%s%s/%s",
						this.adapter.getTestPath(),
						PackageUtils.extractPath(String.format(
								"%s/%s", this.adapter.getSource(), this.localNameSpace)).toLowerCase(),
						String.format(filename, this.datamodel.get(TagConstant.CURRENT_ENTITY)));
		
		String fullTemplatePath = String.format("%s%s",
					this.adapter.getTemplateTestsPath(),
					template);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**
	 * Initialize Test Android Project folders and files
	 * @return success of Test Android project initialization
	 */
	public boolean initTestAndroid() {
		ConsoleUtils.display("> Init Test Project Google Android");

		boolean result = false;

		try {
			if(new TestProjectGenerator(this.adapter).makeProject()){
				ConsoleUtils.displayDebug("Init Test Android Project Success!");
				
				result = true;
			} else {
				ConsoleUtils.displayError("Init Test Android Project Fail!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
