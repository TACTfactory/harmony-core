/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
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
	/** Possible position of Header.ftl. (harmony/app/android/) */
	private static final String ANDROID_FOLDER_HEADER_PATH = 
			Harmony.getProjectAndroidPath() + "header.ftl";
	
	/** Possible position of Header.ftl. (harmony/) */
	private static final String BASE_FOLDER_HEADER_PATH = 
			Harmony.getRootPath() + "header.ftl";

	/** Possible position of Header.ftl. (harmony/vendor/tact-core/tpl/) */
	private static final String TEMPLATE_FOLDER_HEADER_PATH = 
			Harmony.getRootPath() + "vendor/tact-core/tpl/header.ftl";
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
		if (HeaderParser.fileExists(ANDROID_FOLDER_HEADER_PATH)) {
			HeaderParser.loadHeaderFile(ANDROID_FOLDER_HEADER_PATH);
			
		} else if (HeaderParser.fileExists(BASE_FOLDER_HEADER_PATH)) {
			HeaderParser.loadHeaderFile(BASE_FOLDER_HEADER_PATH);
			
		} else if (HeaderParser.fileExists(TEMPLATE_FOLDER_HEADER_PATH)) {
			HeaderParser.loadHeaderFile(TEMPLATE_FOLDER_HEADER_PATH);
			
		} else {
			ConsoleUtils.display(">>>> No header.ftl found... Skipping...");
		}
	}
	
	/**
	 * Check if header file exists and notice user if so.
	 * @param filePath The file path
	 * @return True if the file has been found
	 */
	private static boolean fileExists(final String filePath) {
		final File f = new File(filePath);
		final boolean result = f.exists();
		
		if (result) {
			ConsoleUtils.display(
					">>>> Header.ftl found in " + f.getAbsolutePath());
		} 
		
		return result;
	}

	/**
	 * Load header file into the application metadata.
	 * @param f The file to load
	 */
	protected static void loadHeaderFile(final String f) {
		final String header = 
				TactFileUtils.fileToStringBuffer(new File(f)).toString();
		ApplicationMetadata.INSTANCE.setHeaderTemplate(header);
	}
}
