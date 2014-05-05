/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser.java;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.InterfaceMetadata;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.parser.BaseParser;
import com.tactfactory.harmony.parser.java.visitor.FileVisitor;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Parses a group of java files.
 */
public class JavaModelParser {
	/** Java file parser. */
	private FileVisitor fileVisitor = new FileVisitor();

	/** Extension. */
	private static final String FILE_EXT =
			".java";

	/** Entity path. */
	private static final String PATH_ENTITY =
			"/entity/";


	/** Entities compilations units (used by JavaParser). */
	private List<CompilationUnit> entities =
			new ArrayList<CompilationUnit>();

	/** List of all the bundles parsers. */
	private static List<BaseParser> bundleParsers =
			new ArrayList<BaseParser>();

	/** Adapter used to retrieves entity files. */
	private final BaseAdapter adapter = new AndroidAdapter();

	/** Entity files path. */
	private final String entityPath =
			this.adapter.getSourcePath()
			+ ApplicationMetadata.INSTANCE.getProjectNameSpace()
				.replaceAll("\\.", "/")
			+ PATH_ENTITY;

	/** Filter for java files only. */
	private final FilenameFilter filter = new FilenameFilter() {
	    @Override
		public boolean accept(final File dir, final String name) {
	        return name.endsWith(FILE_EXT);
	    }
	};

	/**
	 * Register a parser to the general parser.
	 * @param parser The parser to register.
	 */
	public final void registerParser(final BaseParser parser) {
		bundleParsers.add(parser);
	}

	/**
	 * Load entity from one specified file.
	 *
	 * @param filename or path to file to parse
	 */
	public final void loadEntity(final String filename) {
		this.parseJavaFile(filename);
	}

	/**
	 * Load entities files found in entity folder.
	 * @throws Exception if no entity files were found.
	 */
	public final void loadEntities() throws Exception {
		final File dir = new File(this.entityPath);
		final String[] files = dir.list(this.filter);
		final ArrayList<String> filesNames =
				new ArrayList<String>(Arrays.asList(files));
		Collections.sort(filesNames);

		if (files == null) {
			throw new Exception("No entity files found!");
		} else {
			for (final String filename : filesNames) {
				this.parseJavaFile(this.entityPath + filename);
			}
		}
	}

	/**
	 * Parse java file to load entities parameters.
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
	/*public final List<ClassMetadata> getMetas() {
		return this.metas;
	}*/

	/**
	 * Parse the given compilation unit and convert it to a
	 * ClassMetadata.
	 * @param mclass The compilation unit
	 * @param appMetas The metadatas of the application
	 */
	public final void parse(final CompilationUnit mclass,
			final ApplicationMetadata appMetas) {
		final ArrayList<ClassMetadata> classes = this.fileVisitor.visit(mclass);

		for (ClassMetadata classMeta : classes) {
			// Add the Metadata to the general list
			appMetas.getClasses().put(classMeta.getName(), classMeta);

			// If it is an entity
			if (classMeta instanceof EntityMetadata) {
				appMetas.getEntities().put(
						classMeta.getName(), (EntityMetadata) classMeta);
			} else

			// If it is an enum
			if (classMeta instanceof EnumMetadata) {
				appMetas.getEnums().put(
						classMeta.getName(), (EnumMetadata) classMeta);
			} else

			// If it is an interface
			if (classMeta instanceof InterfaceMetadata) {
				appMetas.getInterfaces().put(
						classMeta.getName(), (InterfaceMetadata) classMeta);
			}
		}

	}

	/**
	 * Return the list containing the bundle parsers.
	 * @return The list of bundle parsers.
	 */
	public static List<BaseParser> getBundleParsers() {
		return bundleParsers;
	}
}
