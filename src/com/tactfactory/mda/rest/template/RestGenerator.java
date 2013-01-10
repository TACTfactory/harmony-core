package com.tactfactory.mda.rest.template;

import java.util.HashMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;

public class RestGenerator extends BaseGenerator {

	public RestGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		this.datamodel = this.metas.toMap(this.adapter);
		this.generateWSAdapter();
	}
	
	protected void generateWSAdapter(){
		for (ClassMetadata cm : Harmony.metas.entities.values()) {
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
	}
	
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.metas.projectNameSpace + "/" + this.adapter.getData() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
