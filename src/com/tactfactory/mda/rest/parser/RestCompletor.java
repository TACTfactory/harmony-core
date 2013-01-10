package com.tactfactory.mda.rest.parser;

import com.tactfactory.mda.orm.ApplicationMetadata;
import com.tactfactory.mda.orm.ClassMetadata;
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
