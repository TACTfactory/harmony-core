/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.sync.template;

import com.google.common.base.CaseFormat;
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
				"TemplateSyncBinder.java", 
				String.format("%sSyncBinder.java", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name)),
				true);
		
		// Make RestClient
		this.makeSource(
				"ITemplateSyncListener.java", 
				String.format("I%sSyncListener.java", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name)),
				true);
		
		// Make RestClient
		this.makeSource(
				"ITemplateSyncService.java", 
				String.format("I%sSyncService.java", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name)),
				true);
	}
		
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getService() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceServicePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}

