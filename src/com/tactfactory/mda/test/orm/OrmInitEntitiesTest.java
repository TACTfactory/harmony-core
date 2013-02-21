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
	private static ClassMetadata userMeta = null;
	private static ClassMetadata postMeta = null;
	private static ClassMetadata commentMeta = null;
	private static final String CREATED_AT = "createdAt";
	private static final String SERIALIZABLE = "Serializable";
	private static final String LASTNAME = "lastname";
	private static final String PASSWORD = "password";
	private static final String FIRSTNAME = "firstname";
	private static final String LOGIN = "login";
	private static final String MSG_CHECK_IF_FIELD = "Check if field ";
	
	private static final int DEFAULT_SIZE = 255;
	
	private static final String ENTITY_PATH = 
			"android/src/com/tactfactory/mda/test/demact/entity/";
	
	private static final String DATA_PATH = 
			"android/src/com/tactfactory/mda/test/demact/data/";
	
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
		System.out.println("\nTest Orm generate entity");
		System.out.println(
				"########################################"
				 + "######################################");

		getHarmony().findAndExecute(ProjectCommand.INIT_ANDROID, null, null);
		makeEntities();
		getHarmony().findAndExecute(OrmCommand.GENERATE_ENTITIES,
				new String[] {}, 
				null);
		final OrmCommand command = 
				(OrmCommand) Harmony.instance.getCommand(OrmCommand.class);

		command.generateMetas();
		
		for (final ClassMetadata classMetadata
				: ApplicationMetadata.INSTANCE.entities.values()) {
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
	public final void all() {		
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
	public final void hasPostEntity() {
		CommonTest.hasFindFile(ENTITY_PATH + "Post.java");
		Assert.assertNotNull("Post no parsed !", postMeta);
	}
	
	@Test
	public final void hasPostImplement() {
		this.hasImplement(postMeta, SERIALIZABLE);
	}
	
	@Test
	public final void hasPostImport() {
		this.hasImport(postMeta, SERIALIZABLE);
		this.hasImport(postMeta, "ArrayList");
		this.hasImport(postMeta, "DateTime");
		this.hasImport(postMeta, "Entity");
	}
	
	@Test
	public final void hasPostId() {
		this.hasId(postMeta, "id");
	}
	
	@Test
	public final void hasPostColumn() {
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
	public final void hasCommentEntity() {		
		CommonTest.hasFindFile(ENTITY_PATH + "Comment.java");
		Assert.assertNotNull("Comment no parsed !", commentMeta);
	}
	
	@Test
	public final void hasCommentImplement() {
		this.hasImplement(commentMeta, SERIALIZABLE);
		//TODO add test this.hasExtend(commentMeta, "EntityBase");
	}
	
	@Test
	public final void hasCommentImport() {
		this.hasImport(commentMeta, SERIALIZABLE);
		this.hasImport(commentMeta, "DateTime");
		this.hasImport(commentMeta, "Entity");
	}
	
	@Test
	public final void hasCommentId() {
		this.hasId(commentMeta, "id");
	}
	
	@Test
	public final void hasCommentColumn() {
		this.hasColumn(commentMeta, "id");
		this.hasColumn(commentMeta, "content");
		this.hasColumn(commentMeta, "owner");
		this.hasColumn(commentMeta, "post");
		this.hasColumn(commentMeta, CREATED_AT);
	}
	
	
	//// USER ////
	@Test
	public final void hasUserEntity() {
		CommonTest.hasFindFile(ENTITY_PATH + "User.java");
		Assert.assertNotNull("User no parsed !", userMeta);
	}
	
	@Test
	public final void hasUserImplement() {
		this.hasImplement(userMeta, SERIALIZABLE);
	}
	
	@Test
	public final void hasUserImport() {
		this.hasImport(userMeta, SERIALIZABLE);
		this.hasImport(userMeta, "DateTime");
		this.hasImport(userMeta, "Entity");
		this.hasImport(userMeta, "Type");
	}
	
	@Test
	public final void hasUserId() {
		this.hasId(userMeta, "id");
	}
	
	@Test
	public final void hasUserColumn() {
		this.hasColumn(userMeta, "id");
		this.hasColumn(userMeta, LOGIN);
		this.hasColumn(userMeta, PASSWORD);
		this.hasColumn(userMeta, FIRSTNAME);
		this.hasColumn(userMeta, LASTNAME);
		this.hasColumn(userMeta, CREATED_AT);
	}
	
	@Test
	public final void hasUserFieldAnnotations() {
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
		this.isFieldLength(userMeta, LOGIN, DEFAULT_SIZE);
		this.isFieldLength(userMeta, PASSWORD, DEFAULT_SIZE);
		this.isFieldLength(userMeta, FIRSTNAME, DEFAULT_SIZE);
		this.isFieldLength(userMeta, LASTNAME, DEFAULT_SIZE);
		this.isFieldLength(userMeta, CREATED_AT, Integer.MAX_VALUE);
	}
	
	//// REPOSITORY POST ////
	@Test
	public final void hasPostRepository() {
		CommonTest.hasFindFile(DATA_PATH + "PostSQLiteAdapter.java");
		CommonTest.hasFindFile(DATA_PATH + "base/PostSQLiteAdapterBase.java");
	}
	
	
	//// REPOSITORY COMMENT ////
	@Test
	public final void hasCommentRepository() {
		CommonTest.hasFindFile(DATA_PATH + "CommentSQLiteAdapter.java");
		CommonTest.hasFindFile(DATA_PATH 
				 + "base/CommentSQLiteAdapterBase.java");
	}
	
	
	//// REPOSITORY USER ////
	@Test
	public final void hasUserRepository() {
		CommonTest.hasFindFile(DATA_PATH + "UserSQLiteAdapter.java");
		CommonTest.hasFindFile(DATA_PATH + "base/UserSQLiteAdapterBase.java");
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
	private void hasColumn(final ClassMetadata meta, final String value) {
		Assert.assertTrue("Check if column " + value,
				meta.fields.containsKey(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasId(final ClassMetadata meta, final String value) {
		Assert.assertTrue("Check if key " + value,
				meta.ids.containsKey(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasImport(final ClassMetadata meta, final String value) {
		Assert.assertTrue("Check if import " + value,
				meta.imports.contains(value));
	}

	/**
	 * @param userMeta
	 */
	private void hasImplement(final ClassMetadata meta, final String value) {
		Assert.assertTrue("Check if implement " + value,
				meta.implementTypes.contains(value));
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void isFieldNullable(final ClassMetadata classMeta,
			final String fieldName,
			final boolean nullable) {
		if (nullable) {
			Assert.assertTrue(
					MSG_CHECK_IF_FIELD + fieldName + " is nullable",
					classMeta.fields.get(fieldName).nullable);
		} else {
			Assert.assertFalse(
					MSG_CHECK_IF_FIELD + fieldName + " is nullable",
					classMeta.fields.get(fieldName).nullable);
		}
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void isFieldUnique(final ClassMetadata classMeta,
			final String fieldName,
			final boolean unique) {
		if (unique) {
			Assert.assertTrue(MSG_CHECK_IF_FIELD + fieldName + " is unique",
					classMeta.fields.get(fieldName).unique);
		} else {
			Assert.assertFalse(MSG_CHECK_IF_FIELD + fieldName + " is unique",
					classMeta.fields.get(fieldName).unique);
		}
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void hasFieldColumnName(final ClassMetadata classMeta, 
			final String fieldName, 
			final String name) {
		Assert.assertEquals(classMeta.fields.get(fieldName).columnName, name);
	}
	
	/**
	 * @param classMeta
	 * @param fieldName
	 */
	private void isFieldLength(final ClassMetadata classMeta,
			final String fieldName,
			final int length) {
		Assert.assertEquals(classMeta.fields.get(fieldName).length,
				Integer.valueOf(length));
	}
}
