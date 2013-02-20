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

public class YamlModelParser {
	
    public void loadEntities(final String filename) {
    	InputStream in = null;
		try {
			in = new FileInputStream(filename);
	        final Yaml yaml = new Yaml();
	        yaml.load(in);
	        
	        // TODO parse given Object to CompilationUnit
	        
			in.close();
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			ConsoleUtils.displayError(e);
		}
    }
}
