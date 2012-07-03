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
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ActivityGenerator {	
	protected ClassMetadata meta;
	protected BaseAdapter adapter;
	protected String localNameSpace;
	protected boolean isWritable = true;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();
	
	public ActivityGenerator(ClassMetadata meta, BaseAdapter adapter) throws Exception {
		if (meta != null && adapter != null)
			throw new Exception("No meta or adapter define.");
		
		
		this.meta 		= meta;
		this.adapter	= adapter;
		
		this.localNameSpace = this.adapter.getNameSpace(this.meta);
		
		// Make fields
		ArrayList<Map<String, Object>> modelFields = new ArrayList<Map<String,Object>>();
		for (FieldMetadata field : this.meta.fields.values()) {
			Map<String, Object> modelField = new HashMap<String, Object>();
			field.customize();
			modelField.put("name", field.name);
			modelField.put("type", field.type);
			modelField.put("customEditType", field.customEditType);
			modelField.put("customShowType", field.customShowType);
			
			modelFields.add(modelField);
		}
		
		// Make class
		this.datamodel.put("namespace", meta.nameSpace);
		this.datamodel.put("name", 		meta.nameClass);
		this.datamodel.put("localnamespace", this.localNameSpace);
		this.datamodel.put("fields", 	modelFields);
	}
	
	public ActivityGenerator(ClassMetadata meta, BaseAdapter adapter, Boolean isWritable) throws Exception {
		this(meta, adapter);
		
		this.isWritable = isWritable;
	}
	
	/** List Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void generateListAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.makeSourceControler(cfg, 
				"TemplateListActivity.java", 
				"%sListActivity.java");
		this.makeResourceLayout(cfg, 
				"activity_template_list.xml", 
				"activity_%s_list.xml");
		
		this.makeSourceControler(cfg, 
				"TemplateListFragment.java", 
				"%sListFragment.java");
		this.makeResourceLayout(cfg, 
				"fragment_template_list.xml", 
				"fragment_%s_list.xml");
		
		this.makeSourceControler(cfg, 
				"TemplateListAdapter.java", 
				"%sListAdapter.java");
		this.makeResourceLayout(cfg, 
				"row_template.xml", 
				"row_%s.xml");
		
		this.makeSourceControler(cfg, 
				"TemplateListLoader.java", 
				"%sListLoader.java");
		
		this.updateManifest("ListActivity");
	}

	/** Show Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void generateShowAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.makeSourceControler(cfg, 
				"TemplateShowActivity.java", 
				"%sShowActivity.java");
		this.makeResourceLayout(cfg, 
				"activity_template_show.xml", 
				"activity_%s_show.xml");
		
		this.makeSourceControler(cfg, 
				"TemplateShowFragment.java", 
				"%sShowFragment.java");
		this.makeResourceLayout(cfg, 
				"fragment_template_show.xml", 
				"fragment_%s_show.xml");
		
		this.updateManifest("ShowActivity");
	}

	/** Edit Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void generateEditAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.makeSourceControler(cfg, 
				"TemplateEditActivity.java", 
				"%sEditActivity.java");
		this.makeResourceLayout(cfg, 
				"activity_template_edit.xml", 
				"activity_%s_edit.xml");
		
		this.makeSourceControler(cfg, 
				"TemplateEditFragment.java", 
				"%sEditFragment.java");
		this.makeResourceLayout(cfg, 
				"fragment_template_edit.xml", 
				"fragment_%s_edit.xml");
		
		this.updateManifest("EditActivity");
	}

	/** Create Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	public void generateCreateAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.makeSourceControler(cfg, 
				"TemplateCreateActivity.java", 
				"%sCreateActivity.java"  );
		this.makeResourceLayout(cfg, 
				"activity_template_create.xml", 
				"activity_%s_create.xml");
		
		this.makeSourceControler(cfg, 
				"TemplateCreateFragment.java", 
				"%sCreateFragment.java");
		this.makeResourceLayout(cfg, 
				"fragment_template_create.xml", 
				"fragment_%s_create.xml");
		
		this.updateManifest("CreateActivity");
	}
	
	/** All Actions (List, Show, Edit, Create) */
	public void generateAllAction() {
		// Info
		System.out.print(">> Generate CRUD view for " +  meta.nameClass);
		
		try {
			Configuration cfg = new Configuration();
			
			if (this.isWritable ) {
				// Info
				System.out.print(" with write actions \n");
				
				this.generateCreateAction(cfg);
				this.generateEditAction(cfg);
			} else {
				// Info
				System.out.print("\n");
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

	/** Make Java Source Code
	 * @param cfg Template engine
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeSourceControler(Configuration cfg, String template, String filename) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(
				String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						String.format(filename, this.meta.nameClass)));
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Source : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(
				this.adapter.getTemplateSourceControlerPath() + template);
		
		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
	}
	
	/** Make Resource file
	 * 
	 * @param cfg Template engine
	 * @param template Template path file.
	 * @param filename Resource file. <br/>prefix is type of view "row_" or "activity_" or "fragment_" with <br/>postfix is type of action and extension file : "_list.xml" or "_edit.xml".
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void makeResourceLayout(Configuration cfg, String template, String filename) throws IOException,
		TemplateException {
	
		File file = FileUtils.makeFile(
				String.format("%s/%s", 
						this.adapter.getRessourceLayoutPath(),
						String.format(filename, this.meta.nameClass.toLowerCase())));
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Ressource : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(
				String.format("%s/%s",
						this.adapter.getTemplateRessourceLayoutPath(),
						template));
		
		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
	}
	
	/** Make Manifest file
	 * 
	 * @param cfg Template engine
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	private void makeManifest(Configuration cfg) throws IOException, TemplateException {
		File file = FileUtils.makeFile(this.adapter.getManifestPathFile());
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tGenerate Manifest : " + file.getAbsoluteFile() + "\n");
		
		// Create
		Template tpl = cfg.getTemplate(this.adapter.getTemplateManifestPathFile());
		OutputStreamWriter output = new FileWriter(file);
		tpl.process(datamodel, output);
		output.flush();
		output.close();
	}

	/**  Update Android Manifest
	 * 
	 * @param classFile
	 */
	private void updateManifest(String classFile) {
		classFile = this.meta.nameClass + classFile;
		String pathRelatif = String.format(".%s.%s.%s",
				this.adapter.getController(), 
				this.meta.nameClass.toLowerCase(), 
				classFile );
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\tUpdate Manifest : " + pathRelatif + "\n\n");
		
		try {			
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			Document doc = (Document) builder.build(xmlFile);
			Element rootNode = doc.getRootElement();
			Namespace ns = rootNode.getNamespace("android");
			
			// get Application Node
			Element findActivity = null;
			Element applicationNode = rootNode.getChild("application");
			if (applicationNode != null) {
				List<Element> activities = applicationNode.getChildren("activity");
				for (Element activity : activities) {
					if (activity.hasAttributes() && activity.getAttributeValue("name",ns).equals(pathRelatif) ) {
						findActivity = activity;
						break;
					}
				}
			}
			
			if (findActivity == null) {
				findActivity = new Element("activity");
				findActivity.setAttribute("name", pathRelatif, ns);
				applicationNode.addContent(findActivity);
			}
			
			findActivity.setAttribute("label", "@string/app_name", ns);
	 
			// Write to File
			XMLOutputter xmlOutput = new XMLOutputter();
	 
			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, new FileWriter(xmlFile.getAbsoluteFile()));
		  } catch (IOException io) {
			io.printStackTrace();
		  } catch (JDOMException e) {
			e.printStackTrace();
		  }
		}
}