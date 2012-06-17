/**
 * 
 */
package com.tactfactory.mda.android;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.tactfactory.mda.android.annotation.orm.Entity;

/**
 * @author micky
 *
 */
public class Console extends com.tactfactory.mda.android.command.Console {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		// tact-mdatest:com/tactfactory/mdatest/android:User
		String[] argProject = args[1].split(":");
		
		// If no arg
		if (argProject.length < 2) {
			System.out.print("Usage : java -jar tact-mda.jar testBundle:com/tactfactory/mdatest/android:User");
			throw new Exception("Usage Exception, please launch help !");
		}
			
		// Need Project name !!!
		projectFolder = argProject[0];
		String patchNameSpace = "src/" + argProject[1] + "/entity/";
			
		Console csl = new Console();

		if (argProject.length == 3) {
			csl.parseJavaFile(patchNameSpace + argProject[2] + ".java");
		} else {
			FilenameFilter filter = new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.endsWith(".java");
			    }
			};
			
			File dir = new File(Console.pathProject + patchNameSpace);
			String[] files = dir.list(filter);
			
			for (String filename : files) {
				csl.parseJavaFile(patchNameSpace + filename);
			}
		}

		csl.findAndExecute(args[0]);

	}

	/** Load Entity */
	private void parseJavaFile(String filename) {
        FileInputStream in = null;
        CompilationUnit cu = null;
        
		try {
			// creates an input stream for the file to be parsed
			in = new FileInputStream(Console.pathProject + filename);

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
				if (DEBUG)
					e.printStackTrace();
			}
        }
		
		if (cu != null)
			this.entities.add(cu);
	}
	
	/** Constructor */
	public Console() throws Exception {
		
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}
}
