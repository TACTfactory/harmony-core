/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.command;

import java.util.LinkedHashMap;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.command.questionnary.Questionnary;
import com.tactfactory.harmony.command.questionnary.Question;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.android.AndroidAdapter;
import com.tactfactory.harmony.template.CommonGenerator;
import com.tactfactory.harmony.template.CommonGenerator.ViewType;
import com.tactfactory.harmony.utils.ConsoleUtils;

/**
 * Common generation commands.
 */
@PluginImplementation
public class CommonCommand extends BaseCommand {
	/** Question for view name. */
	private static final String QUESTION_VIEW_NAME =
			"Name of the view ? (The activity will be named {Name}Activity)";
//	/** Question for view type. */
//	private static final String QUESTION_VIEW_TYPE =
//			"Type of the view ?\n";
//	/** Question for linked entity. */
//	private static final String QUESTION_LINKED_ENTITY =
//			"Link this view to which entity ? (leave blank for no entity)";
	/** Question for package name. */
	private static final String QUESTION_VIEW_PACKAGE_NAME =
			"Name of the package the view will be in ?"
			+ "(The activity will be created in"
			+ "{YourProjectNamespace}.view.{PackageName})";
	
	/** Bundle name. */
	public static final String BUNDLE = "common";
	/** Subject. */
	public static final String SUBJECT = "generate";

	/** Action entity. */
	public static final String ACTION_STATIC = "static";

	//commands
	/** Command : COMMON:GENERATE:STATIC. */
	public static final String GENERATE_STATIC =
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_STATIC;

	/** Adapter. */
	private BaseAdapter adapter = new AndroidAdapter();


	@Override
	public final void execute(final String action,
			final String[] args,
			final String option) {
		ConsoleUtils.display("> Common generator ");

		this.setCommandArgs(Console.parseCommandArgs(args));

		if (GENERATE_STATIC.equals(action)) {
			this.generateStaticView();
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
		
		// Type question
//		String[] options = new String[ViewType.values().length];
//		List<String> choiceStrings = new ArrayList<String>(options.length);
//		
//		for (int i = 0; i < ViewType.values().length; i++) {
//			ViewType possibleType = ViewType.values()[i];
//			options[i] = String.valueOf(possibleType.getId());
//			choiceStrings.add(possibleType.getChoiceString());
//		}
//		String possibleTypes = Joiner.on('\n').join(choiceStrings);
//		Question viewTypeQuestion = new Question();
//		viewTypeQuestion.setQuestion(QUESTION_VIEW_TYPE + possibleTypes);
//		viewTypeQuestion.setParamName("type");
//		viewTypeQuestion.setShortParamName("t");
//		viewTypeQuestion.setAcceptBlankAnswer(false);
//		viewTypeQuestion.setAuthorizedValues(options);
		
		// Linked entity question
//		Question entityQuestion = new Question();
//		entityQuestion.setParamName("entity");
//		entityQuestion.setShortParamName("e");
//		entityQuestion.setAcceptBlankAnswer(true);
//		entityQuestion.setQuestion(QUESTION_LINKED_ENTITY);
		
		// Package name question
		Question packageQuestion = new Question();
		packageQuestion.setParamName("package");
		packageQuestion.setShortParamName("p");
		packageQuestion.setAcceptBlankAnswer(false);
		packageQuestion.setQuestion(QUESTION_VIEW_PACKAGE_NAME);
		
		// Add the questions to the questionnary
		questionnary.addQuestion("package", packageQuestion);
		questionnary.addQuestion("name", viewNameQuestion);
		//questionnary.addQuestion("type", viewTypeQuestion);
		//questionnary.addQuestion("entity", entityQuestion);
		questionnary.launchQuestionnary();
		
		/*ViewType viewType = ViewType.fromId(
				Integer.valueOf(questionnary.getAnswer("type")));*/
		ViewType viewType = ViewType.EMPTY;
		String viewName = questionnary.getAnswer("name");
		String packageName = questionnary.getAnswer("package").toLowerCase();
		//String linkedEntityName = questionnary.getAnswer("entity");
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

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		commands.put(GENERATE_STATIC, "Generate static view stack");
		
		ConsoleUtils.displaySummary(
				BUNDLE,
				commands);
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return GENERATE_STATIC.equals(command);
	}
}
