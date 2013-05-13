package com.tactfactory.mda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.template.TagConstant;
import com.tactfactory.mda.utils.ConsoleUtils;
import com.tactfactory.mda.utils.OsUtil;
import com.tactfactory.mda.utils.TactFileUtils;

public class ProjectDiscover {
	// DEMO/TEST MODE
	/** Default project name. */
	private static final String DEFAULT_PROJECT_NAME = "demact";
	
	/** Default project NameSpace. */
	private static final String DEFAULT_PROJECT_NAMESPACE = 
			"com.tactfactory.mda.test.demact";
	
	/** Android SDK version. */
	private static String androidSdkVersion;

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
							+ "full path [/root/android-sdk/]:");
			
			if (!Strings.isNullOrEmpty(sdkPath)) {
				ApplicationMetadata.setAndroidSdkPath(sdkPath.trim());
				androidSdkVersion = ProjectDiscover.setAndroidSdkPath(sdkPath.trim());
			} else {
				String osMessage = "Detected OS: ";
				
				if (OsUtil.isWindows()) {
					if (OsUtil.isX64()) {
						osMessage += "Windows x64";
						ApplicationMetadata.setAndroidSdkPath(
								"C:/Program Files/android-sdk");
						
					} else if (!OsUtil.isX64()) {
						osMessage += "Windows x86";
						ApplicationMetadata.setAndroidSdkPath(
								"C:/Program Files (x86)/android-sdk");
					} else {
						osMessage += "Windows x??";
						ApplicationMetadata.setAndroidSdkPath(
								"C:/Program Files/android-sdk");
					}
				} else if (OsUtil.isLinux()) {
					osMessage += "Linux";
					ApplicationMetadata.setAndroidSdkPath("/opt/android-sdk/");
				}
				
				// Debug Log
				ConsoleUtils.displayDebug(osMessage);
			}
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
		
		return projectNamespace;
	}
	
	/**
	 * Extract Project Name from configuration file.
	 * 
	 * @param config Configuration file
	 * @return Project Name Space
	 */
	public static String getProjectNameFromConfig(final File config) {
		/*String projname = null;
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
				// Load Name space (required for manipulate attributes)
				//Namespace ns = rootNode.getNamespace("android");	

				for (final Element element : rootNode.getChildren("string")) {
					if (element.getAttribute("name").getValue()
							.equals("app_name")) {
						projname = element.getValue();
						break;
					}
					
				}
			} catch (final JDOMException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
		
		return projname;*/
		// TODO MATCH : Voir avec Mickael pertinence d'utiliser le build.xml
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
		
		return projname;
	}
	
	/**
	 * Prompt Project Name to the user.
	 */
	public static void initProjectName() {
		if (Strings.isNullOrEmpty(ApplicationMetadata.INSTANCE.getName())) {
			final String projectName = 
					ConsoleUtils.getUserInput("Please enter your Project Name ["
						+ DEFAULT_PROJECT_NAME 
						+ "]:");
			
			if (Strings.isNullOrEmpty(projectName)) {
				ApplicationMetadata.INSTANCE.setName(DEFAULT_PROJECT_NAME.trim());
			} else {
				ApplicationMetadata.INSTANCE.setName(projectName.trim());
			}
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
										+ DEFAULT_PROJECT_NAMESPACE
										+ "]:");
				
				if (Strings.isNullOrEmpty(projectNameSpace)) {
					ApplicationMetadata.INSTANCE.setProjectNameSpace(
						DEFAULT_PROJECT_NAMESPACE
								.replaceAll("\\.", Context.DELIMITER));
					good = true;
					
				} else {
						
					String namespaceForm = 
							"^(((([a-z0-9_]+)\\.)*)([a-z0-9_]+))$";
					
					if (Pattern.matches(namespaceForm, projectNameSpace)) {
						ApplicationMetadata.INSTANCE.setProjectNameSpace(
							projectNameSpace.replaceAll("\\.", Context.DELIMITER)
								.trim());
						good = true;
					} else {
						ConsoleUtils.display(
								"You can't use special characters "
								+ "except '.' in the NameSpace.");
					}
				}
			}
		}
	}
	
}
