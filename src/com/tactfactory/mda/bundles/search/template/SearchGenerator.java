 package com.tactfactory.mda.bundles.search.template;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.BaseGenerator;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.PackageUtils;

public class SearchGenerator  extends BaseGenerator {

	public SearchGenerator(BaseAdapter adapter) throws Exception {
		super(adapter);
		// TODO Auto-generated constructor stub
	}
	
	public void generateAll() {
		this.datamodel = this.appMetas.toMap(this.adapter);
		for(ClassMetadata cm : this.appMetas.entities.values()){
			if(isClassSearchable(cm)){
				this.generateActivity(cm);
			}
		}
	}
	
	private void generateActivity(ClassMetadata cm){
		this.makeSource(cm, "TemplateSearchActivity.java",cm.name+"SearchActivity.java",false);
		this.makeSource(cm, "TemplateSearchFragment.java",cm.name+"SearchFragment.java",false);
		
		this.makeLayout(cm, "activity_template_search.xml","activity_"+cm.name.toLowerCase()+"_search.xml",false);
		this.makeLayout(cm, "fragment_template_search.xml","fragment_"+cm.name.toLowerCase()+"_search.xml",false);
	}
	
	private boolean isClassSearchable(ClassMetadata cm){
		for(FieldMetadata fm : cm.fields.values()){
			if(fm.options.containsKey("search")){
				return true;
			}
		}
		return false;
	}

	protected void makeSource(ClassMetadata cm, String templateName, String fileName, boolean override) {
		this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
		String fullFilePath = this.adapter.getSourcePath() + PackageUtils.extractPath(this.adapter.getNameSpace(cm, this.adapter.getController())+"."+cm.getName().toLowerCase())+ "/" + fileName;
		String fullTemplatePath = this.adapter.getTemplateSourceControlerPath().substring(1) + templateName;
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
	
	protected void makeLayout(ClassMetadata cm, String templateName, String fileName, boolean override) {
		this.datamodel.put(TagConstant.CURRENT_ENTITY, cm.name);
		String fullFilePath = String.format("%s/%s", 
				this.adapter.getRessourceLayoutPath(),
				fileName);
		String fullTemplatePath = String.format("%s/%s",
				this.adapter.getTemplateRessourceLayoutPath().substring(1),
				templateName);
		
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
