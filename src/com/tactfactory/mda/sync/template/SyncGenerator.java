package com.tactfactory.mda.sync.template;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;

public class SyncGenerator extends BaseGenerator {

	public SyncGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.generateSync();
	}
	
	protected void generateSync(){
		
		
		// Make Abstract Adapter Base general for all entities
		this.makeSource(
				"SyncBinder.java", 
				"SyncBinder.java",
				true);
		
		// Make RestClient
		this.makeSource(
				"ISyncListener.java", 
				"ISyncListener.java",
				true);
		
		// Make RestClient
		this.makeSource(
				"ISyncService.java", 
				"ISyncService.java",
				true);
	}
		
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getService() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceServicePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}

