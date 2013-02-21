/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.plateforme;

import com.tactfactory.mda.meta.ClassMetadata;

/** Apple iOS Adapter of project structure. */
public final class IosAdapter extends BaseAdapter {

	/**
	 * Constructor.
	 */
	public IosAdapter()	 {
		super();
		this.project	= "project";
		this.platform	= "ios";
		this.resource 	= "res";
		this.source 	= "src";
	}


	/**
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#
	 * getNameSpace(com.tactfactory.mda.orm.ClassMetadata, java.lang.String)
	 */
	@Override
	public String getNameSpace(final ClassMetadata cm, final String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#
	 * getNameSpaceEntity(com.tactfactory.mda.orm.ClassMetadata, java.lang.String)
	 */
	@Override
	public String getNameSpaceEntity(final ClassMetadata cm, final String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public String getViewComponentShow(FieldMetadata field) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/*@Override
	public String getViewComponentEdit(FieldMetadata field) {
		// TODO Auto-generated method stub
		return null;
	}*/


	@Override
	public String getNativeType(final String type) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void resizeImage() {
		// TODO Auto-generated method stub
		
	}
	
}
