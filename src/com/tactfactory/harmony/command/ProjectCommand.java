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

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.HarmonyContext;
import com.tactfactory.harmony.ProjectContext;
import com.tactfactory.harmony.dependencies.android.sdk.AndroidSDKManager;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.parser.HeaderParser;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.TargetPlatform;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.plateforme.ios.IosAdapter;
import com.tactfactory.harmony.plateforme.rim.RimAdapter;
import com.tactfactory.harmony.plateforme.winphone.WinphoneAdapter;
import com.tactfactory.harmony.template.ApplicationGenerator;
import com.tactfactory.harmony.template.ProjectGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Project Structure Generator.
 *
 * can :
 * <ul>
 * <li>Make a structure</li>
 * <li>Remove project</li>
 * </ul>
 *
 */
@PluginImplementation
public class ProjectCommand extends BaseCommand {
	/** Bundle name. */
	public static final String BUNDLE 		= "project";

	/** Error Message. */
	private static final String ERROR_MSG =
			"Please check your file browser or file editor and try again...";

	// Actions
	/** Init action. */
	public static final String ACTION_INIT 	= "init";
	/** Remove action. */
	public static final String ACTION_REMOVE 	= "remove";
	/** Update action. */
	public static final String ACTION_UPDATE 	= "update";

	// Subjects
	/** SDK_PATH Subject. */
	public static final String SUBJECT_SDK_PATH = "sdk-path";
	/** Dependencies Subject. */
	public static final String SUBJECT_DEPENDENCIES = "dependencies";

	// Commands
	/** Command : PROJECT:INIT:ANDROID. */
	public static final String INIT_ANDROID 	= BUNDLE
								+ SEPARATOR
								+ ACTION_INIT
								+ SEPARATOR
								+ TargetPlatform.ANDROID.toLowerString();

	/** Command : PROJECT:INIT:IOS. */
	public static final String INIT_IOS 		= BUNDLE
								+ SEPARATOR
								+ ACTION_INIT
								+ SEPARATOR
								+ TargetPlatform.IPHONE.toLowerString();

	/** Command : PROJECT:INIT:RIM. */
	public static final String INIT_RIM 		= BUNDLE
								+ SEPARATOR
								+ ACTION_INIT
								+ SEPARATOR
								+ TargetPlatform.RIM.toLowerString();

	/** Command : PROJECT:INIT:WINPHONE. */
	public static final String INIT_WINPHONE 	= BUNDLE
								+ SEPARATOR
								+ ACTION_INIT
								+ SEPARATOR
								+ TargetPlatform.WINPHONE.toLowerString();

	/** Command : PROJECT:INIT:ALL. */
	public static final String INIT_ALL		= BUNDLE
								+ SEPARATOR
								+ ACTION_INIT
								+ SEPARATOR
								+ TargetPlatform.ALL.toLowerString();

	/** Command : PROJECT:REMOVE:ANDROID. */
	public static final String REMOVE_ANDROID = BUNDLE
								+ SEPARATOR
								+ ACTION_REMOVE
								+ SEPARATOR
								+ TargetPlatform.ANDROID.toLowerString();

	/** Command : PROJECT:REMOVE:IOS. */
	public static final String REMOVE_IOS 	= BUNDLE
								+ SEPARATOR
								+ ACTION_REMOVE
								+ SEPARATOR
								+ TargetPlatform.IPHONE.toLowerString();

	/** Command : PROJECT:REMOVE:RIM. */
	public static final String REMOVE_RIM 	= BUNDLE
								+ SEPARATOR
								+ ACTION_REMOVE
								+ SEPARATOR
								+ TargetPlatform.RIM.toLowerString();

	/** Command : PROJECT:REMOVE:WINPHONE. */
	public static final String REMOVE_WINPHONE = BUNDLE
								+ SEPARATOR
								+ ACTION_REMOVE
								+ SEPARATOR
								+ TargetPlatform.WINPHONE.toLowerString();

	/** Command : PROJECT:REMOVE:ALL. */
	public static final String REMOVE_ALL 	= BUNDLE
								+ SEPARATOR
								+ ACTION_REMOVE
								+ SEPARATOR
								+ TargetPlatform.ALL.toLowerString();

	/** Command : PROJECT:UPDATE:SDK-PATH. */
	public static final String UPDATE_SDK = BUNDLE
								+ SEPARATOR
								+ ACTION_UPDATE
								+ SEPARATOR
								+ SUBJECT_SDK_PATH;

	/** Command : PROJECT:UPDATE:DEPENDENCIES. */
	public static final String UPDATE_DEPENDENCIES = BUNDLE
								+ SEPARATOR
								+ ACTION_UPDATE
								+ SEPARATOR
								+ SUBJECT_DEPENDENCIES;

	// Internal
	/** Android adapter. */
	private final BaseAdapter adapterAndroid = new AndroidAdapter();
	/** iOS adapter. */
	private final BaseAdapter adapterIOS = new IosAdapter();
	/** RIM adapter. */
	private final BaseAdapter adapterRIM = new RimAdapter();
	/** Windows Phone adapter. */
	private final BaseAdapter adapterWinPhone = new WinphoneAdapter();

	/** Has user confirmed ? */
	private boolean userHasConfirmed;

	/** Is project initialized ? */
	private boolean isProjectInit;

	/**
	 * Initialize Project Parameters.
	 * (project name, namespace, android sdk path)
	 */
	public final void initProjectParam() {
		if (!this.isProjectInit) {
			while (!this.userHasConfirmed) {
				ConsoleUtils.display(">> Project Parameters");

				
				ProjectContext.promptProjectName(
						this.getCommandArgs());
				
				ProjectContext.promptProjectNameSpace(
						this.getCommandArgs());
				
				HarmonyContext.initProjectAndroidSdkPath(
						this.getCommandArgs());

				ConsoleUtils.display("Project Name: "
							+ ApplicationMetadata.INSTANCE.getName()
						 + "\nProject NameSpace: "
							+ ApplicationMetadata.INSTANCE.getProjectNameSpace()
						 + "\nAndroid SDK Path: "
							+ ApplicationMetadata.getAndroidSdkPath());

				// Confirmation
				if (ConsoleUtils.isConsole()) {
					final String accept =
							ConsoleUtils.getUserInput(
									"Use below given parameters "
									 + "to process files? (y/n) ");

					if (!accept.contains("n")) {
						this.userHasConfirmed 			= true;
					} else {
						ApplicationMetadata.INSTANCE.setName("");
						ApplicationMetadata.INSTANCE.setProjectNameSpace("");
						ApplicationMetadata.setAndroidSdkPath("");
						this.getCommandArgs().clear();
					}
				} else {
					this.userHasConfirmed = true;
				}
			}

			this.userHasConfirmed = false;
			this.isProjectInit = true;
		}
	}

	/**
	 * Initialize Android Project folders and files.
	 * @return success of Android project initialization
	 */
	public final boolean initAndroid() {
		ConsoleUtils.display("> Init Project Google Android");

		this.initProjectParam();
		boolean result = false;

		boolean androidSDKExists = AndroidSDKManager.checkIfAndroidSDKExists(
				ApplicationMetadata.getAndroidSdkPath()); 
		if (!androidSDKExists) {
			/*AndroidSDKManager sdkManager = new AndroidSDKManager();
			androidSDKExists = sdkManager.installSDKTo(AndroidSDKManager.LINUX,
							ApplicationMetadata.getAndroidSdkPath());*/
			ConsoleUtils.displayWarning("No SDK found at given path, please "
					+ "launch command " + DependenciesCommand.INSTALL_SDK + " "
					+ "to install Android SDK.");
		} 
		try {
			if (new ProjectGenerator(this.adapterAndroid).makeProject()) {
				ConsoleUtils.displayDebug("Init Android Project Success!");

				Harmony.getInstance().getProjectContext()
				    .addAdapter(TargetPlatform.ANDROID, this.adapterAndroid);
				new ApplicationGenerator(this.adapterAndroid)
							.generateApplication();
				result = true;
			} else {
				ConsoleUtils.displayError(
						new Exception("Init Android Project Fail!"));
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
		
		return result;
	}

	/**
	 * Initialize IOS Project folders and files.
	 * @return success of IOS project initialization
	 */
	public final boolean initIOS() {
		ConsoleUtils.display("> Init Project Apple iOS");

		this.initProjectParam();
		boolean result = false;

		try {
			if (new ProjectGenerator(this.adapterIOS).makeProject()) {
				ConsoleUtils.displayDebug("Init IOS Project Success!");
			    Harmony.getInstance().getProjectContext()
			        .addAdapter(TargetPlatform.IPHONE, this.adapterIOS);
				result = true;
			} else {
				ConsoleUtils.displayError(
						new Exception("Init IOS Project Fail!"));
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}

		return result;
	}

	/**
	 * Initialize RIM Project folders and files.
	 * @return success of RIM project initialization
	 */
	public final boolean initRIM() {
		ConsoleUtils.display("> Init Project BlackBerry RIM");

		this.initProjectParam();
		boolean result = false;

		try {
			if (new ProjectGenerator(this.adapterRIM).makeProject()) {
				ConsoleUtils.displayDebug("Init RIM Project Success!");
				Harmony.getInstance().getProjectContext()
                    .addAdapter(TargetPlatform.RIM, this.adapterRIM);
				result = true;
			} else {
				ConsoleUtils.displayError(
						new Exception("Init RIM Project Fail!"));
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}

		return result;
	}

	/**
	 * Initialize Windows Phone Project folders and files.
	 * @return success of Windows Phone project initialization
	 */
	public final boolean initWinPhone() {
		ConsoleUtils.display("> Init Project Windows Phone");

		this.initProjectParam();
		boolean result = false;

		try {
			if (new ProjectGenerator(this.adapterWinPhone).makeProject()) {
				ConsoleUtils.displayDebug("Init WinPhone Project Success!");
				Harmony.getInstance().getProjectContext()
                    .addAdapter(TargetPlatform.WINPHONE, this.adapterWinPhone);
				result = true;
			} else {
				ConsoleUtils.displayError(
						new Exception("Init WinPhone Project Fail!"));
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}

		return result;
	}

	/**
	 * Initialize all project platforms.
	 */
	public final void initAll() {
		ConsoleUtils.display("> Init All Projects");

		this.initProjectParam();
		this.initAndroid();
		this.initIOS();
		//this.initRIM();
		//this.initWinPhone();
	}

	/**
	 * Remove Android project folder.
	 */
	public final void removeAndroid() {
		if (!this.userHasConfirmed) {
			final String accept =
					ConsoleUtils.getUserInput("Are you sure to Delete "
							 + "Android Project? (y/n) ");

			if (accept.contains("n")) {
				return;
			}

			this.userHasConfirmed = true;
		}

		try {
			new ProjectGenerator(this.adapterAndroid).removeProject();

		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Remove IOS project folder.
	 */
	public final void removeIOS() {
		if (!this.userHasConfirmed) {
			final String accept =
					ConsoleUtils.getUserInput("Are you sure to Delete "
							+ "Apple iOS Project? (y/n) ");

			if (accept.contains("n")) { return; }

			this.userHasConfirmed = true;
		}

		try {
			if (!new ProjectGenerator(this.adapterIOS).removeProject()) {
				ConsoleUtils.display(ERROR_MSG);
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Remove RIM project folder.
	 */
	public final void removeRIM() {
		if (!this.userHasConfirmed) {
			final String accept =
					ConsoleUtils.getUserInput(
							"Are you sure to Delete"
							+ " BlackBerry Rim Project? (y/n)");

			if (accept.contains("n")) {
				return;
			}

			this.userHasConfirmed = true;
		}

		try {
			if (!new ProjectGenerator(this.adapterRIM).removeProject()) {
				ConsoleUtils.display(ERROR_MSG);
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Remove Windows Phone project folder.
	 */
	public final void removeWinPhone() {
		if (!this.userHasConfirmed) {
			final String accept = ConsoleUtils.getUserInput(
					"Are you sure to Delete Windows Phone Project? (y/n) ");

			if (accept.contains("n")) { return; }

			this.userHasConfirmed = true;
		}

		try {
			if (!new ProjectGenerator(this.adapterWinPhone).removeProject()) {
				ConsoleUtils.display(ERROR_MSG);
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}

	/**
	 * Remove all project platforms.
	 */
	public final void removeAll() {
		if (!this.userHasConfirmed) {
			final String accept =
					ConsoleUtils.getUserInput(
							"Are you sure to Delete All Projects? (y/n) ");

			if (accept.contains("n")) { return; }

			this.userHasConfirmed = true;
		}

		try {
			final boolean removedAndroid =
					new ProjectGenerator(this.adapterAndroid).removeProject();
			final boolean removedIOS =
					new ProjectGenerator(this.adapterIOS).removeProject();
			final boolean removedRIM =
					new ProjectGenerator(this.adapterRIM).removeProject();
			final boolean removedWin =
					new ProjectGenerator(this.adapterWinPhone).removeProject();
			if (!removedAndroid
				|| !removedIOS
				|| !removedRIM
				|| !removedWin) {
				ConsoleUtils.display(ERROR_MSG);
			}
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		} finally {
			this.userHasConfirmed = false;
		}
	}

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		
		// Init
		commands.put(INIT_ANDROID, "Init Google Android project directory");
//		commands.put(INIT_IOS, "Init Apple IOS project directory");
//		commands.put(INIT_RIM, "Init BlackBerry project directory");
//		commands.put(INIT_WINPHONE, "Init Windows Phone project directory");
		commands.put(INIT_ALL, "Init All project directories");
		
		// Remove
		commands.put(REMOVE_ANDROID, "Remove Google Android project directory");
//		commands.put(REMOVE_IOS, "Remove Apple IOS project directory");
//		commands.put(REMOVE_RIM, "Remove BlackBerry project directory");
//		commands.put(REMOVE_WINPHONE, "Remove Windows Phone project directory");
		commands.put(REMOVE_ALL, "Remove All project directories");
		
		// Update
		commands.put(UPDATE_SDK, "Update the SDK Path");
		commands.put(UPDATE_DEPENDENCIES, "Update the dependencies from an existing project");
		
		ConsoleUtils.displaySummary(
				BUNDLE,
				commands);
	}

	@Override
	public final void execute(final String action,
			final String[] args,
			final String option) {
		this.setCommandArgs(Console.parseCommandArgs(args));
		HeaderParser.parseHeaderFile();
		if (action.equals(INIT_ANDROID)) {
			this.initAndroid();
		} else

		if (action.equals(INIT_IOS)) {
			this.initIOS();
		} else

//		if (action.equals(INIT_RIM)) {
//			this.initRIM();
//		} else

//		if (action.equals(INIT_WINPHONE)) {
//			this.initWinPhone();
//		} else

		if (action.equals(INIT_ALL)) {
			this.initAll();
		} else

		if (action.equals(REMOVE_ANDROID)) {
			this.removeAndroid();
		} else

		if (action.equals(REMOVE_IOS)) {
			this.removeIOS();
		} else

//		if (action.equals(REMOVE_RIM)) {
//			this.removeRIM();
//		} else

//		if (action.equals(REMOVE_WINPHONE)) {
//			this.removeWinPhone();
//		} else

		if (action.equals(REMOVE_ALL)) {
			this.removeAll();
		} else

		if (action.equals(UPDATE_SDK)) {
			ApplicationMetadata.setAndroidSdkPath("");
			HarmonyContext.initProjectAndroidSdkPath(this.getCommandArgs());
			ProjectGenerator.updateSDKPath();
		} else

		if (action.equals(UPDATE_DEPENDENCIES)) {
			try {
				new ProjectGenerator(this.adapterAndroid).updateDependencies();
			} catch (Exception e) {
				ConsoleUtils.displayError(e);
			}
		}
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return  command.equals(INIT_ANDROID)
				|| command.equals(INIT_IOS)
				//|| command.equals(INIT_RIM)
				//|| command.equals(INIT_WINPHONE)
				|| command.equals(INIT_ALL)
				|| command.equals(REMOVE_ANDROID)
				|| command.equals(REMOVE_IOS)
				//|| command.equals(REMOVE_RIM)
				//|| command.equals(REMOVE_WINPHONE)
				|| command.equals(REMOVE_ALL)
				|| command.equals(UPDATE_SDK)
				|| command.equals(UPDATE_DEPENDENCIES);
	}
}
