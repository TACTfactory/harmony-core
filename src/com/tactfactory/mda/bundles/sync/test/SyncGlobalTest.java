/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.bundles.sync.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.bundles.sync.annotation.Sync;
import com.tactfactory.mda.bundles.sync.annotation.Sync.Level;
import com.tactfactory.mda.bundles.sync.annotation.Sync.Mode;
import com.tactfactory.mda.bundles.sync.annotation.Sync.Priority;
import com.tactfactory.mda.bundles.sync.command.SyncCommand;
import com.tactfactory.mda.bundles.sync.meta.SyncMetadata;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.test.CommonTest;

public class SyncGlobalTest extends CommonTest {
	private static final String POST = "Post";
	private static final String COMMENT = "Comment";
	private static final String USER = "User";
	private static final String SYNC = "sync";
	
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
		harmony.findAndExecute(SyncCommand.GENERATE_SERVICE, new String[] {}, null);
		
		final SyncCommand command = (SyncCommand) Harmony.instance.getCommand(SyncCommand.class);
		command.generateMetas();
	}
	
	@Test
	@Ignore
	public void all() {		
		this.isCommentSync();
		this.isPostSync();
		this.isUserSync();
		
		this.hasCommentSyncParameters();
		this.hasPostSyncParameters();
		this.hasUserSyncParameters();
	}
	
	@Test
	@Ignore
	public void hasGlobalService() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/service/IDemactSyncListener.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/service/IDemactSyncService.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/service/DemactSyncBinder.java");
	}
	
	@Test
	public void isUserSync() {
		this.isSync(ApplicationMetadata.INSTANCE.entities.get(USER));
	}
	
	@Test
	public void isCommentSync() {
		this.isSync(ApplicationMetadata.INSTANCE.entities.get(COMMENT));
	}
	
	@Test
	public void isPostSync() {
		this.isSync(ApplicationMetadata.INSTANCE.entities.get(POST));
	}
	
	@Test
	public void hasPostSyncParameters() {
		this.hasLevel(ApplicationMetadata.INSTANCE.entities.get(POST), Level.GLOBAL);
		this.hasMode(ApplicationMetadata.INSTANCE.entities.get(POST), Mode.POOLING);
		this.hasPriority(ApplicationMetadata.INSTANCE.entities.get(POST), 1);
	}
	
	@Test
	public void hasUserSyncParameters() {
		this.hasLevel(ApplicationMetadata.INSTANCE.entities.get(USER), Level.GLOBAL);
		this.hasMode(ApplicationMetadata.INSTANCE.entities.get(USER), Mode.REAL_TIME);
		this.hasPriority(ApplicationMetadata.INSTANCE.entities.get(USER), 1);
	}
	
	@Test
	public void hasCommentSyncParameters() {
		this.hasLevel(ApplicationMetadata.INSTANCE.entities.get(COMMENT), Level.SESSION);
		this.hasMode(ApplicationMetadata.INSTANCE.entities.get(COMMENT), Mode.REAL_TIME);
		this.hasPriority(ApplicationMetadata.INSTANCE.entities.get(COMMENT), Priority.LOW);
	}
	
	private void isSync(final ClassMetadata cm) {
		Assert.assertTrue("Check if sync " + cm.name, cm.options.containsKey(SYNC));
	}
	
	private void hasMode(final ClassMetadata cm, final Sync.Mode value) {
		Assert.assertTrue("Check if Mode of " + cm.name + " is " + value.getValue(), ((SyncMetadata) cm.options.get(SYNC)).mode.equals(value));
	}
	
	private void hasLevel(final ClassMetadata cm, final Sync.Level value) {
		Assert.assertTrue("Check if Level of " + cm.name + " is " + value.getValue(), ((SyncMetadata) cm.options.get(SYNC)).level.equals(value));
	}
	
	private void hasPriority(final ClassMetadata cm, final int value) {
		Assert.assertTrue("Check if Priority of " + cm.name + " is " + value, ((SyncMetadata) cm.options.get(SYNC)).priority == value);
	}
	
}
