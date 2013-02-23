/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.parser;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;

/**
 * Base Parser.
 * Extend this parser if you want to parse new annotations.
 */
public abstract class BaseParser {

	/**
	 * Class visitor.
	 * @param n The class declaration
	 * @param meta The class metadata
	 */
	public abstract void visitClass(ClassOrInterfaceDeclaration n, 
			ClassMetadata meta);
	
	/**
	 * Annotation class visitor.
	 * @param cm The class metadata
	 * @param fieldAnnot The class annotations
	 */
	public abstract void visitClassAnnotation(ClassMetadata cm, 
			AnnotationExpr fieldAnnot);
	
	/**
	 * Visit the field declaration.
	 * @param field The field declaration.
	 * @param meta The class Metadata
	 */
	public abstract void visitField(FieldDeclaration field, ClassMetadata meta);
	
	/**
	 * Visit the field Annotation.
	 * @param field The field metadata
	 * @param fieldAnnot The field annotation
	 * @param meta The class Metadata
	 */
	public abstract void visitFieldAnnotation(FieldMetadata field, 
			AnnotationExpr fieldAnnot, ClassMetadata meta);
	
	/**
	 * Visit a method declaration.
	 * @param method The method declaration.
	 * @param meta The class metadata.
	 */
	public abstract void visitMethod(MethodDeclaration method, 
			ClassMetadata meta);
	
	/**
	 * Visit the classes imports.
	 * @param imp The import declaration.
	 * @param meta The class Metadata
	 */
	public abstract void visitImport(ImportDeclaration imp, ClassMetadata meta);
}
