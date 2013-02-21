/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.orm;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.test.CommonTest;

public class OrmInitViewTest extends CommonTest {
	private static final String VIEW_PATH = 
			"android/src/com/tactfactory/mda/test/demact/view/";
	
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
	public final void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public final void tearDown() throws Exception {
		super.tearDown();
	}
	
	private static void initAll() {
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
		getHarmony().findAndExecute(OrmCommand.GENERATE_CRUD, new String[] {}, null);
	}
	
	//@Test
	public final void all() {
		this.viewPostCreate();
		this.viewPostEdit();
		this.viewPostList();
		this.viewPostShow();
		
		this.viewCommentCreate();
		this.viewCommentEdit();
		this.viewCommentList();
		this.viewCommentShow();
		
		this.viewUserCreate();
		this.viewUserEdit();
		this.viewUserList();
		this.viewUserShow();
		
		this.ressourcePostCreate();
		this.ressourcePostEdit();
		this.ressourcePostList();
		this.ressourcePostShow();
		
		this.ressourceCommentCreate();
		this.ressourceCommentEdit();
		this.ressourceCommentList();
		this.ressourceCommentShow();
		
		this.ressourceUserCreate();
		this.ressourceUserEdit();
		this.ressourceUserList();
		this.ressourceUserShow();
	}
	
	@Test
	public final void viewPostCreate() {
		CommonTest.hasFindFile(VIEW_PATH + "post/PostCreateActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "post/PostCreateFragment.java");
	}
	
	@Test
	public final void viewPostEdit() {
		CommonTest.hasFindFile(VIEW_PATH + "post/PostEditActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "post/PostEditFragment.java");
	}
	
	@Test
	public final void viewPostList() {
		CommonTest.hasFindFile(VIEW_PATH + "post/PostListActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "post/PostListFragment.java");
		CommonTest.hasFindFile(VIEW_PATH + "post/PostListAdapter.java");
		CommonTest.hasFindFile(VIEW_PATH + "post/PostListLoader.java");
	}
	
	@Test
	public final void viewPostShow() {
		CommonTest.hasFindFile(VIEW_PATH + "post/PostShowActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "post/PostShowFragment.java");
	}
	
	@Test
	public final void viewCommentCreate() {
		CommonTest.hasFindFile(
				VIEW_PATH + "comment/CommentCreateActivity.java");
		CommonTest.hasFindFile(
				VIEW_PATH + "comment/CommentCreateFragment.java");
	}
	
	@Test
	public final void viewCommentEdit() {
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentEditActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentEditFragment.java");
	}
	
	@Test
	public final void viewCommentList() {
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentListActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentListFragment.java");
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentListAdapter.java");
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentListLoader.java");
	}
	
	@Test
	public final void viewCommentShow() {
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentShowActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "comment/CommentShowFragment.java");
	}
	
	@Test
	public final void viewUserCreate() {
		CommonTest.hasFindFile(VIEW_PATH + "user/UserCreateActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "user/UserCreateFragment.java");
	}
	
	@Test
	public final void viewUserEdit() {
		CommonTest.hasFindFile(VIEW_PATH + "user/UserEditActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "user/UserEditFragment.java");
	}
	
	@Test
	public final void viewUserList() {
		CommonTest.hasFindFile(VIEW_PATH + "user/UserListActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "user/UserListFragment.java");
		CommonTest.hasFindFile(VIEW_PATH + "user/UserListAdapter.java");
		CommonTest.hasFindFile(VIEW_PATH + "user/UserListLoader.java");
	}
	
	@Test
	public final void viewUserShow() {
		CommonTest.hasFindFile(VIEW_PATH + "user/UserShowActivity.java");
		CommonTest.hasFindFile(VIEW_PATH + "user/UserShowFragment.java");
	}
	
	@Test
	public final void ressourcePostCreate() {
		CommonTest.hasFindFile("android/res/layout/activity_post_create.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_create.xml");
	}
	
	@Test
	public final void ressourcePostEdit() {
		CommonTest.hasFindFile("android/res/layout/activity_post_edit.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_edit.xml");
	}
	
	@Test
	public final void ressourcePostList() {
		CommonTest.hasFindFile("android/res/layout/activity_post_list.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_list.xml");
		CommonTest.hasFindFile("android/res/layout/row_post.xml");
	}
	
	@Test
	public final void ressourcePostShow() {
		CommonTest.hasFindFile("android/res/layout/activity_post_show.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_show.xml");
	}
	
	@Test
	public final void ressourceCommentCreate() {
		CommonTest.hasFindFile(
				"android/res/layout/activity_comment_create.xml");
		CommonTest.hasFindFile(
				"android/res/layout/fragment_comment_create.xml");
	}
	
	@Test
	public final void ressourceCommentEdit() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_edit.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_edit.xml");
	}
	
	@Test
	public final void ressourceCommentList() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_list.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_list.xml");
		CommonTest.hasFindFile("android/res/layout/row_comment.xml");
	}
	
	@Test
	public final void ressourceCommentShow() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_show.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_show.xml");
	}
	
	@Test
	public final void ressourceUserCreate() {
		CommonTest.hasFindFile("android/res/layout/activity_user_create.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_create.xml");
	}
	
	@Test
	public final void ressourceUserEdit() {
		CommonTest.hasFindFile("android/res/layout/activity_user_edit.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_edit.xml");
	}
	
	@Test
	public final void ressourceUserList() {
		CommonTest.hasFindFile("android/res/layout/activity_user_list.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_list.xml");
		CommonTest.hasFindFile("android/res/layout/row_user.xml");
	}
	
	@Test
	public final void ressourceUserShow() {
		CommonTest.hasFindFile("android/res/layout/activity_user_show.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_show.xml");
	}
}
