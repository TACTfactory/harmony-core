/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.JSPFProperties;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.mda.command.*;
import com.tactfactory.mda.orm.ApplicationMetadata;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.OsUtil;

/** Harmony main class */
public class Harmony {
	/** Debug state*/
	public static boolean debug = false;
	
	/** Harmony version */
	public static final String VERSION = "0.4.0-DEV";
	
	/** Singleton of console */
	public static Harmony instance;
	
	/** Application meta-data */
	public static ApplicationMetadata metas = new ApplicationMetadata();
	
	/** Path of Harmony base */
	public static String pathBase = "./";
	
	/** Path of project ( app folder in Harmony root ) */
	public static String pathProject = pathBase + "app";
	
	/** Path of templates */
	public static String pathTemplate = pathBase + "tpl"; // "../../tpl";
	
	/** Path of libs */
	public static String pathLibs = pathBase + "lib";
	
	/** Path of harmony.jar */
	public static String pathHarmony = pathBase + "vendor/tact-core";
	
	/** Project space */
	public static String projectFolder = "android";

	/** Android SDK path */
	public static String androidSdkPath;
	
	/** Android SDK version */
	public static String androidSdkVersion;

	/** Symfony path */
	public static String symfonyPath = "D:/Site/wamp/www/Symfony";
	
	private static final String DEFAULT_PROJECT_NAME = "demact";
	private static final String DEFAULT_PROJECT_NAMESPACE = "com.tactfactory.mda.test.demact";
	
	public static boolean isConsole = false;
	
	private PluginManager pluginManager;
	private HashMap<Class<?>, Command> bootstrap = new HashMap<Class<?>, Command>();

	public Harmony() throws Exception {
		final JSPFProperties props = new JSPFProperties();
		/* props.setProperty(PluginManager.class, "cache.enabled", "true");
		props.setProperty(PluginManager.class, "cache.mode",    "weak"); //optional
		props.setProperty(PluginManager.class, "cache.file",    "jspf.cache");*/
		
		this.pluginManager = PluginManagerFactory.createPluginManager(props);
		this.pluginManager.addPluginsFrom(new URI("classpath://*"));
		this.pluginManager.addPluginsFrom(new File("vendor/").toURI());
		
		PluginManagerUtil pmu = new PluginManagerUtil(this.pluginManager);
		Collection<Command> commands = pmu.getPlugins(Command.class);
		
		for (Command command : commands) {
			this.bootstrap.put(command.getClass(), command);
		}
	
		Locale.setDefault(Locale.US);
		Harmony.instance = this;
	}

	/** Initialize Harmony 
	 * @throws Exception */
	protected void initialize() throws Exception {
		// Check project folder
		if (Strings.isNullOrEmpty(projectFolder)) {
			ConsoleUtils.displayError("Project folder undefined");
			throw new Exception("Project folder undefined");
		}
		
		ConsoleUtils.display("Current Working Path: " + new File(".").getCanonicalPath());

		// Check name space
		if (Strings.isNullOrEmpty(Harmony.metas.projectNameSpace)) {
			
			// get project namespace and project name from AndroidManifest.xml
			File manifest = new File(String.format("%s/%s/%s",Harmony.pathProject,Harmony.projectFolder,"AndroidManifest.xml"));
			
			if(manifest.exists()) {
				Harmony.metas.projectNameSpace = Harmony.getNameSpaceFromManifest(manifest);

				String[] projectNameSpaceData = Harmony.metas.projectNameSpace.split("/");
				Harmony.metas.name = projectNameSpaceData[projectNameSpaceData.length-1];
			}
			
			// get android sdk dir from local.properties
			File local_prop = new File(String.format("%s/%s/%s",Harmony.pathProject,Harmony.projectFolder,"project.properties"));
			if(local_prop.exists())
				Harmony.androidSdkPath = Harmony.getSdkDirFromProject(local_prop);
				Harmony.androidSdkVersion = getAndroidSdkVersion(Harmony.androidSdkPath);
		}
		else {
			String[] projectNameSpaceData = Harmony.metas.projectNameSpace.split("/");
			Harmony.metas.name = projectNameSpaceData[projectNameSpaceData.length-1];
		}
		
		// Debug Log
		ConsoleUtils.display(
				"Current Project : " + Harmony.metas.name + "\n" +
				"Current NameSpace : " + Harmony.metas.projectNameSpace + "\n" +
				"Current Android SDK Path : " + Harmony.androidSdkPath + "\n" +
				"Current Android SDK Revision : " + Harmony.androidSdkVersion);
	}
	
	/**
	 * Select the proper command class
	 * 
	 * @param commandName Class command name
	 * @return BaseCommand object
	 */
	public Command getCommand(Class<?> commandName) {
		return this.bootstrap.get(commandName);
	}
	
	public Collection<Command> getCommands() {
		return this.bootstrap.values();
	}

	/**
	 * Check and launch command in proper command class
	 * 
	 * @param action Command to execute
	 * @param args Commands arguments
	 * @param option Console option (ANSI,Debug,...)
	 */
	public void findAndExecute(String action, String[] args, String option) {
		boolean isfindAction = false;
		
		// Select Action and launch
		for (Command baseCommand : this.bootstrap.values()) {
			if (baseCommand.isAvailableCommand(action)) {
				baseCommand.execute(action, args, option);
				isfindAction = true;
			}
		}

		// No found action
		if (!isfindAction) {
			ConsoleUtils.display("Command not found...");
			
			this.getCommand(GeneralCommand.class).execute(GeneralCommand.LIST,null,null);
		}
		
		this.pluginManager.shutdown();
	}

	/**
	 * Generic user console prompt
	 * 
	 * @param promptMessage message to display
	 * @return input user input
	 */
	public static String getUserInput(String promptMessage) {
		String input = null;
		
		ConsoleUtils.display(promptMessage);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			input = br.readLine();
		} catch (IOException e) {
			e.getMessage();
		}
		
		return input;
	}

	/**
	 * Prompt Project Name to the user
	 */
	public static void initProjectName()
	{
		if (Strings.isNullOrEmpty(Harmony.metas.name)) {
			String projectName = Harmony.getUserInput("Please enter your Project Name ["+DEFAULT_PROJECT_NAME+"]:");
			
			if (!Strings.isNullOrEmpty(projectName))
				Harmony.metas.name = projectName;
			else
				Harmony.metas.name = DEFAULT_PROJECT_NAME;
		}
	}
	
	/**
	 * Prompt Project Name Space to the user
	 */
	public static void initProjectNameSpace() {
		if (Strings.isNullOrEmpty(Harmony.metas.projectNameSpace)) {
			boolean good = false;
			
			while (!good) {
				String projectNameSpace = Harmony.getUserInput("Please enter your Project NameSpace ["+DEFAULT_PROJECT_NAMESPACE+"]:");
				
				if(!Strings.isNullOrEmpty(projectNameSpace)) {
					if(projectNameSpace.toLowerCase().endsWith(Harmony.metas.name.toLowerCase())){
						Harmony.metas.projectNameSpace = projectNameSpace.replaceAll("\\.", "/");
						good = true;
					} else {
						ConsoleUtils.display("The NameSpace has to end with Project Name !");
					}
				} else {
					Harmony.metas.projectNameSpace = DEFAULT_PROJECT_NAMESPACE.replaceAll("\\.", "/");
					good=true;
				}
			}
		}
	}

	/**
	 * Prompt Project Android SDK Path to the user
	 */
	public static void initProjectAndroidSdkPath() {
		if (Strings.isNullOrEmpty(Harmony.androidSdkPath)) {
			String sdkPath = Harmony.getUserInput("Please enter AndroidSDK full path [/root/android-sdk/]:");
			
			if(!Strings.isNullOrEmpty(sdkPath)){
				Harmony.androidSdkPath = sdkPath;
				Harmony.androidSdkVersion = getAndroidSdkVersion(Harmony.androidSdkPath);
			} else {
				String os_message = "Detected OS: ";
				
				if(OsUtil.isWindows()) {
					if(!OsUtil.isX64()) {
						os_message += "Windows x86";
						Harmony.androidSdkPath = String.format("%s/%s/","C:/Program Files (x86)","android-sdk");
					} else if(OsUtil.isX64()) {
						os_message += "Windows x64";
						Harmony.androidSdkPath = String.format("%s/%s/","C:/Program Files","android-sdk");
					} else {
						os_message += "Windows x??";
						Harmony.androidSdkPath = String.format("%s/%s/","C:/Program Files","android-sdk");
					}
				}
				else if(OsUtil.isLinux()) {
					os_message += "Linux";
					Harmony.androidSdkPath = "/opt/android-sdk/";
				}
				
				// Debug Log
				ConsoleUtils.displayDebug(os_message);
			}
		}
	}

	/**
	 * Check initialization of project by searching nameSpace in androidmanifest.xml
	 *
	 * @return true if success
	 */
	public static boolean isProjectInit() {
		boolean result = false;
		File projectFolder = new File(Harmony.projectFolder);
		
		if(projectFolder.exists() && projectFolder.listFiles().length!=0){
			File manifest = new File(Harmony.projectFolder + "AndroidManifest.xml");
			String namespace = Harmony.getNameSpaceFromManifest(manifest);
			
			if(namespace!=null && namespace!="${namespace}"){
				result = true;
			}
		}
		
		return result;
	}
	
	/**
	 * Extract Project NameSpace from AndroidManifest file
	 * 
	 * @param manifest Manifest File
	 * @return Project Name Space
	 */
	public static String getNameSpaceFromManifest(File manifest) {
		String projnamespace = null;
		SAXBuilder builder;
		Document doc;
		
		if(manifest.exists()) {
			builder = new SAXBuilder();								// Make engine
			try {
				doc = (Document) builder.build(manifest);			// Load XML File
				Element rootNode = doc.getRootElement(); 			// Load Root element
				//Namespace ns = rootNode.getNamespace("android");	// Load Name space (required for manipulate attributes)

				projnamespace = rootNode.getAttributeValue("package"); // Get Name Space from package declaration
				projnamespace = projnamespace.replaceAll("\\.", "/");
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return projnamespace;
	}
	
	/**
	 * Extract Android SDK Path from local.properties file
	 * 
	 * @param local_prop Local.properties File
	 * @return Android SDK Path
	 */
	public static String getSdkDirFromProject(File local_prop) {
		String result = null;
		
		if(local_prop.exists()){
			ArrayList<String> lines = FileUtils.FileToStringArray(local_prop);
			
			for(int i=0;i<lines.size();i++){
				if(lines.get(i).startsWith("sdk.dir=")){
					if(!lines.get(i).contains(TagConstant.ANDROID_SDK_DIR)){
						result=lines.get(i).replace("sdk.dir=", "");
					} else {
						ConsoleUtils.displayWarning("Android SDK Dir not defined please init project...");
					}
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * Extract Android SDK Path from local.properties file
	 * 
	 * @param local_prop Local.properties File
	 * @return Android SDK Path
	 */
	public static String getAndroidSdkVersion(String sdkPath) {
		String result = null;
		
		File sdkProperties = new File(sdkPath+"/tools/source.properties");
		if(sdkProperties.exists()){
			try{
				FileInputStream fis = new FileInputStream(sdkProperties);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while((line = br.readLine()) !=null){
					if(line.startsWith("Pkg.Revision")){
						result = line.substring(line.lastIndexOf("=")+1);
					}
				}
				br.close();
			}catch(IOException e){
				ConsoleUtils.displayError(e.getMessage());
			}
		}
		return result;
	}
}
