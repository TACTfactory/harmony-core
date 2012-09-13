/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda;

/** Harmony console class*/
public class Console extends Harmony {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(Harmony.VERSION + "\n");
		Console csl = new Console();
		csl.initialize();
		
		if(args.length==0) {
			csl.findAndExecute("help");
		}
		else {
			//Execute command given in arguments
			csl.findAndExecute(args);
		}
	}

	/** Constructor */
	public Console() throws Exception {
		
		// Extend bootstrap
		//this.bootstrap.put(key, value)
	}
}
