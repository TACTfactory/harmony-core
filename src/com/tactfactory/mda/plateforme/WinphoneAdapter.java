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

/** Microsoft Adapter of project structure */
public final class WinphoneAdapter extends BaseAdapter {

	public WinphoneAdapter()
	{
		this.platform	= "winphone";
		this.resource 	= "res";
		this.source 	= "src";
	}

	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getNameSpace(com.tactfactory.mda.orm.ClassMetadata, java.lang.String)
	 */
	@Override
	public String getNameSpace(ClassMetadata meta, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getNameSpace(com.tactfactory.mda.orm.ClassMetadata)
	 */
	@Override
	public String getNameSpaceEntity(ClassMetadata meta, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getViewComponentShow(com.tactfactory.mda.orm.FieldMetadata)
	 */
	@Override
	public String getViewComponentShow(FieldMetadata field) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tactfactory.mda.plateforme.BaseAdapter#getViewComponentEdit(com.tactfactory.mda.orm.FieldMetadata)
	 */
	@Override
	public String getViewComponentEdit(FieldMetadata field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNativeType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
