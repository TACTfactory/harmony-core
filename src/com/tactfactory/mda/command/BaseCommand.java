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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.parser.BaseParser;
import com.tactfactory.mda.parser.ClassCompletor;
import com.tactfactory.mda.parser.java.JavaModelParser;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.TactFileUtils;

/** 
 * Common Command structure.
 */
public abstract class BaseCommand implements Command {
	/** Registered parsers for the global parser. */
	private ArrayList<BaseParser> registeredParsers 
			= new ArrayList<BaseParser>();
	
	/** Command separator. */
	protected static final String SEPARATOR = ":";
	
	/** Command arguments. */
	private HashMap<String, String> commandArgs;
	
	/** Parser. */
	private JavaModelParser javaModelParser;
	
	/**
	 * Gets the Metadatas of all the entities actually in the package entity.
	 * You can register your own bundle parsers 
	 * with the method this.javaModelParser.registerParser() 
	 */
	public void generateMetas() {
		ConsoleUtils.display(">> Search for header.ftl...");
		// Look in app/android...
		String headerPath = Harmony.getProjectAndroidPath() + "header.ftl";
		File appAndroidHeader = new File(headerPath);
		if (appAndroidHeader.exists()) {
			ConsoleUtils.display(">>>> header.ftl found in " 
								+ headerPath 
								+ ". Skipping...");
			this.loadHeaderFile(appAndroidHeader);
		} else {
			// Look in /...
			headerPath = Harmony.getRootPath() + "header.ftl";
			File baseHeader = new File(headerPath);
			if (baseHeader.exists()) {
				ConsoleUtils.display(">>>> header.ftl found in " 
								+ headerPath
								+ ". Skipping...");
				this.loadHeaderFile(baseHeader);
				
			} else {
				ConsoleUtils.display(">>>> header.ftl not found. Skipping...");
			}
		}
		
		ConsoleUtils.display(">> Analyse Models...");
		this.javaModelParser = new JavaModelParser();
		for (final BaseParser parser : this.registeredParsers) {
			this.javaModelParser.registerParser(parser);
		}
		// Parse models and load entities into CompilationUnits
		try {
			this.javaModelParser.loadEntities();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}

		// Convert CompilationUnits entities to ClassMetaData
		if (this.javaModelParser.getEntities().size() > 0) {
			for (final CompilationUnit mclass 
					: this.javaModelParser.getEntities()) {
				this.javaModelParser.parse(
						mclass, 
						ApplicationMetadata.INSTANCE);
			}
	
			// TODO : Refactor ClassCompletor
			new ClassCompletor(
					ApplicationMetadata.INSTANCE.getEntities()).execute();
		} else {
			ConsoleUtils.displayWarning("No entities found in entity package!");
		}
	}
	
	/**
	 * Register a parser to the global parser.
	 * @param parser The parser to register.
	 */
	public final void registerParser(final BaseParser parser) {
		this.registeredParsers.add(parser);
	}
	
	/**
	 * Set the command arguments.
	 * @param args The arguments.
	 */
	protected final void setCommandArgs(final HashMap<String, String> args) {
		this.commandArgs = args;
	}
	
	/**
	 * Get the command arguments.
	 * @return The arguments.
	 */
	protected final HashMap<String, String> getCommandArgs() {
		return this.commandArgs;
	}
	
	protected final void loadHeaderFile(File f) {
		String header = TactFileUtils.fileToStringBuffer(f).toString();
		ApplicationMetadata.INSTANCE.setHeaderTemplate(header);
	}
}
