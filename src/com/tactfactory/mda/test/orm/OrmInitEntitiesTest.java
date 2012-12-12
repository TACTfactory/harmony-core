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
import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.tactfactory.mda.ConsoleUtils;
import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.orm.ClassMetadata;
import com.tactfactory.mda.test.CommonTest;
import com.tactfactory.mda.utils.FileUtils;

public class OrmInitEntitiesTest extends CommonTest {
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
		
		ClassMetadata userMeta = null, postMeta = null, commentMeta = null;
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
		this.modelUser();
		Assert.assertNotNull("User no parsed !", userMeta);
		this.hasImplement(userMeta, "Serializable");
		this.hasImport(userMeta, "Serializable");
		this.hasImport(userMeta, "Date");
		this.hasImport(userMeta, "annotation");
		this.hasImport(userMeta, "Type");
		this.hasId(userMeta, "id");
		this.hasColumn(userMeta, "id");
		this.hasColumn(userMeta, "login");
		this.hasColumn(userMeta, "password");
		this.hasColumn(userMeta, "firstname");
		this.hasColumn(userMeta, "lastname");
		this.hasColumn(userMeta, "createdAt");

		
		// Post
		this.modelPost();
		Assert.assertNotNull("Post no parsed !", postMeta);
		this.hasImplement(postMeta, "Serializable");
		this.hasImport(postMeta, "Serializable");
		this.hasImport(postMeta, "ArrayList");
		this.hasImport(postMeta, "Date");
		this.hasImport(postMeta, "DateTime");
		this.hasImport(postMeta, "annotation");
		this.hasId(postMeta, "id");
		this.hasColumn(postMeta, "id");
		this.hasColumn(postMeta, "title");
		this.hasColumn(postMeta, "content");
		this.hasColumn(postMeta, "owner");
		this.hasColumn(postMeta, "comments");
		this.hasColumn(postMeta, "createdAt");
		this.hasColumn(postMeta, "updatedAt");
		this.hasColumn(postMeta, "expiresAt");
		
		// Comment
		this.modelComment();
		Assert.assertNotNull("Comment no parsed !", commentMeta);
		this.hasImplement(commentMeta, "Serializable");
		this.hasExtend(commentMeta, "Object");
		this.hasImport(commentMeta, "Serializable");
		this.hasImport(commentMeta, "Date");
		this.hasImport(commentMeta, "DateTime");
		this.hasImport(commentMeta, "annotation");
		this.hasId(commentMeta, "id");
		this.hasColumn(commentMeta, "id");
		this.hasColumn(commentMeta, "content");
		this.hasColumn(commentMeta, "owner");
		this.hasColumn(commentMeta, "post");
		this.hasColumn(commentMeta, "createdAt");
		
		
		this.dataPost();
		this.dataComment();
		this.dataUser();
		
		/* For Yoan
		this.testRepoPost();
		this.testRepoComment();
		this.testRepoUser();
		*/
	}

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
	
	@Test
	public void modelPost() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/Post.java");
	}
	
	@Test
	public void modelComment() {		
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/Comment.java");
	}
	
	@Test
	public void modelUser() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/User.java");
	}
	
	@Test
	public void dataPost() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostAdapterBase.java");
	}
	
	@Test
	public void dataComment() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentAdapterBase.java");
	}
	
	@Test
	public void dataUser() {
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserAdapter.java");
		this.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserAdapterBase.java");
	}
	
	
}
