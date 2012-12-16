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
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.annotation.Column;
import com.tactfactory.mda.orm.annotation.Entity;
import com.tactfactory.mda.orm.annotation.Id;
import com.tactfactory.mda.orm.annotation.JoinColumn;
import com.tactfactory.mda.orm.annotation.ManyToMany;
import com.tactfactory.mda.orm.annotation.ManyToOne;
import com.tactfactory.mda.orm.annotation.OneToMany;
import com.tactfactory.mda.orm.annotation.OneToOne;
import com.tactfactory.mda.orm.annotation.Column.Type;
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
						ConsoleUtils.displayDebug("Entity : " + meta.space + ".entity." +  meta.name);
					}
				}
				
				if (!Strings.isNullOrEmpty(meta.name)) {
					// Get list of Implement type
					List<ClassOrInterfaceType> impls = n.getImplements();
					if(impls!=null){
						for(ClassOrInterfaceType impl : impls){					
							meta.impls.add(impl.getName());
							
							// Debug Log
							ConsoleUtils.displayDebug("\tImplement : " + impl.getName());
						}
					}
					
					// Get Extend type
					List<ClassOrInterfaceType> exts = n.getExtends();
					if(exts!=null){
						for(ClassOrInterfaceType ext : exts){					
							meta.exts = ext.getName();		
							
							// Debug Log
							ConsoleUtils.displayDebug("\tExtend : " + ext.getName());
						}
					}
					
					// Get list of Members
					/*List<BodyDeclaration> members = n.getMembers();
					if(members!=null){
						for(BodyDeclaration member : members){					
							meta.members.add(member.getName());	///TODO Micky > Good or Trash ? [Gregg]	
						}
					}*/
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
				fieldMeta.isFinal = ModifierSet.isFinal(field.getModifiers());
				fieldMeta.name = field.getVariables().get(0).getId().getName(); // FIXME not manage multi-variable
				fieldMeta.name_in_db = fieldMeta.name;
				fieldMeta.type = field.getType().toString();

				fieldMeta.nullable = false; // Default nullable is false
				fieldMeta.unique = false; 
				
				//HashMap<String, String> rel = new HashMap<String, String>();
				RelationMetadata rel = new RelationMetadata();
				
				// Database
				boolean isColumn = false;
				boolean isId = false;
				boolean isRelation = false;
				
				for (AnnotationExpr annotationExpr : fieldAnnotations) {
					String annotationType = annotationExpr.getName().toString();
	
					isId = this.isId(fieldMeta, isId, annotationType);
					isColumn = this.isColumn(fieldMeta, isColumn, annotationType);
					isRelation = this.isRelation(fieldMeta, isRelation, annotationType);
					
					if (annotationType.equals(FILTER_ONE2ONE)	||
							annotationType.equals(FILTER_ONE2MANY)	||
							annotationType.equals(FILTER_MANY2ONE)	||
							annotationType.equals(FILTER_MANY2MANY)	){
						rel.type = annotationType;
					}
					
					this.loadAttributes(rel, fieldMeta, annotationExpr, annotationType);
				}
				
				if (isId) {
					meta.ids.put(fieldMeta.name, fieldMeta);
				}
	
				if (isRelation) {
					rel.field = fieldMeta.name;
					rel.entity_ref = PackageUtils.extractClassNameFromArray(fieldMeta.type);
					fieldMeta.relation = rel;
					meta.relations.put(fieldMeta.name, fieldMeta);
				}
				
				if(isId || isColumn || isRelation)
					meta.fields.put(fieldMeta.name, fieldMeta);
				
				if (Strings.isNullOrEmpty(fieldMeta.columnDefinition)) {
					if(fieldMeta.type_attribute!=null)
						fieldMeta.columnDefinition = fieldMeta.type_attribute.getValue();
					else
						fieldMeta.columnDefinition = fieldMeta.type;
				}
				fieldMeta.columnDefinition = SqliteAdapter.generateColumnType(fieldMeta);
				
			}
		}



		/**
		 * @param fieldMeta
		 * @param annotationExpr
		 * @param annotationType
		 */
		private void loadAttributes(RelationMetadata rel, FieldMetadata fieldMeta, AnnotationExpr annotationExpr, String annotationType) {
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
								
							// set name
							if (mvp.getName().equals("name")) {
								if(mvp.getValue() instanceof StringLiteralExpr)
									fieldMeta.name_in_db = ((StringLiteralExpr)mvp.getValue()).getValue();
								else
									fieldMeta.name_in_db = mvp.getValue().toString();
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
								//TODO : Generate warning if type not recognized
								if(mvp.getValue() instanceof StringLiteralExpr)
									fieldMeta.type_attribute = Type.fromString(((StringLiteralExpr)mvp.getValue()).getValue());
								else
									fieldMeta.type_attribute = Type.fromString(mvp.getValue().toString());
							}
						} else
						
						if (annotationType.equals(FILTER_JOINCOLUMN)) { // for @JoinColumn
							/*if(mvp.getName().equals("referencedColumnName")){
								rel.field_ref.add(mvp.getValue().toString());
							}*/
							if(mvp.getName().equals("name")){
								rel.name = mvp.getValue().toString();
							}
						}
					}	
				}
			}
		}
		
		

		/**
		 * Check if Id annotation is present in entity
		 * 
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
				ConsoleUtils.displayDebug("\tID : " + fieldMeta.name);
			}
			
			return isId;
		}
		
		/**
		 * Check if Column annotation is present in entity
		 * 
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
				String type = "Column";
				if (annotationType.equals(FILTER_JOINCOLUMN))
					type = "Join Column";
				
				ConsoleUtils.displayDebug("\t" + type + " : " + fieldMeta.name + 
						" type of " + fieldMeta.type);
			}
			
			return isColumn;
		}

		/**
		 * Check if Relation annotation is present in entity
		 * 
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
				ConsoleUtils.displayDebug("\tRelation " + annotationType + 
						" : " + fieldMeta.name + " type of " + fieldMeta.type);
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
			methodMeta.isFinal = ModifierSet.isFinal(method.getModifiers());
			
			// Add Parameters
			List<Parameter> parameters = method.getParameters();
			if(parameters!=null){
				for(Parameter param : parameters){
					methodMeta.argumentsTypes.add(param.getType().toString());
				}
			}
			
			meta.methods.add(methodMeta);
			
			// Debug Log
			if(Harmony.debug){
				StringBuilder builder = new StringBuilder(
						String.format("\tMethod : %s %s(", methodMeta.type, methodMeta.name));
				
				for(String args : methodMeta.argumentsTypes) {
					if (args != methodMeta.argumentsTypes.get(0))
						builder.append(", ");
					
					builder.append(String.format("%s", args));
				}
					
				builder.append(")");

				ConsoleUtils.displayDebug(builder.toString());
			}
		}
	}
	
	private static class ImportVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public void visit(ImportDeclaration imp, ClassMetadata meta) {
			String impName = imp.getName().getName();
			meta.imports.add(impName);
			
			// Debug Log
			ConsoleUtils.displayDebug("\tImport : " + impName);
		}
	}
}
