package com.tactfactory.mda.rest.template;

import java.io.File;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.ConfigMetadata;
import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.ConfigGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.template.TranslationGenerator;
import com.tactfactory.mda.utils.FileUtils;

public class RestGenerator extends BaseGenerator {

	public RestGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.generateWSAdapter();
		try {
			new TestWSGenerator(this.adapter).generateAll();
			
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	protected void generateWSAdapter(){
		this.updateLibrary("httpmime-4.1.1.jar");
		
		TranslationMetadata.addDefaultTranslation("common_network_error", "Connection error", Group.COMMON);
		
		ConfigMetadata.addConfiguration("rest_url_prod", "https://domain.tlk:443/");
		ConfigMetadata.addConfiguration("rest_url_dev", "https://dev.domain.tlk:443/");
		ConfigMetadata.addConfiguration("rest_check_ssl", "true");
		ConfigMetadata.addConfiguration("rest_ssl", "ca.cert");
		
		// Make Abstract Adapter Base general for all entities
		this.makeSource(
				"WebServiceClientAdapterBase.java", 
				"WebServiceClientAdapterBase.java",
				true);
		
		// Make RestClient
		this.makeSource(
				"RestClient.java", 
				"RestClient.java",
				true);
		
		for (ClassMetadata cm : this.appMetas.entities.values()) {
			if (cm.options.get("rest")!=null) {
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeSource( 
						"TemplateWebServiceClientAdapterBase.java", 
						cm.name+"WebServiceClientAdapterBase.java", 
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getData() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
