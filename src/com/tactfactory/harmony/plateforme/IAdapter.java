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
import java.util.List;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;

import freemarker.template.Configuration;

public interface IAdapter {
    ApplicationMetadata getApplicationMetadata();

    // Abstract Methods
    /**
     * Generate platform Namespace.
     *
     * @param cm Entity to extract the namespace
     * @param type The namespace type.
     * @return String Namespace
     */
    String getNameSpace(ClassMetadata cm, String type);

    /** Generate platform Namespace.
     *
     * @param cm Entity to extract the namespace
     * @param type The namespace type.
     * @return String Namespace
     */
    String getNameSpaceEntity(ClassMetadata cm, String type);

    /**
	 * Convert a Harmony type into a native type.
	 *
	 * @param field The field name
	 * @return String of the native type
	 */
    String getNativeType(FieldMetadata field);

    /** Convert image structure to alternative resolution. */
    void resizeImage();
    
    /**
     * Checks whether the two versions of the given file are different.
     * Header can be ignored.
     * 
     * @param oldContent Old content of the file
     * @param newContent New content of the file
     * @param fileName The file name
     * @param ignoreHeader True if ignore header
     * 
     * @return True if files are the same
     */
    boolean filesEqual(String oldContent,
            String newContent,
            String fileName,
            boolean ignoreHeader);

    // Utils
    /**
     * Get the template project path.
     * @return The template project path
     */
    String getTemplateProjectPath();

    /**
     * Get the libraries path.
     * @return The libraries path
     */
    String getLibsPath();

    /**
     * Get the tests path.
     * @return The tests path
     */
    String getTestPath();

    /**
     * Get the test libraries path.
     * @return The test libraries path
     */
    String getTestLibsPath();

    /**
     * Get the sources path.
     * @return The sources path
     */
    String getSourcePath();
    
    /**
     * Get the sources path.
     * @return The sources path
     */
    String getSourceControllerPath();
    
    /**
     * Get the sources path for entities.
     * @return The entities sources path
     */
    String getSourceEntityPath();

    /**
     * Get the widgets path.
     * @return The widgets path
     */
    String getWidgetPath();

    /**
     * Get the utility classes path.
     * @return The utility classes path
     */
    String getUtilPath();

    /**
     * Get the project's menu path.
     * @return The menu path
     */
    String getMenuPath();
    
    /**
     * Get the project's menu base path.
     * @return The menu base path
     */
    String getMenuBasePath();
    
    /**
     * Get the project's menu path.
     * @return The menu path
     */
    String getTemplateMenuPath();
    
    /**
     * Get the project's menu base path.
     * @return The menu base path
     */
    String getTemplateMenuBasePath();

    /**
     * Get the widget's templates path.
     * @return The widget's templates path
     */
    String getTemplateWidgetPath();

    /**
     * Get the utility classes' templates path.
     * @return The utility classes' templates path
     */
    String getTemplateUtilPath();

    /**
     * Get the utility classes' templates path.
     * @return The utility classes' templates path
     */
    String getTemplateUtilityPath();

    /**
     * Get the sources's templates path.
     * @return The source's templates path
     */
    String getTemplateSourcePath();

    /**
     * Get the controllers' templates path.
     * @return The controllers' templates path
     */
    String getTemplateSourceControlerPath();

    /**
     * Get the services' templates path.
     * @return The services' templates path
     */
    String getTemplateSourceServicePath();

    /**
     * Get the entities' templates path.
     * @return The entities' templates path
     */
    String getTemplateSourceEntityBasePath();

    /**
     * Get the providers' templates path.
     * @return The providers' templates path
     */
    String getTemplateSourceProviderPath();

    /**
     * Get the criteria's templates path.
     * @return The criteria's templates path
     */
    String getTemplateSourceCriteriasPath();

    /**
     * Get the fixtures' templates path.
     * @return The fixtures' templates path
     */
    String getTemplateSourceFixturePath();

    /**
     * Get the common's templates path.
     * @return The common's templates path
     */
    String getTemplateSourceCommonPath();

    /**
     * Get the assets path.
     * @return The assets path
     */
    String getAssetsPath();

    

    /**
     * Get the manifest's path.
     * @return The manifest's path
     */
    String getManifestPathFile();

    /**
     * Get the manifest's template path.
     * @return The manifest's template path
     */
    String getTemplateManifestPathFile();

    /**
     * Get the strings path.
     * @return The strings path
     */
    String getStringsPathFile();

    /**
     * Get the strings template path.
     * @return The strings template path
     */
    String getTemplateStringsPathFile();

    /**
     * Get the configs path.
     * @return The configs path
     */
    String getConfigsPathFile();

    /**
     * Get the configs template path.
     * @return The configs template path
     */
    String getTemplateConfigsPathFile();

    /**
     * Get the tests templates path.
     * @return The tests templates path
     */
    String getTemplateTestsPath();

    /**
     * Get the test project templates path.
     * @return The test project templates path
     */
    String getTemplateTestProjectPath();

    /**
     * Get the strings tests path.
     * @return The strings tests path
     */
    String getStringsTestPathFile();

    /**
     * Get the strings tests templates path.
     * @return The strings tests templates path
     */
    String getTemplateStringsTestPathFile();
    
    /**
     * Get the resource path.
     * @return The resource path
     */
    String getRessourcePath();

    /**
     * Get the resource's templates path.
     * @return The resource's templates path
     */
    String getTemplateRessourcePath();

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getSourceDataNameSpace();

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getAnnotationBundleTemplatePath();


    /**
     * Get the source data namespace.
     * @param bundleOwnerName The bundle owner name
     * @param bundleNamespace The bundle namespace
     * @param bundleName The bundle name
     * @return The source data namespace
     */
    String getAnnotationBundlePath(
             String bundleOwnerName,
             String bundleNamespace,
             String bundleName);

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getCommandBundleTemplatePath();


    /**
     * Get the source data namespace.
     * @param bundleOwnerName The bundle owner name
     * @param bundleNamespace The bundle namespace
     * @param bundleName The bundle name
     * @return The source data namespace
     */
    String getCommandBundlePath(
             String bundleOwnerName,
             String bundleNamespace,
             String bundleName);

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getMetaBundleTemplatePath();


    /**
     * Get the source data namespace.
     * @param bundleOwnerName The bundle owner name
     * @param bundleNamespace The bundle namespace
     * @param bundleName The bundle name
     * @return The source data namespace
     */
    String getMetaBundlePath(
             String bundleOwnerName,
             String bundleNamespace,
             String bundleName);

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getTemplateBundleTemplatePath();


    /**
     * Get the source data namespace.
     * @param bundleOwnerName The bundle owner name
     * @param bundleNamespace The bundle namespace
     * @param bundleName The bundle name
     * @return The source data namespace
     */
    String getTemplateBundlePath(
             String bundleOwnerName,
             String bundleNamespace,
             String bundleName);

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getParserBundleTemplatePath();


    /**
     * Get the source data namespace.
     * @param bundleOwnerName The bundle owner name
     * @param bundleNamespace The bundle namespace
     * @param bundleName The bundle name
     * @return The source data namespace
     */
    String getParserBundlePath(
             String bundleOwnerName,
             String bundleNamespace,
             String bundleName);

    /**
     * Get the source data namespace.
     * @return The source data namespace
     */
    String getBundleTemplatePath();


    /**
     * Get the source data namespace.
     * @param bundleOwnerName The bundle owner name
     * @param bundleName The bundle name
     * @return The source data namespace
     */
    String getBundlePath(
             String bundleOwnerName,
             String bundleName);

    // Getter and Setter
    /**
     * @return the project
     */
    String getProject();

    /**
     * @param project the project to set
     */
    void setProject( String project);
    /**
     * @return the resource
     */
    String getResource();

    /**
     * @return the resource
     */
    String getAssets();

    /**
     * @return the source
     */
    String getSource();

    /**
     * @return the model
     */
    String getModel();

    /**
     * @return the view
     */
    String getView();
    
    /**
     * @return the large view
     */
    String getLargeView();

    /**
     * @return the controller
     */
    String getController();

    /**
     * @return the manifest
     */
    String getManifest();

    /**
     * @return the platform
     */
    String getPlatform();

    /**
     * @param platform the platform to set
     */
    void setPlatform( String platform);

    /**
     * @param ressource the resource to set
     */
    void setResource( String ressource);

    /**
     * @param assets the assets folder
     */
    void setAssets( String assets);

    /**
     * @param source the source to set
     */
    void setSource( String source);

    /**
     * @param model the model to set
     */
    void setModel( String model);

    /**
     * @param view the view to set
     */
    void setView( String view);
    /**
     * @param controller the controller to set
     */
    void setController( String controller);

    /**
     * @param manifest the manifest to set
     */
    void setManifest( String manifest);

    /**
     * @return the data
     */
    String getData();

    /**
     * @param data the data to set
     */
    void setData( String data);

    /**
     * @return the provider
     */
    String getProvider();

    /**
     * @param provider the provider to set
     */
    void setProvider( String provider);

    /**
     * @return the criterias
     */
    String getCriterias();

    /**
     * @param criterias the criterias to set
     */
    void setCriterias( String criterias);
    
    /**
     * @return the base
     */
    String getBase();

    /**
     * @param base the base to set
     */
    void setBase( String base);

    /**
     * @return the common
     */
    String getCommon();

    /**
     * @param common the common to set
     */
    void setCommon( String common);

    /**
     * @return the service
     */
    String getService();

    /**
     * @param service the service to set
     */
    void setService( String service);


    /**
     * @return the service
     */
    String getFixture();

    /**
     * @param fixture the fixture folder
     */
    void setFixture( String fixture);


    /**
     * @return the values
     */
    String getValues();
    
    /**
     * @return the values-xlarge
     */
    String getValuesXLarge();

    /**
     * @param values the values to set
     */
    void setValues( String values);

    /**
     * @return the libs
     */
    String getLibs();

    /**
     * @param libs the libs to set
     */
    void setLibs( String libs);
    /**
     * @return the HomeActivity filename
     */
    String getHome();

    /**
     * @param home the HomeActivity filename to set
     */
    void setHome( String home);

    /**
     * @return the strings.xml
     */
    String getStrings();

    /**
     * @return the configs.xml
     */
    String getConfigs();

    /**
     * @param config The config.
     */
    void setConfigs( String config);

    /**
     * @param strings the strings.xml filename to set
     */
    void setStrings( String strings);

    /**
     * @return the test
     */
    String getTest();

    /**
     * @param test the test to set
     */
    void setTest( String test);

    /**
     * @return the testLibs
     */
    String getTestLibs();

    /**
     * @param testLibs the testLibs to set
     */
    void setTestLibs( String testLibs);

    /**
     * @return the harmony
     */
    String getHarmony();

    /**
     * @param harmony the harmony to set
     */
    void setHarmony( String harmony);

    /**
     * @return the widget
     */
    String getWidget();

    /**
     * @param widget the widget to set
     */
    void setWidget( String widget);

    /**
     * @return the util
     */
    String getUtil();

    /**
     * @param util the util to set
     */
    void setUtil( String util);

    /**
     * @return the menu
     */
    String getMenu();

    /**
     * @param menu the menu to set
     */
    void setMenu( String menu);


    /**
     * @return the utility path
     */
    String getUtilityPath();

    /**
     * @param utilityPath The utility path to set
     */
    void setUtilityPath( String utilityPath);

    /**
     * @return the bundleTemplates
     */
    String getBundleTemplates();

    /**
     * @param bundleTemplates the bundleTemplates to set
     */
    void setBundleTemplates( String bundleTemplates);

    /**
     * @return the annotationsBundleTemplates
     */
    String getAnnotationsBundleTemplates();

    /**
     * @param annotationsBundleTemplates the annotationsBundleTemplates to set
     */
    void setAnnotationsBundleTemplates( String annotationsBundleTemplates);

    /**
     * @return the templateBundleTemplates
     */
    String getTemplateBundleTemplates();

    /**
     * @param templateBundleTemplates the templateBundleTemplates to set
     */
    void setTemplateBundleTemplates( String templateBundleTemplates);

    /**
     * @return the parserBundleTemplates
     */
    String getParserBundleTemplates();

    /**
     * @param parserBundleTemplates the parserBundleTemplates to set
     */
    void setParserBundleTemplates( String parserBundleTemplates);

    /**
     * @return the metaBundleTemplates
     */
    String getMetaBundleTemplates();

    /**
     * @param metaBundleTemplates the metaBundleTemplates to set
     */
    void setMetaBundleTemplates( String metaBundleTemplates);

    /**
     * @return the commandBundleTemplates
     */
    String getCommandBundleTemplates();

    /**
     * @param commandBundleTemplates the commandBundleTemplates to set
     */
    void setCommandBundleTemplates( String commandBundleTemplates);

    /**
     * Gets the source file manipulator associated to this adapter.
     *  
     * @param file The file to open.
     * 
     * @return The associated file manipulator
     */
    SourceFileManipulator getFileManipulator(
             File file,
             Configuration config);
    
    //Project generator
    List<String> getDirectoryForResources();
    
    IAdapterProject getAdapterProject();
}
