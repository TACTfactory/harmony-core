/**
 * This file is part of the Symfodroid package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.orm;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.command.PackageUtils;
import com.tactfactory.mda.orm.annotation.Column;
import com.tactfactory.mda.orm.annotation.Entity;

public class JavaAdapter {
	private ArrayList<ClassMetadata> metas = new ArrayList<ClassMetadata>();
	
	/**
	 * @return the metas
	 */
	public ArrayList<ClassMetadata> getMetas() {
		return metas;
	}

	public void parse(CompilationUnit mclass) {
		
		String spackage = PackageUtils.extractNameSpace(mclass.getPackage().getName().toString());
		if (!Strings.isNullOrEmpty(spackage)) {
			
			ClassMetadata meta = new ClassMetadata();
			meta.nameSpace = spackage;
			
			new ClassVisitor().visit(mclass, meta);
			if (!Strings.isNullOrEmpty(meta.nameClass)) {
				new FieldVisitor().visit(mclass, meta);
				
				this.metas.add(meta);
			}
		}
	}
	
	private static class ClassVisitor extends VoidVisitorAdapter<ClassMetadata> {
	    @Override
	    public void visit(ClassOrInterfaceDeclaration n, ClassMetadata meta) {
	    	List<AnnotationExpr> classAnnotations = n.getAnnotations();
			
			for (AnnotationExpr annotationExpr : classAnnotations) {
				
				String annotationType = annotationExpr.getName().toString();
				if (annotationType.equals(PackageUtils.extractNameEntity(Entity.class))) {
					 
					meta.nameClass = PackageUtils.extractNameEntity(n.getName());
					
					// Debug Log
					if (com.tactfactory.mda.command.Console.DEBUG)
						System.out.print("\tEntity: " + meta.nameSpace + "." +  meta.nameClass + "\n");
				}
			}
	    }
	}

	private static class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		@Override
		public void visit(FieldDeclaration n, ClassMetadata meta) {
			HashMap<String, FieldMetadata> fields = meta.fields;
			List<AnnotationExpr> fieldAnnotations = n.getAnnotations();
			FieldMetadata fieldMeta = null;
			
			boolean isfind = false;
			for (AnnotationExpr annotationExpr : fieldAnnotations) {
				String annotationType = annotationExpr.getName().toString();
				
				if (annotationType.equals(PackageUtils.extractNameEntity(Column.class))) {
					fieldMeta = new FieldMetadata();
					fieldMeta.name = n.getVariables().get(0).toString();
					fieldMeta.type = n.getType().toString();
					
					// Debug Log
					if (com.tactfactory.mda.command.Console.DEBUG)
						System.out.print("\t    Column: " + fieldMeta.name + 
								" type of " + fieldMeta.type +"\n");
					
					isfind = true;
				}
			}
			
			// Set Field meta
			if (isfind)  {
				fields.put(fieldMeta.name, fieldMeta);
			}
			
			/*for (Field field : mclass.getDeclaredFields()) {
				

				if (columnAnnotation != null) {
					
									Annotation idAnnotation = field.getAnnotation(Id.class);
				Annotation generateValueAnnotation = field.getAnnotation(GeneratedValue.class);
				
				if (com.tactfactory.mda.android.command.Console.DEBUG) {
					if (idAnnotation != null)
						System.out.print("\t " + idAnnotation + "\n");
					
					if (generateValueAnnotation != null)
						System.out.print("\t " + generateValueAnnotation + "\n");
				}
					
					
				}
			}*/
		}
	}
	
}
