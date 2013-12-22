/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.util.LinkedHashMap;

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
	public final void execute(final String action,
			final String[] args,
			final String option) {
		final AndroidSDKManager androidSDKManager = new AndroidSDKManager();
		final String distantSDKUrl = androidSDKManager.findLatestSDKToolsLink(
						AndroidSDKManager.LINUX);
		final String sdkPath = ConsoleUtils.getUserInput(
					"Where do you want to install the Android SDK ?");
		androidSDKManager.downloadAndInstallAndroidSDK(distantSDKUrl, sdkPath);
		//androidSDKManager.initSDKList(sdkPath);
		/**
		ConsoleUtils.display(distantSDKUrl);**/

	}

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		commands.put(INSTALL_SDK, "Install the android SDK");
		
		ConsoleUtils.displaySummary(
				BUNDLE,
				commands);
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return INSTALL_SDK.equals(command);
	}
}
