/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.fixture.template;

import java.io.File;
import java.io.IOException;

import java.io.FileFilter;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.bundles.fixture.metadata.FixtureMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
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
		File fixtTestSrc = new File("fixtures/test");
		if(fixtAppSrc.exists()){
			File fixtAppDest = new File(this.adapter.getAssetsPath()+"/app");
			File fixtTestDest = new File(this.adapter.getAssetsPath()+"/test");
			if(!fixtAppDest.exists())
				fixtAppDest.mkdir();
			if(!fixtTestDest.exists())
				fixtTestDest.mkdir();
			try {
				FileFilter ff = new FileFilter() {
					@Override
					public boolean accept(File arg0) {
						return (arg0.getPath().endsWith(".xml") || arg0.getPath().endsWith(".yml")); 
					}
				};
				FileUtils.copyDirectory(fixtAppSrc, fixtAppDest, ff);
				ConsoleUtils.displayDebug("Copying fixtures/app into "+fixtAppDest.getPath());
				FileUtils.copyDirectory(fixtTestSrc, fixtTestDest, ff);
				ConsoleUtils.displayDebug("Copying fixtures/test into "+fixtTestDest.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			ConsoleUtils.displayError(new Exception("You must init the fixtures before loading them. Use the command orm:fixture:init."));
		}
	}
	
	public void init() {
		 try {
			 String fixtureType = ((FixtureMetadata)this.appMetas.options.get("fixture")).type;
			 
			 //Copy JDOM Library
			this.updateLibrary("jdom-2.0.2.jar");
			this.updateLibrary("snakeyaml-1.10-android.jar");
			
			//Create base classes for Fixtures loaders
			this.makeSource("FixtureBase.java", "FixtureBase.java", true);
			this.makeSource("DataManager.java", "DataManager.java", true);
			
			//Update SQLiteOpenHelper
			new SQLiteGenerator(this.adapter).generateDatabase();
			
			//Create each entity's data loader
			for(ClassMetadata cm : this.appMetas.entities.values()){
				if(cm.fields.size()>0){
					this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
					this.makeSource("TemplateDataLoader.java", cm.name+"DataLoader.java", true);
					this.makeBaseFixture("TemplateFixture."+fixtureType, cm.name+"."+fixtureType, false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void purge() {
		for(ClassMetadata cm : this.appMetas.entities.values()){
			this.removeSource(cm.name+".xml");
			this.removeSource(cm.name+".yml");
		}
		
	}
	
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getFixture() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	protected void removeSource(String fileName) {
		String fullFilePath = this.adapter.getAssetsPath() + "/" + fileName;
		File f = new File(fullFilePath);
		f.delete();
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
