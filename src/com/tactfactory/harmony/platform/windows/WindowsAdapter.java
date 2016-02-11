/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.windows;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.ImageUtils;
import com.tactfactory.harmony.utils.TactFileUtils;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.platform.manipulator.CsharpFileManipulator;
import com.tactfactory.harmony.platform.manipulator.SourceFileManipulator;

import freemarker.template.Configuration;

/**
 * Microsoft Adapter of project structure.
 *
 */
public class WindowsAdapter extends BaseAdapter {
	/** Float type. */
    private static final String FLOAT = "float";

    /** String type. */
    private static final String STR = "string";

    /** Int type. */
    private static final String INT = "int";
    
    /** Byte type. */
    private static final String BYTE = "byte";

    /** DateTime type. */
    private static final String DATETIME = "DateTime";
    
    /** Constant for Drawable folder */
    private static final String DRAWABLE_FOLDER = "/drawable";

    /** Constant for XHDPI drawable folder */
    private static final String XHDPI_FOLDER = DRAWABLE_FOLDER + "-xhdpi";
    
    /** Constant for HDPI drawable folder */
    private static final String HDPI_FOLDER = DRAWABLE_FOLDER + "-hdpi";
    
    /** Constant for MDPI drawable folder */
    private static final String MDPI_FOLDER = DRAWABLE_FOLDER + "-mdpi";
    
    /** Constant for LDPI drawable folder */
    private static final String LDPI_FOLDER = DRAWABLE_FOLDER + "-ldpi";
    
    /** Ratio for HD images resizing. */
    private static final float HD_RATIO = 0.75f;

    /** Ratio for MD images resizing. */
    private static final float MD_RATIO = 0.50f;

    /** Ratio for LD images resizing. */
    private static final float LD_RATIO = 0.375f;
    
    /**
     * FilenameFilter for images.
     */
    private final FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(final File dir, final String name) {
            return  name.endsWith(".png") || name.endsWith(".jpg");
        }
    };
    
    private IAdapterProject adapterProject = new WindowsProjectAdapter(this);
    
	/**
	 * Constructor.
	 */
	public WindowsAdapter() {
		super();
		this.setProject("project");
		this.setResource("res");
        this.setLibs("libs");
        this.setTest("test");
        this.setTestLibs("libs");
        this.setHarmony("Harmony");
        this.setWidget("widget");
        this.setUtil("Util");
        this.setUtilityPath("ftl_methods");
        this.setMenu("menu");
        
        this.setPlatform("windows");
        this.setAssets("Assets");
        this.setFixture("Fixture");
        this.setSource("src");
        this.setData("Data");
        this.setController("View");
        this.setValues("Values");
        this.setStrings("StringsResources.resx");
        this.setConfigs("ConfigsResources.resx");
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
    public String getNativeType(final FieldMetadata field) {
        String ret = field.getHarmonyType();

        if (ret.equals(Column.Type.STRING.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.TEXT.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.CHAR.getValue())) {
            ret = STR;
        } else
        
    	if (ret.equals(Column.Type.BYTE.getValue())) {
            ret = BYTE;
        } else

        if (ret.equals(Column.Type.INTEGER.getValue())) {
            ret = INT;
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
            ret = INT;
        } else

        if (ret.equals(Column.Type.COUNTRY.getValue())) {
            ret = STR;
        } else

        if (ret.equals(Column.Type.BC_EAN.getValue())) {
            ret = INT;
        }
        return ret;
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
	public boolean filesEqual(String oldContent, String newContent,
			String fileName, boolean ignoreHeader) {
		return oldContent.equals(newContent);
	}

	@Override
	public SourceFileManipulator getFileManipulator(
			final File file,
			final Configuration config) {
	    return new CsharpFileManipulator(file, this, config);
	}

    @Override
    public List<String> getDirectoryForResources() {
        List<String> result = new ArrayList<String>();
        
        result.add("Drawable");
        
        return result;
    }

    @Override
    public IAdapterProject getAdapterProject() {
        return this.adapterProject;
    }

    @Override
    public String getSourceEntityPath() {
        return String.format("%s%s/%s/",
                this.getSourcePath(),
                this.getApplicationMetadata().getProjectNameSpace(),
                "entity");
    }
    
    @Override
    public final String getSourcePath() {
        return String.format("%s%s/",
                Harmony.getProjectPath(),
                this.getPlatform());
    }
    
    @Override
    public final String getRessourcePath() {
        return String.format("%s%s/Resources/",
                Harmony.getProjectPath(),
                this.getPlatform());
    }
    
    @Override
    public final String getSourceControllerPath() {
        return String.format("%s%s/",
                this.getSourcePath(),
                this.getController());
    }
    
    @Override
    public final String getStringsPathFile() {
        return String.format("%s%s/Resources/%s/%s",
                Harmony.getProjectPath(),
                this.getPlatform(),
                this.getValues(),
                this.getStrings());
    }
    
    public String getUtilPath() {
		return String.format("%s%s/%s/",
				this.getSourcePath(),
				this.getHarmony(),
				this.getUtil());
	}
}
