package com.tactfactory.mda.android.command;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tactfactory.mda.android.annotation.orm.Entity;
import com.tactfactory.mda.android.annotation.orm.Column;
import com.tactfactory.mda.android.annotation.orm.Id;
import com.tactfactory.mda.android.annotation.orm.GeneratedValue;
import com.tactfactory.mda.android.template.ActivityGenerator;

public class OrmCommand extends Command {
	public static String GENERATE_ENTITY 	= "orm:generate:entity";
	public static String GENERATE_ENTITIES	= "orm:generate:entities";
	public static String GENERATE_FORM 		= "orm:generate:form";
	public static String GENERATE_CRUD 		= "orm:generate:crud";
	
	protected ArrayList<CompilationUnit> entities;
	protected final HashMap<String, Object> datamodel = new HashMap<String, Object>();
	
	private static class FieldVisitor extends VoidVisitorAdapter {
		@Override
		public void visit(FieldDeclaration n, Object arg) {
			ArrayList<String> fields = (ArrayList<String>) arg;
			List<AnnotationExpr> fieldAnnotations = n.getAnnotations();
			
			boolean isfind = false;
			for (AnnotationExpr annotationExpr : fieldAnnotations) {
				if (annotationExpr.getName().toString().equals(PackageUtils.extractNameEntity(Column.class)))
					isfind = true;
			}
			
			if (isfind)  {
				String name = n.getVariables().get(0).toString();
				String type = n.getType().toString();
				
				// debug
				if (com.tactfactory.mda.android.command.Console.DEBUG)
					System.out.print("\t " + n.toString() + 
							" for " + name + 
							" type of " + type +"\n");
					
				// Set Field meta
				String fieldJava = String.format("%s %s", type, name);
				fields.add(fieldJava);
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
	
	private static class ClassVisitor extends VoidVisitorAdapter<Object> {
	    @Override
	    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
	    	HashMap<String, Object> datamodel = (HashMap<String, Object>) arg;
			List<AnnotationExpr> classAnnotations = n.getAnnotations();
			
			boolean isfind = false;
			for (AnnotationExpr annotationExpr : classAnnotations) {
				if (annotationExpr.getName().toString().equals(PackageUtils.extractNameEntity(Entity.class)))
					isfind = true;
			}

			if (isfind) {
				System.out.print("\t " + n.getName() +"\n");
				
				String sclass = PackageUtils.extractNameEntity(n.getName());
				datamodel.put("name", sclass);
			}
	    }
	}
	
	@SuppressWarnings("unchecked")
	protected void generateEntity(CompilationUnit mclass) {
		String spackage = PackageUtils.extractNameSpace(mclass.getPackage().getName().toString());
		datamodel.put("namespace", spackage);
		
		new ClassVisitor().visit(mclass, datamodel);
		if (datamodel.containsKey("namespace")); {
			ArrayList<String> fields = new ArrayList<String>();
			datamodel.put("fields", fields);
			new FieldVisitor().visit(mclass, fields);
			
			new ActivityGenerator(datamodel).generate();
		}
		
		return;
	}

	protected void generateForm() {

	}

	protected void generateEntities() {
		for (CompilationUnit mclass : entities) {
			this.generateEntity(mclass);
		}
	}

	protected void generateCrud() {

	}

	@Override
	public void summary() {
		System.out.print("\n> ORM Bundle\n");
		System.out.print(GENERATE_ENTITY + "\t => Generate Entry\n");
		System.out.print(GENERATE_ENTITIES + "\t => Generate Entries\n");
		System.out.print(GENERATE_FORM + "\t => Generate Form\n");
		System.out.print(GENERATE_CRUD + "\t => Generate CRUD\n");
	}

	@Override
	public void execute(String action, ArrayList<CompilationUnit> entities) {
		System.out.print("\n> ORM Bundle\n");
		this.entities = entities;
		
		
		if (action.equals(GENERATE_ENTITY)) {
			this.generateEntity(entities.get(0));
		} else
			
		if (action.equals(GENERATE_ENTITIES)) {
			this.generateEntities();
		} else
		
		if (action.equals(GENERATE_FORM)) {
			this.generateForm();
		} else
			
		if (action.equals(GENERATE_CRUD)) {
			this.generateCrud();
		} else
			
		{
			
		}
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return (command.equals(GENERATE_ENTITY) ||
				command.equals(GENERATE_ENTITIES) ||
				command.equals(GENERATE_FORM) ||
				command.equals(GENERATE_CRUD) );
	}
}