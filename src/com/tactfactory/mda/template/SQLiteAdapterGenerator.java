/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.PackageUtils;

public class SQLiteAdapterGenerator extends BaseGenerator {	
	protected Map<String, Object> entities;
	protected String localNameSpace;
	protected boolean isWritable = true;

	public SQLiteAdapterGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.entities = new HashMap<String, Object>();
		
		for(ClassMetadata meta : metas.entities.values()){
			if(!meta.fields.isEmpty()){
				Map<String, Object> modelClass = meta.toMap(this.adapter);
				this.entities.put((String) modelClass.get(TagConstant.NAME), modelClass);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void generateAll() {
		ConsoleUtils.display(">> Generate Adapter...");
		
		for(Object modelEntity : this.entities.values()) {
			
			Map<String, Object> entity = (Map<String, Object>) modelEntity;
			this.localNameSpace = (String) entity.get(TagConstant.DATA_NAMESPACE);
			
			// Make class
			this.datamodel = (HashMap<String, Object>) modelEntity;
			
			this.generate();
		}
	}
	
	private void generate() {
		// Info
		ConsoleUtils.display(">>> Generate Adapter for " +  this.datamodel.get(TagConstant.NAME));
		
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
				String.format(filename, this.datamodel.get(TagConstant.NAME)));
		
		String fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + template;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
