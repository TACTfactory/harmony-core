/**
 * This file is part of the Harmony package.
 *
 * (c) Gregg Cesarine <gregg.cesarine@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.symfony.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.Console;
import com.tactfactory.mda.bundles.rest.parser.RestCompletor;
import com.tactfactory.mda.bundles.rest.parser.RestParser;
import com.tactfactory.mda.bundles.symfony.adapter.SymfonyAdapter;
import com.tactfactory.mda.bundles.symfony.template.WebGenerator;
import com.tactfactory.mda.command.BaseCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.plateforme.AndroidAdapter;
import com.tactfactory.mda.utils.ConsoleUtils;

/**
 * Symfony command class.
 */
@PluginImplementation
public class SymfonyCommand extends BaseCommand {
	
	//bundle name
	/** Bundle name. */
	public static final String BUNDLE = "web";
	/** Symfony subject. */
	public static final String SUBJECT_SYMFONY = "symfony";
	/** Bundles subject. */
	public static final String SUBJECT_BUNDLES = "bundles";
	/** Project subject. */
	public static final String SUBJECT_PROJECT = "project";
	/** Generation subject. */
	public static final String SUBJECT_GENERATE = "generate";

	//actions
	/** Install Action. */
	public static final String ACTION_INSTALL = "install";
	/** Init Action. */
	public static final String ACTION_INIT = "init";
	/** Entities Action. */
	public static final String ACTION_ENTITIES = "entities";
	/** Repositories Action. */
	public static final String ACTION_REPOSITORIES = "repositories";

	//commands
	/** Command : WEB:PROJECT:INIT. */
	public static final String PROJECT_INIT = 
			BUNDLE + SEPARATOR + SUBJECT_PROJECT + SEPARATOR + ACTION_INIT;
	
	/** Command : WEB:GENERATE:ENTITIES. */
	public static final String GENERATE_ENTITIES =
			BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_ENTITIES;
	
	/** Command : WEB:GENERATE:REPOSITORIES. */
	public static final String GENERATE_REPOSITORIES =
			BUNDLE + SEPARATOR + SUBJECT_GENERATE 
			+ SEPARATOR + ACTION_REPOSITORIES;
	
	/** Command : WEB:SYMFONY:INSTALL. */
	public static final String INSTALL_SYMFONY = 
			BUNDLE + SEPARATOR + SUBJECT_SYMFONY + SEPARATOR + ACTION_INSTALL;
	
	/** Command : WEB:BUNDLES:INSTALL. */
	public static final String INSTALL_BUNDLES = 
			BUNDLE + SEPARATOR + SUBJECT_BUNDLES + SEPARATOR + ACTION_INSTALL;

	@Override
	public final void execute(final String action, 
			final String[] args, 
			final String option) {
		ConsoleUtils.display("> Adapters Generator");

		this.setCommandArgs(Console.parseCommandArgs(args));
		if (action.equals(GENERATE_ENTITIES)) {
			this.generateEntities();
		} else if (action.equals(GENERATE_REPOSITORIES)) {
			this.generateWebRepositories();
		} else if (action.equals(PROJECT_INIT)) {
			this.initProject();
		} else if (action.equals(INSTALL_BUNDLES)) {
			this.installBundles();
		} else if (action.equals(INSTALL_SYMFONY)) {
			this.installSymfony();
		}
	}
	
	/**
	 * Generate java code files from parsed Entities.
	 */
	protected final void initProject() {

		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.entities != null) {
			try {
				new WebGenerator(new AndroidAdapter(),
						new SymfonyAdapter()).initProject();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/**
	 * Generate yaml entities for symfony.
	 */
	protected final void generateEntities() {

		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.entities != null) {
			try {
				new WebGenerator(new AndroidAdapter(),
						new SymfonyAdapter()).generateEntities();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/**
	 * Generate yaml entities for symfony.
	 */
	protected final void generateWebRepositories() {

		this.generateMetas();
		if (ApplicationMetadata.INSTANCE.entities != null) {
			try {
				new WebGenerator(new AndroidAdapter(), 
						new SymfonyAdapter()).generateWebControllers();
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				ConsoleUtils.displayError(e);
			}
		}
	}
	
	/**
	 * Generate yaml entities for symfony.
	 */
	protected final void installSymfony() {
		try {
			new WebGenerator(new AndroidAdapter(), 
					new SymfonyAdapter()).installSymfony();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	/**
	 * Generate yaml entities for symfony.
	 */
	protected final void installBundles() {
		try {
			new WebGenerator(new AndroidAdapter(),
					new SymfonyAdapter()).installBundles();
		} catch (final Exception e) {
			ConsoleUtils.displayError(e);
		}
	}
	
	@Override
	public final void generateMetas() {
		this.registerParser(new RestParser());
		super.generateMetas();
		new RestCompletor().generateApplicationRestMetadata(
				ApplicationMetadata.INSTANCE);
	}
	
	

	@Override
	public final void summary() {
		ConsoleUtils.display("\n> REST \n" 
				+ "\t" + INSTALL_SYMFONY + "\t => Install symfony\n" 
				+ "\t" + INSTALL_BUNDLES + "\t => Install the needed bundles\n" 
				+ "\t" + PROJECT_INIT + "\t => Init symfony project\n" 
				+ "\t" + GENERATE_ENTITIES + "\t => Generate Web Entities\n" 
				+ "\t" + GENERATE_REPOSITORIES 
							+ "\t => Generate Web Repositories");
		
	}

	@Override
	public final boolean isAvailableCommand(final String command) {
		return  command.equals(PROJECT_INIT) 
				|| command.equals(INSTALL_BUNDLES) 
				|| command.equals(INSTALL_SYMFONY) 
				|| command.equals(GENERATE_ENTITIES) 
				|| command.equals(GENERATE_REPOSITORIES);
	}

}
