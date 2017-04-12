/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.platform;

import java.io.File;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;

/** Base Adapter of project structure. */
public abstract class BaseAdapter implements IAdapter {
	// Structure
	/** Project path. */
	private String project;
	/** Platform path. */
	private String platform;
	/** Resources path. */
	private String resource;
	/** Assets path. */
	private String assets;
	/** Source path. */
	private String source;
	/** Libs path. */
	private String libs;
	/** Tests path. */
	private String test;
	/** Tests libraries path. */
	private String testLibs;
	/** Harmony path. */
	private String harmony;
	/** Widgets path. */
	private String widget;
	/** Utility classes path. */
	private String util;
	/** Template Utility files path. */
	private String utilityPath;

	// MVC
	/** Models path. */
	private String model		= "entity";
	/** Views path. */
	private String view 		= "layout";
	/** Views path. */
	private String largeView 		= "layout-xlarge";
	/** Values path. */
	private String values		= "values";
	/** Values path. */
	private String valuesXLarge		= "values-xlarge";
	/** Controllers path. */
	private String controller 	= "view";
	/** Data path. */
	private String data			= "data";
	/** Providers path. */
	private String provider		= "provider";
	/** Common path. */
	private String common		= "common";
	/** Services path. */
	private String service		= "service";
	/** Fixtures path. */
	private String fixture 		= "fixture";
	/** Criterias path. */
	private String criterias  	= "criterias";
	/** Base path. */
	private String base  		= "base";

	// File
	/** Manifest path. */
	private String manifest;
	/** Home path. */
	private String home;
	/** Strings path. */
	private String strings;
	/** Configs path. */
	private String configs;

	/** Menu path. */
	private String menu;

    public BaseAdapter(final BaseAdapter adapter) {
        this();
        adapter.cloneTo(this);
    }

    public BaseAdapter() { }

	@Override
	public final ApplicationMetadata getApplicationMetadata() {
	    //TODO return a copy of ApplicationMetadata.INSTANCE
	    // to preserve data modifications
	    return ApplicationMetadata.INSTANCE;
	}

	// Utils
	/**
	 * Get the template project path.
	 * @return The template project path
	 */
	@Override
    public final String getTemplateProjectPath() {
		return String.format("%s%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getProject());
	}

	/**
	 * Get the libraries path.
	 * @return The libraries path
	 */
	@Override
    public final String getLibsPath() {
		return String.format("%s%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getLibs());
	}

	/**
	 * Get the tests path.
	 * @return The tests path
	 */
	@Override
    public String getTestPath() {
		return String.format("%s%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getTest());
	}

	/**
	 * Get the test libraries path.
	 * @return The test libraries path
	 */
	@Override
    public final String getTestLibsPath() {
		return String.format("%s%s/%s/",
		        Harmony.getProjectPath(),
				this.getPlatform(),
				this.getTestLibs());
	}

	/**
	 * Get the sources path.
	 * @return The sources path
	 */
	@Override
    public String getSourcePath() {
		return String.format("%s%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource());
	}

	/**
	 * Get the sources path.
	 * @return The sources path
	 */
	@Override
    public String getSourceControllerPath() {
		return String.format("%s%s/%s/",
				this.getSourcePath(),
				this.getApplicationMetadata().getProjectNameSpace(),
				this.getController());
	}

	/**
	 * Get the widgets path.
	 * @return The widgets path
	 */
	@Override
    public final String getWidgetPath() {
		return String.format("%s%s/%s/%s/",
				this.getSourcePath(),
				this.getApplicationMetadata().getProjectNameSpace(),
				this.getHarmony(),
				this.getWidget());
	}

	/**
	 * Get the utility classes path.
	 * @return The utility classes path
	 */
	@Override
    public String getUtilPath() {
		return String.format("%s%s/%s/%s/",
				this.getSourcePath(),
				this.getApplicationMetadata().getProjectNameSpace(),
				this.getHarmony(),
				this.getUtil());
	}

	/**
	 * Get the project's menu path.
	 * @return The menu path
	 */
	@Override
    public final String getMenuPath() {
		return String.format("%s%s/%s/",
				this.getSourcePath(),
				this.getApplicationMetadata().getProjectNameSpace(),
				this.getMenu());
	}

	/**
	 * Get the project's menu base path.
	 * @return The menu base path
	 */
	@Override
    public final String getMenuBasePath() {
		return String.format("%s%s/",
				this.getMenuPath(),
				this.getBase());
	}

	/**
	 * Get the project's menu path.
	 * @return The menu path
	 */
	@Override
    public final String getTemplateMenuPath() {
		return String.format("%s%s/",
				this.getTemplateSourcePath(),
				this.getMenu());
	}

	/**
	 * Get the project's menu base path.
	 * @return The menu base path
	 */
	@Override
    public final String getTemplateMenuBasePath() {
		return String.format("%s%s/",
				this.getTemplateMenuPath(),
				this.getBase());
	}

	/**
	 * Get the widget's templates path.
	 * @return The widget's templates path
	 */
	@Override
    public final String getTemplateWidgetPath() {
		return String.format("%s%s/",
				this.getTemplateSourcePath(),
				this.getWidget());
	}

	/**
	 * Get the utility classes' templates path.
	 * @return The utility classes' templates path
	 */
	@Override
    public final String getTemplateUtilPath() {
		return String.format("%s%s/%s/",
		        this.getTemplateSourcePath(),
				this.getHarmony(),
				this.getUtil());
	}

	/**
     * Get the utility classes' templates path.
     * @return The utility classes' templates path
     */
    public final String getTemplateEntityPath() {
        return String.format("%s%s/%s/",
                this.getTemplateSourcePath(),
                this.getHarmony(),
                this.getUtil());
    }

	/**
	 * Get the utility classes' templates path.
	 * @return The utility classes' templates path
	 */
	@Override
    public final String getTemplateUtilityPath() {
	    //Path begin with / because it is interpreted by freemarker.
		return String.format("/%s%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getUtilityPath());
	}

	/**
	 * Get the sources's templates path.
	 * @return The source's templates path
	 */
	@Override
    public final String getTemplateSourcePath() {
		return String.format("%s%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getSource());
	}

	/**
	 * Get the controllers' templates path.
	 * @return The controllers' templates path
	 */
	@Override
    public final String getTemplateSourceControlerPath() {
		return String.format("%s%s/",
		        this.getTemplateSourcePath(),
				this.getController());
	}

	/**
	 * Get the services' templates path.
	 * @return The services' templates path
	 */
	@Override
    public final String getTemplateSourceServicePath() {
		return String.format("%s%s/",
		        this.getTemplateSourcePath(),
				this.getService());
	}

	/**
	 * Get the entities' templates path.
	 * @return The entities' templates path
	 */
	@Override
    public final String getTemplateSourceEntityBasePath() {
		return String.format("%s%s/%s/",
		        this.getTemplateSourcePath(),
				this.getModel(),
				"base");
	}

	/**
	 * Get the providers' templates path.
	 * @return The providers' templates path
	 */
	@Override
    public final String getTemplateSourceProviderPath() {
		return String.format("%s%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getSource(),
				this.getProvider());
	}

	/**
     * Get the data' templates path.
     * @return The data' templates path
     */
    public final String getTemplateSourceDataPath() {
        return String.format("%s%s/%s/%s/",
                Harmony.getTemplatesPath(),
                this.getPlatform(),
                this.getSource(),
                this.getData());
    }

	/**
	 * Get the criteria's templates path.
	 * @return The criteria's templates path
	 */
	@Override
    public final String getTemplateSourceCriteriasPath() {
		return String.format("%s%s/",
		        this.getTemplateSourcePath(),
				this.getCriterias());
	}

	/**
	 * Get the fixtures' templates path.
	 * @return The fixtures' templates path
	 */
	@Override
    public final String getTemplateSourceFixturePath() {
		return String.format("%s%s/",
		        this.getTemplateSourcePath(),
				this.getFixture());
	}

	/**
	 * Get the common's templates path.
	 * @return The common's templates path
	 */
	@Override
    public final String getTemplateSourceCommonPath() {
		return String.format("%s%s/",
		        this.getTemplateSourcePath(),
				this.getCommon());
	}

	/**
	 * Get the assets path.
	 * @return The assets path
	 */
	@Override
    public final String getAssetsPath() {
		return String.format("%s%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getAssets());
	}

	/**
	 * Get the manifest's path.
	 * @return The manifest's path
	 */
	@Override
    public final String getManifestPathFile() {
		return String.format("%s/%s/%s",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getManifest());
	}

	/**
	 * Get the manifest's template path.
	 * @return The manifest's template path
	 */
	@Override
    public final String getTemplateManifestPathFile() {
		return String.format("%s%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getProject(),
				this.getManifest());
	}

	/**
	 * Get the strings path.
	 * @return The strings path
	 */
	@Override
    public String getStringsPathFile() {
		return String.format("%s%s/%s/%s/%s",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValues(),
				this.getStrings());
	}

	/**
	 * Get the strings template path.
	 * @return The strings template path
	 */
	@Override
    public String getTemplateStringsPathFile() {
		return String.format("%s%s/%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValues(),
				this.getStrings());
	}

	/**
	 * Get the configs path.
	 * @return The configs path
	 */
	@Override
    public String getConfigsPathFile() {
		return String.format("%s%s/%s/%s/%s",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValues(),
				this.getConfigs());
	}

	/**
	 * Get the configs template path.
	 * @return The configs template path
	 */
	@Override
    public String getTemplateConfigsPathFile() {
		return String.format("%s%s/%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValues(),
				this.getConfigs());
	}

	/**
	 * Get the tests templates path.
	 * @return The tests templates path
	 */
	@Override
    public String getTemplateTestsPath() {
		return String.format("%s%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getTest());
	}

	/**
	 * Get the test project templates path.
	 * @return The test project templates path
	 */
	@Override
    public final String getTemplateTestProjectPath() {
		return String.format("%s%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getTest(),
				this.getProject());
	}

	/**
	 * Get the strings tests path.
	 * @return The strings tests path
	 */
	@Override
    public String getStringsTestPathFile() {
		return String.format("%s%s/%s/%s/%s/%s",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getTest(),
				this.getResource(),
				this.getValues(),
				this.getStrings());
	}

	/**
	 * Get the strings tests templates path.
	 * @return The strings tests templates path
	 */
	@Override
    public String getTemplateStringsTestPathFile() {
		return String.format("%s%s/%s/%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getTest(),
				this.getResource(),
				this.getValues(),
				this.getStrings());
	}

	/**
     * Get the resource path.
     * @return The resource path
     */
    @Override
    public String getRessourcePath() {
        return String.format("%s%s/%s/",
                Harmony.getProjectPath(),
                this.getPlatform(),
                this.getResource());
    }

	/**
     * Get the resource's templates path.
     * @return The resource's templates path
     */
    @Override
    public String getTemplateRessourcePath() {
        return String.format("%s%s/%s/",
                Harmony.getTemplatesPath(),
                this.getPlatform(),
                this.getResource());
    }

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	@Override
    public final String getSourceDataNameSpace() {
		return String.format("%s.%s.%s.%s",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				this.getData());
	}

	/**
     * Get the source data namespace.
     * @return The source data namespace
     */
    @Override
    public final String getSourceEntityNameSpace() {
        return String.format("%s.%s.%s.%s",
                Harmony.getProjectPath(),
                this.getPlatform(),
                this.getSource(),
                this.getModel());
    }

	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	@Override
    public final String getBundlePath(
			final String bundleOwnerName,
			final String bundleName) {
		return String.format("%s%s/",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase());
	}

	// Getter and Setter
	/**
	 * @return the project
	 */
	@Override
    public final String getProject() {
		return this.project;
	}

	/**
	 * @param project the project to set
	 */
	@Override
    public final void setProject(final String project) {
		this.project = project;
	}
	/**
	 * @return the resource
	 */
	@Override
    public final String getResource() {
		return this.resource;
	}

	/**
	 * @return the resource
	 */
	@Override
    public final String getAssets() {
		return this.assets;
	}

	/**
	 * @return the source
	 */
	@Override
    public String getSource() {
		return this.source;
	}

	/**
	 * @return the model
	 */
	@Override
    public final String getModel() {
		return this.model;
	}

	/**
	 * @return the view
	 */
	@Override
    public final String getView() {
		return this.view;
	}

	/**
	 * @return the large view
	 */
	@Override
    public final String getLargeView() {
		return this.largeView;
	}

	/**
	 * @return the controller
	 */
	@Override
    public String getController() {
		return this.controller;
	}

	/**
	 * @return the manifest
	 */
	@Override
    public final String getManifest() {
		return this.manifest;
	}

	/**
	 * @return the platform
	 */
	@Override
    public final String getPlatform() {
		return this.platform;
	}

	/**
	 * @param platform the platform to set
	 */
	@Override
    public final void setPlatform(final String platform) {
		this.platform = platform;
	}

	/**
	 * @param ressource the resource to set
	 */
	@Override
    public final void setResource(final String ressource) {
		this.resource = ressource;
	}

	/**
	 * @param assets the assets folder
	 */
	@Override
    public final void setAssets(final String assets) {
		this.assets = assets;
	}

	/**
	 * @param source the source to set
	 */
	@Override
    public final void setSource(final String source) {
		this.source = source;
	}

	/**
	 * @param model the model to set
	 */
	@Override
    public final void setModel(final String model) {
		this.model = model;
	}

	/**
	 * @param view the view to set
	 */
	@Override
    public final void setView(final String view) {
		this.view = view;
	}

	/**
	 * @param controller the controller to set
	 */
	@Override
    public final void setController(final String controller) {
		this.controller = controller;
	}

	/**
	 * @param manifest the manifest to set
	 */
	@Override
    public final void setManifest(final String manifest) {
		this.manifest = manifest;
	}

	/**
	 * @return the data
	 */
	@Override
    public final String getData() {
		return this.data;
	}

	/**
	 * @param data the data to set
	 */
	@Override
    public final void setData(final String data) {
		this.data = data;
	}

	/**
	 * @return the provider
	 */
	@Override
    public final String getProvider() {
		return this.provider;
	}

	/**
	 * @param provider the provider to set
	 */
	@Override
    public final void setProvider(final String provider) {
		this.provider = provider;
	}

	/**
	 * @return the criterias
	 */
	@Override
    public final String getCriterias() {
		return this.criterias;
	}

	/**
	 * @param criterias the criterias to set
	 */
	@Override
    public final void setCriterias(final String criterias) {
		this.criterias = criterias;
	}

	/**
	 * @return the base
	 */
	@Override
    public final String getBase() {
		return this.base;
	}

	/**
	 * @param base the base to set
	 */
	@Override
    public final void setBase(final String base) {
		this.base = base;
	}

	/**
	 * @return the common
	 */
	@Override
    public final String getCommon() {
		return this.common;
	}

	/**
	 * @param common the common to set
	 */
	@Override
    public final void setCommon(final String common) {
		this.common = common;
	}

	/**
	 * @return the service
	 */
	@Override
    public final String getService() {
		return this.service;
	}

	/**
	 * @param service the service to set
	 */
	@Override
    public final void setService(final String service) {
		this.service = service;
	}


	/**
	 * @return the service
	 */
	@Override
    public String getFixture() {
		return this.fixture;
	}

	/**
	 * @param fixture the fixture folder
	 */
	@Override
    public final void setFixture(final String fixture) {
		this.fixture = fixture;
	}


	/**
	 * @return the values
	 */
	@Override
    public final String getValues() {
		return this.values;
	}

	/**
	 * @return the values-xlarge
	 */
	@Override
    public final String getValuesXLarge() {
		return this.valuesXLarge;
	}

	/**
	 * @param values the values to set
	 */
	@Override
    public final void setValues(final String values) {
		this.values = values;
	}

	/**
	 * @return the libs
	 */
	@Override
    public final String getLibs() {
		return this.libs;
	}

	/**
	 * @param libs the libs to set
	 */
	@Override
    public final void setLibs(final String libs) {
		this.libs = libs;
	}
	/**
	 * @return the HomeActivity filename
	 */
	@Override
    public final String getHome() {
		return this.home;
	}

	/**
	 * @param home the HomeActivity filename to set
	 */
	@Override
    public final void setHome(final String home) {
		this.home = home;
	}

	/**
	 * @return the strings.xml
	 */
	@Override
    public final String getStrings() {
		return this.strings;
	}

	/**
	 * @return the configs.xml
	 */
	@Override
    public final String getConfigs() {
		return this.configs;
	}

	/**
	 * @param config The config.
	 */
	@Override
    public final void setConfigs(final String config) {
		this.configs = config;
	}

	/**
	 * @param strings the strings.xml filename to set
	 */
	@Override
    public final void setStrings(final String strings) {
		this.strings = strings;
	}

	/**
	 * @return the test
	 */
	@Override
    public final String getTest() {
		return this.test;
	}

	/**
	 * @param test the test to set
	 */
	@Override
    public final void setTest(final String test) {
		this.test = test;
	}

	/**
	 * @return the testLibs
	 */
	@Override
    public final String getTestLibs() {
		return this.testLibs;
	}

	/**
	 * @param testLibs the testLibs to set
	 */
	@Override
    public final void setTestLibs(final String testLibs) {
		this.testLibs = testLibs;
	}

	/**
	 * @return the harmony
	 */
	@Override
    public final String getHarmony() {
		return this.harmony;
	}

	/**
	 * @param harmony the harmony to set
	 */
	@Override
    public final void setHarmony(final String harmony) {
		this.harmony = harmony;
	}

	/**
	 * @return the widget
	 */
	@Override
    public final String getWidget() {
		return this.widget;
	}

	/**
	 * @param widget the widget to set
	 */
	@Override
    public final void setWidget(final String widget) {
		this.widget = widget;
	}

	/**
	 * @return the util
	 */
	@Override
    public final String getUtil() {
		return this.util;
	}

	/**
	 * @param util the util to set
	 */
	@Override
    public final void setUtil(final String util) {
		this.util = util;
	}

	/**
	 * @return the menu
	 */
	@Override
    public final String getMenu() {
		return this.menu;
	}

	/**
	 * @param menu the menu to set
	 */
	@Override
    public final void setMenu(final String menu) {
		this.menu = menu;
	}


	/**
	 * @return the utility path
	 */
	@Override
    public final String getUtilityPath() {
		return this.utilityPath;
	}

	/**
	 * @param utilityPath The utility path to set
	 */
	@Override
    public final void setUtilityPath(final String utilityPath) {
		this.utilityPath = utilityPath;
	}

	/**
	 * Check if the given library exists in the platform libraries folder.
	 * @param library The name of the file to looking for
	 * @return True if the file was founded
	 */
	public boolean libraryExists(String library) {
        boolean result = false;

        result = new File(this.getLibsPath() + library).exists();

        return result;
	}

	public void cloneTo(BaseAdapter adapter) {
        //TODO use reflection for that !
	    adapter.setAssets(this.getAssets());
	    adapter.setBase(this.getBase());
	    adapter.setCommon(this.getCommon());
	    adapter.setConfigs(this.getConfigs());
	    adapter.setController(this.getController());
	    adapter.setCriterias(this.getCriterias());
	    adapter.setData(this.getData());
	    adapter.setFixture(this.getFixture());
	    adapter.setHarmony(this.getHarmony());
	    adapter.setHome(this.getHome());
	    adapter.setLibs(this.getLibs());
	    adapter.setManifest(this.getManifest());
	    adapter.setMenu(this.getMenu());
	    adapter.setModel(this.getModel());
	    adapter.setPlatform(this.getPlatform());
	    adapter.setProject(this.getProject());
	    adapter.setProvider(this.getProvider());
	    adapter.setResource(this.getResource());
	    adapter.setService(this.getService());
	    adapter.setSource(this.getSource());
	    adapter.setStrings(this.getStrings());
	    adapter.setTest(this.getTest());
	    adapter.setTestLibs(this.getTestLibs());
	    adapter.setUtil(this.getUtil());
	    adapter.setUtilityPath(this.getUtilityPath());
	    adapter.setValues(this.getValues());
	    adapter.setView(this.getView());
	    adapter.setWidget(this.getWidget());
	}
}
