/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.plateforme.BaseAdapter;

public class MethodMetadata extends BaseMetadata {	
	/** Return type */
	public String type;
	
	/** Arguments types*/
	public ArrayList<String> argumentsTypes = new ArrayList<String>();
	
	/** final ?*/
	public boolean isFinal = false;

	@Override
	public HashMap<String, Object> toMap(BaseAdapter adapter) {
		// TODO Auto-generated method stub
		return null;
	}
}
