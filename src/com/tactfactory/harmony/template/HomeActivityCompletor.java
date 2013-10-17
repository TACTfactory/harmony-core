package com.tactfactory.harmony.template;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.utils.XMLUtils;

/**
 * Utility class used to add buttons to the home activity. 
 */
public abstract class HomeActivityCompletor {
	/** Init buttons method declaration. */
	private static final String INIT_BUTTON_DECL = 
			"private void initButtons() {";
	/** On Click method declaration. */
	private static final String ON_CLICK_DECL =
			"public void onClick(View v) {";
	/** Template for listener adding. */
	private static final String ADD_LISTENER_TEMPLATE =
			"\t\tthis.findViewById(R.id.%s).setOnClickListener(this);";
	/** Template for action case adding. */
	private static final String CASE_TEMPLATE = 
			"\t\t\tcase R.id.%s:\n"
			+ "\t\t\t\tintent = new Intent(this, %s.class);\n"
			+ "\t\t\t\tbreak;\n";
	/**
	 * Update Class : HomeActivity.
	 * HomeActivity : add new Map button to Entity activity
	 * @param generator BaseGenerator
	 * @param activity Activity without Entity name (GMapsActivity, OSMActivity)
	 * @param entity Entity to add
	 */
	public static final void addLaunchActivityButton(
			final BaseGenerator generator,
			final String activity,
			final String buttonId) {
		
		// Update HomeActivity
		File file = new File(generator.getAdapter().getHomeActivityPathFile());
		if (file != null && file.isFile()) {
			String strFile = TactFileUtils.fileToString(file);
			
			String activityName = activity.substring(
					activity.lastIndexOf('.') + 1);
			
			String listenerTpl = 
					String.format(ADD_LISTENER_TEMPLATE, buttonId);
			
			String caseTpl = 
					String.format(CASE_TEMPLATE, buttonId, activityName);
			
			// Import Activity
			if (!strFile.contains(
					"import " + activity + ";")) {
				strFile = strFile.replaceFirst("import",
						"import " + activity + ";"
								+ "\n\nimport");
			}
			
			addButtonToMainXML(generator, activityName, buttonId);
			
			// If Listener not set 
			if (!strFile.contains(listenerTpl)) {
				
				if (strFile.contains(INIT_BUTTON_DECL)) {
					strFile = strFile.replace(INIT_BUTTON_DECL, INIT_BUTTON_DECL
							+ "\n"
							+ listenerTpl);
				} else {
					int lastBracketIndex = strFile.lastIndexOf('}');
					strFile = insertAtPosition(strFile,
							listenerTpl,
							lastBracketIndex);
				}
			}
			
			// If case not set 
			if (!strFile.contains(caseTpl)) {
				
				if (strFile.contains(ON_CLICK_DECL)) {
					final String switchStart = "switch (v.getId()) {"; 
					strFile = strFile.replace(switchStart, switchStart
							+ "\n"
							+ caseTpl);
				} else {
					int lastBracketIndex = strFile.lastIndexOf('}');
					strFile = insertAtPosition(strFile,
							caseTpl,
							lastBracketIndex);
				}
			}
						
			try {
				TactFileUtils.writeStringToFile(file, strFile);
			} catch (IOException e) {
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/**
	 * Insert the given string into another string at given position.
	 * @param initialString The initial string
	 * @param insert The string to insert
	 * @param position The position where to insert
	 * @return The new forged string
	 */
	private static String insertAtPosition(
			String initialString,
			String insert,
			int position) {
		
		String result = null;

		String begin = initialString.substring(0, position);
		String end = initialString.substring(position);
		result = begin + insert + end;
		
		return result;
	}
	
	/**
	 * Add a button to main.xml.
	 * @param generator The generator
	 * @param text The displayed text
	 * @param buttonId The button id
	 */
	private static void addButtonToMainXML(final BaseGenerator generator,
			final String text,
			final String buttonId) {
		String xmlFileName = generator.getAdapter().getRessourceLayoutPath() 
				+ "main.xml";
		Document doc = XMLUtils.openXML(xmlFileName);
		Namespace androidNs = doc.getRootElement().getNamespace("android");
		Element linearL = doc.getRootElement().getChild("LinearLayout");
		
		boolean alreadyExists = false;
		
		for (Element element : linearL.getChildren("Button")) {
			if (element.getAttributeValue("id", androidNs)
					.equals("@+id/" + buttonId)) {
				alreadyExists = true;
			}
		}
			
		if (!alreadyExists) {
			Element newButton = new Element("Button");
			newButton.setAttribute("id", "@+id/" + buttonId, androidNs);
			newButton.setAttribute("layout_width", "match_parent", androidNs);
			newButton.setAttribute("layout_height", "wrap_content", androidNs);
			newButton.setAttribute("text", text, androidNs);
			
			linearL.addContent(newButton);
		}
		XMLUtils.writeXMLToFile(doc, xmlFileName);
	}
}
