package com.tactfactory.mda.symfony.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	private static final int GENERATE_ENTITIES	= 0x00;
	private static final int INSTALL_SYMFONY	= 0x01;
	private static final int INSTALL_BUNDLES		= 0x02;
	private static final int INIT_PROJECT 		= 0x03;
	
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
		this.launchCommand(GENERATE_ENTITIES);
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
		this.launchCommand(INSTALL_SYMFONY);
	}
	
	public void installBundles(){
		
		//Copy composer.json to symfony's path
		this.copyFile(this.adapter.getWebTemplatePath()+"composer.json", this.symfonyPath+"/Symfony/");
		
		//Execute composer.phar to download and install fosrestbundle:
		this.launchCommand(INSTALL_BUNDLES);
		
		File configTplFile = new File("tpl/web/config/config.yml");
		StringBuffer sb = FileUtils.FileToStringBuffer(configTplFile);
		//this.addToFile(sb.toString(), this.symfonyPath+"/Symfony/app/config/config.yml");
		File configFile = new File(this.symfonyPath+"/Symfony/app/config/config.yml");
		FileUtils.appendToFile(sb.toString(), configFile);
		
		String restBundleLoad = "\n\t\tnew FOS\\RestBundle\\FOSRestBundle(),";
		this.addAfter(restBundleLoad, "$bundles = array(", this.symfonyPath+"/Symfony/app/AppKernel.php");
		String serializerBundleLoad = "\n\t\tnew JMS\\SerializerBundle\\JMSSerializerBundle(),";
		this.addAfter(serializerBundleLoad, restBundleLoad, this.symfonyPath+"/Symfony/app/AppKernel.php");
	}
	
	public void initProject(){
		this.launchCommand(INIT_PROJECT);
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
	
	protected void launchCommand(int command){
		try {
			ProcessBuilder pb = new ProcessBuilder(this.getCommand(command));
			Process exec = pb.start();
			

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
	
	protected void addAfter(String content, String after, String filePath){
		File file = new File(filePath);
		FileUtils.addToFile(content, after, file);
	}
	
	private ArrayList<String> getCommand(int command){
		String projectName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name);
		ArrayList<String> commandArgs = new ArrayList<String>();
		switch(command){
			case INSTALL_SYMFONY:
				commandArgs.add("php");
				commandArgs.add(this.symfonyPath+"/composer.phar"); // Composer path
				commandArgs.add("create-project");
				commandArgs.add("symfony/framework-standard-edition"); // Project to install
				commandArgs.add(this.symfonyPath+"/Symfony"); // Installation folder
				commandArgs.add("dev-master"); // Version
				break;
				
			case INSTALL_BUNDLES:
				//"php "+this.symfonyPath+"/composer.phar update -d "+this.symfonyPath+"/Symfony"
				commandArgs.add("php");
				commandArgs.add(this.symfonyPath+"/composer.phar"); // Composer path
				commandArgs.add("update");
				commandArgs.add("-d");
				commandArgs.add(this.symfonyPath+"/Symfony"); // Installation folder
				break;
				
			case INIT_PROJECT:
				//"php "+this.symfonyPath+"/Symfony/app/console generate:bundle --namespace="+name+"/ApiBundle --dir="+this.symfonyPath+"/Symfony/src"+"  --bundle-name="+name+"ApiBundle --format=yml --structure=yes --no-interaction"
				commandArgs.add("php");
				commandArgs.add(this.symfonyPath+"/Symfony/app/console"); // Symfony console path
				commandArgs.add("generate:bundle");
				commandArgs.add("--namespace="+projectName+"/ApiBundle"); // Namespace
				commandArgs.add("--dir="+this.symfonyPath+"/Symfony/src"); // Bundle folder
				commandArgs.add("--bundle-name="+projectName+"ApiBundle"); // Bundle name
				commandArgs.add("--format=yml"); // Format
				commandArgs.add("--structure=yes"); // Generate project folder structure
				commandArgs.add("--no-interaction"); // Silent mode
				break;
				
			case GENERATE_ENTITIES:
				//"php "+this.symfonyPath+"/Symfony/app/console doctrine:generate:entities "+name+"ApiBundle"
				commandArgs.add("php");
				commandArgs.add(this.symfonyPath+"/Symfony/app/console"); // Symfony console path
				commandArgs.add("generate:generate:entities");
				commandArgs.add(projectName+"ApiBundle"); // Bundle Namespace
				break;
		}
		
		return commandArgs;
	}
}
