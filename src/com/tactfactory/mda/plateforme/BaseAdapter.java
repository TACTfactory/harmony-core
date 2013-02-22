/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.plateforme;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;

/** Base Adapter of project structure. */
public abstract class BaseAdapter {	
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

	// MVC
	/** Models path. */
	private String model		= "entity";
	/** Views path. */
	private String view 		= "layout";
	/** Values path. */
	private String values		= "values";
	/** Controllers path. */
	private String controller = "view";
	/** Data path. */
	private String data		= "data";
	/** Providers path. */
	private String provider	= "provider";
	/** Common path. */
	private String common		= "common";
	/** Services path. */
	private String service	= "service";
	/** Fixtures path. */
	private String fixture 	= "fixture";
	/** Criterias path. */
	private String criterias  = "criterias"; 
	
	// File
	/** Manifest path. */
	private String manifest;
	/** Home path. */
	private String home;
	/** Strings path. */
	private String strings;
	/** Configs path. */
	private String configs;
	
	// Abstract Methods
	/** 
	 * Generate platform Namespace.
	 * 
	 * @param cm Entity to extract the namespace 
	 * @param type The namespace type.
	 * @return String Namespace
	 */
	public abstract String getNameSpace(ClassMetadata cm, String type);
	
	/** Generate platform Namespace.
	 * 
	 * @param cm Entity to extract the namespace 
	 * @param type The namespace type.
	 * @return String Namespace
	 */
	public abstract String getNameSpaceEntity(ClassMetadata cm, String type);
	
	/** Convert a Harmony type into a native type.
	 * 
	 * @param type The type name
	 * @return String of the native type 
	 */
	public abstract String getNativeType(String type);
	
	/** Convert image structure to alternative resolution. */
	public abstract void resizeImage();
	
	// Utils
	/**
	 * Get the template project path.
	 * @return The template project path
	 */
	public final String getTemplateProjectPath() {
		return String.format("%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(),
				this.getProject());
	}
	
	/**
	 * Get the libraries path.
	 * @return The libraries path
	 */
	public final String getLibsPath() {
		return String.format("%s/%s/%s/",
				Harmony.PATH_PROJECT,
				this.getPlatform(),
				this.getLibs());
	}
	
	/**
	 * Get the tests path.
	 * @return The tests path
	 */
	public final String getTestPath() {
		return String.format("%s/%s/%s/", 
				Harmony.PATH_PROJECT,
				this.getPlatform(), 
				this.getTest());
	}
	
	/**
	 * Get the test libraries path.
	 * @return The test libraries path
	 */
	public final String getTestLibsPath() {
		return String.format("%s/%s/%s/%s", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getTest(), 
				this.getTestLibs());
	}
	
	/**
	 * Get the sources path.
	 * @return The sources path
	 */
	public final String getSourcePath() {
		return String.format("%s/%s/%s/", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getSource());
	}
	
	/**
	 * Get the widgets path.
	 * @return The widgets path
	 */
	public final String getWidgetPath() {
		return String.format("%s/%s/%s/%s/%s/%s/", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getSource(), 
				ApplicationMetadata.INSTANCE.projectNameSpace, 
				this.getHarmony(), 
				this.getWidget());
	}
	
	/**
	 * Get the utility classes path.
	 * @return The utility classes path
	 */
	public final String getUtilPath() {
		return String.format("%s/%s/%s/%s/%s/%s/",
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getSource(), 
				ApplicationMetadata.INSTANCE.projectNameSpace, 
				this.getHarmony(),
				this.getUtil());
	}
	
	/**
	 * Get the widget's templates path.
	 * @return The widget's templates path
	 */
	public final String getTemplateWidgetPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getSource(), 
				this.getWidget());
	}
	
	/**
	 * Get the utility classes' templates path.
	 * @return The utility classes' templates path
	 */
	public final String getTemplateUtilPath() {
		return String.format("%s/%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getSource(), 
				this.getHarmony(), 
				this.getUtil());
	}	
	
	/**
	 * Get the sources's templates path.
	 * @return The source's templates path
	 */
	public final String getTemplateSourcePath() {
		return String.format("%s/%s/%s/", 
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getSource());
	}
	
	/**
	 * Get the controllers' templates path.
	 * @return The controllers' templates path
	 */
	public final String getTemplateSourceControlerPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(),
				this.getSource(),
				this.getController());
	}
	
	/**
	 * Get the services' templates path.
	 * @return The services' templates path
	 */
	public final String getTemplateSourceServicePath() {
		return String.format("%s/%s/%s/%s/", 
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getSource(), 
				this.getService());
	}
	
	/**
	 * Get the entities' templates path.
	 * @return The entities' templates path
	 */
	public final String getTemplateSourceEntityBasePath() {
		return String.format("%s/%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getSource(),
				this.getModel(), 
				"base");
	}
	
	/**
	 * Get the providers' templates path.
	 * @return The providers' templates path
	 */
	public final String getTemplateSourceProviderPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(),
				this.getSource(), 
				this.getProvider());
	}
	
	/**
	 * Get the criteria's templates path.
	 * @return The criteria's templates path
	 */
	public final String getTemplateSourceCriteriasPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getSource(), 
				this.getCriterias());
	}
	
	/**
	 * Get the fixtures' templates path.
	 * @return The fixtures' templates path
	 */
	public final String getTemplateSourceFixturePath() {
		return String.format("%s/%s/%s/%s/", 
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getSource(), 
				this.getFixture());
	}

	/**
	 * Get the common's templates path.
	 * @return The common's templates path
	 */
	public final String getTemplateSourceCommonPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getSource(), 
				this.getCommon());
	}
	
	/**
	 * Get the resource path.
	 * @return The resource path
	 */
	public final String getRessourcePath() {
		return String.format("%s/%s/%s/",
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getResource());
	}
	
	/**
	 * Get the assets path.
	 * @return The assets path
	 */
	public final String getAssetsPath() {
		return String.format("%s/%s/%s/", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getAssets());
	}
	
	/**
	 * Get the resource's templates path.
	 * @return The resource's templates path
	 */
	public final String getTemplateRessourcePath() {
		return String.format("%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getResource());
	}
	
	/**
	 * Get the resource's layouts path.
	 * @return The resource's layouts path
	 */
	public final String getRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_PROJECT,
				this.getPlatform(), 
				this.getResource(),
				this.getView());
	}
	
	/**
	 * Get the resources' layouts' templates path.
	 * @return The resources' layouts' templates path
	 */
	public final String getTemplateRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(),
				this.getResource(),
				this.getView());
	}

	/**
	 * Get the resources' values path.
	 * @return The resources values path
	 */
	public final String getRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getResource(), 
				this.getValues());
	}
	
	/**
	 * Get the resources' values' templates path.
	 * @return The resources values' templates path
	 */
	public final String getTemplateRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/", 
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getResource(),
				this.getValues());
	}

	/**
	 * Get the manifest's path.
	 * @return The manifest's path
	 */
	public final String getManifestPathFile() {
		return String.format("%s/%s/%s", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(),
				this.getManifest());
	}
	
	/**
	 * Get the manifest's template path.
	 * @return The manifest's template path
	 */
	public final String getTemplateManifestPathFile() {
		return String.format("%s/%s/%s/%s",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getProject(), 
				this.getManifest());
	}

	/**
	 * Get the home activity path.
	 * @return The home activity path
	 */
	public final String getHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s/%s",
				Harmony.PATH_PROJECT,
				this.getPlatform(),
				this.getSource(), 
				ApplicationMetadata.INSTANCE.projectNameSpace, 
				this.getHome());
	}
	
	/**
	 * Get the home activity template path.
	 * @return The home activity template path
	 */
	public final String getTemplateHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s", 
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getSource(), 
				this.getHome());
	}

	/**
	 * Get the strings path.
	 * @return The strings path
	 */
	public final String getStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(),
				this.getResource(), 
				this.getValues(),
				this.getStrings());
	}
	
	/**
	 * Get the strings template path.
	 * @return The strings template path
	 */
	public final String getTemplateStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s",
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(),
				this.getResource(),
				this.getValues(),
				this.getStrings());
	}
	
	/**
	 * Get the configs path.
	 * @return The configs path
	 */
	public final String getConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s", 
				Harmony.PATH_PROJECT,
				this.getPlatform(),
				this.getResource(), 
				this.getValues(), 
				this.getConfigs());
	}

	/**
	 * Get the configs template path.
	 * @return The configs template path
	 */
	public final String getTemplateConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s",
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(),							
				this.getResource(), 
				this.getValues(),
				this.getConfigs());
	}
	
	/**
	 * Get the tests templates path.
	 * @return The tests templates path
	 */
	public final String getTemplateTestsPath() {
		return String.format("%s/%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getPlatform(),
				this.getTest());
	}
	
	/**
	 * Get the test project templates path.
	 * @return The test project templates path
	 */
	public final String getTemplateTestProjectPath() {
		return String.format("%s/%s/%s/%s/", 
				Harmony.PATH_TEMPLATE,
				this.getPlatform(), 
				this.getTest(), 
				this.getProject());
	}
	
	/**
	 * Get the strings tests path.
	 * @return The strings tests path
	 */
	public final String getStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s",
				Harmony.PATH_PROJECT, 
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
	public final String getTemplateStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s",
				Harmony.PATH_TEMPLATE, 
				this.getPlatform(), 
				this.getTest(),	
				this.getResource(), 
				this.getValues(), 
				this.getStrings());
	}
	
	
	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getSourceDataNameSpace() {
		return String.format("%s.%s.%s.%s", 
				Harmony.PATH_PROJECT, 
				this.getPlatform(), 
				this.getSource(), 
				this.getData());
	}
	
	// Getter and Setter
	/**
	 * @return the project
	 */
	public final String getProject() {
		return this.project;
	}

	/**
	 * @param project the project to set
	 */
	public final void setProject(final String project) {
		this.project = project;
	}
	/**
	 * @return the resource
	 */
	public final String getResource() {
		return this.resource;
	}
	
	/**
	 * @return the resource
	 */
	public final String getAssets() {
		return this.assets;
	}

	/**
	 * @return the source
	 */
	public final String getSource() {
		return this.source;
	}

	/**
	 * @return the model
	 */
	public final String getModel() {
		return this.model;
	}

	/**
	 * @return the view
	 */
	public final String getView() {
		return this.view;
	}

	/**
	 * @return the controller
	 */
	public final String getController() {
		return this.controller;
	}

	/**
	 * @return the manifest
	 */
	public final String getManifest() {
		return this.manifest;
	}

	/**
	 * @return the platform
	 */
	public final String getPlatform() {
		return this.platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public final void setPlatform(final String platform) {
		this.platform = platform;
	}

	/**
	 * @param ressource the resource to set
	 */
	public final void setResource(final String ressource) {
		this.resource = ressource;
	}
	
	/**
	 * @param assets the assets folder
	 */
	public final void setAssets(final String assets) {
		this.assets = assets;
	}

	/**
	 * @param source the source to set
	 */
	public final void setSource(final String source) {
		this.source = source;
	}

	/**
	 * @param model the model to set
	 */
	public final void setModel(final String model) {
		this.model = model;
	}

	/**
	 * @param view the view to set
	 */
	public final void setView(final String view) {
		this.view = view;
	}

	/**
	 * @param controller the controller to set
	 */
	public final void setController(final String controller) {
		this.controller = controller;
	}

	/**
	 * @param manifest the manifest to set
	 */
	public final void setManifest(final String manifest) {
		this.manifest = manifest;
	}

	/**
	 * @return the data
	 */
	public final String getData() {
		return this.data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(final String data) {
		this.data = data;
	}

	/**
	 * @return the provider
	 */
	public final String getProvider() {
		return this.provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public final void setProvider(final String provider) {
		this.provider = provider;
	}
	
	/**
	 * @return the criterias
	 */
	public final String getCriterias() {
		return this.criterias;
	}

	/**
	 * @param criterias the criterias to set
	 */
	public final void setCriterias(final String criterias) {
		this.criterias = criterias;
	}

	/**
	 * @return the common
	 */
	public final String getCommon() {
		return this.common;
	}

	/**
	 * @param common the common to set
	 */
	public final void setCommon(final String common) {
		this.common = common;
	}
	
	/**
	 * @return the service
	 */
	public final String getService() {
		return this.service;
	}

	/**
	 * @param service the service to set
	 */
	public final void setService(final String service) {
		this.service = service;
	}
	
	
	/**
	 * @return the service
	 */
	public final String getFixture() {
		return this.fixture;
	}

	/**
	 * @param fixture the fixture folder
	 */
	public final void setFixture(final String fixture) {
		this.fixture = fixture;
	}

	
	/**
	 * @return the values
	 */
	public final String getValues() {
		return this.values;
	}

	/**
	 * @param values the values to set
	 */
	public final void setValues(final String values) {
		this.values = values;
	}

	/**
	 * @return the libs
	 */
	public final String getLibs() {
		return this.libs;
	}

	/**
	 * @param libs the libs to set
	 */
	public final void setLibs(final String libs) {
		this.libs = libs;
	}
	/**
	 * @return the HomeActivity filename
	 */
	public final String getHome() {
		return this.home;
	}

	/**
	 * @param home the HomeActivity filename to set
	 */
	public final void setHome(final String home) {
		this.home = home;
	}

	/**
	 * @return the strings.xml
	 */
	public final String getStrings() {
		return this.strings;
	}
	
	/**
	 * @return the configs.xml
	 */
	public String getConfigs() {
		return this.configs;
	}
	
	/**
	 * @param config The config.
	 */
	public void setConfigs(String config) {
		this.configs = config;
	}

	/**
	 * @param strings the strings.xml filename to set
	 */
	public final void setStrings(final String strings) {
		this.strings = strings;
	}
	
	/**
	 * @return the test
	 */
	public String getTest() {
		return this.test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(final String test) {
		this.test = test;
	}
	
	/**
	 * @return the testLibs
	 */
	public String getTestLibs() {
		return this.testLibs;
	}

	/**
	 * @param testLibs the testLibs to set
	 */
	public void setTestLibs(final String testLibs) {
		this.testLibs = testLibs;
	}

	/**
	 * @return the harmony
	 */
	public String getHarmony() {
		return this.harmony;
	}

	/**
	 * @param harmony the harmony to set
	 */
	public void setHarmony(final String harmony) {
		this.harmony = harmony;
	}

	/**
	 * @return the widget
	 */
	public String getWidget() {
		return this.widget;
	}

	/**
	 * @param widget the widget to set
	 */
	public void setWidget(final String widget) {
		this.widget = widget;
	}

	/**
	 * @return the util
	 */
	public String getUtil() {
		return this.util;
	}

	/**
	 * @param util the util to set
	 */
	public void setUtil(final String util) {
		this.util = util;
	}

}
