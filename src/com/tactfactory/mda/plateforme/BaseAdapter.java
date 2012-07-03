package com.tactfactory.mda.plateforme;

import com.tactfactory.mda.command.Console;
import com.tactfactory.mda.orm.ClassMetadata;

public abstract class BaseAdapter {
	protected String separator = "/";
	
	// Structure
	protected String ressource 	= "res";
	protected String source 	= "src";
	protected String template 	= "tpl/";
	
	// MVC
	protected String model 		= "entity";
	protected String view 		= "";
	protected String controler 	= "";
	
	// File
	protected String manifest 	= "";
	
	public abstract String getNameSpace(ClassMetadata meta);
	
	public final String getSourcePath() {
		return String.format("%s/%s/", Console.pathProject, this.getSource() );
	}
	
	public final String getTemplateSourcePath() {
		return String.format("%s/%s/", this.getTemplate(), this.getSource() );
	}
	
	public final String getTemplateSourceControlerPath() {
		return String.format("%s/%s/", this.getTemplateSourcePath(), this.getControler() );
	}
	
	public final String getRessourcePath() {
		return String.format("%s/%s/", Console.pathProject, this.getRessource() );
	}
	
	public final String getTemplateRessourcePath() {
		return String.format("%s/%s/", this.getTemplate(), this.getRessource() );
	}
	
	public final String getRessourceLayoutPath() {
		return String.format("%s/%s/", this.getRessourcePath(), this.getView() );
	}
	
	public final String getTemplateRessourceLayoutPath() {
		return String.format("%s/%s/", this.getTemplateRessourcePath(), this.getView() );
	}
	
	public final String getManifestPathFile() {
		return String.format("%s/%s", Console.pathProject, this.getManifest() );
	}

	/**
	 * @return the ressource
	 */
	public final String getRessource() {
		return ressource;
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
	 * @return the controler
	 */
	public final String getControler() {
		return controler;
	}

	/**
	 * @return the manifest
	 */
	public final String getManifest() {
		return manifest;
	}

	/**
	 * @param ressource the ressource to set
	 */
	public final void setRessource(String ressource) {
		this.ressource = ressource;
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
	 * @param controler the controler to set
	 */
	public final void setControler(String controler) {
		this.controler = controler;
	}

	/**
	 * @param manifest the manifest to set
	 */
	public final void setManifest(String manifest) {
		this.manifest = manifest;
	}
	
	
}
