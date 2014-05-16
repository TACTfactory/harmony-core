/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.orm;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.test.CommonTest;

@RunWith(Parameterized.class)
/**
 * CRUD Generation tests class.
 *
 */
public class OrmInitViewTest extends CommonTest {
	/** Path to the generated view folder. */
	private final static String VIEW_PATH =
			"android/src/%s/view/";

	private static final String VIEW_PATH_CREATE_ACTIVITY =
			VIEW_PATH + "%s/%sCreateActivity.java";
	private static final String VIEW_PATH_CREATE_FRAGMENT =
			VIEW_PATH + "%s/%sCreateFragment.java";
	private static final String VIEW_PATH_EDIT_ACTIVITY =
			VIEW_PATH + "%s/%sEditActivity.java";
	private static final String VIEW_PATH_EDIT_FRAGMENT =
			VIEW_PATH + "%s/%sEditFragment.java";
	private static final String VIEW_PATH_SHOW_ACTIVITY=
			VIEW_PATH + "%s/%sShowActivity.java";
	private static final String VIEW_PATH_SHOW_FRAGMENT =
			VIEW_PATH + "%s/%sShowFragment.java";
	private static final String VIEW_PATH_LIST_ACTIVITY =
			VIEW_PATH + "%s/%sListActivity.java";
	private static final String VIEW_PATH_LIST_FRAGMENT =
			VIEW_PATH + "%s/%sListFragment.java";
	private static final String VIEW_PATH_LIST_ADAPTER =
			VIEW_PATH + "%s/%sListAdapter.java";
	private static final String VIEW_PATH_LIST_LOADER =
			VIEW_PATH + "%s/%sListLoader.java";

	private static final String LAYOUT_PATH = 
			"android/res/layout/";
	private static final String LAYOUT_PATH_CREATE_ACTIVITY =
			LAYOUT_PATH + "activity_%s_create.xml";
	private static final String LAYOUT_PATH_CREATE_FRAGMENT =
			LAYOUT_PATH + "fragment_%s_create.xml";
	private static final String LAYOUT_PATH_EDIT_ACTIVITY =
			LAYOUT_PATH + "activity_%s_edit.xml";
	private static final String LAYOUT_PATH_EDIT_FRAGMENT =
			LAYOUT_PATH + "fragment_%s_edit.xml";
	private static final String LAYOUT_PATH_SHOW_ACTIVITY =
			LAYOUT_PATH + "activity_%s_show.xml";
	private static final String LAYOUT_PATH_SHOW_FRAGMENT =
			LAYOUT_PATH + "fragment_%s_show.xml";
	private static final String LAYOUT_PATH_LIST_ACTIVITY =
			LAYOUT_PATH + "activity_%s_list.xml";
	private static final String LAYOUT_PATH_LIST_FRAGMENT =
			LAYOUT_PATH + "fragment_%s_list.xml";
	private static final String LAYOUT_PATH_ROW =
			LAYOUT_PATH + "row_%s.xml";

	public OrmInitViewTest(ApplicationMetadata currentMetadata)
	        throws Exception {
		super(currentMetadata);
	}
	
	@Override
	public void setUpBeforeNewParameter() throws Exception {
		super.setUpBeforeNewParameter();
		
		this.initAll();
	}

	@Before
	@Override
	public final void setUp() throws RuntimeException {
		super.setUp();
	}

	@After
	@Override
	public final void tearDown() throws RuntimeException {
		super.tearDown();
	}
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return CommonTest.getParameters();
	}

	/**
	 * Initialize everything needed for the test.
	 */
	private void initAll() {
		System.out.println("\nTest Orm generate View");
		System.out.println("###############"
				 + "##############"
				 + "##############"
				 + "##################################");

		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(OrmCommand.GENERATE_ENTITIES,
				new String[] {},
				null);
		getHarmony().findAndExecute(
				OrmCommand.GENERATE_CRUD, new String[] {}, null);
	}
	
	@Test
	public final void viewEntitiesCreate() {
		for (EntityMetadata entity 
				: this.currentMetadata.getEntities().values()) {
			if (!entity.getFields().isEmpty()) {
				String lowerName = entity.getName().toLowerCase();
				String name = entity.getName();
				if (entity.isCreateAction()) {
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_CREATE_ACTIVITY,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_CREATE_FRAGMENT,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));

					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_CREATE_ACTIVITY,
							entity.getName().toLowerCase()));
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_CREATE_FRAGMENT,
							entity.getName().toLowerCase()));
				}
				
				if (entity.isEditAction()) {
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_EDIT_ACTIVITY,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_EDIT_FRAGMENT,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_EDIT_ACTIVITY,
							entity.getName().toLowerCase()));
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_EDIT_FRAGMENT,
							entity.getName().toLowerCase()));
				}
				
				if (entity.isShowAction()) {
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_SHOW_ACTIVITY,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_SHOW_FRAGMENT,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));

					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_SHOW_ACTIVITY,
							entity.getName().toLowerCase()));
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_SHOW_FRAGMENT,
							entity.getName().toLowerCase()));
				}
				
				if (entity.isListAction()) {
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_LIST_ACTIVITY,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_LIST_FRAGMENT,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_LIST_ADAPTER,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							VIEW_PATH_LIST_LOADER,
							this.currentMetadata.getProjectNameSpace(),
							lowerName,
							name));
					
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_LIST_ACTIVITY,
							entity.getName().toLowerCase()));
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_LIST_FRAGMENT,
							entity.getName().toLowerCase()));
					CommonTest.hasFindFile(String.format(
							LAYOUT_PATH_ROW,
							entity.getName().toLowerCase()));
				}
			}
		}
	}
}
