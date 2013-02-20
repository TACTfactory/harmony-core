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
import java.io.FileFilter;
import java.io.IOException;

import com.tactfactory.mda.bundles.fixture.metadata.FixtureMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.SQLiteGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

public class FixtureGenerator extends BaseGenerator{

	public FixtureGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void load() {
		final File fixtAppSrc = new File("fixtures/app");
		final File fixtTestSrc = new File("fixtures/test");
		if(fixtAppSrc.exists()){
			final File fixtAppDest = new File(this.adapter.getAssetsPath()+"/app");
			final File fixtTestDest = new File(this.adapter.getAssetsPath()+"/test");
			if(!fixtAppDest.exists()) {
				fixtAppDest.mkdir();
			}
			if(!fixtTestDest.exists()) {
				fixtTestDest.mkdir();
			}
			try {
				final FileFilter ff = new FileFilter() {
					@Override
					public boolean accept(final File arg0) {
						return arg0.getPath().endsWith(".xml") || arg0.getPath().endsWith(".yml"); 
					}
				};
				FileUtils.copyDirectory(fixtAppSrc, fixtAppDest, ff);
				ConsoleUtils.displayDebug("Copying fixtures/app into "+fixtAppDest.getPath());
				FileUtils.copyDirectory(fixtTestSrc, fixtTestDest, ff);
				ConsoleUtils.displayDebug("Copying fixtures/test into "+fixtTestDest.getPath());
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		}else{
			ConsoleUtils.displayError(new Exception("You must init the fixtures before loading them. Use the command orm:fixture:init."));
		}
	}
	
	public void init() {
		 try {
			 final String fixtureType = ((FixtureMetadata)this.appMetas.options.get("fixture")).type;
			 
			 //Copy JDOM Library
			this.updateLibrary("jdom-2.0.2.jar");
			this.updateLibrary("snakeyaml-1.10-android.jar");
			
			//Create base classes for Fixtures loaders
			this.makeSource("FixtureBase.java", "FixtureBase.java", true);
			this.makeSource("DataManager.java", "DataManager.java", true);
			
			//Update SQLiteOpenHelper
			new SQLiteGenerator(this.adapter).generateDatabase();
			
			//Create each entity's data loader
			for(final ClassMetadata cm : this.appMetas.entities.values()){
				if(cm.fields.size()>0){
					this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
					this.makeSource("TemplateDataLoader.java", cm.name+"DataLoader.java", true);
					this.makeBaseFixture("TemplateFixture."+fixtureType, cm.name+"."+fixtureType, false);
				}
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	public void purge() {
		for(final ClassMetadata cm : this.appMetas.entities.values()){
			this.removeSource(cm.name+".xml");
			this.removeSource(cm.name+".yml");
		}
		
	}
	
	@Override
	protected void makeSource(final String templateName, final String fileName, final boolean override) {
		final String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getFixture() + "/" + fileName;
		final String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	protected void removeSource(final String fileName) {
		final String fullFilePath = this.adapter.getAssetsPath() + "/" + fileName;
		final File f = new File(fullFilePath);
		f.delete();
	}
	
	protected void makeBaseFixture(final String templateName, final String fileName, final boolean override){
		String fullFilePath = "fixtures/app/"+fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
		
		fullFilePath = "fixtures/test/"+fileName;
		fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
