/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.rest.template;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.ConfigMetadata;
import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.ConfigGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.template.TranslationGenerator;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * Generator for bundle Rest.
 */
public class RestGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The used adapter.
	 * @throws Exception 
	 */
	public RestGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}
	
	/**
	 * Generates everything.
	 */
	public final void generateAll() {
		this.generateWSAdapter();
		try {
			new TestWSGenerator(this.getAdapter()).generateAll();
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate WebService Adapter.
	 */
	protected final void generateWSAdapter() {
		this.updateLibrary("httpmime-4.1.1.jar");
		
		TranslationMetadata.addDefaultTranslation(
				"common_network_error", "Connection error", Group.COMMON);
		
		ConfigMetadata.addConfiguration(
				"rest_url_prod", "https://domain.tlk:443/");
		ConfigMetadata.addConfiguration(
				"rest_url_dev", "https://dev.domain.tlk:443/");
		ConfigMetadata.addConfiguration("rest_check_ssl", "true");
		ConfigMetadata.addConfiguration("rest_ssl", "ca.cert");
		
		// Make Abstract Adapter Base general for all entities
		this.makeSource(
				"base/WebServiceClientAdapterBase.java", 
				"base/WebServiceClientAdapterBase.java",
				true);
		
		// Make RestClient
		this.makeSource(
				"RestClient.java", 
				"RestClient.java",
				true);
		
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (cm.getOptions().get("rest") != null) {
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeSource(
						"base/TemplateWebServiceClientAdapterBase.java", 
						"base/" + cm.getName() 
							+ "WebServiceClientAdapterBase.java", 
						true);
				this.makeSource(
						"TemplateWebServiceClientAdapter.java", 
						cm.getName() + "WebServiceClientAdapter.java", 
						true);
			}
		}	
		try {
			new TranslationGenerator(this.getAdapter()).generateStringsXml();
			new ConfigGenerator(this.getAdapter()).generateConfigXml();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
		
	@Override
	protected final void makeSource(final String templateName, 
			final String fileName, 
			final boolean override) {
		final String fullFilePath = 
				this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace() 
				+ "/" + this.getAdapter().getData() + "/"
				+ fileName;
		final String fullTemplatePath = 
				this.getAdapter().getTemplateSourceProviderPath().substring(1) 
				+ templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
