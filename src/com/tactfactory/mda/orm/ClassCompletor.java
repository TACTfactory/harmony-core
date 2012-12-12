package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.Harmony;

/** The class ClassCompletor will complete all ClassMetadatas 
 * with the information it needs from the others ClassMetadatas*/

public class ClassCompletor {
	HashMap<String, ClassMetadata> metas = new HashMap<String, ClassMetadata>();
	
	public ClassCompletor(ArrayList<ClassMetadata> metas){
		for(ClassMetadata cm : metas){
			this.metas.put(cm.name, cm);
		}
	}
	
	public void execute(){
		for(ClassMetadata cm : metas.values()){
			updateRelations(cm);
		}
	}
	
	/**
	 * Update all the relations in a class 
	 * (actually sets the referenced columns for the target entities)
	 * @param cm The class owning the relations
	 */
	private void updateRelations(ClassMetadata cm){
		for(FieldMetadata fm : cm.relations.values()){ // For each relation in the class
			RelationMetadata rel = fm.relation; 
			if(rel.field_ref.isEmpty()){
				String targetEntity = rel.entity_ref;
				ClassMetadata cm_ref = this.metas.get(targetEntity);
				ArrayList<FieldMetadata> ids = new ArrayList<FieldMetadata>(cm_ref.ids.values());
				
				for(int i=0;i<ids.size();i++){
					rel.field_ref.add(ids.get(i).name);
				}
			}
			if(Harmony.debug){
				System.out.println("Relation "+rel.type+" on field "+rel.field+" targets "+rel.entity_ref+"("+rel.field_ref.get(0)+")");
			}
		}
	}
}
