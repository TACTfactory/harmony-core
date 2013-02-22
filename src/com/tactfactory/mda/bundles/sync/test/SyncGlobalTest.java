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

/**
 * Tests for Sync bundle.
 * @author gregg
 *
 */
public class SyncGlobalTest extends CommonTest {
	/** Service path. */
	private static final String SERVICE_PATH = 
			"android/src/com/tactfactory/mda/test/demact/service/";
	
	/** Post entity name. */
	private static final String POST = "Post";
	/** Comment entity name. */
	private static final String COMMENT = "Comment";
	/** User entity name. */
	private static final String USER = "User";
	/** Sync bundle name. */
	private static final String SYNC = "sync";
	
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
	 * Initialize all the tests.
	 */
	private static void initAll() {
		System.out.println("\nTest Orm generate entity");
		System.out.println("######################################" 
				+ "#########################################");
		
		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_ENTITIES, new String[] {}, null);
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_CRUD, new String[] {}, null);
		getHarmony().findAndExecute(
				SyncCommand.GENERATE_SERVICE, new String[] {}, null);
		
		final SyncCommand command =
				(SyncCommand) Harmony.getInstance().getCommand(
						SyncCommand.class);
		command.generateMetas();
	}
	
	/**
	 * All tests.
	 */
	@Test
	@Ignore
	public final void all() {		
		this.isCommentSync();
		this.isPostSync();
		this.isUserSync();
		
		this.hasCommentSyncParameters();
		this.hasPostSyncParameters();
		this.hasUserSyncParameters();
	}
	
	/**
	 * Test service files existence.
	 */
	@Test
	@Ignore
	public final void hasGlobalService() {
		CommonTest.hasFindFile(SERVICE_PATH + "IDemactSyncListener.java");
		CommonTest.hasFindFile(SERVICE_PATH + "IDemactSyncService.java");
		CommonTest.hasFindFile(SERVICE_PATH + "DemactSyncBinder.java");
	}
	
	/**
	 * Tests sync metadata for User.
	 */
	@Test
	public final void isUserSync() {
		this.isSync(ApplicationMetadata.INSTANCE.entities.get(USER));
	}
	
	/**
	 * Tests sync metadata for Comment.
	 */
	@Test
	public final void isCommentSync() {
		this.isSync(ApplicationMetadata.INSTANCE.entities.get(COMMENT));
	}
	
	/**
	 * Tests sync metadata for Post.
	 */
	@Test
	public final void isPostSync() {
		this.isSync(ApplicationMetadata.INSTANCE.entities.get(POST));
	}
	
	/**
	 * Tests sync parameters for Post.
	 */
	@Test
	public final void hasPostSyncParameters() {
		this.hasLevel(ApplicationMetadata.INSTANCE.entities.get(POST),
				Level.GLOBAL);
		this.hasMode(ApplicationMetadata.INSTANCE.entities.get(POST), 
				Mode.POOLING);
		this.hasPriority(ApplicationMetadata.INSTANCE.entities.get(POST), 1);
	}
	
	/**
	 * Tests sync parameters for User.
	 */
	@Test
	public final void hasUserSyncParameters() {
		this.hasLevel(ApplicationMetadata.INSTANCE.entities.get(USER),
				Level.GLOBAL);
		this.hasMode(ApplicationMetadata.INSTANCE.entities.get(USER), 
				Mode.REAL_TIME);
		this.hasPriority(ApplicationMetadata.INSTANCE.entities.get(USER), 1);
	}
	
	/**
	 * Tests sync parameters for Comment.
	 */
	@Test
	public final void hasCommentSyncParameters() {
		this.hasLevel(ApplicationMetadata.INSTANCE.entities.get(COMMENT), 
				Level.SESSION);
		this.hasMode(ApplicationMetadata.INSTANCE.entities.get(COMMENT), 
				Mode.REAL_TIME);
		this.hasPriority(ApplicationMetadata.INSTANCE.entities.get(COMMENT), 
				Priority.LOW);
	}
	
	/**
	 * Tests if class is sync.
	 * @param cm Class to test;
	 */
	private void isSync(final ClassMetadata cm) {
		Assert.assertTrue(
				"Check if sync " + cm.name, cm.options.containsKey(SYNC));
	}
	
	/**
	 * Tests if class has sync mode.
	 * @param cm Class to test
	 * @param value The wanted mode.
	 */
	private void hasMode(final ClassMetadata cm, final Sync.Mode value) {
		Assert.assertTrue(
				"Check if Mode of " + cm.name + " is " + value.getValue(), 
				((SyncMetadata) cm.options.get(SYNC)).mode.equals(value));
	}
	
	/**
	 * Tests if class has sync level.
	 * @param cm Class to test
	 * @param value The wanted leve.
	 */
	private void hasLevel(final ClassMetadata cm, final Sync.Level value) {
		Assert.assertTrue(
				"Check if Level of " + cm.name + " is " + value.getValue(),
				((SyncMetadata) cm.options.get(SYNC)).level.equals(value));
	}
	
	/**
	 * Tests if class has sync priority.
	 * @param cm Class to test
	 * @param value The wanted priority.
	 */
	private void hasPriority(final ClassMetadata cm, final int value) {
		Assert.assertTrue(
				"Check if Priority of " + cm.name + " is " + value,
				((SyncMetadata) cm.options.get(SYNC)).priority == value);
	}
	
}
