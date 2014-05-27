package com.tactfactory.harmony.meta;

import java.util.HashMap;
import java.util.Map;

import com.tactfactory.harmony.generator.TagConstant;
import com.tactfactory.harmony.plateforme.IAdapter;

/**
 * Metadata linking a type Enum field to the concerned enum.
 */
public class EnumTypeMetadata extends BaseMetadata {
	
	/**
	 * Name of the target enum.
	 */
	private String targetEnum;

	/**
	 * Constructor.
	 */
	public EnumTypeMetadata() {
		this.setName(TagConstant.ENUM);
	}
	
	@Override
	public Map<String, Object> toMap(IAdapter adapter) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(TagConstant.ENUM_REF, this.targetEnum);
		return result;
	}

	/**
	 * @return the targetEnum
	 */
	public final String getTargetEnum() {
		return targetEnum;
	}

	/**
	 * @param targetEnum the targetEnum to set
	 */
	public final void setTargetEnum(String targetEnum) {
		this.targetEnum = targetEnum;
	}

}
