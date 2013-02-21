/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.tactfactory.mda.utils.ConsoleUtils;

@PluginImplementation
public class RouterCommand extends BaseCommand {

	public static final String BUNDLE = "router";
	public static final String SUBJECT_GENERATE = "generate";
	public static final String SUBJECT_DEBUG = "debug";
	public static final String ACTION_MANIFEST = "manifest";
	
	public static final String ROUTER_DEBUG = BUNDLE + SEPARATOR + SUBJECT_DEBUG;
	public static final String ROUTER_GENERATE_MANIFEST = BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_MANIFEST;
	
	protected void routerDebug() {
	}

	public void generateManifest() {
	}

	@Override
	public void summary() {
		ConsoleUtils.display("\n> ROUTER \n" + 
				"\t" + ROUTER_DEBUG + "\t\t => List all URI\n" + 
				"\t" + ROUTER_GENERATE_MANIFEST + " => Generate Manifest\n");
	}

	@Override
	public void execute(final String action, final String[] args, final String option) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAvailableCommand(final String command) {
		// TODO Auto-generated method stub
		return false;
	}

}
