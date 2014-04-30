/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.google.common.base.Strings;
import com.tactfactory.harmony.command.questionnary.Question;
import com.tactfactory.harmony.command.questionnary.Questionnary;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * The project context is the specific configuration of your project.
 * 
 * You can find :
 * <ul><li>Project name;</li>
 * <li>Global name space;</li>
 * <li>SDK version</li>
 * </ul>
 */
public final class ProjectContext {
	// DEMO/TEST MODE
	/** Default project name. */
	private static String defaultProjectName = "demact";

	/** Default project NameSpace. */
	private static String defaultProjectNamespace =
			"com.tactfactory.harmony.test.demact";

	/**
	 * Private constructor.
	 */
	private ProjectContext() { }

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
						projectNamespace.replaceAll("\\.", HarmonyContext.DELIMITER);
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
				ConsoleUtils.displayError(e);
			} catch (final IOException e) {
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
				defaultProjectNamespace.replaceAll("\\.", HarmonyContext.DELIMITER)
					.trim());
		}
	}

}
