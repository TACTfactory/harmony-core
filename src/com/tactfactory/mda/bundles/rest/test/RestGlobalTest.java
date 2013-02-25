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

/**
 * Tests for Rest bundle generation.
 */
public class RestGlobalTest extends CommonTest {
	/** Data path. */
	private static final String DATA_PATH = 
			"android/src/com/tactfactory/mda/test/demact/data/";
	
	/** Post entity name. */
	private static final String POST = "Post";
	/** Comment entity name. */
	private static final String COMMENT = "Comment";
	/** User entity name. */
	private static final String USER = "User";
	/** Rest bundle name. */
	private static final String REST = "rest";
	/**
	 * @throws java.lang.Exception 
	 */
	@BeforeClass
	public static void setUpBefore() throws Exception {
		CommonTest.setUpBefore();
		initAll();
	}
	

	@Before
	@Override
	public final void setUp() throws Exception {
		super.setUp();
	}


	@After
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
	}
	
	/**
	 * Test initialization.
	 */
	private static void initAll() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("########################################"
				+ "#######################################");
		
		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_ENTITIES, new String[] {}, null);
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_CRUD, new String[] {}, null);
		getHarmony().findAndExecute(
				RestCommand.GENERATE_ADAPTERS, new String[] {}, null);
				
		final RestCommand command = 
				(RestCommand) Harmony.getInstance().getCommand(
						RestCommand.class);
		command.generateMetas();	
	}
	
	//@Test
	/**
	 * Launch all tests.
	 */
	public final void all() {		
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
	
	/**
	 * Tests if global web service adapter has been generated.
	 */
	@Test
	public final void hasGlobalAbstractWebServiceAdapters() {
		CommonTest.hasFindFile(
				DATA_PATH + "base/WebServiceClientAdapterBase.java");
	}
	
	////WEB SERVICE ADAPTERS POST ////
	/**
	 * Tests if post web service adapter has been generated.
	 */
	@Test
	public final void hasPostWebServiceAdapters() {
		CommonTest.hasFindFile(
				DATA_PATH + "PostWebServiceClientAdapter.java");
		CommonTest.hasFindFile(
				DATA_PATH + "base/PostWebServiceClientAdapterBase.java");
	}
	
	////WEB SERVICE ADAPTERS USER ////
	/**
	 * Tests if user web service adapter has been generated.
	 */
	@Test
	public final void hasUserWebServiceAdapters() {
		CommonTest.hasFindFile(
				DATA_PATH + "UserWebServiceClientAdapter.java");
		CommonTest.hasFindFile(
				DATA_PATH + "base/UserWebServiceClientAdapterBase.java");
	}
	
	/**
	 * Tests if user is rest enabled.
	 */
	@Test
	public final void isUserRest() {
		this.isRest(ApplicationMetadata.INSTANCE.getEntities().get(USER));
	}
	
	/**
	 * Tests if comment is rest enabled.
	 */
	@Test
	public final void isCommentRest() {
		this.isRest(ApplicationMetadata.INSTANCE.getEntities().get(COMMENT));
	}
	
	/**
	 * Tests if post is rest enabled.
	 */
	@Test
	public final void isPostRest() {
		this.isRest(ApplicationMetadata.INSTANCE.getEntities().get(POST));
	}
	
	/**
	 * Tests if post URI is "Post".
	 */
	@Test
	public final void hasPostUri() {
		this.hasUri(ApplicationMetadata.INSTANCE.getEntities().get(POST), POST);
	}
	
	/**
	 * Tests if user URI is "user-uri".
	 */
	@Test
	public final void hasUserUri() {
		this.hasUri(ApplicationMetadata.INSTANCE.getEntities().get(USER), 
				"user-uri");
	}
	
	/**
	 * Tests if post security is None.
	 */
	@Test
	public final void hasPostSecurity() {
		this.hasSecurity(ApplicationMetadata.INSTANCE.getEntities().get(POST),
				Rest.Security.NONE);
	}
	
	/**
	 * Tests if user security is Session.
	 */
	@Test
	public final void hasUserSecurity() {
		this.hasSecurity(ApplicationMetadata.INSTANCE.getEntities().get(USER),
				Rest.Security.SESSION);
	}
	
	/**
	 * Tests if given entity is rest enabled.
	 * @param cm The given entity
	 */
	private void isRest(final ClassMetadata cm) {
		Assert.assertTrue("Check if rest " + cm.getName(),
				cm.getOptions().containsKey(REST));
	}
	
	/**
	 * Tests if given entity has the given uri.
	 * @param cm The given entity
	 * @param value The given uri
	 */
	private void hasUri(final ClassMetadata cm, final String value) {
		Assert.assertTrue("Check if URI of " + cm.getName() + " is " + value, 
				((RestMetadata) cm.getOptions().get(REST)).getUri()
					.equals(value));
	}
	
	/**
	 * Tests if given entity has the given security.
	 * @param cm The given entity
	 * @param value The given security
	 */
	private void hasSecurity(final ClassMetadata cm,
			final Rest.Security value) {
		Assert.assertTrue(
				"Check if SECURITY of " 
						+ cm.getName() 
						+ " is " 
						+ value.getValue(), 
				((RestMetadata) cm.getOptions().get(REST))
					.getSecurity().equals(value));
	}
	
}
