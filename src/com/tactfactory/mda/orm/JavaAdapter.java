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
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
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
				new ImportVisitor().visit(mclass,meta);
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
			if(classAnnotations!=null){
				for (AnnotationExpr annotationExpr : classAnnotations) {
					
					String annotationType = annotationExpr.getName().toString();
					if (annotationType.equals(PackageUtils.extractNameEntity(Entity.class))) {
						 
						meta.name = PackageUtils.extractNameEntity(n.getName());
						
						// Debug Log
						if (Harmony.DEBUG)
							System.out.print("\tEntity: " + meta.space + ".entity." +  meta.name + "\n");
					}
				}
				 
				List<ClassOrInterfaceType> impls = n.getImplements();
				if(impls!=null){
					for(ClassOrInterfaceType impl : impls){					
						meta.impls.add(impl.getName());		
					}
				}
				
				List<ClassOrInterfaceType> exts = n.getExtends();
				if(exts!=null){
					for(ClassOrInterfaceType ext : exts){					
						meta.exts = ext.getName();		
					}
				}
			}
			
	    }
	}

	private static class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		@Override
		public void visit(FieldDeclaration field, ClassMetadata meta) {
			List<AnnotationExpr> fieldAnnotations = field.getAnnotations();
			
			if (	fieldAnnotations != null && 
					fieldAnnotations.size() > 0 ) {
				
				FieldMetadata fieldMeta = new FieldMetadata();
				fieldMeta.name = field.getVariables().get(0).toString(); // FIXME not manage multi-variable
				fieldMeta.type = field.getType().toString();
				fieldMeta.relation_type = field.getAnnotations().get(0).toString(); // FIXME not manage multi-annotation
				fieldMeta.nullable = false; // Default nullable is false
				fieldMeta.unique = false; 
				
				
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
					
					if (annotationType.equals(PackageUtils.extractNameEntity(JoinColumn.class))) {
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
					
					if(annotationExpr instanceof NormalAnnotationExpr){
						NormalAnnotationExpr norm = (NormalAnnotationExpr)annotationExpr;
						if(norm.getPairs()!=null && norm.getPairs().size()>0)
							for(MemberValuePair mvp : norm.getPairs()){ // Check if there are any arguments in the annotation
								if(annotationType.equals(PackageUtils.extractNameEntity(Column.class))){ // for @Column
									if(mvp.getName().equals("nullable") && mvp.getValue().toString().equals("true")){
										fieldMeta.nullable = true;
									}else if(mvp.getName().equals("unique") && mvp.getValue().toString().equals("true")){
										fieldMeta.unique = true;
									}else if(mvp.getName().equals("length")){
										fieldMeta.length = Integer.parseInt(mvp.getValue().toString());
									}else if(mvp.getName().equals("precision")){
										fieldMeta.precision = Integer.parseInt(mvp.getValue().toString());
									}else if(mvp.getName().equals("scale")){
										fieldMeta.scale = Integer.parseInt(mvp.getValue().toString());
									}else if(mvp.getName().equals("type")){
										fieldMeta.entity_type = mvp.getValue().toString();
									}
								} else if(annotationType.equals(PackageUtils.extractNameEntity(JoinColumn.class))){ // for @JoinColumn
									
								}
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
	
	private static class ImportVisitor extends VoidVisitorAdapter<ClassMetadata> {
		@Override
		public void visit(ImportDeclaration imp, ClassMetadata meta) {
			String impName = imp.getName().getName();
			meta.imports.add(impName);
			
		}
	}
}
