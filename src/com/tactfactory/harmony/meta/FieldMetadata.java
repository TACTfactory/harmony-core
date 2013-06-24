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
import java.util.Locale;
import java.util.Map;

import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.meta.TranslationMetadata.Group;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.plateforme.SqliteAdapter;
import com.tactfactory.harmony.template.TagConstant;

/** Entity field metadata. */
public class FieldMetadata extends BaseMetadata {
	/** Owner. */
	private ClassMetadata owner;
	
	/** Field type. */
	private String type;
	
	/** Field harmony type. */
	private String harmonyType;
	
	/** Column name. */
	private String columnName;
	
	/** Field database type. */
	private String columnDefinition;
	
	/** Relation mapped to this field. */
	private RelationMetadata relation;
	
	/** Field nullability. */
	private Boolean nullable;
	/** Field unicity. */
	private Boolean unique;
	/** Field locality. */
	private Boolean isLocale;
	
	/** Field length. */
	private Integer length;
	/** Field precision. */
	private Integer precision;
	/** Field scale. */
	private Integer scale;
	
	/** Is Field hidden ? */
	private boolean hidden;
	/** Is field ID ? */
	private boolean id;
	
	
	/** Is field internal ? */
	private boolean internal = false;
	
	/**
	 * Constructor.
	 * @param owner ClassMetadata owning this field.
	 */
	public FieldMetadata(final ClassMetadata owner) {
		super();
		this.owner = owner;
	}

	
	/** Add Component String of field.
	 * @param componentName The component name
	 */
	public final void makeString(final String componentName) {
		final String key = 
				this.owner.getName().toLowerCase() 
				+ "_" 
				+ this.getName().toLowerCase();
		final boolean isDate = this.harmonyType.equals(Type.DATE.getValue());
		final boolean isTime = this.harmonyType.equals(Type.TIME.getValue());
		final boolean isDateTime = 
				this.harmonyType.equals(Type.DATETIME.getValue());
		
		if (isDate || isDateTime || isTime) {
			final String formatKey = "%s_%s_title";
			final String formatTitle = "Select %s %s";
			if (isDate || isDateTime) {
				TranslationMetadata.addDefaultTranslation(
						String.format(formatKey, key, Type.DATE.getValue()),
						String.format(formatTitle,
								this.getName(),
								Type.DATE.getValue()), 
						Group.MODEL);
			} 
			
			if (isTime || isDateTime) {
				TranslationMetadata.addDefaultTranslation(
						String.format(formatKey, key, Type.TIME.getValue()),
						String.format(formatTitle,
								this.getName(),
								Type.TIME.getValue()), 
						Group.MODEL);
			} 
		} 
		
		TranslationMetadata.addDefaultTranslation(
					key + "_" + componentName.toLowerCase(Locale.ENGLISH),
					this.getName(), 
					Group.MODEL);
	}
	
	/**
	 * Transform the field to a map of strings and a relation map.
	 * @param adapter The adapter to use.
	 * @return the map
	 */
	@Override
	public final Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = new HashMap<String, Object>();
		
		model.put(TagConstant.NAME, 		this.getName());
		model.put(TagConstant.TYPE, 		this.type);
		model.put(TagConstant.HARMONY_TYPE, 		this.harmonyType);
		model.put(TagConstant.FIELD_NAME, 	this.columnName);
		model.put(TagConstant.FIELD_DEF, 	this.columnDefinition);
		model.put(TagConstant.HIDDEN, 		this.hidden);
		model.put(TagConstant.UNIQUE, 		this.unique);
		model.put(TagConstant.ID, 		    this.id);

		
		model.put(TagConstant.SCHEMA,
				SqliteAdapter.generateStructure(this));
		model.put(TagConstant.INTERNAL, 	this.internal);
		model.put(TagConstant.IS_LOCALE, 	this.isLocale);
		model.put(TagConstant.NULLABLE,		this.nullable);
		
		if (this.relation != null) {
			model.put(TagConstant.RELATION, this.relation.toMap(adapter));
		}
		
		final HashMap<String, Object> optionsModel =
				new HashMap<String, Object>();
		for (final Metadata bm : this.getOptions().values()) {
			optionsModel.put(bm.getName(), bm.toMap(adapter));
		}
		model.put(TagConstant.OPTIONS, optionsModel);
		
		return model;
	}


	/**
	 * @return the owner
	 */
	public final ClassMetadata getOwner() {
		return owner;
	}


	/**
	 * @param owner the owner to set
	 */
	public final void setOwner(final ClassMetadata owner) {
		this.owner = owner;
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
	 * @return the columnName
	 */
	public final String getColumnName() {
		return columnName;
	}


	/**
	 * @param columnName the columnName to set
	 */
	public final void setColumnName(final String columnName) {
		this.columnName = columnName;
	}


	/**
	 * @return the columnDefinition
	 */
	public final String getColumnDefinition() {
		return columnDefinition;
	}


	/**
	 * @param columnDefinition the columnDefinition to set
	 */
	public final void setColumnDefinition(final String columnDefinition) {
		this.columnDefinition = columnDefinition;
	}


	/**
	 * @return the relation
	 */
	public final RelationMetadata getRelation() {
		return relation;
	}


	/**
	 * @param relation the relation to set
	 */
	public final void setRelation(final RelationMetadata relation) {
		this.relation = relation;
	}


	/**
	 * @return the nullable
	 */
	public final Boolean isNullable() {
		return nullable;
	}


	/**
	 * @param nullable the nullable to set
	 */
	public final void setNullable(final Boolean nullable) {
		this.nullable = nullable;
	}


	/**
	 * @return the unique
	 */
	public final Boolean isUnique() {
		return unique;
	}


	/**
	 * @param unique the unique to set
	 */
	public final void setUnique(final Boolean unique) {
		this.unique = unique;
	}


	/**
	 * @return the isLocale
	 */
	public final Boolean isLocale() {
		return isLocale;
	}


	/**
	 * @param isLocale the isLocale to set
	 */
	public final void setIsLocale(final Boolean isLocale) {
		this.isLocale = isLocale;
	}


	/**
	 * @return the length
	 */
	public final Integer getLength() {
		return length;
	}


	/**
	 * @param length the length to set
	 */
	public final void setLength(final Integer length) {
		this.length = length;
	}


	/**
	 * @return the precision
	 */
	public final Integer getPrecision() {
		return precision;
	}


	/**
	 * @param precision the precision to set
	 */
	public final void setPrecision(final Integer precision) {
		this.precision = precision;
	}


	/**
	 * @return the scale
	 */
	public final Integer getScale() {
		return scale;
	}


	/**
	 * @param scale the scale to set
	 */
	public final void setScale(final Integer scale) {
		this.scale = scale;
	}


	/**
	 * @return the hidden
	 */
	public final boolean isHidden() {
		return hidden;
	}


	/**
	 * @param hidden the hidden to set
	 */
	public final void setHidden(final boolean hidden) {
		this.hidden = hidden;
	}


	/**
	 * @return the id
	 */
	public final boolean isId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public final void setId(final boolean id) {
		this.id = id;
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
	 * @return the harmonyType
	 */
	public final String getHarmonyType() {
		return harmonyType;
	}


	/**
	 * @param harmonyType the harmonyType to set
	 */
	public final void setHarmonyType(final String harmonyType) {
		this.harmonyType = harmonyType;
	}
}
