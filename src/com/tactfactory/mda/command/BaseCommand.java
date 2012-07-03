/**
 * This file is part of the Symfodroid package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.command;

import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;

public abstract class BaseCommand {

	public abstract void execute(String action, ArrayList<CompilationUnit> entities);

	public abstract void summary();
	
	public abstract boolean isAvailableCommand(String command);

}