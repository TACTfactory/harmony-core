package com.tactfactory.mda.rest.template;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;

public class WebGenerator extends BaseGenerator{
	private String symfonyPath = Harmony.pathProject+"/web";
	
	public WebGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		this.datamodel = this.appMetas.toMap(this.adapter);
	}
	
	public void generateEntities(){
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeEntity(cm.name);
			}
		}
	}
	
	public void generateWebRepositories(){
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(cm.options.containsKey("rest") && cm.fields.size()>0){
				this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.getName());
				makeRepository(cm.name);
			}
		}
	}
	
	protected void makeEntity(String entityName){
		String fullFilePath = symfonyPath + "/" + "Entity" + "/" + entityName+".orm.yml";
		String fullTemplatePath = this.adapter.getWebTemplateSourceEntityPath().substring(1)+"entity.yml";
		
		ConsoleUtils.displayDebug("Generating "+fullFilePath+" from "+fullTemplatePath);
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}
	
	protected void makeRepository(String entityName){
		String fullFilePath = symfonyPath + "/" + "Entity" + "/" + "Rest"+entityName+"Repository.php";
		String fullTemplatePath = this.adapter.getWebTemplateSourceEntityPath().substring(1)+"RestTemplateRepository.php";
		
		ConsoleUtils.displayDebug("Generating "+fullFilePath+" from "+fullTemplatePath);
		super.makeSource(fullTemplatePath, fullFilePath, true);
	}

}
