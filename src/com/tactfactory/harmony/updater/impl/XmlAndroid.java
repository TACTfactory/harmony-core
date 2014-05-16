package com.tactfactory.harmony.updater.impl;

import java.util.Comparator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.TranslationMetadata;
import com.tactfactory.harmony.updater.old.IConfigFileUtil;
import com.tactfactory.harmony.updater.old.ITranslateFileUtil;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.XMLUtils;

public class XmlAndroid implements ITranslateFileUtil, IConfigFileUtil {
    /**
     * Xml element name.
     */
    private static final String XML_ELEMENT_NAME = "name";
    
    /**
     * Xml element string.
     */
    private static final String XML_ELEMENT_STRING = "string";
    
    private String file;
    private Document doc;
    private Element rootNode;
    private Namespace namespace;
    
    @Override
    public void open(String file) {
        this.file = file;
        
        if (TactFileUtils.exists(file)) {
            this.doc = XMLUtils.openXMLFile(file);
        } else {
            Element rootElement = new Element("resources");
            this.doc = new Document(rootElement);
        }

        // Load Root element
        this.rootNode = doc.getRootElement();

        // Load Name space (required for manipulate attributes)
        this.namespace = rootNode.getNamespace("android");
    }

    @Override
    public void save() {
        // Clean code
        this.rootNode.sortChildren(new Comparator<Element>() {

            @Override
            public int compare(final Element o1, final Element o2) {
                final String metaName1 = o1.getAttributeValue(
                        XML_ELEMENT_NAME,
                        XmlAndroid.this.namespace);
                final String metaName2 = o2.getAttributeValue(
                        XML_ELEMENT_NAME,
                        XmlAndroid.this.namespace);
                
                final TranslationMetadata meta1 = ApplicationMetadata.INSTANCE
                        .getTranslates().get(metaName1);
                final TranslationMetadata meta2 = ApplicationMetadata.INSTANCE
                        .getTranslates().get(metaName2);

                if (meta1 != null && meta2 != null) {
                    final int groupScore =
                            meta1.getGroup().getValue()
                            - meta2.getGroup().getValue();
                    if (groupScore != 0) {
                        return groupScore;
                    }
                }

                return metaName1.compareToIgnoreCase(metaName2);
            }
        });

        XMLUtils.writeXMLToFile(doc, this.file);
    }

    @Override
    public String addElement(String key, String value) {
        String result = value;
        
        final Element newNode = new Element(XML_ELEMENT_STRING);

        // Add name to element
        newNode.setAttribute(XML_ELEMENT_NAME, key, this.namespace);

        // Set values
        newNode.setText(value);

        // If not found Node, create it
        if (!XMLUtils.addValue(newNode, XML_ELEMENT_NAME, rootNode)) {
            result = newNode.getText();
        }
        
        return result;
    }

    @Override
    public void mergeFiles(String from, String to) {
        // TODO Auto-generated method stub
        
    }
}
