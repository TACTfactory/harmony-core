/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

import japa.parser.ast.CompilationUnit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.command.FosCommand;
import com.tactfactory.mda.command.GeneralCommand;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.command.RouterCommand;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.FileUtils;

/** Harmony main class */
public class Harmony {
	/** Debug state*/
	public static final boolean DEBUG = true;
	public static final String VERSION = "Harmony version 0.1.0-DEV";
	/** Singleton of console */
	public static Harmony instance;
	/** Full path of project ( app folder in Harmony root ) */
	public static String pathProject = "../app/";
	/** Project space */
	public static String projectFolder = "android/";

	public static String projectName;
	public static String projectNameSpace;
	public static String androidSdkPath;

	
	public HashMap<Class<?>, BaseCommand> bootstrap = new HashMap<Class<?>, BaseCommand>();
	protected ArrayList<CompilationUnit> entities = new ArrayList<CompilationUnit>();

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
		
		Harmony.instance = this;
		
		System.out.println("Current Working Path: "+new File(".").getCanonicalPath());

		if (Harmony.projectNameSpace == null || Harmony.projectNameSpace.equals("")) {
			
			// get project namespace and project name from AndroidManifest.xml
			File manifest = new File(Harmony.pathProject+Harmony.projectFolder+"AndroidManifest.xml");
			if(manifest.exists()) {
				Harmony.projectNameSpace = Harmony.getNameSpaceFromManifest(manifest);

				String[] projectNameSpaceData = Harmony.projectNameSpace.split("/");
				Harmony.projectName = projectNameSpaceData[projectNameSpaceData.length-1];
			}
			else {
				System.out.println("NameSpace not defined, please init the project with 'project:init:all'");
			}
			// get android sdk dir from local.properties
			File local_prop = new File(Harmony.pathProject+Harmony.projectFolder+"local.properties");
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
	
	/** Select the proper command class */
	public BaseCommand getCommand(Class<?> commandName) {
		return this.bootstrap.get(commandName);
	}

	/**
	 * @return the entities
	 */
	public ArrayList<CompilationUnit> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(ArrayList<CompilationUnit> entities) {
		this.entities = entities;
	}

	/** Check and launch command in proper command class */
	public void findAndExecute(String action) {
		String[] actions = {action};
		this.findAndExecute(actions);
	}
	public void findAndExecute(String[] action) {
		
		// Select Action and launch
		boolean isfindAction = false;
		for (BaseCommand baseCommand : this.bootstrap.values()) {
			if (baseCommand.isAvailableCommand(action[0])) {
				baseCommand.execute(action[0], entities);
				isfindAction = true;
			}
		}

		// No found action
		if (!isfindAction) {
			this.getCommand(GeneralCommand.class).execute(GeneralCommand.LIST, null);
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

	/** Check initialization of project by searching nameSpace in androidmanifest.xml */
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
	
	/** Extract Project NameSpace from AndroidManifest file */
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
	
	/** Extract Android SDK Path from local.properties file */
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