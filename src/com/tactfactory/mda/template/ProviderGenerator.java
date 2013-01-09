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
import java.util.Comparator;
import java.util.HashMap;
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
import com.tactfactory.mda.orm.TranslationMetadata;
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
		this.localNameSpace = this.metas.projectNameSpace.replace('/', '.') +"."+ this.adapter.getProvider();
		
		this.datamodel = (HashMap<String, Object>) this.metas.toMap(this.adapter);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE, this.localNameSpace);
	}
	
	public void generateProvider() {
		try {
			Configuration cfg = new Configuration();	// Initialization of template engine
			cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
			
			this.makeSourceProvider(cfg, "TemplateProvider.java", this.nameProvider + ".java");
			
			this.updateManifest(this.nameProvider);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TranslationMetadata.addDefaultTranslation("uri_not_supported", "URI not supported");
		TranslationMetadata.addDefaultTranslation("app_provider_name", "Provider of " + this.metas.projectName);
		TranslationMetadata.addDefaultTranslation("app_provider_description", "Provider of " + this.metas.projectName + " for acces to data");
		
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
	 * @param nameProvider 
	 * 
	 * @param classFile
	 */
	private void updateManifest(String nameProvider) {
		String pathRelatif = String.format("%s.%s",
				this.localNameSpace, 
				nameProvider );

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);

		try {
			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			Element rootNode = doc.getRootElement(); 			// Load Root element
			Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			// Find Application Node
			Element findProvider = null;
			Element applicationNode = rootNode.getChild("application"); 	// Find a element
			if (applicationNode != null) {

				// Find Activity Node
				List<Element> providers = applicationNode.getChildren("provider"); 	// Find many elements
				for (Element provider : providers) {
					if (provider.hasAttributes() && provider.getAttributeValue("name",ns).equals(pathRelatif) ) {	// Load attribute value
						findProvider = provider;
						break;
					}
				}

				// If not found Node, create it
				if (findProvider == null) {
					findProvider = new Element("provider");				// Create new element
					findProvider.setAttribute("name", pathRelatif, ns);	// Add Attributes to element

					applicationNode.addContent(findProvider);
				}

				// Set values
				findProvider.setAttribute("authorities", 	this.metas.projectNameSpace.replace('/', '.'), ns);
				findProvider.setAttribute("label", 			"@string/app_provider_name", ns);
				findProvider.setAttribute("description", 	"@string/app_provider_description", ns);
				
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