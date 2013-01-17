package com.tactfactory.mda.fixture.template;

import java.io.File;
import java.io.IOException;

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
	
	public void load() {
		File fixtAppSrc = new File("fixtures/app");
		if(fixtAppSrc.exists()){
			File fixtAppDest = new File(this.adapter.getAssetsPath());
			try {
				FileUtils.copyDirectory(fixtAppSrc, fixtAppDest);
				ConsoleUtils.displayDebug("Copying fixtures/app into "+fixtAppDest.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			ConsoleUtils.displayError("You must init the fixtures before loading them. Use the command orm:fixture:init.");
		}
	}
	
	public void init() {
		 try {
			 //Copy JDOM Library
			this.updateLibrary("jdom-2.0.2.jar");
			
			//Create base classes for Fixtures loaders
			this.makeSource("FixtureBase.java", "FixtureBase.java", true);
			this.makeSource("DataManager.java", "DataManager.java", true);
			
			//Update ApplicationGenerator
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteGenerator(this.adapter).generateDatabase();
			
			//Create each entity's data loader
			for(ClassMetadata cm : this.appMetas.entities.values()){
				if(cm.fields.size()>0){
					this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
					this.makeSource("TemplateDataLoader.java", cm.name+"DataLoader.java", true);
					this.makeBaseFixture("TemplateFixture.xml", cm.name+".xml", true);
				}
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
		String fullFilePath = "fixtures/app/"+fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
		
		fullFilePath = "fixtures/test/"+fileName;
		fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
