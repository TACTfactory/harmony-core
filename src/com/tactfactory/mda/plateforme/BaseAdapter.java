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
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;

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
	public abstract String getNameSpace(ClassMetadata meta, String type);
	
	/** Generate platform Namespace
	 * 
	 * @param meta Entity to extract the namespace 
	 * @return String Namespace
	 */
	public abstract String getNameSpaceEntity(ClassMetadata meta, String type);
	
	/** Generate platform view component for Show action
	 * 
	 * @param field The field based of generator
	 * @return String of the platform Component type
	 */
	public abstract String getViewComponentShow(FieldMetadata field);
	
	/** Generate platform view component for Edit action
	 * 
	 * @param field The field based of generator
	 * @return String of the platform Component type
	 */
	public abstract String getViewComponentEdit(FieldMetadata field);
	
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
		return String.format("%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getProject() );
	}
	
	public final String getLibsPath() {
		return String.format("%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getLibs() );
	}
	
	public final String getTestPath() {
		return String.format("%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getTest() );
	}
	
	public final String getSourcePath() {
		return String.format("%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getSource() );
	}
	
	public final String getWidgetPath() {
		return String.format("%s/%s/%s/%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getSource(), 
				Harmony.metas.projectNameSpace, this.getHarmony(), this.getWidget() );
	}
	
	public final String getUtilPath() {
		return String.format("%s/%s/%s/%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getSource(), 
				Harmony.metas.projectNameSpace, this.getHarmony(), this.getUtil() );
	}
	
	public final String getTemplateWidgetPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getWidget() );
	}
	
	public final String getTemplateUtilPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getUtil() );
	}	
	
	public final String getTemplateSourcePath() {
		return String.format("%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource() );
	}
	
	public final String getTemplateSourceControlerPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getController() );
	}
	
	public final String getTemplateSourceServicePath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getService() );
	}
	
	public final String getTemplateSourceProviderPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getProvider() );
	}
	
	public final String getTemplateSourceFixturePath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getFixture() );
	}

	
	public final String getTemplateSourceCommonPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getCommon() );
	}
	
	public final String getRessourcePath() {
		return String.format("%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getResource() );
	}
	
	public final String getAssetsPath() {
		return String.format("%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getAssets() );
	}
	
	public final String getTemplateRessourcePath() {
		return String.format("%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getResource() );
	}
	
	public final String getRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getResource(), this.getView() );
	}
	
	public final String getTemplateRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getResource(), this.getView() );
	}

	public final String getRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathProject, this.getPlatform(), this.getResource(), this.getValues() );
	}
	
	public final String getTemplateRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getResource(), this.getValues() );
	}

	public final String getManifestPathFile() {
		return String.format("%s/%s/%s", Harmony.pathProject, this.getPlatform(), this.getManifest() );
	}
	
	public final String getTemplateManifestPathFile() {
		return String.format("%s/%s/%s/%s", Harmony.pathTemplate, this.getPlatform(), this.getProject(), this.getManifest() );
	}

	public final String getHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.pathProject, this.getPlatform(), this.getSource(), Harmony.metas.projectNameSpace, this.getHome() );
	}
	
	public final String getTemplateHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s", Harmony.pathTemplate, this.getPlatform(), this.getSource(), this.getHome() );
	}

	public final String getStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.pathProject, this.getPlatform(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getTemplateStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.pathTemplate, this.getPlatform(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.pathProject, this.getPlatform(),
											this.getResource(), this.getValues(), this.getConfigs() );
	}

	public final String getTemplateConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s", Harmony.pathTemplate, this.getPlatform(),
											this.getResource(), this.getValues(), this.getConfigs() );
	}
	
	public final String getTemplateTestsPath() {
		return String.format("%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getTest() );
	}
	
	public final String getTemplateTestProjectPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getPlatform(), this.getTest(), this.getProject() );
	}
	
	public final String getStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s", Harmony.pathProject, this.getPlatform(), this.getTest(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getTemplateStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s", Harmony.pathTemplate, this.getPlatform(),this.getTest(),
											this.getResource(), this.getValues(), this.getStrings() );
	}
	
	public final String getSourceDataNameSpace() {
		return String.format("%s.%s.%s.%s", Harmony.pathProject, this.getPlatform(), this.getSource(), this.getData());
	}
	
	// Getter and Setter
	/**
	 * @return the project
	 */
	public final String getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public final void setProject(String project) {
		this.project = project;
	}
	/**
	 * @return the resource
	 */
	public final String getResource() {
		return resource;
	}
	
	/**
	 * @return the resource
	 */
	public final String getAssets() {
		return assets;
	}

	/**
	 * @return the source
	 */
	public final String getSource() {
		return source;
	}

	/**
	 * @return the model
	 */
	public final String getModel() {
		return model;
	}

	/**
	 * @return the view
	 */
	public final String getView() {
		return view;
	}

	/**
	 * @return the controller
	 */
	public final String getController() {
		return controller;
	}

	/**
	 * @return the manifest
	 */
	public final String getManifest() {
		return manifest;
	}

	/**
	 * @return the platform
	 */
	public final String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public final void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @param ressource the resource to set
	 */
	public final void setResource(String ressource) {
		this.resource = ressource;
	}
	
	/**
	 * @param ressource the resource to set
	 */
	public final void setAssets(String assets) {
		this.assets = assets;
	}

	/**
	 * @param source the source to set
	 */
	public final void setSource(String source) {
		this.source = source;
	}

	/**
	 * @param model the model to set
	 */
	public final void setModel(String model) {
		this.model = model;
	}

	/**
	 * @param view the view to set
	 */
	public final void setView(String view) {
		this.view = view;
	}

	/**
	 * @param controller the controller to set
	 */
	public final void setController(String controller) {
		this.controller = controller;
	}

	/**
	 * @param manifest the manifest to set
	 */
	public final void setManifest(String manifest) {
		this.manifest = manifest;
	}

	/**
	 * @return the data
	 */
	public final String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the provider
	 */
	public final String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public final void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the common
	 */
	public final String getCommon() {
		return common;
	}

	/**
	 * @param common the common to set
	 */
	public final void setCommon(String common) {
		this.common = common;
	}
	
	/**
	 * @return the service
	 */
	public final String getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public final void setService(String service) {
		this.service = service;
	}
	
	
	/**
	 * @return the service
	 */
	public final String getFixture() {
		return fixture;
	}

	/**
	 * @param service the service to set
	 */
	public final void setFixture(String fixture) {
		this.fixture = fixture;
	}

	
	/**
	 * @return the values
	 */
	public final String getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public final void setValues(String values) {
		this.values = values;
	}

	/**
	 * @return the libs
	 */
	public final String getLibs() {
		return libs;
	}

	/**
	 * @param libs the libs to set
	 */
	public final void setLibs(String libs) {
		this.libs = libs;
	}
	/**
	 * @return the HomeActivity filename
	 */
	public final String getHome() {
		return home;
	}

	/**
	 * @param home the HomeActivity filename to set
	 */
	public final void setHome(String home) {
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
	public final void setStrings(String strings) {
		this.strings = strings;
	}
	
	/**
	 * @return the test
	 */
	public String getTest() {
		return test;
	}

	/**
	 * @param test the test to set
	 */
	public void setTest(String test) {
		this.test = test;
	}

	/**
	 * @return the harmony
	 */
	public String getHarmony() {
		return harmony;
	}

	/**
	 * @param harmony the harmony to set
	 */
	public void setHarmony(String harmony) {
		this.harmony = harmony;
	}

	/**
	 * @return the widget
	 */
	public String getWidget() {
		return widget;
	}

	/**
	 * @param widget the widget to set
	 */
	public void setWidget(String widget) {
		this.widget = widget;
	}

	/**
	 * @return the util
	 */
	public String getUtil() {
		return util;
	}

	/**
	 * @param util the util to set
	 */
	public void setUtil(String util) {
		this.util = util;
	}

}
