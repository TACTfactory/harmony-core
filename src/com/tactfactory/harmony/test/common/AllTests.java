/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All tests related to ORM initialization and generation.
 */
@RunWith(Suite.class)
@SuiteClasses({
	GenerateStaticViewTest.class,
	GenerateConfigFileTest.class })

public class AllTests {
	
}
