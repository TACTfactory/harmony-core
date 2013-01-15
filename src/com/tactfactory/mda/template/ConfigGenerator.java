package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import com.tactfactory.mda.meta.ConfigMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

public class ConfigGenerator extends BaseGenerator {

	public ConfigGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}

	/**
	 * Update XML Strings
	 * 
	 * @param cfg Template engine
	 */
	public void generateConfigXml() {		
		ConsoleUtils.display(">> Generate config string...");
		Element findConfig;
		
		try {
			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = FileUtils.makeFile(this.adapter.getConfigsPathFile() );
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			for (ConfigMetadata configMeta : this.appMetas.configs.values()) {
				findConfig = null;
				
				// Debug Log
				ConsoleUtils.displayDebug("Update config : " + configMeta.key);
				
				// Find String Node
				List<Element> configs = rootNode.getChildren("string"); 	// Find many elements
				for (Element configXml : configs) {
					if (configXml.hasAttributes() && 
							configXml.getAttributeValue("name",ns).equals(configMeta.key)) {	// Load name value
						findConfig = configXml;
						
						break;
					}
				}

				// If not found Node, create it
				if (findConfig == null) {
					findConfig = new Element("string");		// Create new element
					findConfig.setAttribute("name", configMeta.key, ns);	// Add name to element
					findConfig.setText(configMeta.value); // Set values
					
					rootNode.addContent(findConfig);
				} else {
					configMeta.value = findConfig.getText();
				}
			}
			
			// Clean code
			rootNode.sortChildren(new Comparator<Element>() {
				String metaName1;
				String metaName2;
				
				@Override
				public int compare(Element o1, Element o2) {
					metaName1 = o1.getAttributeValue("name", ns);
					metaName2 = o2.getAttributeValue("name", ns);
					
					return metaName1.compareToIgnoreCase(metaName2);
				}
			});
			
			// Write to File
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());			// Make beautiful file with indent !!!
			xmlOutput.output(doc, new FileWriter(xmlFile.getAbsoluteFile()));
			
		} catch (IOException io) {
			ConsoleUtils.displayError(io.getMessage());
			io.printStackTrace();
		} catch (JDOMException e) {
			ConsoleUtils.displayError(e.getMessage());
			e.printStackTrace();
		}
	}
}
