/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.template.androidxml;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.old.IXmlUtil;

/**
 * Represents an android dimens.xml file.
 */
public class ColorsFile extends XmlManager implements IXmlUtil {
	/** Resources element. */
	private final static String ELEMENT_ROOT = "resources";
	/** Dimen element. */
	private final static String ELEMENT_COLOR = "color";
	/** Name Attribute. */
	private final static String ATTRIBUTE_NAME = "name";
	
	/**
	 * List of defined dimens.
	 */
	protected ArrayList<Dimen> dimens = new ArrayList<Dimen>();
	
	/**
	 * Constructor.
	 * 
	 * @param adapter The adapter
	 * @param dimenFilePath The file path
	 */
	public ColorsFile(IAdapter adapter, String dimenFilePath) {
		super(adapter, dimenFilePath);
		Element root = this.getDocument().getRootElement();
		List<Element> dimens = root.getChildren(ELEMENT_COLOR);
		for (Element dimen : dimens) {
			this.dimens.add(new Dimen(dimen));
		}
		
	}
	
	public ColorsFile() {
	    
	}
	
	/**
	 * Add a dimen if it doesn't exist yet.
	 * 
	 * @param dimen The dimen to add.
	 */
	public void addDimen(Dimen dimen) {
		if (getDimen(dimen.getName()) == null) {
			this.dimens.add(dimen);
			this.getDocument().getRootElement().addContent(dimen.getElement());
		}
	}
	
	/** 
	 * Get the dimen named name.
	 * 
	 * @param name The name of the dimen
	 * @return The dimen
	 */
	public Dimen getDimen(String name) {
		Dimen result = null;
		for (Dimen dimen : this.dimens) {
			if (dimen.getName().equals(name)) {
				result = dimen;
			}
		}
		return result;
	}
	
	/**
	 * Merge a DimensFile into this one.
	 * 
	 * @param dimenManager The dimensfile to merge into this one
	 */
	public void mergeFrom(ColorsFile dimenManager) {
		ArrayList<Dimen> dimens = dimenManager.dimens;
		for (Dimen dimen : dimens) {
			this.addDimen(dimen.clone());
		}
	}
	
	/**
	 * Class representing an android dimen.
	 */
	public static class Dimen {
		/** The associated xml element. */
		protected Element element;
		/** Dimen's name. */
		protected String name;
		/** Dimen's value. */
		protected String value;
		
		/**
		 * Empty constructor.
		 */
		public Dimen() {
			this.element = new Element(ELEMENT_COLOR);
		}
		
		/**
		 * Clone the dimen.
		 * 
		 * @return The dimen
		 */
		public Dimen clone() {
			Dimen result = new Dimen();
			result.setName(this.name);
			result.setValue(this.value);
			
			return result;
		}
		
		/**
		 * Constructor.
		 * 
		 * @param element The element to extract
		 */
		public Dimen(Element element) {
			this.element = element;
			this.parseFromElement();
		}
		
		/**
		 * Parse the element to fill this dimen.
		 */
		private void parseFromElement() {
			
			this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
			this.value = this.element.getValue();
		}
		
		/**
		 * @return the element
		 */
		public final Element getElement() {
			return element;
		}

		/**
		 * @return the name
		 */
		public final String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public final void setName(String name) {
			this.name = name;
			this.element.setAttribute(ATTRIBUTE_NAME, this.name);
		}
		
		/**
		 * @return the name
		 */
		public final String getValue() {
			return this.value;
		}

		/**
		 * @param name the name to set
		 */
		public final void setValue(String value) {
			this.value = value;
			this.element.setText(value);
		}
	}
	
	/**
	 * Merge a dimens.xml files into another one. 
	 * @param adapter The adapter
	 * @param from The source dimens.xml 
	 * @param to The dimens.xml to overwrite
	 */
	public static void mergeFromTo(IAdapter adapter, String from, String to) {
		ColorsFile fromDimens = new ColorsFile(adapter, from);
		ColorsFile toDimens = new ColorsFile(adapter, to);
		toDimens.mergeFrom(fromDimens);
		toDimens.save();
	}

	@Override
	protected Element getDefaultRoot() {
		Element rootElement = new Element(ELEMENT_ROOT);
		return rootElement;
	}

    @Override
    public void mergeFiles(String from, String to) {
        mergeFromTo(null, from, to);
    }

    @Override
    public void open(String file) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String addElement(String key, String value) {
        // TODO Auto-generated method stub
        return null;
    }
}
