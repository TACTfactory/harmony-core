/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.sync.template;

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
import com.tactfactory.mda.bundles.rest.template.RestGenerator;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ApplicationGenerator;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.SQLiteAdapterGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

public class SyncGenerator extends BaseGenerator {

	public SyncGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.generateSync();
	}
	
	protected void generateSync() {
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
		
		
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (cm.options.containsKey("sync")) {
				this.addInheritance(cm);
			}
		}
		
		try {
			new ApplicationGenerator(this.adapter).generateApplication();
			new SQLiteAdapterGenerator(this.adapter).generateAll();
			new RestGenerator(this.adapter).generateAll();
			
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
	}
		
	@Override
	protected void makeSource(final String templateName, final String fileName, final boolean override) {
		final String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getService() + "/" + fileName;
		final String fullTemplatePath = this.adapter.getTemplateSourceServicePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	private void addInheritance(final ClassMetadata cm) {
		final String entityName = cm.name;
		final File entityFile = new File(this.adapter.getSourcePath() + this.appMetas.projectNameSpace.replaceAll("\\.", "/") + "/entity/" + entityName + ".java");
		final StringBuffer sb = FileUtils.fileToStringBuffer(entityFile);
		final String extendsString = " extends EntityBase";
		final String classDeclaration = "class " + entityName;
		final int aClassDefinitionIndex = this.indexOf(sb, classDeclaration, false) + classDeclaration.length();
		
		if (cm.extendType != null) { 	// Entity already extends something
			final String extendedClass = cm.extendType;
			if (!extendedClass.equals("EntityBase")) { 				// Extended class is already Entity Base, do nothing
				if (!this.appMetas.entities.containsKey(extendedClass)) { 		// Extended class is not an entity, warn the user
					ConsoleUtils.displayError(new Exception("The entity " + entityName + " must extends a sync Entity or nothing."));
					
				} else {
					
					final ClassMetadata extendedCm = this.appMetas.entities.get(extendedClass); // Get extended entity
					if (!extendedCm.options.containsKey("sync")) {				// Extended class is an entity but which is not syncable, warn the user
						ConsoleUtils.displayError(new Exception("The entity " + entityName + " must extends a sync Entity or nothing."));
					} 
				}
			}
		} else {				// Entity doesn't extend anything
			sb.insert(aClassDefinitionIndex, extendsString);
			if (!cm.imports.contains("EntityBase")) { // Add import EntityBase if it doesn't exist yet
				final int packageIndex = this.indexOf(sb, "package", false);
				final int lineAfterPackageIndex = sb.indexOf("\n", packageIndex) + 1;
				sb.insert(lineAfterPackageIndex, String.format("%nimport %s.base.EntityBase;%n", this.datamodel.get(TagConstant.ENTITY_NAMESPACE)));
				
			}
			FileUtils.stringBufferToFile(sb, entityFile);
		}
	}
	
	private int indexOf(final StringBuffer sb, final String content, final boolean allowComments) {
		return this.indexOf(sb, content, 0, allowComments);
	}
	
	private int indexOf(final StringBuffer sb, final String content, int fromIndex, final boolean allowComments) {
		int index = -1;
		if (allowComments) {
			index = sb.indexOf(content, fromIndex);
		} else {
			int tmpIndex;
			do {
				tmpIndex = sb.indexOf(content, fromIndex);
				final int lastCommentClose = sb.lastIndexOf("*/", tmpIndex);
				final int lastCommentOpen = sb.lastIndexOf("/*", tmpIndex);
				final int lastLineComment = sb.lastIndexOf("//", tmpIndex);
				final int lastCarriotRet = sb.lastIndexOf("\n", tmpIndex);
				if (lastCommentClose >= lastCommentOpen		// If the last multi-line comment is close 
					&& 	lastLineComment  <= lastCarriotRet) { 	// And if there is a carriot return after the last single-line comment
					index = tmpIndex;							// Index is good 
					break;
				} else {
					fromIndex = tmpIndex + 1;
				}
			 } while (tmpIndex != -1);
		}
		
		return index;
	}
	
	/**  Update Android Manifest
	 * 
	 * @param classFile
	 */
	private void addPermissionManifest(final String permissionName) {
		try { 
			final SAXBuilder builder = new SAXBuilder();		// Make engine
			final File xmlFile = FileUtils.makeFile(this.adapter.getManifestPathFile());
			final Document doc = builder.build(xmlFile); 	// Load XML File
			final Element rootNode = doc.getRootElement(); 			// Load Root element
			final Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)
			Element foundPermission = null;
			// Find Permission Node
			final List<Element> permissions = rootNode.getChildren("uses-permission"); 	// Find many elements
			for (final Element permission : permissions) {
				if (permission.getAttributeValue("name", ns).equals(permissionName)) {	// Load attribute value
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
				final XMLOutputter xmlOutput = new XMLOutputter();

				// display nice nice
				xmlOutput.setFormat(Format.getPrettyFormat());				// Make beautiful file with indent !!!
				xmlOutput.output(doc, new FileWriter(xmlFile.getAbsoluteFile()));
			}
		} catch (final JDOMException e) {
			ConsoleUtils.displayError(e);
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		}
	}
}

