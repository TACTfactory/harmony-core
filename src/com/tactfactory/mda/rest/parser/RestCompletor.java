/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.rest.parser;

import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.rest.meta.RestMetadata;

public class RestCompletor {
	public RestCompletor(){
		
	}
	
	public void generateApplicationRestMetadata(ApplicationMetadata am){
		//ApplicationRestMetadata ret = new ApplicationRestMetadata();
		for(ClassMetadata cm : am.entities.values()){
			if(cm.options.containsKey("rest")){
				RestMetadata rm = (RestMetadata)cm.options.get("rest");
				if(rm.uri==null || rm.uri.equals("")){
					rm.uri = cm.name;
				}
				//ret.entities.put(cm.getName(), cm);
			}
		}
		//am.options.put(ret.getName(), ret);
		
	}
}
