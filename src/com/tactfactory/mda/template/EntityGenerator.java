/** 
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.EntityMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.MethodMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Entity Generator.
 * Used to decorate or generate entities.
 */
public class EntityGenerator extends BaseGenerator {
	/** remove HARD CODED String. */
	private String getterTemplate = "itemGetter.java";
	
	/** remove HARD CODED String. */
	private String setterTemplate = "itemSetter.java";
	
	/** Entities folder. */
	private String entityFolder;
	
	/** Constructor.
	 * @param adapter Adapter used by this generator
	 * @throws Exception 
	 */
	public EntityGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.entityFolder = 
				this.getAdapter().getSourcePath() 
				+ this.getAppMetas()
					.getProjectNameSpace().replaceAll("\\.", "/") 
				+ "/entity/";
	}
	
	/**
	 * Implements serializable 
	 * and add necessary getters and setters for all classes. 
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Decorate entities...");
		
		for (final EntityMetadata cm 
				: this.getAppMetas().getEntities().values()) { 
			final String filepath = String.format("%s/%s",
					this.entityFolder,
					String.format("%s.java", cm.getName()));
			
			ConsoleUtils.display(">>> Decorate " + cm.getName());
			
			final File entityFile = TactFileUtils.getFile(filepath);
			if (entityFile.exists()) {
				// Load the file once in a String buffer
				final StringBuffer fileString = 
						TactFileUtils.fileToStringBuffer(entityFile); 
				
				this.addImplementsSerializable(fileString, cm);
				this.addImportSerializable(fileString, cm);
				this.generateGetterAndSetters(fileString, cm);
				
				 // After treatment on entity, write it in the original file
				TactFileUtils.stringBufferToFile(fileString, entityFile);
			}
		}
	}
	
	
	/**
	 * Implements serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	protected final void addImplementsSerializable(
			final StringBuffer fileString,
			final EntityMetadata cm) {
		if (!this.alreadyImplementsSerializable(cm)) {
			ConsoleUtils.displayDebug("Add serializable implement");
			final int firstAccolade = fileString.indexOf(" {");
			
			// Class already implements an interface which is not Serializable
			if (cm.getImplementTypes().size() > 0) { 
				fileString.insert(firstAccolade, ", Serializable");
			} else {
				fileString.insert(firstAccolade, " implements Serializable");
			}				
		}
	}
	
	/**
	 * Import serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	protected final void addImportSerializable(final StringBuffer fileString,
			final ClassMetadata cm) {
		if (!this.alreadyImportsSerializable(cm)) {
			ConsoleUtils.displayDebug("Add serializable import");
			int insertPos;
			
			if (cm.getImports().size() > 0) { 
				insertPos = fileString.indexOf("import");
			} else {
				insertPos = fileString.indexOf(";") + 1;
			}
			
			fileString.insert(insertPos, "\rimport java.io.Serializable;\r");
		}
	}
	
	
	/**
	 * Generate the necessary getters and setters for the class.
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	protected final void generateGetterAndSetters(final StringBuffer fileString,
			final ClassMetadata cm) {
		final Collection<FieldMetadata> fields = cm.getFields().values();
		
		for (final FieldMetadata f : fields) {
			if (!f.isInternal()) {
				// Getter
				if (!this.alreadyImplementsGet(f, cm)) { 
					ConsoleUtils.displayDebug("Add implements getter of " 
							+ f.getName() 
							+ " => get" 
							+ CaseFormat.LOWER_CAMEL.to(
									CaseFormat.UPPER_CAMEL,
									f.getName()));
					
					this.generateMethod(fileString, f, this.getterTemplate);
				}
				
				// Setter
				if (!this.alreadyImplementsSet(f, cm)) {
					ConsoleUtils.displayDebug("Add implements setter of " 
							+ f.getName() 
							+ " => set" 
							+ CaseFormat.LOWER_CAMEL.to(
									CaseFormat.UPPER_CAMEL,
									f.getName()));
					
					this.generateMethod(fileString, f, this.setterTemplate);
				}
			}
		}
	}
	
	
	/**
	 * Generate a get or set method following the given template.
	 * @param fileString The stringbuffer containing the class java code
	 * @param f The concerned field
	 * @param templateName The template file name
	 */
	protected final void generateMethod(final StringBuffer fileString, 
			final FieldMetadata f, 
			final String templateName) {
		final int lastAccolade = fileString.lastIndexOf("}");
		
		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("property", f.getName());
		map.put("property_type", this.getAdapter().getNativeType(f.getType()));
		
		try {
			final StringWriter writer = new StringWriter();
			
			final Template tpl = this.getCfg().getTemplate(
					String.format("%s%s",
							this.getAdapter().getTemplateSourceCommonPath(),
							templateName + ".ftl"));
			// Load template file in engine
			
			tpl.process(map, writer);
			final StringBuffer getString = writer.getBuffer();
			fileString.insert(lastAccolade, getString + "\n\n");
			
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		} catch (final TemplateException e) {
			ConsoleUtils.displayError(e);
		}		
	}
	
	/**
	 * Check if the class implements the class Serializable.
	 * @param cm The Metadata containing the infos on the java class
	 * @return True if it already implements serializable
	 */
	protected final boolean alreadyImplementsSerializable(
			final EntityMetadata cm) {
		boolean ret = false;
		for (final String impl : cm.getImplementTypes()) {
			if ("Serializable".equals(impl)) {				
				ret = true;
				
				ConsoleUtils.displayDebug("Already implements Serializable !");
			}
		}
					
		return ret;
	}
	
	/**
	 * Check if the class already has a getter for the given field.
	 * @param fm The Metadata of the field
	 * @param cm The Metadata containing the infos on the java class
	 * @return True if it already has getter for this field
	 */
	protected final boolean alreadyImplementsGet(final FieldMetadata fm,
			final ClassMetadata cm) {
		boolean ret = false;
		final List<MethodMetadata> methods = cm.getMethods();
		final String capitalizedName = 
				fm.getName().substring(0, 1).toUpperCase() 
				+ fm.getName().substring(1);
		String prefix = "get";
		if ("boolean".equals(fm.getType())) {
			prefix = "is";
		}
		for (final MethodMetadata m : methods) {
			if (m.getName().equals(prefix + capitalizedName) 
					&& m.getArgumentsTypes().size() == 0 
					&& m.getType().equals(
							this.getAdapter().getNativeType(fm.getType()))) {
				ret = true;
				
				ConsoleUtils.displayDebug("Already implements getter of " 
						+ fm.getName() 
						+ " => " 
						+ m.getName());
			}
		}
					
		return ret;
	}
	
	/**
	 * Check if the class already has a setter for the given field.
	 * @param fm The Metadata of the field
	 * @param cm The Metadata containing the infos on the java class
	 * @return True if it already has setter for this field
	 */
	protected final boolean alreadyImplementsSet(final FieldMetadata fm, 
			final ClassMetadata cm) {
		boolean result = false;
		final List<MethodMetadata> methods = cm.getMethods();
		final String capitalizedName = 
				fm.getName().substring(0, 1).toUpperCase() 
				+ fm.getName().substring(1);
		
		for (final MethodMetadata m : methods) {
			if (m.getName().equals("set" + capitalizedName) 
					&& m.getArgumentsTypes().size() == 1 
					&& m.getArgumentsTypes().get(0).equals(
							this.getAdapter().getNativeType(fm.getType()))) {
				result = true;
				
				ConsoleUtils.displayDebug("Already implements setter of " 
						+ fm.getName() 
						+ " => " 
						+ m.getName());
			}
		}
		
		return result;
	}
	
	/**
	 * Check if the class already imports Serializable.
	 * @param cm The Metadata containing the infos on the java class
	 * @return True if it already imports serializable
	 */
	protected final boolean alreadyImportsSerializable(final ClassMetadata cm) {
		boolean ret = false;
		for (final String imp : cm.getImports()) {
			if ("Serializable".equals(imp)) {
				ret = true;
			}
		}
		
		return ret;
	}
}
