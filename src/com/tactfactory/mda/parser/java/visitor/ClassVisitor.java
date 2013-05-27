package com.tactfactory.mda.parser.java.visitor;

import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.EntityMetadata;
import com.tactfactory.mda.meta.EnumMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.InterfaceMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.parser.java.JavaModelParser;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * JavaParser Class Visitor.
 */
public class ClassVisitor {
	/** Entity annotation name. */
	private static final String FILTER_ENTITY	 	= 
			PackageUtils.extractNameEntity(Entity.class);
	
	/** The field visitor used by this visitor. */
	private final FieldVisitor fieldVisitor = new FieldVisitor();
	
	/** The method visitor used by this visitor. */
	private final MethodVisitor methodVisitor = new MethodVisitor();
	
	/**
	 * Visit a class.
	 * @param n The class or interface declaration
	 * @return The ClassMetadata containing the metadata of the visited class
	 */
    public final ClassMetadata visit(final ClassOrInterfaceDeclaration n) {
    	ClassMetadata result;
    	
    	boolean isEntity = false;
    	boolean isInterface = false;
    	
    	// Detect whether we have an interface or an entity
    	if (n.isInterface()) {
    		result = new InterfaceMetadata();
    		isInterface = true;
    	} else {
    		result = new EntityMetadata();
    	}
    	
    	
    	// *** Get the common attributes ***
    	result.setName(PackageUtils.extractNameEntity(n.getName()));
    	// Debug Log
    	ConsoleUtils.displayDebug("Found class : " +  result.getName());
    	// Check reserved keywords
		SqliteAdapter.Keywords.exists(result.getName());
		
		
    	
		// If the class has a name
		if (!Strings.isNullOrEmpty(result.getName())) {
			
	    	// Call the parsers which have been registered by the bundle
	    	for (final BaseParser bParser 
	    			: JavaModelParser.getBundleParsers()) {
	    		
	    		bParser.visitClass(n, result);
	    	}
	    	
	    	
	    	// Parse the annotations
	    	final List<AnnotationExpr> classAnnotations = n.getAnnotations();
			if (classAnnotations != null) {
				for (final AnnotationExpr annotationExpr : classAnnotations) {
	
					// Call the bundles class annotations parsers 
					for (final BaseParser bParser 
							: JavaModelParser.getBundleParsers()) {
			    		bParser.visitClassAnnotation(result, annotationExpr);
					}
					
					// Get annotation Type
					final String annotationType = 
							annotationExpr.getName().toString();
					
					// Detect whether class is an entity 
					isEntity = isEntity || annotationType.equals(FILTER_ENTITY);
				}
			}
			
			// Get list of Implement type
			final List<ClassOrInterfaceType> impls = n.getImplements();
			if (impls != null) {
				for (final ClassOrInterfaceType impl : impls) {
					result.getImplementTypes().add(impl.getName());
					
					// Debug Log
					ConsoleUtils.displayDebug("\tImplement : " 
								+ impl.getName());
				}
			}
			
			// Get Extend type
			final List<ClassOrInterfaceType> exts = n.getExtends();
			if (exts != null) {
				for (final ClassOrInterfaceType ext : exts) {			
					result.setExtendType(ext.getName());		
					
					// Debug Log
					ConsoleUtils.displayDebug("\tExtend : " 
								+ ext.getName());
				}
			}
				
			// Get list of Members (Methods, Fields, Subclasses, Enums, etc.)
			Map<String, ClassMetadata> subClasses = 
					new LinkedHashMap<String, ClassMetadata>();
			List<BodyDeclaration> members = n.getMembers();
			if (members != null) {
				for (BodyDeclaration member : members) {
					// Subclass or Enum
					if (member instanceof TypeDeclaration) {
						ClassMetadata subClass = 
								this.visit((TypeDeclaration) member);
						subClasses.put(subClass.getName(), subClass);
					} else
					
					// Fields
					if (member instanceof FieldDeclaration) {
						FieldMetadata fieldMetas = this.fieldVisitor.visit(
										(FieldDeclaration) member,
										result);
						if (fieldMetas != null) {
							result.getFields().put(
									fieldMetas.getName(),
									fieldMetas);
							
							if (fieldMetas.isId()) {
								((EntityMetadata) result).getIds().put(
										fieldMetas.getName(),
										fieldMetas);
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
					}	
				}
				
				for (ClassMetadata subClass : subClasses.values()) {
					subClass.setMotherClass(result.getName());
				}
				result.setSubClasses(subClasses);
			}
		}
		
		if (!isEntity && !isInterface) {
			result = null;
		}
		return result;
    }
    
    /**
	 * Visit an enum.
	 * @param n The enum declaration
	 * @return The ClassMetadata containing the metadata of the visited enum
	 */
    public final ClassMetadata visit(final EnumDeclaration n) {		
    	ConsoleUtils.displayDebug("Found enum " + n.getName());
    	EnumMetadata result = new EnumMetadata();
    	result.setName(n.getName());
    	// Check reserved keywords
		SqliteAdapter.Keywords.exists(result.getName());
    	
    	// Get the Enum entries
    	if (n.getEntries() != null) {
    		for (EnumConstantDeclaration enumEntry : n.getEntries()) {
    			result.getEntries().add(enumEntry.getName());
    		}
    	}
    	
    	// Get list of Members (Methods, Fields, Subclasses, Enums, etc.)
		List<BodyDeclaration> members = n.getMembers();
		if (members != null) {
			for (BodyDeclaration member : members) {
				// Subclass or Enum
				if (member instanceof TypeDeclaration) {
					this.visit((TypeDeclaration) member);
				} else
				
				// Fields
				if (member instanceof FieldDeclaration) {
					FieldMetadata fieldMetas = this.fieldVisitor.visit(
							(FieldDeclaration) member, 
							result);
					if (fieldMetas != null) {
						if (fieldMetas.isId()) {
							result.setIdName(fieldMetas.getName());
						}
						result.getFields().put(
								fieldMetas.getName(), 
								fieldMetas);
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
    	
    	
    	return result;
    }
    
    /**
	 * Visit a class.
	 * @param n The class or interface declaration
	 * @return The ClassMetadata containing the metadata of the visited class
	 */
    public final ClassMetadata visit(final TypeDeclaration n) {
    	ClassMetadata result;
    	
    	if (n instanceof ClassOrInterfaceDeclaration) {
    		result = this.visit((ClassOrInterfaceDeclaration) n);
    	} else if (n instanceof EnumDeclaration) {
    		result = this.visit((EnumDeclaration) n);
    	} else {
    		result = null;
    	}
    	
    	return result;
    }
}
