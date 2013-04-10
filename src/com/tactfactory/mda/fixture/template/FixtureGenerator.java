/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.fixture.template;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import com.tactfactory.mda.fixture.metadata.FixtureMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.SQLiteGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

/**
 * Fixture bundle generator.
 */
public class FixtureGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception 
	 */
	public FixtureGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}
	
	/**
	 * Load the fixtures.
	 */
	public final void load() {
		final File fixtAppSrc = new File("fixtures/app");
		final File fixtTestSrc = new File("fixtures/test");
		
		if (fixtAppSrc.exists()) {
			final File fixtAppDest = 
					new File(this.getAdapter().getAssetsPath() + "/app");
			
			final File fixtTestDest = 
					new File(this.getAdapter().getAssetsPath() + "/test");
			
			if (!fixtAppDest.exists()) {
				if (!fixtAppDest.mkdirs()) {
					ConsoleUtils.displayError(
							new Exception("Couldn't create folder "
									+ fixtAppDest.getAbsolutePath()));
				}
			}
			if (!fixtTestDest.exists()) {
				if (!fixtTestDest.mkdirs()) {
					ConsoleUtils.displayError(
							new Exception("Couldn't create folder "
									+ fixtTestDest.getAbsolutePath()));
				}
			}
			try {
				final FileFilter ff = new FileFilter() {
					@Override
					public boolean accept(final File arg0) {
						return arg0.getPath().endsWith(".xml")
								|| arg0.getPath().endsWith(".yml"); 
					}
				};
				TactFileUtils.copyDirectory(fixtAppSrc, fixtAppDest, ff);
				ConsoleUtils.displayDebug(
						"Copying fixtures/app into " + fixtAppDest.getPath());
				TactFileUtils.copyDirectory(fixtTestSrc, fixtTestDest, ff);
				ConsoleUtils.displayDebug(
						"Copying fixtures/test into " + fixtTestDest.getPath());
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		} else {
			ConsoleUtils.displayError(new Exception(
					"You must init the fixtures before loading them."
					+ " Use the command orm:fixture:init."));
		}
	}
	
	/**
	 * Generate the loaders and the base fixtures.
	 * @param force True if you want to overwrite the fixture loaders.
	 */
	public final void init(final boolean force) {
		 try {
			 final String fixtureType = ((FixtureMetadata) 
							 this.getAppMetas().getOptions()
							 	.get("fixture")).getType();
			 
			 //Copy JDOM Library
			this.updateLibrary("jdom-2.0.2.jar");
			this.updateLibrary("snakeyaml-1.10-android.jar");
			
			//Create base classes for Fixtures loaders
			this.makeSource("FixtureBase.java", "FixtureBase.java", force);
			this.makeSource("DataManager.java", "DataManager.java", force);
			
			//Update SQLiteOpenHelper
			new SQLiteGenerator(this.getAdapter()).generateDatabase();
			
			//Create each entity's data loader
			for (final ClassMetadata cm 
					: this.getAppMetas().getEntities().values()) {
				if (cm.getFields().size() > 0) {
					this.getDatamodel().put(TagConstant.CURRENT_ENTITY,
							cm.getName());
					this.makeSource("TemplateDataLoader.java",
							cm.getName() + "DataLoader.java",
							force);
					this.makeBaseFixture("TemplateFixture." + fixtureType, 
							cm.getName() + "." + fixtureType, 
							false);
				}
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Delete the existing fixtures.
	 */
	public final void purge() {
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (cm.getFields().size() > 0) {
				this.removeSource("app/" + cm.getName() + ".xml");
				this.removeSource("app/" + cm.getName() + ".yml");
				
				this.removeSource("test/" + cm.getName() + ".xml");
				this.removeSource("test/" + cm.getName() + ".yml");
			}
		}
		
	}
	
	@Override
	protected final void makeSource(final String templateName,
			final String fileName, 
			final boolean override) {
		final String fullFilePath = 
				this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace()
				+ "/" + this.getAdapter().getFixture() + "/" 
				+ fileName;
		
		final String fullTemplatePath = 
				this.getAdapter().getTemplateSourceFixturePath() 
				+ templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**
	 * Delete file in assets directory.
	 * @param fileName The filename.
	 */
	protected final void removeSource(final String fileName) {
		final String fullFilePath = 
				this.getAdapter().getAssetsPath() + fileName;
		final File f = new File(fullFilePath);
		
		if (f.exists()) {
			if (!f.delete()) {
				ConsoleUtils.displayError(
						new Exception("Couldn't delete file "
								+ f.getPath()));
			}
		}
	}
	
	/**
	 * Make base fixture.
	 * @param templateName The template name.
	 * @param fileName The destination file name.
	 * @param override True for overwrite existing fixture.
	 */
	protected final void makeBaseFixture(final String templateName,
			final String fileName, 
			final boolean override) {
		String fullFilePath = "fixtures/app/" + fileName;
		String fullTemplatePath = 
				this.getAdapter().getTemplateSourceFixturePath()
				+ templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
		
		fullFilePath = "fixtures/test/" + fileName;
		fullTemplatePath =
				this.getAdapter().getTemplateSourceFixturePath()
				+ templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
