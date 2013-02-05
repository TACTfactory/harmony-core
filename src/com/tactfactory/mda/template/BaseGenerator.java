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
import java.util.HashMap;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class BaseGenerator {
	protected ApplicationMetadata appMetas;	// Meta-models
	protected BaseAdapter adapter;			// Platform adapter
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();
	
	protected Configuration cfg = new Configuration();	// Config

	public BaseGenerator(BaseAdapter adapter) throws Exception {
		if (adapter == null)
			throw new Exception("No adapter define.");
		
		this.appMetas		= Harmony.metas;	// FIXME Clone object tree
		this.adapter	= adapter;
		
		this.cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param templatePath Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param generatePath
	 * @param override
	 */
	protected void makeSource(String templatePath, String generatePath, boolean override) {
		if (!FileUtils.exists(generatePath) || override){
			File generateFile = FileUtils.makeFile(generatePath);
			
			try {
				// Debug Log
				ConsoleUtils.displayDebug("Generate Source : " + generateFile.getPath()); 
				
				// Create
				Template tpl = this.cfg.getTemplate(templatePath);
				
				// Write and close
				OutputStreamWriter output = new FileWriter(generateFile);
				tpl.process(this.datamodel, output);
				output.flush();
				output.close();
				
			} catch (IOException e) {
				ConsoleUtils.displayError(e.getMessage());
				e.printStackTrace();
			} catch (TemplateException e) {
				ConsoleUtils.displayError(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	/** 
	 * Append Source Code to existing file
	 * 
	 * @param templatePath Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param generatePath
	 */
	protected void appendSource(String templatePath, String generatePath) {
		if (FileUtils.exists(generatePath)){
			File generateFile = new File(generatePath);
			
			try {
				// Debug Log
				ConsoleUtils.displayDebug("Append Source : " + generateFile.getPath()); 
				
				// Create
				Template tpl = this.cfg.getTemplate(templatePath);
				
				// Write and close
				OutputStreamWriter output = new FileWriter(generateFile, true);
				tpl.process(this.datamodel, output);
				output.flush();
				output.close();
				
			} catch (IOException e) {
				ConsoleUtils.displayError(e.getMessage());
				e.printStackTrace();
			} catch (TemplateException e) {
				ConsoleUtils.displayError(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Update Libs
	 */
	protected void updateLibrary(String libName) {
		File dest = new File(String.format("%s/%s", this.adapter.getLibsPath(), libName));
		
		if (!dest.exists())
			FileUtils.copyfile(
					new File(String.format("%s/%s", Harmony.pathLibs, libName)),
					dest);
	}
}
