package com.tactfactory.harmony.parser;

import java.io.File;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

public class HeaderParser {

	public static void parseHeaderFile() {
		ConsoleUtils.display(">> Search for header.ftl...");
		// Look in app/android...
		String headerPath = Harmony.getProjectAndroidPath() + "header.ftl";
		File appAndroidHeader = new File(headerPath);
		if (appAndroidHeader.exists()) {
			ConsoleUtils.display(">>>> header.ftl found in "
								+ headerPath
								+ ".");
			loadHeaderFile(appAndroidHeader);
		} else {
			// Look in /...
			headerPath = Harmony.getRootPath() + "header.ftl";
			File baseHeader = new File(headerPath);
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

	protected static final void loadHeaderFile(File f) {
		String header = TactFileUtils.fileToStringBuffer(f).toString();
		ApplicationMetadata.INSTANCE.setHeaderTemplate(header);
	}
}
