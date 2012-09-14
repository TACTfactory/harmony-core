package com.tactfactory.mda.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;


public class JavaModelParser {

	protected ArrayList<CompilationUnit> entities = new ArrayList<CompilationUnit>();
	private BaseAdapter adapter = new AndroidAdapter();
	private String entityPath;
	
	/** Constructor */
	public JavaModelParser() {

		this.entityPath = this.adapter.getSourcePath() + Harmony.projectNameSpace + "entity/";
	}
	
	/**
	 * Load entity from one specified file
	 * 
	 * @param filename
	 */
	public void loadEntity(String filename)
	{
		this.parseJavaFile(this.entityPath + filename);
	}
	
	/**
	 * Load entities from all files found in entity folder
	 */
	public void loadEntities() {
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".java");
		    }
		};
		File dir = new File(this.entityPath);
		String[] files = dir.list(filter);

		for (String filename : files) {
			this.parseJavaFile(this.entityPath + filename);
		}
	}

	/** Parse java file to load entity */
	private void parseJavaFile(String filename) {
        FileInputStream in = null;
        CompilationUnit cu = null;
        
		try {
			// creates an input stream for the file to be parsed
			in = new FileInputStream(Harmony.pathProject + filename);

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
				if (Harmony.DEBUG)
					e.printStackTrace();
			}
        }
		
		if (cu != null)
			this.entities.add(cu);
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

}