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

	@Override
	public String getNameSpace(ClassMetadata meta) {
		return String.format("%s.%s.%s", 
				meta.nameSpace,
				this.getController(), 
				meta.nameClass.toLowerCase());
	}

}
