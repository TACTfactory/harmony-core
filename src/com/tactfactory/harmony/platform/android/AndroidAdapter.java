/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.android;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.platform.BaseAdapter;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.platform.manipulator.JavaFileManipulator;
import com.tactfactory.harmony.platform.manipulator.SourceFileManipulator;
import com.tactfactory.harmony.updater.impl.CopyFile;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.ImageUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;

/** Google Android Adapter of project structure. */
public class AndroidAdapter extends BaseAdapter {

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
        this.setSource("app/src/main/java");
        this.setLibs("libs");
        this.setTest("app/src/test/java");
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

    // Begin path overrides.

    @Override
    public String getStringsTestPathFile() {
        String result = String.format("%s%s/%s/../%s/%s/%s",
                Harmony.getProjectPath(),
                this.getPlatform(),
                this.getTest(),
                this.getResource(),
                this.getValues(),
                this.getStrings());

        return result;
    }

    /**
     * Get the strings tests templates path.
     * @return The strings tests templates path
     */
    @Override
    public final String getTemplateStringsTestPathFile() {
        String result = String.format("%s%s/%s/../%s/%s/%s",
                Harmony.getTemplatesPath(),
                this.getPlatform(),
                this.getTest(),
                this.getResource(),
                this.getValues(),
                this.getStrings());
        return result;
    }

    /**
     * Get the resource path.
     * @return The resource path
     */
    @Override
    public String getRessourcePath() {
        String result = String.format("%s%s/%s/../%s/",
                Harmony.getProjectPath(),
                this.getPlatform(),
                this.getSource(),
                this.getResource());

        return result;
    }

    /**
     * Get the resource's templates path.
     * @return The resource's templates path
     */
    @Override
    public final String getTemplateRessourcePath() {
        return String.format("%s%s/%s/../%s/",
                Harmony.getTemplatesPath(),
                this.getPlatform(),
                this.getSource(),
                this.getResource());
    }

    /**
     * Get the strings template path.
     * @return The strings template path
     */
    @Override
    public final String getTemplateStringsPathFile() {
        return String.format("%s%s/%s/../%s/%s/%s",
                Harmony.getTemplatesPath(),
                this.getPlatform(),
                this.getSource(),
                this.getResource(),
                this.getValues(),
                this.getStrings());
    }

    /**
     * Get the configs path.
     * @return The configs path
     */
    @Override
    public final String getConfigsPathFile() {
        return String.format("%s%s/%s/../%s/%s/%s",
                Harmony.getProjectPath(),
                this.getPlatform(),
                this.getSource(),
                this.getResource(),
                this.getValues(),
                this.getConfigs());
    }

    /**
     * Get the configs template path.
     * @return The configs template path
     */
    @Override
    public final String getTemplateConfigsPathFile() {
        return String.format("%s%s/%s/../%s/%s/%s",
                Harmony.getTemplatesPath(),
                this.getPlatform(),
                this.getSource(),
                this.getResource(),
                this.getValues(),
                this.getConfigs());
    }

    // End of path overrides.

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

        if (ret.equals(Column.Type.INTEGER.getValue())) {
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
    @Override
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
                this.getValuesXLarge());
    }

    @Override
    public final String getSourceEntityPath() {
        return String.format("%s%s/%s/",
                this.getSourcePath(),
                this.getApplicationMetadata().getProjectNameSpace(),
                "entity");
    }

    protected List<CopyFile> getLibrariesCopyFile(List<String> libraries) {
        return this.getLibrariesCopyFile(
                libraries,
                this.getLibsPath() + "%s");
    }

    protected List<CopyFile> getLibrariesTestCopyFile(List<String> libraries) {
        return this.getLibrariesCopyFile(
                libraries,
                this.getTestLibsPath() + "%s");
    }

    private List<CopyFile> getLibrariesCopyFile(
            List<String> libraries, String destination) {
        List<CopyFile> result = new ArrayList<CopyFile>();

        for (String library : libraries) {
            result.add(new CopyFile(
                    Harmony.getLibrary(library).getAbsolutePath(),
                    String.format(destination, library)));
        }

        return result;
    }

    @Override
    public void cloneTo(BaseAdapter adapter) {
        super.cloneTo(adapter);

        ((AndroidAdapter) adapter).adapterProject = this.adapterProject;
    }
}
