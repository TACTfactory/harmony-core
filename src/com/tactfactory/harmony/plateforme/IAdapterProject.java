package com.tactfactory.harmony.plateforme;

import java.util.List;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.updater.IConfigFileUtil;
import com.tactfactory.harmony.updater.ITranslateFileUtil;
import com.tactfactory.harmony.updater.IUpdater;
import com.tactfactory.harmony.updater.IUpdaterProject;

public interface IAdapterProject {
    //Used by ProjectGenerator
    List<IUpdater> getProjectFilesToClear();
    List<IUpdater> getProjectFiles();
    List<IUpdater> getStartViewFiles();
    List<IUpdater> getLibraries();
    List<IUpdater> getCreateFolders();
    
    //Used by SqlGenerator
    List<IUpdater> getCriteriasFiles();
    List<IUpdater> getCriteriasEntityFiles(EntityMetadata entity);
    List<IUpdater> getDatabaseFiles();
    List<IUpdater> getSqlAdapterEntityFiles(EntityMetadata entity);
    
    IUpdaterProject getUpdaterProject();
    
    //Used by ActivityGenerator
    List<IUpdater> getActivityLibraries();
    List<IUpdater> getCreateView(EntityMetadata entity);
    List<IUpdater> getEditView(EntityMetadata entity);
    List<IUpdater> getShowView(EntityMetadata entity);
    List<IUpdater> getListView(EntityMetadata entity);
    List<IUpdater> getCommonView(EntityMetadata entity, boolean isWritable);
    List<IUpdater> getViews(boolean isDate, boolean isTime);
    
    //Used by FixtureGenerator
    List<IUpdater> getFixtureLibraries(String type);
    List<IUpdater> getFixtureFiles(boolean forceOverwrite);
    List<IUpdater> getFixtureEntityFiles(boolean forceOverwrite, String fixtureType, EntityMetadata entity);
    
    //Used by ApplicationGenerator
    List<IUpdater> getApplicationFiles();
    List<String> getServices();
    
    //Used by TranslationGenerator
    ITranslateFileUtil getTranslateFileUtil();
    
    IConfigFileUtil getConfigFileUtil();
    
    //Used by ProviderGenerator
    List<IUpdater> getProviderFiles();
    List<IUpdater> getProviderAdaptersEntityFiles(EntityMetadata entity);
    
    //Used by TestProviderGenerator
    boolean isDataLoaderAlreadyGenerated();
    List<IUpdater> getTestProviderEntityFiles(EntityMetadata entity);
    
    //Used by TestProjectGenerator
    List<IUpdater> getTestProjectFilesToClear();
    List<IUpdater> getTestProjectCreateFolders();
    List<IUpdater> getTestProjectLibraries();
    List<IUpdater> getTestProjectFiles();
    
    //Used by TestGenerator
    List<IUpdater> getTestFiles();
    List<IUpdater> getTestEntityFiles(EntityMetadata entity);
    
    //Used by MenuGenerator
    List<IUpdater> getMenuFiles();
    List<IUpdater> getMenuBaseFiles();
    List<IUpdater> getMenuFiles(String menuName);
    List<String> getAvailableMenus();
    
    //Used by EntityGenerator
    List<IUpdater> getEntityFiles(EntityMetadata entity);
}
