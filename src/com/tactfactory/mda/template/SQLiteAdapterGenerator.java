/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.PackageUtils;

public class SQLiteAdapterGenerator extends BaseGenerator {	
	protected String localNameSpace;
	protected boolean isWritable = true;

	public SQLiteAdapterGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.datamodel = Harmony.metas.toMap(this.adapter);
	}
	
	public void generateAll() {
		ConsoleUtils.display(">> Generate Adapter...");
		
		for(ClassMetadata cm : Harmony.metas.entities.values()){
			if(!cm.fields.isEmpty()){
				this.localNameSpace = this.adapter.getNameSpace(cm, this.adapter.getData());
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
				this.generate();
			}
		}
	}
	
	private void generate() {
		// Info
		ConsoleUtils.display(">>> Generate Adapter for " +  this.datamodel.get(TagConstant.CURRENT_ENTITY));
		
		try {
			this.makeSourceControler( 
					"TemplateSQLiteAdapterBase.java", 
					"%sSQLiteAdapterBase.java", true);
			
			this.makeSourceControler(
					"TemplateSQLiteAdapter.java", 
					"%sSQLiteAdapter.java", false);
			
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceControler(String template, String filename, boolean override) {
		String fullFilePath = String.format("%s%s/%s",
				this.adapter.getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				String.format(filename, this.datamodel.get(TagConstant.CURRENT_ENTITY)));
		
		String fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + template;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
