package com.tactfactory.mda.android.command;

public class PackageUtils {
	public static String extractNameSpace(Class<?> mclass) {
		String mpackage = mclass.getPackage().getName();
		return extractNameEntity(mpackage);
	}
	
	public static String extractNameSpace(String mpackage) {
		int position = mpackage.lastIndexOf('.');
		return mpackage.substring(0, position);
	}
	
	public static String extractNameEntity(Class<?> mclass) {
		String mname = mclass.getName().toString();
		return extractNameEntity(mname);
	}
	public static String extractNameEntity(String mname) {
		int position = mname.lastIndexOf(".");
		return mname.substring(position+1);
	}
	
	public static String extractPath(String bundlePackage) {
		return bundlePackage.replace('.', '/');
	}
}
