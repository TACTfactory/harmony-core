package com.tactfactory.mda.fixture.template;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ApplicationGenerator;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.ProjectGenerator;
import com.tactfactory.mda.template.TagConstant;

public class FixtureGenerator extends BaseGenerator{

	public FixtureGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void init() {
		 try {
			this.makeSource("FixtureBase.java", "FixtureBase.java", false);
			this.makeSource("DataManager.java", "DataManager.java", false);
			new ApplicationGenerator(this.adapter).generateApplication();
			for(ClassMetadata cm : this.appMetas.entities.values()){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
				this.makeSource("TemplateDataLoader.java", cm.name+"DataLoader.java", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void makeSource(String templateName, String fileName, boolean override) {
		String fullFilePath = this.adapter.getSourcePath() + this.appMetas.projectNameSpace + "/" + this.adapter.getFixture() + "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceFixturePath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}

}
