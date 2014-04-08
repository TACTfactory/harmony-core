/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.ElementFilter;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.google.common.base.Joiner;
import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.command.questionnary.Questionnary;
import com.tactfactory.harmony.command.questionnary.Question;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.AndroidAdapter;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.CommonGenerator;
import com.tactfactory.harmony.template.CommonGenerator.ViewType;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.XMLUtils;

/**
 * Common generation commands.
 */
@PluginImplementation
public class CommonCommand extends BaseCommand {
	/** Question for view name. */
	private static final String QUESTION_VIEW_NAME =
			"Name of the view ? (The activity will be named {Name}Activity)";
	/** Question for package name. */
	private static final String QUESTION_VIEW_PACKAGE_NAME =
			"Name of the package the view will be in ?"
			+ "(The activity will be created in"
			+ "{YourProjectNamespace}.view.{PackageName})";
	
	/** Bundle name. */
	public static final String BUNDLE = "common";
	/** Generate. */
	public static final String SUBJECT_GENERATE = "generate";
	/** Clean. */
	public static final String SUBJECT_CLEAN = "clean";

	/** Action static. */
	public static final String ACTION_STATIC = "static";
	/** Action strings. */
	public static final String ACTION_STRINGS = "strings";

	//commands
	/** Command : COMMON:GENERATE:STATIC. */
	public static final String GENERATE_STATIC =
			BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_STATIC;
	/** Command : COMMON:CLEAN:STRINGS. */
	public static final String CLEAN_STRINGS =
			BUNDLE + SEPARATOR + SUBJECT_CLEAN + SEPARATOR + ACTION_STRINGS;

	/** Adapter. */
	private BaseAdapter adapter = new AndroidAdapter();
	
	/** Temp File name for lint report. */
	private static final String TMP_LINT_REPORT_FILENAME = 
			"tmp_lint_unusedstrings.xml";
	
	/** Log warning : empty file. */
	private static final String LOG_WARNING_EMPTY_FILE =
			"The file %s is now empty. You can remove it.";

	/** Log info : removed strings count. */
	private static final String LOG_REMOVED_COUNT =
			">>> Removed %s strings !";

	/** Log info : cleaning strings. */
	private static final String LOG_CLEANING_STRINGS =
			">> Cleaning strings...";


	@Override
	public final void execute(final String action,
			final String[] args,
			final String option) {
		ConsoleUtils.display("> Common generator ");

		this.setCommandArgs(Console.parseCommandArgs(args));

		if (GENERATE_STATIC.equals(action)) {
			this.generateStaticView();
		} else if (CLEAN_STRINGS.equals(action)) {
			this.cleanUnusedStrings();
		}
	}


	/**
	 * Generate an empty bundle.
	 */
	private void generateStaticView() {
		this.generateMetas();
		Questionnary questionnary = new Questionnary(this.getCommandArgs());
		
		// Name Question
		Question viewNameQuestion = new Question();
		viewNameQuestion.setQuestion(QUESTION_VIEW_NAME);
		viewNameQuestion.setParamName("name");
		viewNameQuestion.setShortParamName("n");
		viewNameQuestion.setAcceptBlankAnswer(false);
		
		// Package name question
		Question packageQuestion = new Question();
		packageQuestion.setParamName("package");
		packageQuestion.setShortParamName("p");
		packageQuestion.setAcceptBlankAnswer(false);
		packageQuestion.setQuestion(QUESTION_VIEW_PACKAGE_NAME);
		
		// Add the questions to the questionnary
		questionnary.addQuestion("package", packageQuestion);
		questionnary.addQuestion("name", viewNameQuestion);
		questionnary.launchQuestionnary();
		
		ViewType viewType = ViewType.EMPTY;
		String viewName = questionnary.getAnswer("name");
		String packageName = questionnary.getAnswer("package").toLowerCase();
		String linkedEntityName = "";
		
		EntityMetadata linkedEntity = 
				ApplicationMetadata.INSTANCE.getEntities().get(
						linkedEntityName);
		
		
		new CommonGenerator(this.adapter).generateStaticView(
				packageName,
				viewName,
				viewType,
				linkedEntity);
	}
	
	/**
	 * This method will clean the unused android XML strings.
	 */
	private void cleanUnusedStrings() {
		this.generateMetas();
		
		ConsoleUtils.display(LOG_CLEANING_STRINGS);
		
		this.generateLintErrorFile(
				TMP_LINT_REPORT_FILENAME,
				true,
				"UnusedResources");
		
		Map<String, List<String>> unusedStrings = 
				this.getUnusedStringsErrors(TMP_LINT_REPORT_FILENAME);
		
		for (String file : unusedStrings.keySet()) {
			this.removeStringsFromFile(file, unusedStrings.get(file));
		}
	}
	
	/** 
	 * Generate a lint error report in xml.
	 * @param fileName The file where the report is generated.
	 * @param temporaryFile If true, the file will be deleted after harmony
	 * 		quits. (DeleteOnExit)
	 * @param checks The list of checks to check. Need at least one.
	 */
	private void generateLintErrorFile(
			final String fileName,
			final boolean temporaryFile,
			final String... checks) {
		
		String sdkPath = ApplicationMetadata.getAndroidSdkPath() + "/tools/lint";
		ArrayList<String> command = new ArrayList<String>();
		command.add(sdkPath);
		command.add("--xml");
		command.add(fileName);
		command.add("--nolines");
		command.add("--check");
		command.add(Joiner.on(',').join(checks));
		command.add("--fullpath");
		command.add("./");
		ConsoleUtils.launchCommand(command);
		
		if (temporaryFile) {
			File report = new File(fileName);
			report.deleteOnExit();
		}
	}
	
	/**
	 * Reads a lint report file and gives back the list of unused strings.
	 * @param fileName The file to read.
	 * @return The list of unused strings as follow : 
	 * 							Map<FileName, List<UnusedStringId>>
	 */
	private Map<String, List<String>> getUnusedStringsErrors(String fileName) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		Document doc = XMLUtils.openXML(fileName);
		Element root = doc.getRootElement();
		List<Element> lintErrors = root.getChildren();
		if (lintErrors != null) {
			for (Element error : lintErrors) {
				String id = error.getAttributeValue("id");
				if (id.equals("UnusedResources")) {
					String message = error.getAttributeValue("message");
					
					int start = message.indexOf("R.string.");
					
					if (start != -1) {
						start += "R.string.".length();
						int end = message.indexOf(" ", start);
						
						String stringId = message.substring(start, end);
						
						Element location = error.getChild("location");
						String file = location.getAttributeValue("file");
						
						if (!result.containsKey(file)) {
							result.put(file, new ArrayList<String>());
						}
						result.get(file).add(stringId);
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Remove a set of string from an Android strings xml file.
	 * 
	 * @param fileName The file name
	 * @param stringIds The list of string ids to remove
	 */
	private void removeStringsFromFile(String fileName, List<String> stringIds) {
		Document doc = XMLUtils.openXML(fileName);
		Element root = doc.getRootElement();
		AndroidStringsFilter filter = new AndroidStringsFilter(stringIds); 
		root.removeContent(filter);
		ConsoleUtils.display(String.format(
				LOG_REMOVED_COUNT,
				filter.getNbStringsFiltered()));
		
		if (root.getChildren().isEmpty()) {
			ConsoleUtils.displayWarning(String.format(
					LOG_WARNING_EMPTY_FILE,
					fileName));
		}
		XMLUtils.writeXMLToFile(doc, fileName);
	}
	
	/**
	 * A JDOM custom ElementFilter which is used to filter android strings
	 * by their ids.
	 */
	private class AndroidStringsFilter extends ElementFilter {
		/** Serial UUID. */
		private static final long serialVersionUID = 6827981466794994869L;
		/** Attribute name constant. */
		private static final String ATTRIBUTE_NAME = "name";
		/** Log for removing string. */
		private static final String LOG_REMOVE = ">>> Removing string '%s'...";
		/** The list of ids to filter. */
		private List<String> idsToFilter;
		/** Already filtered ids array. */
		private List<String> alreadyFiltered;
		
		/**
		 * Constructor.
		 * @param idsToFilter The list of ids to filter. If a String Element
		 * has not its id in the list, it will be removed from the list.
		 */
		public AndroidStringsFilter(List<String> idsToFilter) {
			this.idsToFilter = idsToFilter;
			this.alreadyFiltered = new ArrayList<String>(idsToFilter.size());
		}

		@Override
		public Element filter(Object arg0) {
			Element result = super.filter(arg0);
			
			if (result != null) {
				String name = result.getAttributeValue(ATTRIBUTE_NAME);
				if (this.idsToFilter.contains(name)) {
					if (!this.alreadyFiltered.contains(name)) {
						ConsoleUtils.displayDebug(
								String.format(LOG_REMOVE, name));
						this.alreadyFiltered.add(name);
					}
				} else {
					result = null;
				}
			}
			
			return result;
		}
		
		/**
		 * Gives back the number of found strings.
		 * 
		 * @return the number of found strings.
		 */
		public int getNbStringsFiltered() {
			return this.alreadyFiltered.size();
		}
	}

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		commands.put(GENERATE_STATIC, "Generate static view stack");
		commands.put(CLEAN_STRINGS, "Clean the unused strings of the project");
		
		ConsoleUtils.displaySummary(
				BUNDLE,
				commands);
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return GENERATE_STATIC.equals(command)
				|| CLEAN_STRINGS.equals(command);
	}
}
