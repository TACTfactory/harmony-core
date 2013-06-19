package com.tactfactory.mda.utils;

import com.tactfactory.mda.meta.ApplicationMetadata;
import com.tactfactory.mda.meta.ClassMetadata;
import com.tactfactory.mda.meta.EntityMetadata;

/**
 * Utility class for metadata.
 *
 */
public abstract class MetadataUtils {

	public static final boolean inheritsFromEntity(ClassMetadata classMeta,
			ApplicationMetadata appMeta) {
		boolean result = false;
		result = appMeta.getEntities().containsKey(classMeta.getExtendType());
		return result;
	}
	
	
	public static EntityMetadata getTopMostMother(final EntityMetadata classMeta,
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
