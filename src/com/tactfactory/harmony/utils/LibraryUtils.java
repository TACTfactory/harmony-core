package com.tactfactory.harmony.utils;

import java.io.File;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.plateforme.BaseAdapter;

/**
 * Utility class for libraries.
 */
public abstract class LibraryUtils {
	
	/**
	 * Update TestLibs.
	 * @param adapter The adapter.
	 * @param libName The library name.
	 */
	public static void addLibraryToTestProject(
			final BaseAdapter adapter, 
			final String libName) {
		
		final File dest = new File(String.format("%s/%s",
				adapter.getTestLibsPath(),
				libName));
		
		if (!dest.exists()) {
			TactFileUtils.copyfile(
					new File(String.format("%s/%s", 
							Harmony.getLibsPath(),
							libName)),
					dest);
		}
	
	}
}
