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
	private final static String PRJ_NAME = "demact";

    /** Default project NameSpace. */
	private final static String PRJ_NS =
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
	public static void loadNameSpaceFromManifest(final File manifest) {
		String result = null;
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
				result = rootNode.getAttributeValue("package");
				result = result.replaceAll("\\.", HarmonyContext.DELIMITER);
			} catch (final JDOMException e) {
				ConsoleUtils.displayError(e);
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		}

		if (result != null) {
			ApplicationMetadata.INSTANCE.setName(result.trim());
		}
	}

	/**
	 * Extract Project Name from configuration file.
	 *
	 * @param config Configuration file
	 * @return Project Name Space
	 */
	public static void loadProjectNameFromConfig(final File config) {
		String result = null;
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
				result = rootNode.getAttribute("name").getValue();
			} catch (final JDOMException e) {
				ConsoleUtils.displayError(e);
			} catch (final IOException e) {
				ConsoleUtils.displayError(e);
			}
		}

		if (result != null) {
			ApplicationMetadata.INSTANCE.setName(result.trim());
		}
	}

   /**
     * Prompt Project Name to the user.
     * 
     * @param arguments The console arguments passed by the user
     */
    public static void initProjectName(HashMap<String, String> arguments) {
        if (Strings.isNullOrEmpty(ApplicationMetadata.INSTANCE.getName())) {
            final String KEY =  "name";

            Question question = new Question();
            question.setParamName(KEY, "n");
            question.setQuestion("Please enter your Project Name [%s]:", PRJ_NAME);
            question.setDefaultValue(PRJ_NAME);

            Questionnary questionnary = new Questionnary(arguments);
            questionnary.addQuestion(KEY, question);
            questionnary.launchQuestionnary();
            ApplicationMetadata.INSTANCE.setName(questionnary.getAnswer(KEY));
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
            final String KEY =  "namespace";
            String result = null;
            
            Question question = new Question();
            question.setParamName(KEY, "ns");
            question.setQuestion("Please enter your Project NameSpace [%s]:",
                    PRJ_NS);
            question.setDefaultValue(PRJ_NS);
            
            Questionnary questionnary = new Questionnary(arguments);
            questionnary.addQuestion(KEY, question);
            
            boolean good = false;

            while (!good) {
                questionnary.launchQuestionnary();
                String nameSpace = questionnary.getAnswer(KEY);

                if (Strings.isNullOrEmpty(nameSpace)) {
                    good = true;
                } else {

                    final String namespaceForm =
                            "^(((([a-z0-9_]+)\\.)*)([a-z0-9_]+))$";

                    if (Pattern.matches(namespaceForm, nameSpace)) {
                        result = nameSpace.trim();
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

            if (result != null) {
                ApplicationMetadata.INSTANCE.setProjectNameSpace(
                        result.replaceAll("\\.", HarmonyContext.DELIMITER).trim());
            }
        }
    }
}
