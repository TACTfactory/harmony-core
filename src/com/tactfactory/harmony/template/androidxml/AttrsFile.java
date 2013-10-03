package com.tactfactory.harmony.template.androidxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.plateforme.BaseAdapter;

/**
 * Class representing an android attrs.xml file.
 */
public class AttrsFile extends XmlManager {
	/** Root element. */
	private final static String ELEMENT_ROOT = "resources";
	/** declare-styleable element. */
	private final static String ELEMENT_STYLEABLE = "declare-styleable";
	/** attr element. */
	private final static String ELEMENT_ATTR = "attr";
	/** enum element. */
	private final static String ELEMENT_ENUM = "enum";
	
	/** name attribute. */
	private final static String ATTRIBUTE_NAME = "name";
	/** format attribute. */
	private final static String ATTRIBUTE_FORMAT = "format";
	/** value attribute. */
	private final static String ATTRIBUTE_VALUE = "value";
	
	/** List of styleable items. */
	protected ArrayList<Styleable> styleables = new ArrayList<Styleable>();
	
	/**
	 * Constructor. 
	 * 
	 * @param adapter The adapter
	 * @param xmlPath The xml path
	 */
	public AttrsFile(BaseAdapter adapter, String xmlPath) {
		super(adapter, xmlPath);
		

		Element root = this.getDocument().getRootElement();
		List<Element> styleables = root.getChildren(ELEMENT_STYLEABLE);
		for (Element styleable : styleables) {
			this.styleables.add(new Styleable(styleable));
		}
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
	
	/**
	 * Add a styleable if none with he same name exists.
	 * @param styleable The styleable to add
	 */
	public void addStyleable(Styleable styleable) {
		if (getStyleable(styleable.getName()) == null) {
			this.styleables.add(styleable);
			this.getDocument().getRootElement().addContent(styleable.getElement());
		}
	}
	
	/**
	 * Gets the styleable named name
	 * @param name The name of the styleable
	 * @return The styleable
	 */
	public Styleable getStyleable(String name) {
		Styleable result = null;
		for (Styleable styleable : this.styleables) {
			if (styleable.getName().equals(name)) {
				result = styleable;
			}
		}
		return result;
	}
	
	/**
	 * Merge another AttrsFile into this one.
	 * @param attrs The other AttrsFile
	 */
	public void mergeFrom(AttrsFile attrs) {
		ArrayList<Styleable> styles = attrs.styleables;
		for (Styleable style : styles) {
			this.addStyleable(style.clone());
		}
	}

	/**
	 * The class representing an android Styleable xml item.
	 */
	public static class Styleable {
		/** The associated element. */
		protected Element element;
		/** Styleable's name.*/
		protected String name;
		/** Styleable's attrs. */
		protected HashMap<String, Attr> attrs = new HashMap<String, Attr>();
		
		/**
		 * Constructor.
		 */
		public Styleable() {
			this.element = new Element(ELEMENT_STYLEABLE);
		}
		
		/**
		 * Constructor.
		 * 
		 * @param element The element to parse
		 */
		public Styleable(Element element) {
			this.element = element;
			this.parseFromElement();
		}
		
		/**
		 * Clone.
		 * 
		 * @return The cloned styleable
		 */
		public Styleable clone() {
			Styleable result = new Styleable();
			result.setName(this.name);
			
			for(Attr attr : this.attrs.values()) {
				result.setAttr(attr.getName(), attr.clone());
			}
			
			return result;
		}
		
		/**
		 * Extract data from xml element.
		 */
		private void parseFromElement() {
			
			this.name = 
					this.element.getAttribute(ATTRIBUTE_NAME).getValue();
			
			for (Element elem : this.element.getChildren(ELEMENT_ATTR)) {
				Attr attr = new Attr(elem);
				this.attrs.put(attr.getName(), attr);
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
		 * Set the attr if not already existing.
		 * @param name The name of the attr
		 * @param attr The attr
		 */
		public void setAttr(String name, Attr attr) {
			if (!this.attrs.containsKey(name)) {
				this.attrs.put(attr.getName(), attr);
				this.element.addContent(attr.getElement());
			}
		}
		
		/**
		 * Class representing an android Attr xml item.
		 */
		public class Attr {
			/** Associated element. */
			private Element element;
			/** Attr's name. */
			private String name;
			/** Attr's format. */
			private String format;
			
			/** List of associated enums. */
			private HashMap<String, Enum> enums = new HashMap<String, Enum>();
			
			/**
			 * Constructor.
			 */
			public Attr() {
				this.element = new Element(ELEMENT_ATTR);
			}
			
			/**
			 * Constructor.
			 * 
			 * @param element The element to parse
			 */
			public Attr(Element element) {
				this.element = element;
				this.parseFromElement();
			}
			
			/**
			 * Clone method.
			 * 
			 * @return The cloned attr
			 */
			public Attr clone() {
				Attr result = new Attr();
				
				result.setName(this.name);
				result.setFormat(this.format);
				

				for(Enum enu : this.enums.values()) {
					result.enums.put(enu.getName(), enu.clone());
				}
				
				return result;
			}
			
			/**
			 * Extract data from set xml element.
			 */
			private void parseFromElement() {
				this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
				this.format = this.element.getAttributeValue(ATTRIBUTE_FORMAT);
				
				for (Element elem : this.element.getChildren(ELEMENT_ENUM)) {
					Enum e = new Enum(elem);
					this.enums.put(e.getName(), e);
				}
			}
			
			/**
			 * @return the name
			 */
			public String getName() {
				return this.name;
			}
			
			/**
			 * @return the format
			 */
			public String getFormat() {
				return this.format;
			}
			
			/**
			 * @param name the name
			 */
			public void setName(String name) {
				this.name = name;
				this.element.setAttribute(ATTRIBUTE_NAME, name);
			}
			
			/**
			 * @param format the format
			 */
			public void setFormat(String format) {
				this.format = format;
				this.element.setAttribute(ATTRIBUTE_FORMAT, format);
			}
			
			/**
			 * @return the associated element
			 */
			public Element getElement() {
				return this.element;
			}
			
			/**
			 * Class representing an android xml enum.
			 */
			public class Enum {
				/** Associated element. */
				private Element element;
				/** Enum's name. */
				private String name;
				/** Enum's value. */
				private String value;
				
				/**
				 * Constructor.
				 */
				public Enum() {
					this.element = new Element(ELEMENT_ENUM);
				}
				
				/**
				 * Constructor.
				 * 
				 * @param element The xml element
				 */
				public Enum(Element element) {
					this.element = element;
					this.parseFromElement();
				}
				
				/**
				 * Clone.
				 */
				public Enum clone() {
					Enum result = new Enum();
					
					result.setName(this.name);
					result.setValue(this.value);
					
					return result;
				}
				
				/**
				 * Extract data from set xml element.
				 */
				private void parseFromElement() {
					this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
					this.value = this.element.getAttributeValue(ATTRIBUTE_VALUE);
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
					this.element.setAttribute(ATTRIBUTE_VALUE, value);
				}
				
				/**
				 * @return the associated element
				 */
				public Element getElement() {
					return this.element;
				}
			}
		}
	}
	
	/**
	 * Merge an android attrs.xml file into another one.
	 * @param adapter The adapter
	 * @param from The source attrs.xml 
	 * @param to The attrs.xml that will be written to
	 */
	public static void mergeFromTo(BaseAdapter adapter, String from, String to) {
		AttrsFile fromStyles = new AttrsFile(adapter, from);
		AttrsFile toStyles = new AttrsFile(adapter, to);
		toStyles.mergeFrom(fromStyles);
		toStyles.save();
	}
}
