package com.tactfactory.harmony.template.androidxml.manifest;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class ManifestIntentFilter extends ManifestElement {
	private String icon;
    private String label;
    private Integer priority;
    private String actionName;
    private String categoryName;
    private List<ManifestData> data;
    
    public ManifestIntentFilter() {
    	this.data = new ArrayList<ManifestIntentFilter.ManifestData>();
    }
    
    @Override
	public Element toElement(Namespace ns) {
		Element result = new Element(ELEMENT_INTENT_FILTER);
		
		this.addAttribute(
				result, ns, ATTRIBUTE_LABEL, this.label);
		
		if (this.actionName != null) {
			Element actionElement = new Element(ELEMENT_ACTION);
			this.addAttribute(
					actionElement, ns, ATTRIBUTE_NAME, this.actionName);
			result.addContent(actionElement);
		}
		
		if (this.categoryName != null) {
			Element categoryElement = new Element(ELEMENT_CATEGORY);
			this.addAttribute(
					categoryElement, ns, ATTRIBUTE_NAME, this.categoryName);
			result.addContent(categoryElement);
		}
		
		if (this.data != null) {
			for (ManifestData data : this.data) {
				result.addContent(data.toElement(ns));
			}
		}
		
		return result;
	}
    
	/**
	 * @return the icon
	 */
	public final String getIcon() {
		return icon;
	}
	
	/**
	 * @return the label
	 */
	public final String getLabel() {
		return label;
	}
	
	/**
	 * @return the priority
	 */
	public final Integer getPriority() {
		return priority;
	}
	
	/**
	 * @return the actionName
	 */
	public final String getActionName() {
		return actionName;
	}
	
	/**
	 * @return the categoryName
	 */
	public final String getCategoryName() {
		return categoryName;
	}
	
	/**
	 * @param icon the icon to set
	 */
	public final void setIcon(String icon) {
		this.icon = icon;
	}
	
	/**
	 * @param label the label to set
	 */
	public final void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * @param priority the priority to set
	 */
	public final void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	/**
	 * @param actionName the actionName to set
	 */
	public final void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	/**
	 * @param categoryName the categoryName to set
	 */
	public final void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	/**
	 * @return the data
	 */
	public final List<ManifestData> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(List<ManifestData> data) {
		this.data = data;
	}
	
	public final void addData(ManifestData data) {
		this.data.add(data);
	}

	public class ManifestData extends ManifestElement {
		private String scheme;
		private String host;
		private String port;
		private String path;
		private String pathPattern;
		private String pathPrefix;
		private String mimeType;
		
		@Override
		public Element toElement(Namespace ns) {
			Element element = new Element(ELEMENT_DATA);
			
			this.addAttribute(
					element, ns, ATTRIBUTE_MIME_TYPE, this.mimeType);
			return element;
		}

		/**
		 * @return the scheme
		 */
		public final String getScheme() {
			return scheme;
		}

		/**
		 * @return the host
		 */
		public final String getHost() {
			return host;
		}

		/**
		 * @return the port
		 */
		public final String getPort() {
			return port;
		}

		/**
		 * @return the path
		 */
		public final String getPath() {
			return path;
		}

		/**
		 * @return the pathPattern
		 */
		public final String getPathPattern() {
			return pathPattern;
		}

		/**
		 * @return the pathPrefix
		 */
		public final String getPathPrefix() {
			return pathPrefix;
		}

		/**
		 * @return the mimeType
		 */
		public final String getMimeType() {
			return mimeType;
		}

		/**
		 * @param scheme the scheme to set
		 */
		public final void setScheme(String scheme) {
			this.scheme = scheme;
		}

		/**
		 * @param host the host to set
		 */
		public final void setHost(String host) {
			this.host = host;
		}

		/**
		 * @param port the port to set
		 */
		public final void setPort(String port) {
			this.port = port;
		}

		/**
		 * @param path the path to set
		 */
		public final void setPath(String path) {
			this.path = path;
		}

		/**
		 * @param pathPattern the pathPattern to set
		 */
		public final void setPathPattern(String pathPattern) {
			this.pathPattern = pathPattern;
		}

		/**
		 * @param pathPrefix the pathPrefix to set
		 */
		public final void setPathPrefix(String pathPrefix) {
			this.pathPrefix = pathPrefix;
		}

		/**
		 * @param mimeType the mimeType to set
		 */
		public final void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}
	}
}
