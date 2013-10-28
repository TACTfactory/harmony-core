/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser.java.visitor;

import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import java.util.List;
import java.util.Map;

import com.tactfactory.harmony.parser.JavaModelParser;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.JoinColumn;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.OneToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.RelationMetadata;
import com.tactfactory.harmony.parser.BaseParser;
import com.tactfactory.harmony.plateforme.SqliteAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

/**
 * JavaParser Field Visitor.
 */
public class FieldVisitor {
	/** Integer literal. */
	private static final String TYPE_INTEGER = "INTEGER";

	/** Id annotation name. */
	private static final String FILTER_ID	 		=
			PackageUtils.extractNameEntity(Id.class);

	/** Column annotation name. */
	private static final String FILTER_COLUMN 		=
			PackageUtils.extractNameEntity(Column.class);

	/** JoinColumn annotation name. */
	private static final String FILTER_JOINCOLUMN 	=
			PackageUtils.extractNameEntity(JoinColumn.class);

	/** OneToOne annotation name. */
	private static final String FILTER_ONE2ONE 		=
			PackageUtils.extractNameEntity(OneToOne.class);

	/** OneToMany annotation name. */
	private static final String FILTER_ONE2MANY 	=
			PackageUtils.extractNameEntity(OneToMany.class);

	/** ManyToOne annotation name. */
	private static final String FILTER_MANY2ONE 	=
			PackageUtils.extractNameEntity(ManyToOne.class);

	/** ManyToMany annotation name. */
	private static final String FILTER_MANY2MANY 	=
			PackageUtils.extractNameEntity(ManyToMany.class);


	/** Column annotation name attribute. */
	private static final String ATTRIBUTE_NAME = "name";

	/** Column annotation type attribute. */
	private static final String ATTRIBUTE_TYPE = "type";

	/** Column annotation nullable attribute. */
	private static final String ATTRIBUTE_NULLABLE = "nullable";

	/** Column annotation unique attribute. */
	private static final String ATTRIBUTE_UNIQUE = "unique";

	/** Column annotation length attribute. */
	private static final String ATTRIBUTE_LENGTH = "length";

	/** Column annotation precision attribute. */
	private static final String ATTRIBUTE_PRECISION = "precision";

	/** Column annotation scale attribute. */
	private static final String ATTRIBUTE_SCALE = "scale";

	/** Column annotation isLocale attribute. */
	private static final String ATTRIBUTE_LOCALE = "locale";

	/** Column annotation hidden attribute. */
	private static final String ATTRIBUTE_HIDDEN = "hidden";

	/** Column annotation columnDefinition attribute. */
	private static final String ATTRIBUTE_COLUMN_DEFINITION =
			"columnDefinition";

	/** Relations annotation mappedBy attribute. */
	private static final String ATTRIBUTE_MAPPED_BY = "mappedBy";

	/** Relations annotation inversedBy attribute. */
	private static final String ATTRIBUTE_INVERSED_BY = "inversedBy";
	
	private Map<String, EntityMetadata> projectEntities = 
			ApplicationMetadata.INSTANCE.getEntities();
	
	private Map<String, ClassMetadata> projectClasses = 
			ApplicationMetadata.INSTANCE.getClasses();
	
	/**
	 * Visit a field declaration to extract metadata.
	 * @param field The field declaration.
	 * @param classMeta The ClassMetada of the field's class
	 * @return The FieldMetadata extracted
	 */
	public final FieldMetadata visit(final FieldDeclaration field,
			final ClassMetadata classMeta) {
		FieldMetadata result = null;
    	// Call the parsers which have been registered by the bundle
    	for (final BaseParser bParser
    			: JavaModelParser.getBundleParsers()) {
    		bParser.visitField(field, classMeta);
    	}

		final List<AnnotationExpr> fieldAnnotations
				= field.getAnnotations();

		if (fieldAnnotations != null) {
			// General (required !)
			// FIXME not manage multi-variable
			String fieldName = field.getVariables().get(0).getId().getName();


			if (classMeta.getFields().containsKey(fieldName)) {
				result = classMeta.getFields().get(fieldName);
			} else {
				result = new FieldMetadata(classMeta);
				result.setName(fieldName);
			}
			result.setInternal(false);
			
			result.setType(field.getType().toString());

			// Java types Date and Time are deprecated in Harmony
			if (result.getType().equalsIgnoreCase("date")
					|| result.getType().equalsIgnoreCase("time")) {
				ConsoleUtils.displayWarning(
						"You should use DateTime java type instead of "
						+ result.getType()
						+ ". Errors may occur.");
			}
			result.setColumnName(result.getName());

			// Set defaults values
			result.setHidden(false);

			// Database definitions
			final RelationMetadata rel = new RelationMetadata();		
			final EntityMetadata referencedEntity;
			boolean isColumn = false;
			boolean isId = false;
			boolean isRelation = false;

			String referencedEntityName = 
					PackageUtils.extractClassNameFromArray(result.getType());
			// Prepare relation ?
			if (projectEntities.containsKey(referencedEntityName)) {
				referencedEntity = this.projectEntities.get(referencedEntityName);
			} else {
				referencedEntity = new EntityMetadata();
				referencedEntity.setName(referencedEntityName);
			}
			
			rel.setEntityRef(referencedEntity);

			// Analyze
			for (final AnnotationExpr annotationExpr : fieldAnnotations) {
				final String annotationType =
						annotationExpr.getName().toString();

		    	for (final BaseParser bParser
		    			: JavaModelParser.getBundleParsers()) {
		    		bParser.visitFieldAnnotation(result,
		    				annotationExpr,
		    				classMeta);
		    	}

				isId = this.isId(result, isId, annotationType);
				isColumn = this.isColumn(result,
						isColumn,
						annotationType);

				isRelation = this.isRelation(result,
						isRelation,
						annotationType);

				if (annotationType.equals(FILTER_ONE2ONE)
						|| annotationType.equals(FILTER_ONE2MANY)
						|| annotationType.equals(FILTER_MANY2ONE)
						|| annotationType.equals(FILTER_MANY2MANY)) {
					rel.setType(annotationType);
				}

				this.loadAttributes(rel,
						result,
						annotationExpr,
						annotationType);
			}

			// ID relation
			if (isId) {
				result.setId(true);
			}

			// Object relation
			if (isRelation) {
				this.projectEntities.put(
						referencedEntityName,
						referencedEntity);
				
				this.projectClasses.put( 
						referencedEntityName,
						referencedEntity);
				
				result.setRelation(rel);
				rel.setField(result.getName());
				
				if (rel.getType().equals("OneToMany")
						&& rel.getMappedBy() == null) {
					this.createMappingField(result);
				} else if (rel.getType().equals("ManyToMany")) {
					// TODO create JoinTable, etc.
					this.createJoinTable(result);
				}
			}


			// Add to meta dictionary
			if (isId || isColumn || isRelation) {
				classMeta.getFields().put(result.getName(), result);
			}

			// Check SQLite reserved keywords
			SqliteAdapter.Keywords.exists(result.getName());
			if (!result.getName().equals(result.getColumnName())) {
				SqliteAdapter.Keywords.exists(result.getColumnName());
			}
			SqliteAdapter.Keywords.exists(result.getType());
		}
		return result;
	}

	/**
	 * Load the field attributes.
	 * @param result The field Metadata.
	 * @param annotationExpr The annotation expression.
	 * @param annotationType The annotation Type.
	 * @param rel The relation Metadata
	 */
	private void loadAttributes(final RelationMetadata rel,
			final FieldMetadata result,
			final AnnotationExpr annotationExpr,
			final String annotationType) {

		if (annotationExpr instanceof NormalAnnotationExpr) {
			final NormalAnnotationExpr norm =
					(NormalAnnotationExpr) annotationExpr;

			// Check if there are any arguments in the annotation
			if (norm.getPairs() != null) {
				for (final MemberValuePair mvp
						: norm.getPairs()) {

					// Argument of Annotation Column
					if (annotationType.equals(FILTER_COLUMN)) {
						// set nullable
						if (mvp.getName().equals(ATTRIBUTE_NULLABLE)
								&& mvp.getValue().toString()
											.equals(String.valueOf(true))) {
							result.setNullable(true);
						} else

						// set name
						if (mvp.getName().equals(ATTRIBUTE_NAME)) {
							if (mvp.getValue()
									instanceof StringLiteralExpr) {
								result.setColumnName(
										((StringLiteralExpr)
												mvp.getValue()).getValue());
							} else {
								result.setColumnName(
										mvp.getValue().toString());
							}
						} else

						// Set unique
						if (mvp.getName().equals(ATTRIBUTE_UNIQUE)
								&& mvp.getValue().toString()
										.equals(String.valueOf(true))) {
							result.setUnique(true);
						} else

						// set length
						if (mvp.getName().equals(ATTRIBUTE_LENGTH)) {
							result.setLength(Integer.parseInt(
									mvp.getValue().toString()));
						} else

						// set precision
						if (mvp.getName().equals(ATTRIBUTE_PRECISION)) {
							result.setPrecision(Integer.parseInt(
									mvp.getValue().toString()));
						} else

						// set scale
						if (mvp.getName().equals(ATTRIBUTE_SCALE)) {
							result.setScale(Integer.parseInt(
									mvp.getValue().toString()));
						} else

						if (mvp.getName().equals("defaultValue")) {
							String defaultValue;
							if (mvp.getValue()
									instanceof StringLiteralExpr) {
								defaultValue = ((StringLiteralExpr)
										mvp.getValue()).getValue();
							} else {
								defaultValue = mvp.getValue().toString();
							}
							result.setDefaultValue(defaultValue);

						} else

						// set scale
						if (mvp.getName().equals(ATTRIBUTE_LOCALE)) {
							result.setIsLocale(Boolean.parseBoolean(
									mvp.getValue().toString()));
						} else

						// set column definition
						if (mvp.getName().equals(ATTRIBUTE_TYPE)) {
							//TODO : Generate warning if type not recognized
							String type = "";

							if (mvp.getValue()
									instanceof StringLiteralExpr) {
								type = ((StringLiteralExpr)
										mvp.getValue()).getValue();
							} else {
								type = mvp.getValue().toString();
							}
							result.setHarmonyType(
									Type.fromName(type).getValue());
							/*result.setColumnDefinition(
									Type.fromName(type).getValue());*/
						} else

						// set scale
						if (mvp.getName().equals(ATTRIBUTE_COLUMN_DEFINITION)) {
							if (mvp.getValue()
									instanceof StringLiteralExpr) {
								result.setColumnDefinition(
										((StringLiteralExpr)
												mvp.getValue()).getValue());
							} else {
								result.setColumnDefinition(
										mvp.getValue().toString());
							}
						} else

						// set if hide column view
						if (mvp.getName().equals(ATTRIBUTE_HIDDEN)
								&& mvp.getValue().toString()
										.equals(String.valueOf(true))) {
							result.setHidden(true);
						}

					} else

					if (annotationType.equals(FILTER_JOINCOLUMN)) {
						if (mvp.getName().equals(ATTRIBUTE_NAME)) {
							rel.setName(((StringLiteralExpr)
									mvp.getValue()).getValue());
						}
					} else

					if (annotationType.equals(FILTER_ONE2MANY)) {
						if (mvp.getName().equals(ATTRIBUTE_MAPPED_BY)) {
							String fieldName = ((StringLiteralExpr)
									mvp.getValue()).getValue();
							FieldMetadata fieldRef;
							if (rel.getEntityRef().getFields().containsKey(fieldName)) {
								fieldRef = rel.getEntityRef().getFields().get(fieldName);
							} else {
								RelationMetadata inversingRel = 
										new RelationMetadata();
								inversingRel.setType("ManyToOne");
								fieldRef = new FieldMetadata(rel.getEntityRef());
								fieldRef.setName(fieldName);
								fieldRef.setRelation(inversingRel);
								fieldRef.setType(result.getOwner().getName());
								fieldRef.setInternal(true);
							}
							fieldRef.getRelation().setInversedBy(result);
							rel.setMappedBy(fieldRef);						}
					} else

					if (annotationType.equals(FILTER_MANY2ONE)) {
						if (mvp.getName().equals(ATTRIBUTE_INVERSED_BY)) {
							String fieldName = ((StringLiteralExpr)
									mvp.getValue()).getValue();
							FieldMetadata fieldRef;
							if (rel.getEntityRef().getFields().containsKey(fieldName)) {
								fieldRef = rel.getEntityRef().getFields().get(fieldName);
							} else {
								RelationMetadata inversingRel = 
										new RelationMetadata();
								inversingRel.setMappedBy(result);
								inversingRel.setType("OneToMany");
								fieldRef = new FieldMetadata(rel.getEntityRef());
								fieldRef.setName(fieldName);
								fieldRef.setRelation(inversingRel);
								fieldRef.setInternal(true);
								fieldRef.setType(result.getOwner().getName());
							}
							rel.setInversedBy(fieldRef);
						}
					}
				}
			}
		}
	}

	/**
	 * Check if Id annotation is present in entity.
	 *
	 * @param result The field metadata to complete
	 * @param old Is the field already known as an id ?
	 * @param annotationType The annotation to parse
	 * @return True if the field is an ID
	 */
	private boolean isId(final FieldMetadata result,
			final boolean old,
			final String annotationType) {
		boolean isId = old;

		if (annotationType.equals(FILTER_ID)) {
			isId = true;

			// Debug Log
			ConsoleUtils.displayDebug("\tID : " + result.getName());
		}

		return isId;
	}

	/**
	 * Check if Column annotation is present in entity.
	 *
	 * @param result The FieldMetadata to complete
	 * @param old Was this field already a column ?
	 * @param annotationType The annotation to parse
	 * @return True if the field is a column
	 */
	private boolean isColumn(final FieldMetadata result,
			final boolean old,
			final String annotationType) {
		boolean isColumn = old;

		if (annotationType.equals(FILTER_COLUMN)
			|| annotationType.equals(FILTER_JOINCOLUMN)) {

			isColumn = true;

			// Debug Log
			String type = "Column";
			if (annotationType.equals(FILTER_JOINCOLUMN)) {
				type = "Join Column";
			}

			ConsoleUtils.displayDebug(
					"\t" + type + " : " + result.getName()
					+ " type of " + result.getType());
		}

		return isColumn;
	}

	/**
	 * Check if Relation annotation is present in entity.
	 *
	 * @param result The FieldMetadata to complete
	 * @param old Was this field already a relation ?
	 * @param annotationType The annotation to parse
	 * @return True if the field is a relation
	 */
	private boolean isRelation(final FieldMetadata result,
			final boolean old,
			final String annotationType) {
		boolean isRelation = old;

		if (annotationType.equals(FILTER_ONE2ONE)
			|| annotationType.equals(FILTER_ONE2MANY)
			|| annotationType.equals(FILTER_MANY2ONE)
			|| annotationType.equals(FILTER_MANY2MANY)) {
			isRelation = true;

			// Debug Log
			ConsoleUtils.displayDebug("\tRelation "
					+ annotationType
					+ " : "
					+ result.getName()
					+ " type of "
					+ result.getType());
		}

		return isRelation;
	}
	
	/**
	 * Creates a field mapping the relation given.
	 * 
	 * @param currentField The field containing the relation to map.
	 */
	private void createMappingField(final FieldMetadata currentField) {
		// Create it
		final EntityMetadata entityRef = currentField.getRelation().getEntityRef();
		final FieldMetadata newField = new FieldMetadata(entityRef);
		newField.setColumnDefinition(TYPE_INTEGER);
		newField.setHidden(true);
		newField.setNullable(currentField.isNullable());
		newField.setInternal(true);
		newField.setName(
				currentField.getOwner().getName() 
				+ currentField.getName() 
				+ "_Internal");
		
		newField.setColumnName(
				currentField.getOwner().getName() 
				+ "_" + currentField.getName() 
				+ "_internal");
		newField.setType(currentField.getOwner().getName());
		newField.setHarmonyType(TYPE_INTEGER);
		newField.setRelation(new RelationMetadata());
		newField.getRelation().setEntityRef((EntityMetadata) currentField.getOwner());
		newField.getRelation().setField(newField.getName());
		newField.getRelation().setType("ManyToOne");
		newField.getRelation().setInversedBy(currentField);
				
		entityRef.getFields().put(newField.getName(), newField);
		entityRef.getRelations().put(
				newField.getName(), newField);
		
		currentField.getRelation().setMappedBy(newField);
	}
	
	/**
	 * Create a jointable for the manytomany relation.
	 * @param currentField The field containing the relation.
	 */
	private void createJoinTable(final FieldMetadata currentField) {
		RelationMetadata rel = currentField.getRelation();
		EntityMetadata cm = (EntityMetadata) currentField.getOwner();
		if (rel.getJoinTable() == null
				|| rel.getJoinTable().isEmpty()) {
			// Name JoinTable AtoB where A and B
			// are the entities names ordered by alphabetic order
			if (cm.getName().compareTo(rel.getEntityRef().getName()) > 0) {
				rel.setJoinTable(
						cm.getName() + "to" + rel.getEntityRef().getName());
			} else {
				rel.setJoinTable(
						rel.getEntityRef().getName() + "to" + cm.getName());
			}
		}
		
		ConsoleUtils.displayDebug(
				"Association Table => " + rel.getJoinTable());
		
		EntityMetadata joinTable;
		// If jointable doesn't exist yet, create it
		if (!this.projectEntities.containsKey(rel.getJoinTable())) {
			joinTable = new EntityMetadata();
			joinTable.setName(rel.getJoinTable());
			joinTable.setInternal(true);
			joinTable.setSpace(cm.getSpace());
			this.projectClasses.put(joinTable.getName(), joinTable);
			this.projectEntities.put(joinTable.getName(), joinTable);
		} else {
			joinTable = this.projectEntities.get(rel.getJoinTable());
		}

		
		FieldMetadata mappingField = this.generateJoinTableRefField(
				(EntityMetadata) currentField.getOwner(),
				joinTable);
		
		mappingField.getRelation().setInversedBy(currentField);
		currentField.getRelation().setMappedBy(mappingField);
		
		if (this.projectEntities.containsKey(rel.getEntityRef().getName())) {
			EntityMetadata entityRef = 
					this.projectEntities.get(rel.getEntityRef().getName());
			this.generateJoinTableRefField(
					(EntityMetadata) entityRef,
					joinTable);
		} 
	}
	
	/**
	 * Generate a field pointing to the given referencedClass in the
	 * given Jointable.
	 * 
	 * @param referencedClass The class to reference
	 * @param joinTable The join table
	 * @return The field
	 */
	private FieldMetadata generateJoinTableRefField(EntityMetadata referencedClass,
			EntityMetadata joinTable) {
		
		FieldMetadata mappingField;
		String mappingFieldName = referencedClass.getName() + "Id";
		
		if (joinTable.getFields().containsKey(mappingFieldName)) {
			// If field already exists
			mappingField = joinTable.getFields().get(mappingFieldName);
		} else {
			// Else create field
			mappingField = new FieldMetadata(joinTable);
			mappingField.setRelation(new RelationMetadata());
			joinTable.getFields().put(mappingFieldName, mappingField);
			joinTable.getRelations().put(mappingFieldName, mappingField);

			mappingField.setName(mappingFieldName);
			mappingField.setColumnName(mappingFieldName);
			mappingField.setType(TYPE_INTEGER);
			mappingField.setColumnDefinition(TYPE_INTEGER);
			mappingField.setHarmonyType(TYPE_INTEGER);
			mappingField.getRelation().setEntityRef(referencedClass);
			mappingField.getRelation().setType("ManyToOne");
		}
		
		return mappingField;
	}
}
	
