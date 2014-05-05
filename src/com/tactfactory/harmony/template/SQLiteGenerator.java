/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.Collection;
import java.util.List;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * SQLite Generator.
 */
public class SQLiteGenerator extends BaseGenerator<IAdapter> {

	/**
	 * Constructor.
	 * @param adapter The adapter to use
	 * @throws Exception if adapter is null
	 */
	public SQLiteGenerator(final IAdapter adapter) throws Exception {
		super(adapter);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
     * Generate the adapters and the criterias.
     */
    public final void generateAll() {
        ConsoleUtils.display(">> Generate Adapter...");
        
        this.generateDatabase();

        Collection<EntityMetadata> metas =
                this.getAppMetas().getEntities().values();
        
        for (final EntityMetadata classMeta : metas) {
            if (classMeta.hasFields() && !classMeta.isInternal()) {
                this.getDatamodel().put(
                        TagConstant.CURRENT_ENTITY,
                        classMeta.getName());
                this.generateAdapters(classMeta);
                this.generateCriterias(classMeta);
            }
        }

        ConsoleUtils.display(">> Generate CriteriaBase...");
        
        List<IUpdater> files =
                this.getAdapter().getAdapterProject().getCriteriasFiles();
        this.processUpdater(files);
    }
    
	/**
	 * Generate Database Interface Source Code.
	 */
	public final void generateDatabase() {
		// Info
		ConsoleUtils.display(">> Generate Database");

		try {
			List<IUpdater> files =
			        this.getAdapter().getAdapterProject().getDatabaseFiles();
			this.processUpdater(files);
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
     * Generate the current entity's adapters.
     */
    private void generateAdapters(EntityMetadata entity) {
        // Info
        ConsoleUtils.display(">>> Generate Adapter for "
                + this.getDatamodel().get(TagConstant.CURRENT_ENTITY));

        try {
            List<IUpdater> files = this.getAdapter().getAdapterProject()
                    .getSqlAdapterEntityFiles(entity);
            
            this.processUpdater(files);
        } catch (final Exception e) {
            ConsoleUtils.displayError(e);
        }
    }
    
    /**
     * Generate the current entity's criteria.
     */
    private void generateCriterias(EntityMetadata entity) {
        // Info
        ConsoleUtils.display(">>> Generate Criterias for "
                + this.getDatamodel().get(TagConstant.CURRENT_ENTITY));
        try {
            List<IUpdater> files = this.getAdapter().getAdapterProject()
                    .getCriteriasEntityFiles(entity);
            
            this.processUpdater(files);
        } catch (final Exception e) {
            ConsoleUtils.displayError(e);
        }
    }
}
