package com.tactfactory.mda.symfony.template;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;

public class WebGenerator extends BaseGenerator{
	private String symfonyPath = "www";
	
	public WebGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void generateEntities(){
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeEntity(cm.name);
			}
		}
	}
	
	public void generateWebRepositories(){
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeRepository(cm.name);
			}
		}
	}
	
	public void initProject(){
		// Copy composer files :
		File srcFile;
		File destDir = new File(this.symfonyPath+"/");
		try {
			srcFile = new File(this.adapter.getWebTemplatePath()+"composer.json");
			FileUtils.copyFileToDirectory(srcFile, destDir);
			srcFile = new File(this.adapter.getWebTemplatePath()+"composer.phar");
			FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Execute composer.phar to download symfony :
		Runtime rt = Runtime.getRuntime();
		try {
			Process exec = rt.exec("php "+this.symfonyPath+"/composer.phar install -d "+this.symfonyPath+"/");

			System.out.println(this.readInput(exec));
			System.out.println(this.readInputErr(exec));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	protected void makeEntity(String entityName){
		String fullFilePath = this.symfonyPath + "/" + "Entity" + "/" + entityName+".orm.yml";
		String fullTemplatePath = this.adapter.getWebTemplateSourceEntityPath().substring(1)+"entity.yml";
		
		ConsoleUtils.displayDebug("Generating "+fullFilePath+" from "+fullTemplatePath);
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	protected void makeRepository(String entityName){
		String fullFilePath = this.symfonyPath + "/" + "Entity" + "/" + "Rest"+entityName+"Repository.php";
		String fullTemplatePath = this.adapter.getWebTemplateSourceEntityPath().substring(1)+"RestTemplateRepository.php";
		
		ConsoleUtils.displayDebug("Generating "+fullFilePath+" from "+fullTemplatePath);
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	private String readInputErr(Process proc){
		StringBuilder sb = new StringBuilder();
		try{
			BufferedInputStream err = new BufferedInputStream(proc.getErrorStream());
			byte[] contents = new byte[1024];
			 	     
	          
	        while ((err.read(contents)) != -1) {
	            sb.append(contents);
	        }
		}catch(IOException e){
			ConsoleUtils.displayError(e.getMessage());
		}
		return sb.toString();
	}
	
	private String readInput(Process proc){
		StringBuilder sb = new StringBuilder();
		try{
			BufferedInputStream in = new BufferedInputStream(proc.getInputStream());
			byte[] contents = new byte[1024];
			 	          
	          
	        while ((in.read(contents)) != -1) {
	            sb.append(contents);
	        }
		}catch(IOException e){
			ConsoleUtils.displayError(e.getMessage());
		}
		return sb.toString();
	}

}
