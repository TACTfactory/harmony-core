<@header?interpret />
package ${local_namespace}.base;

import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriasBase;
import ${data_namespace}.base.SQLiteAdapterBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

}
