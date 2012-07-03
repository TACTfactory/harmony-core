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

public class RouterCommand extends BaseCommand {

	protected void routerDebug() {
	}

	public void generateManifest() {
	}

	@Override
	public void summary() {
		System.out.print("\n> ROUTER Bundle\n");
		System.out.print("router:debug\t => List all URI\n");
		System.out.print("router:generate:manifest\t => Generate Manifest\n");
	}

	@Override
	public void execute(String action, ArrayList<CompilationUnit> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAvailableCommand(String command) {
		// TODO Auto-generated method stub
		return false;
	}

}