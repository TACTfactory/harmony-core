package com.tactfactory.harmony.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Utility class for manipulating XML files.
 */
public abstract class XMLUtils {

	/**
	 * Open an XML file.
	 * @param fileName The name of the file.
	 * @return The openened Document object. Or null if nothing found.
	 */
	public static Document openXMLFile(final String fileName) {
		Document doc = null;
		
		// Make engine
		final SAXBuilder builder = new SAXBuilder();		
		final File xmlFile = 
				TactFileUtils.makeFile(fileName);
		
		try {
			// Load XML File
			doc = builder.build(xmlFile);
		
		} catch (JDOMException e) {
			ConsoleUtils.displayError(e);
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}
		
		return doc;
	}
	
	/**
	 * Write an XML Document to the given file.
	 * @param doc The XML document to write
	 * @param xmlFileName The name of the file
	 */
	public static void writeXMLToFile(final Document doc, 
			final String xmlFileName) {
		try {
			final File xmlFile = 
					TactFileUtils.makeFile(xmlFileName);
			// Write to File
			final XMLOutputter xmlOutput = new XMLOutputter();
			// Make beautiful file with indent !!!
			xmlOutput.setFormat(Format.getPrettyFormat());			
			xmlOutput.output(doc, 
					new OutputStreamWriter(
							new FileOutputStream(
									xmlFile.getAbsoluteFile()),
									TactFileUtils.DEFAULT_ENCODING));
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Add a node to the XML node if it doesn't contains it already.
	 * @param newNode The node to add
	 * @param id The attribute name to compare 
	 * @param baseNode The node to add the new node to.
	 * @return True if the node was added successfully.
	 * False if the node already exists before.
	 */
	public static boolean addValue(final Element newNode, 
			final String id,
			final Element baseNode) {
		
		Element findElement = null;
		
		// Debug Log
		ConsoleUtils.displayDebug(
				"Update String : " + newNode.getAttributeValue(id));
				
		findElement = 
				findNode(baseNode, 
						newNode,
						id);
		
		// If not found Node, create it
		if (findElement == null) {
			baseNode.addContent(newNode);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Find a node in the given node.
	 * @param baseNode The node in whom to search.
	 * @param newNode The node to search.
	 * @param id The attribute name used for the comparison
	 * @return The found node or null if the node doesn't exists
	 */
	public static Element findNode(final Element baseNode, 
			final Element newNode,
			final String id) {
		
		final List<Element> nodes = 
				baseNode.getChildren(newNode.getName()); 	
		
		// Look in the children nodes if one node 
		// has the corresponding key/value couple
		for (final Element node : nodes) {
			if (node.hasAttributes() 
					&&  node.getAttributeValue(id)
						.equals(newNode.getAttributeValue(id))) {	
				return node;
			}
		}
		
		return null;
	}
}

