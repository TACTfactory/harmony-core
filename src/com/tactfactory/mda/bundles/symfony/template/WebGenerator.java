/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.symfony.template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.bundles.symfony.adapter.SymfonyAdapter;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

public final class WebGenerator extends BaseGenerator {
	private static final String PHP = "php";
	
	private static final int GENERATE_ENTITIES	= 0x00;
	private static final int INSTALL_SYMFONY	= 0x01;
	private static final int INSTALL_BUNDLES	= 0x02;
	private static final int INIT_PROJECT 		= 0x03;
	
	private final SymfonyAdapter symfonyAdapter;
	private final String projectName;
		
	public WebGenerator(final BaseAdapter adapter,
			final SymfonyAdapter sAdapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.symfonyAdapter = sAdapter;
		this.projectName = CaseFormat.LOWER_CAMEL.to(
								CaseFormat.UPPER_CAMEL, this.appMetas.name);
	}
	
	public void generateEntities() {
		String fullFilePath = 
				this.symfonyAdapter.getWebBundleYmlEntitiesPath(
						this.projectName)
				 + "EntityBase.orm.yml";
		String fullTemplatePath = 
				this.symfonyAdapter.getWebTemplateEntityPath().substring(1)
				 + "EntityBase.orm.yml";
		super.makeSource(fullTemplatePath, fullFilePath, false);
		

		
		fullFilePath = 
				this.symfonyAdapter.getWebEntitiesPath(
						this.projectName)
				 + "EntityBase.php";
		fullTemplatePath = 
				this.symfonyAdapter.getWebTemplateEntityPath().substring(1)
				 + "EntityBase.php";
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		// Generate yml descriptor files
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (cm.options.containsKey("rest") && cm.fields.size() > 0) {
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeEntity(cm.name);
			}
		}		
		
		// Execute symfony command for entities generation
		ConsoleUtils.launchCommand(this.getCommand(GENERATE_ENTITIES));
		
		// Add inheritance in php entities
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (cm.options.containsKey("rest") && cm.fields.size() > 0) {
				final String content = " extends EntityBase";
				final String after = "class " + cm.name;
				final String filePath = 
						this.symfonyAdapter.getWebEntitiesPath(
								this.projectName) 
						 + cm.name 
						 + ".php";
				this.addAfter(content, after, filePath);
			}
		}
	}
	
	public void generateWebControllers() {
		final String fullFilePath = 
				this.symfonyAdapter.getWebControllerPath(
						this.projectName)
				 + "RestDefaultController.php";
		final String fullTemplatePath = 
				this.symfonyAdapter.getWebTemplateControllerPath().substring(1)
				 + "RestDefaultController.php";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		final String routingPath = 
				this.symfonyAdapter.getWebPath() + "app/config/routing.yml";
		final String content = 
				this.appMetas.name 
				 + "_rest_default:\n    type:     rest\n    resource: "
				 + this.projectName
				 + "\\ApiBundle\\Controller\\RestDefaultController\n\n";
		this.addToFile(content, routingPath);
		
		for (final ClassMetadata cm : this.appMetas.entities.values()) {
			if (cm.options.containsKey("rest") && cm.fields.size() > 0) {
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeController(cm.name);
			}
		}
	}
	
	public void installSymfony() {
		
		// Copy composer.phar :
		this.copyFile(
				this.symfonyAdapter.getWebTemplatePath() + "composer.phar",
				this.symfonyAdapter.getWebRootPath());
		
		// Execute composer.phar to download symfony :
		ConsoleUtils.launchCommand(this.getCommand(INSTALL_SYMFONY));
	}
	
	public void installBundles() {
		
		//Copy composer.json to symfony's path
		this.copyFile(
				this.symfonyAdapter.getWebTemplatePath() + "composer.json",
				this.symfonyAdapter.getWebPath());
		
		//Execute composer.phar to download and install fosrestbundle:
		ConsoleUtils.launchCommand(this.getCommand(INSTALL_BUNDLES));
		
		final File configTplFile = 
				new File(this.symfonyAdapter.getWebTemplateConfigPath()
						 + "config.yml");
		final StringBuffer sb = FileUtils.fileToStringBuffer(configTplFile);
		final File configFile = 
				new File(this.symfonyAdapter.getWebPath()
						 + "app/config/config.yml");
		FileUtils.appendToFile(sb.toString(), configFile);
		
		final String restBundleLoad =
				"\n\t\tnew FOS\\RestBundle\\FOSRestBundle(), ";
		this.addAfter(restBundleLoad,
					"$bundles = array(",
					this.symfonyAdapter.getWebPath() + "app/AppKernel.php");
		final String serializerBundleLoad = 
				"\n\t\tnew JMS\\SerializerBundle\\JMSSerializerBundle(), ";
		this.addAfter(serializerBundleLoad, 
				restBundleLoad, 
				this.symfonyAdapter.getWebPath() + "app/AppKernel.php");
	}
	
	public void initProject() {
		ConsoleUtils.launchCommand(this.getCommand(INIT_PROJECT));
	}
	
	protected void makeEntity(final String entityName) {
		final String fullFilePath = 
				this.symfonyAdapter.getWebBundleYmlEntitiesPath(
						this.projectName) 
				 + entityName
				 + ".orm.yml";
		final String fullTemplatePath = 
				this.symfonyAdapter.getWebTemplateEntityPath().substring(1)
				 + "entity.yml";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	protected void makeController(final String entityName) {
		String fullFilePath = 
				this.symfonyAdapter.getWebControllerPath(
						this.projectName)
				 + "Rest" + entityName + "Controller.php";
		String fullTemplatePath = 
				this.symfonyAdapter.getWebTemplateControllerPath().substring(1)
				 + "RestTemplateController.php";
		
		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		fullFilePath = 
				this.symfonyAdapter.getWebPath() + "app/config/routing.yml";
		fullTemplatePath = 
				this.symfonyAdapter.getWebTemplateConfigPath() + "routing.yml";
		
		super.appendSource(fullTemplatePath, fullFilePath);
	}
	
	protected void copyFile(final String srcPath, final String destPath) {
		File srcFile;
		File destDir;
		try {
			destDir = new File(destPath);
			srcFile = new File(srcPath);
			FileUtils.copyFileToDirectory(srcFile, destDir);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
		
	}
	
	protected void addToFile(final String content, final String filePath) {
		try {
			final File f = new File(filePath);
			final StringBuffer sb = FileUtils.fileToStringBuffer(f);
			if (sb.indexOf(content) == -1) {
				final BufferedWriter bw = 
						new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), FileUtils.DEFAULT_ENCODING));
				bw.write(content);
				bw.close();
			}
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	protected void addAfter(final String content,
			final String after, 
			final String filePath) {
		final File file = new File(filePath);
		FileUtils.addToFile(content, after, file);
	}
	
	private ArrayList<String> getCommand(final int command) {
		ArrayList<String> commandArgs = new ArrayList<String>();
		switch (command) {
			case INSTALL_SYMFONY:
				// PHP command
				commandArgs.add(PHP);
				
				// Composer path
				commandArgs.add(
						this.symfonyAdapter.getWebRootPath() 
						 + "/composer.phar");
				commandArgs.add("create-project");
				
				// Project to install
				commandArgs.add("symfony/framework-standard-edition");
				
				// Installation folder
				commandArgs.add(this.symfonyAdapter.getWebPath());
				
				// Version
				commandArgs.add("2.1.7"); 
				break;
				
			case INSTALL_BUNDLES:
				// PHP Command
				commandArgs.add(PHP);
				
				// Composer path
				commandArgs.add(
						this.symfonyAdapter.getWebRootPath()
						 + "composer.phar");
				
				// Update Command
				commandArgs.add("update");
				// Installation folder
				
				commandArgs.add("-d");
				commandArgs.add(
						this.symfonyAdapter.getWebPath()); 
				break;
				
			case INIT_PROJECT:
				// PHP Command
				commandArgs.add(PHP);
				
				// Symfony console path
				commandArgs.add(
						this.symfonyAdapter.getWebPath()
						 + "app/console"); 
				commandArgs.add("generate:bundle");
				
				 // Namespace
				commandArgs.add("--namespac =" + projectName + "/ApiBundle");
				
				 // Bundle folder
				commandArgs.add(
						"--di =" 
						 + this.symfonyAdapter.getWebPath()
						 + "src");
				
				 // Bundle name
				commandArgs.add("--bundle-nam =" + projectName + "ApiBundle");
				
				// Format
				commandArgs.add("--forma =yml"); 
				
				// Generate project folder structure
				commandArgs.add("--structur =yes"); 
				
				 // Silent mode
				commandArgs.add("--no-interaction");
				break;
				
			case GENERATE_ENTITIES:
				// PHP Command
				commandArgs.add(PHP);
				
				 // Symfony console path
				commandArgs.add(
						this.symfonyAdapter.getWebPath()
						 + "app/console");
				
				commandArgs.add("doctrine:generate:entities");
				
				 // Bundle Namespace
				commandArgs.add(projectName + "ApiBundle");
				break;
				
			default :
				commandArgs = null;
				break;
		}
		
		return commandArgs;
	}
}
