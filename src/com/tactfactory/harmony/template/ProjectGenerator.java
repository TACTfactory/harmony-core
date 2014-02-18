/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.harmony.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Generator class for the project.
 *
 */
public class ProjectGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use for the generation.
	 * @throws Exception if adapter is null
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

			ConsoleUtils.display(
					"Project " + this.getAdapter().getPlatform() + " removed!");
		} else {
			ConsoleUtils.display(
					"An error has occured while deleting the project.");
		}
		return result;
	}

	/**
	 * Generate HomeActivity File and merge it with datamodel.
	 */
	public final void generateHomeActivity() {
		ConsoleUtils.display(">> Generate HomeView & Strings...");

		String fullFilePath = this.getAdapter().getHomeActivityPathFile();
		String fullTemplatePath =
				this.getAdapter().getTemplateHomeActivityPathFile();

		super.makeSource(fullTemplatePath, fullFilePath, true);
		
		// create main.xml
		super.makeSource(
				this.getAdapter().getTemplateRessourceLayoutPath() + "main.xml",
				this.getAdapter().getRessourceLayoutPath() + "main.xml",
				true);
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

		// create configs.xml
		super.makeSource(
				this.getAdapter().getTemplateRessourceXLargeValuesPath()
					+ "configs.xml",
				this.getAdapter().getRessourceXLargeValuesPath() + "configs.xml",
				false);
		
		// create strings.xml
		super.makeSource(
				this.getAdapter().getTemplateStringsPathFile(),
				this.getAdapter().getStringsPathFile(),
				false);

		// create main.xml
		super.makeSource(
				this.getAdapter().getTemplateRessourceLayoutPath() + "main.xml",
				this.getAdapter().getRessourceLayoutPath() + "main.xml",
				false);

		super.makeSource(
				this.getAdapter().getTemplateSourcePath()
				+ "harmony/view/package-info.java",
				this.getAdapter().getSourcePath()
				+ this.getAppMetas().getProjectNameSpace()
				+ "/harmony/view/"
				+ "package-info.java",
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
		
		super.makeSource(
				this.getAdapter().getTemplateSourcePath()
				+ "harmony/view/MultiLoader.java",
				this.getAdapter().getSourcePath()
				+ this.getAppMetas().getProjectNameSpace()
				+ "/harmony/view/"
				+ "MultiLoader.java",
				false);
		
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/view/HarmonyGridFragment.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/view/"
			+ "HarmonyGridFragment.java",
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

		// create NotImplementedException
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/exception/NotImplementedException.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/exception/NotImplementedException.java",
			false);
		
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/exception/package-info.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/exception/package-info.java",
			false);
		
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "harmony/util/package-info.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/util/package-info.java",
			false);
		
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "widget/package-info.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/harmony/widget/package-info.java",
			false);
		
		super.makeSource(
			this.getAdapter().getTemplateSourcePath()
			+ "package-info.java",
			this.getAdapter().getSourcePath()
			+ this.getAppMetas().getProjectNameSpace()
			+ "/package-info.java",
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
		this.updateLibrary("joda-time-2.3.jar");
		this.updateLibrary("guava-12.0.jar");
		this.updateLibrary("jsr305.jar");
		this.updateLibrary("core-annotations.jar");

		/// copy sherlock library
		//this.installAndroidSherlockLib();
		

		String pathLib = new File(String.format("%s%s",
				this.getAdapter().getLibsPath(),
				"sherlock")).getAbsolutePath();
		List<File> filesToDelete = new ArrayList<File>();
		filesToDelete.add(new File(String.format("%s/%s",
							pathLib,
							"samples")));
		
		this.getAdapter().installGitLibrary(
				"https://github.com/JakeWharton/ActionBarSherlock.git",
				pathLib,
				"4.2.0",
				ApplicationMetadata.INSTANCE.getName() + "-abs",
				filesToDelete,
				pathLib + "/library",
				null,
				pathLib + "/library",
				true);
		
		String srcPath = Harmony.getTemplatesPath()
							+ "/android/libs/sherlock_ant.properties";
		String destPath = pathLib + "/library/" + "ant.properties";
		this.makeSource(srcPath, destPath, false);

		srcPath = Harmony.getTemplatesPath()
						+ "/android/libs/sherlock_.project";
		destPath = pathLib + "/library/" + ".project";
		this.makeSource(srcPath, destPath, false);
	}

	/**
	 * Add base drawables.
	 */
	private void addBaseDrawables() {
		final String resourcePath = String.format("%s",
				this.getAdapter().getRessourcePath());
		final String templateResourcePath =  String.format("%s/%s/%s",
				Harmony.getBundlePath(),
				"tact-core",
				this.getAdapter().getTemplateRessourcePath());

		try {
			TactFileUtils.copyDirectory(
					new File(String.format("%s/%s/",
							templateResourcePath,
							"drawable-hdpi")),
					new File(String.format("%s/%s/",
							resourcePath,
							"drawable-hdpi")));

			TactFileUtils.copyDirectory(
					new File(String.format("%s/%s/",
							templateResourcePath,
							"drawable-mdpi")),
					new File(String.format("%s/%s/",
							resourcePath,
							"drawable-mdpi")));

			TactFileUtils.copyDirectory(
					new File(String.format("%s/%s/",
							templateResourcePath,
							"drawable-ldpi")),
					new File(String.format("%s/%s/",
							resourcePath,
							"drawable-ldpi")));

			TactFileUtils.copyDirectory(
					new File(String.format("%s/%s/",
							templateResourcePath,
							"drawable-xhdpi")),
					new File(String.format("%s/%s/",
							resourcePath,
							"drawable-xhdpi")));

			TactFileUtils.copyDirectory(
					new File(String.format("%s/%s/",
							templateResourcePath,
							"drawable-xxhdpi")),
					new File(String.format("%s/%s/",
							resourcePath,
							"drawable-xxhdpi")));
			


			TactFileUtils.copyDirectory(
					new File(String.format("%s/%s/",
							templateResourcePath,
							"drawable-xlarge")),
					new File(String.format("%s/%s/",
							resourcePath,
							"drawable-xlarge")));
		} catch (IOException e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Initialize git project.
	 */
	private void initGitProject() {
		final File projectFolder = new File(Harmony.getProjectAndroidPath());
		try {
			Git.init().setDirectory(projectFolder).call();
		} catch (GitAPIException e) {
			ConsoleUtils.displayError(e);
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
		this.initGitProject();
		this.addBaseDrawables();

		// copy utils
		this.updateUtil("DateUtils.java");
		this.updateUtil("DatabaseUtil.java");

		// Update newly created files with datamodel
		final File dirTpl =
				new File(Harmony.getBundlePath() + "tact-core/"
						+ this.getAdapter().getTemplateProjectPath());
		if (dirTpl.exists() && dirTpl.listFiles().length > 0
				&& this.clearProjectSources()) {
			this.copyProjectTemplates(dirTpl, null,
					Harmony.getProjectPath()
						+ File.separator + this.getAdapter().getPlatform(),
					this.getAdapter().getTemplateProjectPath());
			result = true;
		}

		this.addLibs();

		// Make Test project

		try {
			new TestDBGenerator(this.getAdapter()).initTestAndroid();
		} catch (Exception e) {
			ConsoleUtils.displayError(e);
		}

		return result;
	}

	/**
	 * Delete files that need to be recreated.
	 * @return true if project cleaning successful
	 */
	private boolean clearProjectSources() {
		boolean result = true;

		final String projectPath = Harmony.getProjectPath()
				+ File.separator + this.getAdapter().getPlatform();

		final File buildRules = new File(projectPath
				+ File.separator + "build.rules.xml");

		if (buildRules.exists()) {
			result &= buildRules.delete();
		}

		return result;
	}

	/**
	 * Copy files with recursive.
	 * @param file File to copy
	 * @param directory Directory of the file (null if first)
	 * @param sourcesPath Directory of sources files
	 * @param templatesPath Directory of templates files
	 */
	private void copyProjectTemplates(
			final File file,
			final String directory,
			final String sourcesPath,
			final String templatesPath) {
		String folder = directory;
		if (file.isDirectory()) {
			if (folder == null) {
				folder = "";
			} else {
				folder += File.separator + file.getName();
			}

			final File[] files = file.listFiles();
			for (File subFile : files) {
				this.copyProjectTemplates(
						subFile,
						folder,
						sourcesPath,
						templatesPath);
			}
		} else {
			String tplPath = templatesPath
					+ File.separator + folder
					+ File.separator + file.getName();

			String srcPath = sourcesPath
					+ File.separator + folder
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

	/**
	 * Update the project dependencies.
	 * (WARNING : ONLY ANDROID IS HANDLED FOR NOW)
	 */
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
