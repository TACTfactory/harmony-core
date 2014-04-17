/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template.androidxml;

import org.jdom2.Document;
import org.jdom2.Element;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.utils.XMLUtils;

/**
 * Abstract class for XML file modifications.
 */
public abstract class XmlManager {
	/** File path. */
	private String filePath;
	/** XML Document. */
	private Document docXml;
	/** Adapter. */
	private IAdapter adapter;
	
	/**
	 * Constructor.
	 * 
	 * If xmlPath points to no file, an empty Document will be created, 
	 * and the file will be created on save method call.
	 * 
	 * @param adapter The adapter to use.
	 * @param xmlPath The path to the xml file.
	 */
	public XmlManager(final IAdapter adapter, final String xmlPath) {
		this.filePath = xmlPath;
		this.adapter = adapter;
		this.docXml = XMLUtils.openXML(xmlPath);
		

		if (!this.getDocument().hasRootElement()) {
			this.getDocument().setRootElement(this.getDefaultRoot());
		}
	}
	
	public XmlManager() {
	    
	}
	
	/**
	 * @return the document.
	 */
	public Document getDocument() {
		return this.docXml;
	}
	
	/**
	 * @return the adapter.
	 */
	public IAdapter getAdapter() {
		return this.adapter;
	}
	
	/**
	 * Save the document to the file.
	 */
	public void save() {
		// Write XML to file.
		XMLUtils.writeXMLToFile(
				this.docXml,
				this.filePath);
	}
	
	/**
	 * Get default root element for when file doesn't exist.
	 * @return The default root element.
	 */
	protected abstract Element getDefaultRoot();
}
