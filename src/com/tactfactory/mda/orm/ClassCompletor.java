package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.ConsoleUtils;
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
			String targetEntity = rel.entity_ref;
			if(rel.field_ref.isEmpty()){
				ClassMetadata cm_ref = this.metas.get(targetEntity);
				ArrayList<FieldMetadata> ids = new ArrayList<FieldMetadata>(cm_ref.ids.values());
				
				for(int i=0;i<ids.size();i++){
					rel.field_ref.add(ids.get(i).name);
				}
			}
			ConsoleUtils.displayDebug("Relation "+rel.type+" on field "+rel.field+" targets "+rel.entity_ref+"("+rel.field_ref.get(0)+")");
			if(rel.type.equals("OneToMany")){ // get inversedBy 
				if(rel.inversedBy==null || rel.inversedBy.isEmpty()){
					HashMap<String, FieldMetadata> rels_ref = this.metas.get(targetEntity).relations;
					for(FieldMetadata rel_ref : rels_ref.values()){
						if(rel_ref.relation.entity_ref.equals(cm.name))
						{
							rel.inversedBy = rel_ref.name;
						}
					}
					
				}
			} else
			if(rel.type.equals("ManyToMany")){
				if(rel.joinTable==null || rel.joinTable.isEmpty()){
					if(cm.name.compareTo(rel.entity_ref)>0) // Name JoinTable AtoB where A and B are the entities names ordered by alphabetic order
						rel.joinTable = cm.name+"to"+rel.entity_ref;
					else
						rel.joinTable = rel.entity_ref+"to"+cm.name;
				}
			}
		}
	}
}
