package com.tactfactory.mda.template;

import com.google.common.base.CaseFormat;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.utils.PackageUtils;

public class ApplicationGenerator extends BaseGenerator {
	protected String localNameSpace;
	protected String applicationName;

	public ApplicationGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		
		this.localNameSpace = this.appMetas.projectNameSpace.replace('/', '.');
		this.applicationName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, this.appMetas.name);
		
		this.datamodel = this.appMetas.toMap(this.adapter);
		this.datamodel.put(TagConstant.LOCAL_NAMESPACE, this.localNameSpace);
		
	}
	
	public void generateApplication() {
		this.makeSource(
				"TemplateApplication.java", 
				this.applicationName + "Application.java",
				false);
		
		this.makeSource(
				"TemplateApplicationBase.java", 
				this.applicationName + "ApplicationBase.java",
				true);
	}
	
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = String.format("%s%s/%s",
				this.adapter.getSourcePath(),
				PackageUtils.extractPath(this.localNameSpace).toLowerCase(),
				fileName);

		String fullTemplatePath = String.format("%s%s",
				this.adapter.getTemplateSourcePath() ,
				templateName);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
