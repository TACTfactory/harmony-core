/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tactfactory.mda.annotation.Column.Type;
import com.tactfactory.mda.meta.TranslationMetadata.Group;
import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.plateforme.SqliteAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity field metadata. */
public class FieldMetadata extends BaseMetadata {
	/** Owner. */
	public ClassMetadata owner;
	
	/** Field type. */
	public String type;
	
	/** Column name. */
	public String columnName;
	
	/** Field database type. */
	public String columnDefinition;
	
	/** Relation mapped to this field. */
	public RelationMetadata relation;
	
	/** Field nullability. */
	public Boolean nullable;
	/** Field unicity. */
	public Boolean unique;
	/** Field locality. */
	public Boolean isLocale;
	
	/** Field length. */
	public Integer length;
	/** Field precision. */
	public Integer precision;
	/** Field scale. */
	public Integer scale;
	
	/** Is Field hidden ? */
	public boolean hidden;
	/** Is field ID ? */
	public boolean id;
	
	
	/** Is field internal ? */
	public boolean internal = false;
	
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
				this.owner.name.toLowerCase() + "_" + this.name.toLowerCase();
		final boolean isDate = this.type.equals(Type.DATE.getValue());
		final boolean isTime = this.type.equals(Type.TIME.getValue());
		final boolean isDateTime = this.type.equals(Type.DATETIME.getValue());
		
		if (isDate || isDateTime || isTime) {
			final String formatKey = "%s_%s_title";
			final String formatTitle = "Select %s %s";
			if (isDate || isDateTime) {
				TranslationMetadata.addDefaultTranslation(
						String.format(formatKey, key, Type.DATE.getValue()),
						String.format(formatTitle,
								this.name,
								Type.DATE.getValue()), 
						Group.MODEL);
			} 
			
			if (isTime || isDateTime) {
				TranslationMetadata.addDefaultTranslation(
						String.format(formatKey, key, Type.TIME.getValue()),
						String.format(formatTitle,
								this.name,
								Type.TIME.getValue()), 
						Group.MODEL);
			} 
		} 
		
		TranslationMetadata.addDefaultTranslation(
					key + "_" + componentName.toLowerCase(Locale.ENGLISH),
					this.name, 
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
		
		model.put(TagConstant.NAME, 		this.name);
		model.put(TagConstant.TYPE, 		this.type);
		model.put(TagConstant.FIELD_NAME, 	this.columnName);
		model.put(TagConstant.FIELD_DEF, 	this.columnDefinition);
		model.put(TagConstant.HIDDEN, 		this.hidden);
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
		for (final Metadata bm : this.options.values()) {
			optionsModel.put(bm.getName(), bm.toMap(adapter));
		}
		model.put(TagConstant.OPTIONS, optionsModel);
		
		return model;
	}
}
