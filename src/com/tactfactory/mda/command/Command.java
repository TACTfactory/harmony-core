/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import net.xeoh.plugins.base.Plugin;


public interface Command extends Plugin {
	/** Execute specific action
	 * 
	 * @param action to realize
	 * @param args action arguments
	 * @param option of console
	 */
	public void execute(String action, String[] args, String option);

	/**
	 * Display Bundle commands summary
	 */
	public void summary();
	
	/** Check availability of command
	 * 
	 * @param command to check
	 * @return true if found
	 */
	public boolean isAvailableCommand(String command);
}
