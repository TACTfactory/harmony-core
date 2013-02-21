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
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

public class TranslationGenerator extends BaseGenerator {
	private static final String NAME = "name";

	public TranslationGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
	}

	/**
	 * Update XML Strings
	 */
	public final void generateStringsXml() {
		
		
		ConsoleUtils.display(">> Generate translate string...");
		
		try {
			// Make engine
			final SAXBuilder builder = new SAXBuilder();		
			final File xmlFile = 
					FileUtils.makeFile(this.adapter.getStringsPathFile());
			
			// Load XML File
			final Document doc = builder.build(xmlFile);
			
			// Load Root element
			final Element rootNode = 
					doc.getRootElement(); 			
			
			// Load Name space (required for manipulate attributes)
			final Namespace ns = 
					rootNode.getNamespace("android");	

			for (final TranslationMetadata translationMeta 
					: this.appMetas.translates.values()) {
				Element findTranslation = null;
				
				// Debug Log
				ConsoleUtils.displayDebug(
						"Update String : " + translationMeta.key);
				
				// Find Activity Node
				final List<Element> translates = 
						rootNode.getChildren("string"); 	
				
				// Find many elements
				for (final Element translationXml : translates) {
					if (translationXml.hasAttributes() 
							&&  translationXml.getAttributeValue(NAME, ns)
								.equals(translationMeta.key)) {	
						// Load name value
						findTranslation = translationXml;
						
						break;
					}
				}

				// If not found Node, create it
				if (findTranslation == null) {
					
					// Create new element
					findTranslation = new Element("string");
					
					// Add name to element
					findTranslation.setAttribute(NAME, translationMeta.key, ns);
					
					// Set values
					findTranslation.setText(
							translationMeta.i18n.get(Locale.getDefault())); 
					
					rootNode.addContent(findTranslation);
				} else {
					translationMeta.i18n.put(Locale.getDefault(),
							findTranslation.getText());
				}
			}
			
			// Clean code
			rootNode.sortChildren(new Comparator<Element>() {

				@Override
				public int compare(final Element o1, final Element o2) {
					final String metaName1 = o1.getAttributeValue(NAME, ns);
					final String metaName2 = o2.getAttributeValue(NAME, ns);
					final TranslationMetadata meta1 =
							TranslationGenerator.this.appMetas.translates.get(
									metaName1);
					final TranslationMetadata meta2 = 
							TranslationGenerator.this.appMetas.translates.get(
									metaName2);
					
					if (meta1 != null && meta2 != null) {
						final int groupScore = 
								meta1.group.getValue() - meta2.group.getValue();
						if (groupScore != 0) {
							return groupScore;
						}
					}
					
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
									"UTF-8"));
			
		} catch (final IOException io) {
			ConsoleUtils.displayError(io);
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		}
	}
}
