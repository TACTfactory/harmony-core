package com.tactfactory.mda.bundles.search.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.TranslationMetadata;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ActivityGenerator;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.template.TranslationGenerator;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

public class SearchGenerator  extends BaseGenerator {

	public SearchGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		// TODO Auto-generated constructor stub
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(isClassSearchable(cm)){
				this.generateActivity(cm);
			}
		}
	}
	
	private void generateActivity(ClassMetadata cm){
		this.makeSource(cm, "TemplateSearchActivity.java",cm.name+"SearchActivity.java",false);
		this.makeSource(cm, "TemplateSearchFragment.java",cm.name+"SearchFragment.java",false);
		
		this.makeLayout(cm, "activity_template_search.xml","activity_"+cm.name.toLowerCase()+"_search.xml",false);
		this.makeLayout(cm, "fragment_template_search.xml","fragment_"+cm.name.toLowerCase()+"_search.xml",false);
		
		TranslationMetadata.addDefaultTranslation("common_search", "Search", Group.COMMON);
		
		this.updateManifest("SearchActivity", cm.name);
		
		this.generateMenu();
		try {
			new TranslationGenerator(this.adapter).generateStringsXml();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void generateMenu(){
		this.makeMenu(true);
	}
	
	private boolean isClassSearchable(ClassMetadata cm){
		for(FieldMetadata fm : cm.fields.values()){
			if(fm.options.containsKey("search")){
				return true;
			}
		}
		return false;
	}

	protected void makeSource(ClassMetadata cm, String templateName, String fileName, boolean override) {
		this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
		String fullFilePath = this.adapter.getSourcePath() + PackageUtils.extractPath(this.adapter.getNameSpace(cm, this.adapter.getController())+"."+cm.getName().toLowerCase())+ "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceControlerPath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	protected void makeMenu(boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + "menu" + "/" + "SearchMenuWrapper.java";
		String fullTemplatePath = this.adapter.getTemplateSourcePath()+"menu/SearchMenuWrapper.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	protected void makeLayout(ClassMetadata cm, String templateName, String fileName, boolean override) {
		this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
		String fullFilePath = String.format("%s/%s", 
				this.adapter.getRessourceLayoutPath(),
				fileName);
		String fullTemplatePath = String.format("%s/%s",
				this.adapter.getTemplateRessourceLayoutPath().substring(1),
				templateName);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	/**  Update Android Manifest
	 * 
	 * @param classFile
	 */
	private void updateManifest(String classFile, String entityName) {
		classFile = entityName + classFile;
		String pathRelatif = String.format(".%s.%s.%s",
				this.adapter.getController(), 
				entityName.toLowerCase(), 
				classFile );

		// Debug Log
		ConsoleUtils.displayDebug("Update Manifest : " + pathRelatif);

		try {
			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			final Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

			// Find Application Node
			Element findActivity = null;
			Element applicationNode = rootNode.getChild("application"); 	// Find a element
			if (applicationNode != null) {

				// Find Activity Node
				List<Element> activities = applicationNode.getChildren("activity"); 	// Find many elements
				for (Element activity : activities) {
					if (activity.hasAttributes() && activity.getAttributeValue("name",ns).equals(pathRelatif) ) {	// Load attribute value
						findActivity = activity;
						break;
					}
				}

				// If not found Node, create it
				if (findActivity == null) {
					findActivity = new Element("activity");				// Create new element
					findActivity.setAttribute("name", pathRelatif, ns);	// Add Attributes to element
					Element findFilter = new Element("intent-filter");
					Element findAction = new Element("action");
					Element findCategory = new Element("category");
					findFilter.addContent(findAction);					// Add Child element
					findFilter.addContent(findCategory);
					findActivity.addContent(findFilter);
					applicationNode.addContent(findActivity);
				}

				// Set values
				findActivity.setAttribute("label", "@string/app_name", ns);
				findActivity.setAttribute("exported", "false", ns);
				Element filterActivity = findActivity.getChild("intent-filter");
				if (filterActivity != null) {
					String data = "";
					String action = "SEARCH";
					
					data += this.appMetas.projectNameSpace.replace('/', '.') + "." + entityName;
					filterActivity.getChild("action").setAttribute("name", "android.intent.action."+ action, ns);
					filterActivity.getChild("category").setAttribute("name", "android.intent.category.DEFAULT", ns);
				}
				
				// Clean code
				applicationNode.sortChildren(new Comparator<Element>() {
					@Override
					public int compare(Element o1, Element o2) {
						return (o1.getName().compareToIgnoreCase(o2.getName()));
					}
				});
			}

			// Write to File
			XMLOutputter xmlOutput = new XMLOutputter();

			// display nice nice
			xmlOutput.setFormat(Format.getPrettyFormat());				// Make beautiful file with indent !!!
			xmlOutput.output(doc, new FileWriter(xmlFile.getAbsoluteFile()));
		} catch (IOException io) {
			io.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
	}
}
