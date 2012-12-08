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
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.annotation.Column;
import com.tactfactory.mda.orm.annotation.Entity;
import com.tactfactory.mda.orm.annotation.Id;
import com.tactfactory.mda.orm.annotation.JoinColumn;
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
				new MethodVisitor().visit(mclass, meta);
				
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
			
			// Get list of Implement type
			List<ClassOrInterfaceType> impls = n.getImplements();
			if(impls!=null){
				for(ClassOrInterfaceType impl : impls){					
					meta.impls.add(impl.getName());		
				}
			}
			
			// Get Extend type
			List<ClassOrInterfaceType> exts = n.getExtends();
			if(exts!=null){
				for(ClassOrInterfaceType ext : exts){					
					meta.exts = ext.getName();		
				}
			}
			
			// Get list of Members
			List<BodyDeclaration> members = n.getMembers();
			if(members!=null){
				for(BodyDeclaration member : members){					
					//meta.members.add(member.getName());	///TODO Micky > Good or Trash ? [Gregg]	
				}
			}
			
	    }
	}

	private static class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		private static final String FILTER_COLUMN = PackageUtils.extractNameEntity(Column.class);
		private static final String FILTER_JOINCOLUMN = PackageUtils.extractNameEntity(JoinColumn.class);
		
		
		@Override
		public void visit(FieldDeclaration field, ClassMetadata meta) {
			List<AnnotationExpr> fieldAnnotations = field.getAnnotations();
			
			if (fieldAnnotations != null && 
					fieldAnnotations.size() > 0 ) {
				
				// General
				FieldMetadata fieldMeta = new FieldMetadata();
				fieldMeta.name = field.getVariables().get(0).toString(); // FIXME not manage multi-variable
				fieldMeta.type = field.getType().toString();
				fieldMeta.relation_type = field.getAnnotations().get(0).toString(); // FIXME not manage multi-annotation
				fieldMeta.nullable = false; // Default nullable is false
				
				// Database
				boolean isColumn = false;
				boolean isId = false;
				boolean isRelation = false;
				
				for (AnnotationExpr annotationExpr : fieldAnnotations) {
					String annotationType = annotationExpr.getName().toString();
	
					isId = this.isId(fieldMeta, isId, annotationType);
					isColumn = this.isColumn(fieldMeta, isColumn, annotationType);
					isRelation = this.isRelation(fieldMeta, isRelation, annotationType);
					
					if(annotationExpr instanceof NormalAnnotationExpr){
						NormalAnnotationExpr norm = (NormalAnnotationExpr)annotationExpr;
						if(norm.getPairs()!=null && norm.getPairs().size()>0)
							for(MemberValuePair mvp : norm.getPairs()) // Check if there are any arguments in the annotation
								if(mvp.getName().equals("nullable") && mvp.getValue().toString().equals("true")){
									fieldMeta.nullable = true;
								}
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

		/**
		 * @param fieldMeta
		 * @param isId
		 * @param annotationType
		 * @return
		 */
		private boolean isId(FieldMetadata fieldMeta, boolean old, String annotationType) {
			boolean isId = old;
			
			if (annotationType.equals(PackageUtils.extractNameEntity(Id.class))) {
				isId = true;
				
				// Debug Log
				if (Harmony.DEBUG)
					System.out.print("\t    ID: " + fieldMeta.name +"\n");
			}
			return isId;
		}
		
		/**
		 * @param fieldMeta
		 * @param annotationType
		 * @return
		 */
		private boolean isColumn(FieldMetadata fieldMeta, boolean old, String annotationType) {
			boolean isColumn = old;
			
			if (annotationType.equals(FILTER_COLUMN)	||
				annotationType.equals(FILTER_JOINCOLUMN)) {
				
				isColumn = true;
				
				// Debug Log
				if (Harmony.DEBUG) {
					String type = "Column";
					//if (type.equals()) 
						
						
					System.out.print("\t    " + type + ": " + fieldMeta.name + 
							" type of " + fieldMeta.type +"\n");
				}
			}
			return isColumn;
		}

		/**
		 * @param fieldMeta
		 * @param annotationType
		 * @return
		 */
		private boolean isRelation(FieldMetadata fieldMeta, boolean old, String annotationType) {
			boolean isRelation = old;
			
			if (annotationType.equals(PackageUtils.extractNameEntity(OneToOne.class))	||
				annotationType.equals(PackageUtils.extractNameEntity(OneToMany.class))	||
				annotationType.equals(PackageUtils.extractNameEntity(ManyToOne.class))	||
				annotationType.equals(PackageUtils.extractNameEntity(ManyToMany.class))	) {
				isRelation = true;
				
				// Debug Log
				if (Harmony.DEBUG)
					System.out.print("\t    Relation " + annotationType + ": " + fieldMeta.name + 
							" type of " + fieldMeta.type +"\n");
			}
			return isRelation;
		}
	}
	
	private static class MethodVisitor extends VoidVisitorAdapter<ClassMetadata> {
		@Override
		public void visit(MethodDeclaration method, ClassMetadata meta) {
			MethodMetadata methodMeta = new MethodMetadata();
			methodMeta.name = method.getName();
			List<Parameter> parameters = method.getParameters();
			if(parameters!=null){
				for(Parameter param : parameters){
					methodMeta.argumentsTypes.add(param.getType().toString());
				}
			}
			Type methodType = method.getType();

			methodMeta.type = methodType.toString();
			//methodMeta.argumentsTypes = 
			if(Harmony.DEBUG){
				//methodMeta.argumentsTypes.get(0)
				String mess = "\t\tFound method : "+methodMeta.type+" "+methodMeta.name+"(";
				for(String args : methodMeta.argumentsTypes)
					mess+=args+", ";
				mess+=")";
				System.out.println(mess);
			}
			meta.methods.add(methodMeta);
			
		}
	}
}
