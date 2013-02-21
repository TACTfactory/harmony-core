/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.utils;

public abstract class PackageUtils {
	public static String extractNameSpace(final Class<?> mclass) {
		final String mpackage = mclass.getPackage().getName();
		return extractNameEntity(mpackage);
	}
	
	public static String extractNameSpace(final String mpackage) {
		final int position = mpackage.lastIndexOf('.');
		return mpackage.substring(0, position);
	}
	
	public static String extractNameEntity(final Class<?> mclass) {
		final String mname = mclass.getName().toString();
		return extractNameEntity(mname);
	}
	public static String extractNameEntity(final String mname) {
		final int position = mname.lastIndexOf('.');
		return mname.substring(position+1);
	}
	
	public static String extractPath(final String bundlePackage) {
		return bundlePackage.replace('.', '/');
	}
	
	public static String extractClassNameFromArray(final String arrayName) {
		String cName = arrayName;
		if (arrayName.indexOf('<')>=0) {
			cName = arrayName.substring(
						arrayName.indexOf('<')+1,
						arrayName.indexOf('>'));
		} else if (arrayName.contains("[]")) {
			cName = arrayName.substring(0, arrayName.indexOf('['));
		}
		
		return cName;
	}
}
