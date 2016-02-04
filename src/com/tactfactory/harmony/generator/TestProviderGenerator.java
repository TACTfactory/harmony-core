/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.generator;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

import java.util.List;

/**
 * Generator for provider tests.
 */
public class TestProviderGenerator extends BaseGenerator<IAdapter> {
    /**
     * Constructor.
     * @param adapter The adapter to use
     * @throws Exception if adapter is null
     */
    public TestProviderGenerator(final IAdapter adapter) throws Exception {
        super(adapter);
        this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
    }

    /**
     * Generate all tests.
     */
    public final void generateAll() {
        ConsoleUtils.display(">> Generate Provider test...");

        this.getDatamodel().put("dataLoader", this.getAdapter().getAdapterProject().isDataLoaderAlreadyGenerated());

        for (final EntityMetadata cm : this.getAppMetas().getEntities().values()) {
            if (!cm.isInternal() && cm.hasFields()) {
                this.getDatamodel().put(
                        TagConstant.CURRENT_ENTITY,
                        cm.getName());
                this.generateEntityTest(cm);
            }
        }
    }

    /**
     * Generate DataBase Test for an entity.
     */
    private void generateEntityTest(EntityMetadata entity) {
        // Info
        ConsoleUtils.display(">>> Generate Providers test for " + entity.getName());

        List<IUpdater> updaters = this.getAdapter().getAdapterProject().getTestProviderEntityFiles(entity);
        this.processUpdater(updaters);
    }
}
