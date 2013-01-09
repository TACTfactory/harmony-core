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
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.TranslationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ActivityGenerator extends BaseGenerator {
	//protected List<Map<String, Object>> modelEntities;
	protected String localNameSpace;
	protected boolean isWritable = true;

	public ActivityGenerator(BaseAdapter adapter) throws Exception {
		this(adapter, true);
		
	}

	public ActivityGenerator(BaseAdapter adapter, Boolean isWritable) throws Exception {
		super(adapter);

		this.isWritable = isWritable;
		this.datamodel = Harmony.metas.toMap(this.adapter);
	}
	
	public void generateAll() {
		ConsoleUtils.display(">> Generate CRUD view...");

		for(ClassMetadata cm : Harmony.metas.entities.values()){
			if(!cm.internal && !cm.fields.isEmpty()){
				cm.makeString("label");
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.localNameSpace = this.adapter.getNameSpace(cm, this.adapter.getController())+"."+cm.getName().toLowerCase();
				generateAllAction(cm.getName());
			}
		}
	}
	
	/** All Actions (List, Show, Edit, Create) */
	public void generateAllAction(String entityName) {
		ConsoleUtils.display(">>> Generate CRUD view for " +  entityName);
		
		try {
			if (this.isWritable ) {
				ConsoleUtils.display("   with write actions");
	
				this.generateCreateAction(entityName);
				this.generateEditAction(entityName);
				
				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase() + "_progress_save_title", 
						entityName +" save progress");
				TranslationMetadata.addDefaultTranslation(
						entityName.toLowerCase() + "_progress_save_message", 
						entityName +" is saving to database&#8230;");
			}
	
			this.generateShowAction(entityName);
			this.generateListAction(entityName);
		
			new TranslationGenerator(this.adapter).generateStringsXml();
		} catch (Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}
	}
	
	/** List Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateListAction(String entityName) {
		ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sListActivity.java");
		javas.add("%sListFragment.java");
		javas.add("%sListAdapter.java");
		javas.add("%sListLoader.java");
		
		ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_list.xml");
		xmls.add("fragment_%s_list.xml");		
		xmls.add("row_%s.xml");
		
		for(String java : javas){
			this.makeSourceControler( 
					String.format(java, "Template"),
					String.format(java, entityName));
		}
		
		for(String xml : xmls){
			this.makeResourceLayout( 
					String.format(xml, "template"),
					String.format(xml, entityName.toLowerCase()));
		}
		
		this.updateManifest("ListActivity", entityName);
		
		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase() + "_empty_list", 
				entityName +" list is empty !");
	}

	/** Show Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateShowAction(String entityName) {

		ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sShowActivity.java");
		javas.add("%sShowFragment.java");
		
		ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_show.xml");
		xmls.add("fragment_%s_show.xml");
		

		for(String java : javas){
			this.makeSourceControler(
					String.format(java, "Template"),
					String.format(java, entityName));
		}
		
		for(String xml : xmls){
			this.makeResourceLayout( 
					String.format(xml, "template"),
					String.format(xml, entityName.toLowerCase()));
		}

		this.updateManifest("ShowActivity", entityName);
	}

	/** Edit Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateEditAction(String entityName) {
		
		ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sEditActivity.java");
		javas.add("%sEditFragment.java");
		
		ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_edit.xml");
		xmls.add("fragment_%s_edit.xml");
		

		for(String java : javas){
			this.makeSourceControler( 
					String.format(java, "Template"),
					String.format(java, entityName));
		}
		
		for(String xml : xmls){
			this.makeResourceLayout( 
					String.format(xml, "template"),
					String.format(xml, entityName.toLowerCase()));
		}

		this.updateManifest("EditActivity", entityName);
		
		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase() + "_error_edit", 
				entityName +" edition error&#8230;");
	}

	/** Create Action
	 * @param cfg
	 * @throws IOException
	 * @throws TemplateException
	 */
	protected void generateCreateAction(String entityName) {
		
		ArrayList<String> javas = new ArrayList<String>();
		javas.add("%sCreateActivity.java");
		javas.add("%sCreateFragment.java");
		
		ArrayList<String> xmls = new ArrayList<String>();
		xmls.add("activity_%s_create.xml");
		xmls.add("fragment_%s_create.xml");
		

		for(String java : javas){
			this.makeSourceControler(
					String.format(java, "Template"),
					String.format(java, entityName));
		}
		
		for(String xml : xmls){
			this.makeResourceLayout(
					String.format(xml, "template"),
					String.format(xml, entityName.toLowerCase()));
		}


		this.updateManifest("CreateActivity", entityName);
		
		TranslationMetadata.addDefaultTranslation(
				entityName.toLowerCase() + "_error_create", 
				entityName +" creation error&#8230;");
	}

	/** Make Java Source Code
	 * 
	 * @param template Template path file. <br/>For list activity is "TemplateListActivity.java"
	 * @param filename
	 */
	private void makeSourceControler(String template, String filename) {
		String fullFilePath = String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						filename);
		String fullTemplatePath = String.format("%s%s",
				this.adapter.getTemplateSourceControlerPath(),
				template);
		
		super.makeSource(fullTemplatePath, fullFilePath, false);
	}

	/** 
	 * Make Resource file
	 * 
	 * @param template Template path file.
	 * @param filename Resource file. <br/>prefix is type of view "row_" or "activity_" or "fragment_" with <br/>postfix is type of action and extension file : "_list.xml" or "_edit.xml".
	 */
	private void makeResourceLayout(String template, String filename) {
		String fullFilePath = String.format("%s/%s", 
									this.adapter.getRessourceLayoutPath(),
									filename);
		String fullTemplatePath = String.format("%s/%s",
				this.adapter.getTemplateRessourceLayoutPath().substring(1),
				template);
		
		super.makeSource(fullTemplatePath, fullFilePath, false);
	}

	/** Make Manifest file
	 * 
	 * @param cfg Template engine
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public void makeManifest(Configuration cfg) 
			throws IOException, TemplateException {
		File file = FileUtils.makeFile(this.adapter.getManifestPathFile());

		// Debug Log
		ConsoleUtils.displayDebug("Generate Manifest : " + file.getAbsoluteFile());

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
	private void updateManifest(String classFile, String entityName) {
		classFile = entityName + classFile;
		String pathRelatif = String.format(".%s.%s.%s",
				this.adapter.getController(), 
				entityName.toLowerCase(), 
				classFile );

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);

		try {
			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			Element rootNode = doc.getRootElement(); 			// Load Root element
			Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			// Find Application Node
			Element findActivity = null;
			Element applicationNode = rootNode.getChild("application"); 	// Find a element
			if (applicationNode != null) {

				// Find Activity Node
				List<Element> activities = applicationNode.getChildren("activity"); 	// Find many elements
				for (Element activity : activities) {
					if (activity.hasAttributes() && activity.getAttributeValue("name",ns).equals(pathRelatif) ) {	// Load attribute value
						findActivity = activity;
						break;
					}
				}

				// If not found Node, create it
				if (findActivity == null) {
					findActivity = new Element("activity");				// Create new element
					findActivity.setAttribute("name", pathRelatif, ns);	// Add Attributes to element
					Element findFilter = new Element("intent-filter");
					Element findAction = new Element("action");
					Element findCategory = new Element("category");
					Element findData = new Element("data");

					findFilter.addContent(findAction);					// Add Child element
					findFilter.addContent(findCategory);
					findFilter.addContent(findData);
					findActivity.addContent(findFilter);
					applicationNode.addContent(findActivity);
				}

				// Set values
				findActivity.setAttribute("label", "@string/app_name", ns);
				Element filterActivity = findActivity.getChild("intent-filter");
				if (filterActivity != null) {
					String data = "";
					String action = "VIEW";

					if (pathRelatif.matches(".*List.*")) {
						data = "vnd.android.cursor.collection/";
					} else {
						data = "vnd.android.cursor.item/";

						if (pathRelatif.matches(".*Edit.*"))
							action = "EDIT";
						else 		

							if (pathRelatif.matches(".*Create.*"))
								action = "INSERT";
					}

					
					data += this.metas.projectNameSpace.replace('/', '.') + entityName;
					filterActivity.getChild("action").setAttribute("name", "android.intent.action."+ action, ns);
					filterActivity.getChild("category").setAttribute("name", "android.intent.category.DEFAULT", ns);
					filterActivity.getChild("data").setAttribute("mimeType", data, ns);
				}
				
				// Clean code
				applicationNode.sortChildren(new Comparator<Element>() {

					@Override
					public int compare(Element o1, Element o2) {
						return (o1.getName().compareToIgnoreCase(o2.getName()));
					}
				});
			}

			// Write to File
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());				// Make beautiful file with indent !!!
			xmlOutput.output(doc, new FileWriter(xmlFile.getAbsoluteFile()));
		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}
