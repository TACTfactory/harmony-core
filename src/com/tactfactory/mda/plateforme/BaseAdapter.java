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

/** Base Adapter of project structure */
public abstract class BaseAdapter {	
	// Structure
	protected String project;
	protected String platform;
	protected String resource;
	protected String assets;
	protected String source;
	protected String libs;
	protected String test;
	protected String testLibs;
	protected String harmony;
	protected String widget;
	protected String util;

	// MVC
	protected String model		= "entity";
	protected String view 		= "layout";
	protected String values		= "values";
	protected String controller = "view";
	protected String data		= "data";
	protected String provider	= "provider";
	protected String common		= "common";
	protected String service	= "service";
	protected String fixture 	= "fixture";
	protected String criterias  = "criterias"; 
	
	// File
	protected String manifest;
	protected String home;
	protected String strings;
	protected String configs;
	
	// Abstract Methods
	/** Generate platform Namespace
	 * 
	 * @param meta Entity to extract the namespace 
	 * @return String Namespace
	 */
	public abstract String getNameSpace(ClassMetadata cm, String type);
	
	/** Generate platform Namespace
	 * 
	 * @param meta Entity to extract the namespace 
	 * @return String Namespace
	 */
	public abstract String getNameSpaceEntity(ClassMetadata cm, String type);
	

	/** Generate platform view component for Show action
	 * 
	 * @param field The field based of generator
	 * @return String of the platform Component type
	 */
	//public abstract String getViewComponentShow(FieldMetadata field);
	
	/** Generate platform view component for Edit action
	 * 
	 * @param field The field based of generator
	 * @return String of the platform Component type
	 */
	//public abstract String getViewComponentEdit(FieldMetadata field);
	
	/** Convert a Harmony type into a native type
	 * 
	 * @param type The type name
	 * @return String of the native type 
	 */
	public abstract String getNativeType(String type);
	
	/** Convert image structure to alternative resolution */
	public abstract void resizeImage();
	
	// Utils

	public final String getTemplateProjectPath() {
		return String.format("%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getProject() );
	}
	
	public final String getLibsPath() {
		return String.format("%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getLibs() );
	}
	
	public final String getTestPath() {
		return String.format("%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getTest() );
	}
	
	public final String getTestLibsPath() {
		return String.format("%s/%s/%s/%s", Harmony.PATH_PROJECT, this.getPlatform(), this.getTest(), this.getTestLibs() );
	}
	
	public final String getSourcePath() {
		return String.format("%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getSource() );
	}
	
	public final String getWidgetPath() {
		return String.format("%s/%s/%s/%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getSource(), 
				ApplicationMetadata.INSTANCE.projectNameSpace, this.getHarmony(), this.getWidget() );
	}
	
	public final String getUtilPath() {
		return String.format("%s/%s/%s/%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getSource(), 
				ApplicationMetadata.INSTANCE.projectNameSpace, this.getHarmony(), this.getUtil() );
	}
	
	public final String getTemplateWidgetPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getWidget() );
	}
	
	public final String getTemplateUtilPath() {
		return String.format("%s/%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getHarmony(), this.getUtil() );
	}	
	
	public final String getTemplateSourcePath() {
		return String.format("%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource() );
	}
	
	public final String getTemplateSourceControlerPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getController() );
	}
	
	public final String getTemplateSourceServicePath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getService() );
	}
	
	public final String getTemplateSourceEntityBasePath() {
		return String.format("%s/%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getModel(), "base" );
	}
	
	public final String getTemplateSourceProviderPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getProvider() );
	}
	
	public final String getTemplateSourceCriteriasPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getCriterias() );
	}
	
	public final String getTemplateSourceFixturePath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getFixture() );
	}

	
	public final String getTemplateSourceCommonPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getCommon() );
	}
	
	public final String getRessourcePath() {
		return String.format("%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getResource() );
	}
	
	public final String getAssetsPath() {
		return String.format("%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getAssets() );
	}
	
	public final String getTemplateRessourcePath() {
		return String.format("%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getResource() );
	}
	
	public final String getRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getResource(), this.getView() );
	}
	
	public final String getTemplateRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getResource(), this.getView() );
	}

	public final String getRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_PROJECT, this.getPlatform(), this.getResource(), this.getValues() );
	}
	
	public final String getTemplateRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getResource(), this.getValues() );
	}

	public final String getManifestPathFile() {
		return String.format("%s/%s/%s", Harmony.PATH_PROJECT, this.getPlatform(), this.getManifest() );
	}
	
	public final String getTemplateManifestPathFile() {
		return String.format("%s/%s/%s/%s", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getProject(), this.getManifest() );
	}

	public final String getHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.PATH_PROJECT, this.getPlatform(), this.getSource(), ApplicationMetadata.INSTANCE.projectNameSpace, this.getHome() );
	}
	
	public final String getTemplateHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getSource(), this.getHome() );
	}

	public final String getStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.PATH_PROJECT, this.getPlatform(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getTemplateStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.PATH_TEMPLATE, this.getPlatform(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.PATH_PROJECT, this.getPlatform(),
											this.getResource(), this.getValues(), this.getConfigs() );
	}

	public final String getTemplateConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.PATH_TEMPLATE, this.getPlatform(),
											this.getResource(), this.getValues(), this.getConfigs() );
	}
	
	public final String getTemplateTestsPath() {
		return String.format("%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getTest() );
	}
	
	public final String getTemplateTestProjectPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getPlatform(), this.getTest(), this.getProject() );
	}
	
	public final String getStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s", Harmony.PATH_PROJECT, this.getPlatform(), this.getTest(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getTemplateStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s", Harmony.PATH_TEMPLATE, this.getPlatform(),this.getTest(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getSourceDataNameSpace() {
		return String.format("%s.%s.%s.%s", Harmony.PATH_PROJECT, this.getPlatform(), this.getSource(), this.getData());
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
	private Object getConfigs() {
		return this.configs;
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
