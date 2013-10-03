package com.tactfactory.harmony.template.androidxml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.plateforme.BaseAdapter;


public class StylesFile extends XmlManager {
	private final static String ELEMENT_ROOT = "resources";
	private final static String ELEMENT_STYLE = "style";
	private final static String ELEMENT_ITEM = "item";
	private final static String ATTRIBUTE_NAME = "name";
	private final static String ATTRIBUTE_PARENT = "parent";
	
	protected ArrayList<Style> styles = new ArrayList<Style>();
	
	public StylesFile(BaseAdapter adapter, String styleFilePath) {
		super(adapter, styleFilePath);
		Element root = this.getDocument().getRootElement();
		List<Element> styles = root.getChildren(ELEMENT_STYLE);
		for (Element style : styles) {
			this.styles.add(new Style(style));
		}
		
	}
	
	public void addStyle(Style style) {
		if (getStyle(style.getName()) == null) {
			this.styles.add(style);
			this.getDocument().getRootElement().addContent(style.getElement());
		}
	}
	
	public Style getStyle(String name) {
		Style result = null;
		for (Style style : this.styles) {
			if (style.getName().equals(name)) {
				result = style;
			}
		}
		return result;
	}
	
	public void mergeFrom(StylesFile styleManager) {
		ArrayList<Style> styles = styleManager.styles;
		for (Style style : styles) {
			this.addStyle(style.clone());
		}
	}
	
	public static class Style {
		protected Element element;
		protected String name;
		protected String parent;
		protected HashMap<String, Item> items = new HashMap<String, Item>();
		
		public Style() {
			this.element = new Element(ELEMENT_STYLE);
		}
		
		public Style clone() {
			Style result = new Style();
			result.setName(this.name);
			result.setParent(this.parent);
			
			for(Item item : this.items.values()) {
				result.setItem(item.getName(), item.getValue());
			}
			
			return result;
		}
		
		public Style(Element element) {
			this.element = element;
			this.parseFromElement();
		}
		
		private void parseFromElement() {
			
			this.name = 
					this.element.getAttribute(ATTRIBUTE_NAME).getValue();
			this.parent = 
					this.element.getAttribute(ATTRIBUTE_PARENT).getValue();
			
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
		
		public void setItem(String name, String value) {
			if (!this.items.containsKey(name)) {
				Item item = new Item();
				item.setName(name);
				item.setValue(value);
				this.items.put(item.getName(), item);
				this.element.addContent(item.getElement());
			}
		}
		
		public class Item {
			private Element element;
			private String name;
			private String value;
			
			public Item() {
				this.element = new Element(ELEMENT_ITEM);
			}
			
			public Item(Element element) {
				this.element = element;
				this.parseFromElement();
			}
			
			private void parseFromElement() {
				this.name = this.element.getAttributeValue(ATTRIBUTE_NAME);
				this.value = this.element.getText();
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
				this.element.setText(value);
			}
			
			public Element getElement() {
				return this.element;
			}
		}
	}
	
	public static void mergeFromTo(BaseAdapter adapter, String from, String to) {
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
}
