<#assign curr = entities[current_entity] />
<@header?interpret />
package ${data_namespace};

import ${data_namespace}.base.${curr.name}SQLiteAdapterBase;
import android.content.Context;

/** ${curr.name} adapter database class. */
public class ${curr.name}SQLiteAdapter extends ${curr.name}SQLiteAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name}SQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
