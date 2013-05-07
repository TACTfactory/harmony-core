/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

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

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Make Platform specific Project Structure.
	 * @return success to make the platform project folder
	 */
	public final boolean makeProject() {
		boolean result = false;
		if (this.getAdapter().getPlatform().equals("android")) {
			result = this.makeProjectAndroid();
		} else if (this.getAdapter().getPlatform().equals("ios")) {
			result = this.makeProjectIOS();
		} else if (this.getAdapter().getPlatform().equals("rim")) {
			result = this.makeProjectRIM();
		} else if (this.getAdapter().getPlatform().equals("winphone")) {
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
						Harmony.getProjectPath(), 
						this.getAdapter().getPlatform()));
		
		final int removeResult = TactFileUtils.deleteRecursive(dirproj);

		if (removeResult == 0) {
			result = true;
			
			ConsoleUtils.displayDebug(
					"Project " + this.getAdapter().getPlatform() + " removed!");
		} else {
			ConsoleUtils.displayError(
					new Exception("Remove Project "
							+ this.getAdapter().getPlatform() 
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

		final String fullFilePath = this.getAdapter().getHomeActivityPathFile();
		final String fullTemplatePath = 
				this.getAdapter().getTemplateHomeActivityPathFile();

		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	/**
	 * Create Android project folders.
	 */
	private void createFolders() {
		// create project name space folders
		TactFileUtils.makeFolder(this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace()
					.replaceAll("\\.", "/"));

		// create empty package entity
		TactFileUtils.makeFolder(this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace()
					.replaceAll("\\.", "/") 
				+ "/entity/");
		
		// create util folder
		TactFileUtils.makeFolder(this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace()
					.replaceAll("\\.", "/") 
				+ "/harmony/util/");
		
		// create libs folder
		TactFileUtils.makeFolder(this.getAdapter().getLibsPath());
	}
	
	/**
	 * Make Android sources project File.
	 */
	private void makeSources() {
		// create HomeActivity.java 
		super.makeSource(this.getAdapter().getTemplateHomeActivityPathFile(),
				this.getAdapter().getHomeActivityPathFile(),
				false);
		
		// create configs.xml
		super.makeSource(
				this.getAdapter().getTemplateRessourceValuesPath() 
					+ "configs.xml",
				this.getAdapter().getRessourceValuesPath() + "configs.xml",
				false);

		// create strings.xml
		super.makeSource(
				this.getAdapter().getTemplateStringsPathFile(),
				this.getAdapter().getStringsPathFile(),
				false);
		
		// create configs.xml
		super.makeSource(
				this.getAdapter().getTemplateRessourceValuesPath() 
					+ "styles.xml",
				this.getAdapter().getRessourceValuesPath() + "styles.xml",
				false);

		// create main.xml
		super.makeSource(
				this.getAdapter().getTemplateRessourceLayoutPath() + "main.xml",
				this.getAdapter().getRessourceLayoutPath() + "main.xml",
				false);
		
		// create HarmonyFragmentActivity
		super.makeSource(
			this.getAdapter().getTemplateSourcePath() 
			+ "harmony/view/HarmonyFragmentActivity.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace() 
			+ "/harmony/view/" 
			+ "HarmonyFragmentActivity.java",
			false);
		
		// create HarmonyFragment
		super.makeSource(
			this.getAdapter().getTemplateSourcePath() 
			+ "harmony/view/HarmonyFragment.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace() 
			+ "/harmony/view/" 
			+ "HarmonyFragment.java",
			false);
		
		// create HarmonyListFragment
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/view/HarmonyListFragment.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace() 
			+ "/harmony/view/" 
			+ "HarmonyListFragment.java",
			false);
		
		try {
			new MenuGenerator(this.getAdapter()).generateMenu();
		} catch (Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Add Android libs project File.
	 */
	private void addLibs() {
		// copy libraries
		this.updateLibrary("joda-time-2.1.jar");
		this.updateLibrary("guava-12.0.jar");
		this.updateLibrary("jsr305.jar");
		
		/// copy sherlock library		
		this.installAndroidSherlockLib();
		
		/// copy Harmony library
		TactFileUtils.copyfile(
				new File(String.format("%s/%s", 
						Harmony.getBundlePath(), 
						"tact-core/harmony.jar")), 
				new File(String.format("%s/%s", 
						this.getAdapter().getLibsPath(), 
						"harmony.jar")));
	}

	/**
	 * @param pathSherlock
	 */
	private void installAndroidSherlockLib() {
		//TODO test if git is install
		String pathSherlock = String.format("%s%s", 
				this.getAdapter().getLibsPath(), 
				"sherlock");
		
		if (!TactFileUtils.exists(pathSherlock)) {
			final ArrayList<String> command = new ArrayList<String>();
			
			// command/Tools
			command.add("git");
			
			// command action
			command.add("clone");
			
			// command depot
			command.add("https://github.com/JakeWharton/ActionBarSherlock.git");
			
			// command destination folder
			command.add(pathSherlock);
			
			ConsoleUtils.launchCommand(command);
			command.clear();
			
			// command git checkout
			command.add("git");
			command.add(String.format(
					"%s%s/%s", "--git-dir=", pathSherlock, ".git"));
			command.add(String.format("%s%s", "--work-tree=", pathSherlock));
			command.add("checkout");
			command.add("4.2.0");
			ConsoleUtils.launchCommand(command);
			command.clear();
			
			// delete samples
			TactFileUtils.deleteRecursive(
					new File(String.format("%s/%s", pathSherlock, "samples")));

			String srcPath = Harmony.getTemplatesPath() + "/android/libs/sherlock_ant.properties";
			String destPath = pathSherlock + "/library/ant.properties";
			this.makeSource(srcPath, destPath, false);
			
			//make build sherlock
			command.add(String.format("%s/%s", 
					ApplicationMetadata.getAndroidSdkPath(), 
					"tools/android"));
			command.add("update");
			command.add("project");
			command.add("--path");
			command.add(pathSherlock + "/library");
			ConsoleUtils.launchCommand(command);
		}
	}

	/**
	 * Make Android Project Structure.
	 * @return success to make the platform project folder
	 */
	protected final boolean makeProjectAndroid() {
		boolean result = false;
		
		this.createFolders();
		this.makeSources();
		this.addLibs();

		// copy utils
		this.updateUtil("DateUtils.java");

		// Update newly created files with datamodel
		final File dirTpl = 
				new File(Harmony.getBundlePath() + "tact-core/"
						+ this.getAdapter().getTemplateProjectPath());
		if (dirTpl.exists() && dirTpl.listFiles().length > 0) {				
			this.clearProjectSources();
			this.copyProjectTemplates(dirTpl, null, 
					Harmony.getProjectPath() 
						+ File.separator + this.getAdapter().getPlatform(), 
					this.getAdapter().getTemplateProjectPath());
			result = true;	
		}
		
		// Make Test project
		
		try {
			new TestDBGenerator(this.getAdapter()).initTestAndroid();
		} catch (Exception e) {
			ConsoleUtils.displayError(e);
		}
		return result;
	}
	
	/**
	 * Delete files that need to be recreated
	 */
	private void clearProjectSources() {
		String projectPath = Harmony.getProjectPath() 
				+ File.separator + this.getAdapter().getPlatform();
		
		File buildRules = new File(projectPath 
				+ File.separator + "build.rules.xml");
		
		if (buildRules.exists()) {
			buildRules.delete();
		}
	}
	
	/**
	 * Copy files with recursive
	 * @param file File to copy
	 * @param directory Directory of the file (null if first)
	 * @param sourcesPath Directory of sources files
	 * @param templatesPath Directory of templates files
	 */
	private void copyProjectTemplates(File file, String directory, 
			String sourcesPath, String templatesPath) {
		if (file.isDirectory()) {
			if (directory == null)
				directory = "";
			else
				directory += File.separator + file.getName();
			
			File[] files = file.listFiles();
			for (File subFile : files) {
				this.copyProjectTemplates(
						subFile, 
						directory,
						sourcesPath,
						templatesPath);				
			}
		}
		else {
			String tplPath = templatesPath
					+ File.separator + directory
					+ File.separator + file.getName();
			
			String srcPath = sourcesPath
					+ File.separator + directory
					+ File.separator + file.getName();
			
			tplPath = tplPath.substring(0, tplPath.length() 
					- ".ftl".length());
			srcPath = srcPath.substring(0, srcPath.length() 
					- ".ftl".length());
			
			super.makeSource(
					tplPath,
					srcPath,
					false);
		}
	}

	/**
	 * Make IOS Project Structure.
	 * @return success to make the platform project folder
	 */
	protected final boolean makeProjectIOS() {
		boolean result = false;
		//Generate base folders & files
		final File dirProj = TactFileUtils.makeFolderRecursive(
				String.format("%s/%s/%s/",
						Harmony.getTemplatesPath(),
						this.getAdapter().getPlatform(), 
						this.getAdapter().getProject()),
				String.format("%s/%s/",
						Harmony.getProjectPath(), 
						this.getAdapter().getPlatform()),
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
	
	/**
	 * Updates the local.properties SDK Path with the SDK Path stored
	 * in the ApplicationMetadata.
	 */
	public static final void updateSDKPath() {
		final File fileProp = new File(
				String.format("%s/%s",
						Harmony.getProjectAndroidPath(), 
						"local.properties"));
		
		if (fileProp.exists()) {
			final List<String> lines = 
					TactFileUtils.fileToStringArray(fileProp);
			
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).startsWith("sdk.dir=")) {
					lines.set(i, "sdk.dir="
				+ ApplicationMetadata.getAndroidSdkPath());
					break;
				}
			}
			
			TactFileUtils.stringArrayToFile(lines, fileProp);
		}
	}

	public final void updateDependencies() {
		try {
			/** Only Android is handled for now
				Create libs folder if it doesn't exists*/
			TactFileUtils.makeFolder(this.getAdapter().getLibsPath());
			this.addLibs();
		} catch (Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
}
