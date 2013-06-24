package com.tactfactory.harmony;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.template.TagConstant;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.OsUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

public class ProjectDiscover {
	// DEMO/TEST MODE
	/** Default project name. */
	private static String defaultProjectName = "demact";
	
	/** Default project NameSpace. */
	private static String defaultProjectNamespace = 
			"com.tactfactory.harmony.test.demact";
	
	/** Default project NameSpace. */
	private static String defaultSDKPath = 
			"/opt/android-sdk/";
	
	/** Android SDK version. */
	private static String androidSdkVersion;
	
	private static String detectedOS = "Unknown";

	{
		if (OsUtil.isWindows()) {
			if (OsUtil.isX64()) {
				detectedOS = "Windows x64";
				defaultSDKPath = "C:/Program Files/android-sdk";
				
			} else if (!OsUtil.isX64()) {
				detectedOS = "Windows x86";
				defaultSDKPath = "C:/Program Files (x86)/android-sdk";
			} else {
				detectedOS = "Windows x??";
				defaultSDKPath = "C:/Program Files/android-sdk";
			}
		} else if (OsUtil.isLinux()) {
			detectedOS = "Linux";
			defaultSDKPath = "/opt/android-sdk/";
		}
	}
	
	/**
	 * @param androidSdkVersion the androidSdkVersion to set
	 */
	public static void setAndroidSdkVersion(String androidSdkVersion) {
		ProjectDiscover.androidSdkVersion = androidSdkVersion;
	}

	/**
	 * @return the androidSdkVersion
	 */
	public static String getAndroidSdkVersion() {
		return androidSdkVersion;
	}
	

	/**
	 * Extract Android SDK Path from .properties file.
	 * 
	 * @param fileProp .properties File
	 * @return Android SDK Path
	 */
	public static String getSdkDirFromPropertiesFile(final File fileProp) {
		String result = null;
		
		if (fileProp.exists()) {
			final List<String> lines = 
					TactFileUtils.fileToStringArray(fileProp);
			
			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).startsWith("sdk.dir=")) {
					if (lines.get(i).contains(TagConstant.ANDROID_SDK_DIR)) {
						ConsoleUtils.displayWarning(
								"Android SDK Dir not defined," 
								+ " please init project...");
					} else {
						result = lines.get(i).replace("sdk.dir=", "");
					}
					break;
				}
			}
		}
		
		if (result != null) {
			defaultSDKPath = result;
		}
		
		return result;
	}
	
	/**
	 * Extract Android SDK Path from .properties file.
	 * 
	 * @param filePath .properties File path
	 * @return Android SDK Path
	 */
	public static String getSdkDirFromPropertiesFile(final String filePath) {
		String result = null;
		
		final File fileProp = new File(filePath);
		result = getSdkDirFromPropertiesFile(fileProp);
		
		if (result != null) {
			androidSdkVersion = setAndroidSdkPath(result);
		}
		
		return result;
	}
	
	/**
	 * Extract Android SDK Path from local.properties file.
	 * 
	 * @param sdkPath The sdk path
	 * @return Android SDK Path
	 */
	public static String setAndroidSdkPath(final String sdkPath) {
		String result = null;
		
		final File sdkProperties = 
				new File(sdkPath + "/tools/source.properties");
		
		if (sdkProperties.exists()) {
			try {
				final FileInputStream fis = new FileInputStream(sdkProperties);
				final InputStreamReader isr =
						new InputStreamReader(fis, 
								TactFileUtils.DEFAULT_ENCODING);
				final BufferedReader br = new BufferedReader(isr);
				String line = br.readLine();
				while (line != null) {
					if (line.startsWith("Pkg.Revision")) {
						result = line.substring(line.lastIndexOf('=') + 1);
						
						break;
					}
					line = br.readLine();
				}
				br.close();
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		}
		return result;
	}
	
	/**
	 * Prompt Project Android SDK Path to the user.
	 */
	public static void initProjectAndroidSdkPath() {
		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			final String sdkPath = 
					ConsoleUtils.getUserInput("Please enter AndroidSDK " 
							+ "full path ["
							+ defaultSDKPath
							+ "]:");
			
			if (!Strings.isNullOrEmpty(sdkPath)) {
				defaultSDKPath = sdkPath.trim();
				
			} else {
				String osMessage = "Detected OS: " + detectedOS;
				
				// Debug Log
				ConsoleUtils.displayDebug(osMessage);
			}
			ApplicationMetadata.setAndroidSdkPath(defaultSDKPath);
			androidSdkVersion = ProjectDiscover.setAndroidSdkPath(
					ApplicationMetadata.getAndroidSdkPath());
		}
	}
	
	/**
	 * Extract Project NameSpace from AndroidManifest file.
	 * 
	 * @param manifest Manifest File
	 * @return Project Name Space
	 */
	public static String getNameSpaceFromManifest(final File manifest) {
		String projectNamespace = null;
		SAXBuilder builder;
		Document doc;
		
		if (manifest.exists()) {
			// Make engine
			builder = new SAXBuilder();
			try {
				// Load XML File
				doc = builder.build(manifest);
				
				// Load Root element
				final Element rootNode = doc.getRootElement();

				// Get Name Space from package declaration
				projectNamespace = rootNode.getAttributeValue("package"); 
				projectNamespace = projectNamespace.replaceAll("\\.", Context.DELIMITER);
			} catch (final JDOMException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
		
		if (projectNamespace != null) {
			defaultProjectNamespace = projectNamespace.trim();
		}
		
		return projectNamespace;
	}
	
	/**
	 * Extract Project Name from configuration file.
	 * 
	 * @param config Configuration file
	 * @return Project Name Space
	 */
	public static String getProjectNameFromConfig(final File config) {
		String projname = null;
		SAXBuilder builder;
		Document doc;
		
		if (config.exists()) {
			// Make engine
			builder = new SAXBuilder();	
			try {
				// Load XML File
				doc = builder.build(config);			
				// Load Root element
				final Element rootNode = doc.getRootElement(); 			
				projname = rootNode.getAttribute("name").getValue();
			} catch (final JDOMException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
		if (projname != null) {
			defaultProjectName = projname.trim();
		}
		
		return projname;
	}
	
	/**
	 * Prompt Project Name to the user.
	 */
	public static void initProjectName() {
		if (Strings.isNullOrEmpty(ApplicationMetadata.INSTANCE.getName())) {
			final String projectName = 
					ConsoleUtils.getUserInput("Please enter your Project Name ["
						+ defaultProjectName 
						+ "]:");
			
			if (!Strings.isNullOrEmpty(projectName)) {
				defaultProjectName = projectName.trim();
			}
			ApplicationMetadata.INSTANCE.setName(defaultProjectName);
		}
	}
	
	/**
	 * Prompt Project Name Space to the user.
	 */
	public static void initProjectNameSpace() {
		if (Strings.isNullOrEmpty(
				ApplicationMetadata.INSTANCE.getProjectNameSpace())) {
			boolean good = false;
			
			while (!good) {
				final String projectNameSpace = ConsoleUtils.getUserInput(
								"Please enter your Project NameSpace [" 
										+ defaultProjectNamespace
										+ "]:");
				
				if (Strings.isNullOrEmpty(projectNameSpace)) {
					good = true;
					
				} else {
						
					String namespaceForm = 
							"^(((([a-z0-9_]+)\\.)*)([a-z0-9_]+))$";
					
					if (Pattern.matches(namespaceForm, projectNameSpace)) {
						defaultProjectNamespace = projectNameSpace.trim();
						good = true;
					} else {
						ConsoleUtils.display(
								"You can't use special characters "
								+ "except '.' in the NameSpace.");
					}
				}
			}

			ApplicationMetadata.INSTANCE.setProjectNameSpace(
				defaultProjectNamespace.replaceAll("\\.", Context.DELIMITER)
					.trim());
		}
	}
	
}
