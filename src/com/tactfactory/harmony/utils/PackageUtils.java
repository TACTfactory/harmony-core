/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.utils;

/**
 * Utility class for Package manipulations.
 */
public abstract class PackageUtils {

	/** Extract NameSpace of a class.
	 * @param mclass The class
	 * @return The NameSpace
	 */
	public static String extractNameSpace(final Class<?> mclass) {
		final String mpackage = mclass.getPackage().getName();
		return extractNameEntity(mpackage);
	}

	/** Extract NameSpace of a package.
	 * @param mpackage The package
	 * @return The NameSpace
	 */
	public static String extractNameSpace(final String mpackage) {
		final int position = mpackage.lastIndexOf('.');
		return mpackage.substring(0, position);
	}

	/** Extract Name of a class.
	 * @param mclass The class
	 * @return The Name
	 */
	public static String extractNameEntity(final Class<?> mclass) {
		final String mname = mclass.getName().toString();
		return extractNameEntity(mname);
	}

	/** Extract ClassName of a String.
	 * @param mname The String
	 * @return The Name
	 */
	public static String extractNameEntity(final String mname) {
		final int position = mname.lastIndexOf('.');
		return mname.substring(position + 1);
	}

	/** Converts package to a path.
	 * @param bundlePackage The package
	 * @return The path
	 */
	public static String extractPath(final String bundlePackage) {
		return bundlePackage.replace('.', '/');
	}

	/** Extract a class name from an array.
	 * @param arrayName The array declaration
	 * @return The name of the class
	 */
	public static String extractClassNameFromArray(final String arrayName) {
		String cName = arrayName;
		if (arrayName.contains("<")) {
			cName = arrayName.substring(
						arrayName.indexOf('<') + 1,
						arrayName.indexOf('>'));
		} else if (arrayName.contains("[]")) {
			cName = arrayName.substring(0, arrayName.indexOf('['));
		}

		return cName;
	}
}
