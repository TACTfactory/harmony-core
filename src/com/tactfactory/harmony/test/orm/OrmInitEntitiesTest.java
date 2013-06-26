/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.orm;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.test.CommonTest;

/**
 * Entities and Adapters generation tests.
 */
public class OrmInitEntitiesTest extends CommonTest {
	/** User MetaData. */
	private static EntityMetadata userMeta = null;
	
	/** Post MetaData. */
	private static EntityMetadata postMeta = null;
	
	/** Comment MetaData. */
	private static EntityMetadata commentMeta = null;
	
	/** createdAt field name. */
	private static final String CREATED_AT = "createdAt";
	
	/** Serializable class name. */
	private static final String SERIALIZABLE = "Serializable";
	
	/** lastname field name. */
	private static final String LASTNAME = "lastname";
	
	/** password field name. */
	private static final String PASSWORD = "password";
	
	/** firstname field name. */
	private static final String FIRSTNAME = "firstname";
	
	/** login field name. */
	private static final String LOGIN = "login";
	
	/** Debug message. */
	private static final String MSG_CHECK_IF_FIELD = "Check if field ";
	
	/** Default fields size. */
	private static final int DEFAULT_SIZE = 255;
	
	/** Path of entity folder. */
	private static final String ENTITY_PATH = 
			"android/src/com/tactfactory/harmony/test/demact/entity/";
	
	/** Path of data folder. */
	private static final String DATA_PATH = 
			"android/src/com/tactfactory/harmony/test/demact/data/";
	
	/**
	 * Initialization.
	 * @throws Exception 
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
	 * Initialize everything for the test.
	 */
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
				(OrmCommand) Harmony.getInstance().getCommand(
						OrmCommand.class);

		command.generateMetas();
		
		for (final EntityMetadata classMetadata
				: ApplicationMetadata.INSTANCE.getEntities().values()) {
			if (classMetadata.getName().equals("User")) {
				userMeta = classMetadata;
			}
			
			if (classMetadata.getName().equals("Post")) {
				postMeta = classMetadata;
			}
			
			if (classMetadata.getName().equals("Comment")) {
				commentMeta = classMetadata;
			}
		}
	}
	
	//@Test
	/** 
	 * Launch all the tests.
	 */
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
	/** Is post created and parsed well. */
	@Test
	public final void hasPostEntity() {
		CommonTest.hasFindFile(ENTITY_PATH + "Post.java");
		Assert.assertNotNull("Post no parsed !", postMeta);
	}
	
	/** Does post implement Serializable ? */
	@Test
	public final void hasPostImplement() {
		this.hasImplement(postMeta, SERIALIZABLE);
	}
	
	/** Import tests. */
	@Test
	public final void hasPostImport() {
		this.hasImport(postMeta, SERIALIZABLE);
		this.hasImport(postMeta, "ArrayList");
		this.hasImport(postMeta, "DateTime");
		this.hasImport(postMeta, "Entity");
	}
	
	/** ID Test. */
	@Test
	public final void hasPostId() {
		this.hasId(postMeta, "id");
	}
	
	/** Fields Test. */
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
	/** Is comment created and parsed well. */
	@Test
	public final void hasCommentEntity() {		
		CommonTest.hasFindFile(ENTITY_PATH + "Comment.java");
		Assert.assertNotNull("Comment no parsed !", commentMeta);
	}
	
	/** Does comment implement Serializable ? */
	@Test
	public final void hasCommentImplement() {
		this.hasImplement(commentMeta, SERIALIZABLE);
		//TODO add test this.hasExtend(commentMeta, "EntityBase");
	}
	
	/** Import tests. */
	@Test
	public final void hasCommentImport() {
		this.hasImport(commentMeta, SERIALIZABLE);
		this.hasImport(commentMeta, "DateTime");
		this.hasImport(commentMeta, "Entity");
	}
	
	/** ID Test. */
	@Test
	public final void hasCommentId() {
		this.hasId(commentMeta, "id");
	}
	
	/** Fields Test. */
	@Test
	public final void hasCommentColumn() {
		this.hasColumn(commentMeta, "id");
		this.hasColumn(commentMeta, "content");
		this.hasColumn(commentMeta, "owner");
		this.hasColumn(commentMeta, "post");
		this.hasColumn(commentMeta, CREATED_AT);
	}
	
	
	//// USER ////
	/** Is user created and parsed well. */
	@Test
	public final void hasUserEntity() {
		CommonTest.hasFindFile(ENTITY_PATH + "User.java");
		Assert.assertNotNull("User no parsed !", userMeta);
	}
	
	/** Does user implement Serializable ? */
	@Test
	public final void hasUserImplement() {
		this.hasImplement(userMeta, SERIALIZABLE);
	}
	
	/** Import tests. */
	@Test
	public final void hasUserImport() {
		this.hasImport(userMeta, SERIALIZABLE);
		this.hasImport(userMeta, "DateTime");
		this.hasImport(userMeta, "Entity");
		this.hasImport(userMeta, "Type");
	}
	
	/** ID Test. */
	@Test
	public final void hasUserId() {
		this.hasId(userMeta, "id");
	}
	
	/** Fields Test. */
	@Test
	public final void hasUserColumn() {
		this.hasColumn(userMeta, "id");
		this.hasColumn(userMeta, LOGIN);
		this.hasColumn(userMeta, PASSWORD);
		this.hasColumn(userMeta, FIRSTNAME);
		this.hasColumn(userMeta, LASTNAME);
		this.hasColumn(userMeta, CREATED_AT);
	}
	
	/** Annotations Test. */
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
	/** Post Repository creation test. */
	@Test
	public final void hasPostRepository() {
		CommonTest.hasFindFile(DATA_PATH + "PostSQLiteAdapter.java");
		CommonTest.hasFindFile(DATA_PATH + "base/PostSQLiteAdapterBase.java");
	}
	
	
	//// REPOSITORY COMMENT ////
	/** Comment Repository creation test. */
	@Test
	public final void hasCommentRepository() {
		CommonTest.hasFindFile(DATA_PATH + "CommentSQLiteAdapter.java");
		CommonTest.hasFindFile(DATA_PATH 
				 + "base/CommentSQLiteAdapterBase.java");
	}
	
	
	//// REPOSITORY USER ////
	/** User Repository creation test. */
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
	 * Test if class has field.
	 * @param meta The class to test
	 * @param value The name of the field to find
	 */
	private void hasColumn(final ClassMetadata meta, final String value) {
		Assert.assertTrue("Check if column " + value,
				meta.getFields().containsKey(value));
	}

	/**
	 * Test if class has id.
	 * @param meta The class to test
	 * @param value The name of the id to find
	 */
	private void hasId(final EntityMetadata meta, final String value) {
		Assert.assertTrue("Check if key " + value,
				meta.getIds().containsKey(value));
	}

	/**
	 * Test if class has import.
	 * @param meta The class to test
	 * @param value The name of the import to find
	 */
	private void hasImport(final ClassMetadata meta, final String value) {
		Assert.assertTrue("Check if import " + value,
				meta.getImports().contains(value));
	}

	/**
	 * Test if class implements.
	 * @param meta The class to test
	 * @param value The name of the implementation to find
	 */
	private void hasImplement(final EntityMetadata meta, final String value) {
		Assert.assertTrue("Check if implement " + value,
				meta.getImplementTypes().contains(value));
	}
	
	/**
	 * Test if field nullability.
	 * @param classMeta The class containing the field.
	 * @param fieldName The name of the field.
	 * @param nullable Whether the field should be nullable or not
	 */
	private void isFieldNullable(final ClassMetadata classMeta,
			final String fieldName,
			final boolean nullable) {
		if (nullable) {
			Assert.assertTrue(
					MSG_CHECK_IF_FIELD + fieldName + " is nullable",
					classMeta.getFields().get(fieldName).isNullable());
		} else {
			Assert.assertFalse(
					MSG_CHECK_IF_FIELD + fieldName + " is nullable",
					classMeta.getFields().get(fieldName).isNullable());
		}
	}
	
	/**
	 * Test if field unicity.
	 * @param classMeta The class containing the field.
	 * @param fieldName The name of the field.
	 * @param unique Whether the field should be unique or not
	 */
	private void isFieldUnique(final ClassMetadata classMeta,
			final String fieldName,
			final boolean unique) {
		if (unique) {
			Assert.assertTrue(MSG_CHECK_IF_FIELD + fieldName + " is unique",
					classMeta.getFields().get(fieldName).isUnique());
		} else {
			Assert.assertFalse(MSG_CHECK_IF_FIELD + fieldName + " is unique",
					classMeta.getFields().get(fieldName).isUnique());
		}
	}
	
	/**
	 * Test field's column name.
	 * @param classMeta The class containing the field.
	 * @param fieldName The field name
	 * @param name The wanted column name
	 */
	private void hasFieldColumnName(final ClassMetadata classMeta, 
			final String fieldName, 
			final String name) {
		Assert.assertEquals(
				classMeta.getFields().get(fieldName).getColumnName(), name);
	}
	
	/**
	 * Test field's length.
	 * @param classMeta The class containing the field.
	 * @param fieldName The field name.
	 * @param length The wanted length.
	 */
	private void isFieldLength(final ClassMetadata classMeta,
			final String fieldName,
			final int length) {
		Assert.assertEquals(classMeta.getFields().get(fieldName).getLength(),
				Integer.valueOf(length));
	}
}
