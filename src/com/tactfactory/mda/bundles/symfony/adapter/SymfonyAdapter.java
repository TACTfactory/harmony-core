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

/**
 * Symfony Adapter.
 */
public class SymfonyAdapter {
	/** Web server root. */
	private String webRoot = "www";
	/** Web platform. */
	private String webPlatform = "Symfony";
	/** Config folder. */
	private String config = "config";
	/** Bundle folder. */
	private String bundle = "Bundle";
	/** Controller folder. */
	private String controller = "Controller";
	/** Entity folder. */
	private String entities = "Entity";
	/** Source folder. */
	private String source = "src";
	
	/**
	 * Get entity template path.
	 * @return entity template path
	 */
	public final String getWebTemplateEntityPath() {
		return String.format("%s/%s/%s/%s/", 
				Harmony.PATH_TEMPLATE, 
				this.getWebRoot(), 
				this.getBundle(), 
				this.getEntities());
	}
	
	/**
	 * Get controller template path.
	 * @return controller template path
	 */
	public final String getWebTemplateControllerPath() {
		return String.format("%s/%s/%s/%s/", 
				Harmony.PATH_TEMPLATE,
				this.getWebRoot(), 
				this.getBundle(), 
				this.getController());
	}
	
	/**
	 * Get config template path.
	 * @return config template path
	 */
	public final String getWebTemplateConfigPath() {
		return String.format("%s/%s/%s/", 
				Harmony.PATH_TEMPLATE,
				this.getWebRoot(), 
				this.getConfig());
	}
	
	/**
	 * Get template path.
	 * @return template path
	 */
	public final String getWebTemplatePath() {
		return String.format("%s/%s/",
				Harmony.PATH_TEMPLATE,
				this.getWebRoot());
	}
	
	/**
	 * Get symfony controller path.
	 * @param projectName The project name
	 * @return symfony controller path
	 */
	public final String getWebControllerPath(final String projectName) {
		return String.format("%s%s/", 
				this.getWebBundlePath(projectName),
				this.getController());
	}
	
	/**
	 * Get Yaml entities path.
	 * @param projectName The project name
	 * @return Yaml entities path
	 */
	public final String getWebBundleYmlEntitiesPath(final String projectName) {
		return String.format("%s%s/", 
				this.getWebBundleConfigPath(projectName),
				"doctrine");
	}
	
	/**
	 * Get symfony config path.
	 * @return symfony config path
	 */
	public final String getWebConfigPath() {
		return String.format("%s%s/", 
				this.getWebPath(), 
				this.getConfig());
	}
	
	/**
	 * Get symfony project bundle path.
	 * @param projectName The project name
	 * @return symfony project bundle path
	 */
	public final String getWebBundlePath(final String projectName) {
		return String.format("%s%s/%s/",
				this.getWebSourcePath(),
				projectName, 
				"ApiBundle");
	}
	
	/**
	 * Get symfony bundle config path.
	 * @param projectName The project name
	 * @return symfony bundle config path
	 */
	public final String getWebBundleConfigPath(final String projectName) {
		return String.format("%s%s/%s/", 
				this.getWebBundlePath(projectName),
				"Resources", 
				this.getConfig());
	}
	
	/**
	 * Get symfony entities path.
	 * @param projectName The project name
	 * @return symfony entities path
	 */
	public final String getWebEntitiesPath(final String projectName) {
		return String.format("%s%s/",
				this.getWebBundlePath(projectName), 
				this.getEntities());
	}	
	
	/**
	 * Get symfony source path.
	 * @return symfony source path
	 */
	public final String getWebSourcePath() {
		return String.format("%s%s/", 
				this.getWebPath(),
				this.getSource());
	}
	
	/**
	 * Get symfony path.
	 * @return symfony path
	 */
	public final String getWebPath() {
		return String.format("%s%s/",
				this.getWebRootPath(), 
				this.getWebPlatform());
	}
	
	/**
	 * Get web root path.
	 * @return web root path
	 */
	public final String getWebRootPath() {
		return String.format("%s/", 
				this.getWebRoot());
	}

	/**
	 * @return the webRoot
	 */
	public final String getWebRoot() {
		return webRoot;
	}

	/**
	 * @return the webPlatform
	 */
	public final String getWebPlatform() {
		return webPlatform;
	}

	/**
	 * @return the config
	 */
	public final String getConfig() {
		return config;
	}

	/**
	 * @return the bundle
	 */
	public final String getBundle() {
		return bundle;
	}

	/**
	 * @return the controller
	 */
	public final String getController() {
		return controller;
	}

	/**
	 * @return the entities
	 */
	public final String getEntities() {
		return entities;
	}

	/**
	 * @return the source
	 */
	public final String getSource() {
		return source;
	}

}
