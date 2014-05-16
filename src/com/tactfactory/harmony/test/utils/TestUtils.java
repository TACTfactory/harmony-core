package com.tactfactory.harmony.test.utils;

import org.junit.Assert;

import com.tactfactory.harmony.meta.InheritanceMetadata;

/**
 * Utility class for testing purposes.
 */
public abstract class TestUtils {
	private TestUtils() { }
	
	/**
	 * Tests if 2 inheritance metadata are the same.
	 * @param entityName The tested entity name 
	 * @param projectName The tested project name
	 * @param expected The correct inheritance (expected)
	 * @param actual The tested inheritance (actual)
	 */
	public static void assertEquals(
			String entityName,
			String projectName,
			InheritanceMetadata expected,
			InheritanceMetadata actual) {
		
		if (expected == null) {
			Assert.assertNull(String.format(
						"Inheritance : %s should not be a subclass or a super class in project %s",
						entityName,
						projectName),
					actual);
		} else {
			Assert.assertNotNull(String.format(
						"Inheritance : %s should be a subclass or a super class in project %s",
						entityName,
						projectName),
					actual);
			
			Assert.assertEquals(String.format(
						"Inheritance : %s has wrong inheritance type in project %s",
						entityName,
						projectName),
					expected.getType(),
					actual.getType());

			Assert.assertEquals(String.format(
					"Inheritance : %s has wrong discriminor identifier in project %s",
					entityName,
					projectName),
				expected.getDiscriminorIdentifier(),
				actual.getDiscriminorIdentifier());
			

			if (expected.getDiscriminorColumn() == null) {
				Assert.assertNull(String.format(
						"Inheritance : %s should not have a discriminor column in project %s",
						entityName,
						projectName),
					actual.getDiscriminorColumn());
			} else {
				Assert.assertEquals(String.format(
						"Inheritance : %s has wrong discriminor column name in project %s",
						entityName,
						projectName),
					expected.getDiscriminorColumn().getColumnName(),
					actual.getDiscriminorColumn().getColumnName());
				

				Assert.assertEquals(String.format(
						"Inheritance : %s has wrong discriminor column type in project %s",
						entityName,
						projectName),
					expected.getDiscriminorColumn().getColumnDefinition(),
					actual.getDiscriminorColumn().getColumnDefinition());
			}
		}
	}
}
