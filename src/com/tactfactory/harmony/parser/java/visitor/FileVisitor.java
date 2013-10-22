/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser.java.visitor;

import java.util.ArrayList;

import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.utils.PackageUtils;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.TypeDeclaration;

/**
 * Java file visitor class.
 */
public class FileVisitor {
	/**
	 * The class visitor used by this visitor.
	 */
	private final ClassVisitor classVisitor = new ClassVisitor();
	/**
	 * The import visitor used by this visitor.
	 */
	private final ImportVisitor importVisitor = new ImportVisitor();

	/**
	 * Visit a compilation unit (java file).
	 * @param n The compilation unit to visit.
	 * @return A list of ClassMetadata extracted from the file.
	 */
	public final ArrayList<ClassMetadata> visit(final CompilationUnit n) {
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

	/**
	 * Add a given ClassMetadata and its subclasses to a ClassMetadata list.
	 * @param importList The import list concerning this class and subclasses
	 * @param spackage The package of the classes
	 * @param list The list where to add the classes
	 * @param classMetas The class to add
	 */
	private void addClassToList(
			final ArrayList<String> importList,
			final String spackage,
			final ArrayList<ClassMetadata> list,
			final ClassMetadata classMetas) {
		if (classMetas != null) {
			for (ClassMetadata subClass : 
				classMetas.getInnerClasses().values()) {
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
