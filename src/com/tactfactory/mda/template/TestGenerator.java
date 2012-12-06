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
import java.util.List;
import java.util.Map;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestGenerator {
	private List<ClassMetadata> metas;
	protected BaseAdapter adapter;
	protected String localNameSpace;
	protected List<Map<String, Object>> modelEntities;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();
	
	public TestGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		if (metas == null && adapter == null)
			throw new Exception("No meta or adapter define.");
		
		this.metas 		= metas;
		this.adapter	= adapter;
		this.localNameSpace = String.format("%s/%s", this.adapter.getSource(),
				this.adapter.getNameSpace(this.metas.get(0), this.adapter.getTest()));
		this.datamodel.put("localnamespace", 				this.localNameSpace);
		this.datamodel.put(TagConstant.NAME,	"user" );
	}
	
	/**
	 * Generate DataBase Test
	 */
	public void generateTest() {
		// Info
		System.out.print(">> Generate DB Test\n");
		
		try {
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			this.makeSourceControler(cfg, 
					"TemplateTestDB.java", 
					"%sTestDB.java");
			
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
	private void makeSourceControler(Configuration cfg, String template, String filename) 
			throws IOException, TemplateException {
		String filepath = String.format("%s%s/%s",
						this.adapter.getTestPath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						String.format(filename, this.datamodel.get(TagConstant.NAME)));
		if(!FileUtils.exists(filepath)){
			File file = FileUtils.makeFile(filepath);
			
			// Debug Log
			if (Harmony.DEBUG)
				System.out.print("\tGenerate DB Test : " + file.getPath() + "\n"); 
	
			// Create
			Template tpl = cfg.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateTestsPath(),
							template));		// Load template file in engine
	
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(datamodel, output);		// Process datamodel (with previous template file), and output to output file
			output.flush();
			output.close();
		}
	}
}