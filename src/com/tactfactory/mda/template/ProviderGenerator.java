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
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ProviderGenerator extends BaseGenerator {
	protected String localNameSpace;
	protected String nameProvider;
	
	public ProviderGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.nameProvider = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.metas.projectName + "Provider");
		this.localNameSpace = this.metas.projectNameSpace.replace('/', '.') +"."+ this.adapter.getProvider() + "." + this.nameProvider;
	}
	
	public void generateProvider() {
		try {
			Configuration cfg = new Configuration();	// Initialization of template engine
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			
			
			this.makeSourceProvider(cfg, "TemplateProvider.java", this.nameProvider + ".java");
			
			//this.updateManifest(classFile, entityName);
			
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
	private void makeSourceProvider(Configuration cfg, String template, String filename) 
			throws IOException, TemplateException {
		
		String filepath = String.format("%s%s/%s",
						this.adapter.getSourcePath(),
						PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
						filename);
		
		if(!FileUtils.exists(filepath)){
			File file = FileUtils.makeFile(filepath);
			
			// Debug Log
			ConsoleUtils.displayDebug("Generate Source : " + file.getPath()); 
	
			// Create
			Template tpl = cfg.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceProviderPath(),
							template));		// Load template file in engine
	
			OutputStreamWriter output = new FileWriter(file);
			tpl.process(this.datamodel, output);		// Process datamodel (with previous template file), and output to output file
			output.flush();
			output.close();
		}
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

					
					data += this.metas.projectNameSpace + adapter.getModel() + entityName;
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