package com.tactfactory.mda.search.template;

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
			if(cm.fields.size()>0 && isClassSearchable(cm)){
				this.makeSource(cm, "TemplateSearch.java",cm.name+"Search.java",false);
			}
		}
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
		
		System.out.println(fullTemplatePath+" => "+fullFilePath);
		super.makeSource(fullTemplatePath, fullFilePath, override);
	}
}
