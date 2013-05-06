<#assign curr = entities[current_entity] />
package ${local_namespace};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ${local_namespace}.base.${curr.name?cap_first}ProviderAdapterBase;

/**
 * ${curr.name?cap_first}ProviderAdapter.
 */
public class ${curr.name?cap_first}ProviderAdapter 
					extends ${curr.name?cap_first}ProviderAdapterBase {
	
	/**
	 * Constructor.
	 * @param context context
	 */
	public ${curr.name?cap_first}ProviderAdapter(Context context) {
		this(context, null);
	}

	/**
	 * Constructor.
	 * @param context context
	 * @param db database
	 */
	public ${curr.name?cap_first}ProviderAdapter(Context context, 
							SQLiteDatabase db) {
		super(context, db);
	}
}

