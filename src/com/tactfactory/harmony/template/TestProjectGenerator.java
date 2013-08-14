/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.harmony.template;

import java.io.File;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.utils.LibraryUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Test project generator.
 *
 */
public class TestProjectGenerator extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception
	 */
	public TestProjectGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);

		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
	}

	/**
	 * Make Platform specific Project Structure.
	 * @return success to make the platform test project folder
	 */
	public final boolean makeProject() {
		boolean result = false;
		if (this.getAdapter().getPlatform().equals("android")) {
			result = this.makeTestProjectAndroid();
		} else if (this.getAdapter().getPlatform().equals("ios")) {
			result = this.makeTestProjectIOS();
		} else if (this.getAdapter().getPlatform().equals("rim")) {
			result = this.makeTestProjectRIM();
		} else if (this.getAdapter().getPlatform().equals("winphone")) {
			result = this.makeTestProjectWinPhone();
		}

		return result;
	}

	/**
	 * Make Android Test Project Structure.
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectAndroid() {
		boolean result = false;

		// create project name space folders
		//FileUtils.makeFolder(this.getAdapter().getSourcePath()
		// + Harmony.projectNameSpace.replaceAll("\\.", "/"));

		// create libs folder
		TactFileUtils.makeFolder(this.getAdapter().getTestLibsPath());

		// create strings.xml
		super.makeSource(
				this.getAdapter().getTemplateStringsTestPathFile(),
				this.getAdapter().getStringsTestPathFile(), false);

		LibraryUtils.addLibraryToTestProject(
				this.getAdapter(),
				"android-junit-report-1.5.8.jar");

		final File dirTpl =
				new File(Harmony.getBundlePath() + "tact-core/"
						+ this.getAdapter().getTemplateTestProjectPath());

		// Update newly created files with datamodel
		if (dirTpl.exists() && dirTpl.listFiles().length != 0
				&& this.clearProjectSources()) {
			for (int i = 0; i < dirTpl.listFiles().length; i++) {
				if (dirTpl.listFiles()[i].isFile()) {
					final String fullFilePath = String.format("%s/%s/%s/%s",
							Harmony.getProjectPath(),
							this.getAdapter().getPlatform(),
							this.getAdapter().getTest(),
							dirTpl.listFiles()[i].getName());

					final String fullTemplatePath =
							this.getAdapter().getTemplateTestProjectPath()
							 + dirTpl.listFiles()[i].getName();

					super.makeSource(
							fullTemplatePath.substring(
									0,
									fullTemplatePath.length()
										- ".ftl".length()),
							fullFilePath.substring(
									0,
									fullFilePath.length()
										- ".ftl".length()),
								false);
				}
			}
			result = true;
		}
		return result;
	}

	/**
	 * Make IOS Test Project Structure.
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectIOS() {
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
	 * Make RIM Test Project Structure.
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectRIM() {
		final boolean result = false;

		return result;
	}

	/**
	 * Make Windows Phone Test Project Structure.
	 * @return success to make the platform test project folder
	 */
	private boolean makeTestProjectWinPhone() {
		final boolean result = false;

		return result;
	}

	/**
	 * Delete files that need to be recreated.
	 * @return true if project cleaning successful
	 */
	private boolean clearProjectSources() {
		boolean result = true;

		String projectPath = Harmony.getProjectPath()
				+ File.separator + this.getAdapter().getPlatform()
				+ File.separator + this.getAdapter().getTest();

		File buildRules = new File(projectPath
				+ File.separator + "build.rules.xml");

		if (buildRules.exists()) {
			result &= buildRules.delete();
		}

		return result;
	}

	/**
	 * Update TestLibs.
	 * @param libName The library name.
	 */
	@Override
	protected final void updateLibrary(final String libName) {
		final File dest = new File(String.format("%s/%s",
				this.getAdapter().getTestLibsPath(),
				libName));

		if (!dest.exists()) {
			TactFileUtils.copyfile(
					new File(String.format("%s/%s",
							Harmony.getLibsPath(),
							libName)),
					dest);
		}
	}
}
