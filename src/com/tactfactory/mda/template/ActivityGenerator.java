/**
 * This file is part of the Symfodroid package.
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

import com.tactfactory.mda.command.Console;
import com.tactfactory.mda.command.FileUtils;
import com.tactfactory.mda.command.PackageUtils;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ActivityGenerator {
	private final static String TEMPLATE_VIEW = "view";
	private final static String TEMPLATE_PATH_FOLDER = "template/" + "android/";
	
	protected ClassMetadata meta;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();
	protected String localNameSpace;

	private boolean isWritable = true;
	
	public ActivityGenerator(ClassMetadata meta) {
		this.meta 		= meta;
		//this.nameEntity = meta.nameClass;
		this.localNameSpace = String.format("%s.%s.%s", meta.nameSpace, TEMPLATE_VIEW, this.meta.nameClass.toLowerCase());
		
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
	
	public void generate() {
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

	/** List Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateListAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"view/TemplateListActivity.java", 
				"ListActivity.java");
		this.generateResource(cfg, 
				"activity_template_list.xml", 
				"activity_", 
				"_list.xml");
		
		this.generateSource(cfg, 
				"view/TemplateListFragment.java", 
				"ListFragment.java");
		this.generateResource(cfg, 
				"fragment_template_list.xml", 
				"fragment_", 
				"_list.xml");
		
		this.generateSource(cfg, 
				"view/TemplateListAdapter.java", 
				"ListAdapter.java");
		this.generateResource(cfg, 
				"row_template.xml", 
				"row_", 
				".xml");
		
		this.generateSource(cfg, 
				"view/TemplateListLoader.java", 
				"ListLoader.java");
		
		this.updateManifest("ListActivity");
	}

	/** Show Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateShowAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"view/TemplateShowActivity.java", 
				"ShowActivity.java");
		this.generateResource(cfg, 
				"activity_template_show.xml", 
				"activity_", 
				"_show.xml");
		
		this.generateSource(cfg, 
				"view/TemplateShowFragment.java", 
				"ShowFragment.java");
		this.generateResource(cfg, 
				"fragment_template_show.xml", 
				"fragment_", 
				"_show.xml");
		
		this.updateManifest("ShowActivity");
	}

	/** Edit Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateEditAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"view/TemplateEditActivity.java", 
				"EditActivity.java");
		this.generateResource(cfg, 
				"activity_template_edit.xml", 
				"activity_", 
				"_edit.xml");
		
		this.generateSource(cfg, 
				"view/TemplateEditFragment.java", 
				"EditFragment.java");
		this.generateResource(cfg, 
				"fragment_template_edit.xml", 
				"fragment_", 
				"_edit.xml");
		
		this.updateManifest("EditActivity");
	}

	/** Create Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateCreateAction(Configuration cfg) throws IOException,
			TemplateException {
		
		this.generateSource(cfg, 
				"view/TemplateCreateActivity.java", 
				"CreateActivity.java"  );
		this.generateResource(cfg, 
				"activity_template_create.xml", 
				"activity_", 
				"_create.xml");
		
		this.generateSource(cfg, 
				"view/TemplateCreateFragment.java", 
				"CreateFragment.java");
		this.generateResource(cfg, 
				"fragment_template_create.xml", 
				"fragment_", 
				"_create.xml");
		
		this.updateManifest("CreateActivity");
	}

	/** Make Java Source Code
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void generateSource(Configuration cfg, String template, String filepostname) throws IOException,
			TemplateException {
		
		File file = FileUtils.makeFile(Console.pathProject + "/src" + PackageUtils.extractPath(this.localNameSpace).toLowerCase() + "/" + this.meta.nameClass + filepostname);
		
		// Debug Log
		if (com.tactfactory.mda.command.Console.DEBUG)
			System.out.print("\tGenerate Source : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(TEMPLATE_PATH_FOLDER + "src/" + template);
		
		OutputStreamWriter output;
		/*if (false) //Console.DEBUG)
			output = new OutputStreamWriter(System.out);
		else */
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
	
		File file = FileUtils.makeFile(Console.pathProject + "/res/layout/" + fileprename + this.meta.nameClass.toLowerCase() + filepostname);
		
		// Debug Log
		if (com.tactfactory.mda.command.Console.DEBUG)
			System.out.print("\tGenerate Ressource : " + file.getAbsoluteFile() + "\n"); 
		
		// Create
		Template tpl = cfg.getTemplate(TEMPLATE_PATH_FOLDER+ "res/layout/"  + template);
		
		OutputStreamWriter output;
		/*if (false) //Console.DEBUG)
			output = new OutputStreamWriter(System.out);
		else*/ 
			output = new FileWriter(file);
		
		tpl.process(datamodel, output);
		output.flush();
	}

	/**  Update Android Manifest
	 * 
	 * @param classFile
	 */
	private void updateManifest(String classFile) {
		classFile = this.meta.nameClass + classFile;
		String pathRelatif = String.format(".%s.%s.%s", TEMPLATE_VIEW, this.meta.nameClass.toLowerCase(), classFile );
		
		// Debug Log
		if (com.tactfactory.mda.command.Console.DEBUG)
			System.out.print("\tUpdate Manifest : " + pathRelatif + "\n\n");
		
		try {			
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = FileUtils.makeFile(Console.pathProject + "/AndroidManifest.xml");
	 
			//TODO if (!xmlFile.exists())
				
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