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
import org.junit.Test;

import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.test.CommonTest;

public class OrmInitViewTest extends CommonTest {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.initAllTests();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void initAllTests() {
		System.out.println("\nTest Orm generate View");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		this.makeEntities();
		this.harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		this.harmony.findAndExecute(OrmCommand.GENERATE_CRUD, new String[]{}, null);
	}
	
	//@Test
	public void all() {
		this.initAllTests();
		
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
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostCreateActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostCreateFragment.java");
	}
	
	@Test
	public void viewPostEdit() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostEditActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostEditFragment.java");
	}
	
	@Test
	public void viewPostList() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListFragment.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListLoader.java");
	}
	
	@Test
	public void viewPostShow() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostShowActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostShowFragment.java");
	}
	
	@Test
	public void viewCommentCreate() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentCreateActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentCreateFragment.java");
	}
	
	@Test
	public void viewCommentEdit() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentEditActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentEditFragment.java");
	}
	
	@Test
	public void viewCommentList() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListFragment.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListLoader.java");
	}
	
	@Test
	public void viewCommentShow() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentShowActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentShowFragment.java");
	}
	
	@Test
	public void viewUserCreate() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserCreateActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserCreateFragment.java");
	}
	
	@Test
	public void viewUserEdit() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserEditActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserEditFragment.java");
	}
	
	@Test
	public void viewUserList() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListFragment.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListLoader.java");
	}
	
	@Test
	public void viewUserShow() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserShowActivity.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserShowFragment.java");
	}
	
	@Test
	public void ressourcePostCreate() {
		this.hasFindFile("android/res/layout/activity_post_create.xml");
		this.hasFindFile("android/res/layout/fragment_post_create.xml");
	}
	
	@Test
	public void ressourcePostEdit() {
		this.hasFindFile("android/res/layout/activity_post_edit.xml");
		this.hasFindFile("android/res/layout/fragment_post_edit.xml");
	}
	
	@Test
	public void ressourcePostList() {
		this.hasFindFile("android/res/layout/activity_post_list.xml");
		this.hasFindFile("android/res/layout/fragment_post_list.xml");
		this.hasFindFile("android/res/layout/row_post.xml");
	}
	
	@Test
	public void ressourcePostShow() {
		this.hasFindFile("android/res/layout/activity_post_show.xml");
		this.hasFindFile("android/res/layout/fragment_post_show.xml");
	}
	
	@Test
	public void ressourceCommentCreate() {
		this.hasFindFile("android/res/layout/activity_comment_create.xml");
		this.hasFindFile("android/res/layout/fragment_comment_create.xml");
	}
	
	@Test
	public void ressourceCommentEdit() {
		this.hasFindFile("android/res/layout/activity_comment_edit.xml");
		this.hasFindFile("android/res/layout/fragment_comment_edit.xml");
	}
	
	@Test
	public void ressourceCommentList() {
		this.hasFindFile("android/res/layout/activity_comment_list.xml");
		this.hasFindFile("android/res/layout/fragment_comment_list.xml");
		this.hasFindFile("android/res/layout/row_comment.xml");
	}
	
	@Test
	public void ressourceCommentShow() {
		this.hasFindFile("android/res/layout/activity_comment_show.xml");
		this.hasFindFile("android/res/layout/fragment_comment_show.xml");
	}
	
	@Test
	public void ressourceUserCreate() {
		this.hasFindFile("android/res/layout/activity_user_create.xml");
		this.hasFindFile("android/res/layout/fragment_user_create.xml");
	}
	
	@Test
	public void ressourceUserEdit() {
		this.hasFindFile("android/res/layout/activity_user_edit.xml");
		this.hasFindFile("android/res/layout/fragment_user_edit.xml");
	}
	
	@Test
	public void ressourceUserList() {
		this.hasFindFile("android/res/layout/activity_user_list.xml");
		this.hasFindFile("android/res/layout/fragment_user_list.xml");
		this.hasFindFile("android/res/layout/row_user.xml");
	}
	
	@Test
	public void ressourceUserShow() {
		this.hasFindFile("android/res/layout/activity_user_show.xml");
		this.hasFindFile("android/res/layout/fragment_user_show.xml");
	}
}
