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
import com.tactfactory.mda.rest.template.RestGenerator;
import com.tactfactory.mda.template.ApplicationGenerator;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.EntityGenerator;
import com.tactfactory.mda.template.SQLiteAdapterGenerator;

public class SyncGenerator extends BaseGenerator {

	public SyncGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.generateSync();
	}
	
	protected void generateSync(){
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace.replaceAll("\\.", "/") + "/entity/base/EntityBase.java";
		String fullTemplatePath = this.adapter.getTemplateSourceEntityBasePath().substring(1) + "EntityBase.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		// Make Abstract Adapter Base general for all entities
		this.makeSource(
				"TemplateSyncService.java", 
				String.format("%sSyncService.java", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name)),
				true);
		
		try {
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteAdapterGenerator(this.adapter).generateAll();
			new RestGenerator(this.adapter).generateAll();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getService() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceServicePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}

