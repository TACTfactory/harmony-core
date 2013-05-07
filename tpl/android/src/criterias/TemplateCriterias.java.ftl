<#assign curr = entities[current_entity] />
package ${project_namespace}.criterias;

import ${data_namespace}.${curr.name?cap_first}SQLiteAdapter;
import ${project_namespace}.criterias.base.*;

/**
 * ${curr.name?cap_first}Criterias.
 */
public class ${curr.name?cap_first}Criterias extends CriteriasBase {
	/** String to parcel ${curr.name?uncap_first}Criteria. */
	public static final String _PARCELABLE = 
			"${curr.name?uncap_first}CriteriaPARCEL";

	/**
	 * Constructor.
	 * @param type The Criteria's GroupType
	 */
	public ${curr.name?cap_first}Criterias(final GroupType type) { 
		super(type); 
	}
	
	/**
	 * Checks if the given Criteria is valid.
	 * @param crit The Criteria to validate
	 */
	@Override
	public boolean validCriteria(final Criteria crit) {
		boolean result;
	
		final String key = crit.getKey();
		final String[] possibleKeys = ${curr.name?cap_first}SQLiteAdapter.COLS;
		for (final String pKey : possibleKeys) {
			if (key.equals(pKey)) {
				result = true;
			}
		}
		result = false;
		
		return result;
	}

}
