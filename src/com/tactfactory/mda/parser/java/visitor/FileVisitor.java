package com.tactfactory.mda.parser.java.visitor;

import java.util.ArrayList;

import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.utils.PackageUtils;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.TypeDeclaration;

public class FileVisitor {
	private ClassVisitor classVisitor = new ClassVisitor();
	private ImportVisitor importVisitor = new ImportVisitor();
	
	public ArrayList<ClassMetadata> visit(CompilationUnit n) {
		final ArrayList<ClassMetadata> result = new ArrayList<ClassMetadata>();
		final ArrayList<String> importList = new ArrayList<String>();

		// Extract the package
		final String spackage = PackageUtils.extractNameSpace(
				n.getPackage().getName().toString());
		
		// Parse all the imports in this file
		if (n.getImports() != null) {
			for (ImportDeclaration importDecl : n.getImports()) {
				importList.add(this.importVisitor.visit(importDecl, null));
			}
		}
		
		// Parse all the classes/enums/interfaces in this file 
		// and add to them the imports and the package name.
		ClassMetadata currentClass;
		
		if (n.getTypes() != null) {
			for (TypeDeclaration typeDecl : n.getTypes()) {
				currentClass = this.classVisitor.visit(typeDecl);
				
				if (currentClass != null) {
					currentClass.setImports(importList);
					currentClass.setSpace(spackage);
					result.add(currentClass);
				}
			}
		}
		
		return result;
	}

}
