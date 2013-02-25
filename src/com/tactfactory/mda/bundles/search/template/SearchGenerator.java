/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */ 
package com.tactfactory.mda.bundles.search.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.template.TranslationGenerator;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * Generator for Search Bundle.
 *
 */
public class SearchGenerator  extends BaseGenerator {
	/** Bundle name. */
	private static final String NAME = "name";

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception 
	 */
	public SearchGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Generate all activities.
	 */
	public final void generateAll() {
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (this.isClassSearchable(cm)) {
				this.generateActivity(cm);
			}
		}
	}
	
	/**
	 * Generate activity for given class.
	 * @param cm The class to generate the activity for.
	 */
	private void generateActivity(final ClassMetadata cm) {
		this.makeSource(cm, 
				"TemplateSearchActivity.java", 
				cm.getName() + "SearchActivity.java", 
				false);
		this.makeSource(cm, 
				"TemplateSearchFragment.java",
				cm.getName() + "SearchFragment.java", 
				false);
		
		this.makeLayout(cm, 
				"activity_template_search.xml",
				"activity_" + cm.getName().toLowerCase() + "_search.xml",
				false);
		this.makeLayout(cm, 
				"fragment_template_search.xml", 
				"fragment_" + cm.getName().toLowerCase() + "_search.xml",
				false);
		
		TranslationMetadata.addDefaultTranslation(
				"common_search", "Search", Group.COMMON);
		
		this.updateManifest("SearchActivity", cm.getName());
		
		this.generateMenu();
		try {
			new TranslationGenerator(this.getAdapter()).generateStringsXml();
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate the bundle menu. 
	 */
	private void generateMenu() {
		this.makeMenu(true);
	}
	
	/**
	 * Detect whether the class has a Searchable field or not.
	 * @param cm The class
	 * @return True if the class has a searchable field
	 */
	private boolean isClassSearchable(final ClassMetadata cm) {
		boolean isSearchable = false;
		for (final FieldMetadata fm : cm.getFields().values()) {
			if (fm.getOptions().containsKey("search")) {
				isSearchable = true;
			}
		}
		return isSearchable;
	}

	/**
	 * Make activity/fragment for given entity.
	 * @param cm The entity
	 * @param templateName The template name
	 * @param fileName The destination file name
	 * @param override True if overwrite the file
	 */
	protected final void makeSource(final ClassMetadata cm,
			final String templateName,
			final String fileName, 
			final boolean override) {
		
		this.getDatamodel().put(TagConstant.CURRENT_ENTITY, cm.getName());
		final String fullFilePath = 
				this.getAdapter().getSourcePath()
				+ PackageUtils.extractPath(this.getAdapter().getNameSpace(
						cm, 
						this.getAdapter().getController())
				+ "." 
				+ cm.getName().toLowerCase())
				+ "/" 
				+ fileName;
		final String fullTemplatePath = 
				this.getAdapter().getTemplateSourceControlerPath().substring(1) 
				+ templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**
	 * Build the Search Menu.
	 * @param override True if overwrite existing search menu.
	 */
	protected final void makeMenu(final boolean override) {
		final String fullFilePath = 
				this.getAdapter().getSourcePath()
				+ this.getAppMetas().getProjectNameSpace() 
				+ "/" + "menu" + "/"
				+ "SearchMenuWrapper.java";
		
		final String fullTemplatePath =
				this.getAdapter().getTemplateSourcePath()
				+ "menu/SearchMenuWrapper.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**
	 * Make search activity/fragment layout.
	 * @param cm The class to generate the layout for.
	 * @param templateName The template name
	 * @param fileName The destination file name
	 * @param override True if overwrite file.
	 */
	protected final void makeLayout(final ClassMetadata cm, 
			final String templateName, 
			final String fileName,
			final boolean override) {
		this.getDatamodel().put(TagConstant.CURRENT_ENTITY, cm.getName());
		final String fullFilePath = String.format("%s/%s", 
				this.getAdapter().getRessourceLayoutPath(),
				fileName);
		final String fullTemplatePath = String.format("%s/%s",
				this.getAdapter().getTemplateRessourceLayoutPath().substring(1),
				templateName);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**  
	 * Update Android Manifest.
	 * 
	 * @param classFile The class file.
	 * @param entityName The entity name.
	 */
	private void updateManifest(final String classFile,
			final String entityName) {
		String realClassFile = entityName + classFile;
		final String pathRelatif = String.format(".%s.%s.%s",
				this.getAdapter().getController(), 
				entityName.toLowerCase(), 
				realClassFile);

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);

		try {
			final SAXBuilder builder = new SAXBuilder();		// Make engine
			final File xmlFile =
					FileUtils.makeFile(this.getAdapter().getManifestPathFile());
			final Document doc = builder.build(xmlFile); 	// Load XML File
			// Load Root element
			final Element rootNode = doc.getRootElement(); 			
			// Load Name space (required for manipulate attributes)
			final Namespace ns = rootNode.getNamespace("android");	

			// Find Application Node
			Element findActivity = null;
			final Element applicationNode = rootNode.getChild("application"); 	
			// Find a element
			if (applicationNode != null) {

				// Find Activity Node
				final List<Element> activities = 
						applicationNode.getChildren("activity"); 	
				// Find many elements
				for (final Element activity : activities) {
					if (activity.hasAttributes() 
							&& activity.getAttributeValue(NAME, ns)
								.equals(pathRelatif)) {	
						// Load attribute value
						findActivity = activity;
						break;
					}
				}

				// If not found Node, create it
				if (findActivity == null) {
					// Create new element
					findActivity = new Element("activity");				
					// Add Attributes to element
					findActivity.setAttribute(NAME, pathRelatif, ns);	
					final Element findFilter = new Element("intent-filter");
					final Element findAction = new Element("action");
					final Element findCategory = new Element("category");
					// Add Child element
					findFilter.addContent(findAction);					
					findFilter.addContent(findCategory);
					findActivity.addContent(findFilter);
					applicationNode.addContent(findActivity);
				}

				// Set values
				findActivity.setAttribute("label", "@string/app_name", ns);
				findActivity.setAttribute("exported", "false", ns);
				final Element filterActivity =
						findActivity.getChild("intent-filter");
				if (filterActivity != null) {
					final String action = "SEARCH";
					
					filterActivity.getChild("action").setAttribute(
							NAME,
							"android.intent.action." + action, 
							ns);
					filterActivity.getChild("category").setAttribute(
							NAME, 
							"android.intent.category.DEFAULT",
							ns);
				}
				
				// Clean code
				applicationNode.sortChildren(new Comparator<Element>() {
					@Override
					public int compare(final Element o1, final Element o2) {
						return o1.getName().compareToIgnoreCase(o2.getName());
					}
				});
			}

			// Write to File
			final XMLOutputter xmlOutput = new XMLOutputter();

			// Make beautiful file with indent !!!
			xmlOutput.setFormat(Format.getPrettyFormat());				
			xmlOutput.output(doc, new OutputStreamWriter(
					new FileOutputStream(xmlFile.getAbsoluteFile()), 
					FileUtils.DEFAULT_ENCODING));
		} catch (final IOException io) {
			ConsoleUtils.displayError(io);
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		}
	}
}
