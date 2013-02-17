/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test;

public abstract class FixtureBase {	
	/**
     * Load the fixtures for the current model.
     */
	public abstract void getModelFixtures();

	/**
	 * Load data fixtures
	 */
	public abstract void load(DataManager manager);
	
	public abstract Object getModelFixture(String id);

	/**
	 * Get the order of this fixture
	 * 
	 * @return index order
	 */
	public int getOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
