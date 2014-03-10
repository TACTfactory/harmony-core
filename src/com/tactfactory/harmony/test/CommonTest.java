/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.base.Strings;
import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.ProjectDiscover;
import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.meta.InheritanceMetadata;
import com.tactfactory.harmony.utils.ConsoleUtils;
import com.tactfactory.harmony.utils.TactFileUtils;

/**
 *
 */
public abstract class CommonTest {
	private static final String CLASS_SERIALIZABLE = "Serializable";
	private static final String CLASS_PARCELABLE = "Parcelable";
	private static final String CLASS_PARCEL = "Parcel";
	private static final String CLASS_CLONEABLE = "Cloneable";
	private static final String CLASS_DATETIME = "DateTime";
	private static final String CLASS_ENTITY = "Entity";
	private static final String CLASS_TYPE = "Type";
	private static final String CLASS_ARRAYLIST = "ArrayList";

	private static final String CLASS_COLUMN = "Column";
	private static final String CLASS_COLUMN_RESULT = "ColumnResult";
	private static final String CLASS_DISCRIMINATOR_COLUMN = "DiscriminatorColumn";
	private static final String CLASS_GENERATED_VALUE = "GeneratedValue";
	private static final String CLASS_ID = "Id";
	private static final String CLASS_CRUD = "Crud";
	private static final String CLASS_INHERITANCE_TYPE = "InheritanceType";
	private static final String CLASS_MANY_TO_ONE = "ManyToOne";
	private static final String CLASS_MANY_TO_MANY = "ManyToOne";
	private static final String CLASS_ONE_TO_MANY = "OneToMany";
	private static final String CLASS_TABLE = "Table";
	private static final String CLASS_INHERITANCE_MODE = "InheritanceMode";
	
	/** Current tested metadata. */
	protected final ApplicationMetadata currentMetadata;
	
	/** Old metadata. */
	protected static ApplicationMetadata oldMetadata;

	/** Parsed metadata. */
	protected static ApplicationMetadata parsedMetadata;
	
	/** Console delimiter for tests. */
	protected static final String SHARP_DELIMITOR =
			  "#################"
			 + "#################"
			 + "#################"
			 + "#################"
			 + "#########";

	/** Harmony instance. */
	private static Harmony harmony;
	
	public CommonTest(ApplicationMetadata currentMetadata) {
			this.currentMetadata = currentMetadata;
			if (!this.currentMetadata.equals(oldMetadata)) {
				CommonTest.cleanAndroidFolder();
				this.setUpBeforeNewParameter();
			}
			CommonTest.oldMetadata = this.currentMetadata;
	}
	
	public CommonTest() {
		this.currentMetadata = null;
	}

	public void setUpBeforeNewParameter() {
		// Base configs
		ConsoleUtils.setAnsi(false);
		ConsoleUtils.setQuiet(false);
		ConsoleUtils.setDebug(true);

		// Project test config
		ApplicationMetadata.INSTANCE.setName(
				this.currentMetadata.getName());
		
		ApplicationMetadata.INSTANCE.setProjectNameSpace(
				this.currentMetadata.getProjectNameSpace());

		harmony = Harmony.getInstance();

		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			final String localProp =
					String.format("%s/%s",
							Harmony.getProjectAndroidPath(),
							"local.properties");

			ApplicationMetadata.setAndroidSdkPath(
					ProjectDiscover.getSdkDirFromPropertiesFile(localProp));

			if (ApplicationMetadata.getAndroidSdkPath() == null) {
				ApplicationMetadata.setAndroidSdkPath(
						"/opt/android-sdk-linux_86/");
			}
		}
	}
	
	/**
	 * Add logger to common test life-cycle.
	 */
	@Rule
	public final TestRule watcher = new TestWatcher() {
		protected void starting(final Description description) {
			System.out.println(SHARP_DELIMITOR 
					+ "\n# Starting test: " + description.getMethodName() 
					+ "\n" + SHARP_DELIMITOR);
		}
		
		@Override
		protected void failed(final Throwable e,
				final Description description) {
			
		}
		
		@Override
		protected void succeeded(final Description description) {
			System.out.println("So good !");
		}
		
		@Override
		protected void finished(final Description description) {
			System.out.println(SHARP_DELIMITOR 
					+ "\n# Finishing test: " + description.getMethodName() 
					+ "\n" + SHARP_DELIMITOR + "\n");
		}
	};
	

	/**
	 * Initialization.
	 * @throws Exception if something bad happens
	 */
	public static void setUpBefore() throws RuntimeException {
		// Base configs
		ConsoleUtils.setAnsi(false);
		ConsoleUtils.setQuiet(false);
		ConsoleUtils.setDebug(true);

		// Project test config
		ApplicationMetadata.INSTANCE.setName("demact");
		ApplicationMetadata.INSTANCE.setProjectNameSpace(
				"com/tactfactory/harmony/test/demact");

		harmony = Harmony.getInstance();

		if (Strings.isNullOrEmpty(ApplicationMetadata.getAndroidSdkPath())) {
			final String localProp =
					String.format("%s/%s",
							Harmony.getProjectAndroidPath(),
							"local.properties");

			ApplicationMetadata.setAndroidSdkPath(
					ProjectDiscover.getSdkDirFromPropertiesFile(localProp));

			if (ApplicationMetadata.getAndroidSdkPath() == null) {
				ApplicationMetadata.setAndroidSdkPath(
						"/opt/android-sdk-linux_86/");
			}
		}
	}

	/**
	 * Initialization.
	 * @throws Exception if something bad happends.
	 */
	public void setUp() throws RuntimeException {

	}

	/**
	 * Test clean.
	 * @throws Exception if something bad happends.
	 */
	public void tearDown() throws RuntimeException {
	}

	/** Get Harmony instance.
	 * @return The Harmony instance
	 */
	public static Harmony getHarmony() {
		return harmony;
	}

	/**
	 * Copy the test entities in the test project.
	 */
	protected static void makeEntities() {
		final String pathNameSpace =
				ApplicationMetadata.INSTANCE.getProjectNameSpace()
					.replaceAll("\\.", "/");

		final String srcDir = 
				String.format("%s/tact-core/resources/%s/%s/",
						Harmony.getBundlePath(),
						pathNameSpace, 
						"entity");
		
		final String destDir = 
				String.format("%s/src/%s/%s/", 
						Harmony.getProjectAndroidPath(), 
						pathNameSpace, 
						"entity");

		System.out.println(destDir);

		TactFileUtils.makeFolderRecursive(srcDir, destDir, true);
		if (new File(destDir + "Post.java").exists()) {
			ConsoleUtils.displayDebug("Entity is copy to generated package !");
		}
	}

	/**
	 * Test if file exists.
	 * @param fileName The file name
	 */
	protected static void hasFindFile(final String fileName) {
		final File file =
				new File(
					String.format("%s/%s",
						Harmony.getProjectPath(),
						fileName));
		ConsoleUtils.display("Testing existence of " + file.getAbsolutePath());
		assertTrue(file.getAbsolutePath() + " does not exist", file.exists());
	}
	
	/**
	 * Clean the /android/ folder.
	 */
	protected static void cleanAndroidFolder() {
		ConsoleUtils.display(
				  "################################  "
				+ "Clean Android Folder !! "
				+ "################################");
		final File dirproj = new File(Harmony.getProjectAndroidPath());
		TactFileUtils.deleteRecursive(dirproj);
	}
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		Collection<Object[]> result = new ArrayList<Object[]>();
		result.add(new ApplicationMetadata[]{CommonTest.getDemactMetadata()});
		result.add(new ApplicationMetadata[]{CommonTest.getTracscanMetadata()});
		
		return result;
	}

	private static ApplicationMetadata getDemactMetadata() {
		ApplicationMetadata demact = new ApplicationMetadata();
		demact.setName("demact");
		demact.setProjectNameSpace("com/tactfactory/harmony/test/demact");
		
		// Entities
		EntityMetadata user = new EntityMetadata();
		user.setName("User");
		
		EntityMetadata client = new EntityMetadata();
		client.setName("Client");

		EntityMetadata category = new EntityMetadata();
		category.setName("Category");

		EntityMetadata categoryToComment = new EntityMetadata();
		categoryToComment.setName("CategoryToComment");

		EntityMetadata comment = new EntityMetadata();
		comment.setName("Comment");

		EntityMetadata hiddenEntity = new EntityMetadata();
		hiddenEntity.setName("HiddenEntity");

		EntityMetadata post = new EntityMetadata();
		post.setName("Post");

		EntityMetadata simpleEntity = new EntityMetadata();
		simpleEntity.setName("SimpleEntity");

		EntityMetadata userGroup = new EntityMetadata();
		userGroup.setName("UserGroup");

		EntityMetadata viewComponent = new EntityMetadata();
		viewComponent.setName("ViewComponent");

		EntityMetadata emptyEntity = new EntityMetadata();
		emptyEntity.setName("EmptyEntity");

		EntityMetadata postToCategory = new EntityMetadata();
		postToCategory.setName("PosttoCategory");
		
		demact.getEntities().put(user.getName(), user);
		demact.getEntities().put(client.getName(), client);
		demact.getEntities().put(comment.getName(), comment);
		demact.getEntities().put(category.getName(), category);
		demact.getEntities().put(categoryToComment.getName(), categoryToComment);
		demact.getEntities().put(post.getName(), post);
		demact.getEntities().put(emptyEntity.getName(), emptyEntity);
		demact.getEntities().put(simpleEntity.getName(), simpleEntity);
		demact.getEntities().put(userGroup.getName(), userGroup);
		demact.getEntities().put(viewComponent.getName(), viewComponent);
		demact.getEntities().put(hiddenEntity.getName(), hiddenEntity);
		demact.getEntities().put(postToCategory.getName(), postToCategory);
		

		// Set Implements
		user.getImplementTypes().add(CLASS_SERIALIZABLE);
		user.getImplementTypes().add(CLASS_PARCELABLE);
		user.getImplementTypes().add(CLASS_CLONEABLE);
		comment.getImplementTypes().add(CLASS_SERIALIZABLE);
		comment.getImplementTypes().add(CLASS_PARCELABLE);
		post.getImplementTypes().add(CLASS_SERIALIZABLE);
		post.getImplementTypes().add(CLASS_PARCELABLE);
		client.getImplementTypes().add(CLASS_SERIALIZABLE);
		client.getImplementTypes().add(CLASS_PARCELABLE);
		category.getImplementTypes().add(CLASS_SERIALIZABLE);
		category.getImplementTypes().add(CLASS_PARCELABLE);
		categoryToComment.getImplementTypes().add(CLASS_SERIALIZABLE);
		categoryToComment.getImplementTypes().add(CLASS_PARCELABLE);
		hiddenEntity.getImplementTypes().add(CLASS_SERIALIZABLE);
		hiddenEntity.getImplementTypes().add(CLASS_PARCELABLE);
		simpleEntity.getImplementTypes().add(CLASS_SERIALIZABLE);
		simpleEntity.getImplementTypes().add(CLASS_PARCELABLE);
		userGroup.getImplementTypes().add(CLASS_SERIALIZABLE);
		userGroup.getImplementTypes().add(CLASS_PARCELABLE);
		viewComponent.getImplementTypes().add(CLASS_SERIALIZABLE);
		viewComponent.getImplementTypes().add(CLASS_PARCELABLE);
		emptyEntity.getImplementTypes().add(CLASS_SERIALIZABLE);
		emptyEntity.getImplementTypes().add(CLASS_PARCELABLE);
		
		// Set import
		user.getImports().add(CLASS_SERIALIZABLE);
		user.getImports().add(CLASS_PARCELABLE);
		user.getImports().add(CLASS_PARCEL);
		user.getImports().add(CLASS_DATETIME);
		user.getImports().add(CLASS_ENTITY);
		user.getImports().add(CLASS_TYPE);
		user.getImports().add(CLASS_COLUMN);
		user.getImports().add(CLASS_COLUMN_RESULT);
		user.getImports().add(CLASS_DISCRIMINATOR_COLUMN);
		user.getImports().add(CLASS_GENERATED_VALUE);
		user.getImports().add(CLASS_ID);
		user.getImports().add(CLASS_INHERITANCE_TYPE);
		user.getImports().add(CLASS_MANY_TO_ONE);
		user.getImports().add(CLASS_TABLE);
		user.getImports().add(CLASS_INHERITANCE_MODE);

		client.getImports().add(CLASS_SERIALIZABLE);
		client.getImports().add(CLASS_PARCELABLE);
		client.getImports().add(CLASS_PARCEL);
		client.getImports().add(CLASS_COLUMN);
		client.getImports().add(CLASS_ENTITY);

		comment.getImports().add(CLASS_SERIALIZABLE);
		comment.getImports().add(CLASS_PARCELABLE);
		comment.getImports().add(CLASS_PARCEL);
		comment.getImports().add(CLASS_COLUMN);
		comment.getImports().add(CLASS_ENTITY);
		comment.getImports().add(CLASS_TABLE);
		comment.getImports().add(CLASS_DATETIME);
		comment.getImports().add(CLASS_ARRAYLIST);
		comment.getImports().add(CLASS_GENERATED_VALUE);
		comment.getImports().add(CLASS_MANY_TO_ONE);
		comment.getImports().add(CLASS_ONE_TO_MANY);
		comment.getImports().add(CLASS_ID);
		comment.getImports().add(CLASS_TYPE);

		category.getImports().add(CLASS_SERIALIZABLE);
		category.getImports().add(CLASS_PARCELABLE);
		category.getImports().add(CLASS_PARCEL);
		category.getImports().add(CLASS_COLUMN);
		category.getImports().add(CLASS_ENTITY);
		category.getImports().add(CLASS_ID);
		category.getImports().add(CLASS_ARRAYLIST);
		category.getImports().add(CLASS_ONE_TO_MANY);

		categoryToComment.getImports().add(CLASS_SERIALIZABLE);
		categoryToComment.getImports().add(CLASS_PARCELABLE);
		categoryToComment.getImports().add(CLASS_PARCEL);
		categoryToComment.getImports().add(CLASS_COLUMN);
		categoryToComment.getImports().add(CLASS_ENTITY);
		categoryToComment.getImports().add(CLASS_ID);
		categoryToComment.getImports().add(CLASS_MANY_TO_ONE);

		post.getImports().add(CLASS_SERIALIZABLE);
		post.getImports().add(CLASS_PARCELABLE);
		post.getImports().add(CLASS_PARCEL);
		post.getImports().add(CLASS_ARRAYLIST);
		post.getImports().add(CLASS_DATETIME);
		post.getImports().add(CLASS_COLUMN);
		post.getImports().add(CLASS_ENTITY);
		post.getImports().add(CLASS_TABLE);
		post.getImports().add(CLASS_TYPE);
		post.getImports().add(CLASS_ID);
		post.getImports().add(CLASS_MANY_TO_ONE);
		post.getImports().add(CLASS_MANY_TO_MANY);
		post.getImports().add(CLASS_ONE_TO_MANY);
		post.getImports().add(CLASS_GENERATED_VALUE);

		emptyEntity.getImports().add(CLASS_SERIALIZABLE);
		emptyEntity.getImports().add(CLASS_PARCELABLE);
		emptyEntity.getImports().add(CLASS_PARCEL);
		emptyEntity.getImports().add(CLASS_ENTITY);

		simpleEntity.getImports().add(CLASS_SERIALIZABLE);
		simpleEntity.getImports().add(CLASS_PARCELABLE);
		simpleEntity.getImports().add(CLASS_PARCEL);
		simpleEntity.getImports().add(CLASS_ENTITY);
		simpleEntity.getImports().add(CLASS_COLUMN);
		simpleEntity.getImports().add(CLASS_GENERATED_VALUE);
		simpleEntity.getImports().add(CLASS_ID);
		simpleEntity.getImports().add(CLASS_TYPE);

		userGroup.getImports().add(CLASS_SERIALIZABLE);
		userGroup.getImports().add(CLASS_PARCELABLE);
		userGroup.getImports().add(CLASS_PARCEL);
		userGroup.getImports().add(CLASS_ENTITY);
		userGroup.getImports().add(CLASS_COLUMN);
		userGroup.getImports().add(CLASS_ID);

		viewComponent.getImports().add(CLASS_SERIALIZABLE);
		viewComponent.getImports().add(CLASS_PARCELABLE);
		viewComponent.getImports().add(CLASS_PARCEL);
		viewComponent.getImports().add(CLASS_COLUMN);
		viewComponent.getImports().add(CLASS_ENTITY);
		viewComponent.getImports().add(CLASS_ID);
		viewComponent.getImports().add(CLASS_GENERATED_VALUE);
		viewComponent.getImports().add(CLASS_TYPE);
		viewComponent.getImports().add(CLASS_DATETIME);

		hiddenEntity.getImports().add(CLASS_SERIALIZABLE);
		hiddenEntity.getImports().add(CLASS_PARCELABLE);
		hiddenEntity.getImports().add(CLASS_PARCEL);
		hiddenEntity.getImports().add(CLASS_DATETIME);
		hiddenEntity.getImports().add(CLASS_ARRAYLIST);
		hiddenEntity.getImports().add(CLASS_ENTITY);
		hiddenEntity.getImports().add(CLASS_TYPE);
		hiddenEntity.getImports().add(CLASS_COLUMN);
		hiddenEntity.getImports().add(CLASS_GENERATED_VALUE);
		hiddenEntity.getImports().add(CLASS_ID);
		hiddenEntity.getImports().add(CLASS_MANY_TO_ONE);
		hiddenEntity.getImports().add(CLASS_MANY_TO_MANY);
		hiddenEntity.getImports().add(CLASS_ONE_TO_MANY);
		hiddenEntity.getImports().add(CLASS_CRUD);
		hiddenEntity.getImports().add(CLASS_TABLE);


		// Set entities attributes
		hiddenEntity.setHidden(true);
		postToCategory.setInternal(true);
		postToCategory.setHidden(true);
		
		
		// Set inheritance
		InheritanceMetadata userInheritance = new InheritanceMetadata();
		Map<String, EntityMetadata> userSubclasses = new HashMap<String, EntityMetadata>();
		userSubclasses.put(client.getName(), client);
		userInheritance.setSubclasses(userSubclasses);
		userInheritance.setType(InheritanceMode.SINGLE_TAB);
		FieldMetadata discriminator = new FieldMetadata(user);
		discriminator.setColumnName("type");
		discriminator.setColumnDefinition("varchar");
		userInheritance.setDiscriminorColumn(discriminator);
		user.setInheritance(userInheritance);

		InheritanceMetadata clientInheritance = new InheritanceMetadata();
		clientInheritance.setSuperclass(user);
		clientInheritance.setType(InheritanceMode.SINGLE_TAB);
		clientInheritance.setDiscriminorIdentifier("Client");
		client.setInheritance(clientInheritance);
		
		
		// Set entities ids
		CommonTest.generateIdField(user, "id");
		CommonTest.generateIdField(post, "id");
		CommonTest.generateIdField(comment, "id");
		CommonTest.generateIdField(category, "id");
		CommonTest.generateIdField(categoryToComment, "id");
		CommonTest.generateIdField(userGroup, "id");
		CommonTest.generateIdField(viewComponent, "id");
		CommonTest.generateIdField(hiddenEntity, "id");
		CommonTest.generateIdField(simpleEntity, "id");
		
		
		// Set entities fields
		CommonTest.generateField(user, "login", false, true);
		CommonTest.generateField(user, "password", false, false);
		CommonTest.generateField(user, "firstname", true, false);
		CommonTest.generateField(user, "lastname", false, false);
		CommonTest.generateField(user, "createdAt", false, false, "created_at");
		CommonTest.generateField(user, "birthdate", false, false);
		CommonTest.generateField(user, "userGroup", false, false);
		CommonTest.generateField(user, "title", false, false);
		CommonTest.generateField(user, "fullName", false, false, "firstname || ' ' || lastname");

		CommonTest.generateField(client, "adress", false, false);

		CommonTest.generateField(comment, "content", false, false);
		CommonTest.generateField(comment, "owner", false, false);
		CommonTest.generateField(comment, "post", false, false);
		CommonTest.generateField(comment, "createdAt", false, false, "created_at");
		CommonTest.generateField(comment, "validate", false, false);
		CommonTest.generateField(comment, "categories", false, false);
		
		CommonTest.generateField(category, "name", false, false);
		CommonTest.generateField(category, "comments", false, false);
		
		CommonTest.generateField(categoryToComment, "displayName", false, false);
		CommonTest.generateField(categoryToComment, "category", false, false);
		CommonTest.generateField(categoryToComment, "CommentcategoriesInternal", true, false, "Comment_categories_internal");
		
		CommonTest.generateField(post, "title", false, false);
		CommonTest.generateField(post, "content", false, false);
		CommonTest.generateField(post, "owner", false, false);
		CommonTest.generateField(post, "comments", false, false);
		CommonTest.generateField(post, "categories", false, false);
		CommonTest.generateField(post, "createdAt", false, false, "created_at");
		CommonTest.generateField(post, "updatedAt", false, false, "updated_at");
		CommonTest.generateField(post, "expiresAt", false, false, "expires_at");
		
		CommonTest.generateField(userGroup, "name", false, false);
		CommonTest.generateField(userGroup, "writePermission", false, false);
		CommonTest.generateField(userGroup, "deletePermission", false, false);
		
		CommonTest.generateField(viewComponent, "string", false, false);
		CommonTest.generateField(viewComponent, "text", false, false);
		CommonTest.generateField(viewComponent, "dateTime", false, false);
		CommonTest.generateField(viewComponent, "date", false, false);
		CommonTest.generateField(viewComponent, "time", false, false);
		CommonTest.generateField(viewComponent, "login", false, false);
		CommonTest.generateField(viewComponent, "password", false, false);
		CommonTest.generateField(viewComponent, "email", false, false);
		CommonTest.generateField(viewComponent, "phone", false, false);
		CommonTest.generateField(viewComponent, "city", false, false);
		CommonTest.generateField(viewComponent, "zipCode", false, false);
		CommonTest.generateField(viewComponent, "country", false, false);
		CommonTest.generateField(viewComponent, "byteField", false, false);
		CommonTest.generateField(viewComponent, "charField", false, false);
		CommonTest.generateField(viewComponent, "shortField", false, false);
		CommonTest.generateField(viewComponent, "character", false, false);
		CommonTest.generateField(viewComponent, "choice", false, false);
		
		CommonTest.generateField(hiddenEntity, "content", false, false);
		
		CommonTest.generateField(postToCategory, "PostId", false, false);
		CommonTest.generateField(postToCategory, "CategoryId", false, false);
		
		
		return demact;
	}
	
	private static void generateIdField(EntityMetadata owner, String idName) {
		FieldMetadata id = new FieldMetadata(owner);
		id.setName(idName);
		id.setNullable(false);
		id.setUnique(true);
		id.setColumnName(idName);
		owner.addId(id);
		owner.getFields().put(id.getName(), id);
	}

	private static void generateField(EntityMetadata owner, String fieldName, boolean nullable, boolean unique) {
		CommonTest.generateField(owner, fieldName, nullable, unique, null);
	}

	private static void generateField(EntityMetadata owner, String fieldName, boolean nullable, boolean unique, String columnName) {
		FieldMetadata field = new FieldMetadata(owner);
		field.setName(fieldName);
		field.setNullable(nullable);
		field.setUnique(unique);
		if (columnName == null) {
			field.setColumnName(fieldName);
		} else {
			field.setColumnName(columnName);
		}
		owner.getFields().put(field.getName(), field);
	}
	

	private static ApplicationMetadata getTracscanMetadata() {
		ApplicationMetadata tracscan = new ApplicationMetadata();
		tracscan.setName("tracsan");
		tracscan.setProjectNameSpace("com/tactfactory/harmony/test/tracscan");
		
		EntityMetadata itemProd = new EntityMetadata();
		EntityMetadata logProd = new EntityMetadata();
		EntityMetadata orderProd = new EntityMetadata();
		EntityMetadata user = new EntityMetadata();
		EntityMetadata zone = new EntityMetadata();
		
		itemProd.setName("ItemProd");
		logProd.setName("LogProd");
		orderProd.setName("OrderProd");
		user.setName("User");
		zone.setName("Zone");
		
		tracscan.getEntities().put(itemProd.getName(), itemProd);
		tracscan.getEntities().put(logProd.getName(), logProd);
		tracscan.getEntities().put(orderProd.getName(), orderProd);
		tracscan.getEntities().put(user.getName(), user);
		tracscan.getEntities().put(zone.getName(), zone);
		return tracscan;
	}
}
