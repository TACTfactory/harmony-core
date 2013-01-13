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

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.orm.ClassCompletor;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.parser.JavaModelParser;

/** 
 * Common Command structure 
 */
public abstract class BaseCommand implements Command {
	protected static final String SEPARATOR = ":";
	protected ArrayList<BaseParser> registeredParsers = new ArrayList<BaseParser>();
	
	protected HashMap<String,String> commandArgs;
	protected JavaModelParser javaModelParser;
	
	/**
	 * Gets the Metadatas of all the entities actually in the package entity
	 * You can register your own bundle parsers with the method this.javaModelParser.registerParser() 
	 * @return The metadatas of the application
	 */
	public void generateMetas(){
		ConsoleUtils.display(">> Analyse Models...");
		this.javaModelParser = new JavaModelParser();
		for(BaseParser parser : this.registeredParsers)
			this.javaModelParser.registerParser(parser);
		// Parse models and load entities into CompilationUnits
		try {
			this.javaModelParser.loadEntities();
		} catch(Exception e) {
			ConsoleUtils.displayError(e.getMessage());
		}

		// Convert CompilationUnits entities to ClassMetaData
		if (this.javaModelParser.getEntities().size() > 0) {
			for (CompilationUnit mclass : this.javaModelParser.getEntities()) {
				this.javaModelParser.parse(mclass);
			}
	
			// Generate views from MetaData 
			if (this.javaModelParser.getMetas().size() > 0) {				
				for (ClassMetadata meta : this.javaModelParser.getMetas()) {
					Harmony.metas.entities.put(meta.name, meta);
				}
				new ClassCompletor(Harmony.metas.entities).execute();
			}
		} else {
			ConsoleUtils.displayWarning("No entities found in entity package!");
		}
	}
	
	public void registerParser(BaseParser parser){
		this.registeredParsers.add(parser);
	}
}