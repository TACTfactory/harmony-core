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
	 * Update XML Strings.
	 */
	public final void generateConfigXml() {		
		ConsoleUtils.display(">> Generate config string...");
		
		try {
			// Make engine
			final SAXBuilder builder = new SAXBuilder();		
			final File xmlFile = 
					FileUtils.makeFile(this.adapter.getConfigsPathFile());
			
			// Load XML File
			final Document doc = builder.build(xmlFile);
			
			// Load Root element
			final Element rootNode = doc.getRootElement();
			
			// Load Name space (required for manipulate attributes)
			final Namespace ns = rootNode.getNamespace("android");	

			for (final ConfigMetadata configMeta 
					: this.appMetas.configs.values()) {
				Element findConfig = null;
				
				// Debug Log
				ConsoleUtils.displayDebug("Update config : " + configMeta.key);
				
				// Find String Node
				final List<Element> configs = 
						rootNode.getChildren("string"); 	
				// Find many elements
				for (final Element configXml : configs) {
					if (configXml.hasAttributes() 
							&& configXml.getAttributeValue(NAME, ns)
								.equals(configMeta.key)) {	// Load name value
						findConfig = configXml;
						
						break;
					}
				}

				// If not found Node, create it
				if (findConfig == null) {
					// Create new element
					findConfig = new Element("string");
					
					// Add name to element
					findConfig.setAttribute(NAME, configMeta.key, ns);
					
					findConfig.setText(configMeta.value); // Set values
					
					rootNode.addContent(findConfig);
				} else {
					configMeta.value = findConfig.getText();
				}
			}
			
			// Clean code
			rootNode.sortChildren(new Comparator<Element>() {
							
				@Override
				public int compare(final Element o1, final Element o2) {
					final String metaName1 = o1.getAttributeValue(NAME, ns);
					final String metaName2 = o2.getAttributeValue(NAME, ns);
					
					return metaName1.compareToIgnoreCase(metaName2);
				}
			});
			
			// Write to File
			final XMLOutputter xmlOutput = new XMLOutputter();
			// Make beautiful file with indent !!!
			xmlOutput.setFormat(Format.getPrettyFormat());			
			xmlOutput.output(doc, 
					new OutputStreamWriter(
							new FileOutputStream(
									xmlFile.getAbsoluteFile()),
									FileUtils.DEFAULT_ENCODING));
			
		} catch (final IOException io) {
			ConsoleUtils.displayError(io);
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		}
	}
}
