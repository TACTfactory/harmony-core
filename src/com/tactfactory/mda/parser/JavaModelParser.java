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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.tactfactory.mda.annotation.Column;
import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.annotation.Entity;
import com.tactfactory.mda.annotation.Id;
import com.tactfactory.mda.annotation.JoinColumn;
import com.tactfactory.mda.annotation.ManyToMany;
import com.tactfactory.mda.annotation.ManyToOne;
import com.tactfactory.mda.annotation.OneToMany;
import com.tactfactory.mda.annotation.OneToOne;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.FieldMetadata;
import com.tactfactory.mda.meta.MethodMetadata;
import com.tactfactory.mda.meta.RelationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.PackageUtils;

/**
 * Parses a group of java files
 * @author Gregg Cesarine
 *
 */
public class JavaModelParser {
	/** Extension. */
	private static final String FILE_EXT =
			".java";
	
	/** Entity path. */
	private static final String PATH_ENTITY = 
			"/entity/";
	

	/** Entity annotation name. */
	private static final String FILTER_ENTITY	 	= 
			PackageUtils.extractNameEntity(Entity.class);
	
	/** Id annotation name. */
	private static final String FILTER_ID	 		= 
			PackageUtils.extractNameEntity(Id.class);
	
	/** Column annotation name. */
	private static final String FILTER_COLUMN 		= 
			PackageUtils.extractNameEntity(Column.class);
	
	/** JoinColumn annotation name. */
	private static final String FILTER_JOINCOLUMN 	= 
			PackageUtils.extractNameEntity(JoinColumn.class);
	
	/** OneToOne annotation name. */
	private static final String FILTER_ONE2ONE 		= 
			PackageUtils.extractNameEntity(OneToOne.class);
	
	/** OneToMany annotation name. */
	private static final String FILTER_ONE2MANY 	= 
			PackageUtils.extractNameEntity(OneToMany.class);
	
	/** ManyToOne annotation name. */
	private static final String FILTER_MANY2ONE 	= 
			PackageUtils.extractNameEntity(ManyToOne.class);
	
	/** ManyToMany annotation name. */
	private static final String FILTER_MANY2MANY 	= 
			PackageUtils.extractNameEntity(ManyToMany.class);


	/** Entities compilations units (used by JavaParser). */
	private List<CompilationUnit> entities = 
			new ArrayList<CompilationUnit>();
	
	/** Entity metadatas. */
	private final List<ClassMetadata> metas =
			new ArrayList<ClassMetadata>();
	
	/** List of all the bundles parsers. */
	private final List<BaseParser> bundleParsers = 
			new ArrayList<BaseParser>();
	
	/** Adapter used to retrieves entity files. */
	private final BaseAdapter adapter = new AndroidAdapter();
	
	/** Entity files path. */
	private final String entityPath = this.adapter.getSourcePath() + ApplicationMetadata.INSTANCE.projectNameSpace.replaceAll("\\.", "/") + PATH_ENTITY;
	
	/** Filter for java files only*/
	private final FilenameFilter filter = new FilenameFilter() {
	    @Override
		public boolean accept(final File dir, final String name) {
	        return name.endsWith(FILE_EXT);
	    }
	};
	
	/**
	 * Register a parser to the general parser.
	 * @param parser
	 */
	public final void registerParser(final BaseParser parser) {
		this.bundleParsers.add(parser);
	}
	
	/**
	 * Load entity from one specified file
	 * 
	 * @param filename or path to file to parse
	 */
	public final void loadEntity(final String filename) {
		this.parseJavaFile(filename);
	}
	
	/**
	 * Load entities files found in entity folder
	 */
	public final void loadEntities() throws Exception {
		final File dir = new File(this.entityPath);
		final String[] files = dir.list(this.filter);
		
		if (files == null) {
			throw new Exception("No entity files found!");
		} else {
			for (final String filename : files) {
				this.parseJavaFile(this.entityPath + filename);
			}
		}
	}

	/**
	 * Parse java file to load entities parameters
	 * 
	 * @param filename or path to the java file to parse
	 */
	private void parseJavaFile(final String filename) {
        FileInputStream in = null;
        CompilationUnit cu = null;
        
        if (new File(filename).exists()) {
			try {
				// creates an input stream for the file to be parsed
				in = new FileInputStream(filename);
	
	            // parse the file
				cu = JavaParser.parse(in);
	        } catch (final ParseException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			} catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			} finally {
	            try {
	            	if (in != null) {
	            		in.close();
	            	}
				} catch (final IOException e) {
					ConsoleUtils.displayError(e);
				}
	        }
			
			if (cu != null) {
				this.entities.add(cu);
			}
        } else {
        	ConsoleUtils.displayWarning("Given model file doesn't exist!");
        }
	}

	/**
	 * @return the entities
	 */
	public final List<CompilationUnit> getEntities() {
		return this.entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public final void setEntities(final List<CompilationUnit> entities) {
		this.entities = entities;
	}
	
	/**
	 * @return the appMetas
	 */
	public final List<ClassMetadata> getMetas() {
		return this.metas;
	}
	
	public final void parse(final CompilationUnit mclass) {
		final String spackage = PackageUtils.extractNameSpace(mclass.getPackage().getName().toString());
		if (!Strings.isNullOrEmpty(spackage)) {
			final ClassMetadata meta = new ClassMetadata();
			meta.space = spackage;
			
			new ClassVisitor().visit(mclass, meta);
			if (!Strings.isNullOrEmpty(meta.name)) {
				new ImportVisitor().visit(mclass, meta);
				new FieldVisitor().visit(mclass, meta);
				new MethodVisitor().visit(mclass, meta);
				
				this.metas.add(meta);
			}
		}
	}
	
	private class ClassVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
	    @Override
	    public final void visit(final ClassOrInterfaceDeclaration n, final ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for (final BaseParser bParser : JavaModelParser.this.bundleParsers) {
	    		
	    		bParser.visitClass(n, meta);
	    	}

	    	final List<AnnotationExpr> classAnnotations = n.getAnnotations();
			if (classAnnotations != null) {
				for (final AnnotationExpr annotationExpr : classAnnotations) {

					for (final BaseParser bParser : JavaModelParser.this.bundleParsers) {
			    		bParser.visitClassAnnotation(meta, annotationExpr);
					}
					
					final String annotationType = annotationExpr.getName().toString();
					if (annotationType.equals(FILTER_ENTITY)) {
						meta.name = PackageUtils.extractNameEntity(n.getName());
						
						// Check reserved keywords
						SqliteAdapter.Keywords.exists(meta.name);
						
						// Debug Log
						ConsoleUtils.displayDebug("Entity : " 
								+ meta.space 
								+ ".entity." 
								+  meta.name);
						
						//break;
					}
				}
				
				if (!Strings.isNullOrEmpty(meta.name)) {
					// Get list of Implement type
					final List<ClassOrInterfaceType> impls = n.getImplements();
					if (impls != null) {
						for (final ClassOrInterfaceType impl : impls) {
							meta.implementTypes.add(impl.getName());
							
							// Debug Log
							ConsoleUtils.displayDebug("\tImplement : " 
										+ impl.getName());
						}
					}
					
					// Get Extend type
					final List<ClassOrInterfaceType> exts = n.getExtends();
					if (exts != null) {
						for (final ClassOrInterfaceType ext : exts) {			
							meta.extendType = ext.getName();		
							
							// Debug Log
							ConsoleUtils.displayDebug("\tExtend : " 
										+ ext.getName());
						}
					}
					
					// Get list of Members
					/*List<BodyDeclaration> members = n.getMembers();
					if (members != null) {
						for (BodyDeclaration member : members) {
							//TODO Micky > Good or Trash ? [Gregg]
							meta.members.add(member.getName());	
						}
					}*/
				}
			}
	    }
	}

	public class FieldVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public final void visit(final FieldDeclaration field, 
				final ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for (final BaseParser bParser 
	    			: JavaModelParser.this.bundleParsers) {
	    		bParser.visitField(field, meta);
	    	}
			
			final List<AnnotationExpr> fieldAnnotations 
					= field.getAnnotations();
			
			if (fieldAnnotations != null) {
				// General (required !)
				final FieldMetadata fieldMeta = new FieldMetadata(meta);
			
				fieldMeta.type = Type.toTypeString(field.getType().toString());
				
				// Java types Date and Time are deprecated in Harmony
				if (fieldMeta.type.equalsIgnoreCase("date") 
						|| fieldMeta.type.equalsIgnoreCase("time")) {
					ConsoleUtils.displayWarning(
							"You should use DateTime java type instead of " 
							+ fieldMeta.type
							+ ". Errors may occur.");
				}
				//fieldMeta.isFinal = ModifierSet.isFinal(field.getModifiers());
				// FIXME not manage multi-variable
				fieldMeta.name = 
						field.getVariables().get(0).getId().getName(); 
				fieldMeta.columnName = fieldMeta.name;

				// Set defaults values
				fieldMeta.hidden = false;
								
				// Database definitions
				final RelationMetadata rel = new RelationMetadata();
				boolean isColumn = false;
				boolean isId = false;
				boolean isRelation = false;
				
				// Analyze
				for (final AnnotationExpr annotationExpr : fieldAnnotations) {
					final String annotationType = 
							annotationExpr.getName().toString();
					

			    	for (final BaseParser bParser 
			    			: JavaModelParser.this.bundleParsers) {
			    		bParser.visitFieldAnnotation(fieldMeta,
			    				annotationExpr,
			    				meta);
			    	}
	
					isId = this.isId(fieldMeta, isId, annotationType);
					isColumn = this.isColumn(fieldMeta,
							isColumn, 
							annotationType);
					
					isRelation = this.isRelation(fieldMeta, 
							isRelation, 
							annotationType);
					
					if (annotationType.equals(FILTER_ONE2ONE)	
							|| annotationType.equals(FILTER_ONE2MANY)	
							|| annotationType.equals(FILTER_MANY2ONE)	
							|| annotationType.equals(FILTER_MANY2MANY)) {
						rel.type = annotationType;
					}
					
					this.loadAttributes(rel,
							fieldMeta, 
							annotationExpr, 
							annotationType);
					
					// Set default values for type if type is recognized
					final Type type = Type.fromName(fieldMeta.type);
					if (type != null) {
						fieldMeta.type = type.getValue();
						if (fieldMeta.nullable == null) {
							fieldMeta.nullable = type.isNullable();
						}
						if (fieldMeta.unique == null) {
							fieldMeta.unique = type.isUnique();
						}
						if (fieldMeta.length == null) {
							fieldMeta.length = type.getLength();
						}
						if (fieldMeta.precision == null) {
							fieldMeta.precision = type.getPrecision();
						}
						if (fieldMeta.scale == null) {
							fieldMeta.scale = type.getScale();
						}
						if (fieldMeta.isLocale == null) {
							fieldMeta.isLocale = type.isLocale();
						}
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
					rel.entityRef = PackageUtils.extractClassNameFromArray(field.getType().toString());
					fieldMeta.relation = rel;
					meta.relations.put(fieldMeta.name, fieldMeta);
				}

				// Adjust databases column definition
				if (Strings.isNullOrEmpty(fieldMeta.columnDefinition)) {
					fieldMeta.columnDefinition = SqliteAdapter.generateColumnDefinition(fieldMeta.type);
				}
				fieldMeta.columnDefinition = SqliteAdapter.generateColumnType(fieldMeta.columnDefinition);
				
				// Add to meta dictionary
				if (isId || isColumn || isRelation) {
					meta.fields.put(fieldMeta.name, fieldMeta);
				}
				
				// Check SQLite reserved keywords
				SqliteAdapter.Keywords.exists(fieldMeta.name);
				if (!fieldMeta.name.equals(fieldMeta.columnName)) {
					SqliteAdapter.Keywords.exists(fieldMeta.columnName);
				}
				SqliteAdapter.Keywords.exists(fieldMeta.columnDefinition);
				SqliteAdapter.Keywords.exists(fieldMeta.type);
			}
					
		}

		/**
		 * @param fieldMeta
		 * @param annotationExpr
		 * @param annotationType
		 */
		private void loadAttributes(final RelationMetadata rel, final FieldMetadata fieldMeta, final AnnotationExpr annotationExpr, final String annotationType) {
			if (annotationExpr instanceof NormalAnnotationExpr) {
				final NormalAnnotationExpr norm = (NormalAnnotationExpr) annotationExpr;
				
				if (norm.getPairs() != null) {
					for (final MemberValuePair mvp : norm.getPairs()) { // Check if there are any arguments in the annotation
						
						// Argument of Annotation Column
						if (annotationType.equals(FILTER_COLUMN)) { 
							// set nullable
							if (mvp.getName().equals("nullable")  
									&& mvp.getValue().toString().equals("true")) {
								fieldMeta.nullable = true;
							} else 
								
							// set name
							if (mvp.getName().equals("name")) {
								if (mvp.getValue() instanceof StringLiteralExpr) {
									fieldMeta.columnName = ((StringLiteralExpr) mvp.getValue()).getValue();
								} else {
									fieldMeta.columnName = mvp.getValue().toString();
								}
							} else 
								
							// Set unique 
							if (mvp.getName().equals("unique")  
									&& mvp.getValue().toString().equals("true")) {
								fieldMeta.unique = true;
							} else 
								
							// set length
							if (mvp.getName().equals("length")) {
								fieldMeta.length = Integer.parseInt(mvp.getValue().toString());
							} else 
								
							// set precision
							if (mvp.getName().equals("precision")) {
								fieldMeta.precision = Integer.parseInt(mvp.getValue().toString());
							} else 
								
							// set scale
							if (mvp.getName().equals("scale")) {
								fieldMeta.scale = Integer.parseInt(mvp.getValue().toString());
							} else
								
							// set scale
							if (mvp.getName().equals("locale")) {
								fieldMeta.isLocale = Boolean.parseBoolean(mvp.getValue().toString());
							} else 
								
							// set column definition
							if (mvp.getName().equals("type")) {
								//TODO : Generate warning if type not recognized
								String type = "";
								
								if (mvp.getValue() instanceof StringLiteralExpr) {
									type = ((StringLiteralExpr) mvp.getValue()).getValue();
								} else {
									type = mvp.getValue().toString();
								}
								
								fieldMeta.type = Type.fromName(type).getValue();
							} else
								
							// set scale
							if (mvp.getName().equals("columnDefinition")) {
								if (mvp.getValue() instanceof StringLiteralExpr) {
									fieldMeta.columnDefinition = ((StringLiteralExpr) mvp.getValue()).getValue();
								} else {
									fieldMeta.columnDefinition = mvp.getValue().toString();
								}
							} else 
						
							// set if hide column view
							if (mvp.getName().equals("hidden") && mvp.getValue().toString().equals("true")) {
								fieldMeta.hidden = true;
							}
							
						} else
						
						if (annotationType.equals(FILTER_JOINCOLUMN)) { // for @JoinColumn
							if (mvp.getName().equals("name")) {
								rel.name = ((StringLiteralExpr) mvp.getValue()).getValue();
							}
						} else
							
						if (annotationType.equals(FILTER_ONE2MANY)) {
							if (mvp.getName().equals("mappedBy")) {
								rel.mappedBy = ((StringLiteralExpr) mvp.getValue()).getValue();
							}
						} else
						
						if (annotationType.equals(FILTER_MANY2ONE)) {
							if (mvp.getName().equals("inversedBy")) {
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
		private boolean isId(final FieldMetadata fieldMeta, final boolean old, final String annotationType) {
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
		private boolean isColumn(final FieldMetadata fieldMeta, 
				final boolean old, 
				final String annotationType) {
			boolean isColumn = old;
			
			if (annotationType.equals(FILTER_COLUMN)	
				|| annotationType.equals(FILTER_JOINCOLUMN)) {
				
				isColumn = true;
				
				// Debug Log
				String type = "Column";
				if (annotationType.equals(FILTER_JOINCOLUMN)) {
					type = "Join Column";
				}
				
				ConsoleUtils.displayDebug("\t" + type + " : " + fieldMeta.name
						+ " type of " + fieldMeta.type);
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
		private boolean isRelation(final FieldMetadata fieldMeta, 
				final boolean old, 
				final String annotationType) {
			boolean isRelation = old;
			
			if (annotationType.equals(FILTER_ONE2ONE)	
				|| annotationType.equals(FILTER_ONE2MANY)
				|| annotationType.equals(FILTER_MANY2ONE)
				|| annotationType.equals(FILTER_MANY2MANY)) {
				isRelation = true;
				
				// Debug Log
				ConsoleUtils.displayDebug("\tRelation " 
						+ annotationType 
						+ " : " 
						+ fieldMeta.name 
						+ " type of " 
						+ fieldMeta.type);
			}
			
			return isRelation;
		}
	}
	
	private class MethodVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public void visit(final MethodDeclaration method, final ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for (final BaseParser bParser : JavaModelParser.this.bundleParsers) {
	    		bParser.visitMethod(method, meta);
	    	}
			
			final MethodMetadata methodMeta = new MethodMetadata();
			methodMeta.name = method.getName();
			methodMeta.type = method.getType().toString(); 
			methodMeta.isFinal = ModifierSet.isFinal(method.getModifiers());
			
			// Add Parameters
			final List<Parameter> parameters = method.getParameters();
			if (parameters != null) {
				for (final Parameter param : parameters) {
					methodMeta.argumentsTypes.add(param.getType().toString());
				}
			}
			
			meta.methods.add(methodMeta);
			
			// Debug Log
			if (ConsoleUtils.isDebug()) {
				final StringBuilder builder = new StringBuilder(
						String.format("\tMethod : %s %s(", methodMeta.type, methodMeta.name));
				
				for (final String args : methodMeta.argumentsTypes) {
					if (!args.equals(methodMeta.argumentsTypes.get(0))) {
						builder.append(", ");
					}
					
					builder.append(String.format("%s", args));
				}
					
				builder.append(')');

				ConsoleUtils.displayDebug(builder.toString());
			}
		}
	}
	
	private class ImportVisitor extends VoidVisitorAdapter<ClassMetadata> {
		
		@Override
		public void visit(final ImportDeclaration imp, final ClassMetadata meta) {
	    	// Call the parsers which have been registered by the bundle
	    	for (final BaseParser bParser : JavaModelParser.this.bundleParsers) {
	    		bParser.visitImport(imp, meta);
	    	}
	    	
			final String impName = imp.getName().getName();
			meta.imports.add(impName);
			
			// Debug Log
			ConsoleUtils.displayDebug("\tImport : " + impName);
		}
	}

}
