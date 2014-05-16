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
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.updater.old.IXmlUtil;

/**
 * Represents an android styles.xml file.
 */
public class StylesFile extends XmlManager implements IXmlUtil {
	/** Resources element. */
	private final static String ELEMENT_ROOT = "resources";
	/** Style element. */
	private final static String ELEMENT_STYLE = "style";
	/** Item element. */
	private final static String ELEMENT_ITEM = "item";
	/** Name Attribute. */
	private final static String ATTRIBUTE_NAME = "name";
	/** Parent Attribute. */
	private final static String ATTRIBUTE_PARENT = "parent";
	
	/**
	 * List of defined styles.
	 */
	protected ArrayList<Style> styles = new ArrayList<Style>();
	
	/**
	 * Constructor.
	 * 
	 * @param adapter The adapter
	 * @param styleFilePath The file path
	 */
	public StylesFile(IAdapter adapter, String styleFilePath) {
		super(adapter, styleFilePath);
		Element root = this.getDocument().getRootElement();
		List<Element> styles = root.getChildren(ELEMENT_STYLE);
		for (Element style : styles) {
			this.styles.add(new Style(style));
		}
		
	}
	
	public StylesFile() {

	}
	
	/**
	 * Add a style if it doesn't exist yet.
	 * 
	 * @param style The style to add.
	 */
	public void addStyle(Style style) {
		if (getStyle(style.getName()) == null) {
			this.styles.add(style);
			this.getDocument().getRootElement().addContent(style.getElement());
		}
	}
	
	/** 
	 * Get the style named name.
	 * 
	 * @param name The name of the style
	 * @return The style
	 */
	public Style getStyle(String name) {
		Style result = null;
		for (Style style : this.styles) {
			if (style.getName().equals(name)) {
				result = style;
			}
		}
		return result;
	}
	
	/**
	 * Merge a StylesFile into this one.
	 * 
	 * @param styleManager The stylesfile to merge into this one
	 */
	public void mergeFrom(StylesFile styleManager) {
		ArrayList<Style> styles = styleManager.styles;
		for (Style style : styles) {
			this.addStyle(style.clone());
		}
	}
	
	/**
	 * Class representing an android style.
	 */
	public static class Style {
		/** The associated xml element. */
		protected Element element;
		/** Style's name. */
		protected String name;
		/** Style's parent. */
		protected String parent;
		/** Style's items. */
		protected HashMap<String, Item> items = new HashMap<String, Item>();
		
		/**
		 * Empty constructor.
		 */
		public Style() {
			this.element = new Element(ELEMENT_STYLE);
		}
		
		/**
		 * Clone the style.
		 * 
		 * @return The style
		 */
		public Style clone() {
			Style result = new Style();
			result.setName(this.name);
			result.setParent(this.parent);
			
			for(Item item : this.items.values()) {
				result.setItem(item.getName(), item.getValue());
			}
			
			return result;
		}
		
		/**
		 * Constructor.
		 * 
		 * @param element The element to extract
		 */
		public Style(Element element) {
			this.element = element;
			this.parseFromElement();
		}
		
		/**
		 * Parse the element to fill this style.
		 */
		private void parseFromElement() {
			
			this.name = 
					this.element.getAttribute(ATTRIBUTE_NAME).getValue();
			
			if (this.element.getAttribute(ATTRIBUTE_PARENT) != null) {
				this.parent = 
						this.element.getAttribute(ATTRIBUTE_PARENT).getValue();
			}
			
			for (Element elem : this.element.getChildren(ELEMENT_ITEM)) {
				Item item = new Item(elem);
				this.items.put(item.getName(), item);
			}
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
		 * @return the parent
		 */
		public final String getParent() {
			return parent;
		}

		/**
		 * @param parent the parent to set
		 */
		public final void setParent(String parent) {
			this.parent = parent;
			this.element.setAttribute(ATTRIBUTE_PARENT, this.parent);
		}
		
		/**
		 * Set a style item.
		 * @param name The name of the item
		 * @param value The value of this item
		 */
		public void setItem(String name, String value) {
			if (!this.items.containsKey(name)) {
				Item item = new Item();
				item.setName(name);
				item.setValue(value);
				this.items.put(item.getName(), item);
				this.element.addContent(item.getElement());
			}
		}
		
		/**
		 * Class representing a Style item.
		 */
		public class Item {
			/** The associated element. */
			private Element element;
			/** The item's name. */
			private String name;
			/** The item's value. */
			private String value;
			
			/**
			 * Constructor.
			 */
			public Item() {
				this.element = new Element(ELEMENT_ITEM);
			}
			
			/**
			 * Constructor.
			 * 
			 * @param element The element to extract
			 */
			public Item(Element element) {
				this.element = element;
				this.parseFromElement();
			}
			
			/**
			 * Extract the element's content.
			 */
			private void parseFromElement() {
				this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
				this.value = this.element.getText();
			}
			
			/**
			 * @return the name
			 */
			public String getName() {
				return this.name;
			}
			
			/**
			 * @return the value
			 */
			public String getValue() {
				return this.value;
			}
			
			/**
			 * @param name the name
			 */
			public void setName(String name) {
				this.name = name;
				this.element.setAttribute(ATTRIBUTE_NAME, name);
			}
			
			/**
			 * @param value the value
			 */
			public void setValue(String value) {
				this.value = value;
				this.element.setText(value);
			}
			
			/**
			 * @return the associated element.
			 */
			public Element getElement() {
				return this.element;
			}
		}
	}
	
	/**
	 * Merge a styles.xml files into another one. 
	 * @param adapter The adapter
	 * @param from The source styles.xml 
	 * @param to The styles.xml to overwrite
	 */
	public static void mergeFromTo(IAdapter adapter, String from, String to) {
		StylesFile fromStyles = new StylesFile(adapter, from);
		StylesFile toStyles = new StylesFile(adapter, to);
		toStyles.mergeFrom(fromStyles);
		toStyles.save();
	}

	@Override
	protected Element getDefaultRoot() {
		Element rootElement = new Element(ELEMENT_ROOT);
		rootElement.addNamespaceDeclaration(
				Namespace.getNamespace(
						"android", 
						"http://schemas.android.com/apk/res/android"));
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
