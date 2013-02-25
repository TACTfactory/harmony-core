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
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.bundles.symfony.adapter.SymfonyAdapter;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

/**
 * Symfony generator.
 */
public final class WebGenerator extends BaseGenerator {
	/** PHP Command. */
	private static final String PHP = "php";
	
	/** Symfony entities generation command code. */
	private static final int GENERATE_ENTITIES	= 0x00;
	/** Symfony installation command code. */
	private static final int INSTALL_SYMFONY	= 0x01;
	/** Needed Symfony bundles command code. */
	private static final int INSTALL_BUNDLES	= 0x02;
	/** Symfony project initialization command code. */
	private static final int INIT_PROJECT 		= 0x03;
	
	/** Symfony adapter. */
	private final SymfonyAdapter symfonyAdapter;
	
	/** Project name. */
	private final String projectName;
		
	/**
	 * Constructor.
	 * @param adapter The adapter.
	 * @param sAdapter The Symfony adapter. 
	 * @throws Exception 
	 */
	public WebGenerator(final BaseAdapter adapter,
			final SymfonyAdapter sAdapter) throws Exception {
		super(adapter);
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
		this.symfonyAdapter = sAdapter;
		this.projectName = CaseFormat.LOWER_CAMEL.to(
								CaseFormat.UPPER_CAMEL, 
								this.getAppMetas().getName());
	}
	
	/**
	 * Generate the symfony entities.
	 */
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
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (cm.getOptions().containsKey("rest") 
					&& cm.getFields().size() > 0) {
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeEntity(cm.getName());
			}
		}		
		
		// Execute symfony command for entities generation
		ConsoleUtils.launchCommand(this.getCommand(GENERATE_ENTITIES));
		
		// Add inheritance in php entities
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (cm.getOptions().containsKey("rest") 
					&& cm.getFields().size() > 0) {
				final String content = " extends EntityBase";
				final String after = "class " + cm.getName();
				final String filePath = 
						this.symfonyAdapter.getWebEntitiesPath(
								this.projectName) 
						 + cm.getName() 
						 + ".php";
				this.addAfter(content, after, filePath);
			}
		}
	}
	
	/**
	 * Generate the Symfony sync controllers.
	 */
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
				this.getAppMetas().getName() 
				 + "_rest_default:\n    type:     rest\n    resource: "
				 + this.projectName
				 + "\\ApiBundle\\Controller\\RestDefaultController\n\n";
		this.addToFile(content, routingPath);
		
		for (final ClassMetadata cm 
				: this.getAppMetas().getEntities().values()) {
			if (cm.getOptions().containsKey("rest") 
					&& cm.getFields().size() > 0) {
				this.getDatamodel().put(
						TagConstant.CURRENT_ENTITY, cm.getName());
				this.makeController(cm.getName());
			}
		}
	}
	
	/**
	 * Install symfony.
	 */
	public void installSymfony() {
		
		// Copy composer.phar :
		this.copyFile(
				this.symfonyAdapter.getWebTemplatePath() + "composer.phar",
				this.symfonyAdapter.getWebRootPath());
		
		// Execute composer.phar to download symfony :
		ConsoleUtils.launchCommand(this.getCommand(INSTALL_SYMFONY));
	}
	
	/**
	 * Install FOSRestBundle & dependencies for Symfony.
	 */
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
	
	/**
	 * Initialize the Symfony project.
	 */
	public void initProject() {
		ConsoleUtils.launchCommand(this.getCommand(INIT_PROJECT));
	}
	
	/**
	 * Make a symfony entity.
	 * @param entityName The entity name.
	 */
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
	
	/**
	 * Make the entity's sync controller.
	 * @param entityName The entity name.
	 */
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
	
	/**
	 * Copy a file.
	 * @param srcPath Source file.
	 * @param destPath Destination file.
	 */
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
	
	/**
	 * Append content to file.
	 * @param content The content to add.
	 * @param filePath The file to modify.
	 */
	protected void addToFile(final String content, final String filePath) {
		try {
			final File f = new File(filePath);
			final StringBuffer sb = FileUtils.fileToStringBuffer(f);
			if (sb.indexOf(content) == -1) {
				final BufferedWriter bw = 
						new BufferedWriter(
								new OutputStreamWriter(
										new FileOutputStream(f, true),
										FileUtils.DEFAULT_ENCODING));
				bw.write(content);
				bw.close();
			}
		} catch (final IOException e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Add content to a file after the given string.
	 * @param content The content to add
	 * @param after The String to add the content after
	 * @param filePath The file to modify
	 */
	protected void addAfter(final String content,
			final String after, 
			final String filePath) {
		final File file = new File(filePath);
		FileUtils.addToFile(content, after, file);
	}
	
	/**
	 * Get the command corresponding to the given command number.
	 * @param command The command number.
	 * @return The command.
	 */
	private List<String> getCommand(final int command) {
		List<String> commandArgs = new ArrayList<String>();
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
