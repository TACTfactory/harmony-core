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
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.generator.CommonGenerator.ViewType;
import com.tactfactory.harmony.generator.androidxml.AttrsFile;
import com.tactfactory.harmony.generator.androidxml.ColorsFile;
import com.tactfactory.harmony.generator.androidxml.DimensFile;
import com.tactfactory.harmony.generator.androidxml.ManifestUpdater;
import com.tactfactory.harmony.generator.androidxml.StylesFile;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.platform.android.updater.EntityImplementationAndroid;
import com.tactfactory.harmony.platform.android.updater.EnumImplementationAndroid;
import com.tactfactory.harmony.platform.android.updater.HomeActivityUpdaterAndroid;
import com.tactfactory.harmony.platform.android.updater.ManifestActivityAndroid;
import com.tactfactory.harmony.platform.android.updater.ManifestApplicationThemeAndroid;
import com.tactfactory.harmony.platform.android.updater.ManifestProviderAndroid;
import com.tactfactory.harmony.platform.android.updater.UpdateLibraryAndroid;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.updater.impl.EditFile;
import com.tactfactory.harmony.updater.impl.CopyFile;
import com.tactfactory.harmony.updater.impl.CreateFolder;
import com.tactfactory.harmony.updater.impl.DeleteFile;
import com.tactfactory.harmony.updater.impl.SourceFile;
import com.tactfactory.harmony.updater.impl.XmlAndroid;
import com.tactfactory.harmony.updater.old.IConfigFileUtil;
import com.tactfactory.harmony.updater.old.ITranslateFileUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

import freemarker.template.Configuration;

public class AndroidProjectAdapter implements IAdapterProject {

    /** String prefix. */
    public static final String STRING_PREFIX = "@string/";

    private final AndroidAdapter adapter;

    public AndroidProjectAdapter(AndroidAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public List<IUpdater> getProjectFilesToClear() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        result.add(new DeleteFile(String.format("%s/%s/build.rules.xml",
                Harmony.getProjectPath(),
                this.adapter.getPlatform())));

        return result;
    }

    @Override
    public List<IUpdater> getProjectFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath;
        String filePath;

        // HomeActivity.java
        result.add(new SourceFile(
                this.adapter.getTemplateHomeActivityPathFile(),
                this.adapter.getHomeActivityPathFile(),
                false));

        // configs.xml
        result.add(new SourceFile(
                this.adapter.getTemplateRessourceValuesPath() + "configs.xml",
                this.adapter.getRessourceValuesPath() + "configs.xml",
                false));

        // configs.xml
        result.add(new SourceFile(
                this.adapter.getTemplateRessourceXLargeValuesPath() + "configs.xml",
                this.adapter.getRessourceXLargeValuesPath() + "configs.xml",
                false));

        result.add(new EditFile(
                Harmony.getInstance().getHarmonyContext().getCurrentBundleFolder()
                        + this.adapter.getTemplateRessourceValuesPath()
                        + "themes.xml",
                this.adapter.getRessourceValuesPath() + "themes.xml",
                new StylesFile()));

        result.add(new EditFile(
                Harmony.getInstance().getHarmonyContext().getCurrentBundleFolder()
                        + this.adapter.getTemplateRessourceValuesPath()
                        + "colors.xml",
                this.adapter.getRessourceValuesPath() + "colors.xml",
                new ColorsFile()));

        result.add(new EditFile(
                Harmony.getInstance().getHarmonyContext().getCurrentBundleFolder()
                        + this.adapter.getTemplateRessourceXLargeValuesPath()
                        + "colors.xml",
                this.adapter.getRessourceXLargeValuesPath() + "colors.xml",
                new ColorsFile()));

        // strings.xml
        result.add(new SourceFile(
                this.adapter.getTemplateStringsPathFile(),
                this.adapter.getStringsPathFile(),
                false));

        templatePath = this.adapter.getTemplateSourcePath();
        filePath = this.adapter.getSourcePath()
                + this.adapter.getApplicationMetadata().getProjectNameSpace();

        result.add(new SourceFile(
                templatePath + "harmony/view/package-info.java",
                filePath + "/harmony/view/package-info.java",
                true));

        // HarmonyFragmentActivity
        result.add(new SourceFile(
                templatePath + "harmony/view/HarmonyFragmentActivity.java",
                filePath + "/harmony/view/HarmonyFragmentActivity.java",
                true));

        result.add(new SourceFile(
                templatePath + "harmony/view/MultiLoader.java",
                filePath + "/harmony/view/MultiLoader.java",
                true));

        result.add(new SourceFile(
                templatePath + "harmony/view/HarmonyGridFragment.java",
                filePath + "/harmony/view/HarmonyGridFragment.java",
                true));

        // HarmonyFragment
        result.add(new SourceFile(
                templatePath + "harmony/view/HarmonyFragment.java",
                filePath + "/harmony/view/HarmonyFragment.java",
                true));

        // HarmonyListFragment
        result.add(new SourceFile(
                templatePath + "harmony/view/HarmonyListFragment.java",
                filePath + "/harmony/view/HarmonyListFragment.java",
                true));

        // HarmonyCursorAdapter
        result.add(new SourceFile(
                templatePath + "harmony/view/HarmonyCursorAdapter.java",
                filePath + "/harmony/view/HarmonyCursorAdapter.java",
                true));

        // HarmonyViewHolder
        result.add(new SourceFile(
                templatePath + "harmony/view/HarmonyViewHolder.java",
                filePath + "/harmony/view/HarmonyViewHolder.java",
                true));

        // NotImplementedException
        result.add(new SourceFile(
                templatePath + "harmony/exception/NotImplementedException.java",
                filePath + "/harmony/exception/NotImplementedException.java",
                false));

        result.add(new SourceFile(
                templatePath + "harmony/exception/package-info.java",
                filePath + "/harmony/exception/package-info.java",
                false));

        result.add(new SourceFile(
                templatePath + "harmony/util/package-info.java",
                filePath + "/harmony/util/package-info.java",
                false));

        result.add(new SourceFile(
                templatePath + "widget/package-info.java",
                filePath + "/harmony/widget/package-info.java",
                false));

        result.add(new SourceFile(
                templatePath + "package-info.java",
                filePath + "/package-info.java",
                false));

        templatePath = this.adapter.getTemplateUtilPath();
        filePath = this.adapter.getUtilPath();

        result.add(new SourceFile(
                templatePath + "DateUtils.java",
                filePath + "DateUtils.java",
                false));

        result.add(new SourceFile(
                templatePath + "DatabaseUtil.java",
                filePath + "DatabaseUtil.java",
                false));

        result.addAll(this.getFilesFromFolder(
                Harmony.getBundlePath() + "tact-core/"
                        + this.adapter.getTemplateProjectPath(),
                this.adapter.getTemplateProjectPath(),
                Harmony.getProjectPath() + this.adapter.getPlatform() + "/",
                Harmony.getBundlePath() + "tact-core/"
                        + this.adapter.getTemplateProjectPath()));

        result.add(new ManifestApplicationThemeAndroid("@style/AppTheme"));

        return result;
    }

    private List<IUpdater> getFilesFromFolder(String fullTemplatePath,
            String templatePath, String filePath, String path) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        File file = new File(path);

        if (file.exists()) {
            if (file.isDirectory()) {
                final File[] files = file.listFiles();
                for (File subFile : files) {
                    result.addAll(this.getFilesFromFolder(
                            fullTemplatePath,
                            templatePath,
                            filePath,
                            subFile.getAbsolutePath()));
                }
            } else {
                String tplPath = templatePath
                        + TactFileUtils.absoluteToRelativePath(
                        		file.getAbsolutePath(),
                        		fullTemplatePath);

                String srcPath = filePath
                        + TactFileUtils.absoluteToRelativePath(
                        		file.getAbsolutePath(),
                        		fullTemplatePath);

                tplPath = tplPath.substring(0, tplPath.length()
                        - ".ftl".length());
                srcPath = srcPath.substring(0, srcPath.length()
                        - ".ftl".length());

                result.add(new SourceFile(
                        tplPath,
                        srcPath));
            }
        }

        return result;
    }

    @Override
    public List<IUpdater> getStartViewFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        result.add(new SourceFile(
                this.adapter.getTemplateHomeActivityPathFile(),
                this.adapter.getHomeActivityPathFile(),
                true));

        // toolbar.xml
        result.add(new SourceFile(
                this.adapter.getTemplateRessourceLayoutPath() + "toolbar.xml",
                this.adapter.getRessourceLayoutPath() + "toolbar.xml",
                false));

        result.add(new SourceFile(
                this.adapter.getTemplateRessourceLayoutPath() + "main.xml",
                this.adapter.getRessourceLayoutPath() + "main.xml",
                true));

        return result;
    }

    @Override
    public List<IUpdater> getCriteriasFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        final String filePath = String.format("%s%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getCriterias());

        String templatePath = this.adapter.getTemplateSourceCriteriasPath();

        result.add(new SourceFile(
                templatePath + "package-info.java",
                filePath + "package-info.java",
                false));
        result.add(new SourceFile(
                templatePath + "base/package-info.java",
                filePath + "base/package-info.java",
                false));
        result.add(new SourceFile(
                templatePath + "base/value/package-info.java",
                filePath + "base/value/package-info.java",
                false));
        result.add(new SourceFile(
                templatePath + "base/CriteriaExpression.java",
                filePath + "base/CriteriaExpression.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/Criterion.java",
                filePath + "base/Criterion.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/ICriteria.java",
                filePath + "base/ICriteria.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/CriteriaValue.java",
                filePath + "base/value/CriteriaValue.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/StringValue.java",
                filePath + "base/value/StringValue.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/ArrayValue.java",
                filePath + "base/value/ArrayValue.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/SelectValue.java",
                filePath + "base/value/SelectValue.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/MethodValue.java",
                filePath + "base/value/MethodValue.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/DateTimeValue.java",
                filePath + "base/value/DateTimeValue.java",
                true));

        return result;
    }

    @Override
    public List<IUpdater> getDatabaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String applicationName = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName());

        String templatePath =
                this.adapter.getTemplateSourceDataPath();

        String filePath = String.format("%s%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getData());

        result.add(new SourceFile(
                templatePath + "TemplateSQLiteOpenHelper.java",
                String.format("%s%sSQLiteOpenHelper.java",
                        filePath,
                        applicationName),
                false));

        result.add(new SourceFile(
                templatePath + "base/TemplateSQLiteOpenHelperBase.java",
                String.format("%sbase/%sSQLiteOpenHelperBase.java",
                        filePath,
                        applicationName),
                true));

        result.add(new SourceFile(
                templatePath + "base/ApplicationSQLiteAdapterBase.java",
                filePath + "base/SQLiteAdapterBase.java",
                true));
        result.add(new SourceFile(
                templatePath + "ApplicationSQLiteAdapter.java",
                filePath + "SQLiteAdapter.java",
                false));

        result.add(new SourceFile(
                templatePath + "base/ResourceSQLiteAdapterBase.java",
                String.format("%sbase/ResourceSQLiteAdapterBase.java",
                        filePath),
                true));

        result.add(new SourceFile(
                templatePath + "ResourceSQLiteAdapter.java",
                String.format("%sResourceSQLiteAdapter.java",
                        filePath),
                false));

        result.add(new SourceFile(
                templatePath + "data-package-info.java",
                filePath + "package-info.java",
                true));

        result.add(new SourceFile(
                templatePath + "base/data-package-info.java",
                filePath + "base/package-info.java",
                true));

        return result;
    }

    @Override
    public List<IUpdater> getSqlAdapterEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String filePath = String.format("%s%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getData());

        final String templatePath =
                this.adapter.getTemplateSourceDataPath();

        result.add(new SourceFile(
                templatePath + "base/TemplateSQLiteAdapterBase.java",
                String.format("%sbase/%sSQLiteAdapterBase.java",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "TemplateSQLiteAdapter.java",
                String.format("%s%sSQLiteAdapter.java",
                        filePath,
                        entity.getName()),
                false));

        return result;
    }

    @Override
    public List<IUpdater> getLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        List<String> libraries = new ArrayList<String>();
        libraries.add("joda-time-2.3.jar");
        libraries.add("guava-12.0.jar");
        libraries.add("jsr305.jar");
        libraries.add("core-annotations.jar");

        // TODO add only if an entity has @EntityResource annotation
        libraries.add("universal-image-loader-1.8.6-with-sources.jar");

        result.addAll(this.adapter.getLibrariesCopyFile(libraries));

        String appCompatPath = String.format(
                "%s%s",
                this.adapter.getLibsPath(),
                "appcompat-v7");

        //Add compatv7
        CopyFile copyfile = new CopyFile(
                ApplicationMetadata.getAndroidSdkPath()
                        + "/extras/android/support/v7/appcompat",
                appCompatPath);

        result.add(copyfile);

        result.add(new UpdateLibraryAndroid(
                this.adapter.getApplicationMetadata().getName() + "-appcompat-v7",
                appCompatPath,
                "android-23",
                appCompatPath,
                true));

        return result;
    }

    private List<CopyFile> getTestLibrariesCopyFile(List<String> libraries) {
        List<CopyFile> result = new ArrayList<CopyFile>();
        String destination = this.adapter.getTestLibsPath() + "%s";

        for (String library : libraries) {
            result.add(new CopyFile(
                    Harmony.getLibrary(library).getAbsolutePath(),
                    String.format(destination, library)));
        }

        return result;
    }

    @Override
    public List<IUpdater> getActivityLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        List<String> libraries = new ArrayList<String>();
        libraries.add("universal-image-loader-1.8.6-with-sources.jar");
        libraries.add("ImageViewTouch.jar");

        result.addAll(this.adapter.getLibrariesCopyFile(libraries));

        return result;
    }

    @Override
    public List<IUpdater> getCreateView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getController(),
                entity.getName().toLowerCase(Locale.ENGLISH));

        result.add(new SourceFile(
                templatePath + "TemplateCreateActivity.java",
                String.format("%s%sCreateActivity.java",
                        filePath,
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateCreateFragment.java",
                String.format("%s%sCreateFragment.java",
                        filePath,
                        entity.getName())));

        templatePath = this.adapter.getTemplateRessourceLayoutPath();
        filePath = this.adapter.getRessourceLayoutPath();

        result.add(new SourceFile(
                templatePath + "activity_template_create.xml",
                String.format("%sactivity_%s_create.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new SourceFile(
                templatePath + "fragment_template_create.xml",
                String.format("%sfragment_%s_create.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new ManifestActivityAndroid(
                entity.getName(),
                entity.getName().toLowerCase(),
                "CreateActivity"));

        return result;
    }

    @Override
    public List<IUpdater> getEditView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getController(),
                entity.getName().toLowerCase(Locale.ENGLISH));

        result.add(new SourceFile(
                templatePath + "TemplateEditActivity.java",
                String.format("%s%sEditActivity.java",
                        filePath,
                        entity.getName())));
        result.add(new SourceFile(
                templatePath + "TemplateEditFragment.java",
                String.format("%s%sEditFragment.java",
                        filePath,
                        entity.getName())));

        templatePath = this.adapter.getTemplateRessourceLayoutPath();
        filePath = this.adapter.getRessourceLayoutPath();

        result.add(new SourceFile(
                templatePath + "activity_template_edit.xml",
                String.format("%sactivity_%s_edit.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.ENGLISH))));

        result.add(new SourceFile(
                templatePath + "fragment_template_edit.xml",
                String.format("%sfragment_%s_edit.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.ENGLISH))));

        result.add(new ManifestActivityAndroid(
                entity.getName(),
                entity.getName().toLowerCase(),
                "EditActivity"));

        return result;
    }

    @Override
    public List<IUpdater> getCommonView(EntityMetadata entity,
            boolean isWritable) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getController(),
                entity.getName().toLowerCase(Locale.ENGLISH));

        result.add(new SourceFile(
                templatePath + "entity-package-info.java",
                filePath + "package-info.java"));

        return result;
    }

    @Override
    public List<IUpdater> getShowView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getController(),
                entity.getName().toLowerCase(Locale.ENGLISH));

        result.add(new SourceFile(
                templatePath + "TemplateShowActivity.java",
                String.format("%s%sShowActivity.java",
                        filePath,
                        entity.getName())));
        result.add(new SourceFile(
                templatePath + "TemplateShowFragment.java",
                String.format("%s%sShowFragment.java",
                        filePath,
                        entity.getName())));

        templatePath = this.adapter.getTemplateRessourceLayoutPath();
        filePath = this.adapter.getRessourceLayoutPath();

        result.add(new SourceFile(
                templatePath + "activity_template_show.xml",
                String.format("%sactivity_%s_show.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new SourceFile(
                templatePath + "fragment_template_show.xml",
                String.format("%sfragment_%s_show.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new ManifestActivityAndroid(
                entity.getName(),
                entity.getName().toLowerCase(),
                "ShowActivity"));

        return result;
    }

    @Override
    public List<IUpdater> getListView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getController(),
                entity.getName().toLowerCase(Locale.ENGLISH));

        result.add(new SourceFile(
                templatePath + "TemplateListActivity.java",
                String.format("%s%sListActivity.java",
                        filePath,
                        entity.getName())));
        result.add(new SourceFile(
                templatePath + "TemplateListFragment.java",
                String.format("%s%sListFragment.java",
                        filePath,
                        entity.getName())));
        result.add(new SourceFile(
                templatePath + "TemplateListAdapter.java",
                String.format("%s%sListAdapter.java",
                        filePath,
                        entity.getName())));
        result.add(new SourceFile(
                templatePath + "TemplateListLoader.java",
                String.format("%s%sListLoader.java",
                        filePath,
                        entity.getName())));

        templatePath = this.adapter.getTemplateRessourceLayoutPath();
        filePath = this.adapter.getRessourceLayoutPath();

        result.add(new SourceFile(
                templatePath + "activity_template_list.xml",
                String.format("%sactivity_%s_list.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new SourceFile(
                templatePath + "fragment_template_list.xml",
                String.format("%sfragment_%s_list.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new SourceFile(
                templatePath + "row_template.xml",
                String.format("%srow_%s.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        templatePath = this.adapter.getTemplateRessourceLargeLayoutPath();
        filePath = this.adapter.getRessourceLargeLayoutPath();

        result.add(new SourceFile(
                templatePath + "activity_template_list.xml",
                String.format("%sactivity_%s_list.xml",
                        filePath,
                        entity.getName().toLowerCase(Locale.US))));

        result.add(new EditFile(
                Harmony.getInstance().getHarmonyContext().getCurrentBundleFolder()
                        + this.adapter.getTemplateRessourceValuesPath()
                        + "attrs.xml",
                this.adapter.getRessourceValuesPath() + "attrs.xml",
                new AttrsFile()));

        result.add(new EditFile(
                Harmony.getInstance().getHarmonyContext().getCurrentBundleFolder()
                        + this.adapter.getTemplateRessourceValuesPath()
                        + "styles.xml",
                this.adapter.getRessourceValuesPath() + "styles.xml",
                new StylesFile()));

        result.add(new EditFile(
                Harmony.getInstance().getHarmonyContext().getCurrentBundleFolder()
                        + this.adapter.getTemplateRessourceValuesPath()
                        + "dimens.xml",
                    this.adapter.getRessourceValuesPath() + "dimens.xml",
                    new DimensFile()));

        result.add(new ManifestActivityAndroid(
                entity.getName(),
                entity.getName().toLowerCase(),
                "ListActivity"));

        return result;
    }

    @Override
    public List<IUpdater> getViews(boolean isDate, boolean isTime) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getController());

        result.add(new SourceFile(
                templatePath + "package-info.java",
                filePath + "package-info.java"));

        templatePath = this.adapter.getTemplateWidgetPath();
        filePath = this.adapter.getWidgetPath();

        result.add(new SourceFile(
                templatePath + "ProgressImageLoaderListener.java",
                filePath + "ProgressImageLoaderListener.java"));

        result.add(new SourceFile(
                templatePath + "ValidationButtons.java",
                filePath + "ValidationButtons.java"));

        result.add(new SourceFile(
                templatePath + "EnumSpinner.java",
                filePath + "EnumSpinner.java"));

        result.add(new SourceFile(
                templatePath + "MultiEntityWidget.java",
                filePath + "MultiEntityWidget.java"));

        result.add(new SourceFile(
                templatePath + "SingleEntityWidget.java",
                filePath + "SingleEntityWidget.java"));

        if (isDate || isTime) {
            if (isDate) {
                result.add(new SourceFile(
                        templatePath + "CustomDatePickerDialog.java",
                        filePath + "CustomDatePickerDialog.java"));
                result.add(new SourceFile(
                        templatePath + "DateWidget.java",
                        filePath + "DateWidget.java"));
            }

            if (isTime) {
                result.add(new SourceFile(
                        templatePath + "CustomTimePickerDialog.java",
                        filePath + "CustomTimePickerDialog.java"));
                result.add(new SourceFile(
                        templatePath + "TimeWidget.java",
                        filePath + "TimeWidget.java"));
            }

            if (isDate && isTime) {
                result.add(new SourceFile(
                        templatePath + "DateTimeWidget.java",
                        filePath + "DateTimeWidget.java"));
            }
        }

        templatePath =  this.adapter.getTemplateRessourceLayoutPath();
        filePath = this.adapter.getRessourceLayoutPath();

        result.add(new SourceFile(
                templatePath + "widget_validation_buttons.xml",
                filePath + "widget_validation_buttons.xml"));

        result.add(new SourceFile(
                templatePath + "widget_multi_entity.xml",
                filePath + "widget_multi_entity.xml"));

        result.add(new SourceFile(
                templatePath + "widget_single_entity.xml",
                filePath + "widget_single_entity.xml"));

        result.add(new SourceFile(
                templatePath + "dialog_delete_confirmation.xml",
                filePath + "dialog_delete_confirmation.xml"));

        if (isDate || isTime) {
            if (isDate) {
                result.add(new SourceFile(
                        templatePath + "dialog_date_picker.xml",
                        filePath + "dialog_date_picker.xml"));

                result.add(new SourceFile(
                        templatePath + "widget_date.xml",
                        filePath + "widget_date.xml"));
            }

            if (isTime) {
                result.add(new SourceFile(
                        templatePath + "dialog_time_picker.xml",
                        filePath + "dialog_time_picker.xml"));

                result.add(new SourceFile(
                        templatePath + "widget_time.xml",
                        filePath + "widget_time.xml"));
            }

            if (isDate && isTime) {
                result.add(new SourceFile(
                        templatePath + "widget_datetime.xml",
                        filePath + "widget_datetime.xml"));
            }
        }

        templatePath = this.adapter.getTemplateRessourcePath();
        filePath = this.adapter.getRessourcePath();

        result.add(new SourceFile(
                templatePath + "color/primary_text_color.xml",
                filePath+ "color/primary_text_color.xml"));

        result.add(new SourceFile(
                templatePath + "color/secondary_text_color.xml",
                filePath+ "color/secondary_text_color.xml"));


        result.add(new SourceFile(
                templatePath + "color-xlarge/primary_text_color.xml",
                filePath+ "color-xlarge/primary_text_color.xml"));

        result.add(new SourceFile(
                templatePath + "color-xlarge/secondary_text_color.xml",
                filePath+ "color-xlarge/secondary_text_color.xml"));

        result.add(new SourceFile(
                templatePath + "drawable/list_item_activated_background.xml",
                filePath+ "drawable/list_item_activated_background.xml"));

        result.add(new SourceFile(
                templatePath + "drawable-xlarge/list_item_activated_background.xml",
                filePath+ "drawable-xlarge/list_item_activated_background.xml"));

        templatePath = String.format("%sharmony/view/",
                this.adapter.getTemplateSourcePath());
        filePath = String.format("%s%s/harmony/view/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace());

        // create HarmonyFragment
        result.add(new SourceFile(
            templatePath + "DeleteDialog.java",
            filePath + "DeleteDialog.java"));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureLibraries(String type) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        List<String> libraries = new ArrayList<String>();

        if (type.equals("xml")) {
            libraries.add("jdom-2.0.2.jar");
        } else {
            libraries.add("snakeyaml-1.10-android.jar");
        }

        result.addAll(this.adapter.getLibrariesCopyFile(libraries));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureFiles(boolean forceOverwrite) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath();

        String filePath = this.adapter.getSourcePath()
                + this.adapter.getApplicationMetadata().getProjectNameSpace()
                + "/" + this.adapter.getFixture() + "/";

        //Create base classes for Fixtures loaders
        result.add(new SourceFile(
                templatePath + "FixtureBase.java",
                filePath + "FixtureBase.java",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "DataManager.java",
                filePath + "DataManager.java",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "DataLoader.java",
                filePath + "DataLoader.java",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "package-info.java",
                filePath + "package-info.java"));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureEntityDefinitionFiles(
            String fixtureType, EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath();
        String filePath = this.adapter.getAssetsPath();

        result.add(new SourceFile(
                templatePath + "TemplateFixture." + fixtureType,
                filePath + "app/" + entity.getName() + "." + fixtureType));
        result.add(new SourceFile(
                templatePath + "TemplateFixture." + fixtureType,
                filePath + "debug/" + entity.getName() + "." + fixtureType));
        result.add(new SourceFile(
                templatePath + "TemplateFixture." + fixtureType,
                filePath + "test/" + entity.getName() + "." + fixtureType));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureEntityFiles(boolean forceOverwrite,
            String fixtureType, EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath();

        String filePath = this.adapter.getSourcePath()
                + this.adapter.getApplicationMetadata().getProjectNameSpace()
                + "/" + this.adapter.getFixture() + "/";

        //Create base classes for Fixtures loaders
        result.add(new SourceFile(
                templatePath + "TemplateDataLoader.java",
                filePath + entity.getName() + "DataLoader.java",
                forceOverwrite));

        return result;
    }

    @Override
	public List<IUpdater> getFixtureAssets() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<IUpdater> getApplicationFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String applicationName = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName());

        String templatePath = this.adapter.getTemplateSourcePath();

        String filePath = String.format("%s%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace());

        //Create base classes for Fixtures loaders
        result.add(new SourceFile(
                templatePath + "TemplateApplicationBase.java",
                filePath + applicationName + "ApplicationBase.java",
                true));

        result.add(new SourceFile(
                templatePath + "TemplateApplication.java",
                filePath + applicationName + "Application.java"));

        return result;
    }

    @Override
    public ITranslateFileUtil getTranslateFileUtil() {
        return new XmlAndroid();
    }

    @Override
    public List<IUpdater> getProviderAdaptersEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceProviderPath();
        String filePath = String.format("%s%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getProvider());

        // Provider adapters
        result.add(new SourceFile(
                templatePath + "TemplateProviderAdapter.java",
                filePath + entity.getName() + "ProviderAdapter.java"));

        result.add(new SourceFile(
                templatePath + "base/TemplateProviderAdapterBase.java",
                filePath + "base/" + entity.getName() + "ProviderAdapterBase.java",
                true));

        // Make contracts
        result.add(new SourceFile(
                templatePath + "contract/TemplateContract.java",
                filePath + "contract/" + entity.getName() + "Contract.java"));
        result.add(new SourceFile(
                templatePath + "contract/base/TemplateContractBase.java",
                filePath + "contract/base/" + entity.getName() + "ContractBase.java",
                true));

        // Provider utils
        if (!entity.isInternal()) {
            result.add(new SourceFile(
                    templatePath + "utils/TemplateProviderUtils.java",
                    filePath + "utils/" + entity.getName() + "ProviderUtils.java"));
            result.add(new SourceFile(
                    templatePath + "utils/base/TemplateProviderUtilsBase.java",
                    filePath + "utils/base/" + entity.getName() + "ProviderUtilsBase.java",
                    true));
        }

        return result;
    }

    @Override
    public List<IUpdater> getProviderFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String nameProvider = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName() + "Provider");

        String templatePath = this.adapter.getTemplateSourceProviderPath();
        String filePath = String.format("%s%s/%s/",
                this.adapter.getSourcePath(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getProvider());

        result.add(new SourceFile(
                templatePath + "utils/base/ApplicationProviderUtilsBase.java",
                filePath + "utils/base/ProviderUtilsBase.java",
                true));

        result.add(new SourceFile(
                templatePath + "utils/ApplicationProviderUtils.java",
                filePath + "utils/ProviderUtils.java",
                false));

        result.add(new SourceFile(
                templatePath + "TemplateProvider.java",
                filePath + nameProvider + ".java"));
        result.add(new SourceFile(
                templatePath + "base/TemplateProviderBase.java",
                filePath + "base/" + nameProvider + "Base.java",
                true));
        result.add(new SourceFile(
                templatePath + "base/ProviderAdapterBase.java",
                filePath + "base/ProviderAdapterBase.java",
                true));
        result.add(new SourceFile(
                templatePath + "ProviderAdapter.java",
                filePath + "ProviderAdapter.java",
                false));
        result.add(new SourceFile(
                templatePath + "base/ResourceProviderAdapterBase.java",
                String.format("%sbase/ResourceProviderAdapterBase.java",
                        filePath),
                true));

        result.add(new SourceFile(
                templatePath + "ResourceProviderAdapter.java",
                String.format("%sResourceProviderAdapter.java",
                        filePath),
                false));

        result.add(new SourceFile(
                templatePath + "contract/ResourceContract.java",
                filePath + "contract/ResourceContract.java"));
        result.add(new SourceFile(
                templatePath + "contract/base/ResourceContractBase.java",
                filePath + "contract/base/ResourceContractBase.java",
                true));

        // Package infos
        result.add(new SourceFile(
                templatePath + "provider-package-info.java",
                filePath + "package-info.java"));
        result.add(new SourceFile(
                templatePath + "base/provider-package-info.java",
                filePath + "base/package-info.java"));

        result.add(new SourceFile(
                templatePath + "utils/base/package-info.java",
                filePath + "utils/base/package-info.java"));
        result.add(new SourceFile(
                templatePath + "utils/package-info.java",
                filePath + "utils/package-info.java"));

        String providerNamespace = this.adapter.getApplicationMetadata()
                .getProjectNameSpace().replace('/', '.') + "."
                        + this.adapter.getProvider();

        result.add(new ManifestProviderAndroid(
                providerNamespace,
                nameProvider));

        return result;
    }

    @Override
    public List<IUpdater> getTestProviderEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateTestsPath();
        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getTestPath(),
                this.adapter.getSource(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getTest());

        result.add(new SourceFile(
                templatePath + "base/TemplateTestProviderBase.java",
                String.format("%sbase/%sTestProviderBase.java",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "TemplateTestProvider.java",
                String.format("%s%sTestProvider.java",
                        filePath,
                        entity.getName())));

        return result;
    }

    @Override
    public boolean isDataLoaderAlreadyGenerated() {
        final String dataLoaderPath = this.adapter.getSourcePath()
                + this.adapter.getApplicationMetadata().getProjectNameSpace()
                + "/" + this.adapter.getFixture() + "/"
                + "DataLoader.java";

        return new File(dataLoaderPath).exists();
    }

    @Override
    public List<IUpdater> getTestProjectFilesToClear() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        result.add(new DeleteFile(String.format("%s/%s/%s/build.rules.xml",
                Harmony.getProjectPath(),
                this.adapter.getPlatform(),
                this.adapter.getTest())));

        return result;
    }

    @Override
    public List<IUpdater> getTestProjectLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        List<String> libraries = new ArrayList<String>();
        libraries.add("android-junit-report-1.5.8.jar");

        result.addAll(this.getTestLibrariesCopyFile(libraries));

        return result;
    }

    @Override
    public List<IUpdater> getTestProjectFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        result.add(new SourceFile(
                this.adapter.getTemplateStringsTestPathFile(),
                this.adapter.getStringsTestPathFile()));

        result.addAll(this.getFilesFromFolder(
                Harmony.getBundlePath() + "tact-core/"
                        + this.adapter.getTemplateTestProjectPath(),
                this.adapter.getTemplateTestProjectPath(),
                Harmony.getProjectPath()
                        + this.adapter.getPlatform() + "/"
                        + this.adapter.getTest() + "/",
                Harmony.getBundlePath() + "tact-core/"
                        + this.adapter.getTemplateTestProjectPath()));

        return result;
    }

    @Override
    public List<IUpdater> getCreateFolders() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String sourcePath = this.adapter.getSourcePath()
                + this.adapter.getApplicationMetadata().getProjectNameSpace();

        // create project name space folders
        result.add(new CreateFolder(sourcePath));

        // create empty package entity
        result.add(new CreateFolder(sourcePath + "/entity/"));

        // create util folder
        result.add(new CreateFolder(sourcePath + "/harmony/util/"));

        // create libs folder
        result.add(new CreateFolder(this.adapter.getLibsPath()));

        return result;
    }

    @Override
    public List<IUpdater> getTestFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateTestsPath();
        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getTestPath(),
                this.adapter.getSource(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getTest());

        result.add(new SourceFile(
                templatePath + "utils/TestUtils.java",
                filePath + "utils/TestUtils.java",
                false));

        result.add(new SourceFile(
                templatePath + "base/TestContextIsolatedBase.java",
                filePath + "base/TestContextIsolatedBase.java",
                true));

        result.add(new SourceFile(
                templatePath + "TestContextIsolated.java",
                filePath + "TestContextIsolated.java",
                false));

        result.add(new SourceFile(
                templatePath + "base/TestContextMock.java",
                filePath + "base/TestContextMock.java",
                true));

        result.add(new SourceFile(
                templatePath + "base/TestDBBase.java",
                filePath + "base/TestDBBase.java",
                true));

        result.add(new SourceFile(
                templatePath + "base/TestServiceBase.java",
                filePath + "base/TestServiceBase.java",
                true));

        return result;
    }

    @Override
    public List<IUpdater> getTestEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateTestsPath();
        String filePath = String.format("%s%s/%s/%s/",
                this.adapter.getTestPath(),
                this.adapter.getSource(),
                this.adapter.getApplicationMetadata().getProjectNameSpace(),
                this.adapter.getTest());

        result.add(new SourceFile(
                templatePath + "base/TemplateTestDBBase.java",
                String.format("%sbase/%sTestDBBase.java",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "TemplateTestDB.java",
                String.format("%s%sTestDB.java",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "utils/base/TemplateUtilsBase.java",
                String.format("%sutils/base/%sUtilsBase.java",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "utils/TemplateUtils.java",
                String.format("%sutils/%sUtils.java",
                        filePath,
                        entity.getName()),
                false));

        return result;
    }

    @Override
    public List<String> getServices() {
        List<String> result = new ManifestUpdater(this.adapter).getServices();
        return result;
    }

    @Override
    public List<IUpdater> getTestProjectCreateFolders() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        result.add(new CreateFolder(this.adapter.getTestLibsPath()));

        return result;
    }

    @Override
    public IConfigFileUtil getConfigFileUtil() {
        return new XmlAndroid();
    }

    @Override
    public List<IUpdater> getMenuFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String cappedProjectName = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName());

        String templatePath = this.adapter.getTemplateMenuBasePath();
        String filePath = this.adapter.getMenuBasePath();

        // create MenuWrapper
        result.add(new SourceFile(
            templatePath + "MenuWrapperBase.java",
            filePath + "MenuWrapperBase.java",
            true));

        // Package infos
        result.add(new SourceFile(
                templatePath + "package-info.java",
                filePath + "package-info.java",
                false));

        templatePath = this.adapter.getTemplateMenuPath();
        filePath = this.adapter.getMenuPath();

        // create ProjectMenu
        result.add(new SourceFile(
            templatePath + "TemplateMenu.java",
            String.format("%s%sMenu.java",
                    filePath,
                    cappedProjectName),
            false));

        result.add(new SourceFile(
                templatePath + "package-info.java",
                filePath + "package-info.java",
                false));

        return result;
    }

    @Override
    public List<IUpdater> getMenuBaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String cappedProjectName = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName());

        String templatePath = this.adapter.getTemplateMenuBasePath();
        String filePath = this.adapter.getMenuBasePath();

        // create ProjectMenuBase
        result.add(new SourceFile(
                templatePath + "TemplateMenuBase.java",
                String.format("%s%sMenuBase.java",
                        filePath,
                        cappedProjectName),
                true));

        return result;
    }

    @Override
    public List<IUpdater> getMenuFiles(String menuName) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateMenuPath();
        String filePath = this.adapter.getMenuPath();

        // create MenuWrapper
        result.add(new SourceFile(
                String.format("%s%sMenuWrapper.java",
                        templatePath,
                        menuName),
                String.format("%s%sMenuWrapper.java",
                        filePath,
                        menuName),
                true));

        return result;
    }

    @Override
    public List<String> getAvailableMenus() {
        final ArrayList<String> ret = new ArrayList<String>();
        final File menuFolder = new File(this.adapter.getMenuPath());
        if (menuFolder.isDirectory()) {
            File[] files = menuFolder.listFiles(new FileFilter() {

                @Override
                public boolean accept(final File file) {
                    return file.getName().endsWith("MenuWrapper.java");
                }
            });

            for (File file : files) {
                ret.add(file.getName().split("\\.")[0]);
            }
        }
        return ret;
    }

    @Override
    public List<IUpdater> getEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        final File entityFile = new File(String.format("%s%s.java",
                this.adapter.getSourceEntityPath(),
                entity.getName()));

        if (entityFile.exists()) {
            result.add(new EntityImplementationAndroid(entityFile, entity));
        }

        return result;
    }

    @Override
    public List<IUpdater> getEntityBaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceEntityBasePath();
        String filePath = this.adapter.getSourceEntityPath();

        result.add(new SourceFile(
                templatePath + "Resource.java",
                filePath + "base/Resource.java",
                true));

        result.add(new SourceFile(
                templatePath + "EntityResourceBase.java",
                filePath + "base/EntityResourceBase.java",
                true));

        return result;
    }

    @Override
    public List<IUpdater> updateEnum(EnumMetadata enumMeta, Configuration cfg) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        if (enumMeta.getIdName() != null) {
        	result.add(new EnumImplementationAndroid(enumMeta));
        }

        return result;
    }

    @Override
    public List<IUpdater> getStaticViewFiles(String packageName,
            String viewName, ViewType type) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();
        String filePath = String.format("%s%s/view/%s/",
                this.adapter.getSourcePath(),
                ApplicationMetadata.INSTANCE.getProjectNameSpace(),
                packageName.replace('.', '/'));

        result.add(new SourceFile(
                templatePath + "TemplateStaticFragment.java",
                filePath + viewName + "Fragment.java"));

        result.add(new SourceFile(
                templatePath + "TemplateStaticActivity.java",
                filePath + viewName + "Activity.java"));

        templatePath = this.adapter.getTemplateRessourceLayoutPath();
        filePath = this.adapter.getRessourceLayoutPath();

        result.add(new SourceFile(
                templatePath + "fragment_template_static.xml",
                filePath + "fragment_" + viewName.toLowerCase() + ".xml"));

        result.add(new SourceFile(
                templatePath + "activity_template_static.xml",
                filePath + "activity_" + viewName.toLowerCase() + ".xml"));

        result.add(new ManifestActivityAndroid(
                viewName,
                packageName,
                "Activity"));

        return result;
    }

    @Override
    public List<IUpdater> updateHomeActivity(String activity, String buttonId) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        result.add(new HomeActivityUpdaterAndroid(activity, buttonId));

        return result;
    }
}
