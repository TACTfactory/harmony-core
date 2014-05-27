/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import java.util.List;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;

/**
 * Generate the application.
 *
 */
public class ApplicationGenerator extends BaseGenerator<IAdapter> {
	/**
	 * Constructor.
	 * @param adapter the adapter to use
	 * @throws Exception if adapter is null
	 */
	public ApplicationGenerator(final IAdapter adapter) throws Exception {
		super(adapter);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));

		List<String> services = this.getAdapter().getAdapterProject()
		        .getServices();
		this.getDatamodel().put(TagConstant.SERVICES, services);
	}

	/**
	 * Generate the application.
	 */
	public final void generateApplication() {
	    List<IUpdater> updaters =
	            this.getAdapter().getAdapterProject().getApplicationFiles();
	    this.processUpdater(updaters);
	}
}
