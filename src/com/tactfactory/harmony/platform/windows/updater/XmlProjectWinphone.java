/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.winphone.updater;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;

import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.XMLUtils;

public class XmlProjectWinphone {
    /** Xml element root. */
    private static final String XML_ELEMENT_ROOT = "Project";
    /** Xml element item. */
    private static final String XML_ELEMENT_ITEM = "ItemGroup";
    /** Xml element dependent. */
    private static final String XML_ELEMENT_DEPENDENT = "DependentUpon";
    /** Xml element compile. */
    private static final String XML_ELEMENT_COMPILE = "Compile";
    /** Xml element ApplicationDefinition. */
    private static final String XML_ELEMENT_APPLICATION = "ApplicationDefinition";
    /** Xml element Page. */
    private static final String XML_ELEMENT_PAGE = "Page";
    /** Xml element None. */
    private static final String XML_ELEMENT_NONE = "None";
    /** Xml element Content. */
    private static final String XML_ELEMENT_CONTENT = "Content";
    /** Xml element EmbeddedResource. */
    private static final String XML_ELEMENT_EMBEDDED = "EmbeddedResource";
//    /** Xml element Reference. */
//    private static final String XML_ELEMENT_REFERENCE = "Reference";
    /** Xml element SubType. */
    private static final String XML_ELEMENT_SUBTYPE = "SubType";
    /** Xml element Generator. */
    private static final String XML_ELEMENT_GENERATOR = "Generator";
    /** Xml content Designer. */
    private static final String XML_CONTENT_DESIGNER = "Designer";
    /** Xml content Designer. */
    private static final String XML_CONTENT_MSBUILD_COMPILE = "MSBuild:Compile";
    /** Xml attribut include. */
    private static final String XML_ATTRIBUT_INCLUDE = "Include";
    
    private String file;
    private Document doc;
    private Element rootNode;
    
    public void open(String file) {
        this.file = file;
        
        if (TactFileUtils.exists(file)) {
            this.doc = XMLUtils.openXMLFile(file);
        } else {
            Element rootElement = new Element(XML_ELEMENT_ROOT);
            this.doc = new Document(rootElement);
        }

        // Load Root element
        this.rootNode = doc.getRootElement();
    }

    public void save() {
        XMLUtils.writeXMLToFile(doc, this.file);
    }

    public void addCompileFile(String filename, String depends) {
        final Element newNode = new Element(XML_ELEMENT_COMPILE);

        newNode.setAttribute(XML_ATTRIBUT_INCLUDE, filename);
        
        if (depends != null) {
            Element childDepends = new Element(XML_ELEMENT_DEPENDENT);
            childDepends.addContent(depends);
            newNode.addContent(childDepends);
        }

        Element itemGroup = this.findFirstItemGroup(XML_ELEMENT_COMPILE);
        
        this.removeIfExists(itemGroup, XML_ELEMENT_COMPILE, filename);
        itemGroup.addContent(newNode);
    }
    
    public void addApplicationDefinitionFile(String filename) {
        final Element newNode = new Element(XML_ELEMENT_APPLICATION);

        newNode.setAttribute(XML_ATTRIBUT_INCLUDE, filename);
        
        Element subtype = new Element(XML_ELEMENT_SUBTYPE);
        subtype.setText(XML_CONTENT_DESIGNER);
        Element generator = new Element(XML_ELEMENT_GENERATOR);
        generator.setText(XML_CONTENT_MSBUILD_COMPILE);
        
        newNode.addContent(subtype);
        newNode.addContent(generator);

        Element itemGroup = this.findFirstItemGroup(XML_ELEMENT_APPLICATION);
        
        this.removeIfExists(itemGroup, XML_ELEMENT_APPLICATION, filename);
        itemGroup.addContent(newNode);
    }
    
    public void addPageFile(String filename) {
        final Element newNode = new Element(XML_ELEMENT_PAGE);

        newNode.setAttribute(XML_ATTRIBUT_INCLUDE, filename);
        
        Element subtype = new Element(XML_ELEMENT_SUBTYPE);
        subtype.setText(XML_CONTENT_DESIGNER);
        Element generator = new Element(XML_ELEMENT_GENERATOR);
        generator.setText(XML_CONTENT_MSBUILD_COMPILE);
        
        newNode.addContent(subtype);
        newNode.addContent(generator);

        Element itemGroup = this.findFirstItemGroup(XML_ELEMENT_PAGE);
        
        this.removeIfExists(itemGroup, XML_ELEMENT_PAGE, filename);
        itemGroup.addContent(newNode);
    }
    
    public void addNoneFile(String filename) {
        final Element newNode = new Element(XML_ELEMENT_NONE);

        newNode.setAttribute(XML_ATTRIBUT_INCLUDE, filename);

        Element itemGroup = this.findFirstItemGroup(XML_ELEMENT_NONE);
        
        this.removeIfExists(itemGroup, XML_ELEMENT_NONE, filename);
        itemGroup.addContent(newNode);
    }
    
    public void addContentFile(String filename) {
        final Element newNode = new Element(XML_ELEMENT_CONTENT);
        
        newNode.setAttribute(XML_ATTRIBUT_INCLUDE, filename);
        
        Element itemGroup = this.findFirstItemGroup(XML_ELEMENT_CONTENT);
        
        this.removeIfExists(itemGroup, XML_ELEMENT_CONTENT, filename);
        itemGroup.addContent(newNode);
    }
    
    public void addEmbeddedFile(String filename) {
        final Element newNode = new Element(XML_ELEMENT_EMBEDDED);
        
        newNode.setAttribute(XML_ATTRIBUT_INCLUDE, filename);
        
        Element itemGroup = this.findFirstItemGroup(XML_ELEMENT_EMBEDDED);
        
        this.removeIfExists(itemGroup, XML_ELEMENT_EMBEDDED, filename);
        itemGroup.addContent(newNode);
    }
    
    public void addReferenceFile(String filename) {
        //TODO
    }
    
    private void removeIfExists(Element element, String type, String file) {
    	Filter<Element> filter = new ElementFilter(type);
        List<Element> content = element.getContent(filter);
        List<Element> elementToDelete = new ArrayList<>();
        
        for (Element node : content) {
        	if (node.getAttribute("Include").getValue().equals(file)) {
        		elementToDelete.add(node);
        	}
		}
        
        for (Element node : elementToDelete) {
        	element.removeContent(node);
		}
    }
    
    private Element findFirstItemGroup(String groupType) {
        Element result = null;
        
        Filter<Element> filter = new ElementFilter(XML_ELEMENT_ITEM);
        List<Element> content = this.rootNode.getContent(filter);
        
        filter = new ElementFilter(groupType);
        
        for (Element element : content) {
            content = element.getContent(filter);
            
            if (!content.isEmpty()) {
                result = element;
                break;
            }
        }
        
        if (result == null) {
            result = new Element(XML_ELEMENT_ITEM);
            filter = new ElementFilter("Import");
            content = this.rootNode.getContent(filter);
            rootNode.addContent(rootNode.indexOf(content.get(0)), result);
        }
        
        return result;
    }
}
