/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.TagConstant;

/** Entity class metadata. */
public class EntityMetadata extends ClassMetadata {

	/** Used for join tables (ManyToMany relations). */
	private boolean internal = false;

	/** List of ids of entity class. */
	private Map<String, FieldMetadata> ids =
			new LinkedHashMap<String, FieldMetadata>();

	/** List of relations of entity class. */
	private Map<String, FieldMetadata> relations =
			new LinkedHashMap<String, FieldMetadata>();

	/**
	 * Add Component String of field.
	 * @param componentName Component name
	 */
	public final void makeString(final String componentName) {
		final String key = this.getName().toLowerCase()
				+ "_" + componentName.toLowerCase(Locale.ENGLISH);
		TranslationMetadata.addDefaultTranslation(
				key, this.getName(), Group.MODEL);
	}


	/**
	 * Transform the class to a map given an adapter.
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = super.toMap(adapter);

		model.put(TagConstant.IDS,
				this.toFieldArray(this.ids.values(), adapter));
		model.put(TagConstant.RELATIONS,
				this.toFieldArray(this.relations.values(), adapter));
		model.put(TagConstant.INTERNAL,			"false");
		if (this.internal) {
			model.put(TagConstant.INTERNAL,		"true");
		}

		return model;
	}

	/**
	 * @return the internal
	 */
	public final boolean isInternal() {
		return internal;
	}


	/**
	 * @param internal the internal to set
	 */
	public final void setInternal(final boolean internal) {
		this.internal = internal;
	}

	/**
	 * @return the ids
	 */
	public final Map<String, FieldMetadata> getIds() {
		return ids;
	}


	/**
	 * @param ids the ids to set
	 */
	public final void setIds(final Map<String, FieldMetadata> ids) {
		this.ids = ids;
	}


	/**
	 * @return the relations
	 */
	public final Map<String, FieldMetadata> getRelations() {
		return relations;
	}


	/**
	 * @param relations the relations to set
	 */
	public final void setRelations(final Map<String, FieldMetadata> relations) {
		this.relations = relations;
	}



	/**
	 * Build a map from a collection of fields.
	 * @param c The collection of fields
	 * @param adapter The adapter to use.
	 * @return The fields map.
	 */
	private List<Map<String, Object>> toFieldArray(
			final Collection<FieldMetadata> c,
			final BaseAdapter adapter) {
		final List<Map<String, Object>> result =
				new ArrayList<Map<String, Object>>();
		Map<String, Object> subField = null;

		for (final FieldMetadata field : c) {
			//field.customize(adapter);

			subField = field.toMap(adapter);

			// Add field translate
			if (!field.isInternal() && !field.isHidden()) {
				field.makeString("label");
			}

			result.add(subField);
		}

		return result;
	}
}
