/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.plateforme.android;

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
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.IAdapterProject;
import com.tactfactory.harmony.updater.impl.LibraryGit;
import com.tactfactory.harmony.plateforme.android.AndroidProjectAdapter.LibraryGitAndroid;
import com.tactfactory.harmony.plateforme.manipulator.JavaFileManipulator;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.ImageUtils;
import com.tactfactory.harmony.utils.OsUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;

/** Google Android Adapter of project structure. */
public final class AndroidAdapter extends BaseAdapter {

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
	public void installGitLibrary(LibraryGit library) {
	    String androidTarget = null;
	    String androidReferencePath = null;
	    boolean androidIsSupportV4 = false;
	    
	    if (library instanceof LibraryGitAndroid) {
	        LibraryGitAndroid libAndroid = (LibraryGitAndroid) library;
	        androidTarget = libAndroid.getAndroidTarget();
	        androidReferencePath = libAndroid.getAndroidReferencePath();
	        androidIsSupportV4 = libAndroid.isAndroidIsSupportV4Dependant();
	    }
	    
	    this.installGitLibrary(
	            library.getUrl(),
	            library.getPath(),
	            library.getBranch(),
	            library.getName(),
	            library.getFilesToDelete(),
	            library.getLibraryPath(),
	            androidTarget,
	            androidReferencePath,
	            androidIsSupportV4);
	}
	
	private void installGitLibrary(String url,
			String pathLib,
			String versionTag,
			String libName,
			List<File> filesToDelete,
			String libraryProjectPath,
			String target,
			String referencePath,
			boolean isSupportV4Dependant) {

		if (!TactFileUtils.exists(pathLib)) {
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
		}
	}

	@Override
	public String getNativeType(final String type) {
		String ret = type;

		if (type.equals(Column.Type.STRING.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.TEXT.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.INTEGER.getValue())) {
			ret = INT;
		} else

		if (type.equals(Column.Type.INT.getValue())) {
			ret = INT;
		} else

		if (type.equals(Column.Type.FLOAT.getValue())) {
			ret = FLOAT;
		} else

		if (type.equals(Column.Type.DATETIME.getValue())) {
			ret = DATETIME;
		} else

		if (type.equals(Column.Type.DATE.getValue())) {
			ret = DATETIME;
		} else

		if (type.equals(Column.Type.TIME.getValue())) {
			ret = DATETIME;
		} else

		if (type.equals(Column.Type.LOGIN.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.PASSWORD.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.EMAIL.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.PHONE.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.CITY.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.ZIPCODE.getValue())) {
			ret = INT;
		} else

		if (type.equals(Column.Type.COUNTRY.getValue())) {
			ret = STR;
		} else

		if (type.equals(Column.Type.BC_EAN.getValue())) {
			ret = INT;
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

    @Override
    public List<String> getDirectoryForResources() {
        List<String> result = new ArrayList<String>();
        
        result.add("drawable-hdpi");
        result.add("drawable-ldpi");
        result.add("drawable-mdpi");
        result.add("drawable-xhdpi");
        result.add("drawable-xxhdpi");
        
        return result;
    }
    
    private IAdapterProject adapterProject = new AndroidProjectAdapter(this);
    public IAdapterProject getAdapterProject() {
        return this.adapterProject;
    }
    
    /**
     * Get the home activity path.
     * @return The home activity path
     */
    public final String getHomeActivityPathFile() {
        return String.format("%s/%s/%s",
                this.getSourcePath(),
                this.getApplicationMetadata().getProjectNameSpace(),
                this.getHome());
    }

    /**
     * Get the home activity template path.
     * @return The home activity template path
     */
    public final String getTemplateHomeActivityPathFile() {
        return String.format("%s/%s",
                this.getTemplateSourcePath(),
                this.getHome());
    }

    /**
     * Get the resource's layouts path.
     * @return The resource's layouts path
     */
    public final String getRessourceLayoutPath() {
        return String.format("%s/%s/",
                this.getRessourcePath(),
                this.getView());
    }
    
    /**
     * Get the resource's layouts path.
     * @return The resource's layouts path
     */
    public final String getRessourceLargeLayoutPath() {
        return String.format("%s/%s/",
                this.getRessourcePath(),
                this.getLargeView());
    }

    /**
     * Get the resources' layouts' templates path.
     * @return The resources' layouts' templates path
     */
    public final String getTemplateRessourceLayoutPath() {
        return String.format("%s/%s/",
                this.getTemplateRessourcePath(),
                this.getView());
    }

    
    /**
     * Get the resources' layouts' templates path.
     * @return The resources' layouts' templates path
     */
    public final String getTemplateRessourceLargeLayoutPath() {
        return String.format("%s/%s/",
                this.getTemplateRessourcePath(),
                this.getLargeView());
    }

    /**
     * Get the resources' values path.
     * @return The resources values path
     */
    public final String getRessourceValuesPath() {
        return String.format("%s/%s/",
                this.getRessourcePath(),
                this.getValues());
    }
    
    /**
     * Get the resources' values path.
     * @return The resources values path
     */
    public final String getRessourceXLargeValuesPath() {
        return String.format("%s/%s/",
                this.getRessourcePath(),
                this.getValuesXLarge());
    }

    /**
     * Get the resources' values' templates path.
     * @return The resources values' templates path
     */
    public final String getTemplateRessourceValuesPath() {
        return String.format("%s/%s/",
                this.getTemplateRessourcePath(),
                this.getValues());
    }

    /**
     * Get the resources' values-xlarge' templates path.
     * @return The resources values-xlarge' templates path
     */
    public final String getTemplateRessourceXLargeValuesPath() {
        return String.format("%s/%s/",
                this.getTemplateRessourcePath(),
                this.getValues());
    }
    
    @Override
    public final String getSourceEntityPath() {
        return String.format("%s%s/%s/",
                this.getSourcePath(),
                this.getApplicationMetadata().getProjectNameSpace(),
                "entity");
    }
}
