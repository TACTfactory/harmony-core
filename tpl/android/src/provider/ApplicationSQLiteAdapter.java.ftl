<@header?interpret />
package ${data_namespace};

import android.content.Context;

import ${data_namespace}.base.SQLiteAdapterBase;

/**
 * This is the SQLiteAdapter.
 *
 * Feel free to add any generic custom method in here.
 *
 * This is the base class for all basic operations for your sqlite adapters.
 *
 * @param <T> Entity type of this adapter.
 */
public abstract class SQLiteAdapter<T> extends SQLiteAdapterBase<T> {

	/**
	 * Constructor.
	 * @param ctx context
	 */
	protected SQLiteAdapter(final Context ctx) {
		super(ctx);
	}
}
