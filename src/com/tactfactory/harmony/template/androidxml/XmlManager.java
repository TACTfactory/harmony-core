package com.tactfactory.harmony.template.androidxml;

import org.jdom2.Document;
import org.jdom2.Element;

import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.XMLUtils;

public abstract class XmlManager {
	private String filePath;
	private Document docXml;
	private BaseAdapter adapter;
	
	public XmlManager(final BaseAdapter adapter, final String xmlPath) {
		this.filePath = xmlPath;
		this.adapter = adapter;
		this.docXml = XMLUtils.openXML(xmlPath);
		

		if (!this.getDocument().hasRootElement()) {
			this.getDocument().setRootElement(this.getDefaultRoot());
		}
	}
	
	public Document getDocument() {
		return this.docXml;
	}
	
	public BaseAdapter getAdapter() {
		return this.adapter;
	}
	
	public void save() {
		// Write XML to file.
		XMLUtils.writeXMLToFile(
				this.docXml,
				this.filePath);
	}
	
	protected abstract Element getDefaultRoot();
}
