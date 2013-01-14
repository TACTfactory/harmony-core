package com.tactfactory.mda.sync.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.sync.annotation.Sync;
import com.tactfactory.mda.sync.annotation.Sync.Level;
import com.tactfactory.mda.sync.annotation.Sync.Mode;
import com.tactfactory.mda.sync.annotation.Sync.Priority;
import com.tactfactory.mda.sync.command.SyncCommand;
import com.tactfactory.mda.sync.meta.SyncMetadata;
import com.tactfactory.mda.test.CommonTest;

public class SyncGlobalTest extends CommonTest{
	
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
		this.harmony.findAndExecute(SyncCommand.GENERATE_SERVICE, new String[]{}, null);
		
		this.hasGlobalService();
		
		SyncCommand command = (SyncCommand) Harmony.instance.getCommand(SyncCommand.class);
		command.generateMetas();
		
		
		this.isCommentSync();
		this.isPostSync();
		this.isUserSync();
		
		this.hasCommentSyncParameters();
		this.hasPostSyncParameters();
		this.hasUserSyncParameters();
	}
	
	@Test
	public void hasGlobalService() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/service/IDemactSyncListener.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/service/IDemactSyncService.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/service/DemactSyncBinder.java");
	}
	
	@Test
	public void isUserSync(){
		this.isSync(Harmony.metas.entities.get("User"));
	}
	
	@Test
	public void isCommentSync(){
		this.isSync(Harmony.metas.entities.get("Comment"));
	}
	
	@Test
	public void isPostSync(){
		this.isSync(Harmony.metas.entities.get("Post"));
	}
	
	@Test
	public void hasPostSyncParameters(){
		this.hasLevel(Harmony.metas.entities.get("Post"), Level.GLOBAL);
		this.hasMode(Harmony.metas.entities.get("Post"), Mode.POOLING);
		this.hasPriority(Harmony.metas.entities.get("Post"), 1);
	}
	
	@Test
	public void hasUserSyncParameters(){
		this.hasLevel(Harmony.metas.entities.get("User"), Level.GLOBAL);
		this.hasMode(Harmony.metas.entities.get("User"), Mode.REAL_TIME);
		this.hasPriority(Harmony.metas.entities.get("User"), 1);
	}
	
	@Test
	public void hasCommentSyncParameters(){
		this.hasLevel(Harmony.metas.entities.get("Comment"), Level.SESSION);
		this.hasMode(Harmony.metas.entities.get("Comment"), Mode.REAL_TIME);
		this.hasPriority(Harmony.metas.entities.get("Comment"), Priority.LOW);
	}
	
	private void isSync(ClassMetadata cm){
		Assert.assertTrue("Check if sync " + cm.name, cm.options.containsKey("sync"));
	}
	
	private void isNotSync(ClassMetadata cm){
		Assert.assertFalse("Check if sync " + cm.name, cm.options.containsKey("sync"));
	}
	
	private void hasMode(ClassMetadata cm, Sync.Mode value){
		Assert.assertTrue("Check if Mode of " + cm.name + " is "+value.getValue(), ((SyncMetadata)cm.options.get("sync")).mode.equals(value));
	}
	
	private void hasLevel(ClassMetadata cm, Sync.Level value){
		Assert.assertTrue("Check if Level of " + cm.name + " is "+value.getValue(), ((SyncMetadata)cm.options.get("sync")).level.equals(value));
	}
	
	private void hasPriority(ClassMetadata cm, int value){
		Assert.assertTrue("Check if Priority of " + cm.name + " is "+value, ((SyncMetadata)cm.options.get("sync")).priority == value);
	}
	
}
