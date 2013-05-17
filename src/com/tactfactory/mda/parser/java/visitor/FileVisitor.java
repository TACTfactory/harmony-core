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
				
				this.addClassToList(importList,
						spackage,
						result,
						currentClass);
			}
		}
		
		return result;
	}
	
	private void addClassToList(ArrayList<String> importList,
			String spackage,
			ArrayList<ClassMetadata> list, 
			ClassMetadata classMetas) {
		if (classMetas != null) {
			for (ClassMetadata subClass : classMetas.getSubClasses().values()) {
				this.addClassToList(importList,
						spackage,
						list,
						subClass);
			}
			classMetas.setImports(importList);
			classMetas.setSpace(spackage);
			list.add(classMetas);
		}
		
	}

}
