/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.parser;

import java.util.Map;

import com.google.common.base.Strings;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.ApplicationMetadata;
import com.tactfactory.harmony.meta.ClassMetadata;
import com.tactfactory.harmony.meta.EntityMetadata;
import com.tactfactory.harmony.meta.EnumMetadata;
import com.tactfactory.harmony.meta.FieldMetadata;
import com.tactfactory.harmony.plateforme.SqliteAdapter;
import com.tactfactory.harmony.utils.MetadataUtils;


/** The class ClassCompletor will complete all ClassMetadatas
 * with the information it needs from the others ClassMetadatas. */

public class ClassCompletor {

	/** Class metadata. */
	private Map<String, EntityMetadata> metas;
	
	/** Class metadata. */
	private Map<String, EnumMetadata> enumMetas;

	/**
	 * Constructor.
	 * @param entities Entities map.
	 */
	public ClassCompletor(final Map<String, EntityMetadata> entities,
			final Map<String, EnumMetadata> enums) {
		this.metas = entities;
		this.enumMetas = enums;
	}

	/**
	 * Complete classes.
	 */
	public final void execute() {
		for (final EntityMetadata classMeta : this.metas.values()) {
			this.updateInheritedIds(classMeta);
			this.updateColumnDefinition(classMeta);
		}
		
		for (final EnumMetadata enumMeta : this.enumMetas.values()) {
			this.updateColumnDefinition(enumMeta);
		}
	}

	/**
	 * Add the mother ID field to inherited entities.
	 * @param cm The entity to update
	 */
	private void updateInheritedIds(final EntityMetadata cm) {
		// If entity has a mother
		if (cm.getExtendType() != null) {
			final EntityMetadata mother = MetadataUtils.getTopMostMother(cm,
					ApplicationMetadata.INSTANCE);
			for (String idName : mother.getIds().keySet()) {
				final FieldMetadata id = mother.getIds().get(idName);
				cm.getIds().put(idName, id);
				cm.getFields().put(idName, id);
			}
		}
	}
	
	/**
	 * Update fields column definition.
	 * 
	 * @param entity The entity in which to complete fields
	 */
	private void updateColumnDefinition(final ClassMetadata entity) {
		for (final FieldMetadata field : entity.getFields().values()) {
			// Get column definition if non existent
			if (Strings.isNullOrEmpty(field.getColumnDefinition())) {
				field.setColumnDefinition(
						SqliteAdapter.generateColumnType(field));
			}
			
			// Warn the user if the column definition is a reserved keyword
			SqliteAdapter.Keywords.exists(field.getColumnDefinition());
			
			// Set default values for type if type is recognized
			final Type type;
			if (field.getHarmonyType() != null) {
				type = Type.fromName(
						field.getHarmonyType());
			} else {
				type = Type.fromName(
						field.getType());
			}
			
			if (type != null) {
				//field.setColumnDefinition(type.getValue());
				if (field.isNullable() == null) {
					field.setNullable(type.isNullable());
				}
				if (field.isUnique() == null) {
					field.setUnique(type.isUnique());
				}
				if (field.getLength() == null) {
					field.setLength(type.getLength());
				}
				if (field.getPrecision() == null) {
					field.setPrecision(type.getPrecision());
				}
				if (field.getScale() == null) {
					field.setScale(type.getScale());
				}
				if (field.isLocale() == null) {
					field.setIsLocale(type.isLocale());
				}
			} else {
				if (field.isNullable() == null) {
					field.setNullable(false);
				}
				if (field.isUnique() == null) {
					field.setUnique(false);
				}
				if (field.getLength() == null) {
					field.setLength(null);
				}
				if (field.getPrecision() == null) {
					field.setPrecision(null);
				}
				if (field.getScale() == null) {
					field.setScale(null);
				}
				if (field.isLocale() == null) {
					field.setIsLocale(false);
				}
			}
			
			if (Strings.isNullOrEmpty(field.getHarmonyType())) {
				if (type != null) {
					field.setHarmonyType(type.getValue());
				} else {
					field.setHarmonyType(field.getType());
				}
			}
		}
	}
}
