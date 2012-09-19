package com.tactfactory.mda.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;

public class YamlModelParser {

	public YamlModelParser(){
		
	}
	
    public void loadEntities(String filename) {
    	InputStream in = null;
		try {
			in = new FileInputStream(filename);
	        Yaml yaml = new Yaml();
	        yaml.load(in);
	        
	        // TODO parse given Object to CompilationUnit
	        
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
