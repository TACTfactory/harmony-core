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

import com.google.common.base.Strings;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.RelationMetadata;
import com.tactfactory.harmony.plateforme.SqliteAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.MetadataUtils;


/** The class ClassCompletor will complete all ClassMetadatas
 * with the information it needs from the others ClassMetadatas. */

public class ClassCompletor {
	/** Integer literal. */
	private static final String TYPE_INTEGER = "INTEGER";

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
		for (final EntityMetadata classMeta : this.metas.values()) {
			this.updateInheritedIds(classMeta);
			this.updateRelations(classMeta);
			this.updateColumnDefinition(classMeta);
		}

		for (final EntityMetadata classMeta : this.newMetas.values()) {
			this.metas.put(
					classMeta.getName(),
					classMeta);

			ApplicationMetadata.INSTANCE.getClasses().put(
					classMeta.getName(),
					classMeta);
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
		for (final FieldMetadata fieldMeta : cm.getRelations().values()) {
			boolean isRecursive = false;
			final RelationMetadata rel = fieldMeta.getRelation();
			final String targetEntity = rel.getEntityRef();
			if (targetEntity.equals(cm.getName())) {
				isRecursive = true;
			}

			this.checkRelationIntegrity(fieldMeta);
			if (rel.getFieldRef().isEmpty()) {
				final EntityMetadata cmRef = this.metas.get(targetEntity);
				final ArrayList<FieldMetadata> ids =
						new ArrayList<FieldMetadata>(cmRef.getIds().values());

				for (int i = 0; i < ids.size(); i++) {
					rel.getFieldRef().add(ids.get(i).getName());
				}
				
				if (ids.isEmpty()) {
					final RuntimeException exception = new RuntimeException(
							"Error while updating relations : "
							+ " Your entity " + cm.getName()
							+ " refers the entity " + cmRef.getName()
							+ " which has no ID defined."
							+ " Please add the @Id annotation "
							+ "to one of the fields of " + cmRef.getName());
					ConsoleUtils.displayError(exception);
					throw exception;
				}

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
					final FieldMetadata newField = new FieldMetadata(entityRef);
					newField.setColumnDefinition(TYPE_INTEGER);
					newField.setHidden(true);
					newField.setNullable(fieldMeta.isNullable());
					newField.setInternal(true);
					newField.setName(
							cm.getName() 
							+ fieldMeta.getName() 
							+ "_Internal");
					
					newField.setColumnName(
							cm.getName() 
							+ "_" + fieldMeta.getName() 
							+ "_internal");
					newField.setType(cm.getName());
					newField.setHarmonyType(TYPE_INTEGER);
					newField.setRelation(new RelationMetadata());
					newField.getRelation().setEntityRef(cm.getName());
					for (final FieldMetadata idField : cm.getIds().values()) {
						newField.getRelation().getFieldRef().add(
								idField.getName());
					}
					newField.getRelation().setField(newField.getName());
					newField.getRelation().setType("ManyToOne");
					newField.getRelation().setInversedBy(fieldMeta.getName());
					fieldMeta.getRelation().setInversedBy(newField.getName());
					if (isRecursive) {
						newFields.add(newField);
					} else {
						entityRef.getFields().put(newField.getName(), newField);
						entityRef.getRelations().put(
								newField.getName(), newField);
					}
					rel.setMappedBy(newField.getName());
				} else { // Set inversedBy in mapping field
					final FieldMetadata mappFm =
							entityRef.getFields().get(rel.getMappedBy());
					mappFm.getRelation().setInversedBy(fieldMeta.getName());
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
					final FieldMetadata idField = new FieldMetadata(classMeta);
						idField.setColumnDefinition(TYPE_INTEGER);
						idField.setType(TYPE_INTEGER);
						idField.setHarmonyType(TYPE_INTEGER);
						idField.setName("id");
						idField.setColumnName(idField.getName());
						idField.setId(true);
						classMeta.getIds().put("id", idField);
						classMeta.getFields().put("id", idField);

					final FieldMetadata ref1 =
							generateRefField(cm.getName(), classMeta);
					final RelationMetadata rel1 = new RelationMetadata();
						rel1.setEntityRef(cm.getName());
						for (final FieldMetadata cmid : cm.getIds().values()) {
							rel1.getFieldRef().add(cmid.getName());
						}
						rel1.setInversedBy(fieldMeta.getName());
						rel1.setType("ManyToOne");
						ref1.setRelation(rel1);

					classMeta.getFields().put(ref1.getName(), ref1);
					classMeta.getRelations().put(ref1.getName(), ref1);

					final FieldMetadata ref2 =
							generateRefField(rel.getEntityRef(), classMeta);
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
					relation.getRelation().setInversedBy(fieldMeta.getName());
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
		final FieldMetadata idField = new FieldMetadata(owner);
		idField.setColumnDefinition(TYPE_INTEGER);
		idField.setType(TYPE_INTEGER);
		idField.setHarmonyType(TYPE_INTEGER);
		idField.setName(name.toLowerCase() + "_id");
		idField.setColumnName(idField.getName());
		return idField;
	}

	/**
	 * Check if field relation is valid.
	 * @param fieldMeta The field metadata of the relation.
	 */
	private void checkRelationIntegrity(final FieldMetadata fieldMeta) {
		if (!this.metas.containsKey(fieldMeta.getRelation().getEntityRef())) {
				ConsoleUtils.displayError(new ConstraintException(
						"Entity "
						+ fieldMeta.getName()
						+ " refers to the non Entity class "
						+ fieldMeta.getRelation().getEntityRef()));
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

	/**
	 * Add the mother ID field to inherited entities.
	 * @param cm The entity to update
	 */
	private void updateInheritedIds(final EntityMetadata cm) {
		// If entity has a mother
		if (cm.getExtendType() != null) {
			final EntityMetadata mother = MetadataUtils.getTopMostMother(cm,
					ApplicationMetadata.INSTANCE);
			for (String idName : mother.getIds().keySet()) {
				final FieldMetadata id = mother.getIds().get(idName);
				cm.getIds().put(idName, id);
				cm.getFields().put(idName, id);
			}
		}
	}
	
	/**
	 * Update fields column definition.
	 * 
	 * @param entity The entity in which to complete fields
	 */
	private void updateColumnDefinition(final EntityMetadata entity) {
		for (final FieldMetadata field : entity.getFields().values()) {
			// Get column definition if non existent
			if (Strings.isNullOrEmpty(field.getColumnDefinition())) {
				field.setColumnDefinition(
						SqliteAdapter.generateColumnType(field));
			}
			
			// Warn the user if the column definition is a reserved keyword
			SqliteAdapter.Keywords.exists(field.getColumnDefinition());
			
			// Set default values for type if type is recognized
			final Type type;
			if (field.getHarmonyType() != null) {
				type = Type.fromName(
						field.getHarmonyType());
			} else {
				type = Type.fromName(
						field.getColumnDefinition());
			}
			
			if (type != null) {
				//field.setColumnDefinition(type.getValue());
				if (field.isNullable() == null) {
					field.setNullable(type.isNullable());
				}
				if (field.isUnique() == null) {
					field.setUnique(type.isUnique());
				}
				if (field.getLength() == null) {
					field.setLength(type.getLength());
				}
				if (field.getPrecision() == null) {
					field.setPrecision(type.getPrecision());
				}
				if (field.getScale() == null) {
					field.setScale(type.getScale());
				}
				if (field.isLocale() == null) {
					field.setIsLocale(type.isLocale());
				}
			} else {
				if (field.isNullable() == null) {
					field.setNullable(false);
				}
				if (field.isUnique() == null) {
					field.setUnique(false);
				}
				if (field.getLength() == null) {
					field.setLength(null);
				}
				if (field.getPrecision() == null) {
					field.setPrecision(null);
				}
				if (field.getScale() == null) {
					field.setScale(null);
				}
				if (field.isLocale() == null) {
					field.setIsLocale(false);
				}
			}
		}
	}
}
