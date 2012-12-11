/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import net.xeoh.plugins.base.annotations.Capabilities;
import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.TargetPlatform;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.IosAdapter;
import com.tactfactory.mda.plateforme.RimAdapter;
import com.tactfactory.mda.plateforme.WinphoneAdapter;
import com.tactfactory.mda.template.ProjectGenerator;

/**
 * Project Structure Generator
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
	/** Bundle name */
	public static String BUNDLE 		= "project";

	// Actions
	public static String ACTION_INIT 	= "init";
	public static String ACTION_REMOVE 	= "remove";

	// Commands
	public static String INIT_ANDROID 	= BUNDLE + SEPARATOR + ACTION_INIT + SEPARATOR + TargetPlatform.ANDROID.toLowerString();
	public static String INIT_IOS 		= BUNDLE + SEPARATOR + ACTION_INIT + SEPARATOR + TargetPlatform.IPHONE.toLowerString();
	public static String INIT_RIM 		= BUNDLE + SEPARATOR + ACTION_INIT + SEPARATOR + TargetPlatform.RIM.toLowerString();
	public static String INIT_WINPHONE 	= BUNDLE + SEPARATOR + ACTION_INIT + SEPARATOR + TargetPlatform.WINPHONE.toLowerString();
	public static String INIT_ALL		= BUNDLE + SEPARATOR + ACTION_INIT + SEPARATOR + TargetPlatform.ALL.toLowerString();

	public static String REMOVE_ANDROID = BUNDLE + SEPARATOR + ACTION_REMOVE + SEPARATOR + TargetPlatform.ANDROID.toLowerString();
	public static String REMOVE_IOS 	= BUNDLE + SEPARATOR + ACTION_REMOVE + SEPARATOR + TargetPlatform.IPHONE.toLowerString();
	public static String REMOVE_RIM 	= BUNDLE + SEPARATOR + ACTION_REMOVE + SEPARATOR + TargetPlatform.RIM.toLowerString();
	public static String REMOVE_WINPHONE= BUNDLE + SEPARATOR + ACTION_REMOVE + SEPARATOR + TargetPlatform.WINPHONE.toLowerString();
	public static String REMOVE_ALL 	= BUNDLE + SEPARATOR + ACTION_REMOVE + SEPARATOR + TargetPlatform.ALL.toLowerString();

	// Internal	
	protected BaseAdapter adapterAndroid = new AndroidAdapter();
	protected BaseAdapter adapterIOS = new IosAdapter();
	protected BaseAdapter adapterRIM = new RimAdapter();
	protected BaseAdapter adapterWinPhone = new WinphoneAdapter();

	private static boolean userHasConfirmed = false;
	private static boolean isProjectInit = false;

	/**
	 * Init Project Parameters (project name, namespace, android sdk path)
	 */
	public void initProjectParam()
	{
		if(!isProjectInit) {

			while(!userHasConfirmed)
			{
				ConsoleUtils.display(">> Project Parameters");

				//Project Name
				if(!this.commandArgs.containsKey("name"))
					Harmony.initProjectName();
				else
					Harmony.projectName = this.commandArgs.get("name");

				//Project NameSpace
				if(!this.commandArgs.containsKey("namespace"))
					Harmony.initProjectNameSpace();
				else
					Harmony.projectNameSpace = this.commandArgs.get("namespace").replaceAll("\\.", "/");

				//Android sdk path
				if(!this.commandArgs.containsKey("androidsdk"))
					Harmony.initProjectAndroidSdkPath();
				else
					Harmony.androidSdkPath = this.commandArgs.get("androidsdk");

				ConsoleUtils.displayDebug("Project Name: "			+ Harmony.projectName +
						"\nProject NameSpace: "	+ Harmony.projectNameSpace +
						"\nAndroid SDK Path: "		+ Harmony.androidSdkPath);

				if (Harmony.isConsole) {
					String accept = Harmony.getUserInput("Use below given parameters to process files? (y/n) ");
					if(!accept.contains("n")) {
						userHasConfirmed = true;
					} else {
						Harmony.projectName = null;
						Harmony.projectNameSpace = null;
						Harmony.androidSdkPath = null;
						this.commandArgs.clear();
					}
				} else {
					userHasConfirmed = true;
				}
			}
			userHasConfirmed = false;
			isProjectInit = true;
		}
	}

	/**
	 * Initialize Android Project folders and files
	 * @return success of Android project initialization
	 */
	public boolean initAndroid() {
		ConsoleUtils.display("Init Project Google Android\n----------------------");

		this.initProjectParam();
		boolean result = false;

		try {
			if(new ProjectGenerator(this.adapterAndroid).makeProject()){
				ConsoleUtils.displayDebug("Init Android Project Success!");
				
				result = true;
			} else {
				ConsoleUtils.displayError("Init Android Project Fail!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Initialize IOS Project folders and files
	 * @return success of IOS project initialization
	 */
	public boolean initIOS() {
		ConsoleUtils.display("Init Project Apple iOS\n----------------------");

		this.initProjectParam();
		boolean result = false;
		try {
			if(new ProjectGenerator(this.adapterIOS).makeProject()){
				ConsoleUtils.displayDebug("Init IOS Project Success!");
				result = true;
			} else {
				ConsoleUtils.displayError("Init IOS Project Fail!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Initialize RIM Project folders and files
	 * @return success of RIM project initialization
	 */
	public boolean initRIM() {
		ConsoleUtils.display("Init Project BlackBerry RIM\n----------------------");

		this.initProjectParam();
		boolean result = false;
		try {
			if(new ProjectGenerator(this.adapterRIM).makeProject()){
				ConsoleUtils.displayDebug("Init RIM Project Success!");
				result = true;
			} else {
				ConsoleUtils.displayError("Init RIM Project Fail!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Initialize Windows Phone Project folders and files
	 * @return success of Windows Phone project initialization
	 */
	public boolean initWinPhone() {
		ConsoleUtils.display("Init Project Windows Phone\n----------------------");

		this.initProjectParam();
		boolean result = false;
		try{
			if(new ProjectGenerator(this.adapterWinPhone).makeProject()){
				ConsoleUtils.displayDebug("Init WinPhone Project Success!");
				result = true;
			} else {
				ConsoleUtils.displayError("Init WinPhone Project Fail!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Initialize all project platforms
	 */
	public void initAll() {
		ConsoleUtils.display("Init All Projects\n----------------------");

		this.initProjectParam();
		this.initAndroid();
		this.initIOS();
		//this.initRIM();
		//this.initWinPhone();		
	}

	/**
	 * Remove Android project folder
	 */
	public void removeAndroid() {

		if(!userHasConfirmed) {
			String accept = Harmony.getUserInput("Are you sure to Delete Android Project? (y/n) ");
			if(accept.contains("n")) {
				return;
			}
			userHasConfirmed = true;
		}
		try {
			if(!new ProjectGenerator(this.adapterAndroid).removeProject())
				ConsoleUtils.display("Please check your file browser or file editor and try again...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove IOS project folder
	 */
	public void removeIOS() {

		if(!userHasConfirmed) {
			String accept = Harmony.getUserInput("Are you sure to Delete Apple iOS Project? (y/n) ");
			if(accept.contains("n")) {
				return;
			}
			userHasConfirmed = true;
		}
		try {
			if(!new ProjectGenerator(this.adapterIOS).removeProject())
				ConsoleUtils.display("Please check your file browser or file editor and try again...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove RIM project folder
	 */
	public void removeRIM() {

		if(!userHasConfirmed) {
			String accept = Harmony.getUserInput("Are you sure to Delete BlackBerry Rim Project? (y/n) ");
			if(accept.contains("n")) {
				return;
			}
			userHasConfirmed = true;
		}
		try {
			if(!new ProjectGenerator(this.adapterRIM).removeProject())
				ConsoleUtils.display("Please check your file browser or file editor and try again...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove Windows Phone project folder
	 */
	public void removeWinPhone() {

		if(!userHasConfirmed) {
			String accept = Harmony.getUserInput("Are you sure to Delete Windows Phone Project? (y/n) ");
			if(accept.contains("n")) {
				return;
			}
			userHasConfirmed = true;
		}
		try {
			if(!new ProjectGenerator(this.adapterWinPhone).removeProject())
				ConsoleUtils.display("Please check your file browser or file editor and try again...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Remove all project platforms
	 */
	public void removeAll() {

		if(!userHasConfirmed) {
			String accept = Harmony.getUserInput("Are you sure to Delete All Projects? (y/n) ");
			if(accept.contains("n")) {
				return;
			}
			userHasConfirmed = true;
		}
		try {
			if(	!new ProjectGenerator(this.adapterAndroid).removeProject() |
				!new ProjectGenerator(this.adapterIOS).removeProject() |
				!new ProjectGenerator(this.adapterRIM).removeProject() |
				!new ProjectGenerator(this.adapterWinPhone).removeProject() ){
				ConsoleUtils.display("Please check your file browser or file editor and try again...");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			userHasConfirmed = false;
		}
	}

	/** @see BaseCommand#summary() */
	@Override
	public void summary() {
		ConsoleUtils.display("\n> Project\n"+
				"\t"+INIT_ANDROID+"\t => Init Android project directory\n" +
				"\t"+INIT_IOS+"\t => Init Apple IOS project directory\n" +
				"\t"+INIT_RIM+"\t => Init BlackBerry project directory\n" +
				"\t"+INIT_WINPHONE+"\t => Init Windows Phone project directory\n" +
				"\t"+INIT_ALL+"\t => Init All project directories\n" +
				"\t"+REMOVE_ANDROID+"\t => Remove Google Android project directory\n" +
				"\t"+REMOVE_IOS+"\t => Remove Apple IOS project directory\n" +
				"\t"+REMOVE_RIM+"\t => Remove BlackBerry project directory\n" +
				"\t"+REMOVE_WINPHONE+"\t => Remove Windows Phone project directory\n" +
				"\t"+REMOVE_ALL+"\t => Remove All project directories\n");
	}

	/** @see BaseCommand#execute(String, String[], String) */
	@Override
	public void execute(String action, String[] args, String option) {

		this.commandArgs = Console.parseCommandArgs(args);
		if (action.equals(INIT_ANDROID)) {
			this.initAndroid();
		} else

			if (action.equals(INIT_IOS)) {
				this.initIOS();
			} else

				if (action.equals(INIT_RIM)) {
					this.initRIM();
				} else

					if (action.equals(INIT_WINPHONE)) {
						this.initWinPhone();
					} else

						if (action.equals(INIT_ALL)) {
							this.initAll();
						} else

							if (action.equals(REMOVE_ANDROID)) {
								this.removeAndroid();
							} else

								if (action.equals(REMOVE_IOS)) {
									this.removeIOS();
								} else

									if (action.equals(REMOVE_RIM)) {
										this.removeRIM();
									} else

										if (action.equals(REMOVE_WINPHONE)) {
											this.removeWinPhone();
										} else

											if (action.equals(REMOVE_ALL)) {
												this.removeAll();
											} else

											{

											}
	}

	/** @see BaseCommand#isAvailableCommand(String) */
	@Override
	@Capabilities
	public boolean isAvailableCommand(String command) {
		return (command.equals(INIT_ANDROID) ||
				command.equals(INIT_IOS) ||
				//command.equals(INIT_RIM) ||
				//command.equals(INIT_WINPHONE) ||
				command.equals(INIT_ALL) ||
				command.equals(REMOVE_ANDROID) ||
				command.equals(REMOVE_IOS) ||
				command.equals(REMOVE_RIM) ||
				command.equals(REMOVE_WINPHONE) ||
				command.equals(REMOVE_ALL)
				);
	}
}