/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.dependencies.android.sdk.AndroidSDKManager;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.plateforme.manipulator.JavaFileManipulator;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.GitUtils;
import com.tactfactory.harmony.utils.GitUtils.GitException;
import com.tactfactory.harmony.utils.ImageUtils;
import com.tactfactory.harmony.utils.OsUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;

/** Google Android Adapter of project structure. */
public final class AndroidAdapter extends BaseAdapter {

	/* Constants. */
	/** Constant for java extension. */
	private static final String JAVA_EXTENSION = "java";

	/** Constant for package keyword. */
	private static final String PACKAGE = "package";
	
	/**	Constant for Drawable folder */
	private static final String DRAWABLE_FOLDER = "/drawable";
	
	/**	Constant for XHDPI drawable folder */
	private static final String XHDPI_FOLDER = DRAWABLE_FOLDER + "-xhdpi";
	
	/**	Constant for HDPI drawable folder */
	private static final String HDPI_FOLDER = DRAWABLE_FOLDER + "-hdpi";
	
	/**	Constant for MDPI drawable folder */
	private static final String MDPI_FOLDER = DRAWABLE_FOLDER + "-mdpi";
	
	/**	Constant for LDPI drawable folder */
	private static final String LDPI_FOLDER = DRAWABLE_FOLDER + "-ldpi";

	/** GIT command. */
	protected static final String GIT = "git";
	
	/** Ratio for HD images resizing. */
	private static final float HD_RATIO = 0.75f;

	/** Ratio for MD images resizing. */
	private static final float MD_RATIO = 0.50f;

	/** Ratio for LD images resizing. */
	private static final float LD_RATIO = 0.375f;

	/** Float type. */
	private static final String FLOAT = "float";

	/** String type. */
	private static final String STR = "String";

	/** Int type. */
	private static final String INT = "int";

	/** DateTime type. */
	private static final String DATETIME = "DateTime";

	/**
	 * FilenameFilter for images.
	 */
	private final FilenameFilter filter = new FilenameFilter() {
	    @Override
		public boolean accept(final File dir, final String name) {
	        return	name.endsWith(".png")
	        		|| name.endsWith(".jpg");
	    }
	};

	/**
	 * Constructor.
	 */
	public AndroidAdapter() {
		super();
		// Structure
		this.setProject("project");
		this.setPlatform("android");
		this.setResource("res");
		this.setAssets("assets");
		this.setSource("src");
		this.setLibs("libs");
		this.setTest("test");
		this.setTestLibs("libs");
		this.setHarmony("harmony");
		this.setWidget("widget");
		this.setUtil("util");
		this.setUtilityPath("ftl_methods");
		this.setMenu("menu");

		// MVC
		//this.model 		= "entity";
		//this.view 		= "layout";
		//this.controller	= "view";
		//this.data			= "data";
		//this.provider		= "provider";

		// File
		this.setManifest("AndroidManifest.xml");
		this.setStrings("strings.xml");
		this.setHome("HomeActivity.java");
		this.setConfigs("configs.xml");
	}

	@Override
	public String getNameSpaceEntity(final ClassMetadata cm,
			final String type) {
		return String.format("%s.%s",
				this.getNameSpace(cm, type),
				cm.getName().toLowerCase());
	}

	@Override
	public String getNameSpace(final ClassMetadata cm, final String type) {
		return String.format("%s.%s",
				cm.getSpace(),
				type);
	}
	
	
	@Override
	public void installGitLibrary(String url,
			String pathLib,
			String versionTag,
			String libName,
			List<File> filesToDelete,
			String libraryProjectPath,
			String target,
			String referencePath,
			boolean isSupportV4Dependant) {		

		if (!TactFileUtils.exists(pathLib)) {
			try {
				final File projectFolder = new File(Harmony.getProjectAndroidPath());
				
				GitUtils.cloneRepository(pathLib, url, versionTag);
				GitUtils.addSubmodule(
						projectFolder.getAbsolutePath(), pathLib, url);
				
				// Delete useless files
				if (filesToDelete != null) {
					for (File fileToDelete : filesToDelete) {
						TactFileUtils.deleteRecursive(fileToDelete);
					}
				}

				ArrayList<String> command = new ArrayList<String>();
				//make build sherlock
				String sdkTools = String.format("%s/%s",
						ApplicationMetadata.getAndroidSdkPath(),
						"tools/android");
				if (OsUtil.isWindows()) {
					sdkTools += ".bat";
				}
	
				command.add(new File(sdkTools).getAbsolutePath());
				command.add("update");
				command.add("project");
				command.add("--path");
				command.add(libraryProjectPath);
				command.add("--name");
				command.add(libName);
				
				if (target != null) {
					command.add("--target");
					command.add(target);
				}
				
				ConsoleUtils.launchCommand(command);
				command.clear();
				
				if (isSupportV4Dependant) {
					AndroidSDKManager.copySupportV4Into(
							libraryProjectPath + "/libs/");
				}
			
				if (referencePath != null) {
					// Update android project to reference the new downloaded library
					String projectPath = Harmony.getProjectPath() + this.getPlatform();
					command.add(new File(sdkTools).getAbsolutePath());
					command.add("update");
					command.add("project");
					command.add("--path");
					command.add(projectPath);
					command.add("--library");
					command.add(TactFileUtils.absoluteToRelativePath(
							referencePath, projectPath));
					ConsoleUtils.launchCommand(command);
					command.clear();
				}
			} catch (IOException e) {
				ConsoleUtils.displayError(e);
			} catch (GitException e) {
				ConsoleUtils.displayError(e);
			}
		}
	}

	@Override
	public String getNativeType(final FieldMetadata field) {
		String ret = field.getHarmonyType();
		if (ret.equals(Column.Type.ENUM.getValue())) {
			ret = field.getEnumMeta().getTargetEnum();
		} else
			
		if (ret.equals(Column.Type.RELATION.getValue())) {
			if (field.getRelation().getType().endsWith("ToMany")) {
				ret = String.format("ArrayList<%s>",
						field.getRelation().getEntityRef().getName());
			} else {
				ret = field.getRelation().getEntityRef().getName();
			}
		} else
			
		if (ret.equals(Column.Type.STRING.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.TEXT.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.INT.getValue())) {
			if (field.isPrimitive()) {
				ret = INT;
			} else {
				ret = "Integer";
			}
		} else

		if (ret.equals(Column.Type.FLOAT.getValue())) {
			ret = FLOAT;
		} else

		if (ret.equals(Column.Type.DATETIME.getValue())) {
			ret = DATETIME;
		} else

		if (ret.equals(Column.Type.DATE.getValue())) {
			ret = DATETIME;
		} else

		if (ret.equals(Column.Type.TIME.getValue())) {
			ret = DATETIME;
		} else

		if (ret.equals(Column.Type.LOGIN.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.PASSWORD.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.EMAIL.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.PHONE.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.CITY.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.ZIPCODE.getValue())) {
			if (field.isPrimitive()) {
				ret = INT;
			} else {
				ret = "Integer";
			}
		} else

		if (ret.equals(Column.Type.COUNTRY.getValue())) {
			ret = STR;
		} else

		if (ret.equals(Column.Type.BC_EAN.getValue())) {
			if (field.isPrimitive()) {
				ret = INT;
			} else {
				ret = "Integer";
			}
		} else
		
		if (ret.equals(Column.Type.CHAR.getValue())) {

			if (field.isPrimitive()) {
				ret = "char";
			} else {
				ret = "Character";
			}
		} else
			
		if (ret.equals(Column.Type.FLOAT.getValue())) {
			if (field.isPrimitive()) {
				ret = "float";
			} else {
				ret = "Float";
			}
		}
		
		if (ret.equals(Column.Type.DOUBLE.getValue())) {
			if (field.isPrimitive()) {
				ret = "double";
			} else {
				ret = "Double";
			}
		} else
			
		if (ret.equals(Column.Type.LONG.getValue())) {
			if (field.isPrimitive()) {
				ret = "long";
			} else {
				ret = "Long";
			}
		}
		
		if (ret.equals(Column.Type.BOOLEAN.getValue())) {
			if (field.isPrimitive()) {
				ret = "boolean";
			} else {
				ret = "Boolean";
			}
		}
		return ret;
	}
	
	@Override
	public boolean filesEqual(String oldContent,
			String newContent,
			String fileName,
			boolean ignoreHeader) {
		boolean result = false;
		
		if (ignoreHeader && fileName.endsWith(JAVA_EXTENSION)) {
			oldContent = oldContent.substring(
					Math.max(oldContent.indexOf(PACKAGE), 0));
			newContent = newContent.substring(
					Math.max(newContent.indexOf(PACKAGE), 0));
		}
		
		result = oldContent.equals(newContent);
		return result;
	}



	@Override
	public void resizeImage() {
		final File imageDirectoryXHD
				= new File(this.getRessourcePath() + XHDPI_FOLDER);
		final File imageDirectoryHD
				= new File(this.getRessourcePath() + HDPI_FOLDER);
		final File imageDirectoryMD
				= new File(this.getRessourcePath() + MDPI_FOLDER);
		final File imageDirectoryLD
				= new File(this.getRessourcePath() + LDPI_FOLDER);

		File imageHD;
		File imageMD;
		File imageLD;

		if (imageDirectoryXHD.exists()
				&& imageDirectoryXHD.listFiles().length > 0) {
			final File[] imagesFiles = imageDirectoryXHD.listFiles(this.filter);
			TactFileUtils.makeFolder(imageDirectoryHD.getAbsolutePath());
			TactFileUtils.makeFolder(imageDirectoryMD.getAbsolutePath());
			TactFileUtils.makeFolder(imageDirectoryLD.getAbsolutePath());

			for (final File imageXHD : imagesFiles) {
				try {
					imageHD = new File(imageDirectoryHD.getCanonicalPath()
							+ "/"
							+ imageXHD.getName());

					imageMD = new File(imageDirectoryMD.getCanonicalPath()
							+ "/"
							+ imageXHD.getName());

					imageLD = new File(imageDirectoryLD.getCanonicalPath()
							+ "/"
							+ imageXHD.getName());

					ImageUtils.resize(imageXHD, imageHD, HD_RATIO);
					ImageUtils.resize(imageXHD, imageMD, MD_RATIO);
					ImageUtils.resize(imageXHD, imageLD, LD_RATIO);

				} catch (final IOException e) {
					// TODO Auto-generated catch block
					ConsoleUtils.displayError(e);
				}

			}
		}
	}
	
	@Override
	public SourceFileManipulator getFileManipulator(
			final File file,
			final Configuration config) {
		return new JavaFileManipulator(file, this, config);
	}
}
