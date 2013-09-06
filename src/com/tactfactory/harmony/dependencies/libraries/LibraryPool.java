package com.tactfactory.harmony.dependencies.libraries;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Library Pool. Contains all the libraries brought by 
 * the core and all the bundles.
 */
public class LibraryPool {
	/** File filter to parse only libraries. */
	private final FileFilter filter = new FileFilter() {
		
		@Override
		public boolean accept(File pathname) {
			//return pathname.isDirectory() 
			//		|| pathname.getName().endsWith(".jar");
			return pathname.isDirectory()
					|| (pathname.getAbsolutePath().lastIndexOf("lib/")
							> pathname.getAbsolutePath().lastIndexOf("vendor/"));
		}
	};
	/** Map of all the libraries <FileName, File>. */
	private final Map<String, File> pool = new HashMap<String, File>();
	
	/**
	 * Get the File pointing to the library file.
	 * @param fileName The filename of the library
	 * @return The library File
	 */
	public File getLibrary(String fileName) {
		return this.pool.get(fileName);
	}
	
	/**
	 * Parse the given file to find all the available libraries in 
	 * order to fill the pool.
	 * @param file The folder to parse
	 */
	public void parseLibraries(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles(this.filter);
				for (File f : files) {
					this.parseLibraries(f);
				}
			} else if (file.isFile()) {
				String path = file.getAbsolutePath();
				String fileName = path.substring(path.lastIndexOf("lib/") + 4);
				if (this.pool.get(fileName) != null) {
					ConsoleUtils.displayWarning(
							"The library " 
							+ fileName
							+ " has been found multiple times."
							+ "Keeping "
							+ this.pool.get(fileName).getAbsolutePath());
				} else {
					ConsoleUtils.displayDebug("Adding library " + fileName
							+ " to the pool.");
					this.pool.put(fileName, file);
				}
			}
			
		}
	}
}
