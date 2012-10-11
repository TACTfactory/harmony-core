/**
 * This file is part of the Harmony package.
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
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.annotation.Column;
import com.tactfactory.mda.orm.annotation.Entity;
import com.tactfactory.mda.orm.annotation.Id;
import com.tactfactory.mda.orm.annotation.ManyToMany;
import com.tactfactory.mda.orm.annotation.ManyToOne;
import com.tactfactory.mda.orm.annotation.OneToMany;
import com.tactfactory.mda.orm.annotation.OneToOne;
import com.tactfactory.mda.utils.PackageUtils;

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
			meta.space = spackage;
			
			new ClassVisitor().visit(mclass, meta);
			if (!Strings.isNullOrEmpty(meta.name)) {
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
					 
					meta.name = PackageUtils.extractNameEntity(n.getName());
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\tEntity: " + meta.space + ".entity." +  meta.name + "\n");
				}
			}
	    }
	}

	private static class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		@Override
		public void visit(FieldDeclaration n, ClassMetadata meta) {
			List<AnnotationExpr> fieldAnnotations = n.getAnnotations();
			
			FieldMetadata fieldMeta = new FieldMetadata();
			fieldMeta.name = n.getVariables().get(0).toString();
			fieldMeta.type = n.getType().toString();
			fieldMeta.relation_type = n.getAnnotations().get(0).toString();
			
			boolean isColumn = false;
			boolean isId = false;
			boolean isRelation = false;
			
			for (AnnotationExpr annotationExpr : fieldAnnotations) {
				String annotationType = annotationExpr.getName().toString();

				if (annotationType.equals(PackageUtils.extractNameEntity(Id.class))) {
					isId = true;
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\t    ID: " + fieldMeta.name +"\n");
				}
				
				if (annotationType.equals(PackageUtils.extractNameEntity(Column.class))) {
					isColumn = true;
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\t    Column: " + fieldMeta.name + 
								" type of " + fieldMeta.type +"\n");
				}
				
				if (annotationType.equals(PackageUtils.extractNameEntity(OneToOne.class))) {
					isRelation = true;
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\t    Relation One to One: " + fieldMeta.name + 
								" type of " + fieldMeta.type +"\n");
				}
				
				if (annotationType.equals(PackageUtils.extractNameEntity(OneToMany.class))) {
					isRelation = true;
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\t    Relation One to Many: " + fieldMeta.name + 
								" type of " + fieldMeta.type +"\n");
				}
				
				if (annotationType.equals(PackageUtils.extractNameEntity(ManyToOne.class))) {
					isRelation = true;
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\t    Relation Many to One: " + fieldMeta.name + 
								" type of " + fieldMeta.type +"\n");
				}
				
				if (annotationType.equals(PackageUtils.extractNameEntity(ManyToMany.class))) {
					isRelation = true;
					
					// Debug Log
					if (Harmony.DEBUG)
						System.out.print("\t    Relation Many to Many: " + fieldMeta.name + 
								" type of " + fieldMeta.type +"\n");
				}
			}
			
			// Set Field meta
			if (isId) {
				meta.ids.put(fieldMeta.name, fieldMeta);
			}

			if (isColumn)  {
				meta.fields.put(fieldMeta.name, fieldMeta);
			}

			if (isRelation)  {
				meta.relations.put(fieldMeta.name, fieldMeta);
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
