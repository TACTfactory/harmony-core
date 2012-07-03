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

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.orm.JavaAdapter;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.ActivityGenerator;

public class OrmCommand extends BaseCommand {
	public static String GENERATE_ENTITY 	= "orm:generate:entity";
	public static String GENERATE_ENTITIES	= "orm:generate:entities";
	public static String GENERATE_FORM 		= "orm:generate:form";
	public static String GENERATE_CRUD 		= "orm:generate:crud";
	
	protected ArrayList<CompilationUnit> entities;
	protected BaseAdapter adapter = new AndroidAdapter();

	protected void generateForm() {

	}
	
	protected void generateEntities() {
		// Info Log
		System.out.print(">> Analyse Models...\n");
				
		JavaAdapter adapter = new JavaAdapter();
		
		for (CompilationUnit mclass : entities) {
			adapter.parse(mclass);
		}
		
		// Debug Log
		if (Harmony.DEBUG)
			System.out.print("\n");
		
		ArrayList<ClassMetadata> metas = adapter.getMetas();
		for (ClassMetadata meta : metas) {
			try {
				new ActivityGenerator(meta, this.adapter).generateAllAction();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		System.out.print("> ORM Generator\n");
		this.entities = entities;
		
		
		if (action.equals(GENERATE_ENTITY)) {
			JavaAdapter adapter = new JavaAdapter();
			adapter.parse(entities.get(0));
						
			ArrayList<ClassMetadata> metas = adapter.getMetas();
			try {
				new ActivityGenerator(metas.get(0), this.adapter).generateAllAction();
			} catch (Exception e) {
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