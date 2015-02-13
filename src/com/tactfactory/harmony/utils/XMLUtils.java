/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.support.AbstractXMLOutputProcessor;
import org.jdom2.output.support.FormatStack;

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
	 * Open an XML file.
	 * @param fileName The name of the file.
	 * @return The openened Document object. Or null if nothing found.
	 */
	public static Document openXML(final String fileName) {
		Document doc = null;

		try {
			// Make engine
			final SAXBuilder builder = new SAXBuilder();
			final File xmlFile = new File(fileName);
					
			if (!xmlFile.exists()) {
				doc = new Document();
			} else {
				// Load XML File
				doc = builder.build(xmlFile);
			}

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
			final File xmlFile = TactFileUtils.makeFile(xmlFileName);
			final XMLOutputter xmlOutput = new XMLOutputter();
			
			// Write to File
			// Make beautiful file with indent !!!
			xmlOutput.setFormat(Format.getPrettyFormat().setIndent("    "));
			xmlOutput.setXMLOutputProcessor(new TactXMLOutputter());
			FileOutputStream fos = new FileOutputStream(
					xmlFile.getAbsoluteFile());
			
			xmlOutput.output(
					doc,
					new OutputStreamWriter(
							fos,
							TactFileUtils.DEFAULT_ENCODING));
			
			fos.close();
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
		    newNode.setText(findElement.getText());
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
		
		Element result = null;
		
		final List<Element> nodes =
				baseNode.getChildren(newNode.getName());

		// Look in the children nodes if one node
		// has the corresponding key/value couple
		for (final Element node : nodes) {
			if (node.hasAttributes()
					&&  node.getAttributeValue(id)
						.equals(newNode.getAttributeValue(id))) {
				result = node;
			}
		}

		return result;
	}
	
	/**
	 * Open a remote XML file.
	 * @param url The url of the xml file.
	 * @return The Element corresponding to the XML.
	 */
	public static Document getRemoteXML(final String url) {
		Document result = null;
		try {
			SAXBuilder builder = new SAXBuilder();
			result  = builder.build(new URL(url));
		} catch (MalformedURLException e) {
			ConsoleUtils.displayError(e);
		} catch (JDOMException e) {
			ConsoleUtils.displayError(e);
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}
		
		
		
		return result;
	}
	
	public static class TactXMLOutputter extends AbstractXMLOutputProcessor {
		
		@Override
		protected void printAttribute(final Writer out, final FormatStack fstack,
				final Attribute attribute) throws IOException {

			if (!attribute.isSpecified() && fstack.isSpecifiedAttributesOnly()) {
				return;
			}
			if (attribute.getParent().getAttributes().size() > 1) {
				write(out, fstack.getLineSeparator());
				write(out, fstack.getLevelIndent() + fstack.getIndent());
			} else {
				write(out, " ");
			}
			write(out, attribute.getQualifiedName());
			write(out, "=");

			write(out, "\"");
			attributeEscapedEntitiesFilter(out, fstack, attribute.getValue());
			write(out, "\"");
		}
        
		protected void printNamespace(Writer out, FormatStack fstack, Namespace ns)
		        throws java.io.IOException {
		    if (ns == Namespace.NO_NAMESPACE) {
		        return;
		    } else {
		        super.printNamespace(out, fstack, ns);
		    }
		}
	}
}

