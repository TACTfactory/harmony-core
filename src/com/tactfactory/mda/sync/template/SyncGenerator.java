/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.sync.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.rest.template.RestGenerator;
import com.tactfactory.mda.template.ApplicationGenerator;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.EntityGenerator;
import com.tactfactory.mda.template.SQLiteAdapterGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;

public class SyncGenerator extends BaseGenerator {

	public SyncGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.generateSync();
	}
	
	protected void generateSync(){
		// Add internet permission to manifest :
		this.addPermissionManifest("android.permission.INTERNET");
		// EntityBase.java
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace.replaceAll("\\.", "/") + "/entity/base/EntityBase.java";
		String fullTemplatePath = this.adapter.getTemplateSourceEntityBasePath().substring(1) + "EntityBase.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		// TemplateSyncService.java
		this.makeSource(
				"TemplateSyncService.java", 
				String.format("%sSyncService.java", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name)),
				true);
		
		// TemplateSyncThread.java
		this.makeSource(
				"TemplateSyncThread.java", 
				String.format("%sSyncThread.java", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name)),
				true);
		
		// SyncClientAdapterBase
		fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getData() + "/" + "base/SyncClientAdapterBase.java";
		fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + "base/SyncClientAdapterBase.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getData() + "/" + "base/SyncSQLiteAdapterBase.java";
		fullTemplatePath = this.adapter.getTemplateSourceProviderPath().substring(1) + "base/SyncSQLiteAdapterBase.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("sync")){
				this.addInheritance(cm);
			}
		}
		
		try {
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteAdapterGenerator(this.adapter).generateAll();
			new RestGenerator(this.adapter).generateAll();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getService() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceServicePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	private void addInheritance(ClassMetadata cm){
		String entityName = cm.name;
		File entityFile = new File(this.adapter.getSourcePath() + this.appMetas.projectNameSpace.replaceAll("\\.", "/") + "/entity/" + entityName +".java");
		StringBuffer sb = FileUtils.FileToStringBuffer(entityFile);
		String extendsString = " extends EntityBase";
		String classDeclaration = "class "+entityName;
		int aClassDefinitionIndex = this.indexOf(sb, classDeclaration, false)+classDeclaration.length();
		
		if(cm.extendType!=null){ 	// Entity already extends something
			String extendedClass = cm.extendType;
			if(!extendedClass.equals("EntityBase")){ 				// Extended class is already Entity Base, do nothing
				if(!this.appMetas.entities.containsKey(extendedClass)){ 		// Extended class is not an entity, warn the user
					ConsoleUtils.displayError(new Exception("The entity "+entityName+" must extends a sync Entity or nothing."));
					
				} else {
					
					ClassMetadata extendedCm = this.appMetas.entities.get(extendedClass); // Get extended entity
					if(!extendedCm.options.containsKey("sync")){				// Extended class is an entity but which is not syncable, warn the user
						ConsoleUtils.displayError(new Exception("The entity "+entityName+" must extends a sync Entity or nothing."));
					} 
				}
			}
		} else {				// Entity doesn't extend anything
			sb.insert(aClassDefinitionIndex, extendsString);
			if(!cm.imports.contains("EntityBase")){ // Add import EntityBase if it doesn't exist yet
				int packageIndex = this.indexOf(sb, "package", false);
				int lineAfterPackageIndex = sb.indexOf("\n", packageIndex)+1;
				sb.insert(lineAfterPackageIndex, String.format("\nimport %s.base.EntityBase;\n",this.datamodel.get(TagConstant.ENTITY_NAMESPACE)));
				
			}
			FileUtils.StringBufferToFile(sb, entityFile);
		}
	}
	
	private int indexOf(StringBuffer sb, String content, boolean allowComments){
		return this.indexOf(sb, content, 0, allowComments);
	}
	
	private int indexOf(StringBuffer sb, String content, int fromIndex, boolean allowComments){
		int index = -1;
		if(allowComments){
			index = sb.indexOf(content, fromIndex);
		}else{
			int tmpIndex;
			do{
				tmpIndex = sb.indexOf(content, fromIndex);
				int lastCommentClose = sb.lastIndexOf("*/", tmpIndex);
				int lastCommentOpen = sb.lastIndexOf("/*", tmpIndex);
				int lastLineComment = sb.lastIndexOf("//", tmpIndex);
				int lastCarriotRet = sb.lastIndexOf("\n", tmpIndex);
				if(		(lastCommentClose >= lastCommentOpen)	// If the last multi-line comment is close 
					&& 	(lastLineComment  <= lastCarriotRet)){ 	// And if there is a carriot return after the last single-line comment
					index = tmpIndex;							// Index is good 
					break;
				} else{
					fromIndex= tmpIndex+1;
				}
			}while(tmpIndex!=-1);
		}
		
		return index;
	}
	
	/**  Update Android Manifest
	 * 
	 * @param classFile
	 */
	private void addPermissionManifest(String permissionName) {
		try{ 
			SAXBuilder builder = new SAXBuilder();		// Make engine
			File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			Document doc = (Document) builder.build(xmlFile); 	// Load XML File
			final Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)
			Element foundPermission = null;
			// Find Permission Node
			List<Element> permissions = rootNode.getChildren("uses-permission"); 	// Find many elements
			for (Element permission : permissions) {
				if (permission.getAttributeValue("name",ns).equals(permissionName) ) {	// Load attribute value
					foundPermission = permission;
					break;
				}
			}
			
			// If not found Node, create it
			if (foundPermission == null) {
				foundPermission = new Element("uses-permission");				// Create new element
				foundPermission.setAttribute("name", permissionName, ns);	// Add Attributes to element
				rootNode.addContent(foundPermission);
				
				// Write to File
				XMLOutputter xmlOutput = new XMLOutputter();

				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());				// Make beautiful file with indent !!!
				xmlOutput.output(doc, new FileWriter(xmlFile.getAbsoluteFile()));
			}
		} catch(JDOMException e){
			ConsoleUtils.displayError(e);
		} catch(IOException e){
			ConsoleUtils.displayError(e);
		}
	}
}

