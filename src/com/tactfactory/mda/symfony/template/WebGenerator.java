package com.tactfactory.mda.symfony.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.ConsoleUtils.ProcessToConsoleBridge;
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
		String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name);
		this.launchCommand("php "+this.symfonyPath+"/Symfony/app/console doctrine:generate:entities "+name+"ApiBundle");
	}
	
	public void generateWebControllers(){
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeController(cm.name);
			}
		}
	}
	
	public void installSymfony(){
		
		// Copy composer.phar :
		this.copyFile(this.adapter.getWebTemplatePath()+"composer.phar", this.symfonyPath+"/");
		
		// Execute composer.phar to download symfony :
		this.launchCommand("php "+this.symfonyPath+"/composer.phar create-project symfony/framework-standard-edition "+this.symfonyPath+"/Symfony dev-master");
	}
	
	public void installBundles(){
		
		//Copy composer.json to symfony's path
		this.copyFile(this.adapter.getWebTemplatePath()+"composer.json", this.symfonyPath+"/Symfony/");
		
		//Execute composer.phar to download and install fosrestbundle:
		this.launchCommand("php "+this.symfonyPath+"/composer.phar update -d "+this.symfonyPath+"/Symfony");
		
		StringBuffer sb = FileUtils.FileToStringBuffer(new File("tpl/web/config/config.yml"));
		this.addToFile(sb.toString(), this.symfonyPath+"/Symfony/app/config/config.yml");
	}
	
	public void initProject(){
		
		
		String name = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name);
		//Init project
		String command = "php "+this.symfonyPath+"/Symfony/app/console generate:bundle --namespace="+name+"/ApiBundle --dir="+this.symfonyPath+"/Symfony/src"+"  --bundle-name="+name+"ApiBundle --format=yml --structure=yes --no-interaction";
		System.out.println("Executing : "+command);
		this.launchCommand(command);
	
	}
	
	protected void makeEntity(String entityName){
		String fullFilePath = this.symfonyPath + "/Symfony/src/Demact/ApiBundle/Resources/config/doctrine/" + entityName+".orm.yml";
		String fullTemplatePath = this.adapter.getWebTemplateSourceEntityPath().substring(1)+"entity.yml";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	protected void makeController(String entityName){
		String fullFilePath = this.symfonyPath + "/Symfony/src/Demact/ApiBundle/Controller/Rest"+entityName+"Controller.php";
		String fullTemplatePath = this.adapter.getWebTemplateSourceEntityPath().substring(1)+"RestTemplateController.php";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		fullFilePath = this.symfonyPath + "/Symfony/app/config/routing.yml";
		fullTemplatePath = "tpl/web/config/routing.yml";
		
		super.appendSource(fullTemplatePath, fullFilePath);
	}
	
	protected void launchCommand(String command){
		try {
			Runtime rt = Runtime.getRuntime();
			Process exec = rt.exec(command);
			

			ProcessToConsoleBridge bridge = new ProcessToConsoleBridge(exec);
			bridge.start();
			try {
				exec.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bridge.stop();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void copyFile(String srcPath, String destPath){
		File srcFile;
		File destDir;
		try {
			destDir = new File(destPath);
			srcFile = new File(srcPath);
			FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void addToFile(String content, String filePath){
		try{
			File f = new File(filePath);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			bw.write(content);
			bw.close();
		}catch(IOException e){
			ConsoleUtils.displayError(e.getMessage());
		}
	}
}
