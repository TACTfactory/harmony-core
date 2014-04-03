/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.harmony.command.questionnary.Question;
import com.tactfactory.harmony.command.questionnary.Questionnary;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.template.TagConstant;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.OsUtil;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 * Project Discover class.
 */
public final class ProjectDiscover {
	// DEMO/TEST MODE
	/** Default project name. */
	private static String defaultProjectName = "demact";

	/** Default project NameSpace. */
	private static String defaultProjectNamespace =
			"com.tactfactory.harmony.test.demact";

	/** Default project NameSpace. */
	private static String defaultSDKPath;

	/** Android SDK version. */
	private static String androidSdkVersion;
	
	/** Android home environment variables key. */
	private static final String ANDROID_HOME = "ANDROID_HOME";

	/**
	 * Private constructor.
	 */
	private ProjectDiscover() { }

	static {
		ProjectDiscover.defaultSDKPath = 
				ProjectDiscover.findSDKEnvironmentVariable();
		
		if (ProjectDiscover.defaultSDKPath == null) {
			if (OsUtil.isWindows()) {
				if (OsUtil.isX64()) {
					ProjectDiscover.defaultSDKPath = 
							"C:/Program Files (x86)/android-sdk";
				} else {
					ProjectDiscover.defaultSDKPath = 
							"C:/Program Files/android-sdk";
				}
			} else if (OsUtil.isLinux()) {
				ProjectDiscover.defaultSDKPath = "/opt/android-sdk/";
			} else {
				ProjectDiscover.defaultSDKPath = "/opt/android-sdk/";
			}
		}
	}
	
	/**
	 * Search the system's environment variables for the android sdk path and
	 * returns it.
	 * 
	 * @return The AndroidSDKPath if found, null otherwise
	 */
	private static String findSDKEnvironmentVariable() {
		String result = null;
		
		Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
        	if (envName.equalsIgnoreCase(ANDROID_HOME)) {
        		result = env.get(envName);
        	}
        }
		
		return result;
	}

	/**
	 * @param androidSdkVersion the androidSdkVersion to set
	 */
	public static void setAndroidSdkVersion(final String androidSdkVersion) {
		ProjectDiscover.androidSdkVersion = androidSdkVersion;
	}

	/**
	 * @return the androidSdkVersion
	 */
	public static String getAndroidSdkVersion() {
		return androidSdkVersion;
	}

	/**
	 * Extract Android SDK Path from project.properties file.
	 *
	 * @param fileProp project.properties File
	 * @return Android SDK Path
	 */
	public static String getTargetFromPropertiesFile(final File fileProp) {
		String result = null;
		
		if (fileProp.exists()) {
			final List<String> lines =
					TactFileUtils.fileToStringArray(fileProp);

			for (int i = 0; i < lines.size(); i++) {
				if (lines.get(i).startsWith("target=")) {
					result = lines.get(i).replace("target=", "").trim();
					break;
				}
			}
		}
		return result;
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
			ProjectDiscover.defaultSDKPath = result;
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
	 * 
	 * @param arguments The console arguments passed by the user
	 */
	public static void initProjectAndroidSdkPath(
			HashMap<String, String> arguments) {
		
		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			Questionnary questionnary = new Questionnary(arguments);
			Question question = new Question();
			question.setParamName("androidsdk");
			question.setDefaultValue(ProjectDiscover.defaultSDKPath);
			question.setQuestion(String.format(
					"Please enter AndroidSDK full path [%s]:",
							ProjectDiscover.defaultSDKPath));
			question.setShortParamName("sdk");
			
			
			questionnary.addQuestion("sdk", question);
			questionnary.launchQuestionnary();
			ApplicationMetadata.setAndroidSdkPath(
					questionnary.getAnswer("sdk"));
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
				projectNamespace =
						projectNamespace.replaceAll("\\.", Context.DELIMITER);
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
	 * 
	 * @param arguments The console arguments passed by the user
	 */
	public static void initProjectName(HashMap<String, String> arguments) {
		if (Strings.isNullOrEmpty(ApplicationMetadata.INSTANCE.getName())) {
			Questionnary questionnary = new Questionnary(arguments);
			Question question = new Question();
			question.setParamName("name");
			question.setDefaultValue(defaultProjectName);
			question.setQuestion(String.format(
					"Please enter your Project Name [%s]:",
					defaultProjectName));
			question.setShortParamName("n");
			
			
			questionnary.addQuestion("name", question);
			questionnary.launchQuestionnary();
			ApplicationMetadata.INSTANCE.setName(questionnary.getAnswer("name"));
		}
	}

	/**
	 * Prompt Project Name Space to the user.
	 * 
	 * @param arguments The console arguments passed by the user
	 */
	public static void initProjectNameSpace(
			HashMap<String, String> arguments) {
		if (Strings.isNullOrEmpty(
				ApplicationMetadata.INSTANCE.getProjectNameSpace())) {
			Questionnary questionnary = new Questionnary(arguments);
			Question question = new Question();
			question.setParamName("namespace");
			question.setDefaultValue(defaultProjectNamespace);
			question.setQuestion(String.format(
					"Please enter your Project NameSpace [%s]:",
					defaultProjectNamespace));
			question.setShortParamName("ns");
			
			
			questionnary.addQuestion("namespace", question);
			
			boolean good = false;

			while (!good) {
				questionnary.launchQuestionnary();
				String nameSpace = questionnary.getAnswer("namespace");

				if (Strings.isNullOrEmpty(nameSpace)) {
					good = true;

				} else {

					final String namespaceForm =
							"^(((([a-z0-9_]+)\\.)*)([a-z0-9_]+))$";

					if (Pattern.matches(namespaceForm, nameSpace)) {
						defaultProjectNamespace = nameSpace.trim();
						good = true;
					} else {
						ConsoleUtils.display(
								"You can't use special characters "
								+ "except '.' in the NameSpace.");
						question.setParamName(null);
						question.setShortParamName(null);
					}
				}
			}

			ApplicationMetadata.INSTANCE.setProjectNameSpace(
				defaultProjectNamespace.replaceAll("\\.", Context.DELIMITER)
					.trim());
		}
	}

}
