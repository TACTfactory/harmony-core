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

import com.tactfactory.harmony.Console;
import com.tactfactory.harmony.command.questionnary.Question;
import com.tactfactory.harmony.command.questionnary.Questionnary;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.BundleGenerator;
import com.tactfactory.harmony.utils.ConsoleUtils;

import net.xeoh.plugins.base.annotations.PluginImplementation;

/**
 * Bundle Code Generator.
 */
@PluginImplementation
public class BundleCommand extends BaseCommand {

	/** Bundle name. */
	public static final String BUNDLE = "bundle";
	/** Subject. */
	public static final String SUBJECT = "generate";

	/** Action entity. */
	public static final String ACTION_EMPTY_BUNDLE = "emptybundle";

	//commands
	/** Command : BUNDLE:GENERATE:EMPTYBUNDLE. */
	public static final String GENERATE_EMPTY_BUNDLE =
			BUNDLE + SEPARATOR + SUBJECT + SEPARATOR + ACTION_EMPTY_BUNDLE;

	@Override
	public final void execute(final String action,
			final String[] args,
			final String option) {
		ConsoleUtils.display("> Bundle generator ");

		this.setCommandArgs(Console.parseCommandArgs(args));

		if (GENERATE_EMPTY_BUNDLE.equals(action)) {
			this.generateEmptyBundle();
		}
	}


	/**
	 * Generate an empty bundle.
	 */
	private void generateEmptyBundle() {
		// Confirmation
		if (ConsoleUtils.isConsole()) {
			Questionnary questionnary = new Questionnary(this.getCommandArgs());
			Question ownerQuestion = new Question();
			ownerQuestion.setQuestion("Bundle's owner ?");
			ownerQuestion.setParamName("owner");
			ownerQuestion.setShortParamName("o");
			ownerQuestion.setAcceptBlankAnswer(false);
			questionnary.addQuestion("owner", ownerQuestion);

			Question nameQuestion = new Question();
			nameQuestion.setQuestion("Name of your Bundle ?");
			nameQuestion.setParamName("name");
			nameQuestion.setShortParamName("n");
			ownerQuestion.setAcceptBlankAnswer(false);
			questionnary.addQuestion("name", nameQuestion);

			Question namespaceQuestion = new Question();
			namespaceQuestion.setQuestion("Namespace of your Bundle ?");
			namespaceQuestion.setParamName("namespace");
			namespaceQuestion.setShortParamName("ns");
			ownerQuestion.setAcceptBlankAnswer(false);
			questionnary.addQuestion("namespace", namespaceQuestion);
			
			questionnary.launchQuestionnary();

			for(BaseAdapter adapter : this.adapters) {
			    new BundleGenerator(adapter).generateBundleFiles(
			            questionnary.getAnswer("owner"),
			            questionnary.getAnswer("name"),
			            questionnary.getAnswer("namespace"));
			}
		}
	}

	@Override
	public final void summary() {
		LinkedHashMap<String, String> commands = new LinkedHashMap<String, String>();
		commands.put(GENERATE_EMPTY_BUNDLE, "Generate Empty Bundle");
		
		ConsoleUtils.displaySummary(
				BUNDLE,
				commands);
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return GENERATE_EMPTY_BUNDLE.equals(command);
	}

}
