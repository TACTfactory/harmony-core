/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.annotation.*;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.MethodMetadata;
import com.tactfactory.mda.meta.RelationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.utils.PackageUtils;

public class JavaModelParser {
	private static final String FILE_EXT = ".java";
	private static final String PATH_ENTITY = "/entity/";
	

	private static final String FILTER_ENTITY	 	= PackageUtils.extractNameEntity(Entity.class);
	private static final String FILTER_ID	 		= PackageUtils.extractNameEntity(Id.class);
	private static final String FILTER_COLUMN 		= PackageUtils.extractNameEntity(Column.class);
	private static final String FILTER_JOINCOLUMN 	= PackageUtils.extractNameEntity(JoinColumn.class);
	private static final String FILTER_ONE2ONE 		= PackageUtils.extractNameEntity(OneToOne.class);
	private static final String FILTER_ONE2MANY 	= PackageUtils.extractNameEntity(OneToMany.class);
	private static final String FILTER_MANY2ONE 	= PackageUtils.extractNameEntity(ManyToOne.class);
	private static final String FILTER_MANY2MANY 	= PackageUtils.extractNameEntity(ManyToMany.class);


	private ArrayList<CompilationUnit> entities = new ArrayList<CompilationUnit>();
	private ArrayList<ClassMetadata> metas = new ArrayList<ClassMetadata>();
	private ArrayList<BaseParser> bundleParsers = new ArrayList<BaseParser>();
	private BaseAdapter adapter = new AndroidAdapter();
	private String entityPath;
	
	private FilenameFilter filter = new FilenameFilter() {
	    public boolean accept(File dir, String name) {
	        return name.endsWith(FILE_EXT);
	    }
	};
	
	/**
	 * Constructor
	 */
	public JavaModelParser() {
		this.entityPath = this.adapter.getSourcePath() + Harmony.metas.projectNameSpace.replaceAll("\\.", "/") + PATH_ENTITY;
	}
	
	public void registerParser(BaseParser parser){
		this.bundleParsers.add(parser);
	}
	
	/**
	 * Load entity from one specified file
	 * 
	 * @param filename or path to file to parse
	 */
	public void loadEntity(String filename) {
		this.parseJavaFile(filename);
	}
	
	/**
	 * Load entities files found in entity folder
	 */
	public void loadEntities() throws Exception {
		File dir = new File(this.entityPath);
		String[] files = dir.list(this.filter);
		
		if(files!=null) {
			for (String filename : files) {
				this.parseJavaFile(this.entityPath + filename);
			}
		} else {
			throw new Exception("No entity files found!");
		}
	}

	/**
	 * Parse java file to load entities parameters
	 * 
	 * @param filename or path to the java file to parse
	 */
	private void parseJavaFile(String filename) {
        FileInputStream in = null;
        CompilationUnit cu = null;
        
        if(new File(filename).exists())
        {
			try {
				// creates an input stream for the file to be parsed
				in = new FileInputStream(filename);
	
	            // parse the file
				cu = JavaParser.parse(in);
	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            try {
					in.close();
				} catch (IOException e) {
					if (Harmony.debug)
						e.printStackTrace();
				}
	        }
			
			if (cu != null)
				this.entities.add(cu);
        } else {
        	ConsoleUtils.displayWarning("Given model file doesn't exist!");
        }
	}

	/**
	 * @return the entities
	 */
	public ArrayList<CompilationUnit> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(ArrayList<CompilationUnit> entities) {
		this.entities = entities;
	}
	
	/**
	 * @return the appMetas
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
	
	private class ClassVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
	    @Override
	    public void visit(ClassOrInterfaceDeclaration n, ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for(BaseParser b_parser : bundleParsers){
	    		b_parser.visitClass(n, meta);
	    	}

	    	List<AnnotationExpr> classAnnotations = n.getAnnotations();
			if (classAnnotations!=null) {
				for (AnnotationExpr annotationExpr : classAnnotations) {

					for(BaseParser b_parser : bundleParsers)
			    		b_parser.visitClassAnnotation(meta, annotationExpr);
					
					String annotationType = annotationExpr.getName().toString();
					if (annotationType.equals(FILTER_ENTITY)) {
						meta.name = PackageUtils.extractNameEntity(n.getName());
						
						// Check reserved keywords
						SqliteAdapter.Keywords.exists(meta.name);
						
						// Debug Log
						ConsoleUtils.displayDebug("Entity : " + meta.space + ".entity." +  meta.name);
						
						//break;
					}
				}
				
				if (!Strings.isNullOrEmpty(meta.name)) {
					// Get list of Implement type
					List<ClassOrInterfaceType> impls = n.getImplements();
					if(impls!=null){
						for(ClassOrInterfaceType impl : impls){					
							meta.implementTypes.add(impl.getName());
							
							// Debug Log
							ConsoleUtils.displayDebug("\tImplement : " + impl.getName());
						}
					}
					
					// Get Extend type
					List<ClassOrInterfaceType> exts = n.getExtends();
					if(exts!=null){
						for(ClassOrInterfaceType ext : exts){					
							meta.extendType = ext.getName();		
							
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

	public class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public void visit(FieldDeclaration field, ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for(BaseParser b_parser : bundleParsers)
	    		b_parser.visitField(field, meta);
			
			List<AnnotationExpr> fieldAnnotations = field.getAnnotations();
			
			if (fieldAnnotations != null) {
				// General (required !)
				FieldMetadata fieldMeta = new FieldMetadata(meta);
			
				fieldMeta.type = Type.toTypeString(field.getType().toString());
				
				// Java types Date and Time are deprecated in Harmony
				if(fieldMeta.type.toLowerCase().equals("date") || fieldMeta.type.toLowerCase().equals("time")){
					ConsoleUtils.displayWarning("You should use DateTime java type instead of "+fieldMeta.type+". Errors may occur.");
				}
				//fieldMeta.isFinal = ModifierSet.isFinal(field.getModifiers());
				fieldMeta.name = field.getVariables().get(0).getId().getName(); // FIXME not manage multi-variable
				fieldMeta.columnName = fieldMeta.name;

				// Set defaults values
				fieldMeta.hidden = false;
								
				// Database definitions
				RelationMetadata rel = new RelationMetadata();
				boolean isColumn = false;
				boolean isId = false;
				boolean isRelation = false;
				
				// Analyze
				for (AnnotationExpr annotationExpr : fieldAnnotations) {
					String annotationType = annotationExpr.getName().toString();
					

			    	for(BaseParser b_parser : bundleParsers)
			    		b_parser.visitFieldAnnotation(fieldMeta, annotationExpr, meta);
	
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
					
					// Set default values for type if type is recognized
					Type type = Type.fromName(fieldMeta.type);
					if (type != null) {
						fieldMeta.type = type.getValue();
						if(fieldMeta.nullable == null)
							fieldMeta.nullable = type.isNullable();
						if(fieldMeta.unique == null)
							fieldMeta.unique = type.isUnique();
						if(fieldMeta.length == null)
							fieldMeta.length = type.getLength();
						if(fieldMeta.precision == null)
							fieldMeta.precision = type.getPrecision();
						if(fieldMeta.scale == null)
							fieldMeta.scale = type.getScale();		
						if(fieldMeta.isLocale == null)
							fieldMeta.isLocale = type.isLocale();		
					}
				}
				
				// ID relation
				if (isId) {
					fieldMeta.id = true;
					meta.ids.put(fieldMeta.name, fieldMeta);
				}
	
				// Object relation
				if (isRelation) {
					rel.field = fieldMeta.name;
					rel.entity_ref = PackageUtils.extractClassNameFromArray(field.getType().toString());
					fieldMeta.relation = rel;
					meta.relations.put(fieldMeta.name, fieldMeta);
				}

				// Adjust databases column definition
				if (Strings.isNullOrEmpty(fieldMeta.columnDefinition)) {
					fieldMeta.columnDefinition = SqliteAdapter.generateColumnDefinition(fieldMeta.type);
				}
				fieldMeta.columnDefinition = SqliteAdapter.generateColumnType(fieldMeta);
				
				// Add to meta dictionary
				if (isId || isColumn || isRelation)
					meta.fields.put(fieldMeta.name, fieldMeta);
				
				// Check SQLite reserved keywords
				SqliteAdapter.Keywords.exists(fieldMeta.name);
				if(!fieldMeta.name.equals(fieldMeta.columnName))
					SqliteAdapter.Keywords.exists(fieldMeta.columnName);
				SqliteAdapter.Keywords.exists(fieldMeta.columnDefinition);
				SqliteAdapter.Keywords.exists(fieldMeta.type);
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
									fieldMeta.columnName = ((StringLiteralExpr)mvp.getValue()).getValue();
								else
									fieldMeta.columnName = mvp.getValue().toString();
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
								
							// set scale
							if (mvp.getName().equals("locale")) {
								fieldMeta.isLocale = Boolean.parseBoolean(mvp.getValue().toString());
							}else 
								
							// set column definition
							if (mvp.getName().equals("type")) {
								//TODO : Generate warning if type not recognized
								String type = "";
								
								if(mvp.getValue() instanceof StringLiteralExpr)
									type = ((StringLiteralExpr)mvp.getValue()).getValue();
								else
									type = mvp.getValue().toString();
								
								fieldMeta.type = Type.fromName(type).getValue();
							}else
								
							// set scale
							if (mvp.getName().equals("columnDefinition")) {
								if(mvp.getValue() instanceof StringLiteralExpr)
									fieldMeta.columnDefinition = ((StringLiteralExpr)mvp.getValue()).getValue();
								else
									fieldMeta.columnDefinition = mvp.getValue().toString();
							} else 
						
							// set if hide column view
							if (mvp.getName().equals("hidden") && mvp.getValue().toString().equals("true")){
								fieldMeta.hidden = true;
							}
							
						} else
						
						if (annotationType.equals(FILTER_JOINCOLUMN)) { // for @JoinColumn
							if(mvp.getName().equals("name")){
								rel.name = ((StringLiteralExpr)mvp.getValue()).getValue();
							}
						} else
							
						if (annotationType.equals(FILTER_ONE2MANY)){
							if(mvp.getName().equals("mappedBy")){
								rel.mappedBy = ((StringLiteralExpr)mvp.getValue()).getValue();
							}
						} else
						
						if (annotationType.equals(FILTER_MANY2ONE)){
							if(mvp.getName().equals("inversedBy")){
								rel.inversedBy = mvp.getValue().toString();
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
			
			if (annotationType.equals(FILTER_ID)) {
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
	
	private class MethodVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public void visit(MethodDeclaration method, ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for(BaseParser b_parser : bundleParsers)
	    		b_parser.visitMethod(method, meta);
			
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
	
	private class ImportVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public void visit(ImportDeclaration imp, ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for(BaseParser b_parser : bundleParsers)
	    		b_parser.visitImport(imp, meta);
	    	
			String impName = imp.getName().getName();
			meta.imports.add(impName);
			
			// Debug Log
			ConsoleUtils.displayDebug("\tImport : " + impName);
		}
	}

}
