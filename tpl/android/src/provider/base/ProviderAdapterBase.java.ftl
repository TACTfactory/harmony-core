<@header?interpret />
package ${local_namespace}.base;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.net.Uri;

import ${data_namespace}.base.SQLiteAdapterBase;

/**
 * ProviderAdapterBase<T extends Serializable>.
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapterBase<T> {
	/** TAG for debug purpose. */
	public static final String TAG = "ProviderAdapterBase<T>";
	/** Context. */
	protected Context ctx;
	/** SQLiteAdapterBase. */
	protected SQLiteAdapterBase<T> adapter;
	/** database. */
	protected SQLiteDatabase db;

	/**
	 * Provider Adapter Base constructor.
	 * @param context The context.
	 */
	public ProviderAdapterBase(final Context context) {
		this.ctx = context;
	}

	/**
	 * Get database.
	 * @return database
	 */
	public SQLiteDatabase getDb() {
		return this.db;
	}

	/**
	 * Get the entity URI.
	 * @return The URI
	 */
	public abstract Uri getUri();
}
