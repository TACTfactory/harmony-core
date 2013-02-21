/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.symfony.adapter;

import com.tactfactory.mda.Harmony;

public class SymfonyAdapter {
	protected String webRoot = "www";	
	protected String webPlatform = "Symfony";	
	protected String config = "config";
	protected String bundle = "Bundle";
	protected String controller = "Controller";
	protected String entities = "Entity";
	protected String source = "src";
	
	public final String getWebTemplateEntityPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getWebRoot(), this.getBundle(), this.getEntities());
	}
	
	public final String getWebTemplateControllerPath() {
		return String.format("%s/%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getWebRoot(), this.getBundle(), this.getController());
	}
	
	public final String getWebTemplateConfigPath() {
		return String.format("%s/%s/%s/", Harmony.PATH_TEMPLATE, this.getWebRoot(), this.getConfig());
	}
	
	public final String getWebTemplatePath() {
		return String.format("%s/%s/", Harmony.PATH_TEMPLATE, this.getWebRoot());
	}
	
	public final String getWebControllerPath(final String projectName) {
		return String.format("%s%s/", this.getWebBundlePath(projectName), this.getController());
	}
	
	public final String getWebBundleYmlEntitiesPath(final String projectName) {
		return String.format("%s%s/", this.getWebBundleConfigPath(projectName), "doctrine");
	}
	
	public final String getWebConfigPath() {
		return String.format("%s%s/", this.getWebPath(), this.getConfig());
	}
	
	public final String getWebBundlePath(final String projectName) {
		return String.format("%s%s/%s/", this.getWebSourcePath(), projectName, "ApiBundle");
	}
	
	public final String getWebBundleConfigPath(final String projectName) {
		return String.format("%s%s/%s/", this.getWebBundlePath(projectName), "Resources", this.getConfig());
	}
	
	public String getWebEntitiesPath(final String projectName) {
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
		return this.webPlatform;
	}

	public void setWebPlatform(final String webPlatform) {
		this.webPlatform = webPlatform;
	}
	
	public String getWebRoot() {
		return this.webRoot;
	}

	public void setWebRoot(final String webRoot) {
		this.webRoot = webRoot;
	}

	public String getConfig() {
		return this.config;
	}

	public void setConfig(final String config) {
		this.config = config;
	}

	public String getBundle() {
		return this.bundle;
	}

	public void setBundle(final String bundle) {
		this.bundle = bundle;
	}

	public String getController() {
		return this.controller;
	}

	public void setController(final String controller) {
		this.controller = controller;
	}

	public String getEntities() {
		return this.entities;
	}

	public void setEntities(final String entities) {
		this.entities = entities;
	}	

	public String getSource() {
		return this.source;
	}

	public void setSource(final String source) {
		this.source = source;
	}


}
