<#assign curr = entities[current_entity] />
package ${project_namespace}.criterias;

import ${data_namespace}.${curr.name?cap_first}SQLiteAdapter;
import ${project_namespace}.criterias.base.*;

public class ${curr.name?cap_first}Criterias extends CriteriasBase{
	public static final String _PARCELABLE = "${curr.name?uncap_first}CriteriaPARCEL";

	
	public ${curr.name?cap_first}Criterias(GroupType type){super(type);}
		
	
	@Override
	public boolean validCriteria(Criteria c) {
		String key = c.getKey();
		String[] possibleKeys = ${curr.name?cap_first}SQLiteAdapter.COLS;
		for (String pKey : possibleKeys){
			if (key.equals(pKey)){
				return true;
			}
		}
		return false;
	}

}
