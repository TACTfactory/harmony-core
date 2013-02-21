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

public class RestGenerator extends BaseGenerator {

	public RestGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void generateAll() {
		this.generateWSAdapter();
		try {
			new TestWSGenerator(this.adapter).generateAll();
			
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	protected void generateWSAdapter() {
		this.updateLibrary("httpmime-4.1.1.jar");
		
		TranslationMetadata.addDefaultTranslation("common_network_error", "Connection error", Group.COMMON);
		
		ConfigMetadata.addConfiguration("rest_url_prod", "https://domain.tlk:443/");
		ConfigMetadata.addConfiguration("rest_url_dev", "https://dev.domain.tlk:443/");
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
		
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (cm.options.get("rest")!=null) {
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeSource( 
						"base/TemplateWebServiceClientAdapterBase.java", 
						"base/"+cm.name+"WebServiceClientAdapterBase.java", 
						true);
				this.makeSource(
						"TemplateWebServiceClientAdapter.java", 
						cm.name+"WebServiceClientAdapter.java", 
						true);
			}
		}	
		try {
			new TranslationGenerator(this.adapter).generateStringsXml();
			new ConfigGenerator(this.adapter).generateConfigXml();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
		
	@Override
	protected void makeSource(final String templateName, final String fileName, final boolean override) {
		final String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getData() + "/" + fileName;
		final String fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
