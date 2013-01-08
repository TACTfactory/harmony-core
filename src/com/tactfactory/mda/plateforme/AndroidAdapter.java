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
import com.tactfactory.mda.orm.annotation.Column;

/** Google Android Adapter of project structure */
public final class AndroidAdapter extends BaseAdapter {

	public AndroidAdapter() {
		// Structure
		this.project	= "project";
		this.platform	= "android";
		this.resource 	= "res";
		this.source 	= "src";
		this.libs		= "libs";
		this.test		= "test";
		
		// MVC
		//this.model 		= "entity";
		//this.view 		= "layout";
		//this.controller	= "view";
		//this.data			= "data";
		//this.provider		= "provider";
		
		// File
		this.manifest 	= "AndroidManifest.xml";
		this.strings	= "strings.xml";
		this.home		= "HomeActivity.java";
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

		if(field.relation !=null && (field.relation.type.equals("OneToMany") || field.relation.type.equals("ManyToMany"))) {

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
		
		if (field.type.equals("String") || field.type.equals("int")) {
			result = "EditText";
		} else
			
		if (field.type.equals("Date") ){
			result = "EditText"; //"DatePickerDialog";
		} else

		if (field.type.equals("Boolean")) {
			result = "CheckBox";
		}
			
		if(field.relation !=null && (field.relation.type.equals("ManyToOne") || field.relation.type.equals("OneToOne"))) {

			result = "Spinner";
		}
		
		return result;
	}

	@Override
	public String getNameSpace(ClassMetadata meta, String type) {
		return String.format("%s.%s", 
				meta.space,
				type);
	}

	@Override
	public String getNativeType(String type) {
		String ret = type;
		
		if(type.equals(Column.Type.STRING.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.TEXT.getValue())){
			ret = "String";	
		}else
			
		if(type.equals(Column.Type.INTEGER.getValue())){
			ret = "int";
		}else
			
		if(type.equals(Column.Type.INT.getValue())){
			ret = "int";
		}else
			
		if(type.equals(Column.Type.FLOAT.getValue())){
			ret = "float";
		}else
			
		if(type.equals(Column.Type.DATETIME.getValue())){
			ret = "DateTime";
		}else
			
		if(type.equals(Column.Type.DATE.getValue())){
			ret = "DateTime";
		}else
			
		if(type.equals(Column.Type.TIME.getValue())){
			ret = "DateTime";
		}else
			
		if(type.equals(Column.Type.LOGIN.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.PASSWORD.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.EMAIL.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.PHONE.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.CITY.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.ZIPCODE.getValue())){
			ret = "int";
		}else
			
		if(type.equals(Column.Type.COUNTRY.getValue())){
			ret = "String";
		}else
			
		if(type.equals(Column.Type.BC_EAN.getValue())){
			ret = "int";
		}
		return ret;
	}
	
	
}
