/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser.java.visitor;

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.InterfaceMetadata;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.DiscriminatorColumn;
import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;
import com.tactfactory.harmony.annotation.Crud;
import com.tactfactory.harmony.annotation.DiscriminatorIdentifier;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.InheritanceType;
import com.tactfactory.harmony.annotation.OrderBys;
import com.tactfactory.harmony.annotation.Table;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.InheritanceMetadata;
import com.tactfactory.harmony.parser.BaseParser;
import com.tactfactory.harmony.parser.java.JavaModelParser;
import com.tactfactory.harmony.plateforme.SqliteAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

/**
 * JavaParser Class Visitor.
 */
public class ClassVisitor {
	/** Entity annotation name. */
	private static final String ANNOTATION_ENTITY	 	=
			PackageUtils.extractNameEntity(Entity.class);

	private static final String ANNOTATION_CRUD =
			PackageUtils.extractNameEntity(Crud.class);

	private static final String ANNOTATION_TABLE 		=
			PackageUtils.extractNameEntity(Table.class);

	private static final String ANNOTATION_INHERITANCE_TYPE	=
			PackageUtils.extractNameEntity(InheritanceType.class);

	private static final String ANNOTATION_DISCRIMINATOR_COLUMN =
			PackageUtils.extractNameEntity(DiscriminatorColumn.class);

	private static final String ANNOTATION_DISCRIMINATOR_IDENTIFIER =
			PackageUtils.extractNameEntity(DiscriminatorIdentifier.class);
	
	private static final String ANNOTATION_ORDER_BYS=
			PackageUtils.extractNameEntity(OrderBys.class);
	
	/** Column annotation hidden attribute. */
	private static final String ATTRIBUTE_HIDDEN = "hidden";
	
	/** Column annotation hidden attribute. */
	private static final String ATTRIBUTE_VALUE = "value";
	
	/** Column annotation name attribute. */
	private static final String ATTRIBUTE_NAME = "name";
	
	/** Column annotation column name attribute. */
	private static final String ATTRIBUTE_COLUMN_NAME = "columnName";
	
	/** Column annotation order attribute. */
	private static final String ATTRIBUTE_ORDER = "order";
	
	/** Column annotation indexes attribute. */
	private static final String ATTRIBUTE_INDEXES = "indexes";
	
	/** Column annotation columns attribute. */
	private static final String ATTRIBUTE_COLUMNS = "columns";
	
	/** Column annotation type attribute. */
	private static final String ATTRIBUTE_TYPE = "type";

	/** The field visitor used by this visitor. */
	private final FieldVisitor fieldVisitor = new FieldVisitor();

	/** The method visitor used by this visitor. */
	private final MethodVisitor methodVisitor = new MethodVisitor();
	
	/** The constructor visitor used by this visitor. */
	private final ConstructorVisitor constructorVisitor = 
			new ConstructorVisitor();
	
	/** Annotation map for this class. */
	private Map<String, AnnotationExpr> annotationMap;

	/**
	 * Visit a class.
	 * @param classDeclaration The class or interface declaration
	 * @return The ClassMetadata containing the metadata of the visited class
	 */
    public final ClassMetadata visit(
    		final ClassOrInterfaceDeclaration classDeclaration) {
    	ClassMetadata result;

    	boolean isEntity = false;
    	boolean isInterface = false;
    	
    	String classname = PackageUtils.extractNameEntity(
    			classDeclaration.getName());
    	
    	if (ApplicationMetadata.INSTANCE.getClasses().containsKey(classname)) {
    		result = ApplicationMetadata.INSTANCE.getClasses().get(classname);
    	} else {
	    	// Detect whether we have an interface or an entity
	    	if (classDeclaration.isInterface()) {
	    		result = new InterfaceMetadata();
	    		isInterface = true;
	    	} else {
	    		result = new EntityMetadata();
	    	}
	    	result.setName(PackageUtils.extractNameEntity(
	    			classDeclaration.getName()));
    	}
    	
    	result.setParsed(true);
    	
    	// Debug Log
    	ConsoleUtils.displayDebug("Found class : " + result.getName());
    	// Check reserved keywords
		SqliteAdapter.Keywords.exists(result.getName());
		
		// If the class has a name
		if (!Strings.isNullOrEmpty(result.getName())) {
			
	    	// Call the parsers which have been registered by the bundle
	    	for (final BaseParser bParser
	    			: JavaModelParser.getBundleParsers()) {
	    		
	    		bParser.visitClass(classDeclaration, result);
	    	}
	    	
	    	// Parse the annotations
	    	this.annotationMap = this.getAnnotMap(classDeclaration);
	    	
	    	// TODO : adapt bundles code !
			// Call the bundles class annotations parsers
	    	if (classDeclaration.getAnnotations() != null) {
		    	for (AnnotationExpr annotationExpr 
		    			: classDeclaration.getAnnotations()) {
		    		
					for (final BaseParser bParser
							: JavaModelParser.getBundleParsers()) {
						
			    		bParser.visitClassAnnotation(result, annotationExpr);
					}
		    	}
	    	}
	    	
	    	AnnotationExpr entityAnnot = 
	    			this.annotationMap.get(ANNOTATION_ENTITY);
	    	
	    	if (entityAnnot != null) {
	    		isEntity = true;
	    	}

	    	AnnotationExpr crudAnnot = 
	    			this.annotationMap.get(ANNOTATION_CRUD);
	    	
	    	if (crudAnnot != null) {
				
				if (crudAnnot instanceof NormalAnnotationExpr) {
					List<MemberValuePair> pairs = 
							((NormalAnnotationExpr)
									crudAnnot).getPairs();
					
					if (pairs != null) {	
						for (MemberValuePair pair : pairs) {
							if (ATTRIBUTE_HIDDEN.equals(pair.getName())) {
								
								((EntityMetadata) result).setHidden(
										pair.getValue().toString()
										.equals(String.valueOf(true)));
							}
						}
					}
				}
	    	}
	    	
	    	if (isEntity) {
	    		this.loadInheritanceData(
	    				(EntityMetadata) result, 
	    				classDeclaration);
	    		this.parseTableAnnotation((EntityMetadata) result);
	    		this.parseOrderBysAnnotation((EntityMetadata) result);
	    	}
	    	
	    	
			// Get list of Implement type
			final List<ClassOrInterfaceType> impls =
					classDeclaration.getImplements();
			if (impls != null) {
				for (final ClassOrInterfaceType impl : impls) {
					result.getImplementTypes().add(impl.getName());
					
					// Debug Log
					ConsoleUtils.displayDebug(
							"\tImplement : " + impl.getName());
				}
			}

			// Get list of Members (Methods, Fields, Subclasses, Enums, etc.)
			final Map<String, ClassMetadata> subClasses =
					new LinkedHashMap<String, ClassMetadata>();
			final List<BodyDeclaration> members =
					classDeclaration.getMembers();
			
			if (members != null) {
				for (final BodyDeclaration member : members) {
					// Subclass or Enum
					if (member instanceof TypeDeclaration) {
						final ClassMetadata subClass =
								this.visit((TypeDeclaration) member);
						
						subClasses.put(subClass.getName(), subClass);
					} else

					// Fields
					if (member instanceof FieldDeclaration) {
						final FieldMetadata fieldMetas =
								this.fieldVisitor.visit(
										(FieldDeclaration) member,
										result);
						if (fieldMetas != null) {
							if (fieldMetas.isId()) {
								((EntityMetadata) result).addId(fieldMetas);
							}
							
							if (fieldMetas.getRelation() != null) {
								((EntityMetadata) result).getRelations().put(
										fieldMetas.getName(),
										fieldMetas);
							}
						}
					} else
						
					// Methods
					if (member instanceof MethodDeclaration) {
						result.getMethods().add(this.methodVisitor.visit(
										(MethodDeclaration) member,
										result));
					} else
						
					// Constructors
					if (member instanceof ConstructorDeclaration) {
						result.getMethods().add(this.constructorVisitor.visit(
										(ConstructorDeclaration) member,
										result));
					}
				}
				
				for (final ClassMetadata subClass : subClasses.values()) {
					subClass.setOuterClass(result.getName());
				}
				result.setInnerClasses(subClasses);
			}
		}

		if (!isEntity && !isInterface) {
			result = null;
		}
		return result;
    }

    /**
	 * Visit an enum.
	 * @param enumDecl The enum declaration
	 * @return The ClassMetadata containing the metadata of the visited enum
	 */
    public final ClassMetadata visit(final EnumDeclaration enumDecl) {
    	ConsoleUtils.displayDebug("Found enum ", enumDecl.getName());
    	final EnumMetadata result = new EnumMetadata();
    	result.setName(enumDecl.getName());
    	// Check reserved keywords
		SqliteAdapter.Keywords.exists(result.getName());

    	// Get the Enum entries
    	if (enumDecl.getEntries() != null) {
    		for (final EnumConstantDeclaration enumEntry
    				: enumDecl.getEntries()) {
    			result.getEntries().add(enumEntry.getName());
    		}
    	}

    	// Get list of Members (Methods, Fields, Subclasses, Enums, etc.)
		final List<BodyDeclaration> members = enumDecl.getMembers();
		if (members != null) {
			for (final BodyDeclaration member : members) {
				// Subclass or Enum
				if (member instanceof TypeDeclaration) {
					this.visit((TypeDeclaration) member);
				} else
					
				// Fields
				if (member instanceof FieldDeclaration) {
					final FieldMetadata fieldMetas = this.fieldVisitor.visit(
							(FieldDeclaration) member,
							result);
					if (fieldMetas != null) {
						if (fieldMetas.isId()) {
							result.setIdName(fieldMetas.getName());
						}
					}
				} else
					
				// Methods
				if (member instanceof MethodDeclaration) {
					result.getMethods().add(this.methodVisitor.visit(
							(MethodDeclaration) member,
							result));
				}
			}
		}

		if (!Strings.isNullOrEmpty(result.getIdName())) {
			result.setType(
					result.getFields().get(result.getIdName()).getType());
		} else {
			result.setType(Type.STRING.getValue());
		}
    	return result;
    }

    /**
	 * Visit a class.
	 * @param type The type declaration
	 * @return The ClassMetadata containing the metadata of the visited class
	 */
    public final ClassMetadata visit(final TypeDeclaration type) {
    	ClassMetadata result;

    	if (type instanceof ClassOrInterfaceDeclaration) {
    		result = this.visit((ClassOrInterfaceDeclaration) type);
    	} else if (type instanceof EnumDeclaration) {
    		result = this.visit((EnumDeclaration) type);
    	} else {
    		result = null;
    	}

    	return result;
    }
    
    /**
     * Transform the class declaration to an annotation map
     * @param classDecl The class declaration
     * @return A map <Annotation name, annotation>
     */
    private Map<String, AnnotationExpr> getAnnotMap(
    		ClassOrInterfaceDeclaration classDecl) {
    	
    	Map<String, AnnotationExpr> result = 
    			new HashMap<String, AnnotationExpr>();
    	
    	if (classDecl.getAnnotations() != null) {
	    	for (AnnotationExpr annot : classDecl.getAnnotations()) {
	    		result.put(annot.getName().toString(), annot);
	    	}
    	}
    	
    	return result;
    }
    
    /**
     * Loads the inheritance data for the given entity.
     * @param classMeta The metadata of the entity
     * @param classDecl The class declaration
     */
    private void loadInheritanceData(EntityMetadata classMeta,
    		ClassOrInterfaceDeclaration classDecl) {
    	AnnotationExpr inheritanceTypeAnnot =
    			this.annotationMap.get(ANNOTATION_INHERITANCE_TYPE);
    	AnnotationExpr discriminatorAnnot =
    			this.annotationMap.get(ANNOTATION_DISCRIMINATOR_COLUMN);
    	AnnotationExpr discriminatorIdentifier =
    			this.annotationMap.get(ANNOTATION_DISCRIMINATOR_IDENTIFIER);
    	
    	boolean inherits = false;
    	InheritanceMetadata inheritanceMeta;
    	if (classMeta.getInheritance() != null) {
    		inheritanceMeta = classMeta.getInheritance();
    		inherits = true;
    	} else {
    		inheritanceMeta = new InheritanceMetadata();
    	}
    	
    	if (classDecl.getExtends() != null) {
    		inherits = true;
    		// TODO : check if extends is a entity ?
    		InheritanceMode mode = null;
    		String superClassName = classDecl.getExtends().get(0).getName();
    		
    		EntityMetadata superclass;
    		if (ApplicationMetadata.INSTANCE.getEntities().containsKey(
    				superClassName)) {
    			 superclass = 
    					 ApplicationMetadata.INSTANCE.getEntities().get(
    							 superClassName); 
    			 
    			if (superclass.getInheritance() == null) {
     				superclass.setInheritance(new InheritanceMetadata());
     				
     				superclass.getInheritance().getSubclasses().put(
     						classMeta.getName(),
     						classMeta);
     			} else {
     				mode = superclass.getInheritance().getType();
     				
     				superclass.getInheritance().getSubclasses().put(
     						classMeta.getName(),
     						classMeta);
     			}
    			
    		} else {
    			superclass = new EntityMetadata();
    			superclass.setName(superClassName);
    			
    			if (superclass.getInheritance() == null) {
    				superclass.setInheritance(new InheritanceMetadata());
    			}
    			
    			superclass.getInheritance().getSubclasses().put(
    					classMeta.getName(),
    					classMeta);
    			
    			ApplicationMetadata.INSTANCE.getEntities().put(
    					superClassName, superclass);
    		}

    		inheritanceMeta.setSuperclass(superclass);
    		if (mode != null) {
    			inheritanceMeta.setType(mode);
    		}
    		
    		String discriminatorIdentifierValue = classMeta.getName();
    		if (discriminatorIdentifier instanceof NormalAnnotationExpr) {
				List<MemberValuePair> pairs = 
						((NormalAnnotationExpr) discriminatorIdentifier)
								.getPairs();
				
				for (MemberValuePair pair : pairs) {
					if (ATTRIBUTE_VALUE.equals(pair.getName())) {
						discriminatorIdentifierValue = 
								pair.getValue().toString();
					}
				}
			} else if (discriminatorIdentifier 
					instanceof SingleMemberAnnotationExpr){
				SingleMemberAnnotationExpr annot = 
						(SingleMemberAnnotationExpr) discriminatorIdentifier;
				discriminatorIdentifierValue = 
						((FieldAccessExpr) annot.getMemberValue()).getField();
			}
    		inheritanceMeta.setDiscriminorIdentifier(
    				discriminatorIdentifierValue);
    	} 
    	
    	if (inheritanceTypeAnnot != null) {
    		inherits = true;
    		// Get mode
    		InheritanceMode mode = null;
    		String modeName = null;
    		if (inheritanceTypeAnnot instanceof NormalAnnotationExpr) {
				List<MemberValuePair> pairs = 
						((NormalAnnotationExpr) inheritanceTypeAnnot)
								.getPairs();
				
				for (MemberValuePair pair : pairs) {
					if (ATTRIBUTE_VALUE.equals(pair.getName())) {
						modeName = pair.getValue().toString();
					}
				}
			} else if (inheritanceTypeAnnot 
					instanceof SingleMemberAnnotationExpr){
				SingleMemberAnnotationExpr annot = 
						(SingleMemberAnnotationExpr) inheritanceTypeAnnot;
				modeName = 
						((FieldAccessExpr) annot.getMemberValue()).getField();
			}
    		
    		if (modeName != null) {
    			mode = InheritanceMode.valueOf(modeName);
    		} else {
    			mode = InheritanceMode.JOINED;
    		}

    		// Propagate type to subclasses
    		inheritanceMeta.setType(mode);
    		

			String type = "VARCHAR";
			String columnName = "inheritance_type";
			String name = "discriminatorColumn";
			
    		if (discriminatorAnnot != null) {
    			if (discriminatorAnnot instanceof NormalAnnotationExpr) {
    				
    				List<MemberValuePair> pairs = 
    						((NormalAnnotationExpr) discriminatorAnnot)
    								.getPairs();
    				
    				for (MemberValuePair pair : pairs) {
    					if (ATTRIBUTE_NAME.equals(pair.getName())) {
    						columnName = ((StringLiteralExpr) 
    										pair.getValue()).getValue();
    					} else 
    						
    					if (ATTRIBUTE_TYPE.equals(pair.getName())) {
    						type = ((StringLiteralExpr) 
    								pair.getValue()).getValue();
    					}
    				}
    				
    			}
    		} 
    		
			FieldMetadata discriminatorColumn = 
					new FieldMetadata(classMeta);
			discriminatorColumn.setType(type);
			discriminatorColumn.setColumnDefinition(type);
			discriminatorColumn.setColumnName(columnName);
			discriminatorColumn.setName(name);
			discriminatorColumn.setNullable(true);
			inheritanceMeta.setDiscriminorColumn(discriminatorColumn);
    	}
    	
    	if (inherits) {
    		classMeta.setInheritance(inheritanceMeta);
    		this.propagateModeToSubclasses(classMeta);
    	}
    }
    
    /** 
     * Propagates the inheritance mode to the subclasses. 
     *
     * @param classMeta the entity to propagate the mode.
     */
    private void propagateModeToSubclasses(EntityMetadata classMeta) {
		// Propagate type to subclasses
		for (EntityMetadata subclass 
				: classMeta.getInheritance().getSubclasses().values()) {
			
			subclass.getInheritance().setType(
					classMeta.getInheritance().getType());
			
			this.propagateModeToSubclasses(subclass);
		}
    }
    

    /**
     * Parse the table annotation.
     * 
     * @param classMeta The class to complete
     */
    private void parseOrderBysAnnotation(EntityMetadata classMeta) {
    	AnnotationExpr orderBysAnnot = 
    			this.annotationMap.get(ANNOTATION_ORDER_BYS);
    	
    	if (orderBysAnnot != null) {
    		if (orderBysAnnot instanceof SingleMemberAnnotationExpr) {
    			Expression memberValue = 
    					((SingleMemberAnnotationExpr) 
    							orderBysAnnot).getMemberValue();
    			
				this.parseOrderByArray(
						classMeta,
						(ArrayInitializerExpr) memberValue);

    		}
    	}
    }
    
    /**
     * Parse the table annotation.
     * 
     * @param classMeta The class to complete
     */
    private void parseTableAnnotation(EntityMetadata classMeta) {
    	AnnotationExpr tableAnnot = 
    			this.annotationMap.get(ANNOTATION_TABLE);
    	
    	if (tableAnnot != null) {
    		if (tableAnnot instanceof NormalAnnotationExpr) {
    			List<MemberValuePair> pairs = 
    					((NormalAnnotationExpr) tableAnnot).getPairs();
    			
    			if (pairs != null) {
	    			for (MemberValuePair pair : pairs) {
	    				if (ATTRIBUTE_INDEXES.equals(pair.getName())) {
	    					this.parseIndexes(
	    							classMeta,
	    							(ArrayInitializerExpr) pair.getValue());
	    					
	    				}
	    			}
    			}
    		}
    	}
    }
    
    /**
     * Parse the indexes array.
     * 
     * @param classMeta The class to complete
     * @param indexesArray The indexes array
     */
    private void parseIndexes(EntityMetadata classMeta,
    		ArrayInitializerExpr indexesArray) {
		for (Expression indexExpr : indexesArray.getValues()) {
			if (indexExpr instanceof AnnotationExpr) {
				this.parseIndexAnnotation(
						classMeta, 
						(AnnotationExpr) indexExpr);
			}
		}
    }
    
    /**
     * Parse an index annotation and put it into the metadata of the class.
     * 
     * @param classMeta The class metadata to complete
     * @param indexAnnot The index annotation
     */
    private void parseIndexAnnotation(EntityMetadata classMeta, 
    		AnnotationExpr indexAnnot) {
    	String indexName = null;
		ArrayList<String> columns = new ArrayList<String>();
		List<MemberValuePair> indexPairs = 
						((NormalAnnotationExpr) indexAnnot).getPairs();
		
		for (MemberValuePair indexPair : indexPairs) {
			if (ATTRIBUTE_NAME.equals(indexPair.getName())) {
				indexName = 
						((StringLiteralExpr) indexPair.getValue()).getValue();
			} else 
				
			if (ATTRIBUTE_COLUMNS.equals(indexPair.getName())) {
				ArrayInitializerExpr colArray = 
						((ArrayInitializerExpr) indexPair.getValue());
				
				for (Expression columnExpr : colArray.getValues()) {
					columns.add(((StringLiteralExpr) columnExpr).getValue());
				}
			}
		}
		
		if (indexName != null) {
			classMeta.addIndex(indexName, columns);
		}
    }
    
    /**
     * Parse the indexes array.
     * 
     * @param classMeta The class to complete
     * @param indexesArray The indexes array
     */
    private void parseOrderByArray(EntityMetadata classMeta,
    		ArrayInitializerExpr orderByArray) {
    	for (Expression orderByAnnot : orderByArray.getValues()) {
			if (orderByAnnot instanceof AnnotationExpr) {
				this.parseOrderByAnnotation(
						classMeta, 
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
    private void parseOrderByAnnotation(EntityMetadata classMeta, 
    		AnnotationExpr orderByAnnot) {
    	String columnName = null;
		String order = null;
		List<MemberValuePair> indexPairs = 
						((NormalAnnotationExpr) orderByAnnot).getPairs();
		
		for (MemberValuePair indexPair : indexPairs) {
			if (ATTRIBUTE_COLUMN_NAME.equals(indexPair.getName())) {
				columnName = 
						((StringLiteralExpr) indexPair.getValue()).getValue();
			} else 
			
			if (ATTRIBUTE_ORDER.equals(indexPair.getName())) {
				order = 
						((StringLiteralExpr) indexPair.getValue()).getValue();
			}
		}
		
		if (columnName != null) {
			classMeta.addOrder(columnName, order);
		}
    }
}
