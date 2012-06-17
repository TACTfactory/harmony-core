package com.tactfactory.mda.android.command;

import java.io.File;
import java.io.IOException;

public class FileUtils {
	
	public static File makeFile(String filename) {
		File file = new File(filename);
		
		File parent = file.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		}
	
		
		boolean success = false;
		try {
			success = file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (success) {
	        // File did not exist and was created
	    } else {
	        // File already exists
	    }
		
		return file;
	}

}
