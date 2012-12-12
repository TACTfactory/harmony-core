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
import java.util.Collection;
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

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.orm.SqliteAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ActivityGenerator {	
	protected List<ClassMetadata> metas;
	protected ClassMetadata meta;
	protected List<Map<String, Object>> modelEntities;
	protected BaseAdapter adapter;
	protected String localNameSpace;
	protected boolean isWritable = true;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	public ActivityGenerator(List<ClassMetadata> metas, BaseAdapter adapter) throws Exception {
		if (metas == null && adapter == null)
			throw new Exception("No meta or adapter define.");

		this.metas 		= metas;
		this.adapter	= adapter;
		
		// Make entities
		this.modelEntities = new ArrayList<Map<String, Object>>();
		for (ClassMetadata meta : this.metas) {
			
			this.meta = meta;
			
			Map<String, Object> modelClass = meta.toMap(this.adapter);/*new HashMap<String, Object>();
			modelClass.put(TagConstant.SPACE,	meta.space );
			modelClass.put(TagConstant.NAME,	meta.name );
			modelClass.put(TagConstant.LOCAL_NAMESPACE,	this.adapter.getNameSpaceEntity(this.meta, this.adapter.getController()) );
			

			
			ArrayList<Map<String, Object>> modelFields = this.loadSubFields(this.meta.fields.values());
			ArrayList<Map<String, Object>> modelRelations = this.loadSubFields(this.meta.relations.values());
			
			modelClass.put(TagConstant.FIELDS, modelFields);
			modelClass.put(TagConstant.RELATIONS, modelRelations);*/
			
			this.modelEntities.add(modelClass);
		}
	}
	
	/**
	 * @return
	 */
	/*private ArrayList<Map<String, Object>> loadSubFields(Collection<FieldMetadata> values) {
		ArrayList<Map<String, Object>> SubFields = new ArrayList<Map<String,Object>>();
		for (FieldMetadata field : values) {
			Map<String, Object> subField = new HashMap<String, Object>();
			
			// General
			subField.put(TagConstant.NAME, field.name);
			subField.put(TagConstant.TYPE, field.type);
			subField.put(TagConstant.ALIAS, SqliteAdapter.generateColumnName(field));

			subField.put("customEditType", field.customEditType);
			subField.put("customShowType", field.customShowType);
			
			// if ids
			
			// if models
			field.customize(adapter);
			subField.put(TagConstant.SCHEMA, SqliteAdapter.generateStructure(field));
			
			// if relations
			if(field.relation!=null)
				subField.put(TagConstant.RELATION, loadRelationSubField(field));
			field.customize(adapter);
			subField = field.toMap();
			
			SubFields.add(subField);
		}
		return SubFields;
	}*/
	
	/*private Map<String, Object> loadRelationSubField(FieldMetadata fm){
		Map<String, Object> relationSF = new HashMap<String, Object>();
		
		relationSF.put(TagConstant.TYPE, fm.relation.type);
		relationSF.put("field_ref", fm.relation.field_ref);
		relationSF.put("targetEntity", fm.relation.entity_ref);
		relationSF.put(TagConstant.NAME, fm.relation.name);
		
		return relationSF;
	}*/

	public ActivityGenerator(List<ClassMetadata> metas, BaseAdapter adapter, Boolean isWritable) throws Exception {
		this(metas, adapter);

		this.isWritable = isWritable;
	}
	
	public void generateAll() {
		ConsoleUtils.display(">> Generate CRUD view...");

		int i = 0;
		for(Map<String, Object> entity : this.modelEntities) {
			this.meta = this.metas.get(i);
			this.localNameSpace = this.adapter.getNameSpaceEntity(this.meta, this.adapter.getController());;

			// Make class
			this.datamodel.put("namespace", 					entity.get(TagConstant.SPACE));
			this.datamodel.put(TagConstant.NAME, 				entity.get(TagConstant.NAME));
			this.datamodel.put("localnamespace", 				this.localNameSpace);
			this.datamodel.put(TagConstant.FIELDS, 				entity.get(TagConstant.FIELDS));
			this.datamodel.put(TagConstant.RELATIONS, 			entity.get(TagConstant.RELATIONS));
			
			this.generateAllAction();
			
			i++;
		}
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
		ConsoleUtils.display(">>> Generate CRUD view for " +  meta.name);

		try {

			// TODO Caution, freemarker template folder must have been specified
			// freemarker bug with '../' folder so, just remove first '.'
			Configuration cfg = new Configuration();						// Initialization of template engine
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));

			if (this.isWritable ) {
				// Info
				ConsoleUtils.display("   with write actions");

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
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						String.format(filename, this.meta.name));
		if(!FileUtils.exists(filepath)){
			File file = FileUtils.makeFile(filepath);
			
			// Debug Log
			ConsoleUtils.displayDebug("Generate Source : " + file.getPath()); 
	
			// Create
			Template tpl = cfg.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceControlerPath(),
							template));		// Load template file in engine
	
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(datamodel, output);		// Process datamodel (with previous template file), and output to output file
			output.flush();
			output.close();
		}
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
		String filepath = String.format("%s/%s", 
									this.adapter.getRessourceLayoutPath(),
									String.format(filename, this.meta.name.toLowerCase()));
		if(!FileUtils.exists(filepath)){
			File file = FileUtils.makeFile(filepath);
	
			// Debug Log
			ConsoleUtils.displayDebug("Generate Ressource : " + file.getAbsoluteFile()); 
	
			// Create
			Template tpl = cfg.getTemplate(
					String.format("%s/%s",
							this.adapter.getTemplateRessourceLayoutPath().substring(1),
							template));
	
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(this.datamodel, output);
			output.flush();
			output.close();
		}
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
	private void updateManifest(String classFile) {
		classFile = this.meta.name + classFile;
		String pathRelatif = String.format(".%s.%s.%s",
				this.adapter.getController(), 
				this.meta.name.toLowerCase(), 
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

					data += this.adapter.getNameSpace(this.meta, "entity." + this.meta.name);

					filterActivity.getChild("action").setAttribute("name", "android.intent.action."+ action, ns);
					filterActivity.getChild("category").setAttribute("name", "android.intent.category.DEFAULT", ns);
					filterActivity.getChild("data").setAttribute("mimeType", data, ns);
				}	
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
