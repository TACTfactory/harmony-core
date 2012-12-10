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
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
		private static final String FILTER_ENTITY = PackageUtils.extractNameEntity(Entity.class);
		
	    @Override
	    public void visit(ClassOrInterfaceDeclaration n, ClassMetadata meta) {
	    	// Get list of annotations
	    	List<AnnotationExpr> classAnnotations = n.getAnnotations();
			if(classAnnotations!=null){
				for (AnnotationExpr annotationExpr : classAnnotations) {
					String annotationType = annotationExpr.getName().toString();
					if (annotationType.equals(FILTER_ENTITY)) {
						 
						meta.name = PackageUtils.extractNameEntity(n.getName());
						
						// Debug Log
						if (Harmony.DEBUG)
							System.out.print("\tEntity: " + meta.space + ".entity." +  meta.name + "\n");
					}
				}
				
				if (!Strings.isNullOrEmpty(meta.name)) {
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
	    }
	}

	private static class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		private static final String FILTER_ENTITY 		= PackageUtils.extractNameEntity(Id.class);
		private static final String FILTER_COLUMN 		= PackageUtils.extractNameEntity(Column.class);
		private static final String FILTER_JOINCOLUMN 	= PackageUtils.extractNameEntity(JoinColumn.class);
		private static final String FILTER_ONE2ONE 		= PackageUtils.extractNameEntity(OneToOne.class);
		private static final String FILTER_ONE2MANY 	= PackageUtils.extractNameEntity(OneToMany.class);
		private static final String FILTER_MANY2ONE 	= PackageUtils.extractNameEntity(ManyToOne.class);
		private static final String FILTER_MANY2MANY 	= PackageUtils.extractNameEntity(ManyToMany.class);
		
		@Override
		public void visit(FieldDeclaration field, ClassMetadata meta) {
			List<AnnotationExpr> fieldAnnotations = field.getAnnotations();
			
			if (fieldAnnotations != null) {

				// General (defaults values)
				FieldMetadata fieldMeta = new FieldMetadata();
				fieldMeta.name = field.getVariables().get(0).toString(); // FIXME not manage multi-variable
				fieldMeta.type = field.getType().toString();

				fieldMeta.nullable = false; // Default nullable is false
				fieldMeta.unique = false; 
				
				// Database
				boolean isColumn = false;
				boolean isId = false;
				boolean isRelation = false;
				
				for (AnnotationExpr annotationExpr : fieldAnnotations) {
					String annotationType = annotationExpr.getName().toString();
	
					isId = this.isId(fieldMeta, isId, annotationType);
					isColumn = this.isColumn(fieldMeta, isColumn, annotationType);
					isRelation = this.isRelation(fieldMeta, isRelation, annotationType);
					
					this.loadAttributs(fieldMeta, annotationExpr, annotationType);
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
				
				if (Strings.isNullOrEmpty(fieldMeta.columnDefinition)) {
					fieldMeta.columnDefinition = fieldMeta.type;
				}
				fieldMeta.columnDefinition = SqliteAdapter.generateColumnType(fieldMeta);
				
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
//				/** Store all the annotations for the field, and their arguments, in HashMaps*/
//				HashMap<String, HashMap<String,Object>> annotations = new HashMap<String, HashMap<String,Object>>();
//				for (AnnotationExpr annotationExpr : fieldAnnotations) {
//					HashMap<String, Object> annotationParams = new HashMap<String, Object>();
//					if(annotationExpr instanceof NormalAnnotationExpr){
//						NormalAnnotationExpr norm = (NormalAnnotationExpr)annotationExpr;
//						if(norm.getPairs()!=null && norm.getPairs().size()>0){
//							for(MemberValuePair pair : norm.getPairs()){
//								annotationParams.put(pair.getName(), pair.getValue());
//							}
//						}		
//					}	
//					annotations.put(annotationExpr.getName().toString(),annotationParams);
//				}
				
/*				RelationMetadata rm = null;
				//FieldMetadata fieldMeta = new FieldMetadata();
				
				//fieldMeta.name = field.getVariables().get(0).toString(); // TODO : More than one var on a line
				//fieldMeta.type = field.getType().toString();
				//fieldMeta.entity_type = fieldMeta.type;
				
				if(annotations.containsKey("OneToOne")){
					rm = new RelationMetadata();
					extractRelation(annotations.get("OneToOne"),fieldMeta, rm);
					rm.type = "OneToOne";
					fieldMeta.entity_type = "int";
				}
				else if(annotations.containsKey("ManyToOne")){
					rm = new RelationMetadata();
					extractRelation(annotations.get("ManyToOne"),fieldMeta, rm);
					rm.type = "ManyToOne";
					fieldMeta.entity_type = "int";
				}
				else if(annotations.containsKey("OneToMany")){
					rm = new RelationMetadata();
					extractRelation(annotations.get("OneToMany"),fieldMeta, rm);
					rm.type = "OneToMany";
					fieldMeta.entity_type = "int";
				}
				else if(annotations.containsKey("ManyToMany")){
					rm = new RelationMetadata();
					extractRelation(annotations.get("ManyToMany"),fieldMeta, rm);
					rm.type = "ManyToMany";
					fieldMeta.entity_type = "int";
				}
				
//				if(annotations.containsKey("Column") && rm==null){ // If it's a relation, it can't be a Column					
//					extractColumn(annotations.get("Column"), fieldMeta);
//				}
				
				if(annotations.containsKey("JoinColumn")){
					
					extractJoinColumn(annotations.get("JoinColumn"), fieldMeta, rm);
					
					fieldMeta.type = field.getType().toString();
					rm.field = fieldMeta.name;
					//meta.fields.put(fieldMeta.name, fieldMeta);
					//meta.relations.put(fieldMeta.name, rm);
				}
				
				
				
				if(rm!=null) fieldMeta.relation = rm;//meta.relations.put(fieldMeta.name, rm);
//				if(annotations.containsKey("Id")) meta.ids.put(fieldMeta.name, fieldMeta);
				meta.fields.put(fieldMeta.name, fieldMeta);
	*/			
			}
		}
		/*
		private void extractColumn(HashMap<String, Object> args, FieldMetadata fieldMeta){
			if(args.containsKey("type"))
				fieldMeta.entity_type = args.get("type").toString();
			if(args.containsKey("name"))
				fieldMeta.name = args.get("name").toString();
			if(args.containsKey("nullable"))
				fieldMeta.nullable = args.get("nullable").toString().equals("true");
			if(args.containsKey("unique"))
				fieldMeta.unique = args.get("unique").toString().equals("true");
			if(args.containsKey("length"))
				fieldMeta.length = Integer.parseInt(args.get("length").toString());
			if(args.containsKey("precision"))
				fieldMeta.precision = Integer.parseInt(args.get("precision").toString());
			if(args.containsKey("scale"))
				fieldMeta.scale = Integer.parseInt(args.get("scale").toString());
		}
		
		private void extractJoinColumn(HashMap<String, Object> args, FieldMetadata fieldMeta, RelationMetadata rm){
			if(args.containsKey("type"))
				fieldMeta.type = args.get("type").toString();
			if(args.containsKey("referencedColumnName"))
				rm.field_ref = args.get("referencedColumnName").toString();
			if(args.containsKey("unique"))
				fieldMeta.unique = args.get("unique").toString().equals("true");
			if(args.containsKey("nullable"))
				fieldMeta.nullable = args.get("nullable").toString().equals("true");
		}
		
		private void extractRelation(HashMap<String, Object> args, FieldMetadata fieldMeta, RelationMetadata rm){
			if(args.containsKey("targetEntity")){
				rm.entity_ref = args.get("targetEntity").toString();
			}
			rm.field = fieldMeta.name;
			
		}*/


		/**
		 * @param fieldMeta
		 * @param annotationExpr
		 * @param annotationType
		 */
		private void loadAttributs(FieldMetadata fieldMeta, AnnotationExpr annotationExpr, String annotationType) {
			if (annotationExpr instanceof NormalAnnotationExpr) {
				NormalAnnotationExpr norm = (NormalAnnotationExpr)annotationExpr;
				
				if (norm.getPairs()!=null) {
					for(MemberValuePair mvp : norm.getPairs()) { // Check if there are any arguments in the annotation
						
						// Argument of Annotation Column
						if (annotationType.equals(FILTER_COLUMN)) { 
							// set nullable
							if (mvp.getName().equals("nullable") && 
									mvp.getValue().toString().equals("true")) {
								fieldMeta.nullable = true;
							}else 
								
							// Set unique 
							if (mvp.getName().equals("unique") && 
									mvp.getValue().toString().equals("true")) {
								fieldMeta.unique = true;
							}else 
								
							// set length
							if (mvp.getName().equals("length")) {
								fieldMeta.length = Integer.parseInt(mvp.getValue().toString());
							}else 
								
							// set precision
							if (mvp.getName().equals("precision")) {
								fieldMeta.precision = Integer.parseInt(mvp.getValue().toString());
							}else 
								
							// set scale
							if (mvp.getName().equals("scale")) {
								fieldMeta.scale = Integer.parseInt(mvp.getValue().toString());
							}else 
								
							// set column definition
							if (mvp.getName().equals("type")) {
								fieldMeta.columnDefinition = mvp.getValue().toString();
							}
						} else 
							
						
						// Argument of Annotation join Column
						if (annotationType.equals(FILTER_JOINCOLUMN)) { // for @JoinColumn
							
						}
					}	
				}
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
			
			if (annotationType.equals(FILTER_ENTITY)) {
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
			
			if (annotationType.equals(FILTER_ONE2ONE)	||
				annotationType.equals(FILTER_ONE2MANY)	||
				annotationType.equals(FILTER_MANY2ONE)	||
				annotationType.equals(FILTER_MANY2MANY)	) {
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
			methodMeta.type = method.getType().toString(); 
			
			// Add Parameters
			List<Parameter> parameters = method.getParameters();
			if(parameters!=null){
				for(Parameter param : parameters){
					methodMeta.argumentsTypes.add(param.getType().toString());
				}
			}
			
			// Debug Log
			if(Harmony.DEBUG){
				StringBuilder builder = new StringBuilder(
						String.format("\t\tFound method : %s %s(", methodMeta.type, methodMeta.name));
				
				for(String args : methodMeta.argumentsTypes) {
					if (args != methodMeta.argumentsTypes.get(0))
						builder.append(", ");
					
					builder.append(String.format("%s", args));
				}
					
				builder.append(")");

				System.out.println(builder.toString());
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
