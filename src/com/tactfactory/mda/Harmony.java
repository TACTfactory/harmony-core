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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.command.FosCommand;
import com.tactfactory.mda.command.GeneralCommand;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.command.RouterCommand;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;
import com.tactfactory.mda.utils.OsUtil;

/** Harmony main class */
public class Harmony {
	/** Debug state*/
	public static final boolean DEBUG = true;
	public static final String VERSION = "Harmony version 0.1.0-DEV";
	
	/** Singleton of console */
	public static Harmony instance;
	/** Full path of project ( app folder in Harmony root ) */
	public static String pathProject = "../app";
	
	public static String pathTemplate = "../tpl";
	/** Project space */
	public static String projectFolder = "android";

	public static String projectName;
	public static String projectNameSpace;
	public static String androidSdkPath;
	
	private static final String DEFAULT_PROJECT_NAME = "toto";
	private static final String DEFAULT_PROJECT_NAMESPACE = "com.tactfactory.toto";
	
	public static boolean isConsole = false;
	
	public HashMap<Class<?>, BaseCommand> bootstrap = new HashMap<Class<?>, BaseCommand>();

	public Harmony() throws Exception {

		// Default Commands
		this.bootstrap.put(GeneralCommand.class, 	new GeneralCommand() );
		this.bootstrap.put(ProjectCommand.class, 	new ProjectCommand() );
		this.bootstrap.put(OrmCommand.class, 		new OrmCommand() );
		this.bootstrap.put(RouterCommand.class, 	new RouterCommand() );
		this.bootstrap.put(FosCommand.class, 		new FosCommand() );
	}

	/** Initialize Harmony 
	 * @throws Exception */
	protected void initialize() throws Exception {
		
		if (Strings.isNullOrEmpty(projectFolder)) {
			System.out.print("Not project folder define"); 
			throw new Exception("Not project folder define");
		}
		
		Harmony.instance = this;
		
		System.out.println("Current Working Path: "+new File(".").getCanonicalPath());

		if (Strings.isNullOrEmpty(Harmony.projectNameSpace)) {
			
			// get project namespace and project name from AndroidManifest.xml
			File manifest = new File(String.format("%s/%s/%s",Harmony.pathProject,Harmony.projectFolder,"AndroidManifest.xml"));
			if(manifest.exists()) {
				Harmony.projectNameSpace = Harmony.getNameSpaceFromManifest(manifest);

				String[] projectNameSpaceData = Harmony.projectNameSpace.split("/");
				Harmony.projectName = projectNameSpaceData[projectNameSpaceData.length-1];
			}
			else {
				System.out.println("NameSpace not defined, please init the project with 'project:init:all'");
			}
			// get android sdk dir from local.properties
			File local_prop = new File(String.format("%s/%s/%s",Harmony.pathProject,Harmony.projectFolder,"local.properties"));
			if(local_prop.exists())
				Harmony.androidSdkPath = Harmony.getSdkDirFromProject(local_prop);
			else
				System.out.println("Android SDK dir not defined, please init the project with 'project:init:all'");
		}
		else {
			String[] projectNameSpaceData = Harmony.projectNameSpace.split("/");
			Harmony.projectName = projectNameSpaceData[projectNameSpaceData.length-1];
		}
		
		// Debug Log
		if (Harmony.DEBUG){
			System.out.println("Current Project : " + projectName);
			System.out.println("Current NameSpace : " + projectNameSpace);
			System.out.println("Current Android SDK Path : " + androidSdkPath);
		}
	}
	
	/**
	 * Select the proper command class
	 * 
	 * @param commandName Class command name
	 * @return BaseCommand object
	 */
	public BaseCommand getCommand(Class<?> commandName) {
		return this.bootstrap.get(commandName);
	}

	/**
	 * Check and launch command in proper command class
	 * 
	 * @param action Command to execute
	 * @param args Commands arguments
	 * @param option Console option (ANSI,Debug,...)
	 */
	public void findAndExecute(String action, String[] args, String option) {
		
		// Select Action and launch
		boolean isfindAction = false;
		for (BaseCommand baseCommand : this.bootstrap.values()) {
			if (baseCommand.isAvailableCommand(action)) {
				baseCommand.execute(action,args,option);
				isfindAction = true;
			}
		}

		// No found action
		if (!isfindAction) {
			this.getCommand(GeneralCommand.class).execute(GeneralCommand.LIST,null,null);
		}
	}

	/**
	 * Generic user console prompt
	 * 
	 * @param promptMessage message to display
	 * @return input user input
	 */
	public static String getUserInput(String promptMessage)
	{
		String input = null;
		//  open up standard input
		System.out.print(promptMessage);

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
		if (Strings.isNullOrEmpty(Harmony.projectName)) {
			String projectName = Harmony.getUserInput("Please enter your Project Name ["+DEFAULT_PROJECT_NAME+"]:");
			if(projectName!=null && projectName.length()!=0)
				Harmony.projectName = projectName;
			else
				Harmony.projectName = DEFAULT_PROJECT_NAME;
		}
	}
	
	/**
	 * Prompt Project Name Space to the user
	 */
	public static void initProjectNameSpace()
	{
		if (Strings.isNullOrEmpty(Harmony.projectNameSpace)) {
			boolean good = false;
			while(!good)
			{
				String projectNameSpace = Harmony.getUserInput("Please enter your Project NameSpace ["+DEFAULT_PROJECT_NAMESPACE+"]:");
				if(projectNameSpace!=null && projectNameSpace.length()!=0) {
					if(projectNameSpace.endsWith(Harmony.projectName)){
						Harmony.projectNameSpace = projectNameSpace.replaceAll("\\.", "/");
						good = true;
					} else {
						System.out.println("The NameSpace has to end with Project Name !");
					}
				}
				else {
					Harmony.projectNameSpace = DEFAULT_PROJECT_NAMESPACE.replaceAll("\\.", "/");
					good=true;
				}
			}
		}
	}

	/**
	 * Prompt Project Android SDK Path to the user
	 */
	public static void initProjectAndroidSdkPath()
	{
		if (Strings.isNullOrEmpty(Harmony.androidSdkPath)) {
			String sdkPath = Harmony.getUserInput("Please enter AndroidSDK full path [/root/android-sdk/]:");
			if(sdkPath!=null && sdkPath.length()!=0){
				Harmony.androidSdkPath = sdkPath;
			} else {
				if(OsUtil.isWindows()) {
					if(!OsUtil.isX64()) {
						System.out.println("Detected: OS Windows x86");
						Harmony.androidSdkPath = String.format("%s/%s/","C:/Program Files (x86)","android-sdk");
					} else if(OsUtil.isX64()) {
						System.out.println("Detected: OS Windows x64");
						Harmony.androidSdkPath = String.format("%s/%s/","C:/Program Files","android-sdk");
					} else {
						System.out.println("Detected: OS Windows unkn arch");
						Harmony.androidSdkPath = String.format("%s/%s/","C:/Program Files","android-sdk");
					}
				}
				else if(OsUtil.isLinux()) {
					System.out.println("Detected: OS Linux");
					Harmony.androidSdkPath = "/root/android-sdk/";
				}
			}
		}
	}

	/**
	 * Check initialization of project by searching nameSpace in androidmanifest.xml
	 *
	 * @return true if success
	 */
	public static boolean isProjectInit()
	{
		boolean result = false;
		File projectFolder = new File(Harmony.projectFolder);
		if(projectFolder.exists() && projectFolder.listFiles().length!=0){
			File manifest = new File(Harmony.projectFolder+"AndroidManifest.xml");
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
	public static String getNameSpaceFromManifest(File manifest)
	{
		String projnamespace = null;
		SAXBuilder builder;
		Document doc;
		if(manifest.exists())
		{
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
	public static String getSdkDirFromProject(File local_prop)
	{
		String result = null;
		if(local_prop.exists()){
			ArrayList<String> lines = FileUtils.FileToStringArray(local_prop);
			
			for(int i=0;i<lines.size();i++){
				if(lines.get(i).startsWith("sdk.dir=")){
					if(!lines.get(i).contains(TagConstant.ANDROID_SDK_DIR)){
						result=lines.get(i).replace("sdk.dir=", "");
					} else {
						System.out.println("Android SDK Dir not defined please init project...");
					}
					break;
				}
			}
		}
		return result;
	}
}