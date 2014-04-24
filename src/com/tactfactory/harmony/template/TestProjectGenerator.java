/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.harmony.template;

import java.util.List;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;

/**
 * Test project generator.
 *
 */
public class TestProjectGenerator extends BaseGenerator<IAdapter> {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception if adapter is null
	 */
	public TestProjectGenerator(final IAdapter adapter) throws Exception {
		super(adapter);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
     * Make Platform specific Project Structure.
     * @return success to make the platform test project folder
     */
    public final boolean makeProject() {
		boolean result = false;

		List<IUpdater> updaters = this.getAdapter().getAdapterProject()
                .getTestProjectFilesToClear();
		this.processUpdater(updaters);
		
		// create libs folder
		updaters = this.getAdapter().getAdapterProject()
		        .getTestProjectCreateFolders();
		this.processUpdater(updaters);

		updaters = this.getAdapter().getAdapterProject().getTestProjectFiles();
		this.processUpdater(updaters);

		updaters = this.getAdapter().getAdapterProject()
		        .getTestProjectLibraries();
		this.processUpdater(updaters);
		
		result = true;
		
		return result;
	}
}
