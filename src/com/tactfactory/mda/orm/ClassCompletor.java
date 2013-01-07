package com.tactfactory.mda.orm;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.ConsoleUtils;

/** The class ClassCompletor will complete all ClassMetadatas 
 * with the information it needs from the others ClassMetadatas*/

public class ClassCompletor {
	//ArrayList<ClassMetadata> metas_array;
	HashMap<String, ClassMetadata> metas = new HashMap<String, ClassMetadata>();
	HashMap<String, ClassMetadata> newMetas = new HashMap<String, ClassMetadata>();
	
	public ClassCompletor(HashMap<String, ClassMetadata> metas){
		this.metas = metas;
	}
	
	public void execute(){
		for(ClassMetadata cm : metas.values()){
			updateRelations(cm);
		}
		
		for (ClassMetadata meta : newMetas.values()) {
			this.metas.put(meta.name, meta);
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
					rel.field_ref.add(ids.get(i).fieldName);
				}
			}
			
			ConsoleUtils.displayDebug("Relation "+rel.type+" on field "+rel.field+" targets "+rel.entity_ref+"("+rel.field_ref.get(0)+")");
			if(rel.type.equals("OneToMany")){ // set inverse relation if it doesn't exists
				// Check if relation ManyToOne exists in target entity
				ClassMetadata entity_ref = this.metas.get(rel.entity_ref);

				// if it doesn't :
				if(rel.mappedBy==null){
					// Create it
					FieldMetadata new_field = new FieldMetadata();
					new_field.columnDefinition = "integer";
					new_field.hidden = true;
					new_field.internal = true;
					new_field.fieldName = cm.name.toLowerCase();
					new_field.columnName = new_field.fieldName;
					new_field.type = cm.name;
					new_field.relation = new RelationMetadata();
					new_field.relation.entity_ref = cm.name;
					for(FieldMetadata id : cm.ids.values())
						new_field.relation.field_ref.add(id.fieldName);
					new_field.relation.field = new_field.fieldName;
					new_field.relation.type = "ManyToOne";
					new_field.relation.inversedBy = fm.fieldName;
					entity_ref.fields.put(new_field.fieldName, new_field);
					entity_ref.relations.put(new_field.fieldName, new_field);
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
					classMeta.internal = true;
					classMeta.space = cm.space;
					FieldMetadata id = new FieldMetadata();
						id.columnDefinition = "integer";
						id.type = "integer";
						id.fieldName = "id";
						id.columnName = id.fieldName;
						id.id = true;
						classMeta.ids.put("id", id);
						classMeta.fields.put("id", id);
						
					FieldMetadata ref1 = generateRefField(cm.name);
					RelationMetadata rel1 = new RelationMetadata();
						rel1.entity_ref = cm.name;
						for(FieldMetadata cmid : cm.ids.values())
							rel1.field_ref.add(cmid.fieldName);
						rel1.inversedBy = fm.fieldName;
						rel1.type = "ManyToOne";
						ref1.relation = rel1;
						
					classMeta.fields.put(ref1.fieldName, ref1);
					classMeta.relations.put(ref1.fieldName, ref1);
					
					FieldMetadata ref2 = generateRefField(rel.entity_ref);
					RelationMetadata rel2 = new RelationMetadata();
						rel2.entity_ref = rel.entity_ref;
						rel2.field_ref = rel.field_ref;
						//rel2.inversedBy = rel.inversedBy;
						rel2.type = "ManyToOne";
						ref2.relation = rel2;
						
					classMeta.fields.put(ref2.fieldName, ref2);
					classMeta.relations.put(ref2.fieldName, ref2);
					
					this.newMetas.put(classMeta.name, classMeta);
				}else if(this.newMetas.containsKey(rel.joinTable)){ // Complete it !
					ClassMetadata jtable = this.newMetas.get(rel.joinTable);
					FieldMetadata relation = jtable.relations.get(cm.name.toLowerCase()+"_id");
					relation.relation.inversedBy = fm.fieldName;
				}
			}
		}
	}
	
	private FieldMetadata generateRefField(String name){
		FieldMetadata id = new FieldMetadata();
		id.columnDefinition = "integer";
		id.type = "integer";
		id.fieldName = name.toLowerCase()+"_id";
		id.columnName = id.fieldName;
		return id;
	}
}
