package com.tactfactory.mda.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	com.tactfactory.mda.test.project.AllTests.class, 
	com.tactfactory.mda.test.orm.AllTests.class })
public class AllTests {

}
