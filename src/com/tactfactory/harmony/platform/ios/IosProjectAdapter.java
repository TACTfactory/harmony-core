package com.tactfactory.harmony.platform.ios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.base.CaseFormat;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.generator.CommonGenerator.ViewType;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.updater.impl.CreateFolder;
import com.tactfactory.harmony.updater.impl.DeleteFile;
import com.tactfactory.harmony.updater.impl.SourceFile;
import com.tactfactory.harmony.updater.old.IConfigFileUtil;
import com.tactfactory.harmony.updater.old.ITranslateFileUtil;

public class IosProjectAdapter implements IAdapterProject {

    private final IosAdapter adapter;

    public IosProjectAdapter(IosAdapter adapter) {
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

        String templatePath = this.adapter.getTemplateSourceHarmonyUtilsPath();
        String filePath = String.format("%s/Utils/",
                this.adapter.getSourceHarmonyPath());

        result.add(new SourceFile(
                templatePath + "DateUtils.h",
                filePath + "DateUtils.h",
                false));

        result.add(new SourceFile(
                templatePath + "DateUtils.m",
                filePath + "DateUtils.m",
                false));

        result.add(new SourceFile(
                templatePath + "DatabaseUtils.h",
                filePath + "DatabaseUtils.h",
                false));

        result.add(new SourceFile(
                templatePath + "DatabaseUtils.m",
                filePath + "DatabaseUtils.m",
                false));

        result.add(new SourceFile(
                templatePath + "PushManager.h",
                filePath + "PushManager.h",
                false));

        result.add(new SourceFile(
                templatePath + "PushManager.m",
                filePath + "PushManager.m",
                false));

        result.add(new SourceFile(
                templatePath + "AsyncTask.h",
                filePath + "AsyncTask.h",
                false));

        result.add(new SourceFile(
                templatePath + "AsyncTask.m",
                filePath + "AsyncTask.m",
                false));

        result.add(new SourceFile(
                templatePath + "NSDate+Compare.h",
                filePath + "NSDate+Compare.h",
                false));

        result.add(new SourceFile(
                templatePath + "NSDate+Compare.m",
                filePath + "NSDate+Compare.m",
                false));

        result.add(new SourceFile(
                templatePath + "ImageUtils.h",
                filePath + "ImageUtils.h",
                false));

        result.add(new SourceFile(
                templatePath + "ImageUtils.m",
                filePath + "ImageUtils.m",
                false));

        result.add(new SourceFile(
                templatePath + "Config.h",
                filePath + "Config.h",
                false));

        result.add(new SourceFile(
                templatePath + "Config.m",
                filePath + "Config.m",
                false));


        templatePath = this.adapter.getTemplateSourceHarmonySqlPath();
        filePath = String.format("%s/Sql/",
                this.adapter.getSourceHarmonyPath());

        result.add(new SourceFile(
                templatePath + "ContentObserver.h",
                filePath + "ContentObserver.h",
                false));

        result.add(new SourceFile(
                templatePath + "ContentObserver.m",
                filePath + "ContentObserver.m",
                false));

        result.add(new SourceFile(
                templatePath + "ContentResolver.h",
                filePath + "ContentResolver.h",
                false));

        result.add(new SourceFile(
                templatePath + "ContentResolver.m",
                filePath + "ContentResolver.m",
                false));

        result.add(new SourceFile(
                templatePath + "Cursor.h",
                filePath + "Cursor.h",
                false));

        result.add(new SourceFile(
                templatePath + "Cursor.m",
                filePath + "Cursor.m",
                false));

        result.add(new SourceFile(
                templatePath + "Database.h",
                filePath + "Database.h",
                false));

        result.add(new SourceFile(
                templatePath + "Database.m",
                filePath + "Database.m",
                false));

        templatePath = this.adapter.getTemplateSourceEntityPath();
        filePath = this.adapter.getSourcePath();

        result.add(new SourceFile(
                templatePath + "AppDelegate.m", String.format("%s/AppDelegate.m", filePath), false));

        result.add(new SourceFile(
                templatePath + "AppDelegate.h", String.format("%s/AppDelegate.h", filePath), false));

        result.add(new SourceFile(templatePath + "info.plist", String.format("%s/info.plist", filePath), false));

        result.add(new SourceFile(templatePath + "main.m", String.format("%s/main.m", filePath), false));

        result.add(new SourceFile(
                templatePath + "HomeViewController.h",
                String.format("%s/HomeViewController.h", filePath),
                true));

        result.add(new SourceFile(
                templatePath + "HomeViewController.m",
                String.format("%s/HomeViewController.m", filePath),
                true));

        result.add(new SourceFile(
                templatePath + "HomeViewController.xib",
                String.format("%s/HomeViewController.xib", filePath),
                true));

        templatePath = this.adapter.getTemplateProjectPath();
        filePath = String.format("%s/%s", Harmony.getProjectPath(), this.adapter.getPlatform());

        result.add(new SourceFile(templatePath + "Podfile", String.format("%s/Podfile", filePath), false));

        result.add(new SourceFile(templatePath + "gitignore", String.format("%s/.gitignore", filePath), false));

        result.add(new SourceFile(
                templatePath + "project.pbxproj",
                String.format(
                        "%s/%s.xcodeproj/project.pbxproj",
                        filePath,
                        this.adapter.getApplicationMetadata().getName().toLowerCase()),
                false));

        return result;
    }

    @Override
    public List<IUpdater> getStartViewFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getFilesToDelete() {
        List<IUpdater> result = new ArrayList<IUpdater>();

//        result.add(new DeleteFile(this.adapter.getSourceLibsPath() + "FMDB/Tests"));
//        result.add(new DeleteFile(this.adapter.getSourceLibsPath() + "FMDB/src/sample"));
//        result.add(new DeleteFile(this.adapter.getSourceLibsPath() + "FMDB/src/extra/Swift extensions"));
//        result.add(new DeleteFile(this.adapter.getSourceLibsPath() + "AFNetworking/Example"));
//        result.add(new DeleteFile(this.adapter.getSourceLibsPath() + "AFNetworking/Tests"));

        return result;
    }

    @Override
    public List<IUpdater> getLibraries() {
        List<IUpdater> result = new ArrayList<IUpdater>();

//        String libPath = new File(String.format("%s%s",
//                this.adapter.getSourceLibsPath(), "FMDB")).getAbsolutePath();
//
//        LibraryGit updater = new LibraryGit(
//                "https://github.com/ccgus/fmdb.git",
//                libPath,
//                "v2.5",
//                "FMDB",
//                null,
//                libPath);
//
//        libPath = new File(String.format("%s%s",
//                this.adapter.getSourceLibsPath(), "AFNetworking")).getAbsolutePath();
//
//        result.add(updater);
//
//        updater = new LibraryGit(
//                "https://github.com/AFNetworking/AFNetworking.git",
//                libPath,
//                "2.5.1",
//                "AFNetworking",
//                null,
//                libPath);
//
//        result.add(updater);

        return result;
    }

    @Override
    public List<IUpdater> getCreateFolders() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String sourcePath = this.adapter.getSourcePath();

        // create empty package data
        result.add(new CreateFolder(sourcePath + "/Data/"));

        // create empty package entity
        result.add(new CreateFolder(sourcePath + "/Entity/"));

        // create util folder
        result.add(new CreateFolder(sourcePath + "/Harmony"));

        // create empty package Resources
        result.add(new CreateFolder(sourcePath + "/Resources/"));

        // create empty package Provider
        result.add(new CreateFolder(sourcePath + "/Provider/"));

        // create libs folder
        result.add(new CreateFolder(sourcePath + "/libs/"));

        return result;
    }

    @Override
    public List<IUpdater> getCriteriasFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String filePath = String.format("%s/Criterias/",
                this.adapter.getSourceDataPath());

        String templatePath = this.adapter.getTemplateSourceCriteriasPath();

        result.add(new SourceFile(
                templatePath + "base/CriteriaExpression.h",
                filePath + "Base/CriteriaExpression.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/CriteriaExpression.m",
                filePath + "Base/CriteriaExpression.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/Criterion.h",
                filePath + "Base/Criterion.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/Criterion.m",
                filePath + "Base/Criterion.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/Criteria.h",
                filePath + "Base/Criteria.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/Criteria.m",
                filePath + "Base/Criteria.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/CriteriaValue.h",
                filePath + "Base/Value/CriteriaValue.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/CriteriaValue.m",
                filePath + "Base/Value/CriteriaValue.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/CustomValue.h",
                filePath + "Base/Value/CustomValue.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/CustomValue.m",
                filePath + "Base/Value/CustomValue.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/SelectValue.h",
                filePath + "Base/Value/SelectValue.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/SelectValue.m",
                filePath + "Base/Value/SelectValue.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/StringValue.h",
                filePath + "Base/Value/StringValue.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/StringValue.m",
                filePath + "Base/Value/StringValue.m",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/ArrayValue.h",
                filePath + "Base/Value/ArrayValue.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/value/ArrayValue.m",
                filePath + "Base/Value/ArrayValue.m",
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

        String filePath = String.format("%s/DataBase/",
                this.adapter.getSourceDataPath());

        result.add(new SourceFile(
                templatePath + "TemplateSQLiteOpenHelper.m",
                String.format("%s%sSQLiteOpenHelper.m",
                        filePath,
                        applicationName),
                false));

        result.add(new SourceFile(
                templatePath + "TemplateSQLiteOpenHelper.h",
                String.format("%s%sSQLiteOpenHelper.h",
                        filePath,
                        applicationName),
                false));

        result.add(new SourceFile(
                templatePath + "base/TemplateSQLiteOpenHelperBase.m",
                String.format("%sBase/%sSQLiteOpenHelperBase.m",
                        filePath,
                        applicationName),
                true));

        result.add(new SourceFile(
                templatePath + "base/TemplateSQLiteOpenHelperBase.h",
                String.format("%sBase/%sSQLiteOpenHelperBase.h",
                        filePath,
                        applicationName),
                true));

        result.add(new SourceFile(
                templatePath + "base/ApplicationSQLiteAdapterBase.m",
                filePath + "Base/SQLiteAdapterBase.m",
                true));

        result.add(new SourceFile(
                templatePath + "base/ApplicationSQLiteAdapterBase.h",
                filePath + "Base/SQLiteAdapterBase.h",
                true));

        result.add(new SourceFile(
                templatePath + "ApplicationSQLiteAdapter.m",
                filePath + "SQLiteAdapter.m",
                false));

        result.add(new SourceFile(
                templatePath + "ApplicationSQLiteAdapter.h",
                filePath + "SQLiteAdapter.h",
                false));

        templatePath = this.adapter.getTemplateSourceHarmonySqlPath();
        filePath = String.format("%s/Sql/",
                this.adapter.getSourceHarmonyPath());

        result.add(new SourceFile(
                templatePath + "ContentResolver.h",
                filePath + "ContentResolver.h",
                true));

        result.add(new SourceFile(
                templatePath + "ContentResolver.m",
                filePath + "ContentResolver.m",
                true));

        return result;
    }

    @Override
    public List<IUpdater> getSqlAdapterEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String filePath = String.format("%s/DataBase/",
                this.adapter.getSourceDataPath());

        final String templatePath =
                this.adapter.getTemplateSourceDataPath();

        result.add(new SourceFile(
                templatePath + "base/TemplateSQLiteAdapterBase.h",
                String.format("%sBase/%sSQLiteAdapterBase.h",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "base/TemplateSQLiteAdapterBase.m",
                String.format("%sBase/%sSQLiteAdapterBase.m",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "TemplateSQLiteAdapter.h",
                String.format("%s%sSQLiteAdapter.h",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "TemplateSQLiteAdapter.m",
                String.format("%s%sSQLiteAdapter.m",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "base/ResourceSQLiteAdapterBase.h",
                String.format("%sBase/ResourceSQLiteAdapterBase.h",
                        filePath),
                true));

        result.add(new SourceFile(
                templatePath + "base/ResourceSQLiteAdapterBase.m",
                String.format("%sBase/ResourceSQLiteAdapterBase.m",
                        filePath),
                true));

        result.add(new SourceFile(
                templatePath + "ResourceSQLiteAdapter.h",
                String.format("%sResourceSQLiteAdapter.h",
                        filePath),
                false));

        result.add(new SourceFile(
                templatePath + "ResourceSQLiteAdapter.m",
                String.format("%sResourceSQLiteAdapter.m",
                        filePath),
                false));

        return result;
    }

    @Override
    public List<IUpdater> getActivityLibraries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getCreateView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s/%s/",
                this.adapter.getSourceControllerPath(),
                entity.getName());

//        result.add(new SourceFile(
//                templatePath + "TemplateCreateController.h",
//                String.format("%s%sCreateViewController.h",
//                        filePath,
//                        entity.getName())));
//
//        result.add(new SourceFile(
//                templatePath + "TemplateCreateController.m",
//                String.format("%s%sCreateViewController.m",
//                        filePath,
//                        entity.getName())));
//
//        result.add(new SourceFile(
//                templatePath + "TemplateCreateController.xib",
//                String.format("%s%sCreateViewController.xib",
//                        filePath,
//                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getEditView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

//        String filePath = String.format("%s/%s/",
//                this.adapter.getSourceControllerPath(),
//                entity.getName());
//
//        result.add(new SourceFile(
//                templatePath + "TemplateEditController.h",
//                String.format("%s%sEditViewController.h",
//                        filePath,
//                        entity.getName())));
//
//        result.add(new SourceFile(
//                templatePath + "TemplateEditController.m",
//                String.format("%s%sEditViewController.m",
//                        filePath,
//                        entity.getName())));
//
//        result.add(new SourceFile(
//                templatePath + "TemplateEditController.xib",
//                String.format("%s%sEditViewController.xib",
//                        filePath,
//                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getShowView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s/%s/",
                this.adapter.getSourceControllerPath(),
                entity.getName());

        result.add(new SourceFile(
                templatePath + "TemplateShowController.h",
                String.format("%s%sShowViewController.h",
                        filePath,
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateShowController.m",
                String.format("%s%sShowViewController.m",
                        filePath,
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateShowController.xib",
                String.format("%s%sShowViewController.xib",
                        filePath,
                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getListView(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceControlerPath();

        String filePath = String.format("%s/%s/",
                this.adapter.getSourceControllerPath(),
                entity.getName());

        result.add(new SourceFile(
                templatePath + "TemplateTableViewController.h",
                String.format("%s%sTableViewController.h",
                        filePath,
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateTableViewController.m",
                String.format("%s%sTableViewController.m",
                        filePath,
                        entity.getName())));

        result.add(new SourceFile(
                templatePath + "TemplateTableViewController.xib",
                String.format("%s%sTableViewController.xib",
                        filePath,
                        entity.getName())));

        return result;
    }

    @Override
    public List<IUpdater> getCommonView(EntityMetadata entity,
            boolean isWritable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getViews(boolean isDate, boolean isTime) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = String.format("%sharmony/view/",
                this.adapter.getTemplateSourcePath());
        String filePath = String.format("%s/Harmony/View/",
                this.adapter.getSourcePath());

        result.add(new SourceFile(
                templatePath + "HarmonyDatePicker.h",
                filePath + "HarmonyDatePicker.h"));

        result.add(new SourceFile(
            templatePath + "HarmonyDatePicker.m",
            filePath + "HarmonyDatePicker.m"));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureLibraries(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getFixtureFiles(boolean forceOverwrite) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath();

        String filePath = String.format("%s/Fixture/",
                this.adapter.getSourceDataPath());

        result.add(new SourceFile(
                templatePath + "FixtureBase.h",
                filePath + "FixtureBase.h",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "FixtureBase.m",
                filePath + "FixtureBase.m",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "DataManager.h",
                filePath + "DataManager.h",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "DataManager.m",
                filePath + "DataManager.m",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "DataLoader.h",
                filePath + "DataLoader.h",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "DataLoader.m",
                filePath + "DataLoader.m",
                forceOverwrite));

        filePath = String.format("%s/Fixture/XMLParser/", this.adapter.getSourceDataPath());

        result.add(new SourceFile(
                templatePath + "FixtureXMLToObjectParser.h",
                filePath + "FixtureXMLToObjectParser.h",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "FixtureXMLToObjectParser.m",
                filePath + "FixtureXMLToObjectParser.m",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "ParseValue.h",
                filePath + "ParseValue.h",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "ParseValue.m",
                filePath + "ParseValue.m",
                forceOverwrite));

        return result;
    }

    @Override
    public List<IUpdater> getFixtureEntityDefinitionFiles(String fixtureType,
            EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getFixtureEntityFiles(boolean forceOverwrite,
            String fixtureType, EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceFixturePath();

        String filePath = String.format("%s/Fixture/",
                this.adapter.getSourceDataPath());

        result.add(new SourceFile(
                templatePath + "TemplateDataLoader.h",
                filePath + entity.getName() + "DataLoader.h",
                forceOverwrite));

        result.add(new SourceFile(
                templatePath + "TemplateDataLoader.m",
                filePath + entity.getName() + "DataLoader.m",
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getServices() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITranslateFileUtil getTranslateFileUtil() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IConfigFileUtil getConfigFileUtil() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getProviderFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceProviderPath();
        String filePath = String.format("%s/",
                this.adapter.getSourceProviderPath());

        result.add(new SourceFile(
                templatePath + "utils/base/ApplicationProviderUtilsBase.h",
                filePath + "Utils/Base/ProviderUtilsBase.h",
                true));
        result.add(new SourceFile(
                templatePath + "utils/base/ApplicationProviderUtilsBase.m",
                filePath + "Utils/Base/ProviderUtilsBase.m",
                true));

        result.add(new SourceFile(
                templatePath + "utils/ApplicationProviderUtils.h",
                filePath + "Utils/ProviderUtils.h",
                false));
        result.add(new SourceFile(
                templatePath + "utils/ApplicationProviderUtils.m",
                filePath + "Utils/ProviderUtils.m",
                false));

        result.add(new SourceFile(
                templatePath + "contract/base/ApplicationContractBase.h",
                filePath + "Contract/Base/ContractBase.h",
                true));
        result.add(new SourceFile(
                templatePath + "contract/base/ApplicationContractBase.m",
                filePath + "Contract/Base/ContractBase.m",
                true));

        result.add(new SourceFile(
                templatePath + "base/BatchProvider.h",
                filePath + "Base/BatchProvider.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/BatchProvider.m",
                filePath + "Base/BatchProvider.m",
                true));

        result.add(new SourceFile(
                templatePath + "base/DeleteBatch.h",
                filePath + "Base/DeleteBatch.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/DeleteBatch.m",
                filePath + "Base/DeleteBatch.m",
                true));

        result.add(new SourceFile(
                templatePath + "base/InsertBatch.h",
                filePath + "Base/InsertBatch.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/InsertBatch.m",
                filePath + "Base/InsertBatch.m",
                true));

        result.add(new SourceFile(
                templatePath + "base/ItemBatch.h",
                filePath + "Base/ItemBatch.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/ItemBatch.m",
                filePath + "Base/ItemBatch.m",
                true));

        result.add(new SourceFile(
                templatePath + "base/UpdateBatch.h",
                filePath + "Base/UpdateBatch.h",
                true));
        result.add(new SourceFile(
                templatePath + "base/UpdateBatch.m",
                filePath + "Base/UpdateBatch.m",
                true));

        return result;
    }

    @Override
    public List<IUpdater> getProviderAdaptersEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateSourceProviderPath();
        String filePath = String.format("%s/",
                this.adapter.getSourceProviderPath());

        // Make contracts
        result.add(new SourceFile(
                templatePath + "contract/ResourceContract.h",
                filePath + "Contract/ResourceContract.h"));
        result.add(new SourceFile(
                templatePath + "contract/ResourceContract.m",
                filePath + "Contract/ResourceContract.m"));

        result.add(new SourceFile(
                templatePath + "contract/base/ResourceContractBase.h",
                filePath + "Contract/Base/ResourceContractBase.h",
                true));
        result.add(new SourceFile(
                templatePath + "contract/base/ResourceContractBase.m",
                filePath + "Contract/Base/ResourceContractBase.m",
                true));

        result.add(new SourceFile(
                templatePath + "contract/TemplateContract.h",
                filePath + "Contract/" + entity.getName() + "Contract.h"));
        result.add(new SourceFile(
                templatePath + "contract/TemplateContract.m",
                filePath + "Contract/" + entity.getName() + "Contract.m"));

        result.add(new SourceFile(
                templatePath + "contract/base/TemplateContractBase.h",
                filePath + "Contract/Base/" + entity.getName() + "ContractBase.h",
                true));
        result.add(new SourceFile(
                templatePath + "contract/base/TemplateContractBase.m",
                filePath + "Contract/Base/" + entity.getName() + "ContractBase.m",
                true));

        // Provider utils
        if (!entity.isInternal()) {
            result.add(new SourceFile(
                    templatePath + "utils/TemplateProviderUtils.h",
                    filePath + "Utils/" + entity.getName() + "ProviderUtils.h"));
            result.add(new SourceFile(
                    templatePath + "utils/TemplateProviderUtils.m",
                    filePath + "Utils/" + entity.getName() + "ProviderUtils.m"));

            result.add(new SourceFile(
                    templatePath + "utils/base/TemplateProviderUtilsBase.h",
                    filePath + "Utils/Base/" + entity.getName() + "ProviderUtilsBase.h",
                    true));
            result.add(new SourceFile(
                    templatePath + "utils/base/TemplateProviderUtilsBase.m",
                    filePath + "Utils/base/" + entity.getName() + "ProviderUtilsBase.m",
                    true));
        }

        return result;
    }

    @Override
    public boolean isDataLoaderAlreadyGenerated() {

        final String dataLoaderPath = String.format("%s/Fixture/DataLoader.h",
                this.adapter.getSourceDataPath());

        return new File(dataLoaderPath).exists();
    }

    @Override
    public List<IUpdater> getTestProviderEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = String.format("%sprovider/",
        		this.adapter.getTemplateTestsPath());
        String filePath = String.format("%s/Provider",
                this.adapter.getTestPath());

        result.add(new SourceFile(
                templatePath + "base/TemplateProviderUtilsTestBase.h",
                String.format("%s/Base/%sProviderUtilsTestBase.h",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "base/TemplateProviderUtilsTestBase.m",
                String.format("%s/Base/%sProviderUtilsTestBase.m",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "TemplateProviderUtilsTest.h",
                String.format("%s/%sProviderUtilsTest.h",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "TemplateProviderUtilsTest.m",
                String.format("%s/%sProviderUtilsTest.m",
                        filePath,
                        entity.getName()),
                false));
        return result;
    }

    @Override
    public List<IUpdater> getTestProjectFilesToClear() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getTestProjectCreateFolders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getTestProjectLibraries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getTestProjectFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getTestFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateTestsPath();
        String filePath = String.format("%s/SqlAdapter",
                this.adapter.getTestPath());

        result.add(new SourceFile(
                templatePath + "base/TestDBBase.h",
                filePath + "/Base/TestDBBase.h",
                true));

        result.add(new SourceFile(
                templatePath + "base/TestDBBase.m",
                filePath + "/Base/TestDBBase.m",
                true));

        templatePath = String.format("%sprovider/",
        		this.adapter.getTemplateTestsPath());
        filePath = String.format("%s/Provider",
                this.adapter.getTestPath());

        result.add(new SourceFile(
                templatePath + "base/ProviderUtilsTestBase.h",
                filePath + "/Base/ProviderUtilsTestBase.h",
                true));

        result.add(new SourceFile(
                templatePath + "base/ProviderUtilsTestBase.m",
                filePath + "/Base/ProviderUtilsTestBase.m",
                true));

        result.add(new SourceFile(
                templatePath + "ProviderUtilsTest.h",
                filePath + "/ProviderUtilsTest.h",
                false));

        result.add(new SourceFile(
                templatePath + "ProviderUtilsTest.m",
                filePath + "/ProviderUtilsTest.m",
                false));

        return result;
    }

    @Override
    public List<IUpdater> getTestEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String templatePath = this.adapter.getTemplateTestsPath();
        String filePath = String.format("%s/SqlAdapter",
                this.adapter.getTestPath());

        result.add(new SourceFile(
                templatePath + "base/TemplateTestDBBase.h",
                String.format("%s/Base/%sTestDBBase.h",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "base/TemplateTestDBBase.m",
                String.format("%s/Base/%sTestDBBase.m",
                        filePath,
                        entity.getName()),
                true));

        filePath = String.format("%s/Utils",
                this.adapter.getTestPath());

        result.add(new SourceFile(
                templatePath + "utils/base/TemplateTestUtilsBase.h",
                String.format("%s/Base/%sTestUtilsBase.h",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "utils/base/TemplateTestUtilsBase.m",
                String.format("%s/Base/%sTestUtilsBase.m",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "utils/TemplateTestUtils.h",
                String.format("%s/%sTestUtils.h",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "utils/TemplateTestUtils.m",
                String.format("%s/%sTestUtils.m",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "utils/TestUtils.h",
                String.format("%s/TestUtils.h",
                        filePath),
                false));

        result.add(new SourceFile(
                templatePath + "utils/TestUtils.m",
                String.format("%s/TestUtils.m",
                        filePath),
                false));
        templatePath = this.adapter.getTemplateTestsPath();
        filePath = String.format("%s/SqlAdapter/",
                this.adapter.getTestPath());

        result.add(new SourceFile(
                templatePath + "TemplateTestDB.h",
                String.format("%s/%sTestDB.h",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(
                templatePath + "TemplateTestDB.m",
                String.format("%s/%sTestDB.m",
                        filePath,
                        entity.getName()),
                false));

        result.add(new SourceFile(templatePath + "testInfo.plist", this.adapter.getTestPath() + "info.plist", false));

        return result;
    }

    @Override
    public List<IUpdater> getMenuFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getMenuBaseFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getMenuFiles(String menuName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getAvailableMenus() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getEntityFiles(EntityMetadata entity) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String filePath = String.format("%s/",
                this.adapter.getSourceEntityPath());

        final String templatePath =
                this.adapter.getTemplateSourceEntityPath();

        result.add(new SourceFile(
                templatePath + "TemplateEntity.m",
                String.format("%s/%s.m",
                        filePath,
                        entity.getName()),
                true));

        result.add(new SourceFile(
                templatePath + "TemplateEntity.h",
                String.format("%s%s.h",
                        filePath,
                        entity.getName()),
                true));

        return result;
    }

    @Override
    public List<IUpdater> getStaticViewFiles(String packageName,
            String viewName, ViewType type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> updateHomeActivity(String activity, String buttonId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getEntityBaseFiles() {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String filePath = String.format("%s/Base/",this.adapter.getSourceEntityPath());

        final String templatePath = this.adapter.getTemplateSourceEntityBasePath();

        result.add(new SourceFile(
                templatePath + "EntityResourceBase.h",
                filePath + "EntityResourceBase.h",
                true));

        result.add(new SourceFile(
                templatePath + "EntityResourceBase.m",
                filePath + "EntityResourceBase.m",
                true));

        result.add(new SourceFile(
                templatePath + "Resource.h",
                filePath + "Resource.h",
                true));

        return result;
    }

    @Override
    public List<IUpdater> updateEnum(EnumMetadata enumMeta) {
        List<IUpdater> result = new ArrayList<IUpdater>();

        String filePath = String.format("%s/",
                this.adapter.getSourceEntityPath());

        final String templatePath = this.adapter.getTemplateSourceEntityPath();

        result.add(new SourceFile(
                templatePath + "TemplateEnum.h",
                String.format("%s%s.h", filePath, enumMeta.getName()),
                true));

        return result;
    }

}
