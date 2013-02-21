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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class BaseGenerator {
	protected ApplicationMetadata appMetas;	// Meta-models
	protected BaseAdapter adapter;			// Platform adapter
	protected Map<String, Object> datamodel;
	
	protected Configuration cfg = new Configuration();	// Config

	public BaseGenerator(final BaseAdapter adapter) throws Exception {
		if (adapter == null) {
			throw new Exception("No adapter define.");
		}
		
		this.appMetas		= ApplicationMetadata.INSTANCE;	// FIXME Clone object tree
		this.adapter	= adapter;
		
		this.cfg.setDirectoryForTemplateLoading(new File(Harmony.PATH_BASE));
	}
	
	/** 
	 * Make Java Source Code
	 * 
	 * @param templatePath Template path file. 
	 * 		For list activity is "TemplateListActivity.java"
	 * @param generatePath
	 * @param override
	 */
	protected void makeSource(final String templatePath,
			final String generatePath,
			final boolean override) {
		if (!FileUtils.exists(generatePath) || override) {
			final File generateFile = FileUtils.makeFile(generatePath);
			
			try {
				// Debug Log
				ConsoleUtils.displayDebug("Generate Source : " + generateFile.getPath()); 
				
				// Create
				final Template tpl = this.cfg.getTemplate(templatePath);
				
				// Write and close
				final OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(generateFile), "UTF-8");
				tpl.process(this.datamodel, output);
				output.flush();
				output.close();
				
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
				ConsoleUtils.displayError(e);
			} catch (final TemplateException e) {
				ConsoleUtils.displayError(e);
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/** 
	 * Append Source Code to existing file
	 * 
	 * @param templatePath Template path file. 
	 * 			For list activity is "TemplateListActivity.java"
	 * @param generatePath
	 */
	protected void appendSource(final String templatePath, final String generatePath) {
		if (FileUtils.exists(generatePath)) {
			final File generateFile = new File(generatePath);
			
			try {
				// Debug Log
				ConsoleUtils.displayDebug("Append Source : " + generateFile.getPath()); 
				
				// Create
				final Template tpl = this.cfg.getTemplate(templatePath);
				
				// Write and close
				final OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(generateFile, true), "UTF-8");
				tpl.process(this.datamodel, output);
				output.flush();
				output.close();
				
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
				ConsoleUtils.displayError(e);
			} catch (final TemplateException e) {
				ConsoleUtils.displayError(e);
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	
	/**
	 * Update Libs
	 */
	protected void updateLibrary(final String libName) {
		final File dest = new File(String.format("%s/%s", this.adapter.getLibsPath(), libName));
		
		if (!dest.exists()) {
			FileUtils.copyfile(
					new File(String.format("%s/%s", Harmony.PATH_LIBS, libName)),
					dest);
		}
	}
	
	/**
	 * Generate Utils
	 */
	protected void updateUtil(final String utilName) {		
		this.makeSource(String.format("%s%s", this.adapter.getTemplateUtilPath(), utilName), 
				String.format("%s%s", this.adapter.getUtilPath(), utilName), false);
	}
}
