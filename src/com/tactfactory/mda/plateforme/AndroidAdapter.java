/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.plateforme;

import java.io.File;

import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;
import com.tactfactory.mda.utils.FileUtils;

/** Google Android Adapter of project structure */
public final class AndroidAdapter extends BaseAdapter {
	
	public AndroidAdapter() {
		// Structure
		this.platform	= "android";
		this.resource 	= "res";
		this.source 	= "src";
		this.template 	= "tpl";
		
		// MVC
		//this.model 		= "entity";
		//this.view 		= "layout";
		//this.controller	= "view";
		//this.data			= "data";
		//this.provider		= "provider";
		
		// File
		this.manifest 	= "AndroidManifest.xml";
	}

	@Override
	public boolean makeProject()
	{
		boolean result = false;
		//Generate base folders & files
		File dirProj = FileUtils.makeFolderRecursive("../"+this.template+"/"+this.platform+"/"+"Project"+"/",
							"../app/"+this.platform+"/",
							true);
		if(dirProj.exists() && dirProj.listFiles().length!=0)
			result = true;
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getNameSpace(com.tactfactory.mda.orm.ClassMetadata)
	 */
	@Override
	public String getNameSpaceEntity(ClassMetadata meta, String type) {
		return String.format("%s.%s", 
				this.getNameSpace(meta, type),
				meta.name.toLowerCase());
	}

	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getViewComponentShow(com.tactfactory.mda.orm.FieldMetadata)
	 */
	@Override
	public String getViewComponentShow(FieldMetadata field) {
		String result = "TextView";
		
		if (field.type.equals("String") || field.type.equals("int") || field.type.equals("Date") ) {

		} else 
			
		if (field.type.equals("Boolean")) {
			result = "TextView";
		}
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getViewComponentEdit(com.tactfactory.mda.orm.FieldMetadata)
	 */
	@Override
	public String getViewComponentEdit(FieldMetadata field) {
		String result = "EditText";
		
		if (field.type.equals("String") || field.type.equals("int") || field.type.equals("Date") ) {

		} else 
			
		if (field.type.equals("Boolean")) {
			result = "CheckBox";
		}
		
		return result;
	}

	@Override
	public String getNameSpace(ClassMetadata meta, String type) {
		return String.format("%s.%s", 
				meta.space,
				type);
	}
}
