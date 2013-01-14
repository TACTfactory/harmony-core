<#assign curr = entities[current_entity] />
package ${curr.data_namespace};

import android.content.Context;

/** ${curr.name} adapter database class */
public class ${curr.name}SQLiteAdapter extends ${curr.name}SQLiteAdapterBase {
	
	/**
	 * Constructor
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapter(Context ctx) {
		super(ctx);
	}
}
