/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.plateforme;

import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.FieldMetadata;

/** Google Android Adapter of project structure */
public final class AndroidAdapter extends BaseAdapter {
	
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

	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getNameSpace(com.tactfactory.mda.orm.ClassMetadata)
	 */
	@Override
	public String getNameSpace(ClassMetadata meta) {
		return String.format("%s.%s.%s", 
				meta.nameSpace,
				this.getController(), 
				meta.nameClass.toLowerCase());
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

	

}
