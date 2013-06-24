/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.RelationMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;

/** The class ClassCompletor will complete all ClassMetadatas 
 * with the information it needs from the others ClassMetadatas. */

public class ClassCompletor {
	//ArrayList<ClassMetadata> metas_array;
	/** Class metadata. */
	private Map<String, EntityMetadata> metas;
	
	/** Newly created class metadata. */
	private final Map<String, EntityMetadata> newMetas = 
			new HashMap<String, EntityMetadata>();
	
	/**
	 * Constructor.
	 * @param entities Entities map.
	 */
	public ClassCompletor(final Map<String, EntityMetadata> entities) {
		this.metas = entities;
	}
	
	/**
	 * Complete classes.
	 */
	public final void execute() {
		for (final EntityMetadata cm : this.metas.values()) {
			this.updateRelations(cm);
		}
		
		for (final EntityMetadata cm : this.newMetas.values()) {
			this.metas.put(cm.getName(), cm);
		}
	}
	
	/**
	 * Update all the relations in a class.
	 * (actually sets the referenced columns for the target entities)
	 * @param cm The class owning the relations
	 */
	private void updateRelations(final EntityMetadata cm) {
		final ArrayList<FieldMetadata> newFields = 
				new ArrayList<FieldMetadata>();
		// For each relation in the class
		for (final FieldMetadata fm : cm.getRelations().values()) { 
			boolean isRecursive = false;
			final RelationMetadata rel = fm.getRelation();
			final String targetEntity = rel.getEntityRef();
			if (targetEntity.equals(cm.getName())) {
				isRecursive = true;
			}
			
			this.checkRelationIntegrity(fm);	
			if (rel.getFieldRef().isEmpty()) {
				final EntityMetadata cmRef = this.metas.get(targetEntity);
				final ArrayList<FieldMetadata> ids =
						new ArrayList<FieldMetadata>(cmRef.getIds().values());
				
				for (int i = 0; i < ids.size(); i++) {
					rel.getFieldRef().add(ids.get(i).getName());
				}
				fm.setColumnDefinition(ids.get(0).getType());
				
			}
			
			ConsoleUtils.displayDebug("Relation " + rel.getType()	
						+ " on field " + rel.getField()	
						+ " targets " + rel.getEntityRef()
						+ "(" + rel.getFieldRef().get(0) + ")");
			
			// set inverse relation if it doesn't exists
			if ("OneToMany".equals(rel.getType())) { 
				// Check if relation ManyToOne exists in target entity
				final EntityMetadata entityRef = 
						this.metas.get(rel.getEntityRef());

				// if it doesn't :
				if (rel.getMappedBy() == null) {
					// Create it
					final FieldMetadata newField = new FieldMetadata(cm);
					newField.setColumnDefinition("integer");
					newField.setHidden(true);
					newField.setNullable(fm.isNullable());
					newField.setInternal(true);
					newField.setName(cm.getName() + fm.getName() + "_Internal");
					newField.setColumnName(
							cm.getName() + "_" + fm.getName() + "_internal");
					newField.setType(cm.getName());
					newField.setRelation(new RelationMetadata());
					newField.getRelation().setEntityRef(cm.getName());
					for (final FieldMetadata id : cm.getIds().values()) {
						newField.getRelation().getFieldRef().add(id.getName());
					}
					newField.getRelation().setField(newField.getName());
					newField.getRelation().setType("ManyToOne");
					newField.getRelation().setInversedBy(fm.getName());
					fm.getRelation().setInversedBy(newField.getName());
					if (isRecursive) {
						newFields.add(newField);
					} else {
						entityRef.getFields().put(newField.getName(), newField);
						entityRef.getRelations().put(
								newField.getName(), newField);
					}
					rel.setMappedBy(newField.getName());
				} else { // Set inversedBy in mapping field
					FieldMetadata mappFm = 
							entityRef.getFields().get(rel.getMappedBy());
					mappFm.getRelation().setInversedBy(fm.getName());
				}
				
				
			}
			if ("ManyToMany".equals(rel.getType())) {
				if (rel.getJoinTable() == null 
						|| rel.getJoinTable().isEmpty()) {
					// Name JoinTable AtoB where A and B 
					// are the entities names ordered by alphabetic order
					if (cm.getName().compareTo(rel.getEntityRef()) > 0) { 
						rel.setJoinTable(
								cm.getName() + "to" + rel.getEntityRef());
					} else {
						rel.setJoinTable(
								rel.getEntityRef() + "to" + cm.getName());
					}
				}
				// If jointable doesn't exist yet, create it
				if (!this.metas.containsKey(rel.getJoinTable()) 
						&& !this.newMetas.containsKey(rel.getJoinTable())) { 

					ConsoleUtils.displayDebug(
							"Association Table => " + rel.getJoinTable());
					final EntityMetadata classMeta = new EntityMetadata();
					classMeta.setName(rel.getJoinTable());
					classMeta.setInternal(true);
					classMeta.setSpace(cm.getSpace());
					final FieldMetadata id = new FieldMetadata(classMeta);
						id.setColumnDefinition("integer");
						id.setType("integer");
						id.setName("id");
						id.setColumnName(id.getName());
						id.setId(true);
						classMeta.getIds().put("id", id);
						classMeta.getFields().put("id", id);
						
					final FieldMetadata ref1 =
							generateRefField(cm.getName(), cm);
					final RelationMetadata rel1 = new RelationMetadata();
						rel1.setEntityRef(cm.getName());
						for (final FieldMetadata cmid : cm.getIds().values()) {
							rel1.getFieldRef().add(cmid.getName());
						}
						rel1.setInversedBy(fm.getName());
						rel1.setType("ManyToOne");
						ref1.setRelation(rel1);
						
					classMeta.getFields().put(ref1.getName(), ref1);
					classMeta.getRelations().put(ref1.getName(), ref1);
					
					final FieldMetadata ref2 = 
							generateRefField(rel.getEntityRef(), cm);
					final RelationMetadata rel2 = new RelationMetadata();
						rel2.setEntityRef(rel.getEntityRef());
						rel2.setFieldRef(rel.getFieldRef());
						//rel2.inversedBy = rel.inversedBy;
						rel2.setType("ManyToOne");
						ref2.setRelation(rel2);
						
					classMeta.getFields().put(ref2.getName(), ref2);
					classMeta.getRelations().put(ref2.getName(), ref2);
					
					this.newMetas.put(classMeta.getName(), classMeta);
					
					// Complete it !
				} else if (this.newMetas.containsKey(rel.getJoinTable())) { 
					final EntityMetadata jtable = 
							this.newMetas.get(rel.getJoinTable());
					final FieldMetadata relation = 
							jtable.getRelations()
								.get(cm.getName().toLowerCase() + "_id");
					relation.getRelation().setInversedBy(fm.getName());
				}
			}
		}
		// Add internal recursive relations
		for (final FieldMetadata newField : newFields) {
			cm.getFields().put(newField.getName(), newField);
			cm.getRelations().put(newField.getName(), newField);
		}
	}
	
	/**
	 * Generate a reference field for relations.
	 * @param name referenced field name.
	 * @param owner New field owner.
	 * @return The newly created field.
	 */
	private static FieldMetadata generateRefField(final String name, 
			final EntityMetadata owner) {
		final FieldMetadata id = new FieldMetadata(owner);
		id.setColumnDefinition("integer");
		id.setType("integer");
		id.setName(name.toLowerCase() + "_id");
		id.setColumnName(id.getName());
		return id;
	}
	
	/**
	 * Check if field relation is valid.
	 * @param fm The field metadata of the relation.
	 */
	private void checkRelationIntegrity(final FieldMetadata fm) {
		if (!this.metas.containsKey(fm.getRelation().getEntityRef())) {
				ConsoleUtils.displayError(new ConstraintException(
						"Entity " 
						+ fm.getName() 
						+ " refers to the non Entity class " 
						+ fm.getRelation().getEntityRef()));			
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
