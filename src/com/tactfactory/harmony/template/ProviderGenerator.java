/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.androidxml.ManifestUpdater;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

/**
 * The provider generator.
 *
 */
public class ProviderGenerator extends BaseGenerator {
	/* Constants. */
	/** String prefix. */
	private static final String STRING_PREFIX = "@string/";
	
	/** Provider name string id. */
	private static final String PROVIDER_NAME_STRING_ID = 
			"app_provider_name";
	
	/** Provider description string id. */
	private static final String PROVIDER_DESCRIPTION_STRING_ID = 
			"app_provider_description";
	
	
	/* Variables. */
	/** The local name space. */
	private String localNameSpace;
	/** The provider name. */
	private String nameProvider;

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception if adapter is null
	 */
	public ProviderGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.nameProvider =
				CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,
						this.getAppMetas().getName() + "Provider");
		
		this.localNameSpace =
				this.getAppMetas().getProjectNameSpace().replace('/', '.')
				+ "."
				+ this.getAdapter().getProvider();


		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));

		this.getDatamodel().put(
				TagConstant.LOCAL_NAMESPACE, this.localNameSpace);
	}

	/**
	 * Generate the provider adapters.
	 */
	public final void generateProviderAdapters() {
		this.makeSourceProvider(
				"utils/base/ApplicationProviderUtilsBase.java",
				"utils/base/ProviderUtilsBase.java",
				true);

		for (EntityMetadata cm : this.getAppMetas().getEntities().values()) {
			if (cm.hasFields()) {

				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, cm.getName());
				this.getDatamodel().put(
						TagConstant.PROVIDER_ID, 
						ProviderGenerator.generateProviderUriId(cm));

				// Provider adapters
				this.makeSourceProvider("TemplateProviderAdapter.java",
						cm.getName() + "ProviderAdapter.java", false);
				this.makeSourceProvider("base/TemplateProviderAdapterBase.java",
						"base/" + cm.getName() + "ProviderAdapterBase.java",
						true);
				

				// Make contracts
				this.makeSourceProvider("/contract/TemplateContract.java",
						"contract/" + cm.getName() + "Contract.java", false);
				this.makeSourceProvider("/contract/base/TemplateContractBase.java",
						"contract/base/" + cm.getName() + "ContractBase.java", true);


				// Provider utils
				if (!cm.isInternal()) {
					this.makeSourceProvider(
							"utils/TemplateProviderUtils.java",
							"utils/" + cm.getName() + "ProviderUtils.java",
							false);
					this.makeSourceProvider(
							"utils/base/TemplateProviderUtilsBase.java",
							"utils/base/"
									+ cm.getName()
									+ "ProviderUtilsBase.java",
							true);
				}
			}
		}
	}

	/**
	 * Generate the provider.
	 */
	public final void generateProvider() {
		try {
			this.makeSourceProvider("TemplateProvider.java",
					this.nameProvider + ".java", false);
			this.makeSourceProvider("base/TemplateProviderBase.java",
					"base/" + this.nameProvider + "Base.java", true);
			this.makeSourceProvider("base/ProviderAdapterBase.java",
					"base/ProviderAdapterBase.java", true);

			// Package infos
			this.makeSourceProvider("provider-package-info.java",
					"package-info.java", false);
			this.makeSourceProvider("base/provider-package-info.java",
					"base/package-info.java", false);

			this.makeSourceProvider("utils/base/package-info.java",
					"utils/base/package-info.java", false);
			this.makeSourceProvider("utils/package-info.java",
					"utils/package-info.java", false);

			this.generateProviderAdapters();

			ManifestUpdater manifest = new ManifestUpdater(this.getAdapter());
			manifest.addProvider(
					String.format("%s.%s",
							this.localNameSpace,
							this.nameProvider),
					STRING_PREFIX + PROVIDER_NAME_STRING_ID,
					this.getAppMetas().getProjectNameSpace().replace('/', '.')
								+ ".provider",
					STRING_PREFIX + PROVIDER_DESCRIPTION_STRING_ID);
			manifest.save();

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
	 * Make Java Source Code.
	 *
	 * @param template Template path file.
	 * <br/>For list activity is "TemplateListActivity.java"
	 * @param filename The destination file name
	 * @param overwrite True if the method should overwrite all existing files.
	 */
	private void makeSourceProvider(final String template,
			final String filename, final boolean overwrite) {

		final String fullFilePath = String.format("%s%s/%s",
						this.getAdapter().getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace)
							.toLowerCase(),
						filename);

		final String fullTemplatePath = String.format("%s%s",
				this.getAdapter().getTemplateSourceProviderPath(),
				template);

		super.makeSource(fullTemplatePath, fullFilePath, overwrite);
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
