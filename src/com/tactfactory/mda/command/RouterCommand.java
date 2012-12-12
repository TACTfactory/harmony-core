/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import com.tactfactory.mda.ConsoleUtils;

import net.xeoh.plugins.base.annotations.PluginImplementation;

@PluginImplementation
public class RouterCommand extends BaseCommand {

	public final static String BUNDLE = "router";
	public final static String SUBJECT_GENERATE = "generate";
	public final static String SUBJECT_DEBUG = "debug";
	public final static String ACTION_MANIFEST = "manifest";
	
	public final static String ROUTER_DEBUG = BUNDLE + SEPARATOR + SUBJECT_DEBUG;
	public final static String ROUTER_GENERATE_MANIFEST = BUNDLE + SEPARATOR + SUBJECT_GENERATE + SEPARATOR + ACTION_MANIFEST;
	
	protected void routerDebug() {
	}

	public void generateManifest() {
	}

	@Override
	public void summary() {
		ConsoleUtils.display("\n> ROUTER \n" +
				"\t"+ROUTER_DEBUG+"\t\t => List all URI\n" +
				"\t"+ROUTER_GENERATE_MANIFEST+" => Generate Manifest\n");
	}

	@Override
	public void execute(String action, String[] args, String option) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}

}