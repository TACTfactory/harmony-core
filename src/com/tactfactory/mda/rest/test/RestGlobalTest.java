package com.tactfactory.mda.rest.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.rest.annotation.Rest;
import com.tactfactory.mda.rest.command.RestCommand;
import com.tactfactory.mda.rest.meta.RestMetadata;
import com.tactfactory.mda.test.CommonTest;

public class RestGlobalTest extends CommonTest{
	
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
	
	@Test
	public void all() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		this.makeEntities();
		this.harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		this.harmony.findAndExecute(OrmCommand.GENERATE_CRUD, new String[]{}, null);
		this.harmony.findAndExecute(RestCommand.GENERATE_ADAPTERS, new String[]{}, null);
		
		this.hasGlobalAbstractWebServiceAdapters();
		this.hasPostWebServiceAdapters();
		this.hasUserWebServiceAdapters();
		
		RestCommand command = (RestCommand) Harmony.instance.getCommand(RestCommand.class);
		command.generateMetas();
		
		
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
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/WebServiceClientAdapterBase.java");
	}
	
	////WEB SERVICE ADAPTERS POST ////
	@Test
	public void hasPostWebServiceAdapters() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostWebServiceClientAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/PostWebServiceClientAdapterBase.java");
	}
	
	////WEB SERVICE ADAPTERS USER ////
	@Test
	public void hasUserWebServiceAdapters() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserWebServiceClientAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/UserWebServiceClientAdapterBase.java");
	}
	
	@Test
	public void isUserRest(){
		this.isRest(Harmony.metas.entities.get("User"));
	}
	
	@Test
	public void isCommentRest(){
		this.isRest(Harmony.metas.entities.get("Comment"));
	}
	
	@Test
	public void isPostRest(){
		this.isRest(Harmony.metas.entities.get("Post"));
	}
	
	@Test
	public void hasPostUri(){
		this.hasUri(Harmony.metas.entities.get("Post"), "Post");
	}
	
	@Test
	public void hasUserUri(){
		this.hasUri(Harmony.metas.entities.get("User"), "user-uri");
	}
	
	@Test
	public void hasPostSecurity(){
		this.hasSecurity(Harmony.metas.entities.get("Post"), Rest.Security.NONE);
	}
	
	@Test
	public void hasUserSecurity(){
		this.hasSecurity(Harmony.metas.entities.get("User"), Rest.Security.SESSION);
	}
	
	private void isRest(ClassMetadata cm){
		Assert.assertTrue("Check if rest " + cm.name, cm.options.containsKey("rest"));
	}
	
	private void hasUri(ClassMetadata cm, String value){
		Assert.assertTrue("Check if URI of " + cm.name + " is "+value, ((RestMetadata)cm.options.get("rest")).uri.equals(value));
	}
	
	private void hasSecurity(ClassMetadata cm, Rest.Security value){
		Assert.assertTrue("Check if SECURITY of " + cm.name + " is "+value.getValue(), ((RestMetadata)cm.options.get("rest")).security.equals(value));
	}
	
}
