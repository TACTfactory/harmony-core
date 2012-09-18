/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test;

import com.tactfactory.mda.Harmony;

/**
 *
 */
public abstract class CommonTest {
	protected Harmony harmony;

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		// Base configs
		Harmony.projectName = "testApp";
		Harmony.projectNameSpace = "com.tactfactory.mda.test.demact";
		Harmony.androidSdkPath = "/tmp/";
		Harmony.pathProject = "app/";
		Harmony.pathTemplate = "tpl/";
		
		this.harmony = new Harmony();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}
}
