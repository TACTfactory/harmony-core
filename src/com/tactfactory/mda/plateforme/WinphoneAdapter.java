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

/** 
 * Microsoft Adapter of project structure.
 * 
 */
public final class WinphoneAdapter extends BaseAdapter {

	/**
	 * Constructor
	 */
	public WinphoneAdapter() {
		super();
		this.platform	= "winphone";
		this.resource 	= "res";
		this.source 	= "src";
	}

	/** 
	 * @see com.tactfactory.mda.plateforme.BaseAdapter
	 * #getNameSpace(com.tactfactory.mda.orm.ClassMetadata,
	 * 				 java.lang.String)
	 */
	@Override
	public String getNameSpace(final ClassMetadata cm, final String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see com.tactfactory.mda.plateforme.BaseAdapter
	 * #getNameSpaceEntity(com.tactfactory.mda.orm.ClassMetadata,
	 * 					 java.lang.String)
	 */
	@Override
	public String getNameSpaceEntity(final ClassMetadata cm, 
			final String type) {
		// TODO Auto-generated method stub
		return null;
	}


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
