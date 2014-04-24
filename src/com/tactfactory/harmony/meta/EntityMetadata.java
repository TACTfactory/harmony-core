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
import com.tactfactory.harmony.plateforme.IAdapter;
import com.tactfactory.harmony.template.TagConstant;

/** Entity class metadata. */
public final class EntityMetadata extends ClassMetadata {

	/** Used for join tables (ManyToMany relations). */
	private boolean internal = false;

	/** List of ids of entity class. */
	private Map<String, FieldMetadata> ids =
			new LinkedHashMap<String, FieldMetadata>();

	/** List of relations of entity class. */
	private Map<String, FieldMetadata> relations =
			new LinkedHashMap<String, FieldMetadata>();
	
	/** List of indexes of entity. */
	private Map<String, ArrayList<String>> indexes =
			new LinkedHashMap<String, ArrayList<String>>();
	
	/** List of orderBys of entity. */
	private Map<String, String> orders =
			new LinkedHashMap<String, String>();	
	
	/** Is entity hidden ? */
	private boolean hidden = false;

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
	public final Map<String, Object> toMap(final IAdapter adapter) {
		final Map<String, Object> model = super.toMap(adapter);

		model.put(TagConstant.IDS,
				this.toFieldArray(this.getIds().values(), adapter));
		model.put(TagConstant.RELATIONS,
				this.toFieldArray(this.relations.values(), adapter));
		model.put(TagConstant.INTERNAL,	this.internal);
		model.put(TagConstant.HIDDEN, this.hidden);
		model.put(TagConstant.INDEXES, this.indexes);
		model.put(TagConstant.ORDERS, this.orders);

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
		// TODO : check if hack is valid ?
		Map<String, FieldMetadata> result;
		if (this.ids.isEmpty() && this.getInheritance() != null
				&& this.getInheritance().getSuperclass() != null) {
			result = this.getInheritance().getSuperclass().getIds();
		} else {
			result = ids;
		}
		return result;
	}
	
	/**
	 * Add an id to the entity.
	 */
	public final void addId(FieldMetadata id) {
		this.ids.put(id.getName(), id);
	}

	/**
	 * @param hidden the hidden to set 
	 */
	public final void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}
	
	/**
	 * @return hidden
	 */
	public final boolean isHidden() {
		return this.hidden;
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
			final IAdapter adapter) {
		final List<Map<String, Object>> result =
				new ArrayList<Map<String, Object>>();
		Map<String, Object> subField = null;

		for (final FieldMetadata field : c) {
			//field.customize(adapter);

			subField = field.toMap(adapter);

			// Add field translate
			if (!field.isInternal() && !field.isHidden() && !this.hidden) {
				field.makeString("label");
			}

			result.add(subField);
		}

		return result;
	}

	/**
	 * Add an index constraint to this entity. 
	 * @param indexName The index name
	 * @param columns The columns of this index
	 */
	public void addIndex(String indexName, ArrayList<String> columns) {
		this.indexes.put(indexName, columns);
	}

	/**
	 * Add an order constraint to this entity.
	 * @param columnName The column name
	 * @param order The order (ASC || DESC)
	 */
	public void addOrder(String columnName, String order) {
		this.orders.put(columnName, order);
	}
	

	/**
	 * Tells if this entitymetadata has fields. (either by itself or by its
	 * superclasses)
	 * @return True if has fields
	 */
	public final boolean hasFields() {
		boolean result = false;
		if (!this.getFields().isEmpty()) {
			result = true;
		} else {
			InheritanceMetadata inheritance = this.getInheritance();
			if (inheritance != null
					&& inheritance.getSuperclass() != null) {
				result = inheritance.getSuperclass().hasFields();
			}
		}
			
		return result;
	}
	
	@Override
	public final void removeField(FieldMetadata field) {
		super.removeField(field);
		this.relations.remove(field.getName());
		this.ids.remove(field.getName());
	}
}
