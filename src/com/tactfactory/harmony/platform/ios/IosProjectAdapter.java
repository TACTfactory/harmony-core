package com.tactfactory.harmony.platform.ios;

import java.util.List;

import com.tactfactory.harmony.generator.CommonGenerator.ViewType;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.platform.IAdapterProject;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.updater.old.IConfigFileUtil;
import com.tactfactory.harmony.updater.old.ITranslateFileUtil;

import freemarker.template.Configuration;

public class IosProjectAdapter implements IAdapterProject{

    public IosProjectAdapter(IosAdapter iosAdapter) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<IUpdater> getProjectFilesToClear() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getProjectFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getStartViewFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getLibraries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getCreateFolders() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getCriteriasFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getDatabaseFiles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getSqlAdapterEntityFiles(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getActivityLibraries() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getCreateView(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getEditView(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getShowView(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getListView(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getCommonView(EntityMetadata entity,
            boolean isWritable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getViews(boolean isDate, boolean isTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getFixtureLibraries(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getFixtureFiles(boolean forceOverwrite) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getProviderAdaptersEntityFiles(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDataLoaderAlreadyGenerated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<IUpdater> getTestProviderEntityFiles(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getTestEntityFiles(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
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
    public List<IUpdater> updateEnum(EnumMetadata enumMeta, Configuration cfg) {
        // TODO Auto-generated method stub
        return null;
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
    public List<IUpdater> getEntityFiles(EntityMetadata entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<IUpdater> getEntityBaseFiles() {
        // TODO Auto-generated method stub
        return null;
    }

}
