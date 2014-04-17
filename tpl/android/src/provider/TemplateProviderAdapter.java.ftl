<#assign curr = entities[current_entity] />
<@header?interpret />
package ${project_namespace}.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import ${project_namespace}.provider.base.${curr.name?cap_first}ProviderAdapterBase;

/**
 * ${curr.name?cap_first}ProviderAdapter.
 *
 * A provider adapter is used to separate your provider requests for
 * each entity of your application.
 * You will find here basic methods for database manipulation.
 * Feel free to override any method here.
 */
public class ${curr.name?cap_first}ProviderAdapter
					extends ${curr.name?cap_first}ProviderAdapterBase {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	public ${curr.name?cap_first}ProviderAdapter(final Context ctx) {
		this(ctx, null);
	}

	/**
	 * Constructor.
	 * @param ctx context
	 * @param db database
	 */
	public ${curr.name?cap_first}ProviderAdapter(final Context ctx,
												 final SQLiteDatabase db) {
		super(ctx, db);
	}
}

