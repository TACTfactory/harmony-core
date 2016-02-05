/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform.winphone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.generator.CommonGenerator.ViewType;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.platform.winphone.updater.ProjectUpdater;
import com.tactfactory.harmony.platform.winphone.updater.XmlResourcesWinphone;
import com.tactfactory.harmony.platform.winphone.updater.ProjectUpdater.FileType;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.updater.impl.CopyFile;
import com.tactfactory.harmony.updater.impl.SourceFile;
import com.tactfactory.harmony.updater.old.IConfigFileUtil;
import com.tactfactory.harmony.updater.old.ITranslateFileUtil;

public class WinphoneProjectAdapter implements IAdapterProject {

    private final WinphoneAdapter adapter;

    public WinphoneProjectAdapter(WinphoneAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public List<IUpdater> getProjectFilesToClear() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getProjectFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateProjectPath();
        String filePath = this.adapter.getSourcePath();

        String applicationName = this.adapter.getApplicationMetadata()
                .getName().toLowerCase(Locale.ENGLISH);

        result.add(new SourceFile(
                templatePath + "Template.csproj",
                filePath + applicationName + ".csproj",
                false));

        result.add(new SourceFile(
                templatePath + "Template.sln",
                filePath + applicationName + ".sln",
                false));

        result.add(new SourceFile(
                templatePath + "Properties/Annotations.cs",
                filePath + "Properties/Annotations.cs",
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Properties/" + "Annotations.cs"));

        result.add(new SourceFile(
                templatePath + "Properties/AppManifest.xml",
                filePath + "Properties/AppManifest.xml",
                false));

//        result.add(new ProjectUpdater(
//                FileType.None,
//                "Properties/" + "AppManifest.xml"));

        result.add(new SourceFile(
                templatePath + "Properties/AssemblyInfo.cs",
                filePath + "Properties/AssemblyInfo.cs",
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Properties/" + "AssemblyInfo.cs"));

        result.add(new SourceFile(
                templatePath + "Properties/WMAppManifest.xml",
                filePath + "Properties/WMAppManifest.xml",
                false));

//        result.add(new ProjectUpdater(
//                FileType.None,
//                "Properties/" + "WMAppManifest.xml"));

        templatePath = this.adapter.getTemplateSourcePath();
        filePath = this.adapter.getSourcePath();

        result.add(new SourceFile(
                templatePath + "Utils/Log.cs",
                filePath + "Utils/Log.cs",
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Utils/" + "Log.cs"));

        result.add(new SourceFile(
                templatePath + "Utils/AssetManager.cs",
                filePath + "Utils/AssetManager.cs",
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Utils/" + "AssetManager.cs"));

        templatePath = this.adapter.getTemplateRessourcePath();
        filePath = this.adapter.getRessourcePath();

        result.add(new SourceFile(
                templatePath + "LocalizedStrings.cs",
                filePath + "LocalizedStrings.cs",
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Resources/" + "LocalizedStrings.cs"));

        result.add(new SourceFile(
                templatePath + "Values/StringsResources.Designer.cs",
                filePath + "Values/StringsResources.Designer.cs",
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Resources/Values/" + "StringsResources.Designer.cs",
                "StringsResources.resx"));

        result.add(new SourceFile(
                templatePath + "Values/StringsResources.resx",
                filePath + "Values/StringsResources.resx",
                false));

        result.add(new ProjectUpdater(
                FileType.EmbeddedResource,
                "Resources/Values/" + "StringsResources.resx"));

        templatePath = this.adapter.getTemplateUtilPath();
        filePath = this.adapter.getUtilPath();

        result.add(new SourceFile(
                templatePath + "DateUtils.cs",
                filePath + "DateUtils.cs",
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Harmony/Util/" + "DateUtils.cs"));

        return result;
    }

    @Override
    public List<IUpdater> getStartViewFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = this.adapter.getSourceControllerPath();

        result.add(new SourceFile(
                templatePath + "HomePage.xaml.cs",
                filePath + "HomePage.xaml.cs",
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "View/" + "HomePage.xaml.cs",
                "HomePage.xaml"));

        result.add(new SourceFile(
                templatePath + "HomePage.xaml",
                filePath + "HomePage.xaml",
                true));

        result.add(new ProjectUpdater(
                FileType.Page,
                "View/" + "HomePage.xaml"));

        return result;
    }

    @Override
    public List<IUpdater> getLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getCreateFolders() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getCriteriasFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getDatabaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String applicationName = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName());

        String templatePath = this.adapter.getTemplateSourceDataPath();
        String filePath = this.adapter.getSourcePath() + this.adapter.getData() + "/";

        result.add(new SourceFile(
                templatePath + "Base/ApplicationSqlAdapterBase.cs",
                String.format("%sBase/SqlAdapterBase.cs",
                        filePath),
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Data/" + "Base/SqlAdapterBase.cs"));

        result.add(new SourceFile(
                templatePath + "Base/ApplicationSqlOpenHelperBase.cs",
                String.format("%sBase/%sSqlOpenHelperBase.cs",
                        filePath,
                        applicationName),
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Data/" + String.format("Base/%sSqlOpenHelperBase.cs",
                        applicationName)));

        result.add(new SourceFile(
                templatePath + "ApplicationSqlAdapter.cs",
                String.format("%sSqlAdapter.cs",
                        filePath),
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Data/" + "SqlAdapter.cs"));

        result.add(new SourceFile(
                templatePath + "ApplicationSqlOpenHelper.cs",
                String.format("%s%sSqlOpenHelper.cs",
                        filePath,
                        applicationName),
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Data/" + String.format("%sSqlOpenHelper.cs",
                        applicationName)));

        return result;
    }

    @Override
    public List<IUpdater> getSqlAdapterEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        if (!entity.isInternal())
        {
            String templatePath = this.adapter.getTemplateSourceDataPath();
            String filePath = this.adapter.getSourcePath() + this.adapter.getData() + "/";

            result.add(new SourceFile(
                    templatePath + "Base/TemplateSqlAdapterBase.cs",
                    String.format("%sBase/%sSqlAdapterBase.cs",
                            filePath,
                            entity.getName()),
                    true));

            result.add(new ProjectUpdater(
                    FileType.Compile,
                    "Data/" + String.format("Base/%sSqlAdapterBase.cs",
                            entity.getName())));

            result.add(new SourceFile(
                    templatePath + "TemplateSqlAdapter.cs",
                    String.format("%s%sSqlAdapter.cs",
                            filePath,
                            entity.getName()),
                    false));

            result.add(new ProjectUpdater(
                    FileType.Compile,
                    "Data/" + String.format("%sSqlAdapter.cs",
                            entity.getName())));
        }

        return result;
    }

    @Override
    public List<IUpdater> getActivityLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getCreateView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/",
                this.adapter.getSourceControllerPath(),
                entity.getName());

        result.add(new SourceFile(
                templatePath + "TemplateCreatePage.xaml.cs",
                String.format("%s%sCreatePage.xaml.cs",
                        filePath,
                        entity.getName()),
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "View/" + entity.getName()  + "/" + String.format("%sCreatePage.xaml.cs",
                        entity.getName()),
                String.format("%sCreatePage.xaml",
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateCreatePage.xaml",
                String.format("%s%sCreatePage.xaml",
                        filePath,
                        entity.getName()),
                false));

        result.add(new ProjectUpdater(
                FileType.Page,
                "View/" + entity.getName()  + "/" + String.format("%sCreatePage.xaml",
                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getEditView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getShowView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getListView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s%s/",
                this.adapter.getSourceControllerPath(),
                entity.getName());

        result.add(new SourceFile(
                templatePath + "TemplateListPage.xaml.cs",
                String.format("%s%sListPage.xaml.cs",
                        filePath,
                        entity.getName()),
                false));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "View/" + entity.getName()  + "/" + String.format("%sListPage.xaml.cs",
                        entity.getName()),
                String.format("%sListPage.xaml",
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateListPage.xaml",
                String.format("%s%sListPage.xaml",
                        filePath,
                        entity.getName()),
                false));

        result.add(new ProjectUpdater(
                FileType.Page,
                "View/" + entity.getName()  + "/" + String.format("%sListPage.xaml",
                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getCommonView(EntityMetadata entity,
            boolean isWritable) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getViews(boolean isDate, boolean isTime) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getFixtureLibraries(String type) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getFixtureFiles(boolean forceOverwrite) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath();

        String filePath = this.adapter.getSourcePath()
                + "/" + this.adapter.getFixture() + "/";

        //Create base classes for Fixtures loaders
        result.add(new SourceFile(
                templatePath + "FixtureBase.cs",
                filePath + "FixtureBase.cs",
                forceOverwrite));

        result.add(new ProjectUpdater(
                FileType.Compile,
                this.adapter.getFixture() + "/FixtureBase.cs"));

        result.add(new SourceFile(
                templatePath + "DataManager.cs",
                filePath + "DataManager.cs",
                forceOverwrite));

        result.add(new ProjectUpdater(
                FileType.Compile,
                this.adapter.getFixture() + "/DataManager.cs"));

        result.add(new SourceFile(
                templatePath + "DataLoader.cs",
                filePath + "DataLoader.cs",
                forceOverwrite));

        result.add(new ProjectUpdater(
                FileType.Compile,
                this.adapter.getFixture() + "/DataLoader.cs"));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureEntityDefinitionFiles(
            String fixtureType, EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getFixtureEntityFiles(boolean forceOverwrite,
            String fixtureType, EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath()
                + "Loaders/";

        String filePath = this.adapter.getSourcePath()
                + this.adapter.getFixture() + "/Loaders/";

        //Create base classes for Fixtures loaders
        result.add(new SourceFile(
                templatePath + "TemplateDataLoader.cs",
                filePath + entity.getName() + "DataLoader.cs",
                forceOverwrite));

        result.add(new ProjectUpdater(
                FileType.Compile,
                this.adapter.getFixture() + "/Loaders/"
                        + entity.getName() + "DataLoader.cs"));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureAssets() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        File androidAssets = new File("app/android/assets/");
        if (androidAssets.exists() && androidAssets.isDirectory()) {

            File assets = new File(this.adapter.getAssetsPath() + "app");
            androidAssets = new File("app/android/assets/app");

            if (androidAssets.exists() && androidAssets.isDirectory()) {
                for (File file : androidAssets.listFiles()) {
                    result.add(new CopyFile(
                            file,
                            new File(assets.getAbsolutePath()
                                    + "/"
                                    + file.getName())));
                    result.add(new ProjectUpdater(
                            FileType.Content,
                            this.adapter.getAssets() + "/app/" + file.getName()));
                }
            }

            assets = new File(this.adapter.getAssetsPath() + "debug");
            androidAssets = new File("app/android/assets/debug");


            if (androidAssets.exists() && androidAssets.isDirectory()) {
                for (File file : androidAssets.listFiles()) {
                    result.add(new CopyFile(
                            file,
                            new File(assets.getAbsolutePath()
                                    + "/"
                                    + file.getName())));
                    result.add(new ProjectUpdater(
                            FileType.Content,
                            this.adapter.getAssets() + "/debug/" + file.getName()));
                }
            }

            assets = new File(this.adapter.getAssetsPath() + "test");
            androidAssets = new File("app/android/assets/test");

            if (androidAssets.exists() && androidAssets.isDirectory()) {
                for (File file : androidAssets.listFiles()) {
                    result.add(new CopyFile(
                            file,
                            new File(assets.getAbsolutePath()
                                    + "/"
                                    + file.getName())));
                    result.add(new ProjectUpdater(
                            FileType.Content,
                            this.adapter.getAssets() + "/test/" + file.getName()));
                }
            }

        }

        return result;
    }

    @Override
    public List<IUpdater> getApplicationFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String applicationName = CaseFormat.LOWER_CAMEL.to(
                CaseFormat.UPPER_CAMEL,
                this.adapter.getApplicationMetadata().getName());

        String templatePath = this.adapter.getTemplateSourcePath();
        String filePath = this.adapter.getSourcePath();

        result.add(new SourceFile(
                templatePath + "TemplateApplicationBase.cs",
                filePath + applicationName + "ApplicationBase.cs",
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                applicationName + "ApplicationBase.cs"));

        result.add(new SourceFile(
                templatePath + "TemplateApplication.xaml.cs",
                filePath + applicationName + "Application.xaml.cs"));

        result.add(new ProjectUpdater(
                FileType.Compile,
                applicationName + "Application.xaml.cs",
                applicationName + "Application.xaml"));

        result.add(new SourceFile(
                templatePath + "TemplateApplication.xaml",
                filePath + applicationName + "Application.xaml"));

        result.add(new ProjectUpdater(
                FileType.ApplicationDefinition,
                applicationName + "Application.xaml"));

        return result;
    }

    @Override
    public List<String> getServices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITranslateFileUtil getTranslateFileUtil() {
        return new XmlResourcesWinphone();
    }

    @Override
    public IConfigFileUtil getConfigFileUtil() {
        return new XmlResourcesWinphone();
    }

    @Override
    public List<IUpdater> getProviderFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getProviderAdaptersEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public boolean isDataLoaderAlreadyGenerated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<IUpdater> getTestProviderEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getTestProjectFilesToClear() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getTestProjectCreateFolders() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getTestProjectLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getTestProjectFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getTestFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getTestEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getMenuFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getMenuBaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getMenuFiles(String menuName) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<String> getAvailableMenus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourcePath() + "Entity/";

        String filePath = this.adapter.getSourcePath() + "Entity/";

        result.add(new SourceFile(
                templatePath + "TemplateEntity.cs",
                String.format("%s%s.cs",
                        filePath,
                        entity.getName()),
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Entity/" + String.format("%s.cs",
                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> updateEnum(EnumMetadata enumMeta) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourcePath() + "Entity/";

        String filePath = this.adapter.getSourcePath() + "Entity/";

        result.add(new SourceFile(
                templatePath + "TemplateEnum.cs",
                String.format("%s%s.cs",
                        filePath,
                        enumMeta.getName()),
                true));

        result.add(new ProjectUpdater(
                FileType.Compile,
                "Entity/" + String.format("%s.cs",
                        enumMeta.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getStaticViewFiles(String packageName,
            String viewName, ViewType type) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> updateHomeActivity(String activity, String buttonId) {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getEntityBaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }

    @Override
    public List<IUpdater> getFilesToDelete() {
        List<IUpdater> result = new ArrayList<IUpdater>();
        return result;
    }
}
