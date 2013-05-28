/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

import com.tactfactory.mda.utils.ConsoleUtils;


/**
 * YamlModelParser.
 * Parser for Yaml model description files
 */
public class YamlModelParser {
	
	
	/**
	 * Load the entities in the Yaml file <filename>.
	 * @param filename The filename to load.
	 */
    public final void loadEntities(final String filename) {
    	InputStream inStream = null;
		try {
			inStream = new FileInputStream(filename);
	        final Yaml yaml = new Yaml();
	        yaml.load(inStream);
	        
	        // TODO parse given Object to CompilationUnit
	        
			inStream.close();
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
    }
}
