/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.JavaAdapter;
import com.tactfactory.mda.parser.JavaModelParser;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ActivityGenerator;
import com.tactfactory.mda.template.AdapterGenerator;
import com.tactfactory.mda.template.ProviderGenerator;
import com.tactfactory.mda.template.SQLiteGenerator;
import com.tactfactory.mda.template.WebServiceGenerator;

public class OrmCommand extends BaseCommand {
	
	//bundle name
	public final static String BUNDLE = "orm";
	public final static String SUBJECT = "generate";
	
	//actions
	public final static String ACTION_ENTITY = "entity";
	public final static String ACTION_ENTITIES = "entities";
	public final static String ACTION_FORM = "form";
	public final static String ACTION_CRUD = "crud";
	
	//commands
	public static String GENERATE_ENTITY 	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITY;
	public static String GENERATE_ENTITIES	= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_ENTITIES;
	public static String GENERATE_FORM 		= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_FORM;
	public static String GENERATE_CRUD 		= BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_CRUD;
	
	//internal
	protected HashMap<String,String> command_args;
	protected BaseAdapter adapter = new AndroidAdapter();
	protected JavaModelParser javaModelParser = new JavaModelParser();

	protected void generateForm() {

	}
	
	protected void generateEntity() throws Exception {
		
		if(this.command_args.size()!=0){
			if(this.command_args.containsKey("filename"))
				this.javaModelParser.loadEntity(this.command_args.get("filename"));
			else
				throw new Exception("No file name  to parse specified!");
		}
		
		JavaAdapter javaAdapter = new JavaAdapter();
		javaAdapter.parse(this.javaModelParser.getEntities().get(0));

		ArrayList<ClassMetadata> metas = javaAdapter.getMetas();
		try {
			new ActivityGenerator(metas.get(0), this.adapter).generateAllAction();
			new AdapterGenerator(metas.get(0), this.adapter).generate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void generateEntities() {
		// Info Log
		System.out.print(">> Analyse Models...\n");

		this.javaModelParser.loadEntities();
		
		JavaAdapter javaAdapter = new JavaAdapter();
		
		for (CompilationUnit mclass : this.javaModelParser.getEntities()) {
			javaAdapter.parse(mclass);
		}
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\n");
		
		// Make View
		ArrayList<ClassMetadata> metas = javaAdapter.getMetas();
		for (ClassMetadata meta : metas) {
			try {
				new ActivityGenerator(meta, this.adapter).generateAllAction();
				new AdapterGenerator(meta, this.adapter).generate();
				new WebServiceGenerator();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Make Database
		try {
			new SQLiteGenerator(metas, this.adapter).generateDatabase();
			new ProviderGenerator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void generateCrud() {

	}

	@Override
	public void summary() {
		System.out.print("\n> ORM Bundle\n");
		System.out.print("\t" + GENERATE_ENTITY + "\t => Generate Entry\n");
		System.out.print("\t" + GENERATE_ENTITIES + "\t => Generate Entries\n");
		System.out.print("\t" + GENERATE_FORM + "\t => Generate Form\n");
		System.out.print("\t" + GENERATE_CRUD + "\t => Generate CRUD\n");
	}

	@Override
	public void execute(String action, String[] args, String option) {
		System.out.print("> ORM Generator\n");

		//manage arguments
		if(args.length!=0 && args!=null){
			this.command_args = new HashMap<String,String>();
			for(String arg : args){
				if(arg.startsWith("--")){
					String[] key = arg.split("=");
					if(key.length>1)
						this.command_args.put(key[0].substring(2),key[1]);
				}
			}
		}
		
		if (action.equals(GENERATE_ENTITY)) {
			//TODO Transmit entity filename from console args !!!!
			try {
				this.generateEntity();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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