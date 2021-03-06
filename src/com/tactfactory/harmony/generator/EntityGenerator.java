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

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.platform.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Entity Generator.
 * Used to decorate or generate entities.
 */
public class EntityGenerator extends BaseGenerator<IAdapter> {

    /** Constructor.
     * @param adapter Adapter used by this generator
     * @throws Exception if adapter is null
     */
    public EntityGenerator(final IAdapter adapter) throws Exception {
        super(adapter);
        this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
    }

    /**
     * Implements serializable
     * and add necessary getters and setters for all classes.
     */
    public final void generateAll() {
        ConsoleUtils.display(">> Decorate entities...");

        Iterable<EntityMetadata> entities =
                this.getAppMetas().getEntities().values();

        for (final EntityMetadata entity : entities) {
            if (!entity.isInternal()) {
                this.getDatamodel().put(
                        TagConstant.CURRENT_ENTITY,
                        entity.getName());

                ConsoleUtils.display(">>> Decorate " + entity.getName());

                List<IUpdater> updaters = this.getAdapter().getAdapterProject().getEntityFiles(entity);

                updaters.addAll(this.getAdapter().getAdapterProject().getEntityBaseFiles());

                this.processUpdater(updaters);
            }
        }
    }

       /**
     * Implements serializable
     * and add necessary getters and setters for all classes.
     */
    public final void generateAllBase() {
        ConsoleUtils.display(">> Decorate Entity Base Files...");

        List<IUpdater> updaters = this.getAdapter().getAdapterProject().getEntityBaseFiles();

        this.processUpdater(updaters);
    }
}
