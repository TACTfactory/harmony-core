/**
 * This file is part of the Harmony package.
 */
package com.tactfactory.mda.template;

import java.io.File;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.TactFileUtils;

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
		
		this.updateLibrary("android-junit-report-1.5.8.jar");
		
		final File dirTpl = 
				new File(Harmony.PATH_HARMONY + "/"
						+ this.getAdapter().getTemplateTestProjectPath());

		// Update newly created files with datamodel
		if (dirTpl.exists() && dirTpl.listFiles().length != 0) {
			result = true;
			for (int i = 0; i < dirTpl.listFiles().length; i++) {
				if (dirTpl.listFiles()[i].isFile()) {
					final String fullFilePath = String.format("%s/%s/%s/%s", 
							Harmony.PATH_PROJECT, 
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
						Harmony.PATH_TEMPLATE,
						this.getAdapter().getPlatform(),
						this.getAdapter().getProject()),
				String.format("%s/%s/", 
						Harmony.PATH_PROJECT, 
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
							Harmony.PATH_LIBS,
							libName)),
					dest);
		}
	}
}
