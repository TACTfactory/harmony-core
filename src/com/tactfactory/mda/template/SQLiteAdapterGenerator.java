/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			this.makeSourceControler(cfg, 
					"TemplateSQLiteAdapterBase.java", 
					"%sSQLiteAdapterBase.java", true);
			
			this.makeSourceControler(cfg, 
					"TemplateSQLiteAdapter.java", 
					"%sSQLiteAdapter.java", false);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** Make Java Source Code
	 * @param cfg Template engine
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeSourceControler(Configuration cfg, String template, String filename, boolean override) throws IOException,
			TemplateException {
		String filePath = String.format("%s%s/%s",
				this.adapter.getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				String.format(filename, this.datamodel.get(TagConstant.NAME)));
		File testFile = new File(filePath);

		if (!(!override && testFile.exists())) {
			File file = FileUtils.makeFile(filePath);
			
			// Debug Log
			ConsoleUtils.displayDebug("Generate Source : " + file.getPath()); 
			
			// Create
			Template tpl = cfg.getTemplate(
					this.adapter.getTemplateSourceProviderPath().substring(1) + template);
			
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(datamodel, output);
			output.flush();
			output.close();
		}
	}
}
