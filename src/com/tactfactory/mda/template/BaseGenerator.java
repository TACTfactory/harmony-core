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

/**
 * Base Generator.
 */
public abstract class BaseGenerator {
	// Meta-models
	/** The application metadata. */
	protected ApplicationMetadata appMetas;	
	
	// Platform adapter
	/** The used adapter. */
	protected BaseAdapter adapter;
	/** The datamodel. */
	protected Map<String, Object> datamodel;
	
	// Config
	/** The freemarker configuration. */
	protected Configuration cfg = new Configuration();	

	/**
	 * Constructor.
	 * @param adapt The adapter to use
	 * @throws Exception 
	 */
	public BaseGenerator(final BaseAdapter adapt) throws Exception {
		if (adapt == null) {
			throw new Exception("No adapter define.");
		}
		
		// FIXME Clone object tree
		this.appMetas		= ApplicationMetadata.INSTANCE;	
		this.adapter	= adapt;
		
		this.cfg.setDirectoryForTemplateLoading(new File(Harmony.PATH_BASE));
	}
	
	/** 
	 * Make Java Source Code.
	 * 
	 * @param templatePath Template path file. 
	 * 		For list activity is "TemplateListActivity.java"
	 * @param generatePath The destination file path
	 * @param override True for recreating the file. 
	 * 			False for not writing anything if the file already exists. 
	 */
	protected void makeSource(final String templatePath,
			final String generatePath,
			final boolean override) {
		if (!FileUtils.exists(generatePath) || override) {
			final File generateFile = FileUtils.makeFile(generatePath);
			
			try {
				// Debug Log
				ConsoleUtils.displayDebug("Generate Source : " 
						+ generateFile.getPath()); 
				
				// Create
				final Template tpl = this.cfg.getTemplate(templatePath);
				
				// Write and close
				final OutputStreamWriter output =
						new OutputStreamWriter(
								new FileOutputStream(generateFile), 
								FileUtils.DEFAULT_ENCODING);
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
	 * Append Source Code to existing file.
	 * 
	 * @param templatePath Template path file. 
	 * 			For list activity is "TemplateListActivity.java"
	 * @param generatePath Destination file.
	 */
	protected void appendSource(final String templatePath, 
			final String generatePath) {
		if (FileUtils.exists(generatePath)) {
			final File generateFile = new File(generatePath);
			
			try {
				// Debug Log
				ConsoleUtils.displayDebug("Append Source : "
						+ generateFile.getPath()); 
				
				// Create
				final Template tpl = this.cfg.getTemplate(templatePath);
				
				// Write and close
				final OutputStreamWriter output = 
						new OutputStreamWriter(
								new FileOutputStream(generateFile, true),
								FileUtils.DEFAULT_ENCODING);
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
	 * Update Libs.
	 * @param libName The library name
	 */
	protected void updateLibrary(final String libName) {
		final File dest = new File(
				String.format("%s/%s", this.adapter.getLibsPath(), libName));
		
		if (!dest.exists()) {
			FileUtils.copyfile(
					new File(String.format("%s/%s", 
							Harmony.PATH_LIBS, libName)),
					dest);
		}
	}
	
	/**
	 * Generate Utils.
	 * @param utilName The utility class name
	 */
	protected void updateUtil(final String utilName) {		
		this.makeSource(
				String.format("%s%s", 
						this.adapter.getTemplateUtilPath(), 
						utilName), 
				String.format("%s%s", 
						this.adapter.getUtilPath(),
						utilName), 
				false);
	}
}
