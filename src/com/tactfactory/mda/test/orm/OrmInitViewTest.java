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
		System.out.println("\nTest Orm generate View");
		System.out.println("###############################################################################");
		
		harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		harmony.findAndExecute(OrmCommand.GENERATE_CRUD, new String[]{}, null);
	}
	
	//@Test
	public void all() {
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
	public void viewPostCreate() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostCreateActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostCreateFragment.java");
	}
	
	@Test
	public void viewPostEdit() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostEditActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostEditFragment.java");
	}
	
	@Test
	public void viewPostList() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListFragment.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListLoader.java");
	}
	
	@Test
	public void viewPostShow() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostShowActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostShowFragment.java");
	}
	
	@Test
	public void viewCommentCreate() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentCreateActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentCreateFragment.java");
	}
	
	@Test
	public void viewCommentEdit() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentEditActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentEditFragment.java");
	}
	
	@Test
	public void viewCommentList() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListFragment.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListLoader.java");
	}
	
	@Test
	public void viewCommentShow() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentShowActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentShowFragment.java");
	}
	
	@Test
	public void viewUserCreate() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserCreateActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserCreateFragment.java");
	}
	
	@Test
	public void viewUserEdit() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserEditActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserEditFragment.java");
	}
	
	@Test
	public void viewUserList() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListFragment.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListLoader.java");
	}
	
	@Test
	public void viewUserShow() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserShowActivity.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserShowFragment.java");
	}
	
	@Test
	public void ressourcePostCreate() {
		CommonTest.hasFindFile("android/res/layout/activity_post_create.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_create.xml");
	}
	
	@Test
	public void ressourcePostEdit() {
		CommonTest.hasFindFile("android/res/layout/activity_post_edit.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_edit.xml");
	}
	
	@Test
	public void ressourcePostList() {
		CommonTest.hasFindFile("android/res/layout/activity_post_list.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_list.xml");
		CommonTest.hasFindFile("android/res/layout/row_post.xml");
	}
	
	@Test
	public void ressourcePostShow() {
		CommonTest.hasFindFile("android/res/layout/activity_post_show.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_post_show.xml");
	}
	
	@Test
	public void ressourceCommentCreate() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_create.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_create.xml");
	}
	
	@Test
	public void ressourceCommentEdit() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_edit.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_edit.xml");
	}
	
	@Test
	public void ressourceCommentList() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_list.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_list.xml");
		CommonTest.hasFindFile("android/res/layout/row_comment.xml");
	}
	
	@Test
	public void ressourceCommentShow() {
		CommonTest.hasFindFile("android/res/layout/activity_comment_show.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_comment_show.xml");
	}
	
	@Test
	public void ressourceUserCreate() {
		CommonTest.hasFindFile("android/res/layout/activity_user_create.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_create.xml");
	}
	
	@Test
	public void ressourceUserEdit() {
		CommonTest.hasFindFile("android/res/layout/activity_user_edit.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_edit.xml");
	}
	
	@Test
	public void ressourceUserList() {
		CommonTest.hasFindFile("android/res/layout/activity_user_list.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_list.xml");
		CommonTest.hasFindFile("android/res/layout/row_user.xml");
	}
	
	@Test
	public void ressourceUserShow() {
		CommonTest.hasFindFile("android/res/layout/activity_user_show.xml");
		CommonTest.hasFindFile("android/res/layout/fragment_user_show.xml");
	}
}
