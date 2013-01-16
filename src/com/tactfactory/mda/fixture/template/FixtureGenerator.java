package com.tactfactory.mda.fixture.template;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ApplicationGenerator;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.ProjectGenerator;
import com.tactfactory.mda.template.SQLiteGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;

public class FixtureGenerator extends BaseGenerator{

	public FixtureGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void init() {
		 try {
			 //Copy JDOM Library
			this.updateLibrary("jdom-2.0.2.jar");
			
			//Create fixture's base folders
			FileUtils.makeFolder("fixtures");
			FileUtils.makeFolder("fixtures/app");
			FileUtils.makeFolder("fixtures/test");
			
			//Create base classes for Fixtures loaders
			this.makeSource("FixtureBase.java", "FixtureBase.java", true);
			this.makeSource("DataManager.java", "DataManager.java", true);
			
			//Update ApplicationGenerator
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteGenerator(this.adapter).generateDatabase();
			
			//Create each entity's data loader
			for(ClassMetadata cm : this.appMetas.entities.values()){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
				this.makeSource("TemplateDataLoader.java", cm.name+"DataLoader.java", true);
				this.makeBaseFixture("TemplateFixture.xml", cm.name+".xml", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getFixture() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	protected void makeBaseFixture(String templateName, String fileName, boolean override){
		String fullFilePath = "fixture/app/"+fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
		
		fullFilePath = "fixture/test/"+fileName;
		fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
