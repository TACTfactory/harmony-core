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

import com.tactfactory.harmony.Context;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.plateforme.manipulator.SourceFileManipulator;

import freemarker.template.Configuration;

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
	/** Template Utility files path. */
	private String utilityPath;
	/**	Bundle template files path. */
	private String bundleTemplates = "bundle";


	// Bundles
	/**	Bundle template files path. */
	private String annotationsBundleTemplates = "annotation";
	/**	Bundle template files path. */
	private String templateBundleTemplates = "template";
	/**	Bundle template files path. */
	private String parserBundleTemplates = "parser";
	/**	Bundle template files path. */
	private String metaBundleTemplates = "meta";
	/**	Bundle template files path. */
	private String commandBundleTemplates = "command";

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
	 * @param field The field name
	 * @return String of the native type
	 */
	public abstract String getNativeType(FieldMetadata field);

	/** Convert image structure to alternative resolution. */
	public abstract void resizeImage();
	
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
	public abstract boolean filesEqual(String oldContent,
			String newContent,
			String fileName,
			boolean ignoreHeader);	
	/**
	 * Install an android project library from git.
	 * @param url The url of the git repository.
	 * @param pathLib The folder path where the repo should be downloaded
	 * @param versionTag The tag/commit/branch you want to checkout
	 * @param libName The library name (ie. demact-abs)
	 * @param filesToDelete The list of files/folders to delete (samples, etc.)
	 * @param libraryProjectPath The library project path inside the downloaded
	 * 				folder
	 * @param target The SDK build target
	 * @param referencePath The library path to reference in your project
	 * @param isSupportV4Dependant true if the library is supportv4 dependent
	 */
	public abstract void installGitLibrary(String url,
			String pathLib,
			String versionTag,
			String libName,
			List<File> filesToDelete,
			String libraryProjectPath,
			String target,
			String referencePath,
			boolean isSupportV4Dependant);

	// Utils
	/**
	 * Get the template project path.
	 * @return The template project path
	 */
	public final String getTemplateProjectPath() {
		return String.format("%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getProject());
	}

	/**
	 * Get the libraries path.
	 * @return The libraries path
	 */
	public final String getLibsPath() {
		return String.format("%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getLibs());
	}

	/**
	 * Get the tests path.
	 * @return The tests path
	 */
	public final String getTestPath() {
		return String.format("%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getTest());
	}

	/**
	 * Get the test libraries path.
	 * @return The test libraries path
	 */
	public final String getTestLibsPath() {
		return String.format("%s/%s/%s/%s",
				Harmony.getProjectPath(),
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
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource());
	}
	
	/**
	 * Get the sources path.
	 * @return The sources path
	 */
	public final String getSourceControllerPath() {
		return String.format("%s/%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				ApplicationMetadata.INSTANCE.getProjectNameSpace(),
				this.getController());
	}

	/**
	 * Get the widgets path.
	 * @return The widgets path
	 */
	public final String getWidgetPath() {
		return String.format("%s/%s/%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				ApplicationMetadata.INSTANCE.getProjectNameSpace(),
				this.getHarmony(),
				this.getWidget());
	}

	/**
	 * Get the utility classes path.
	 * @return The utility classes path
	 */
	public final String getUtilPath() {
		return String.format("%s/%s/%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				ApplicationMetadata.INSTANCE.getProjectNameSpace(),
				this.getHarmony(),
				this.getUtil());
	}

	/**
	 * Get the project's menu path.
	 * @return The menu path
	 */
	public final String getMenuPath() {
		return String.format("%s/%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				ApplicationMetadata.INSTANCE.getProjectNameSpace(),
				this.getMenu());
	}
	
	/**
	 * Get the project's menu base path.
	 * @return The menu base path
	 */
	public final String getMenuBasePath() {
		return String.format("%s/%s/",
				this.getMenuPath(),
				this.getBase());
	}
	
	/**
	 * Get the project's menu path.
	 * @return The menu path
	 */
	public final String getTemplateMenuPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getSource(),
				this.getMenu());
	}
	
	/**
	 * Get the project's menu base path.
	 * @return The menu base path
	 */
	public final String getTemplateMenuBasePath() {
		return String.format("%s/%s/",
				this.getTemplateMenuPath(),
				this.getBase());
	}

	/**
	 * Get the widget's templates path.
	 * @return The widget's templates path
	 */
	public final String getTemplateWidgetPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getSource(),
				this.getHarmony(),
				this.getUtil());
	}

	/**
	 * Get the utility classes' templates path.
	 * @return The utility classes' templates path
	 */
	public final String getTemplateUtilityPath() {
		return String.format("/%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getUtilityPath());
	}

	/**
	 * Get the sources's templates path.
	 * @return The source's templates path
	 */
	public final String getTemplateSourcePath() {
		return String.format("%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getSource());
	}

	/**
	 * Get the controllers' templates path.
	 * @return The controllers' templates path
	 */
	public final String getTemplateSourceControlerPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
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
				Harmony.getTemplatesPath(),
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
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource());
	}

	/**
	 * Get the assets path.
	 * @return The assets path
	 */
	public final String getAssetsPath() {
		return String.format("%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getAssets());
	}

	/**
	 * Get the resource's templates path.
	 * @return The resource's templates path
	 */
	public final String getTemplateRessourcePath() {
		return String.format("%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getResource());
	}

	/**
	 * Get the resource's layouts path.
	 * @return The resource's layouts path
	 */
	public final String getRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource(),
				this.getView());
	}
	
	/**
	 * Get the resource's layouts path.
	 * @return The resource's layouts path
	 */
	public final String getRessourceLargeLayoutPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource(),
				this.getLargeView());
	}

	/**
	 * Get the resources' layouts' templates path.
	 * @return The resources' layouts' templates path
	 */
	public final String getTemplateRessourceLayoutPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getResource(),
				this.getView());
	}

	
	/**
	 * Get the resources' layouts' templates path.
	 * @return The resources' layouts' templates path
	 */
	public final String getTemplateRessourceLargeLayoutPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getResource(),
				this.getLargeView());
	}

	/**
	 * Get the resources' values path.
	 * @return The resources values path
	 */
	public final String getRessourceValuesPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValues());
	}
	
	/**
	 * Get the resources' values path.
	 * @return The resources values path
	 */
	public final String getRessourceXLargeValuesPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValuesXLarge());
	}

	/**
	 * Get the resources' values' templates path.
	 * @return The resources values' templates path
	 */
	public final String getTemplateRessourceValuesPath() {
		return String.format("%s%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getResource(),
				this.getValues());
	}

	/**
	 * Get the resources' values-xlarge' templates path.
	 * @return The resources values-xlarge' templates path
	 */
	public final String getTemplateRessourceXLargeValuesPath() {
		return String.format("%s%s/%s/%s/",
				Harmony.getTemplatesPath(),
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
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getManifest());
	}

	/**
	 * Get the manifest's template path.
	 * @return The manifest's template path
	 */
	public final String getTemplateManifestPathFile() {
		return String.format("%s/%s/%s/%s",
				Harmony.getTemplatesPath(),
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
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				ApplicationMetadata.INSTANCE.getProjectNameSpace(),
				this.getHome());
	}

	/**
	 * Get the home activity template path.
	 * @return The home activity template path
	 */
	public final String getTemplateHomeActivityPathFile() {
		return String.format("%s/%s/%s/%s",
				Harmony.getTemplatesPath(),
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
	public final String getTemplateStringsPathFile() {
		return String.format("%s/%s/%s/%s/%s",
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
	public final String getConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s",
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
	public final String getTemplateConfigsPathFile() {
		return String.format("%s/%s/%s/%s/%s",
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
	public final String getTemplateTestsPath() {
		return String.format("%s/%s/%s/",
				Harmony.getTemplatesPath(),
				this.getPlatform(),
				this.getTest());
	}

	/**
	 * Get the test project templates path.
	 * @return The test project templates path
	 */
	public final String getTemplateTestProjectPath() {
		return String.format("%s/%s/%s/%s/",
				Harmony.getTemplatesPath(),
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
	public final String getTemplateStringsTestPathFile() {
		return String.format("%s/%s/%s/%s/%s/%s",
				Harmony.getTemplatesPath(),
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
				Harmony.getProjectPath(),
				this.getPlatform(),
				this.getSource(),
				this.getData());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getAnnotationBundleTemplatePath() {
		return String.format("%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getAnnotationsBundleTemplates());
	}


	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getAnnotationBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s/%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", Context.DELIMITER),
				this.getAnnotationsBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getCommandBundleTemplatePath() {
		return String.format("%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getCommandBundleTemplates());
	}


	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getCommandBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s/%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", Context.DELIMITER),
				this.getCommandBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getMetaBundleTemplatePath() {
		return String.format("%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getMetaBundleTemplates());
	}


	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getMetaBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s/%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", Context.DELIMITER),
				this.getMetaBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getTemplateBundleTemplatePath() {
		return String.format("%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getTemplateBundleTemplates());
	}


	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getTemplateBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s/%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", Context.DELIMITER),
				this.getTemplateBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getParserBundleTemplatePath() {
		return String.format("%s/%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates(),
				this.getParserBundleTemplates());
	}


	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleNamespace The bundle namespace
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getParserBundlePath(
			final String bundleOwnerName,
			final String bundleNamespace,
			final String bundleName) {
		return String.format("%s/%s/src/%s/%s",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase(),
				bundleNamespace.replaceAll("\\.", Context.DELIMITER),
				this.getParserBundleTemplates());
	}

	/**
	 * Get the source data namespace.
	 * @return The source data namespace
	 */
	public final String getBundleTemplatePath() {
		return String.format("%s/%s",
				Harmony.getTemplatesPath(),
				this.getBundleTemplates());
	}


	/**
	 * Get the source data namespace.
	 * @param bundleOwnerName The bundle owner name
	 * @param bundleName The bundle name
	 * @return The source data namespace
	 */
	public final String getBundlePath(
			final String bundleOwnerName,
			final String bundleName) {
		return String.format("%s/%s/",
				Harmony.getBundlePath(),
				bundleOwnerName.toLowerCase() + "-" + bundleName.toLowerCase());
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
	 * @return the large view
	 */
	public final String getLargeView() {
		return this.largeView;
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
	 * @return the base
	 */
	public final String getBase() {
		return this.base;
	}

	/**
	 * @param base the base to set
	 */
	public final void setBase(final String base) {
		this.base = base;
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
	 * @return the values-xlarge
	 */
	public final String getValuesXLarge() {
		return this.valuesXLarge;
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
	public final String getConfigs() {
		return this.configs;
	}

	/**
	 * @param config The config.
	 */
	public final void setConfigs(final String config) {
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
	public final String getTest() {
		return this.test;
	}

	/**
	 * @param test the test to set
	 */
	public final void setTest(final String test) {
		this.test = test;
	}

	/**
	 * @return the testLibs
	 */
	public final String getTestLibs() {
		return this.testLibs;
	}

	/**
	 * @param testLibs the testLibs to set
	 */
	public final void setTestLibs(final String testLibs) {
		this.testLibs = testLibs;
	}

	/**
	 * @return the harmony
	 */
	public final String getHarmony() {
		return this.harmony;
	}

	/**
	 * @param harmony the harmony to set
	 */
	public final void setHarmony(final String harmony) {
		this.harmony = harmony;
	}

	/**
	 * @return the widget
	 */
	public final String getWidget() {
		return this.widget;
	}

	/**
	 * @param widget the widget to set
	 */
	public final void setWidget(final String widget) {
		this.widget = widget;
	}

	/**
	 * @return the util
	 */
	public final String getUtil() {
		return this.util;
	}

	/**
	 * @param util the util to set
	 */
	public final void setUtil(final String util) {
		this.util = util;
	}

	/**
	 * @return the menu
	 */
	public final String getMenu() {
		return this.menu;
	}

	/**
	 * @param menu the menu to set
	 */
	public final void setMenu(final String menu) {
		this.menu = menu;
	}


	/**
	 * @return the utility path
	 */
	public final String getUtilityPath() {
		return this.utilityPath;
	}

	/**
	 * @param utilityPath The utility path to set
	 */
	public final void setUtilityPath(final String utilityPath) {
		this.utilityPath = utilityPath;
	}

	/**
	 * @return the bundleTemplates
	 */
	public final String getBundleTemplates() {
		return bundleTemplates;
	}

	/**
	 * @param bundleTemplates the bundleTemplates to set
	 */
	public final void setBundleTemplates(
			final String bundleTemplates) {
		this.bundleTemplates = bundleTemplates;
	}

	/**
	 * @return the annotationsBundleTemplates
	 */
	public final String getAnnotationsBundleTemplates() {
		return annotationsBundleTemplates;
	}

	/**
	 * @param annotationsBundleTemplates the annotationsBundleTemplates to set
	 */
	public final void setAnnotationsBundleTemplates(
			final String annotationsBundleTemplates) {
		this.annotationsBundleTemplates = annotationsBundleTemplates;
	}

	/**
	 * @return the templateBundleTemplates
	 */
	public final String getTemplateBundleTemplates() {
		return templateBundleTemplates;
	}

	/**
	 * @param templateBundleTemplates the templateBundleTemplates to set
	 */
	public final void setTemplateBundleTemplates(
			final String templateBundleTemplates) {
		this.templateBundleTemplates = templateBundleTemplates;
	}

	/**
	 * @return the parserBundleTemplates
	 */
	public final String getParserBundleTemplates() {
		return parserBundleTemplates;
	}

	/**
	 * @param parserBundleTemplates the parserBundleTemplates to set
	 */
	public final void setParserBundleTemplates(
			final String parserBundleTemplates) {
		this.parserBundleTemplates = parserBundleTemplates;
	}

	/**
	 * @return the metaBundleTemplates
	 */
	public final String getMetaBundleTemplates() {
		return metaBundleTemplates;
	}

	/**
	 * @param metaBundleTemplates the metaBundleTemplates to set
	 */
	public final void setMetaBundleTemplates(
			final String metaBundleTemplates) {
		this.metaBundleTemplates = metaBundleTemplates;
	}

	/**
	 * @return the commandBundleTemplates
	 */
	public final String getCommandBundleTemplates() {
		return commandBundleTemplates;
	}

	/**
	 * @param commandBundleTemplates the commandBundleTemplates to set
	 */
	public final void setCommandBundleTemplates(
			final String commandBundleTemplates) {
		this.commandBundleTemplates = commandBundleTemplates;
	}

	/**
	 * Gets the source file manipulator associated to this adapter.
	 *  
	 * @param file The file to open.
	 * 
	 * @return The associated file manipulator
	 */
	public abstract SourceFileManipulator getFileManipulator(
			final File file,
			final Configuration config);
}
