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
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;

/** Base Adapter of project structure */
public abstract class BaseAdapter {	
	// Structure
	protected String resource;
	protected String source;
	protected String template;
	
	// MVC
	protected String model		= "entity";
	protected String view 		= "layout";
	protected String controller = "view";
	protected String data		= "data";
	protected String provider	= "provider";
	
	// File
	protected String manifest;
	
	// Abstract Methods
	/** Generate plateforme Namespace
	 * 
	 * @param meta Entity to extract the namespace 
	 * @return String Namespace
	 */
	public abstract String getNameSpace(ClassMetadata meta, String type);
	
	/** Generate plateforme Namespace
	 * 
	 * @param meta Entity to extract the namespace 
	 * @return String Namespace
	 */
	public abstract String getNameSpaceEntity(ClassMetadata meta, String type);
	
	/** Generate plateforme view component for Show action
	 * 
	 * @param field The field based of generator
	 * @return String of the plateforme Component type
	 */
	public abstract String getViewComponentShow(FieldMetadata field);
	
	/** Generate plateforme view component for Edit action
	 * 
	 * @param field The field based of generator
	 * @return String of the plateforme Component type
	 */
	public abstract String getViewComponentEdit(FieldMetadata field);
	
	// Utils
	public final String getSourcePath() {
		return String.format("%s/%s/", Harmony.pathProject, this.getSource() );
	}
	
	public final String getTemplateSourcePath() {
		return String.format("%s/%s/", this.getTemplate(), this.getSource() );
	}
	
	public final String getTemplateSourceControlerPath() {
		return String.format("%s/%s/", this.getTemplateSourcePath(), this.getController() );
	}
	
	public final String getTemplateSourceProviderPath() {
		return String.format("%s/%s/", this.getTemplateSourcePath(), this.getProvider() );
	}
	
	public final String getRessourcePath() {
		return String.format("%s/%s/", Harmony.pathProject, this.getResource() );
	}
	
	public final String getTemplateRessourcePath() {
		return String.format("%s/%s/", this.getTemplate(), this.getResource() );
	}
	
	public final String getRessourceLayoutPath() {
		return String.format("%s/%s/", this.getRessourcePath(), this.getView() );
	}
	
	public final String getTemplateRessourceLayoutPath() {
		return String.format("%s/%s/", this.getTemplateRessourcePath(), this.getView() );
	}
	
	public final String getManifestPathFile() {
		return String.format("%s/%s", Harmony.pathProject, this.getManifest() );
	}
	
	public final String getTemplateManifestPathFile() {
		return String.format("%s/project/%s", this.getTemplate(), this.getManifest() );
	}

	// Getter and Setter
	/**
	 * @return the resource
	 */
	public final String getResource() {
		return resource;
	}

	/**
	 * @return the source
	 */
	public final String getSource() {
		return source;
	}

	/**
	 * @return the template
	 */
	public final String getTemplate() {
		return template;
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
	 * @param ressource the resource to set
	 */
	public final void setResource(String ressource) {
		this.resource = ressource;
	}

	/**
	 * @param source the source to set
	 */
	public final void setSource(String source) {
		this.source = source;
	}

	/**
	 * @param template the template to set
	 */
	public final void setTemplate(String template) {
		this.template = template;
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
	 * @param controler the controller to set
	 */
	public final void setController(String controler) {
		this.controller = controler;
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
	 * @return the provider
	 */
	public final String getProvider() {
		return provider;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(String data) {
		this.data = data;
	}

	/**
	 * @param provider the provider to set
	 */
	public final void setProvider(String provider) {
		this.provider = provider;
	}
	
	
}
