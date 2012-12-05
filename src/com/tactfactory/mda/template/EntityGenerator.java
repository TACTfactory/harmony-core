package com.tactfactory.mda.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.orm.MethodMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.PackageUtils;

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
	
	private final static int GET = 0;
	private final static int SET = 1;

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
	
	public void generateAll(){
		for(ClassMetadata cm : metas){ 
			String filepath = String.format("%s/%s",
					this.entityFolder,
					String.format("%s.java", cm.name));
			
			File entityFile = FileUtils.getFile(filepath);
			if(entityFile.exists()){
				StringBuffer fileString = FileUtils.FileToStringBuffer(entityFile); // Load the file once in a String buffer
				
				addImplementsSerializable(fileString, cm);
				generateGetterAndSetters(fileString, cm);
				
				FileUtils.StringBufferToFile(fileString, entityFile); // After treatment on entity, write it in the original file
			}
		}
	}
	
	private void addImplementsSerializable(StringBuffer fileString, ClassMetadata cm){
		
			if(!alreadyImplementsSerializable(cm)){
				
					int firstAccolade = fileString.indexOf("{");
					if(cm.impls.size()>0){ // Class already implements an interface which is not Serializable
						fileString.insert(firstAccolade, ", Serializable");
					}else{
						fileString.insert(firstAccolade, " implements Serializable");
					}				
			}
	}
	
	private void generateGetterAndSetters(StringBuffer fileString, ClassMetadata cm){
		Collection<FieldMetadata> fields = cm.fields.values();
		
		for(FieldMetadata f : fields){
			if(!alreadyImplementsGet(f, cm)){
				generateGetOrSet(fileString, f, GET);
			}
			if(!alreadyImplementsSet(f, cm)){
				generateGetOrSet(fileString, f, SET);
			}
		}
	}
	
	private void generateGetOrSet(StringBuffer fileString, FieldMetadata f, int type){
		int lastAccolade = fileString.lastIndexOf("}");
		try{
			String tplPath = "";
			if(type==GET){
				tplPath = this.getTemplate;
			}else if (type==SET){
				tplPath = this.setTemplate;
			}
			Template tpl = this.cfg.getTemplate(
					String.format("%s%s",
							this.adapter.getTemplateSourceCommonPath(),
							tplPath));		// Load template file in engine
		
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("property",f.name);
			map.put("property_type",f.type);
			
			StringWriter writer = new StringWriter();
			tpl.process(map, writer);
			StringBuffer getString = writer.getBuffer();
			System.out.println(getString);
			fileString.insert(lastAccolade,getString);
			
		}catch(IOException e){
			System.out.println("Error : "+e);
		}catch(TemplateException e){
			System.out.println("Error : "+e);
		}		
	}
	
	public boolean alreadyImplementsSerializable(ClassMetadata cm){
		boolean ret = false;
		for(String impl : cm.impls)
			if(impl.equals("Serializable")){
				if(Harmony.DEBUG) System.out.println(cm.name + " : Already implements Serializable !");
				ret = true;
			}
					
		return ret;
	}
	
	private boolean alreadyImplementsGet(FieldMetadata fm, ClassMetadata cm){
		boolean ret = false;
		ArrayList<MethodMetadata> methods = cm.methods;
		String capitalizedName = fm.name.substring(0,1).toUpperCase() + fm.name.substring(1);
		for(MethodMetadata m : methods){
			if(m.name.equals("get"+capitalizedName) && m.argumentsTypes.size()==0 && m.type.equals(fm.type)){
				ret = true;
				if(Harmony.DEBUG) System.out.println("Already implements getter of "+fm.name+" => " + m.name);
			}
		}
		return ret;
	}
	
	private boolean alreadyImplementsSet(FieldMetadata fm, ClassMetadata cm){
		boolean ret = false;
		ArrayList<MethodMetadata> methods = cm.methods;
		String capitalizedName = fm.name.substring(0,1).toUpperCase() + fm.name.substring(1);
		for(MethodMetadata m : methods){
			if(m.name.equals("set"+capitalizedName) && m.argumentsTypes.size()==1 && m.argumentsTypes.get(0).equals(fm.type)){
				ret = true;
				if(Harmony.DEBUG) System.out.println("Already implements setter of "+fm.name+" => " + m.name);
			}
		}
		
		return ret;
	}
}
