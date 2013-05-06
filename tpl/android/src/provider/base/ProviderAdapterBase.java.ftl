package ${local_namespace}.base;

import java.io.Serializable;
import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriasBase;
import ${data_namespace}.base.SQLiteAdapterBase;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
/**
 * ProviderAdapterBase<T extends Serializable>.
 * @param <T> must extends Serializable
 */
public abstract class ProviderAdapterBase<T extends Serializable> {
	public static final String TAG = "ProviderAdapterBase<T>";
	protected SQLiteAdapterBase<T> adapter;
	protected SQLiteDatabase db;

	/**
	 * Insert Bundle into the database.
	 * @param arg Argument
	 * @param extras The Bundle to insert
	 * @return The updated Bundle containing the new id
	 */
	protected Bundle insert(String arg, Bundle extras) {
		long newId = -1;
		@SuppressWarnings("unchecked")
		T item = (T) extras.getSerializable(this.getItemKey());
		this.db.beginTransaction();
		try {
			newId = this.adapter.insert(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " 
						+ e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		Bundle result = new Bundle();
		result.putLong("result", newId);
		return result;
	}

	/**
	 * Delete Bundle into the database.
	 * @param arg Argument
	 * @param extras The Bundle to delete
	 * @return The updated Bundle containing how many updated fields
	 */
	public Bundle delete(String arg, Bundle extras) {
		int adaptResult = 0;
		@SuppressWarnings("unchecked")
		T item = (T) extras.getSerializable(this.getItemKey());
		this.db.beginTransaction();
		try {
			adaptResult = this.adapter.delete(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " 
						+ e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		result.putInt("result", adaptResult);
		return result;
	}

	/**
	 * Update Bundle into the database.
	 * @param arg Argument
	 * @param extras The Bundle to update.
	 * @return The updated Bundle containing how many updated fields
	 */
	public Bundle update(String arg, Bundle extras) {
		int adaptResult = 0;
		@SuppressWarnings("unchecked")
		T item = (T) extras.getSerializable(this.getItemKey());
		this.db.beginTransaction();
		try {
			adaptResult = this.adapter.update(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " 
						+ e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		result.putInt("result", adaptResult);
		return result;
	}

	/**
	 * QueryAll.
	 * @param arg Argument
	 * @param extras Bundle
	 * @return The updated Bundle containing a list a of items
	 */
	public Bundle queryAll(String arg, Bundle extras) {
		CriteriasBase crits = null;
		if (extras != null) {
			crits = (CriteriasBase) extras.getSerializable("crits");
		}
		ArrayList<T> itemList = null;
		this.db.beginTransaction();
		try {
			itemList = this.adapter.getAll(crits);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " 
						+ e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		if (itemList != null) {
			result.putSerializable(this.getItemKey(), itemList);
		}
		return result;
	}

	/**
	 * Get database.
	 * @return database
	 */
	public SQLiteDatabase getDb() {
		return this.db;
	}

	/**
	 * Get the itemKey.
	 * @return A String representing the key
	 */
	public abstract String getItemKey();
}
