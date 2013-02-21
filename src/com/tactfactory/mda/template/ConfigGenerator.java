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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.tactfactory.mda.meta.ConfigMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

public class ConfigGenerator extends BaseGenerator {
	private static final String NAME = "name";

	public ConfigGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
	}

	/**
	 * Update XML Strings
	 */
	public void generateConfigXml() {		
		ConsoleUtils.display(">> Generate config string...");
		
		try {
			final SAXBuilder builder = new SAXBuilder();		// Make engine
			final File xmlFile = FileUtils.makeFile(this.adapter.getConfigsPathFile() );
			final Document doc = builder.build(xmlFile); 	// Load XML File
			final Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			for (final ConfigMetadata configMeta : this.appMetas.configs.values()) {
				Element findConfig = null;
				
				// Debug Log
				ConsoleUtils.displayDebug("Update config : " + configMeta.key);
				
				// Find String Node
				final List<Element> configs = rootNode.getChildren("string"); 	// Find many elements
				for (final Element configXml : configs) {
					if (configXml.hasAttributes() && 
							configXml.getAttributeValue(NAME,ns).equals(configMeta.key)) {	// Load name value
						findConfig = configXml;
						
						break;
					}
				}

				// If not found Node, create it
				if (findConfig == null) {
					findConfig = new Element("string");		// Create new element
					findConfig.setAttribute(NAME, configMeta.key, ns);	// Add name to element
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
				public int compare(final Element o1, final Element o2) {
					this.metaName1 = o1.getAttributeValue(NAME, ns);
					this.metaName2 = o2.getAttributeValue(NAME, ns);
					
					return this.metaName1.compareToIgnoreCase(this.metaName2);
				}
			});
			
			// Write to File
			final XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());			// Make beautiful file with indent !!!
			xmlOutput.output(doc, new OutputStreamWriter(new FileOutputStream(xmlFile.getAbsoluteFile()), FileUtils.DEFAULT_ENCODING));
			
		} catch (final IOException io) {
			ConsoleUtils.displayError(io);
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		}
	}
}
