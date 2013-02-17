/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

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
			this.updateRelations(cm);
		}
		
		for (ClassMetadata cm : newMetas.values()) {
			this.metas.put(cm.name, cm);
		}
	}
	
	/**
	 * Update all the relations in a class 
	 * (actually sets the referenced columns for the target entities)
	 * @param cm The class owning the relations
	 */
	private void updateRelations(ClassMetadata cm){
		ArrayList<FieldMetadata> newFields= new ArrayList<FieldMetadata>();
		for(FieldMetadata fm : cm.relations.values()){ // For each relation in the class
			boolean isRecursive = false;
			RelationMetadata rel = fm.relation;
			String targetEntity = rel.entity_ref;
			if(targetEntity.equals(cm.name)){
				isRecursive = true;
			}
			
			this.checkRelationIntegrity(fm);
			
			if(rel.field_ref.isEmpty()){
				ClassMetadata cm_ref = this.metas.get(targetEntity);
				ArrayList<FieldMetadata> ids = new ArrayList<FieldMetadata>(cm_ref.ids.values());
				
				for(int i=0;i<ids.size();i++){
					rel.field_ref.add(ids.get(i).name);
				}
				fm.columnDefinition = ids.get(0).type;
				
			}
			
			ConsoleUtils.displayDebug("Relation "+rel.type+" on field "+rel.field+" targets "+rel.entity_ref+"("+rel.field_ref.get(0)+")");
			if(rel.type.equals("OneToMany")){ // set inverse relation if it doesn't exists
				// Check if relation ManyToOne exists in target entity
				ClassMetadata entity_ref = this.metas.get(rel.entity_ref);

				// if it doesn't :
				if(rel.mappedBy==null){
					// Create it
					FieldMetadata new_field = new FieldMetadata(cm);
					new_field.columnDefinition = "integer";
					new_field.hidden = true;
					new_field.nullable = fm.nullable;
					new_field.internal = true;
					new_field.name = cm.name+fm.name+"_Internal";
					new_field.columnName = cm.name+"_"+fm.name+"_internal";
					new_field.type = cm.name;
					new_field.relation = new RelationMetadata();
					new_field.relation.entity_ref = cm.name;
					for(FieldMetadata id : cm.ids.values())
						new_field.relation.field_ref.add(id.name);
					new_field.relation.field = new_field.name;
					new_field.relation.type = "ManyToOne";
					new_field.relation.inversedBy = fm.name;
					fm.relation.inversedBy = new_field.name;
					if(isRecursive){
						newFields.add(new_field);
					}else{
						entity_ref.fields.put(new_field.name, new_field);
						entity_ref.relations.put(new_field.name, new_field);
					}
					rel.mappedBy = new_field.name;
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
					FieldMetadata id = new FieldMetadata(classMeta);
						id.columnDefinition = "integer";
						id.type = "integer";
						id.name = "id";
						id.columnName = id.name;
						id.id = true;
						classMeta.ids.put("id", id);
						classMeta.fields.put("id", id);
						
					FieldMetadata ref1 = generateRefField(cm.name, cm);
					RelationMetadata rel1 = new RelationMetadata();
						rel1.entity_ref = cm.name;
						for(FieldMetadata cmid : cm.ids.values())
							rel1.field_ref.add(cmid.name);
						rel1.inversedBy = fm.name;
						rel1.type = "ManyToOne";
						ref1.relation = rel1;
						
					classMeta.fields.put(ref1.name, ref1);
					classMeta.relations.put(ref1.name, ref1);
					
					FieldMetadata ref2 = generateRefField(rel.entity_ref, cm);
					RelationMetadata rel2 = new RelationMetadata();
						rel2.entity_ref = rel.entity_ref;
						rel2.field_ref = rel.field_ref;
						//rel2.inversedBy = rel.inversedBy;
						rel2.type = "ManyToOne";
						ref2.relation = rel2;
						
					classMeta.fields.put(ref2.name, ref2);
					classMeta.relations.put(ref2.name, ref2);
					
					this.newMetas.put(classMeta.name, classMeta);
				}else if(this.newMetas.containsKey(rel.joinTable)){ // Complete it !
					ClassMetadata jtable = this.newMetas.get(rel.joinTable);
					FieldMetadata relation = jtable.relations.get(cm.name.toLowerCase()+"_id");
					relation.relation.inversedBy = fm.name;
				}
			}
		}
		// Add internal recursive relations
		for(FieldMetadata newField : newFields){
			cm.fields.put(newField.name, newField);
			cm.relations.put(newField.name, newField);
		}
	}
	
	private static FieldMetadata generateRefField(String name, ClassMetadata owner){
		FieldMetadata id = new FieldMetadata(owner);
		id.columnDefinition = "integer";
		id.type = "integer";
		id.name = name.toLowerCase()+"_id";
		id.columnName = id.name;
		return id;
	}
	
	private void checkRelationIntegrity(FieldMetadata fm){
		if(!this.metas.containsKey(fm.relation.entity_ref)){
				ConsoleUtils.displayError(new Exception("Entity "+fm.name+" refers to the non Entity class "+fm.relation.entity_ref));
			
		}
	}
}
