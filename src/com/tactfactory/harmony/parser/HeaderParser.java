package com.tactfactory.harmony.parser;

import java.io.File;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Header.ftl parser.
 */
public final class HeaderParser {
	
	/**
	 * Private Constructor.
	 */
	private HeaderParser() { }

	/**
	 * Look for header.ftl in various folders (mainly / and /app/) and
	 * load it up in the ApplicationMetadata instance.
	 */
	public static void parseHeaderFile() {
		ConsoleUtils.display(">> Search for header.ftl...");
		// Look in app/android...
		String headerPath = Harmony.getProjectAndroidPath() + "header.ftl";
		final File appAndroidHeader = new File(headerPath);
		if (appAndroidHeader.exists()) {
			ConsoleUtils.display(">>>> header.ftl found in "
								+ headerPath
								+ ".");
			loadHeaderFile(appAndroidHeader);
		} else {
			// Look in /...
			headerPath = Harmony.getRootPath() + "header.ftl";
			final File baseHeader = new File(headerPath);
			if (baseHeader.exists()) {
				ConsoleUtils.display(">>>> header.ftl found in "
								+ headerPath
								+ ".");
				loadHeaderFile(baseHeader);

			} else {
				ConsoleUtils.display(">>>> header.ftl not found. Skipping...");
			}
		}
	}

	/**
	 * Load header file into the application metadata.
	 * @param f The file to load
	 */
	protected static void loadHeaderFile(File f) {
		final String header = TactFileUtils.fileToStringBuffer(f).toString();
		ApplicationMetadata.INSTANCE.setHeaderTemplate(header);
	}
}
