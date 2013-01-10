package com.tactfactory.mda.rest.parser;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.orm.ApplicationMetadata;
import com.tactfactory.mda.orm.Metadata;
import com.tactfactory.mda.orm.ClassMetadata;

public class RestCompletor {
	public RestCompletor(){
		
	}
	
	public void generateApplicationRestMetadata(ApplicationMetadata am){
		Map<String, Metadata> ret = new HashMap<String, Metadata>();
		for(ClassMetadata cm : am.entities.values()){
			if(cm.options.containsKey("rest")){
				ret.put(cm.getName(), cm.options.get("rest"));
			}
		}
		
	}
}
