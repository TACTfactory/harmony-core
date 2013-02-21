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
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.mda.Harmony;
import com.tactfactory.mda.command.OrmCommand;
import com.tactfactory.mda.command.ProjectCommand;
import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.test.CommonTest;

public class OrmInitEntitiesTest extends CommonTest {
	static ClassMetadata userMeta = null, postMeta = null, commentMeta = null;
	private final static String CREATED_AT = "createdAt";
	private final static String SERIALIZABLE = "Serializable";
	private final static String LASTNAME = "lastname";
	private final static String PASSWORD = "password";
	private final static String FIRSTNAME = "firstname";
	private final static String LOGIN = "login";
	private final static String MSG_CHECK_IF_FIELD = "Check if field ";
	
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
		harmony.findAndExecute(OrmCommand.GENERATE_ENTITIES, new String[]{}, null);
		final OrmCommand command = (OrmCommand) Harmony.instance.getCommand(OrmCommand.class);

		command.generateMetas();
		
		for (final ClassMetadata classMetadata : ApplicationMetadata.INSTANCE.entities.values()) {
			if (classMetadata.name.equals("User")) {
				userMeta = classMetadata;
			}
			
			if (classMetadata.name.equals("Post")) {
				postMeta = classMetadata;
			}
			
			if (classMetadata.name.equals("Comment")) {
				commentMeta = classMetadata;
			}
		}
	}
	
	//@Test
	public void all() {		
		// Check Model Decoration
		// User
		this.hasUserEntity();
		this.hasUserImplement();
		this.hasUserImport();
		this.hasUserId();
		this.hasUserColumn();
		this.hasUserFieldAnnotations();
		
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
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/Post.java");
		Assert.assertNotNull("Post no parsed !", postMeta);
	}
	
	@Test
	public void hasPostImplement() {
		this.hasImplement(postMeta, SERIALIZABLE);
	}
	
	@Test
	public void hasPostImport() {
		this.hasImport(postMeta, SERIALIZABLE);
		this.hasImport(postMeta, "ArrayList");
		this.hasImport(postMeta, "DateTime");
		this.hasImport(postMeta, "Entity");
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
		this.hasColumn(postMeta, CREATED_AT);
		this.hasColumn(postMeta, "updatedAt");
		this.hasColumn(postMeta, "expiresAt");
	}
	
	
	//// COMMENT ////
	@Test
	public void hasCommentEntity() {		
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/Comment.java");
		Assert.assertNotNull("Comment no parsed !", commentMeta);
	}
	
	@Test
	public void hasCommentImplement() {
		this.hasImplement(commentMeta, SERIALIZABLE);
		//TODO add test this.hasExtend(commentMeta, "EntityBase");
	}
	
	@Test
	public void hasCommentImport() {
		this.hasImport(commentMeta, SERIALIZABLE);
		this.hasImport(commentMeta, "DateTime");
		this.hasImport(commentMeta, "Entity");
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
		this.hasColumn(commentMeta, CREATED_AT);
	}
	
	
	//// USER ////
	@Test
	public void hasUserEntity() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/entity/User.java");
		Assert.assertNotNull("User no parsed !", userMeta);
	}
	
	@Test
	public void hasUserImplement() {
		this.hasImplement(userMeta, SERIALIZABLE);
	}
	
	@Test
	public void hasUserImport() {
		this.hasImport(userMeta, SERIALIZABLE);
		this.hasImport(userMeta, "DateTime");
		this.hasImport(userMeta, "Entity");
		this.hasImport(userMeta, "Type");
	}
	
	@Test
	public void hasUserId() {
		this.hasId(userMeta, "id");
	}
	
	@Test
	public void hasUserColumn() {
		this.hasColumn(userMeta, "id");
		this.hasColumn(userMeta, LOGIN);
		this.hasColumn(userMeta, PASSWORD);
		this.hasColumn(userMeta, FIRSTNAME);
		this.hasColumn(userMeta, LASTNAME);
		this.hasColumn(userMeta, CREATED_AT);
	}
	
	@Test
	public void hasUserFieldAnnotations() {
		this.isFieldNullable(userMeta, "id", false);
		this.isFieldNullable(userMeta, LOGIN, false);
		this.isFieldNullable(userMeta, PASSWORD, false);
		this.isFieldNullable(userMeta, FIRSTNAME, true);
		this.isFieldNullable(userMeta, LASTNAME, false);
		this.isFieldNullable(userMeta, CREATED_AT, false);
		
		this.isFieldUnique(userMeta, "id", false);
		this.isFieldUnique(userMeta, LOGIN, true);
		this.isFieldUnique(userMeta, PASSWORD, false);
		this.isFieldUnique(userMeta, FIRSTNAME, false);
		this.isFieldUnique(userMeta, LASTNAME, false);
		this.isFieldUnique(userMeta, CREATED_AT, false);
		
		this.hasFieldColumnName(userMeta, "id", "id");
		this.hasFieldColumnName(userMeta, LOGIN, LOGIN);
		this.hasFieldColumnName(userMeta, PASSWORD, PASSWORD);
		this.hasFieldColumnName(userMeta, FIRSTNAME, FIRSTNAME);
		this.hasFieldColumnName(userMeta, LASTNAME, LASTNAME);
		this.hasFieldColumnName(userMeta, CREATED_AT, "created_at");
		
		
		this.isFieldLength(userMeta, "id", Integer.MAX_VALUE);
		this.isFieldLength(userMeta, LOGIN, 255);
		this.isFieldLength(userMeta, PASSWORD, 255);
		this.isFieldLength(userMeta, FIRSTNAME, 255);
		this.isFieldLength(userMeta, LASTNAME, 255);
		this.isFieldLength(userMeta, CREATED_AT, Integer.MAX_VALUE);
	}
	
	//// REPOSITORY POST ////
	@Test
	public void hasPostRepository() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/PostSQLiteAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/PostSQLiteAdapterBase.java");
	}
	
	
	//// REPOSITORY COMMENT ////
	@Test
	public void hasCommentRepository() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/CommentSQLiteAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/CommentSQLiteAdapterBase.java");
	}
	
	
	//// REPOSITORY USER ////
	@Test
	public void hasUserRepository() {
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/UserSQLiteAdapter.java");
		CommonTest.hasFindFile("android/src/com/tactfactory/mda/test/demact/data/base/UserSQLiteAdapterBase.java");
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
	private void hasColumn(final ClassMetadata userMeta, final String value) {
		Assert.assertTrue("Check if column " + value, userMeta.fields.containsKey(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasId(final ClassMetadata userMeta, final String value) {
		Assert.assertTrue("Check if key " + value, userMeta.ids.containsKey(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasImport(final ClassMetadata userMeta, final String value) {
		Assert.assertTrue("Check if import " + value, userMeta.imports.contains(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasImplement(final ClassMetadata userMeta, final String value) {
		Assert.assertTrue("Check if implement " + value, userMeta.implementTypes.contains(value));
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void isFieldNullable(final ClassMetadata classMeta, final String fieldName, final boolean nullable) {
		if (nullable) {
			Assert.assertTrue(
					MSG_CHECK_IF_FIELD +fieldName +" is nullable",
					classMeta.fields.get(fieldName).nullable);
		} else {
			Assert.assertFalse(
					MSG_CHECK_IF_FIELD +fieldName +" is nullable",
					classMeta.fields.get(fieldName).nullable);
		}
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void isFieldUnique(final ClassMetadata classMeta, final String fieldName, final boolean unique) {
		if (unique) {
			Assert.assertTrue(MSG_CHECK_IF_FIELD +fieldName +" is unique", classMeta.fields.get(fieldName).unique);
		} else {
			Assert.assertFalse(MSG_CHECK_IF_FIELD +fieldName +" is unique", classMeta.fields.get(fieldName).unique);
		}
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void hasFieldColumnName(final ClassMetadata classMeta, final String fieldName, final String name) {
		Assert.assertEquals(classMeta.fields.get(fieldName).columnName,name);
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void isFieldLength(final ClassMetadata classMeta, final String fieldName, final int length) {
		Assert.assertEquals(classMeta.fields.get(fieldName).length,Integer.valueOf(length));
	}
}
