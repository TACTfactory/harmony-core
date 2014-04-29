/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.test.orm;

import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.tactfactory.harmony.Harmony;
import com.tactfactory.harmony.command.OrmCommand;
import com.tactfactory.harmony.command.ProjectCommand;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.test.CommonTest;
import com.tactfactory.harmony.test.utils.TestUtils;


@RunWith(Parameterized.class)
/**
 * Entities and Adapters generation tests.
 */
public class OrmInitEntitiesTest extends CommonTest {

	/** Path of entity folder. */
	private static final String ENTITY_PATH =
			"android/src/%s/entity/%s.java";

	/** Path of data folder. */
	private static final String DATA_PATH =
			"android/src/%s/data/%s.java";
	
	public OrmInitEntitiesTest(ApplicationMetadata currentMetadata) {
		super(currentMetadata);
	}

	@Before
	@Override
	public final void setUp() throws RuntimeException {
		super.setUp();
	}

	@After
	@Override
	public final void tearDown() throws RuntimeException {
		super.tearDown();
	}
	
	@Override
	public void setUpBeforeNewParameter() {
		super.setUpBeforeNewParameter();
		
		this.initAll();
	}

	/**
	 * Initialize everything for the test.
	 */
	private void initAll() {
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

		parsedMetadata = ApplicationMetadata.INSTANCE;
	}
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		return CommonTest.getParameters();
	}

	@Test
	public final void testEntities() {
		for (EntityMetadata entity : this.currentMetadata.getEntities().values()) {
			Assert.assertNotNull(String.format(
					"Entity %s not found in project : %s.",
					entity.getName(),
					this.currentMetadata.getName()),
					parsedMetadata.getEntities().get(entity.getName())); 
			
			if (!entity.isInternal()) {
				CommonTest.hasFindFile(
						String.format(
								ENTITY_PATH,
								this.currentMetadata.getProjectNameSpace(),
								entity.getName()));
			}
		}
		
		Assert.assertEquals(
				String.format(
						"Found a wrong number of entities in project : %s",
						this.currentMetadata.getName()),
				this.currentMetadata.getEntities().size(),
				parsedMetadata.getEntities().size());
	}
	
	@Test
	public final void testInheritance() {
		for (EntityMetadata entity : this.currentMetadata.getEntities().values()) {
			TestUtils.assertEquals(
				entity.getName(),
				this.currentMetadata.getName(),
				entity.getInheritance(),
				parsedMetadata.getEntities().get(entity.getName()).getInheritance());
		}
	}
	
	@Test
	public final void testImplements() {
		for (EntityMetadata entity 
				: this.currentMetadata.getEntities().values()) {
			EntityMetadata parsedEntity = 
					parsedMetadata.getEntities().get(entity.getName());
			
			for (String implement : entity.getImplementTypes()) {
				Assert.assertTrue(String.format(
						"Implement : %s should implement %s in project %s",
						entity.getName(),
						implement,
						this.currentMetadata.getName()),
					parsedEntity.getImplementTypes().contains(implement));
			}
			
			Assert.assertEquals(String.format(
					"Implement : %s implements a wrong number of interfaces in project %s",
					entity.getName(),
					this.currentMetadata.getName()),
				entity.getImplementTypes().size(),
				parsedEntity.getImplementTypes().size());
		}
	}
	
	@Test
	public final void testImport() {
		for (EntityMetadata entity 
				: this.currentMetadata.getEntities().values()) {
			EntityMetadata parsedEntity = 
					parsedMetadata.getEntities().get(entity.getName());
			
			for (String impor : entity.getImports()) {
				Assert.assertTrue(String.format(
						"Import : %s should import %s in project %s",
						entity.getName(),
						impor,
						this.currentMetadata.getName()),
					parsedEntity.getImports().contains(impor));
			}
			
			Assert.assertEquals(String.format(
					"Import : %s has a wrong number of imports in project %s",
					entity.getName(),
					this.currentMetadata.getName()),
				entity.getImports().size(),
				parsedEntity.getImports().size());
		}
	}
	
	@Test
	public void testIds() {
		for (EntityMetadata entity 
				: this.currentMetadata.getEntities().values()) {
			EntityMetadata parsedEntity = 
					parsedMetadata.getEntities().get(entity.getName());
			
			for (FieldMetadata id : entity.getIds().values()) {
				Assert.assertNotNull(String.format(
						"Ids : %s have wrong id in project %s",
						entity.getName(),
						this.currentMetadata.getName()),
					parsedEntity.getIds().get(id.getName()));
			}
			
			Assert.assertEquals(String.format(
					"Ids : %s has a wrong number of ids in project %s",
					entity.getName(),
					this.currentMetadata.getName()),
				entity.getIds().size(),
				parsedEntity.getIds().size());
		}
	}
	
	@Test
	public void testFields() {
		for (EntityMetadata entity 
				: this.currentMetadata.getEntities().values()) {
			EntityMetadata parsedEntity = 
					parsedMetadata.getEntities().get(entity.getName());
			
			for (FieldMetadata field : entity.getFields().values()) {
				FieldMetadata parsedField = 
						parsedEntity.getFields().get(field.getName()); 
				Assert.assertNotNull(String.format(
						"Fields : %s should have field %s in project %s (have %s)",
						entity.getName(),
						field.getName(),
						this.currentMetadata.getName(),
						parsedEntity.getFields().values().toString()),
					parsedField);

				Assert.assertEquals(String.format(
						"Fields : %s.%s should have nullable = %s in project %s",
						entity.getName(),
						field.getName(),
						field.isNullable(),
						this.currentMetadata.getName()),
					field.isNullable(),
					parsedField.isNullable());
				

				Assert.assertEquals(String.format(
						"Fields : %s.%s has a wrong column name in project %s",
						entity.getName(),
						field.getName(),
						this.currentMetadata.getName()),
					field.getColumnName(),
					parsedField.getColumnName());
			}
			
			Assert.assertEquals(String.format(
					"Fields : %s has a wrong number of fields in project %s",
					entity.getName(),
					this.currentMetadata.getName()),
				entity.getFields().size(),
				parsedEntity.getFields().size());
		}
	}
	
	@Test
	public void testRepositories() {
		for (EntityMetadata entity 
				: this.currentMetadata.getEntities().values()) {
			
			if (!entity.getFields().isEmpty()) {
				CommonTest.hasFindFile(String.format(
						DATA_PATH,
						this.currentMetadata.getProjectNameSpace(),
						entity.getName() + "SQLiteAdapter"));
	
				CommonTest.hasFindFile(String.format(
						DATA_PATH,
						this.currentMetadata.getProjectNameSpace(),
						"base/" + entity.getName() + "SQLiteAdapterBase"));
			}
		}
	}
}
