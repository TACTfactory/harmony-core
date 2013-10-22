package com.tactfactory.harmony.meta;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.annotation.InheritanceType.InheritanceMode;
import com.tactfactory.harmony.plateforme.BaseAdapter;
import com.tactfactory.harmony.template.TagConstant;

public class InheritanceMetadata extends BaseMetadata {
	private InheritanceMode type;
	private String discriminorColumn;
	private String discriminorIdentifier;
	private String superclass;
	/** Map to evolve easily to Map<String, ClassMetadata>. */
	private Map<String, String> subclasses =
				new HashMap<String, String>();
	
	
	@Override
	public Map<String, Object> toMap(BaseAdapter adapter) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(TagConstant.INHERITANCE_TYPE,
				this.type);
		
		result.put(TagConstant.DISCRIMINATOR_COLUMN,
				this.discriminorColumn);
		
		result.put(TagConstant.DISCRIMINATOR_IDENTIFIER,
				this.discriminorIdentifier);
		
		result.put(TagConstant.SUPERCLASS, this.superclass);
		result.put(TagConstant.SUBCLASSES, this.subclasses.values());
		return result;
	}

	/**
	 * @return the type
	 */
	public final InheritanceMode getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public final void setType(InheritanceMode type) {
		this.type = type;
	}


	/**
	 * @return the discriminorColumn
	 */
	public final String getDiscriminorColumn() {
		return discriminorColumn;
	}


	/**
	 * @param discriminorColumn the discriminorColumn to set
	 */
	public final void setDiscriminorColumn(String discriminorColumn) {
		this.discriminorColumn = discriminorColumn;
	}


	/**
	 * @return the discriminorIdentifier
	 */
	public final String getDiscriminorIdentifier() {
		return discriminorIdentifier;
	}


	/**
	 * @param discriminorIdentifier the discriminorIdentifier to set
	 */
	public final void setDiscriminorIdentifier(String discriminorIdentifier) {
		this.discriminorIdentifier = discriminorIdentifier;
	}


	/**
	 * @return the superclass
	 */
	public final String getSuperclass() {
		return superclass;
	}


	/**
	 * @param superclass the superclass to set
	 */
	public final void setSuperclass(String superclass) {
		this.superclass = superclass;
	}


	/**
	 * @return the subclasses
	 */
	public final Map<String, String> getSubclasses() {
		return subclasses;
	}


	/**
	 * @param subclasses the subclasses to set
	 */
	public final void setSubclasses(Map<String, String> subclasses) {
		this.subclasses = subclasses;
	}
}
