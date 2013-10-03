package com.tactfactory.harmony.template.androidxml;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

public class ManifestUpdater extends XmlManager {
	private static final String ELEMENT_APPLICATION = "application";
	private static final String ELEMENT_ACTIVITY = "activity";
	private static final String ELEMENT_INTENT_FILTER = "intent-filter";
	private static final String ELEMENT_ACTION = "action";
	private static final String ELEMENT_CATEGORY = "category";
	private static final String ELEMENT_DATA = "data";
	
	private static final String ATTRIBUTE_NAME = "name";
	private static final String ATTRIBUTE_LABEL = "label";
	private static final String ATTRIBUTE_EXPORTED = "exported";
	private static final String ATTRIBUTE_MIME_TYPE = "mimeType";
	
	private static final String ACTION_VIEW = "VIEW";
	private static final String ACTION_EDIT = "EDIT";
	private static final String ACTION_INSERT = "INSERT";
	
	private static final String NAMESPACE_ANDROID = "android";
	
	private final static Comparator<Element> ABC_COMPARATOR =
			new Comparator<Element>() {
				@Override
				public int compare(final Element o1, final Element o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			};
	
	public ManifestUpdater(final BaseAdapter adapter) {
		super(adapter, adapter.getManifestPathFile());
	}
	/**
	 * Update Android Manifest.
	 * @param classF The class file name
	 * @param entityName the entity for which to update the manifest for.
	 */
	public void addActivity(
			final String projectNamespace,
			final String classF,
			final String entityName) {
		
		final String classFile = entityName + classF;
		final String pathRelatif = String.format(".%s.%s.%s",
				this.getAdapter().getController(),
				entityName.toLowerCase(Locale.ENGLISH),
				classFile);

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);
		

		// Load Root element
		final Element rootNode = this.getDocument().getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace(NAMESPACE_ANDROID);

		// Find Application Node
		Element findActivity = null;

		// Find a element
		final Element applicationNode = 
				rootNode.getChild(ELEMENT_APPLICATION);
		if (applicationNode != null) {
			
			findActivity = this.findActivityNamed(pathRelatif, ns);

			// If not found Node, create it
			if (findActivity == null) {
				// Create new element
				findActivity = new Element(ELEMENT_ACTIVITY);

				// Add Attributes to element
				findActivity.setAttribute(ATTRIBUTE_NAME, pathRelatif, ns);
				final Element findFilter = 
						new Element(ELEMENT_INTENT_FILTER);
				final Element findAction = new Element(ELEMENT_ACTION);
				final Element findCategory = new Element(ELEMENT_CATEGORY);
				final Element findData = new Element(ELEMENT_DATA);

				// Add Child element
				findFilter.addContent(findAction);
				findFilter.addContent(findCategory);
				findFilter.addContent(findData);
				findActivity.addContent(findFilter);
				applicationNode.addContent(findActivity);
			}

			// Set values
			findActivity.setAttribute(
					ATTRIBUTE_LABEL,
					"@string/app_name",
					ns);
			
			findActivity.setAttribute(
					ATTRIBUTE_EXPORTED,
					"false",
					ns);
			
			final Element filterActivity =
					findActivity.getChild(ELEMENT_INTENT_FILTER);
			if (filterActivity != null) {
				final StringBuffer data = new StringBuffer();
				String action = ACTION_VIEW;

				if (pathRelatif.matches(".*List.*")) {
					data.append("vnd.android.cursor.collection/");
				} else {
					data.append("vnd.android.cursor.item/");

					if (pathRelatif.matches(".*Edit.*")) {
						action = ACTION_EDIT;
					} else

					if (pathRelatif.matches(".*Create.*")) {
						action = ACTION_INSERT;
					}
				}


				data.append(projectNamespace.replace('/', '.'));
				data.append('.');
				data.append(entityName);

				filterActivity.getChild(ELEMENT_ACTION).setAttribute(
						ATTRIBUTE_NAME,
						"android.intent.action." + action,
						ns);
				
				filterActivity.getChild(ELEMENT_CATEGORY).setAttribute(
						ATTRIBUTE_NAME,
						"android.intent.category.DEFAULT",
						ns);
				
				filterActivity.getChild(ELEMENT_DATA).setAttribute(
						ATTRIBUTE_MIME_TYPE,
						data.toString(),
						ns);
			}

			// Clean code
			applicationNode.sortChildren(ABC_COMPARATOR);
		}
	}
	
	private List<Element> getActivities() {
		List<Element> result = null;
		Element appNode = 
				this.getDocument().getRootElement().getChild(ELEMENT_APPLICATION);
		result = appNode.getChildren(ELEMENT_ACTIVITY);
		return result;
	}
	
	private Element findActivityNamed(
			String name,
			Namespace namespace) {
		Element result = null;
		List<Element> activities = this.getActivities();
		
		for (final Element activity : activities) {
			// Load attribute value
			if (activity.hasAttributes()
					&& activity.getAttributeValue(ATTRIBUTE_NAME, namespace)
						.equals(name)) {
				result = activity;
				break;
			}
		}
		
		return result;
	}
	@Override
	protected Element getDefaultRoot() {
		Namespace androidNs = Namespace.getNamespace(
				"android", 
				"http://schemas.android.com/apk/res/android"); 
		Element rootElement = new Element("manifest");
		rootElement.addNamespaceDeclaration(androidNs);
		rootElement.setAttribute(
				"package",
				ApplicationMetadata.INSTANCE.getProjectNameSpace());
		
		rootElement.setAttribute("versionCode",
				"1",
				androidNs);
		
		rootElement.setAttribute("versionName",
				"@string/app_version",
				androidNs);
		return rootElement;
	}	
}

