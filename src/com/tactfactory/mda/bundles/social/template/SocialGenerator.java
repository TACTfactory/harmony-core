package com.tactfactory.mda.bundles.social.template;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;

/**
 * Generator for Social bundle.
 *
 */
public class SocialGenerator  extends BaseGenerator {

	/**
	 * Constructor.
	 * @param adapter The adapter to use.
	 * @throws Exception 
	 */
	public SocialGenerator(final BaseAdapter adapter) throws Exception {
		super(adapter);
	}
	
	/**
	 * Generate the social menu. 
	 */
	public final void generateMenu() {
		this.setDatamodel(this.getAppMetas().toMap(this.getAdapter()));
		this.makeMenu(true);
	}

	/**
	 * Generate the social menu.
	 * @param override True if overwrite existing menu file.
	 */
	protected final void makeMenu(final boolean override) {
		final String fullFilePath = 
				this.getAdapter().getSourcePath() 
				+ this.getAppMetas().getProjectNameSpace() 
				+ "/" + "menu" + "/" 
				+ "SocialMenuWrapper.java";
		final String fullTemplatePath = 
				this.getAdapter().getTemplateSourcePath() 
				+ "menu/SocialMenuWrapper.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
