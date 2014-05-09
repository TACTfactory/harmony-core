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
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.harmony.parser.JavaModelParser;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.ColumnResult;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.JoinColumn;
import com.tactfactory.harmony.annotation.ManyToMany;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.OneToOne;
import com.tactfactory.harmony.annotation.OrderBys;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumTypeMetadata;
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
	
	/** Id annotation name. */
	private static final String FILTER_GENERATED_VALUE	 		=
			PackageUtils.extractNameEntity(GeneratedValue.class);

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
	
	/** ColumnResult annotation name. */
	private static final String FILTER_COLUMNRESULT 	=
			PackageUtils.extractNameEntity(ColumnResult.class);

	/** OrderBys annotation. */
	private static final String ANNOTATION_ORDER_BYS=
			PackageUtils.extractNameEntity(OrderBys.class);

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

	/** Column annotation strategy attribute. */
	private static final String ATTRIBUTE_STRATEGY = "strategy";

	/** Column annotation column name attribute. */
	private static final String ATTRIBUTE_COLUMN_NAME = "columnName";
	
	/** Column annotation order attribute. */
	private static final String ATTRIBUTE_ORDER = "order";

	/** Column annotation columnDefinition attribute. */
	private static final String ATTRIBUTE_COLUMN_DEFINITION =
			"columnDefinition";

	/** Relations annotation mappedBy attribute. */
	private static final String ATTRIBUTE_MAPPED_BY = "mappedBy";

	/** Relations annotation inversedBy attribute. */
	private static final String ATTRIBUTE_INVERSED_BY = "inversedBy";
	
	/** The project entities metadata. */
	private Map<String, EntityMetadata> projectEntities = 
			ApplicationMetadata.INSTANCE.getEntities();
	
	/** The project classes metadata. */
	private Map<String, ClassMetadata> projectClasses = 
			ApplicationMetadata.INSTANCE.getClasses();
	
	/** Annotation map for this field. */
	private Map<String, AnnotationExpr> annotationMap;
	
	/**
	 * Visit a field declaration to extract metadata.
	 * @param field The field declaration.
	 * @param classMeta The ClassMetada of the field's class
	 * @return The FieldMetadata extracted
	 */
	public final FieldMetadata visit(final FieldDeclaration field,
			final ClassMetadata classMeta) {
		FieldMetadata result = null;
		
		this.annotationMap = this.getAnnotMap(field);
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
			
			String javaType = field.getType().toString();
			
			result.setStatic(ModifierSet.isStatic(field.getModifiers()));

			if (Type.fromString(javaType) != null) {
				result.setHarmonyType(Type.fromString(field.getType().toString()).getValue());
			} else {
				if (javaType.equals("Integer")) {
					result.setHarmonyType(Type.INT.getValue());
				} else if (javaType.equals("Character")) {
					result.setHarmonyType(Type.CHAR.getValue());
				} else {
					result.setHarmonyType(javaType);
				}
			}
			// Java types Date and Time are deprecated in Harmony
			if (javaType.equalsIgnoreCase("date")
					|| javaType.equalsIgnoreCase("time")) {
				ConsoleUtils.displayWarning(
						"You should use DateTime java type instead of "
						+ javaType
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
					PackageUtils.extractClassNameFromArray(javaType);
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
					result.setHarmonyType(Type.RELATION.getValue());
				}
				
				if (annotationType.equals(FILTER_COLUMNRESULT)) {
					result.setColumnResult(true);
				}
				
				if (annotationType.equals(FILTER_GENERATED_VALUE)) {
					Strategy strategy = Strategy.MODE_NONE;
					if (annotationExpr instanceof NormalAnnotationExpr) {
						List<MemberValuePair> pairs = 
								((NormalAnnotationExpr) annotationExpr)
										.getPairs();
						if (pairs != null) {
							for (MemberValuePair pair : pairs) {
								if (pair.getName().equals(ATTRIBUTE_STRATEGY)) {
									String strategyName;
									if (pair.getValue()
											instanceof StringLiteralExpr) {
										strategyName = ((StringLiteralExpr)
												pair.getValue()).getValue();
									} else {
										strategyName = 
												pair.getValue().toString();
									}
									
									strategyName = strategyName.substring(
											strategyName.indexOf('.') + 1);
									strategy = Strategy.valueOf(strategyName);
								}
							}
						}
					}
					result.setStrategy(strategy);
				}

				this.loadAttributes(rel,
						result,
						annotationExpr,
						annotationType);
				
				if (Type.ENUM.getValue().equals(result.getHarmonyType())) {
					EnumTypeMetadata enumMeta = new EnumTypeMetadata();
					enumMeta.setTargetEnum(field.getType().toString());
					result.setEnumMeta(enumMeta);
				}
				
				if (field.getType().toString().matches("^[a-z].*")) {
					result.setPrimitive(true);
				}
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
					this.createJoinTable(result);
				}
				
				if (rel.getType().equals("OneToMany") ||
						rel.getType().equals("ManyToMany")) {
					this.parseOrderBysAnnotation(rel);
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
			SqliteAdapter.Keywords.exists(javaType);
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
							if (rel.getEntityRef().getFields().containsKey(
									fieldName)) {
								fieldRef = 
										rel.getEntityRef().getFields().get(
												fieldName);
							} else {
								RelationMetadata inversingRel = 
										new RelationMetadata();
								inversingRel.setType("ManyToOne");
								fieldRef = new FieldMetadata(rel.getEntityRef());
								fieldRef.setName(fieldName);
								fieldRef.setRelation(inversingRel);
								fieldRef.setHarmonyType(Column.Type.RELATION.getValue());
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
								if (rel.getEntityRef().getFields().containsKey(
										fieldName)) {
									fieldRef = rel.getEntityRef().getFields().get(
											fieldName);
								} else {
									RelationMetadata inversingRel = 
											new RelationMetadata();
									inversingRel.setMappedBy(result);
									inversingRel.setType("OneToMany");
									fieldRef = new FieldMetadata(rel.getEntityRef());
									fieldRef.setName(fieldName);
									fieldRef.setRelation(inversingRel);
									fieldRef.setInternal(true);
									fieldRef.setHarmonyType(Column.Type.RELATION.getValue());
								}
								rel.setInversedBy(fieldRef);
							}
						}else

						if (annotationType.equals(FILTER_MANY2MANY)) {
							if (mvp.getName().equals(ATTRIBUTE_INVERSED_BY)
									|| mvp.getName().equals(ATTRIBUTE_MAPPED_BY)) {
								String fieldName = ((StringLiteralExpr)
										mvp.getValue()).getValue();
								FieldMetadata fieldRef;
								if (rel.getEntityRef().getFields().containsKey(
										fieldName)) {
									fieldRef = rel.getEntityRef().getFields().get(
											fieldName);
								} else {
									RelationMetadata inversingRel = 
											new RelationMetadata();
									inversingRel.setMappedBy(result);
									inversingRel.setInversedBy(result);
									inversingRel.setType("ManyToMany");
									fieldRef = new FieldMetadata(rel.getEntityRef());
									fieldRef.setName(fieldName);
									fieldRef.setRelation(inversingRel);
									fieldRef.setInternal(true);
									fieldRef.setHarmonyType(Column.Type.RELATION.getValue());
								}
								rel.setInversedBy(fieldRef);
								rel.setMappedBy(fieldRef);
							}
						} else
							
						if (annotationType.equals(FILTER_COLUMNRESULT)) {
							if (mvp.getName().equals(ATTRIBUTE_COLUMN_NAME)) {
								String command = ((StringLiteralExpr)
										mvp.getValue()).getValue();
								result.setColumnName(command);
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
			|| annotationType.equals(FILTER_JOINCOLUMN)
			|| annotationType.equals(FILTER_COLUMNRESULT)) {

			isColumn = true;

			// Debug Log
			String type = "Column";
			if (annotationType.equals(FILTER_JOINCOLUMN)) {
				type = "Join Column";
			}

			ConsoleUtils.displayDebug(
					"\t" + type + " : " + result.getName()
					+ " type of " + result.getHarmonyType());
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
					+ result.getHarmonyType());
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
		newField.setNullable(true);
		newField.setInternal(true);
		newField.setName(
				currentField.getOwner().getName() 
				+ currentField.getName() 
				+ "Internal");
		
		newField.setColumnName(
				currentField.getOwner().getName() 
				+ "_" + currentField.getName() 
				+ "_internal");
		newField.setHarmonyType(Column.Type.RELATION.getValue());
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
				"Association Table => ", rel.getJoinTable());
		
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
		
		// Create or retrieve inversing field in the joinTable
		FieldMetadata invertField = currentField.getRelation().getMappedBy();
		if (invertField == null) {
			invertField = currentField.getRelation().getInversedBy();
		}
		String invertFieldName;
		if (invertField != null) {
			invertFieldName = invertField.getName();
		} else {
			invertFieldName = currentField.getOwner().getName() + "InternalId";
		}
		
		FieldMetadata joinTableInvertField = 
				joinTable.getFields().get(invertFieldName);
		
		if (joinTableInvertField == null) {
			joinTableInvertField = new FieldMetadata(joinTable);
			joinTableInvertField.setName(invertFieldName);
			joinTable.getIds().put(invertFieldName, joinTableInvertField);
			joinTable.getRelations().put(invertFieldName, joinTableInvertField);
			joinTable.getFields().put(invertFieldName, joinTableInvertField);
			joinTableInvertField.setColumnName(invertFieldName);
			joinTableInvertField.setColumnDefinition(TYPE_INTEGER);
			joinTableInvertField.setHarmonyType(Column.Type.RELATION.getValue());
			joinTableInvertField.setRelation(new RelationMetadata());
			joinTableInvertField.getRelation().setEntityRef(
					(EntityMetadata) currentField.getOwner());
			joinTableInvertField.getRelation().setType("ManyToOne");
		}
		
		currentField.getRelation().setMappedBy(joinTableInvertField);
		
		// Create or retrieve associated field in the joinTable
		FieldMetadata joinTableAssociatedField = 
				joinTable.getFields().get(currentField.getName());
		
		if (joinTableAssociatedField == null) {
			joinTableAssociatedField = new FieldMetadata(joinTable);
			joinTableAssociatedField.setName(currentField.getName());
			joinTable.getIds().put(currentField.getName(), joinTableAssociatedField);
			joinTable.getRelations().put(currentField.getName(), joinTableAssociatedField);
			joinTable.getFields().put(currentField.getName(), joinTableAssociatedField);
			joinTableAssociatedField.setColumnName(currentField.getName());
			joinTableAssociatedField.setColumnDefinition(TYPE_INTEGER);
			joinTableAssociatedField.setHarmonyType(Column.Type.RELATION.getValue());
			joinTableAssociatedField.setRelation(new RelationMetadata());
			joinTableAssociatedField.getRelation().setEntityRef(
					(EntityMetadata) currentField.getRelation().getEntityRef());
			joinTableAssociatedField.getRelation().setType("ManyToOne");
		}
	}
	
    /**
     * Transform the class declaration to an annotation map
     * @param classDecl The class declaration
     * @return A map <Annotation name, annotation>
     */
    private Map<String, AnnotationExpr> getAnnotMap(
    		FieldDeclaration fieldDecl) {
    	
    	Map<String, AnnotationExpr> result = 
    			new HashMap<String, AnnotationExpr>();
    	if (fieldDecl.getAnnotations() != null) {
	    	for (AnnotationExpr annot : fieldDecl.getAnnotations()) {
	    		result.put(annot.getName().toString(), annot);
	    	}
    	}
    	
    	return result;
    }    
    /**
     * Parse the indexes array.
     * 
     * @param classMeta The class to complete
     * @param indexesArray The indexes array
     */
    private void parseOrderByArray(RelationMetadata relationMeta,
    		ArrayInitializerExpr orderByArray) {
    	for (Expression orderByAnnot : orderByArray.getValues()) {
			if (orderByAnnot instanceof AnnotationExpr) {
				this.parseOrderByAnnotation(
						relationMeta, 
						(AnnotationExpr) orderByAnnot);
			}
		}
    }
    
    /**
     * Parse an index annotation and put it into the metadata of the class.
     * 
     * @param classMeta The class metadata to complete
     * @param indexAnnot The index annotation
     */
    private void parseOrderByAnnotation(RelationMetadata relationMeta, 
    		AnnotationExpr orderByAnnot) {
    	String columnName = null;
		String order = null;
		List<MemberValuePair> indexPairs = 
						((NormalAnnotationExpr) orderByAnnot).getPairs();
		for (MemberValuePair indexPair : indexPairs) {
			if (ATTRIBUTE_COLUMN_NAME.equals(indexPair.getName())) {
				columnName = 
						((StringLiteralExpr) indexPair.getValue()).getValue();
			} else if (ATTRIBUTE_ORDER.equals(indexPair.getName())) {
				order = 
						((StringLiteralExpr) indexPair.getValue()).getValue();
			}
		}	
		if (columnName != null) {
			relationMeta.addOrder(columnName, order);
		}
    }
    
    /**
     * Parse the table annotation.
     * 
     * @param classMeta The class to complete
     */
    private void parseOrderBysAnnotation(RelationMetadata relationMeta) {
    	AnnotationExpr orderBysAnnot = 
    			this.annotationMap.get(ANNOTATION_ORDER_BYS);
    	if (orderBysAnnot != null) {
    		if (orderBysAnnot instanceof SingleMemberAnnotationExpr) {
    			Expression memberValue = ((SingleMemberAnnotationExpr)
    						orderBysAnnot).getMemberValue();
				this.parseOrderByArray(
						relationMeta,
						(ArrayInitializerExpr) memberValue);

    		}
    	}
    }
}
	
