/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.test.orm;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.test.CommonTest;

public class OrmInitEntitiesTest extends CommonTest {
	static ClassMetadata userMeta = null, postMeta = null, commentMeta = null;
	
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
		OrmCommand command = (OrmCommand) Harmony.instance.bootstrap.get(OrmCommand.class);
		
		
		ArrayList<ClassMetadata> metas = command.getMetasFromAll();
		for (ClassMetadata classMetadata : metas) {
			if (classMetadata.name.equals("User"))
				userMeta = classMetadata;
			
			if (classMetadata.name.equals("Post"))
				postMeta = classMetadata;
			
			if (classMetadata.name.equals("Comment"))
				commentMeta = classMetadata;
		}
		
		// Check Model Decoration
		// User
		this.hasUserEntity();
		this.hasUserImplement();
		this.hasUserImport();
		this.hasUserId();
		this.hasUserColumn();
		
		// Post
		this.hasPostEntity();
		this.hasPostImplement();
		this.hasPostImport();
		this.hasPostId();
		this.hasPostColumn();
		
		// Comment
		this.hasCommentEntity();
		this.hasCommentImplement();
		this.hasCommentImport();
		this.hasCommentId();
		this.hasCommentColumn();
		
		
		this.hasUserRepository();
		this.hasPostRepository();
		this.hasCommentRepository();
		
		/* For Yoan
		this.testRepoPost();
		this.testRepoComment();
		this.testRepoUser();
		*/
	}

	
	//// POST ////
	@Test
	public void hasPostEntity() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/Post.java");
		Assert.assertNotNull("Post no parsed !", postMeta);
	}
	
	@Test
	public void hasPostImplement() {
		this.hasImplement(postMeta, "Serializable");
	}
	
	@Test
	public void hasPostImport() {
		this.hasImport(postMeta, "Serializable");
		this.hasImport(postMeta, "ArrayList");
		this.hasImport(postMeta, "Date");
		this.hasImport(postMeta, "DateTime");
		this.hasImport(postMeta, "annotation");
	}
	
	@Test
	public void hasPostId() {
		this.hasId(postMeta, "id");
	}
	
	@Test
	public void hasPostColumn() {
		this.hasColumn(postMeta, "id");
		this.hasColumn(postMeta, "title");
		this.hasColumn(postMeta, "content");
		this.hasColumn(postMeta, "owner");
		this.hasColumn(postMeta, "comments");
		this.hasColumn(postMeta, "createdAt");
		this.hasColumn(postMeta, "updatedAt");
		this.hasColumn(postMeta, "expiresAt");
	}
	
	
	//// COMMENT ////
	@Test
	public void hasCommentEntity() {		
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/Comment.java");
		Assert.assertNotNull("Comment no parsed !", commentMeta);
	}
	
	@Test
	public void hasCommentImplement() {
		this.hasImplement(commentMeta, "Serializable");
		this.hasExtend(commentMeta, "Object");
	}
	
	@Test
	public void hasCommentImport() {
		this.hasImport(commentMeta, "Serializable");
		this.hasImport(commentMeta, "Date");
		this.hasImport(commentMeta, "DateTime");
		this.hasImport(commentMeta, "annotation");
	}
	
	@Test
	public void hasCommentId() {
		this.hasId(commentMeta, "id");
	}
	
	@Test
	public void hasCommentColumn() {
		this.hasColumn(commentMeta, "id");
		this.hasColumn(commentMeta, "content");
		this.hasColumn(commentMeta, "owner");
		this.hasColumn(commentMeta, "post");
		this.hasColumn(commentMeta, "createdAt");
	}
	
	
	//// USER ////
	@Test
	public void hasUserEntity() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/User.java");
		Assert.assertNotNull("User no parsed !", userMeta);
	}
	
	@Test
	public void hasUserImplement() {
		this.hasImplement(userMeta, "Serializable");
	}
	
	@Test
	public void hasUserImport() {
		this.hasImport(userMeta, "Serializable");
		this.hasImport(userMeta, "Date");
		this.hasImport(userMeta, "annotation");
		this.hasImport(userMeta, "Type");
	}
	
	@Test
	public void hasUserId() {
		this.hasId(userMeta, "id");
	}
	
	@Test
	public void hasUserColumn() {
		this.hasColumn(userMeta, "id");
		this.hasColumn(userMeta, "login");
		this.hasColumn(userMeta, "password");
		this.hasColumn(userMeta, "firstname");
		this.hasColumn(userMeta, "lastname");
		this.hasColumn(userMeta, "createdAt");
	}
	
	
	//// REPOSITORY POST ////
	@Test
	public void hasPostRepository() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostAdapterBase.java");
	}
	
	
	//// REPOSITORY COMMENT ////
	@Test
	public void hasCommentRepository() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentAdapterBase.java");
	}
	
	
	//// REPOSITORY USER ////
	@Test
	public void hasUserRepository() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserAdapterBase.java");
	}
	
	
	//// TEST REPOSITORY POST ////
	//TODO Yoan
	
	
	//// TEST REPOSITORY COMMENT ////
	//TODO Yoan
	
	
	//// TEST REPOSITORY USER ////
	//TODO Yoan
	
	
	//// INTERNAL ////
	/**
	 * @param userMeta
	 */
	private void hasColumn(ClassMetadata userMeta, String value) {
		Assert.assertTrue("Check if column " + value, userMeta.fields.containsKey(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasId(ClassMetadata userMeta, String value) {
		Assert.assertTrue("Check if key " + value, userMeta.ids.containsKey(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasImport(ClassMetadata userMeta, String value) {
		Assert.assertTrue("Check if import " + value, userMeta.imports.contains(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasImplement(ClassMetadata userMeta, String value) {
		Assert.assertTrue("Check if implement " + value, userMeta.impls.contains(value));
	}
	
	/**
	 * @param userMeta
	 */
	private void hasExtend(ClassMetadata userMeta, String value) {
		Assert.assertTrue("Check if extend " + value, userMeta.exts.contains(value));
	}
}
