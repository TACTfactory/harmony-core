/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * All Harmony tests.
 */
@RunWith(Suite.class)
@SuiteClasses({
	com.tactfactory.harmony.test.CoreTest.class,
	com.tactfactory.harmony.test.bundles.AllTests.class,
	com.tactfactory.harmony.test.project.AllTests.class,
	com.tactfactory.harmony.test.orm.AllTests.class,
	com.tactfactory.harmony.test.fixture.AllTests.class })
public class AllTests {

}
