package com.tactfactory.mda.rest.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class RestGenerator extends BaseGenerator {

	public RestGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		generateWSAdapter();
	}
	
	private void generateWSAdapter(){
		try{
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			for(ClassMetadata cm : Harmony.metas.entities.values()){
				if(cm.options.get("rest")!=null){
					makeSource(cfg, "TemplateWebServiceClientAdapterBase.java", cm.name+"WebServiceClientAdapterBase.java", cm.toMap(this.adapter), true);
					makeSource(cfg, "TemplateWebServiceClientAdapter.java", cm.name+"WebServiceClientAdapter.java", cm.toMap(this.adapter), true);
				}
			}
		}catch(IOException e){
			ConsoleUtils.displayError(e.getMessage());
			e.printStackTrace();
		} catch (TemplateException e) {
			ConsoleUtils.displayError(e.getMessage());
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
	private void makeSource(Configuration cfg, String templatePath, String filePath, Map<String, Object> datamodel, boolean override) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(this.adapter.getSourcePath()+this.metas.projectNameSpace+"/"+this.adapter.getData()+"/"+filePath);
		
		if (override || !file.exists()) {
			
			// Debug Log
			ConsoleUtils.displayDebug("Generate Source : " + file.getPath()); 
			
			// Create
			Template tpl = cfg.getTemplate(
					this.adapter.getTemplateSourceProviderPath().substring(1) + templatePath);
			
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(datamodel, output);
			output.flush();
			output.close();
		}
	}
}
