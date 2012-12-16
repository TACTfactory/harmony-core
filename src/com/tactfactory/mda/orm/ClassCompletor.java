package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;

/** The class ClassCompletor will complete all ClassMetadatas 
 * with the information it needs from the others ClassMetadatas*/

public class ClassCompletor {
	ArrayList<ClassMetadata> metas_array;
	HashMap<String, ClassMetadata> metas = new HashMap<String, ClassMetadata>();
	HashMap<String, ClassMetadata> newMetas = new HashMap<String, ClassMetadata>();
	
	public ClassCompletor(ArrayList<ClassMetadata> metas){
		this.metas_array = metas;
		for(ClassMetadata cm : metas){
			this.metas.put(cm.name, cm);
		}
	}
	
	public void execute(){
		for(ClassMetadata cm : metas.values()){
			updateRelations(cm);
		}
		this.metas_array.addAll(newMetas.values());
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
			if(rel.type.equals("OneToMany") || rel.type.equals("ManyToMany")){ // get inversedBy 
				if(rel.inversedBy==null || rel.inversedBy.isEmpty()){
					HashMap<String, FieldMetadata> rels_ref = this.metas.get(targetEntity).relations;
					for(FieldMetadata rel_ref : rels_ref.values()){
						if(rel_ref.relation.entity_ref.equals(cm.name))
						{
							rel.inversedBy = rel_ref.name;
						}
					}
					
				}
			}
			if(rel.type.equals("ManyToMany")){
				if(rel.joinTable==null || rel.joinTable.isEmpty()){
					if(cm.name.compareTo(rel.entity_ref)>0) // Name JoinTable AtoB where A and B are the entities names ordered by alphabetic order
						rel.joinTable = cm.name+"to"+rel.entity_ref;
					else
						rel.joinTable = rel.entity_ref+"to"+cm.name;
				}
				if(!this.metas.containsKey(rel.joinTable) && !this.newMetas.containsKey(rel.joinTable)){ // If jointable doesn't exist yet, create it

					ConsoleUtils.displayDebug("Association Table => "+rel.joinTable);
					ClassMetadata classMeta = new ClassMetadata();
					classMeta.name = rel.joinTable;
					classMeta.isAssociationClass = true;
					classMeta.space = cm.space;
					FieldMetadata id = new FieldMetadata();
						id.columnDefinition = "integer";
						id.type = "integer";
						id.name = "id";
						classMeta.ids.put("id", id);
						classMeta.fields.put("id", id);
						
					FieldMetadata ref1 = generateRefField(cm.name);
					RelationMetadata rel1 = new RelationMetadata();
						rel1.entity_ref = cm.name;
						for(FieldMetadata cmid : cm.ids.values())
							rel1.field_ref.add(cmid.name);
						rel1.inversedBy = rel.inversedBy;
						rel1.type = "ManyToOne";
						ref1.relation = rel1;
						
					classMeta.fields.put(ref1.name, ref1);
					classMeta.relations.put(ref1.name, ref1);
					
					FieldMetadata ref2 = generateRefField(rel.entity_ref);
					RelationMetadata rel2 = new RelationMetadata();
						rel2.entity_ref = rel.entity_ref;
						rel2.field_ref = rel.field_ref;
						rel2.inversedBy = fm.name;
						rel2.type = "ManyToOne";
						ref2.relation = rel2;
						
					classMeta.fields.put(ref2.name, ref2);
					classMeta.relations.put(ref2.name, ref2);
					
					this.newMetas.put(classMeta.name, classMeta);
				}else if(this.newMetas.containsKey(rel.joinTable)){ // Complete it !
					ClassMetadata jtable = this.newMetas.get(rel.joinTable);
					FieldMetadata relation = jtable.relations.get(rel.entity_ref.toLowerCase()+"_id");
					relation.relation.inversedBy = fm.name;
				}
			}
		}
	}
	
	private FieldMetadata generateRefField(String name){
		FieldMetadata id = new FieldMetadata();
		id.columnDefinition = "integer";
		id.type = name;
		id.name = name.toLowerCase()+"_id";
		id.name_in_db = id.name;
		return id;
	}
}
