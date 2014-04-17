/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

import java.util.List;

/**
 * Tests generator.
 */
public class TestGenerator extends BaseGenerator {
	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public TestGenerator(final IAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate all tests.
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Generate Repository test...");

		//this.initTestAndroid();
		this.getDatamodel().put("dataLoader", this.getAdapter()
		        .getAdapterProject().isDataLoaderAlreadyGenerated());

		List<IUpdater> updaters = this.getAdapter().getAdapterProject()
                .getTestFiles();
        this.processUpdater(updaters);
        
        Iterable<EntityMetadata> entities =
                this.getAppMetas().getEntities().values();
        
		for (EntityMetadata entity : entities) {
			if (!entity.isInternal() && entity.hasFields()) {
				this.getDatamodel().put(
				        TagConstant.CURRENT_ENTITY,
						entity.getName());
				this.generateEntityTest(entity);
			}
		}
	}

	/**
	 * Generate entity DataBase Test.
	 */
	private void generateEntityTest(EntityMetadata entity) {
		// Info
		ConsoleUtils.display(">>> Generate Repository test for "
					+ this.getDatamodel().get(
							TagConstant.CURRENT_ENTITY));

		try {
			List<IUpdater> updaters = this.getAdapter().getAdapterProject()
			        .getTestEntityFiles(entity);
			this.processUpdater(updaters);
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Initialize Test Project folders and files.
	 * @return success of Test project initialization
	 */
	public final boolean initTestProject() {
		ConsoleUtils.display(String.format(
		        "> Init Test Project for %s project",
		        this.getAdapter().getPlatform()));

		boolean result = false;

		try {
			if (new TestProjectGenerator(this.getAdapter()).makeProject()) {
				ConsoleUtils.displayDebug(String.format(
				        "Init Test %s Project Success!",
				        this.getAdapter().getPlatform()));

				result = true;
			} else {
				ConsoleUtils.displayError(
						new Exception(String.format(
						        "Init Test %s Project Fail!",
						        this.getAdapter().getPlatform())));
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
		
		return result;
	}
}
