package com.tactfactory.mda.bundles.symfony.adapter;

import com.tactfactory.mda.Harmony;

public class SymfonyAdapter{
	protected String webRoot = "www";	
	protected String webPlatform = "Symfony";	
	protected String config = "config";
	protected String bundle = "Bundle";
	protected String controller = "Controller";
	protected String entities = "Entity";
	protected String source = "src";
	
	public final String getWebTemplateEntityPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getWebRoot(), this.getBundle(), this.getEntities());
	}
	
	public final String getWebTemplateControllerPath() {
		return String.format("%s/%s/%s/%s/", Harmony.pathTemplate, this.getWebRoot(), this.getBundle(), this.getController());
	}
	
	public final String getWebTemplateConfigPath() {
		return String.format("%s/%s/%s/", Harmony.pathTemplate, this.getWebRoot(), this.getConfig());
	}
	
	public final String getWebTemplatePath() {
		return String.format("%s/%s/", Harmony.pathTemplate, this.getWebRoot());
	}
	
	public final String getWebControllerPath(String projectName) {
		return String.format("%s%s/", this.getWebBundlePath(projectName), this.getController());
	}
	
	public final String getWebBundleYmlEntitiesPath(String projectName) {
		return String.format("%s%s/", this.getWebBundleConfigPath(projectName), "doctrine");
	}
	
	public final String getWebConfigPath() {
		return String.format("%s%s/", this.getWebPath(), this.getConfig());
	}
	
	public final String getWebBundlePath(String projectName) {
		return String.format("%s%s/%s/", this.getWebSourcePath(), projectName, "ApiBundle");
	}
	
	public final String getWebBundleConfigPath(String projectName) {
		return String.format("%s%s/%s/", this.getWebBundlePath(projectName), "Resources", this.getConfig());
	}
	
	public String getWebEntitiesPath(String projectName) {
		return String.format("%s%s/", this.getWebBundlePath(projectName), this.getEntities());
	}	
	
	public final String getWebSourcePath() {
		return String.format("%s%s/", this.getWebPath(), this.getSource());
	}
	
	public final String getWebPath() {
		return String.format("%s%s/", this.getWebRootPath(), this.getWebPlatform());
	}
	
	public final String getWebRootPath() {
		return String.format("%s/", this.getWebRoot());
	}

	public String getWebPlatform() {
		return webPlatform;
	}

	public void setWebPlatform(String webPlatform) {
		this.webPlatform = webPlatform;
	}
	
	public String getWebRoot() {
		return webRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getEntities() {
		return entities;
	}

	public void setEntities(String entities) {
		this.entities = entities;
	}	

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}


}
