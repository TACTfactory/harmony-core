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

public abstract class BaseParser {

	public abstract void visitClass(ClassOrInterfaceDeclaration n, ClassMetadata meta);
	
	public abstract void visitClassAnnotation(ClassMetadata cm, AnnotationExpr fieldAnnot);
	
	public abstract void visitField(FieldDeclaration field, ClassMetadata meta);
	
	public abstract void visitFieldAnnotation(FieldMetadata field, AnnotationExpr fieldAnnot, ClassMetadata meta);
	
	public abstract void visitMethod(MethodDeclaration method, ClassMetadata meta);
	
	public abstract void visitImport(ImportDeclaration imp, ClassMetadata meta);
}
