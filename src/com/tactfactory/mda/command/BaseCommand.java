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

/** 
 * Common Command structure 
 */
public abstract class BaseCommand implements Command {
	protected static final String SEPARATOR = ":";
	
	protected HashMap<String,String> commandArgs;
}