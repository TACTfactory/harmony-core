/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
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
	/** Library footprint. */
	private final static String LIB_FOOTPRINT = "lib" + File.separator;
	/** Error message for not found library. */
	private final static String MESSAGE_LIBRARY_NOT_FOUND = 
			"The library %s has not been found. Aborting.";
	
	/** File filter to parse only libraries. */
	private final FileFilter filter = new FileFilter() {
		
		@Override
		public boolean accept(final File pathname) {
			return pathname.isDirectory()
					|| (pathname.getAbsolutePath().lastIndexOf(LIB_FOOTPRINT)
						> pathname.getAbsolutePath().lastIndexOf(
								"vendor" + File.separator));
		}
	};
	/** Map of all the libraries <FileName, File>. */
	private final Map<String, File> pool = new HashMap<String, File>();
	
	/**
	 * Get the File pointing to the library file.
	 * @param fileName The filename of the library
	 * @throws RuntimeException if the required library has not been found
	 * @return The library File
	 */
	public final File getLibrary(final String fileName) throws RuntimeException {
		File result = this.pool.get(fileName);
		if (result == null) {
			throw new RuntimeException(
					String.format(MESSAGE_LIBRARY_NOT_FOUND, fileName));
		}
		return result;
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
			final String path = file.getAbsolutePath();
			
			// Extract some folder.
			if (path.contains("lib") && !file.isDirectory()) {
				final String fileName = 
					path.substring(path.lastIndexOf(LIB_FOOTPRINT) 
					+ LIB_FOOTPRINT.length());
				
				// Check if loaded.
				if (this.pool.get(fileName) != null) {
					ConsoleUtils.displayWarning(
							"The library " 
							+ fileName
							+ " has been found multiple times."
							+ "Keeping "
							+ this.pool.get(fileName).getAbsolutePath());
				} else {
					ConsoleUtils.displayDebug(
							"Adding to library pool : " 
							+ path	);
					
					this.pool.put(fileName, file);
				}
			}
				
			// Recursive if folder.
			if (file.isDirectory()) {
				final File[] files = file.listFiles(this.filter);
				for (File f : files) {
					
					// Exclude git and doc folder.
					if (	!f.getAbsolutePath().contains(".git") &&
							!f.getAbsolutePath().contains("doc")) {
						this.parseLibraries(f);
					}
				}
			}
		}
	}
}
