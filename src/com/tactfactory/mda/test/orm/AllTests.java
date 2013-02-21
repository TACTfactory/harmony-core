/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.orm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests related to ORM initialization and generation
 * @author gregg
 *
 */
@RunWith(Suite.class)
@SuiteClasses( {
	OrmInitEntitiesTest.class,
	OrmInitViewTest.class,
	OrmUpdateEntitiesTest.class })

public class AllTests {
        /*public static void main(String[] args) {
                JUnitCore.runClasses(new Class[] { AllTests.class });
        }*/
}
