/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.RelationMetadata;
import com.tactfactory.mda.utils.ConsoleUtils;

/** The class ClassCompletor will complete all ClassMetadatas 
 * with the information it needs from the others ClassMetadatas. */

public class ClassCompletor {
	//ArrayList<ClassMetadata> metas_array;
	/** Class metadata. */
	private Map<String, ClassMetadata> metas;
	
	/** Newly created class metadata. */
	private final Map<String, ClassMetadata> newMetas = 
			new HashMap<String, ClassMetadata>();
	
	/**
	 * Constructor.
	 * @param entities Entities map.
	 */
	public ClassCompletor(final Map<String, ClassMetadata> entities) {
		this.metas = entities;
	}
	
	/**
	 * Complete classes.
	 */
	public final void execute() {
		for (final ClassMetadata cm : this.metas.values()) {
			this.updateRelations(cm);
		}
		
		for (final ClassMetadata cm : this.newMetas.values()) {
			this.metas.put(cm.name, cm);
		}
	}
	
	/**
	 * Update all the relations in a class.
	 * (actually sets the referenced columns for the target entities)
	 * @param cm The class owning the relations
	 */
	private void updateRelations(final ClassMetadata cm) {
		final ArrayList<FieldMetadata> newFields = 
				new ArrayList<FieldMetadata>();
		// For each relation in the class
		for (final FieldMetadata fm : cm.relations.values()) { 
			boolean isRecursive = false;
			final RelationMetadata rel = fm.relation;
			final String targetEntity = rel.entityRef;
			if (targetEntity.equals(cm.name)) {
				isRecursive = true;
			}
			
			this.checkRelationIntegrity(fm);
			
			if (rel.fieldRef.isEmpty()) {
				final ClassMetadata cmRef = this.metas.get(targetEntity);
				final ArrayList<FieldMetadata> ids =
						new ArrayList<FieldMetadata>(cmRef.ids.values());
				
				for (int i = 0; i < ids.size(); i++) {
					rel.fieldRef.add(ids.get(i).name);
				}
				fm.columnDefinition = ids.get(0).type;
				
			}
			
			ConsoleUtils.displayDebug("Relation " + rel.type	
						+ " on field " + rel.field	
						+ " targets " + rel.entityRef 
						+ "(" + rel.fieldRef.get(0) + ")");
			
			// set inverse relation if it doesn't exists
			if ("OneToMany".equals(rel.type)) { 
				// Check if relation ManyToOne exists in target entity
				final ClassMetadata entityRef = this.metas.get(rel.entityRef);

				// if it doesn't :
				if (rel.mappedBy == null) {
					// Create it
					final FieldMetadata newField = new FieldMetadata(cm);
					newField.columnDefinition = "integer";
					newField.hidden = true;
					newField.nullable = fm.nullable;
					newField.internal = true;
					newField.name = cm.name + fm.name + "_Internal";
					newField.columnName = cm.name + "_" + fm.name + "_internal";
					newField.type = cm.name;
					newField.relation = new RelationMetadata();
					newField.relation.entityRef = cm.name;
					for (final FieldMetadata id : cm.ids.values()) {
						newField.relation.fieldRef.add(id.name);
					}
					newField.relation.field = newField.name;
					newField.relation.type = "ManyToOne";
					newField.relation.inversedBy = fm.name;
					fm.relation.inversedBy = newField.name;
					if (isRecursive) {
						newFields.add(newField);
					} else {
						entityRef.fields.put(newField.name, newField);
						entityRef.relations.put(newField.name, newField);
					}
					rel.mappedBy = newField.name;
				}
				
			}
			if ("ManyToMany".equals(rel.type)) {
				if (rel.joinTable == null || rel.joinTable.isEmpty()) {
					// Name JoinTable AtoB where A and B 
					// are the entities names ordered by alphabetic order
					if (cm.name.compareTo(rel.entityRef) > 0) { 
						rel.joinTable = cm.name + "to" + rel.entityRef;
					} else {
						rel.joinTable = rel.entityRef + "to" + cm.name;
					}
				}
				// If jointable doesn't exist yet, create it
				if (!this.metas.containsKey(rel.joinTable) 
						&& !this.newMetas.containsKey(rel.joinTable)) { 

					ConsoleUtils.displayDebug(
							"Association Table => " + rel.joinTable);
					final ClassMetadata classMeta = new ClassMetadata();
					classMeta.name = rel.joinTable;
					classMeta.internal = true;
					classMeta.space = cm.space;
					final FieldMetadata id = new FieldMetadata(classMeta);
						id.columnDefinition = "integer";
						id.type = "integer";
						id.name = "id";
						id.columnName = id.name;
						id.id = true;
						classMeta.ids.put("id", id);
						classMeta.fields.put("id", id);
						
					final FieldMetadata ref1 = generateRefField(cm.name, cm);
					final RelationMetadata rel1 = new RelationMetadata();
						rel1.entityRef = cm.name;
						for (final FieldMetadata cmid : cm.ids.values()) {
							rel1.fieldRef.add(cmid.name);
						}
						rel1.inversedBy = fm.name;
						rel1.type = "ManyToOne";
						ref1.relation = rel1;
						
					classMeta.fields.put(ref1.name, ref1);
					classMeta.relations.put(ref1.name, ref1);
					
					final FieldMetadata ref2 = 
							generateRefField(rel.entityRef, cm);
					final RelationMetadata rel2 = new RelationMetadata();
						rel2.entityRef = rel.entityRef;
						rel2.fieldRef = rel.fieldRef;
						//rel2.inversedBy = rel.inversedBy;
						rel2.type = "ManyToOne";
						ref2.relation = rel2;
						
					classMeta.fields.put(ref2.name, ref2);
					classMeta.relations.put(ref2.name, ref2);
					
					this.newMetas.put(classMeta.name, classMeta);
					
					// Complete it !
				} else if (this.newMetas.containsKey(rel.joinTable)) { 
					final ClassMetadata jtable = 
							this.newMetas.get(rel.joinTable);
					final FieldMetadata relation = 
							jtable.relations.get(cm.name.toLowerCase() + "_id");
					relation.relation.inversedBy = fm.name;
				}
			}
		}
		// Add internal recursive relations
		for (final FieldMetadata newField : newFields) {
			cm.fields.put(newField.name, newField);
			cm.relations.put(newField.name, newField);
		}
	}
	
	/**
	 * Generate a reference field for relations.
	 * @param name referenced field name.
	 * @param owner New field owner.
	 * @return The newly created field.
	 */
	private static FieldMetadata generateRefField(final String name, 
			final ClassMetadata owner) {
		final FieldMetadata id = new FieldMetadata(owner);
		id.columnDefinition = "integer";
		id.type = "integer";
		id.name = name.toLowerCase() + "_id";
		id.columnName = id.name;
		return id;
	}
	
	/**
	 * Check if field relation is valid.
	 * @param fm The field metadata of the relation.
	 */
	private void checkRelationIntegrity(final FieldMetadata fm) {
		if (!this.metas.containsKey(fm.relation.entityRef)) {
				ConsoleUtils.displayError(new ConstraintException(
						"Entity " 
						+ fm.name 
						+ " refers to the non Entity class " 
						+ fm.relation.entityRef));			
		}
	}
	
	/**
	 * Relation Constraint Exception. 
	 */
	@SuppressWarnings("serial")
	public static class ConstraintException extends Exception {

		/**
		 * Constructor.
		 * @param msg The exception message.
		 */
		public ConstraintException(final String msg) {
			super(msg);
		}
		
	}
}
