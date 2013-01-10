package com.tactfactory.mda.rest.parser;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.orm.ApplicationMetadata;
import com.tactfactory.mda.orm.BaseMetadata;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.rest.meta.ApplicationRestMetadata;

public class RestCompletor {
	public RestCompletor(){
		
	}
	
	public void generateApplicationRestMetadata(ApplicationMetadata am){
		ApplicationRestMetadata ret = new ApplicationRestMetadata();
		for(ClassMetadata cm : am.entities.values()){
			if(cm.options.containsKey("rest")){
				ret.entities.put(cm.getName(), cm);
			}
		}
		am.options.put(ret.getName(), ret);
		
	}
}
