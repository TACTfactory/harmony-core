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

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class SQLiteGenerator extends BaseGenerator {
	protected String localNameSpace;

	public SQLiteGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		String globalNameSpace = "";
		this.datamodel = (HashMap<String, Object>) this.metas.toMap(this.adapter);
		this.localNameSpace = this.metas.projectNameSpace+"/"+this.adapter.getData();
	}

	/**
	 * Generate Database Interface Source Code
	 */
	public void generateDatabase() {
		// Info
		ConsoleUtils.display(">> Generate Database");
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			this.makeSourceData(cfg, 
					"TemplateSQLiteOpenHelper.java", 
					"%sSQLiteOpenHelper.java");
			
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
	 * @param template Template path file.
	 * @param filename
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeSourceData(Configuration cfg, String template, String filename) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(
				String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						String.format(filename, CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.metas.projectName))));
		
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
