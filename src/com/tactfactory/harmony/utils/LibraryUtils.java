/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.utils;

import java.io.File;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.plateforme.IAdapter;

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
			final IAdapter adapter,
			final String libName) {

		final File dest = new File(String.format("%s/%s",
				adapter.getTestLibsPath(),
				libName));

		if (!dest.exists()) {
			File src = Harmony.getLibrary(libName);
            TactFileUtils.copyfile(
                    src,
                    dest);
		}

	}
}
