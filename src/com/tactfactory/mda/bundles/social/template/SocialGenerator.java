package com.tactfactory.mda.bundles.social.template;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;

public class SocialGenerator  extends BaseGenerator {

	public SocialGenerator(BaseAdapter adapter) throws Exception{
		super(adapter);
		// TODO Auto-generated constructor stub
	}
	
	public void generateMenu(){
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.makeMenu(true);
	}

	
	protected void makeMenu(boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + "menu" + "/" + "SocialMenuWrapper.java";
		String fullTemplatePath = this.adapter.getTemplateSourcePath()+"menu/SocialMenuWrapper.java";
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
