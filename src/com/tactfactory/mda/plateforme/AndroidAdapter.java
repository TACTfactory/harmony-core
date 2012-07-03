package com.tactfactory.mda.plateforme;

import com.tactfactory.mda.orm.ClassMetadata;

public class AndroidAdapter extends BaseAdapter {
	
	public AndroidAdapter() {
		// Structure
		this.resource 	= "res";
		this.source 	= "src";
		this.template 	= "tpl/android/";
		
		// MVC
		this.model 		= "entity";
		this.view 		= "layout";
		this.controller = "view";
		
		// File
		this.manifest 	= "AndroidManifest.xml";
	}

	@Override
	public String getNameSpace(ClassMetadata meta) {
		return String.format("%s.%s.%s", 
				meta.nameSpace,
				this.getController(), 
				meta.nameClass.toLowerCase());
	}

}
