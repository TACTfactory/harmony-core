package com.tactfactory.mda.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.orm.MethodMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class EntityGenerator {
	private List<ClassMetadata> metas;
	private BaseAdapter adapter;
	private String entityFolder;
	private String getTemplate;
	private String setTemplate;
	protected HashMap<String, Object> datamodel = new HashMap<String, Object>();

	Configuration cfg = new Configuration(); // Initialization of template engine

	public EntityGenerator(List<ClassMetadata> metas, BaseAdapter adapter){
		this.metas = metas;
		this.adapter = adapter;

		this.entityFolder = this.adapter.getSourcePath() + Harmony.projectNameSpace.replaceAll("\\.", "/") + "/entity/";
		
		this.getTemplate = "itemGetter.java";
		this.setTemplate = "itemSetter.java";
		
		try {
			this.cfg.setDirectoryForTemplateLoading(new File(Harmony.pathBase));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Implements serializable and add necessary getters and setters for all classes 
	 */
	public void generateAll(){
		ConsoleUtils.display(">> Decorate entities...");
		
		for(ClassMetadata cm : metas){ 
			String filepath = String.format("%s/%s",
					this.entityFolder,
					String.format("%s.java", cm.name));
			
			ConsoleUtils.display(">>> Decorate " + cm.name);
			
			File entityFile = FileUtils.getFile(filepath);
			if(entityFile.exists()){
				StringBuffer fileString = FileUtils.FileToStringBuffer(entityFile); // Load the file once in a String buffer
				
				this.addImplementsSerializable(fileString, cm);
				this.addImportSerializable(fileString,cm);
				this.generateGetterAndSetters(fileString, cm);
				
				FileUtils.StringBufferToFile(fileString, entityFile); // After treatment on entity, write it in the original file
			}
		}
	}
	
	
	/**
	 * Implements serializable in the class if it doesn't already
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	private void addImplementsSerializable(StringBuffer fileString, ClassMetadata cm){
		if(!this.alreadyImplementsSerializable(cm)){
			ConsoleUtils.displayDebug("Add serializable implement");
			int firstAccolade = fileString.indexOf("{");
			
			if(cm.implementTypes.size() > 0){ // Class already implements an interface which is not Serializable
				fileString.insert(firstAccolade, ", Serializable");
			}else{
				fileString.insert(firstAccolade, " implements Serializable");
			}				
		}
	}
	
	/**
	 * Import serializable in the class if it doesn't already
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	private void addImportSerializable(StringBuffer fileString, ClassMetadata cm){
		if(!this.alreadyImportsSerializable(cm)){
			ConsoleUtils.displayDebug("Add serializable import");
			int insertPos ;
			
			if(cm.imports.size()>0){ 
				insertPos = fileString.indexOf("import");
			}else{
				insertPos = fileString.indexOf(";")+1;
			}
			
			fileString.insert(insertPos, "\rimport java.io.Serializable;\r");
		}
	}
	
	
	/**
	 * Generate the necessary getters and setters for the class
	 * @param fileString The stringbuffer containing the class java code
	 * @param cm The Metadata containing the infos on the java class
	 */
	private void generateGetterAndSetters(StringBuffer fileString, ClassMetadata cm){
		Collection<FieldMetadata> fields = cm.fields.values();
		
		for(FieldMetadata f : fields){
			if(!f.internal){
				// Getter
				if(!this.alreadyImplementsGet(f, cm)){ 
					ConsoleUtils.displayDebug("Add implements getter of " + f.fieldName + " => get" + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, f.fieldName));
					
					this.generateMethod(fileString, f, this.getTemplate);
				}
				
				// Setter
				if(!this.alreadyImplementsSet(f, cm)){
					ConsoleUtils.displayDebug("Add implements setter of " + f.fieldName + " => set" + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, f.fieldName));
					
					this.generateMethod(fileString, f, this.setTemplate);
				}
			}
		}
	}
	
	
	/**
	 * Generate a get or set method following the given template
	 * @param fileString The stringbuffer containing the class java code
	 * @param f The concerned field
	 * @param templateName The template file name
	 */
	private void generateMethod(StringBuffer fileString, FieldMetadata f, String templateName){
		int lastAccolade = fileString.lastIndexOf("}");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("property",f.fieldName);
		map.put("property_type",this.adapter.getNativeType(f.type));
		
		try{
			StringWriter writer = new StringWriter();
			
			Template tpl = this.cfg.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceCommonPath(),
							templateName));		// Load template file in engine
			
			tpl.process(map, writer);
			StringBuffer getString = writer.getBuffer();
			fileString.insert(lastAccolade, getString + "\n\n");
			
		}catch(IOException e){
			ConsoleUtils.displayError(e.toString());
		}catch(TemplateException e){
			ConsoleUtils.displayError(e.toString());
		}		
	}
	
	/**
	 * Check if the class implements the class Serializable
	 * @param cm The Metadata containing the infos on the java class
	 */
	
	public boolean alreadyImplementsSerializable(ClassMetadata cm){
		boolean ret = false;
		for(String impl : cm.implementTypes)
			if(impl.equals("Serializable")){				
				ret = true;
				
				ConsoleUtils.displayDebug("Already implements Serializable !");
			}
					
		return ret;
	}
	
	/**
	 * Check if the class already has a getter for the given field
	 * @param fm The Metadata of the field
	 * @param cm The Metadata containing the infos on the java class
	 */
	private boolean alreadyImplementsGet(FieldMetadata fm, ClassMetadata cm){
		boolean ret = false;
		ArrayList<MethodMetadata> methods = cm.methods;
		String capitalizedName = fm.fieldName.substring(0,1).toUpperCase() + fm.fieldName.substring(1);
		String prefix = "get";
		if(fm.type.equals("boolean")){
			prefix = "is";
		}
		for(MethodMetadata m : methods){
			if(m.name.equals(prefix+capitalizedName) && 
					m.argumentsTypes.size()==0 && 
					m.type.equals(this.adapter.getNativeType(fm.type))){
				ret = true;
				
				ConsoleUtils.displayDebug("Already implements getter of " + fm.fieldName + " => " + m.name);
			}
		}
					
		return ret;
	}
	
	/**
	 * Check if the class already has a setter for the given field
	 * @param fm The Metadata of the field
	 * @param cm The Metadata containing the infos on the java class
	 */
	private boolean alreadyImplementsSet(FieldMetadata fm, ClassMetadata cm){
		boolean result = false;
		ArrayList<MethodMetadata> methods = cm.methods;
		String capitalizedName = fm.fieldName.substring(0,1).toUpperCase() + fm.fieldName.substring(1);
		
		for(MethodMetadata m : methods){
			if(m.name.equals("set"+capitalizedName) && 
					m.argumentsTypes.size()==1 && 
					m.argumentsTypes.get(0).equals(this.adapter.getNativeType(fm.type))){
				result = true;
				
				ConsoleUtils.displayDebug("Already implements setter of " + fm.fieldName + " => " + m.name);
			}
		}
		
		return result;
	}
	
	/**
	 * Check if the class already imports Serializable
	 * @param cm The Metadata containing the infos on the java class
	 */
	private boolean alreadyImportsSerializable(ClassMetadata cm){
		boolean ret = false;
		for(String imp : cm.imports){
			if(imp.equals("Serializable")) 
				ret=true;
		}
		
		return ret;
	}
}
