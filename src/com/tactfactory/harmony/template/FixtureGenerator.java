/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.io.File;
import java.util.List;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FixtureMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Fixture bundle generator.
 */
public class FixtureGenerator extends BaseGenerator<IAdapter> {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception If adapter is null
	 */
	public FixtureGenerator(final IAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Load the fixtures.
	 */
	public final void load() {
		List<IUpdater> updaters = this.getAdapter()
				.getAdapterProject().getFixtureAssets();
		
		this.processUpdater(updaters);

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
			
			List<IUpdater> updaters = this.getAdapter()
			        .getAdapterProject().getFixtureLibraries(meta.getType());
			this.processUpdater(updaters);

			updaters = this.getAdapter()
                    .getAdapterProject().getFixtureFiles(force);
            this.processUpdater(updaters);

			//Update SQLiteOpenHelper
			new SQLiteGenerator(this.getAdapter()).generateDatabase();
			new TestGenerator(this.getAdapter()).generateAll();
			new TestProviderGenerator(this.getAdapter()).generateAll();

			Iterable<EntityMetadata> entities =
			        this.getAppMetas().getEntities().values();
			
			//Create each entity's data loader
			for (final EntityMetadata classMeta : entities) {
				if (classMeta.hasFields() && !classMeta.isInternal()) {
					this.getDatamodel().put(
					        TagConstant.CURRENT_ENTITY,
							classMeta.getName());
					
					updaters = this.getAdapter().getAdapterProject()
					        .getFixtureEntityDefinitionFiles(
					        		fixtureType, classMeta);
					this.processUpdater(updaters);
					
					updaters = this.getAdapter().getAdapterProject()
					        .getFixtureEntityFiles(
					        		force, fixtureType, classMeta);
		            this.processUpdater(updaters);
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

				this.removeSource("debug/" + classMeta.getName() + ".xml");
                this.removeSource("debug/" + classMeta.getName() + ".yml");
                
				this.removeSource("test/" + classMeta.getName() + ".xml");
				this.removeSource("test/" + classMeta.getName() + ".yml");
			}
		}
	}

	/**
	 * Delete file in assets directory.
	 * @param fileName The filename.
	 */
	private void removeSource(final String fileName) {
		final String fullFilePath =
				this.getAdapter().getAssetsPath() + fileName;
		final File file = new File(fullFilePath);

		if (file.exists() && !file.delete()) {
			ConsoleUtils.displayError(
					new Exception("Couldn't delete file "
							+ file.getPath()));
		}
	}
}
