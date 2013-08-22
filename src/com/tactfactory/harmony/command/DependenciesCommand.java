package com.tactfactory.harmony.command;

import com.tactfactory.harmony.dependencies.android.sdk.AndroidSDKManager;
import com.tactfactory.harmony.utils.ConsoleUtils;

import net.xeoh.plugins.base.annotations.PluginImplementation;

/**
 * Class for SDK commands.
 */
@PluginImplementation
public class DependenciesCommand extends BaseCommand {

	/** Bundle name. */
	public static final String BUNDLE 		= "dependencies";

	/** Subject name. */
	public static final String SUBJECT 		= "install";

	/** Action name. */
	public static final String ACTION_SDK	= "sdk";

	/** SDK:INSTALL:SDK. */
	public static final String INSTALL_SDK	= BUNDLE
			+ SEPARATOR
			+ SUBJECT
			+ SEPARATOR
			+ ACTION_SDK;

	@Override
	public void execute(String action, String[] args, String option) {
		AndroidSDKManager androidSDKManager = new AndroidSDKManager();
		String distantSDKUrl = 
				androidSDKManager.findLatestSDKToolsLink(AndroidSDKManager.LINUX);
		String sdkPath = ConsoleUtils.getUserInput(
					"Where do you want to install the Android SDK ?");
		androidSDKManager.downloadAndInstallAndroidSDK(distantSDKUrl, sdkPath);
		//androidSDKManager.initSDKList(sdkPath);
		/**
		ConsoleUtils.display(distantSDKUrl);**/

	}

	@Override
	public void summary() {
		ConsoleUtils.display("\n> DEPENDENCIES \n"
				+ "\t" + INSTALL_SDK
				+ "\t => Install the android SDK\n");
	}

	@Override
	public boolean isAvailableCommand(String command) {
		return INSTALL_SDK.equals(command);
	}
}
