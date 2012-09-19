/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import java.util.HashMap;

/** Common Command structure */
public abstract class BaseCommand {
	protected HashMap<String,String> commandArgs;
	protected static final String SEPARATOR = ":";

	/** Execute specific action
	 * 
	 * @param action to realize
	 * @param args action arguments
	 * @param option of console
	 */
	public abstract void execute(String action, String[] args, String option);

	/** Help of the bundle */
	public abstract void summary();
	
	/** Check availability of command
	 * 
	 * @param command to check
	 * @return true if found
	 */
	public abstract boolean isAvailableCommand(String command);

}