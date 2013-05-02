<#assign curr = entities[current_entity] />
package ${local_namespace};

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ${local_namespace}.base.${curr.name?cap_first}ProviderAdapterBase;

public class ${curr.name?cap_first}ProviderAdapter extends ${curr.name?cap_first}ProviderAdapterBase {
	
	public ${curr.name?cap_first}ProviderAdapter(Context context) {
		this(context, null);
	}

	public ${curr.name?cap_first}ProviderAdapter(Context context, SQLiteDatabase db) {
		super(context, db);
	}
}

