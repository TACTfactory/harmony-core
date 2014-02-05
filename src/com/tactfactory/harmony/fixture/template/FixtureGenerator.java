/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.fixture.template;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import com.tactfactory.harmony.fixture.metadata.FixtureMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.BaseGenerator;
import com.tactfactory.harmony.template.SQLiteGenerator;
import com.tactfactory.harmony.template.TagConstant;
import com.tactfactory.harmony.template.TestDBGenerator;
import com.tactfactory.harmony.template.TestProviderGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Fixture bundle generator.
 */
public class FixtureGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception If adapter is null
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
		final File fixtDebugSrc = new File("fixtures/debug");
		final File fixtTestSrc = new File("fixtures/test");

		if (fixtAppSrc.exists()) {
			final File fixtAppDest =
					new File(this.getAdapter().getAssetsPath() + "/app");

			final File fixtDebugDest =
					new File(this.getAdapter().getAssetsPath() + "/debug");

			final File fixtTestDest =
					new File(this.getAdapter().getAssetsPath() + "/test");

			if (!fixtAppDest.exists() && !fixtAppDest.mkdirs()) {
				ConsoleUtils.displayError(
						new Exception("Couldn't create folder "
								+ fixtAppDest.getAbsolutePath()));
			}

			if (!fixtDebugDest.exists() && !fixtDebugDest.mkdirs()) {
				ConsoleUtils.displayError(
						new Exception("Couldn't create folder "
								+ fixtDebugDest.getAbsolutePath()));
			}

			if (!fixtTestDest.exists() && !fixtTestDest.mkdirs()) {
				ConsoleUtils.displayError(
						new Exception("Couldn't create folder "
								+ fixtTestDest.getAbsolutePath()));
			}

			try {
				final FileFilter fileFilter = new FileFilter() {
					@Override
					public boolean accept(final File arg0) {
						return arg0.getPath().endsWith(".xml")
								|| arg0.getPath().endsWith(".yml");
					}
				};

				TactFileUtils.copyDirectory(
						fixtAppSrc, fixtAppDest, fileFilter);
				ConsoleUtils.displayDebug(
						"Copying fixtures/app into ",
						fixtAppDest.getPath());

				TactFileUtils.copyDirectory(
						fixtDebugSrc, fixtDebugDest, fileFilter);
				ConsoleUtils.displayDebug(
						"Copying fixtures/debug into ",
						fixtDebugDest.getPath());

				TactFileUtils.copyDirectory(
						fixtTestSrc, fixtTestDest, fileFilter);
				ConsoleUtils.displayDebug(
						"Copying fixtures/test into ",
						fixtTestDest.getPath());

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

			final FixtureMetadata meta =
					 (FixtureMetadata) this.getAppMetas().getOptions().get(
							 FixtureMetadata.NAME);
			 //Copy JDOM Library
			if (meta.getType().equals("xml")) {
				this.updateLibrary("jdom-2.0.2.jar");
			} else {
				this.updateLibrary("snakeyaml-1.10-android.jar");
			}

			//Create base classes for Fixtures loaders
			this.makeSource("FixtureBase.java", "FixtureBase.java", force);
			this.makeSource("DataManager.java", "DataManager.java", force);
			this.makeSource("DataLoader.java", "DataLoader.java", force);
			
			this.makeSource("package-info.java", "package-info.java", false);

			//Update SQLiteOpenHelper
			new SQLiteGenerator(this.getAdapter()).generateDatabase();
			new TestDBGenerator(this.getAdapter()).generateAll();
			new TestProviderGenerator(this.getAdapter()).generateAll();

			//Create each entity's data loader
			for (final EntityMetadata classMeta
					: this.getAppMetas().getEntities().values()) {
				if (classMeta.hasFields()
						&& !classMeta.isInternal()) {
					this.getDatamodel().put(TagConstant.CURRENT_ENTITY,
							classMeta.getName());
					this.makeSource("TemplateDataLoader.java",
							classMeta.getName() + "DataLoader.java",
							force);
					this.makeBaseFixture("TemplateFixture." + fixtureType,
							classMeta.getName() + "." + fixtureType,
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
		for (final ClassMetadata classMeta
				: this.getAppMetas().getEntities().values()) {
			if (classMeta.getFields().size() > 0) {
				this.removeSource("app/" + classMeta.getName() + ".xml");
				this.removeSource("app/" + classMeta.getName() + ".yml");

				this.removeSource("test/" + classMeta.getName() + ".xml");
				this.removeSource("test/" + classMeta.getName() + ".yml");
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
		final File file = new File(fullFilePath);

		if (file.exists() && !file.delete()) {
			ConsoleUtils.displayError(
					new Exception("Couldn't delete file "
							+ file.getPath()));
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


		fullFilePath = "fixtures/debug/" + fileName;
		fullTemplatePath =
				this.getAdapter().getTemplateSourceFixturePath()
				+ templateName;
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
