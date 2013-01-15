package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

public class TranslationGenerator extends BaseGenerator {

	public TranslationGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}

	/**
	 * Update XML Strings
	 * 
	 * @param cfg Template engine
	 */
	public void generateStringsXml() {
		Element findTranslation;
		
		ConsoleUtils.display(">> Generate translate string...");
		
		try {
			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = FileUtils.makeFile(this.adapter.getStringsPathFile() );
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			for (TranslationMetadata translationMeta : this.appMetas.translates.values()) {
				findTranslation = null;
				
				// Debug Log
				ConsoleUtils.displayDebug("Update String : " + translationMeta.key);
				
				// Find Activity Node
				List<Element> translates = rootNode.getChildren("string"); 	// Find many elements
				for (Element translationXml : translates) {
					if (translationXml.hasAttributes() && 
							translationXml.getAttributeValue("name",ns).equals(translationMeta.key)) {	// Load name value
						findTranslation = translationXml;
						
						break;
					}
				}

				// If not found Node, create it
				if (findTranslation == null) {
					findTranslation = new Element("string");		// Create new element
					findTranslation.setAttribute("name", translationMeta.key, ns);	// Add name to element
					findTranslation.setText(translationMeta.i18n.get(Locale.getDefault()) ); // Set values
					
					rootNode.addContent(findTranslation);
				} else {
					translationMeta.i18n.put(Locale.getDefault(), findTranslation.getText());
				}
			}
			
			// Clean code
			rootNode.sortChildren(new Comparator<Element>() {

				@Override
				public int compare(Element o1, Element o2) {
					String metaName1 = o1.getAttributeValue("name", ns);
					String metaName2 = o2.getAttributeValue("name", ns);
					TranslationMetadata meta1 = TranslationGenerator.this.appMetas.translates.get(metaName1);
					TranslationMetadata meta2 = TranslationGenerator.this.appMetas.translates.get(metaName2);
					
					if (meta1 != null && meta2 != null) {
						int groupScore = meta1.group.getValue() - meta2.group.getValue();
						if (groupScore != 0) return groupScore;
					}
					
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
