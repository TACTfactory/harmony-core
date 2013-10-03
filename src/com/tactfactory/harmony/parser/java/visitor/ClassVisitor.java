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
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.InterfaceMetadata;
import com.tactfactory.harmony.parser.JavaModelParser;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.parser.BaseParser;
import com.tactfactory.harmony.plateforme.SqliteAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.PackageUtils;

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
	
	/** The constructor visitor used by this visitor. */
	private final ConstructorVisitor constructorVisitor = 
			new ConstructorVisitor();

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

    	// Detect whether we have an interface or an entity
    	if (classDeclaration.isInterface()) {
    		result = new InterfaceMetadata();
    		isInterface = true;
    	} else {
    		result = new EntityMetadata();
    	}


    	// *** Get the common attributes ***
    	result.setName(PackageUtils.extractNameEntity(
    			classDeclaration.getName()));
    	// Debug Log
    	ConsoleUtils.displayDebug("Found class : " +  result.getName());
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
	    	final List<AnnotationExpr> classAnnotations =
	    			classDeclaration.getAnnotations();
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
			final List<ClassOrInterfaceType> impls =
					classDeclaration.getImplements();
			if (impls != null) {
				for (final ClassOrInterfaceType impl : impls) {
					result.getImplementTypes().add(impl.getName());

					// Debug Log
					ConsoleUtils.displayDebug("\tImplement : "
								+ impl.getName());
				}
			}

			// Get Extend type
			final List<ClassOrInterfaceType> exts =
					classDeclaration.getExtends();
			if (exts != null) {
				for (final ClassOrInterfaceType ext : exts) {
					result.setExtendType(ext.getName());

					// Debug Log
					ConsoleUtils.displayDebug("\tExtend : "
								+ ext.getName());
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
					} else
						
					// Constructors
					if (member instanceof ConstructorDeclaration) {
						result.getMethods().add(this.constructorVisitor.visit(
										(ConstructorDeclaration) member,
										result));
					}
				}

				for (final ClassMetadata subClass : subClasses.values()) {
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
	 * @param enumDecl The enum declaration
	 * @return The ClassMetadata containing the metadata of the visited enum
	 */
    public final ClassMetadata visit(final EnumDeclaration enumDecl) {
    	ConsoleUtils.displayDebug("Found enum " + enumDecl.getName());
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
}
