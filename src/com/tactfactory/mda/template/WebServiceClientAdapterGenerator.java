package com.tactfactory.mda.template;

import java.util.List;

import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;

public class WebServiceClientAdapterGenerator extends GeneratorBase {
	
	public WebServiceClientAdapterGenerator(List<ClassMetadata> metas,
			BaseAdapter adapter) throws Exception {
		super(metas, adapter);
		
		
	}

	/**
	 * Update XML Strings
	 * 
	 * @param cfg Template engine
	 */
	public void updateConfig() {
		/* Add to config file (configs.xml on android)
		 *
		 * REST_URL_PROD = "https://domain.tld:443/"
		 * REST_URL_DEV = "https://domain.tld:443/"
		 * REST_CHECK_SSL = true
		 * REST_SSL = "ca.cert"
		 * SYNC_POOL_TIMER = 3600
		 * SYNC_POOL_FIRST = 1
		 */
		
	}
}
