package com.tactfactory.harmony.dependencies.libraries;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.OsUtil;

/**
 * Library Pool. Contains all the libraries brought by 
 * the core and all the bundles.
 */
public class LibraryPool {
	/** File filter to parse only libraries. */
	private final FileFilter filter = new FileFilter() {
		
		@Override
		public boolean accept(final File pathname) {
			//return pathname.isDirectory() 
			//		|| pathname.getName().endsWith(".jar");
			return pathname.isDirectory()
					|| (pathname.getAbsolutePath().lastIndexOf("lib" + File.separator)
						> pathname.getAbsolutePath().lastIndexOf("vendor" + File.separator));
		}
	};
	/** Map of all the libraries <FileName, File>. */
	private final Map<String, File> pool = new HashMap<String, File>();
	
	/**
	 * Get the File pointing to the library file.
	 * @param fileName The filename of the library
	 * @return The library File
	 */
	public final File getLibrary(final String fileName) {
		return this.pool.get(fileName);
	}
	
	/**
	 * Finds the library license file (.txt) associated to the library.
	 * @param libName The library name
	 * @return The library license file
	 */
	public final File findLibraryLicense(final String libName) {
		for (String fileName : this.pool.keySet()) {
			if (fileName.startsWith(libName) && fileName.endsWith(".txt")) {
				return this.pool.get(fileName);
			}
		}
		return null;
	}
	
	/**
	 * Parse the given file to find all the available libraries in 
	 * order to fill the pool.
	 * @param file The folder to parse
	 */
	public final void parseLibraries(final File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				final File[] files = file.listFiles(this.filter);
				for (File f : files) {
					this.parseLibraries(f);
				}
			} else if (file.isFile()) {
				final String path = file.getAbsolutePath();
				final String fileName = path.substring(path.lastIndexOf("lib" + File.separator) 
						+ ("lib" + File.separator).length());
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
