/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.util.ArrayList;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.FileUtils;

/**
 * Generator class for the project.
 *
 */
public class ProjectGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use for the generation.
	 * @throws Exception 
	 */
	public ProjectGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.datamodel = this.appMetas.toMap(this.adapter);
	}

	/**
	 * Make Platform specific Project Structure.
	 * @return success to make the platform project folder
	 */
	public final boolean makeProject() {
		boolean result = false;
		if (this.adapter.getPlatform().equals("android")) {
			result = this.makeProjectAndroid();
		} else if (this.adapter.getPlatform().equals("ios")) {
			result = this.makeProjectIOS();
		} else if (this.adapter.getPlatform().equals("rim")) {
			result = this.makeProjectRIM();
		} else if (this.adapter.getPlatform().equals("winphone")) {
			result = this.makeProjectWinPhone();
		}
		return result;
	}
	
	/**
	 * Remove Platform specific Project Structure.
	 * @return success to make the platform project folder
	 */
	public final boolean removeProject() {
		boolean result = false;
		final File dirproj = new File(
				String.format("%s/%s/",
						Harmony.PATH_PROJECT, 
						this.adapter.getPlatform()));
		
		final int removeResult = FileUtils.deleteRecursive(dirproj);

		if (removeResult == 0) {
			result = true;
			
			ConsoleUtils.displayDebug(
					"Project " + this.adapter.getPlatform() + " removed!");
		} else {
			ConsoleUtils.displayError(
					new Exception("Remove Project "
							+ this.adapter.getPlatform() 
							+ " return " + removeResult 
							+ " errors...\n"));
		}
		return result;
	}

	/**
	 * Generate HomeActivity File and merge it with datamodel.
	 */
	public final void generateHomeActivity() {
		ConsoleUtils.display(">> Generate HomeView & Strings...");

		final String fullFilePath = this.adapter.getHomeActivityPathFile();
		final String fullTemplatePath = 
				this.adapter.getTemplateHomeActivityPathFile().substring(1);

		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	/**
	 * Create Android project folders
	 */
	private void createFolders() {
		// create project name space folders
		FileUtils.makeFolder(this.adapter.getSourcePath() 
				+ this.appMetas.projectNameSpace.replaceAll("\\.", "/"));

		// create empty package entity
		FileUtils.makeFolder(this.adapter.getSourcePath() 
				+ this.appMetas.projectNameSpace.replaceAll("\\.", "/") 
				+ "/entity/");
		
		// create util folder
		FileUtils.makeFolder(this.adapter.getSourcePath() 
				+ this.appMetas.projectNameSpace.replaceAll("\\.", "/") 
				+ "/harmony/util/");
		
		// create libs folder
		FileUtils.makeFolder(this.adapter.getLibsPath());
	}
	
	/**
	 * Make Android sources project File
	 */
	private void makeSources() {
		// create HomeActivity.java 
		super.makeSource(this.adapter.getHomeActivityPathFile(),
				this.adapter.getTemplateHomeActivityPathFile(),
				false);

		// create configs.xml
		super.makeSource(
				this.adapter.getTemplateRessourceValuesPath() + "configs.xml",
				this.adapter.getRessourceValuesPath() + "configs.xml",
				false);

		// create strings.xml
		super.makeSource(
				this.adapter.getTemplateStringsPathFile(),
				this.adapter.getStringsPathFile(),
				false);
		
		// create configs.xml
		super.makeSource(
				this.adapter.getTemplateRessourceValuesPath() + "styles.xml",
				this.adapter.getRessourceValuesPath() + "styles.xml",
				false);

		// create main.xml
		super.makeSource(
				this.adapter.getTemplateRessourceLayoutPath() + "main.xml",
				this.adapter.getRessourceLayoutPath() + "main.xml",
				false);
		
		// create HarmonyFragmentActivity
		super.makeSource(
			this.adapter.getTemplateSourcePath() 
			+ "harmony/view/HarmonyFragmentActivity.java",
			this.adapter.getSourcePath()
			+ this.appMetas.projectNameSpace 
			+ "/harmony/view/" 
			+ "HarmonyFragmentActivity.java",
			false);
		
		// create HarmonyFragment
		super.makeSource(
			this.adapter.getTemplateSourcePath() 
			+ "harmony/view/HarmonyFragment.java",
			this.adapter.getSourcePath()
			+ this.appMetas.projectNameSpace 
			+ "/harmony/view/" 
			+ "HarmonyFragment.java",
			false);
		
		// create HarmonyListFragment
		super.makeSource(
			this.adapter.getTemplateSourcePath()
			+ "harmony/view/HarmonyListFragment.java",
			this.adapter.getSourcePath()
			+ this.appMetas.projectNameSpace 
			+ "/harmony/view/" 
			+ "HarmonyListFragment.java",
			false);
		
		// create ProjectMenuBase
		super.makeSource(
			this.adapter.getTemplateSourcePath() 
			+ "menu/TemplateMenuBase.java",
			this.adapter.getMenuPath()

			+ CaseFormat.LOWER_CAMEL.to(
					CaseFormat.UPPER_CAMEL,
					this.appMetas.name) 
			+ "MenuBase.java",
			false);
		
		// create ProjectMenu
		super.makeSource(
			this.adapter.getTemplateSourcePath() 
			+ "menu/TemplateMenu.java",
			this.adapter.getMenuPath() 
			+ CaseFormat.LOWER_CAMEL.to(
					CaseFormat.UPPER_CAMEL, 
					this.appMetas.name) 
			+ "Menu.java",
			false);
		
		// create MenuWrapper
		super.makeSource(
			this.adapter.getTemplateSourcePath() 
			+ "menu/MenuWrapperBase.java",
			this.adapter.getMenuPath() + "MenuWrapperBase.java",
			false);
	}
	
	/**
	 * Add Android libs project File
	 */
	private void addLibs() {
		// copy libraries
		this.updateLibrary("joda-time-2.1.jar");
		this.updateLibrary("guava-12.0.jar");
		this.updateLibrary("jsr305.jar");
		
		/// copy sherlock library		
		this.installAndroidSherlockLib();
		
		/// copy Harmony library
		FileUtils.copyfile(
				new File(String.format("%s/%s", 
						Harmony.PATH_HARMONY, 
						"harmony.jar")),
				new File(String.format("%s/%s", 
						this.adapter.getLibsPath(), 
						"harmony.jar")));
	}

	/**
	 * @param pathSherlock
	 */
	private void installAndroidSherlockLib() {
		//TODO test if git is install
		String pathSherlock = String.format("%s%s", 
				this.adapter.getLibsPath(), 
				"sherlock");
		
		if (!FileUtils.exists(pathSherlock)) {
			final ArrayList<String> command = new ArrayList<String>();
			
			// Command/Tools
			command.add("git");
			
			// Command action
			command.add("clone");
			
			// command depot
			command.add("https://github.com/JakeWharton/ActionBarSherlock.git");
			
			// Command destination folder
			command.add(pathSherlock);
			
			ConsoleUtils.launchCommand(command);
			command.clear();
	
			/*command.add("git");
			command.add("init");
			//ConsoleUtils.launchCommand(command, "/home/yo/git/Harmony/app/Android/libs/sherlock");
			ConsoleUtils.launchCommand(command, pathSherlock);
			command.clear();
			
			command.add("git");
			command.add("remote");
			command.add("add");
			command.add("-t");
			command.add("master");
			command.add("origin");
			command.add("https://github.com/JakeWharton/ActionBarSherlock.git");
			ConsoleUtils.launchCommand(command, pathSherlock);
			command.clear();
			
			command.add("git");
			command.add("config");
			command.add("core.sparsecheckout");
			command.add("true");
			ConsoleUtils.launchCommand(command, pathSherlock);
			command.clear();
	
			command.add("echo");
			command.add("library/");
			command.add(">");
			command.add(".git/info/sparse-checkout");
			ConsoleUtils.launchCommand(command, pathSherlock);
			command.clear();
	
			command.add("git");
			command.add("fetch");
			command.add("--depth=1");
			command.add("origin");
			command.add("master");
			ConsoleUtils.launchCommand(command, pathSherlock);
			command.clear();
			
			command.add("git");
			command.add("pull");
			command.add("origin");
			command.add("master");
			ConsoleUtils.launchCommand(command, pathSherlock);
			command.clear();*/
	
			//delete samples
			command.add("git");
			command.add(String.format("%s%s/%s", "--git-dir=", pathSherlock, ".git"));
			command.add(String.format("%s%s", "--work-tree=", pathSherlock));
			command.add("checkout");
			command.add("4.2.0");
			ConsoleUtils.launchCommand(command);
			command.clear();
			
			//make build sherlock
			command.add(String.format("%s/%s", ApplicationMetadata.androidSdkPath, "tools/android" ));
			command.add("update");
			command.add("project");
			command.add("--path");
			command.add(".");
			ConsoleUtils.launchCommand(command);
		}
	}

	/**
	 * Make Android Project Structure
	 * @return success to make the platform project folder
	 */
	protected final boolean makeProjectAndroid(){
		boolean result = false;
		
		this.createFolders();
		this.makeSources();
		this.addLibs();

		// copy utils
		this.updateUtil("DateUtils.java");

		// Update newly created files with datamodel
		final File dirTpl = new File(this.adapter.getTemplateProjectPath());
		if (dirTpl.exists() && dirTpl.listFiles().length != 0) {
			result = true;
			
			for (int i = 0; i < dirTpl.listFiles().length; i++) {
				if (dirTpl.listFiles()[i].isFile()) {
					super.makeSource(
							this.adapter.getTemplateProjectPath() 
								+ dirTpl.listFiles()[i].getName(),
							String.format("%s/%s/",
									Harmony.PATH_PROJECT, 
									this.adapter.getPlatform()) 
										+ dirTpl.listFiles()[i].getName(),
							false);
				}
			}
		}
		return result;
	}

	/**
	 * Make IOS Project Structure.
	 * @return success to make the platform project folder
	 */
	protected final boolean makeProjectIOS() {
		boolean result = false;
		//Generate base folders & files
		final File dirProj = FileUtils.makeFolderRecursive(
				String.format("%s/%s/%s/",
						Harmony.PATH_TEMPLATE ,
						this.adapter.getPlatform(), 
						this.adapter.getProject()),
				String.format("%s/%s/",
						Harmony.PATH_PROJECT, 
						this.adapter.getPlatform()),
				true);
		
		if (dirProj.exists() && dirProj.listFiles().length != 0) {
			result = true;
		}

		return result;
	}

	/**
	 * Make RIM Project Structure.
	 * @return success to make the platform project folder
	 */
	protected final boolean makeProjectRIM() {
		final boolean result = false;

		return result;
	}

	/**
	 * Make Windows Phone Project Structure.
	 * @return success to make the platform project folder
	 */
	protected final boolean makeProjectWinPhone() {
		final boolean result = false;

		return result;
	}
}
