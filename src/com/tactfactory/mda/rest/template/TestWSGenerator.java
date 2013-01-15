/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.rest.template;

import java.util.Map;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.rest.meta.RestMetadata;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.PackageUtils;

public class TestWSGenerator extends BaseGenerator {
	protected String localNameSpace;
	protected Map<String, Object> entities;
	
	public TestWSGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void generateAll() {
		ConsoleUtils.display(">> Generate Rest test...");
		
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && !cm.internal && !cm.fields.isEmpty()){
				this.localNameSpace = this.adapter.getNameSpace(cm, this.adapter.getTest());
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.generate();
			}
		}
	}
	
	/**  
	 * Generate Rest Test  
	 */ 
	private void generate() {
		// Info
				ConsoleUtils.display(">>> Generate Rest test for " +  this.datamodel.get(TagConstant.CURRENT_ENTITY));
		
		try {			
			this.makeSourceTest(
					"TemplateTestWSBase.java", 
					"%sTestWSBase.java",
					true);
			
			this.makeSourceTest(
					"TemplateTestWS.java", 
					"%sTestWS.java",
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

}
