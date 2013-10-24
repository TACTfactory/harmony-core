/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.harmony.meta;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.TagConstant;

/** Entity relation Metadata. */
public class RelationMetadata extends BaseMetadata {

	/** The type of relation. */
	private String type;

	/** The entity's field which will be used for the relation. */
	private String field;

	/** The related entity. */
	private EntityMetadata entityRef;

	/** Inversed by (in case of OneToMany). */
	private FieldMetadata mappedBy;

	/** Inversed by (in case of ManyToOne). */
	private FieldMetadata inversedBy;

	/** Name of the join table used to join ManyToMany relations. */
	private String joinTable;

	/**
	 * Transform the relation to a field of map of strings.
	 * @param adapter The adapter to use.
	 * @return the generated HashMap
	 */
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = new HashMap<String, Object>();
		model.put(TagConstant.NAME, this.getName());
		model.put(TagConstant.TYPE, this.type);
		model.put(TagConstant.ENTITY_REF, this.entityRef.getName());
		model.put(TagConstant.FIELD_REF, this.entityRef.getIds().values());
		if (this.inversedBy != null) {
			model.put("inversedBy", this.inversedBy.getName());
		}
		if (this.mappedBy != null) {
			model.put("mappedBy", this.mappedBy.getName());
		}
		model.put("joinTable", this.joinTable);

		return model;
	}

	/**
	 * @return the type
	 */
	public final String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the field
	 */
	public final String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public final void setField(final String field) {
		this.field = field;
	}

	/**
	 * @return the entityRef
	 */
	public final EntityMetadata getEntityRef() {
		return this.entityRef;
	}

	/**
	 * @param entityRef the entityRef to set
	 */
	public final void setEntityRef(final EntityMetadata entityRef) {
		this.entityRef = entityRef;
	}

	/**
	 * @return the mappedBy
	 */
	public final FieldMetadata getMappedBy() {
		return mappedBy;
	}

	/**
	 * @param mappedBy the mappedBy to set
	 */
	public final void setMappedBy(final FieldMetadata mappedBy) {
		this.mappedBy = mappedBy;
	}

	/**
	 * @return the inversedBy
	 */
	public final FieldMetadata getInversedBy() {
		return inversedBy;
	}

	/**
	 * @param inversedBy the inversedBy to set
	 */
	public final void setInversedBy(final FieldMetadata inversedBy) {
		this.inversedBy = inversedBy;
	}

	/**
	 * @return the joinTable
	 */
	public final String getJoinTable() {
		return joinTable;
	}

	/**
	 * @param joinTable the joinTable to set
	 */
	public final void setJoinTable(final String joinTable) {
		this.joinTable = joinTable;
	}
}
