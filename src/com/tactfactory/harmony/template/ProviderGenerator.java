/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import java.util.List;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * The provider generator.
 *
 */
public class ProviderGenerator extends BaseGenerator {
	/** Provider name string id. */
	public static final String PROVIDER_NAME_STRING_ID = 
			"app_provider_name";
	
	/** Provider description string id. */
	public static final String PROVIDER_DESCRIPTION_STRING_ID = 
			"app_provider_description";

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception if adapter is null
	 */
	public ProviderGenerator(final IAdapter adapter) throws Exception {
		super(adapter);
		
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Generate the provider.
	 */
	public final void generateProvider() {
		try {
			List<IUpdater> updaters = this.getAdapter().getAdapterProject()
			        .getProviderFiles();
			this.processUpdater(updaters);

			for (EntityMetadata cm : this.getAppMetas().getEntities().values()) {
	            if (cm.hasFields()) {
	                this.getDatamodel().put(
	                        TagConstant.CURRENT_ENTITY, cm.getName());
	                this.getDatamodel().put(
	                        TagConstant.PROVIDER_ID, 
	                        ProviderGenerator.generateProviderUriId(cm));

	                updaters = this.getAdapter().getAdapterProject()
	                        .getProviderAdaptersEntityFiles(cm);
	                this.processUpdater(updaters);
	            }
	        }

			TranslationMetadata.addDefaultTranslation(
					"uri_not_supported",
					"URI not supported",
					Group.PROVIDER);
			TranslationMetadata.addDefaultTranslation(
					PROVIDER_NAME_STRING_ID,
					"Provider of " + this.getAppMetas().getName(),
					Group.PROVIDER);
			TranslationMetadata.addDefaultTranslation(
					PROVIDER_DESCRIPTION_STRING_ID,
					"Provider of "
						+ this.getAppMetas().getName()
						+ " to access data",
					Group.PROVIDER);

			new TranslationGenerator(this.getAdapter()).generateStringsXml();
			new TestProviderGenerator(this.getAdapter()).generateAll();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate a provider uri ID.
	 * @param em The entity metadata
	 * @return The provider uri ID
	 */
	public static int generateProviderUriId(final EntityMetadata em) {
		int result = 0;
		result = Math.abs(em.getName().hashCode());
		return result;
	}
}
