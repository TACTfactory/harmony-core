/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.orm;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.FileUtils;

public class OrmInitTest extends CommonTest {
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
		System.out.println("Test Orm all");
		System.out.println("###############################################################################");
		
		this.harmony.findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		
		String pathNameSpace = Harmony.projectNameSpace.replaceAll("\\.", "/");
		String srcDir = String.format("src/%s/%s/", pathNameSpace, "entity");
		String destDir = String.format("%s/android/src/%s/%s/", Harmony.pathProject, pathNameSpace, "entity");
		System.out.println(destDir);
		

		FileUtils.makeFolderRecursive(srcDir, destDir, true);
		if(new File(destDir+"Post.java").exists())
			System.out.println("yeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees!");
		
		this.harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		this.harmony.findAndExecute(OrmCommand.GENERATE_CRUD, new String[]{}, null);
		
		this.modelPost();
		this.modelComment();
		this.modelUser();
		
		this.dataPost();
		this.dataComment();
		this.dataUser();
		
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
	public void modelPost() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/entity/Post.java");
	}
	
	@Test
	public void modelComment() {		
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/entity/Comment.java");
	}
	
	@Test
	public void modelUser() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/entity/User.java");
	}
	
	@Test
	public void dataPost() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/data/PostAdapter.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/data/PostAdapterBase.java");
	}
	
	@Test
	public void dataComment() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentAdapter.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentAdapterBase.java");
	}
	
	@Test
	public void dataUser() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/data/UserAdapter.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/data/UserAdapterBase.java");
	}
	
	@Test
	public void viewPostCreate() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostCreateActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostCreateFragment.java");
	}
	
	@Test
	public void viewPostEdit() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostEditActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostEditFragment.java");
	}
	
	@Test
	public void viewPostList() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListFragment.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListAdapter.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostListLoader.java");
	}
	
	@Test
	public void viewPostShow() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostShowActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/post/PostShowFragment.java");
	}
	
	@Test
	public void viewCommentCreate() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentCreateActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentCreateFragment.java");
	}
	
	@Test
	public void viewCommentEdit() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentEditActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentEditFragment.java");
	}
	
	@Test
	public void viewCommentList() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListFragment.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListAdapter.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentListLoader.java");
	}
	
	@Test
	public void viewCommentShow() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentShowActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/comment/CommentShowFragment.java");
	}
	
	@Test
	public void viewUserCreate() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserCreateActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserCreateFragment.java");
	}
	
	@Test
	public void viewUserEdit() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserEditActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserEditFragment.java");
	}
	
	@Test
	public void viewUserList() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListFragment.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListAdapter.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserListLoader.java");
	}
	
	@Test
	public void viewUserShow() {
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserShowActivity.java");
		this.isFindFile("android/src/com/tactfactory/mda/test/demact/view/user/UserShowFragment.java");
	}
	
	@Test
	public void ressourcePostCreate() {
		this.isFindFile("android/res/layout/activity_post_create.xml");
		this.isFindFile("android/res/layout/fragment_post_create.xml");
	}
	
	@Test
	public void ressourcePostEdit() {
		this.isFindFile("android/res/layout/activity_post_edit.xml");
		this.isFindFile("android/res/layout/fragment_post_edit.xml");
	}
	
	@Test
	public void ressourcePostList() {
		this.isFindFile("android/res/layout/activity_post_list.xml");
		this.isFindFile("android/res/layout/fragment_post_list.xml");
		this.isFindFile("android/res/layout/row_post.xml");
	}
	
	@Test
	public void ressourcePostShow() {
		this.isFindFile("android/res/layout/activity_post_show.xml");
		this.isFindFile("android/res/layout/fragment_post_show.xml");
	}
	
	@Test
	public void ressourceCommentCreate() {
		this.isFindFile("android/res/layout/activity_comment_create.xml");
		this.isFindFile("android/res/layout/fragment_comment_create.xml");
	}
	
	@Test
	public void ressourceCommentEdit() {
		this.isFindFile("android/res/layout/activity_comment_edit.xml");
		this.isFindFile("android/res/layout/fragment_comment_edit.xml");
	}
	
	@Test
	public void ressourceCommentList() {
		this.isFindFile("android/res/layout/activity_comment_list.xml");
		this.isFindFile("android/res/layout/fragment_comment_list.xml");
		this.isFindFile("android/res/layout/row_comment.xml");
	}
	
	@Test
	public void ressourceCommentShow() {
		this.isFindFile("android/res/layout/activity_comment_show.xml");
		this.isFindFile("android/res/layout/fragment_comment_show.xml");
	}
	
	@Test
	public void ressourceUserCreate() {
		this.isFindFile("android/res/layout/activity_user_create.xml");
		this.isFindFile("android/res/layout/fragment_user_create.xml");
	}
	
	@Test
	public void ressourceUserEdit() {
		this.isFindFile("android/res/layout/activity_user_edit.xml");
		this.isFindFile("android/res/layout/fragment_user_edit.xml");
	}
	
	@Test
	public void ressourceUserList() {
		this.isFindFile("android/res/layout/activity_user_list.xml");
		this.isFindFile("android/res/layout/fragment_user_list.xml");
		this.isFindFile("android/res/layout/row_user.xml");
	}
	
	@Test
	public void ressourceUserShow() {
		this.isFindFile("android/res/layout/activity_user_show.xml");
		this.isFindFile("android/res/layout/fragment_user_show.xml");
	}
}
