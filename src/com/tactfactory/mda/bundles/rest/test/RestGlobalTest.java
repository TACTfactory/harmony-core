package com.tactfactory.mda.bundles.rest.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.bundles.rest.annotation.Rest;
import com.tactfactory.mda.bundles.rest.command.RestCommand;
import com.tactfactory.mda.bundles.rest.meta.RestMetadata;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.test.CommonTest;

public class RestGlobalTest extends CommonTest {
	private static final String POST = "Post";
	private static final String COMMENT = "Comment";
	private static final String USER = "User";
	private static final String REST = "rest";
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
		initAll();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	private static void initAll() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("###############################################################################");
		
		harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[] {}, null);
		harmony.findAndExecute(OrmCommand.GENERATE_CRUD, new String[] {}, null);
		harmony.findAndExecute(RestCommand.GENERATE_ADAPTERS, new String[] {}, null);
				
		final RestCommand command = (RestCommand) Harmony.instance.getCommand(RestCommand.class);
		command.generateMetas();	
	}
	
	//@Test
	public void all() {		
		this.hasGlobalAbstractWebServiceAdapters();
		this.hasPostWebServiceAdapters();
		this.hasUserWebServiceAdapters();
		
		this.isCommentRest();
		
		this.hasPostSecurity();
		this.hasPostUri();
		this.isPostRest();
		
		this.hasUserSecurity();
		this.hasUserUri();
		this.isUserRest();
	}
	
	@Test
	public void hasGlobalAbstractWebServiceAdapters() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/WebServiceClientAdapterBase.java");
	}
	
	////WEB SERVICE ADAPTERS POST ////
	@Test
	public void hasPostWebServiceAdapters() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostWebServiceClientAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/PostWebServiceClientAdapterBase.java");
	}
	
	////WEB SERVICE ADAPTERS USER ////
	@Test
	public void hasUserWebServiceAdapters() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserWebServiceClientAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/UserWebServiceClientAdapterBase.java");
	}
	
	@Test
	public void isUserRest() {
		this.isRest(ApplicationMetadata.INSTANCE.entities.get(USER));
	}
	
	@Test
	public void isCommentRest() {
		this.isRest(ApplicationMetadata.INSTANCE.entities.get(COMMENT));
	}
	
	@Test
	public void isPostRest() {
		this.isRest(ApplicationMetadata.INSTANCE.entities.get(POST));
	}
	
	@Test
	public void hasPostUri() {
		this.hasUri(ApplicationMetadata.INSTANCE.entities.get(POST), POST);
	}
	
	@Test
	public void hasUserUri() {
		this.hasUri(ApplicationMetadata.INSTANCE.entities.get(USER), "user-uri");
	}
	
	@Test
	public void hasPostSecurity() {
		this.hasSecurity(ApplicationMetadata.INSTANCE.entities.get(POST), Rest.Security.NONE);
	}
	
	@Test
	public void hasUserSecurity() {
		this.hasSecurity(ApplicationMetadata.INSTANCE.entities.get(USER), Rest.Security.SESSION);
	}
	
	private void isRest(final ClassMetadata cm) {
		Assert.assertTrue("Check if rest " + cm.name, cm.options.containsKey(REST));
	}
	
	private void hasUri(final ClassMetadata cm, final String value) {
		Assert.assertTrue("Check if URI of " + cm.name + " is " + value, ((RestMetadata)cm.options.get(REST)).uri.equals(value));
	}
	
	private void hasSecurity(final ClassMetadata cm, final Rest.Security value) {
		Assert.assertTrue("Check if SECURITY of " + cm.name + " is " + value.getValue(), ((RestMetadata)cm.options.get(REST)).security.equals(value));
	}
	
}
