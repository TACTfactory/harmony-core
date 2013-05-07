package com.tactfactory.mda.utils;

import java.io.File;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.plateforme.BaseAdapter;

public abstract class LibraryUtils {
	
	/**
	 * Update TestLibs.
	 * @param libName The library name.
	 */
	public static void addLibraryToTestProject(BaseAdapter adapter, 
			String libName) {
		
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
