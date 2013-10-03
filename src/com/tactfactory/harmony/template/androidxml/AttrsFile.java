package com.tactfactory.harmony.template.androidxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.plateforme.BaseAdapter;

public class AttrsFile extends XmlManager {
	private final static String ELEMENT_ROOT = "resources";
	private final static String ELEMENT_STYLEABLE = "declare-styleable";
	private final static String ELEMENT_ATTR = "attr";
	private final static String ELEMENT_ENUM = "enum";
	
	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_FORMAT = "format";
	private final static String ATTRIBUTE_VALUE = "value";
	
	protected ArrayList<Styleable> styleables = new ArrayList<Styleable>();
	
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
	
	public void addStyleable(Styleable styleable) {
		if (getStyleable(styleable.getName()) == null) {
			this.styleables.add(styleable);
			this.getDocument().getRootElement().addContent(styleable.getElement());
		}
	}
	
	public Styleable getStyleable(String name) {
		Styleable result = null;
		for (Styleable styleable : this.styleables) {
			if (styleable.getName().equals(name)) {
				result = styleable;
			}
		}
		return result;
	}
	
	public void mergeFrom(AttrsFile attrs) {
		ArrayList<Styleable> styles = attrs.styleables;
		for (Styleable style : styles) {
			this.addStyleable(style.clone());
		}
	}

	public static class Styleable {
		protected Element element;
		protected String name;
		protected HashMap<String, Attr> attrs = new HashMap<String, Attr>();
		
		public Styleable() {
			this.element = new Element(ELEMENT_STYLEABLE);
		}
		
		public Styleable(Element element) {
			this.element = element;
			this.parseFromElement();
		}
		
		public Styleable clone() {
			Styleable result = new Styleable();
			result.setName(this.name);
			
			for(Attr attr : this.attrs.values()) {
				result.setAttr(attr.getName(), attr.clone());
			}
			
			return result;
		}
		
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
		
		public void setAttr(String name, Attr attr) {
			if (!this.attrs.containsKey(name)) {
				this.attrs.put(attr.getName(), attr);
				this.element.addContent(attr.getElement());
			}
		}
		
		public class Attr {
			private Element element;
			private String name;
			private String format;
			
			private HashMap<String, Enum> enums = new HashMap<String, Enum>();
			
			public Attr() {
				this.element = new Element(ELEMENT_ATTR);
			}
			
			public Attr(Element element) {
				this.element = element;
				this.parseFromElement();
			}
			
			public Attr clone() {
				Attr result = new Attr();
				
				result.setName(this.name);
				result.setFormat(this.format);
				

				for(Enum enu : this.enums.values()) {
					result.enums.put(enu.getName(), enu.clone());
				}
				
				return result;
			}
			
			private void parseFromElement() {
				this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
				this.format = this.element.getAttributeValue(ATTRIBUTE_FORMAT);
				
				for (Element elem : this.element.getChildren(ELEMENT_ENUM)) {
					Enum e = new Enum(elem);
					this.enums.put(e.getName(), e);
				}
			}
			
			public String getName() {
				return this.name;
			}
			
			public String getFormat() {
				return this.format;
			}
			
			public void setName(String name) {
				this.name = name;
				this.element.setAttribute(ATTRIBUTE_NAME, name);
			}
			
			public void setFormat(String format) {
				this.format = format;
				this.element.setAttribute(ATTRIBUTE_FORMAT, format);
			}
			
			public Element getElement() {
				return this.element;
			}
			
			public class Enum {
				private Element element;
				private String name;
				private String value;
				
				public Enum() {
					this.element = new Element(ELEMENT_ENUM);
				}
				
				public Enum(Element element) {
					this.element = element;
					this.parseFromElement();
				}
				
				public Enum clone() {
					Enum result = new Enum();
					
					result.setName(this.name);
					result.setValue(this.value);
					
					return result;
				}
				
				private void parseFromElement() {
					this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
					this.value = this.element.getAttributeValue(ATTRIBUTE_VALUE);
				}
				
				public String getName() {
					return this.name;
				}
				
				public String getValue() {
					return this.value;
				}
				
				public void setName(String name) {
					this.name = name;
					this.element.setAttribute(ATTRIBUTE_NAME, name);
				}
				
				public void setValue(String value) {
					this.value = value;
					this.element.setAttribute(ATTRIBUTE_VALUE, value);
				}
				
				public Element getElement() {
					return this.element;
				}
			}
		}
	}
	
	public static void mergeFromTo(BaseAdapter adapter, String from, String to) {
		AttrsFile fromStyles = new AttrsFile(adapter, from);
		AttrsFile toStyles = new AttrsFile(adapter, to);
		toStyles.mergeFrom(fromStyles);
		toStyles.save();
	}
}
