package com.tactfactory.mda.android.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.tactfactory.mda.android.command.Console;
import com.tactfactory.mda.android.command.FileUtils;
import com.tactfactory.mda.android.command.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ActivityGenerator {
	private final static String TEMPLATE_PATH_FOLDER = "template/";
	
	protected HashMap<String, Object> datamodel;
	protected String localNameSpace;
	protected String nameEntity;

	private boolean isWritable = true;
	
	public ActivityGenerator(HashMap<String, Object> datamodel) {
		this.datamodel = (HashMap<String, Object>) datamodel.clone();
		
		this.nameEntity = (String) datamodel.get("name");
		this.localNameSpace = datamodel.get("namespace") + ".view." + this.nameEntity.toLowerCase();
		this.datamodel.put("localnamespace", this.localNameSpace);
	}
	
	public void generate() {
		try {
			Configuration cfg = new Configuration();
			
			if (this.isWritable ) {
				this.generateCreateAction(cfg);
				this.generateEditAction(cfg);
			}

			this.generateShowAction(cfg);
			this.generateListAction(cfg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** List Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateListAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"TemplateListActivity.java", 
				"ListActivity.java");
		this.generateResource(cfg, 
				"activity_template_list.xml", 
				"activity_", 
				"_list.xml");
		
		this.generateSource(cfg, 
				"TemplateListFragment.java", 
				"ListFragment.java");
		this.generateResource(cfg, 
				"fragment_template_list.xml", 
				"fragment_", 
				"_list.xml");
		
		this.generateSource(cfg, 
				"TemplateListAdapter.java", 
				"ListAdapter.java");
		this.generateResource(cfg, 
				"row_template.xml", 
				"row_", 
				".xml");
		
		this.generateSource(cfg, 
				"TemplateListLoader.java", 
				"ListLoader.java");
	}

	/** Show Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateShowAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"TemplateShowActivity.java", 
				"ShowActivity.java");
		this.generateResource(cfg, 
				"activity_template_show.xml", 
				"activity_", 
				"_show.xml");
		
		this.generateSource(cfg, 
				"TemplateShowFragment.java", 
				"ShowFragment.java");
		this.generateResource(cfg, 
				"fragment_template_show.xml", 
				"fragment_", 
				"_show.xml");
	}

	/** Edit Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateEditAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"TemplateEditActivity.java", 
				"EditActivity.java");
		this.generateResource(cfg, 
				"activity_template_edit.xml", 
				"activity_", 
				"_edit.xml");
		
		this.generateSource(cfg, 
				"TemplateEditFragment.java", 
				"EditFragment.java");
		this.generateResource(cfg, 
				"fragment_template_edit.xml", 
				"fragment_", 
				"_edit.xml");
	}

	/** Create Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateCreateAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"TemplateCreateActivity.java", 
				"CreateActivity.java"  );
		this.generateResource(cfg, 
				"activity_template_create.xml", 
				"activity_", 
				"_create.xml");
		
		this.generateSource(cfg, 
				"TemplateCreateFragment.java", 
				"CreateFragment.java");
		this.generateResource(cfg, 
				"fragment_template_create.xml", 
				"fragment_", 
				"_create.xml");
	}

	/** Make Java Source Code
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void generateSource(Configuration cfg, String template, String filepostname) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(Console.pathProject + "/src/" + PackageUtils.extractPath(this.localNameSpace).toLowerCase() + "/" + this.nameEntity + filepostname);
		System.out.print("\n\t Generate : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(TEMPLATE_PATH_FOLDER + "view/" + template);
		
		OutputStreamWriter output;
		if (false) //Console.DEBUG)
			output = new OutputStreamWriter(System.out);
		else 
			output = new FileWriter(file);
		
		tpl.process(datamodel, output);
		output.flush();
	}
	
	/** Make Resource file
	 * 
	 * @param cfg Template engin
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param fileprename Prefix of resource file. <br/>Common is type of view "row_" or "activity_" or "fragment_"
	 * @param filepostname Postfix of resource file. <br/>Common is type of action and extention file : "_list.xml" or "_edit.xml".
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void generateResource(Configuration cfg, String template, String fileprename, String filepostname) throws IOException,
		TemplateException {
	
		File file = FileUtils.makeFile(Console.pathProject + "/res/layout/" + fileprename + this.nameEntity.toLowerCase() + filepostname);
		System.out.print("\n\t Generate : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(TEMPLATE_PATH_FOLDER+ "res/layout/"  + template);
		
		OutputStreamWriter output;
		if (false) //Console.DEBUG)
			output = new OutputStreamWriter(System.out);
		else 
			output = new FileWriter(file);
		
		tpl.process(datamodel, output);
		output.flush();
	}
}