package com.tactfactory.harmony.template;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.XMLUtils;

public class ManifestUpdater {
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
	
	private final static Comparator<Element> ABC_COMPARATOR =
			new Comparator<Element>() {
				@Override
				public int compare(final Element o1, final Element o2) {
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			};
	
	private BaseAdapter adapter;
	
	public ManifestUpdater(final BaseAdapter adapter) {
		this.adapter = adapter;
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
				this.adapter.getController(),
				entityName.toLowerCase(Locale.ENGLISH),
				classFile);

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);
		
		// Load XML File
		final Document doc = XMLUtils.openXMLFile(
				this.adapter.getManifestPathFile());

		// Load Root element
		final Element rootNode = doc.getRootElement();

		// Load Name space (required for manipulate attributes)
		final Namespace ns = rootNode.getNamespace("android");

		// Find Application Node
		Element findActivity = null;

		// Find a element
		final Element applicationNode = 
				rootNode.getChild(ELEMENT_APPLICATION);
		if (applicationNode != null) {

			// Find Activity Node
			final List<Element> activities =
					applicationNode.getChildren(ELEMENT_ACTIVITY);

			// Find many elements
			for (final Element activity : activities) {
				// Load attribute value
				if (activity.hasAttributes()
						&& activity.getAttributeValue(ATTRIBUTE_NAME, ns)
							.equals(pathRelatif)) {
					findActivity = activity;
					break;
				}
			}

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

		// Write XML to file.
		XMLUtils.writeXMLToFile(doc, this.adapter.getManifestPathFile());
	}
	
	public List<Element> getActivities(Document xmlDocument) {
		List<Element> result = null;
		Element appNode = 
				xmlDocument.getRootElement().getChild(ELEMENT_APPLICATION);
		result = appNode.getChildren(ELEMENT_ACTIVITY);
		return result;
	}
}

