package com.tactfactory.mda.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.meta.EntityMetadata;
import com.tactfactory.mda.meta.EnumMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.MethodMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EnumCompletor extends BaseGenerator{
	/**
	 * Java Getter Template.
	 */
	private static final String JAVA_GETTER_TEMPLATE = "enumGetter.java";
	
	/**
	 * Java Getter Template.
	 */
	private static final String JAVA_RETRIEVER_TEMPLATE = "enumRetriever.java";
	
	/** Entities folder. */
	private String entityFolder;

	/** Constructor.
	 * @param adapter Adapter used by this generator
	 * @throws Exception 
	 */
	public EnumCompletor(BaseAdapter adapt) throws Exception {
		super(adapt);
		this.entityFolder = 
				this.getAdapter().getSourcePath() 
				+ this.getAppMetas()
					.getProjectNameSpace().replaceAll("\\.", "/") 
				+ "/entity/";
	}
	
	/**
	 * Add getValue and fromValue to enum who declares an ID 
	 * and don't have theses methods.  
	 */
	public final void generateAll() {
		ConsoleUtils.display(">> Decorate enums...");
		
		for (final EnumMetadata enumMeta 
				: this.getAppMetas().getEnums().values()) { 
			
			if (enumMeta.getIdName() != null) {
				final String filepath = String.format("%s/%s",
						this.entityFolder,
						String.format("%s.java", enumMeta.getName()));
				
				ConsoleUtils.display(">>> Decorate " + enumMeta.getName());
				
				final File entityFile = TactFileUtils.getFile(filepath);
				if (entityFile.exists()) {
					// Load the file once in a String buffer
					final StringBuffer fileString = 
							TactFileUtils.fileToStringBuffer(entityFile); 
					
					this.addGetterAndRetriever(fileString, enumMeta);
					//this.addRetriever(fileString, enumMeta);
					
					 // After treatment on entity, write it in the original file
					TactFileUtils.stringBufferToFile(fileString, entityFile);
				}
			}
		}
	}
	

	/**
	 * Implements serializable in the class if it doesn't already.
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	protected final void addGetterAndRetriever(
			final StringBuffer fileString,
			final EnumMetadata enumMeta) {
		
		if (!this.alreadyHasGetter(enumMeta)) {
			ConsoleUtils.displayDebug("Add method getValue()");
			this.generateMethod(fileString, enumMeta, JAVA_GETTER_TEMPLATE);
			
			
		}
		
		if (!this.alreadyHasRetriever(enumMeta)) {
			ConsoleUtils.displayDebug("Add method fromValue()");			
			this.generateMethod(fileString, enumMeta, JAVA_RETRIEVER_TEMPLATE);
			
			
		}	
	}
	
	/**
	 * Check if the class implements the class Serializable.
	 * @param cm The Metadata containing the infos on the java class
	 * @return True if it already implements serializable
	 */
	protected final boolean alreadyHasGetter(
			final EnumMetadata enumMeta) {
		boolean ret = false;
		for (final MethodMetadata method : enumMeta.getMethods()) {
			if (method.getName().equals("getValue") && method.getArgumentsTypes().size() == 0) {				
				ret = true;
				ConsoleUtils.displayDebug("Already has getValue() !");
			}
		}
					
		return ret;
	}
	
	/**
	 * Check if the class implements the class Serializable.
	 * @param cm The Metadata containing the infos on the java class
	 * @return True if it already implements serializable
	 */
	protected final boolean alreadyHasRetriever(
			final EnumMetadata enumMeta) {
		boolean ret = false;
		for (final MethodMetadata method : enumMeta.getMethods()) {
			if (method.getName().equals("fromValue") 
					&& method.getArgumentsTypes().size() == 1
					&& method.getType().equals(enumMeta.getName())) {
				
				ret = true;
				ConsoleUtils.displayDebug("Already has fromValue() !");
			}
		}
					
		return ret;
	}
	
	protected final String calculateIndentLevel(EnumMetadata enumMeta) {
		String result = "";
		
		for (int i = 0; i < this.nbMotherClass(enumMeta); i++) {
			result += "\t";
		}
		
		return result;
	}
	
	protected final int nbMotherClass(EnumMetadata enumMeta) {
		int result = 1;
		if (enumMeta.getMotherClass() != null) {
			return result + this.nbMotherClass(
					this.getAppMetas().getEnums().get(
							enumMeta.getMotherClass()));
		} else {
			return result;
		}
	}
	
	/**
	 * Generate a get or set method following the given template.
	 * @param fileString The stringbuffer containing the class java code
	 * @param f The concerned field
	 * @param templateName The template file name
	 */
	protected final void generateMethod(final StringBuffer fileString, 
			final EnumMetadata enumMeta, 
			final String templateName) {
		final int lastAccolade = fileString.lastIndexOf("}");
		
		final Map<String, Object> map = this.getAppMetas().toMap(this.getAdapter());
		map.put("indentLevel", this.calculateIndentLevel(enumMeta));
		map.put(TagConstant.CURRENT_ENTITY, enumMeta.getName());
		//map.put("property", enumMeta.getIdName());
		//map.put("property_type", enumMeta.getFields().get(enumMeta.getIdName()).getType());
		
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
}
