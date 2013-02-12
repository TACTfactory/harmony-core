package com.tactfactory.mda.bundles.symfony.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.ConsoleUtils.ProcessToConsoleBridge;
import com.tactfactory.mda.bundles.symfony.adapter.SymfonyAdapter;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;

public class WebGenerator extends BaseGenerator{
	private static final int GENERATE_ENTITIES	= 0x00;
	private static final int INSTALL_SYMFONY	= 0x01;
	private static final int INSTALL_BUNDLES	= 0x02;
	private static final int INIT_PROJECT 		= 0x03;
	
	private SymfonyAdapter symfonyAdapter;
	private String projectName;
		
	public WebGenerator(BaseAdapter adapter, SymfonyAdapter sAdapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.symfonyAdapter = sAdapter;
		this.projectName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name);
	}
	
	public void generateEntities(){
		String fullFilePath = this.symfonyAdapter.getWebBundleYmlEntitiesPath(this.projectName) + "EntityBase.orm.yml";
		String fullTemplatePath = this.symfonyAdapter.getWebTemplateEntityPath().substring(1)+"EntityBase.orm.yml";
		super.makeSource(fullTemplatePath, fullFilePath, false);
		

		
		fullFilePath = this.symfonyAdapter.getWebEntitiesPath(this.projectName) + "EntityBase.php";
		fullTemplatePath = this.symfonyAdapter.getWebTemplateEntityPath().substring(1)+"EntityBase.php";
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		// Generate yml descriptor files
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeEntity(cm.name);
			}
		}		
		
		// Execute symfony command for entities generation
		this.launchCommand(GENERATE_ENTITIES);

		
		// Add inheritance in php entities
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				String content = " extends EntityBase";
				String after = "class "+cm.name;
				String filePath = this.symfonyAdapter.getWebEntitiesPath(this.projectName) + cm.name + ".php";
				this.addAfter(content, after, filePath);
			}
		}
	}
	
	public void generateWebControllers(){
		String fullFilePath = this.symfonyAdapter.getWebControllerPath(this.projectName)+"RestDefaultController.php";
		String fullTemplatePath = this.symfonyAdapter.getWebTemplateControllerPath().substring(1)+"RestDefaultController.php";
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		String routingPath = this.symfonyAdapter.getWebPath()+"app/config/routing.yml";
		String content = this.appMetas.name+"_rest_default:\n    type:     rest\n    resource: "+this.projectName+"\\ApiBundle\\Controller\\RestDefaultController\n\n";
		this.addToFile(content, routingPath);
		
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeController(cm.name);
			}
		}
	}
	
	public void installSymfony(){
		
		// Copy composer.phar :
		this.copyFile(this.symfonyAdapter.getWebTemplatePath()+"composer.phar", this.symfonyAdapter.getWebRootPath());
		
		// Execute composer.phar to download symfony :
		this.launchCommand(INSTALL_SYMFONY);
	}
	
	public void installBundles(){
		
		//Copy composer.json to symfony's path
		this.copyFile(this.symfonyAdapter.getWebTemplatePath()+"composer.json", this.symfonyAdapter.getWebPath());
		
		//Execute composer.phar to download and install fosrestbundle:
		this.launchCommand(INSTALL_BUNDLES);
		
		File configTplFile = new File(this.symfonyAdapter.getWebTemplateConfigPath()+"config.yml");
		StringBuffer sb = FileUtils.FileToStringBuffer(configTplFile);
		//this.addToFile(sb.toString(), this.symfonyPath+"/Symfony/app/config/config.yml");
		File configFile = new File(this.symfonyAdapter.getWebPath()+"app/config/config.yml");
		FileUtils.appendToFile(sb.toString(), configFile);
		
		String restBundleLoad = "\n\t\tnew FOS\\RestBundle\\FOSRestBundle(),";
		this.addAfter(restBundleLoad, "$bundles = array(", this.symfonyAdapter.getWebPath()+"app/AppKernel.php");
		String serializerBundleLoad = "\n\t\tnew JMS\\SerializerBundle\\JMSSerializerBundle(),";
		this.addAfter(serializerBundleLoad, restBundleLoad, this.symfonyAdapter.getWebPath()+"app/AppKernel.php");
	}
	
	public void initProject(){
		this.launchCommand(INIT_PROJECT);
	}
	
	protected void makeEntity(String entityName){
		String fullFilePath = this.symfonyAdapter.getWebBundleYmlEntitiesPath(this.projectName) + entityName+".orm.yml";
		String fullTemplatePath = this.symfonyAdapter.getWebTemplateEntityPath().substring(1)+"entity.yml";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	protected void makeController(String entityName){
		String fullFilePath = this.symfonyAdapter.getWebControllerPath(this.projectName)+"Rest"+entityName+"Controller.php";
		String fullTemplatePath = this.symfonyAdapter.getWebTemplateControllerPath().substring(1)+"RestTemplateController.php";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		fullFilePath = this.symfonyAdapter.getWebPath()+"app/config/routing.yml";
		fullTemplatePath = this.symfonyAdapter.getWebTemplateConfigPath()+"routing.yml";
		
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
			StringBuffer sb = FileUtils.FileToStringBuffer(f);
			if(sb.indexOf(content)==-1){
				BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
				bw.write(content);
				bw.close();
			}
		}catch(IOException e){
			ConsoleUtils.displayError(e);
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
				commandArgs.add(this.symfonyAdapter.getWebRootPath()+"/composer.phar"); // Composer path
				commandArgs.add("create-project");
				commandArgs.add("symfony/framework-standard-edition"); // Project to install
				commandArgs.add(this.symfonyAdapter.getWebPath()); // Installation folder
				commandArgs.add("2.1.7"); // Version
				break;
				
			case INSTALL_BUNDLES:
				//"php "+this.symfonyPath+"/composer.phar update -d "+this.symfonyPath+"/Symfony"
				commandArgs.add("php");
				commandArgs.add(this.symfonyAdapter.getWebRootPath()+"composer.phar"); // Composer path
				commandArgs.add("update");
				commandArgs.add("-d");
				commandArgs.add(this.symfonyAdapter.getWebPath()); // Installation folder
				break;
				
			case INIT_PROJECT:
				//"php "+this.symfonyPath+"/Symfony/app/console generate:bundle --namespace="+name+"/ApiBundle --dir="+this.symfonyPath+"/Symfony/src"+"  --bundle-name="+name+"ApiBundle --format=yml --structure=yes --no-interaction"
				commandArgs.add("php");
				commandArgs.add(this.symfonyAdapter.getWebPath()+"app/console"); // Symfony console path
				commandArgs.add("generate:bundle");
				commandArgs.add("--namespace="+projectName+"/ApiBundle"); // Namespace
				commandArgs.add("--dir="+this.symfonyAdapter.getWebPath()+"src"); // Bundle folder
				commandArgs.add("--bundle-name="+projectName+"ApiBundle"); // Bundle name
				commandArgs.add("--format=yml"); // Format
				commandArgs.add("--structure=yes"); // Generate project folder structure
				commandArgs.add("--no-interaction"); // Silent mode
				break;
				
			case GENERATE_ENTITIES:
				//"php "+this.symfonyPath+"/Symfony/app/console doctrine:generate:entities "+name+"ApiBundle"
				commandArgs.add("php");
				commandArgs.add(this.symfonyAdapter.getWebPath()+"app/console"); // Symfony console path
				commandArgs.add("doctrine:generate:entities");
				commandArgs.add(projectName+"ApiBundle"); // Bundle Namespace
				break;
		}
		
		return commandArgs;
	}
}
