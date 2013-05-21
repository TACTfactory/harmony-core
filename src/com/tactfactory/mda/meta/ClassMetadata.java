/**
 * This file is part of the Harmony package.
 *
 * (c) Mickael Gaillard <mickael.gaillard@tactfactory.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package com.tactfactory.mda.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tactfactory.mda.plateforme.BaseAdapter;
import com.tactfactory.mda.template.TagConstant;

/** Entity class metadata. */
public class ClassMetadata extends BaseMetadata {
	/** SubClasses array. */
	private Map<String, ClassMetadata> subClasses = 
			new LinkedHashMap<String, ClassMetadata>();
	
	/** Mother Class name. */
	private String motherClass;
	
	/** List of fields of entity class. */
	private Map<String, FieldMetadata> fields = 
			new LinkedHashMap<String, FieldMetadata>();
		
	/** Implemented class list of the entity class. */
	private List<String> implementTypes = 
			new ArrayList<String>();

	/** Namespace of entity class. */
	private String space = "";
	
	/** Class inherited by the entity class or null if none. */
	private String extendType;
	
	/** Implemented class list of the entity class. */
	private List<MethodMetadata> methods = 
			new ArrayList<MethodMetadata>();

	/** Imports of the class. */
	private List<String> imports = new ArrayList<String>();
	
	/**
	 * Transform the class to a map given an adapter.
	 * @param adapter The adapter used to customize the fields
	 * @return the map
	 */
	@Override
	public Map<String, Object> toMap(final BaseAdapter adapter) {
		final Map<String, Object> model = new HashMap<String, Object>();
		
		model.put(TagConstant.SPACE,			this.space);
		model.put(TagConstant.NAME,				this.getName());
		model.put(TagConstant.EXTENDS,			this.extendType);
		model.put(TagConstant.CONTROLLER_NAMESPACE, 
				adapter.getNameSpaceEntity(this, adapter.getController()));
		model.put(TagConstant.DATA_NAMESPACE, 	
				adapter.getNameSpace(this, adapter.getData()));
		model.put(TagConstant.TEST_NAMESPACE, 	
				adapter.getNameSpace(this, adapter.getTest()));
		model.put(TagConstant.FIELDS,			
				this.toFieldArray(this.fields.values(), adapter));
		
		final Map<String, Object> optionsModel = new HashMap<String, Object>();
		for (final Metadata option : this.getOptions().values()) {
			optionsModel.put(option.getName(), option.toMap(adapter));
		}
		model.put(TagConstant.OPTIONS, optionsModel);

		if (motherClass != null) {
			model.put(TagConstant.MOTHER, this.motherClass);	
		}
		
		return model;
	}

	/**
	 * @return the fields
	 */
	public final Map<String, FieldMetadata> getFields() {
		return fields;
	}


	/**
	 * @param fields the fields to set
	 */
	public final void setFields(final Map<String, FieldMetadata> fields) {
		this.fields = fields;
	}


	/**
	 * @return the extendType
	 */
	public final String getExtendType() {
		return extendType;
	}


	/**
	 * @param extendType the extendType to set
	 */
	public final void setExtendType(final String extendType) {
		this.extendType = extendType;
	}

	/**
	 * @return the methods
	 */
	public final List<MethodMetadata> getMethods() {
		return methods;
	}


	/**
	 * @param methods the methods to set
	 */
	public final void setMethods(final List<MethodMetadata> methods) {
		this.methods = methods;
	}


	/**
	 * @return the imports
	 */
	public final List<String> getImports() {
		return imports;
	}


	/**
	 * @return the space
	 */
	public final String getSpace() {
		return space;
	}


	/**
	 * @param space the space to set
	 */
	public final void setSpace(final String space) {
		this.space = space;
	}
	
	
	/**
	 * @param imports the imports to set
	 */
	public final void setImports(final List<String> imports) {
		this.imports = imports;
	}
	
	
	/**
	 * @return the implementTypes
	 */
	public final List<String> getImplementTypes() {
		return implementTypes;
	}


	/**
	 * @param implementTypes the implementTypes to set
	 */
	public final void setImplementTypes(final List<String> implementTypes) {
		this.implementTypes = implementTypes;
	}


	/**
	 * Build a map from a collection of fields.
	 * @param c The collection of fields
	 * @param adapter The adapter to use.
	 * @return The fields map.
	 */
	private Map<String, Map<String, Object>> toFieldArray(
			final Collection<FieldMetadata> c, 
			final BaseAdapter adapter) {
		final Map<String, Map<String, Object>> result = 
				new LinkedHashMap<String, Map<String, Object>>();
		Map<String, Object> subField = null;
		
		for (final FieldMetadata field : c) {
			//field.customize(adapter);
			
			subField = field.toMap(adapter);
			
			// Add field translate
			if (!field.isInternal() && !field.isHidden()) {
				field.makeString("label");
			}
			
			result.put(field.getName(), subField);
		}
		
		return result;
	}

	/**
	 * @return the motherClass
	 */
	public final String getMotherClass() {
		return motherClass;
	}

	/**
	 * @param motherClass the motherClass to set
	 */
	public final void setMotherClass(String motherClass) {
		this.motherClass = motherClass;
	}

	/**
	 * @return the subClasses
	 */
	public final Map<String, ClassMetadata> getSubClasses() {
		return subClasses;
	}

	/**
	 * @param subClasses the subClasses to set
	 */
	public final void setSubClasses(Map<String, ClassMetadata> subClasses) {
		this.subClasses = subClasses;
	}	
}
