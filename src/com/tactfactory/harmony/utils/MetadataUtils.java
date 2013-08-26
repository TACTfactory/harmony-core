package com.tactfactory.harmony.utils;

import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;

/**
 * Utility class for metadata.
 *
 */
public abstract class MetadataUtils {

	/**
	 * Tells whether or not an Entity inherits from another Entity.
	 * @param classMeta The entity to test.
	 * @param appMeta The application metadata
	 * @return True if inheritance found, false otherwise
	 */
	public static final boolean inheritsFromEntity(ClassMetadata classMeta,
			ApplicationMetadata appMeta) {
		boolean result = false;
		result = appMeta.getEntities().containsKey(classMeta.getExtendType());
		return result;
	}

	/**
	 * Gets the top level inheritancy found for the given entity.
	 * @param classMeta The entity metadata
	 * @param appMeta The application metadata
	 * @return The top level of inheritance for this entity. (Will be the given
	 * 				entity if no inheritance found.)
	 */
	public static EntityMetadata getTopMostMother(
			final EntityMetadata classMeta,
			final ApplicationMetadata appMeta) {
		if (appMeta.getEntities().get(classMeta.getExtendType()) != null) {
			return MetadataUtils.getTopMostMother(
					appMeta.getEntities().get(classMeta.getExtendType()),
					appMeta);
		} else {
			return classMeta;
		}
	}
}
