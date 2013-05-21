package com.tactfactory.mda.parser.java.visitor;

import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.JoinColumn;
import com.tactfactory.mda.annotation.ManyToMany;
import com.tactfactory.mda.annotation.ManyToOne;
import com.tactfactory.mda.annotation.OneToMany;
import com.tactfactory.mda.annotation.OneToOne;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.RelationMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.parser.java.JavaModelParser;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * JavaParser Field Visitor.
 */
public class FieldVisitor {
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
	
	
	
	public final FieldMetadata visit(final FieldDeclaration field, 
			final ClassMetadata classMeta) {
		FieldMetadata result = null;
    	// Call the parsers which have been registered by the bundle
    	for (final BaseParser bParser 
    			: JavaModelParser.bundleParsers) {
    		bParser.visitField(field, classMeta);
    	}
		
		final List<AnnotationExpr> fieldAnnotations 
				= field.getAnnotations();
		
		if (fieldAnnotations != null) {
			// General (required !)
			result = new FieldMetadata(classMeta);
		
			/*result.setType(
					Type.toTypeString(field.getType().toString()));*/
			result.setType(
					field.getType().toString());
			result.setHarmonyType(
					Type.toTypeString(field.getType().toString()));
			
			// Java types Date and Time are deprecated in Harmony
			if (result.getType().equalsIgnoreCase("date") 
					|| result.getType().equalsIgnoreCase("time")) {
				ConsoleUtils.displayWarning(
						"You should use DateTime java type instead of " 
						+ result.getType()
						+ ". Errors may occur.");
			}
			//result.isFinal = ModifierSet.isFinal(field.getModifiers());
			// FIXME not manage multi-variable
			result.setName(
					field.getVariables().get(0).getId().getName()); 
			result.setColumnName(result.getName());

			// Set defaults values
			result.setHidden(false);
							
			// Database definitions
			final RelationMetadata rel = new RelationMetadata();
			boolean isColumn = false;
			boolean isId = false;
			boolean isRelation = false;
			
			// Analyze
			for (final AnnotationExpr annotationExpr : fieldAnnotations) {
				final String annotationType = 
						annotationExpr.getName().toString();
				

		    	for (final BaseParser bParser 
		    			: JavaModelParser.bundleParsers) {
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
				
				// Adjust databases column definition
				if (Strings.isNullOrEmpty(
						result.getColumnDefinition())) {
					result.setColumnDefinition(
							SqliteAdapter.generateColumnType(
									result.getType()));
				}
				
				// Set default values for type if type is recognized
				final Type type = Type.fromName(
						result.getColumnDefinition());
				if (type != null) {
					result.setColumnDefinition(type.getValue());
					if (result.isNullable() == null) {
						result.setNullable(type.isNullable());
					}
					if (result.isUnique() == null) {
						result.setUnique(type.isUnique());
					}
					if (result.getLength() == null) {
						result.setLength(type.getLength());
					}
					if (result.getPrecision() == null) {
						result.setPrecision(type.getPrecision());
					}
					if (result.getScale() == null) {
						result.setScale(type.getScale());
					}
					if (result.isLocale() == null) {
						result.setIsLocale(type.isLocale());
					}
				} else {
					if (result.isNullable() == null) {
						result.setNullable(false);
					}
					if (result.isUnique() == null) {
						result.setUnique(false);
					}
					if (result.getLength() == null) {
						result.setLength(255);
					}
					if (result.getPrecision() == null) {
						result.setPrecision(255);
					}
					if (result.getScale() == null) {
						result.setScale(255);
					}
					if (result.isLocale() == null) {
						result.setIsLocale(false);
					}
				}
			}
			
			// ID relation
			if (isId) {
				result.setId(true);
				//meta.getIds().put(result.getName(), result);
			}

			// Object relation
			if (isRelation) {
				rel.setField(result.getName());
				rel.setEntityRef(PackageUtils.extractClassNameFromArray(
						field.getType().toString()));
				result.setRelation(rel);
				//meta.getRelations().put(result.getName(), result);
			}

			/*if (result.getType().equalsIgnoreCase("DateTime") 
					&& result.getColumnDefinition() != null) {
				result.setType(result.getColumnDefinition());
			}*/
			
			result.setColumnDefinition(
					SqliteAdapter.generateColumnType(
							result.getColumnDefinition()));
			
			// Add to meta dictionary
			if (isId || isColumn || isRelation) {
				classMeta.getFields().put(result.getName(), result);
			}
			
			// Check SQLite reserved keywords
			SqliteAdapter.Keywords.exists(result.getName());
			if (!result.getName().equals(result.getColumnName())) {
				SqliteAdapter.Keywords.exists(result.getColumnName());
			}
			SqliteAdapter.Keywords.exists(result.getColumnDefinition());
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
						if (mvp.getName().equals("nullable")  
								&& mvp.getValue().toString()
											.equals("true")) {
							result.setNullable(true);
						} else 
							
						// set name
						if (mvp.getName().equals("name")) {
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
						if (mvp.getName().equals("unique")  
								&& mvp.getValue().toString()
										.equals("true")) {
							result.setUnique(true);
						} else 
							
						// set length
						if (mvp.getName().equals("length")) {
							result.setLength(Integer.parseInt(
									mvp.getValue().toString()));
						} else 
							
						// set precision
						if (mvp.getName().equals("precision")) {
							result.setPrecision(Integer.parseInt(
									mvp.getValue().toString()));
						} else 
							
						// set scale
						if (mvp.getName().equals("scale")) {
							result.setScale(Integer.parseInt(
									mvp.getValue().toString()));
						} else
							
						// set scale
						if (mvp.getName().equals("locale")) {
							result.setIsLocale(Boolean.parseBoolean(
									mvp.getValue().toString()));
						} else 
							
						// set column definition
						if (mvp.getName().equals("type")) {
							//TODO : Generate warning if type not recognized
							String type = "";
							
							if (mvp.getValue() 
									instanceof StringLiteralExpr) {
								type = ((StringLiteralExpr) 
										mvp.getValue()).getValue();
							} else {
								type = mvp.getValue().toString();
							}
							result.setHarmonyType(Type.fromName(type).getValue());
							result.setColumnDefinition(
									Type.fromName(type).getValue());
						} else
							
						// set scale
						if (mvp.getName().equals("columnDefinition")) {
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
						if (mvp.getName().equals("hidden") 
								&& mvp.getValue().toString()
										.equals("true")) {
							result.setHidden(true);
						}
						
					} else
					
					if (annotationType.equals(FILTER_JOINCOLUMN)) {
						if (mvp.getName().equals("name")) {
							rel.setName(((StringLiteralExpr) 
									mvp.getValue()).getValue());
						}
					} else
						
					if (annotationType.equals(FILTER_ONE2MANY)) {
						if (mvp.getName().equals("mappedBy")) {
							rel.setMappedBy(((StringLiteralExpr) 
									mvp.getValue()).getValue());
						}
					} else
					
					if (annotationType.equals(FILTER_MANY2ONE)) {
						if (mvp.getName().equals("inversedBy")) {
							rel.setInversedBy(mvp.getValue().toString());
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
}
