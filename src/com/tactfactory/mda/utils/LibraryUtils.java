package com.tactfactory.mda.utils;

import java.io.File;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;

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
