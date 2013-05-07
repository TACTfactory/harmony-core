package ${local_namespace}.base;

import java.io.Serializable;
import java.util.ArrayList;

import ${project_namespace}.criterias.base.CriteriasBase;
import ${data_namespace}.base.SQLiteAdapterBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public abstract class ProviderAdapterBase<T extends Serializable> {
	public static final String TAG = "ProviderAdapterBase<T>";
	protected Context ctx;
	protected SQLiteAdapterBase<T> adapter;
	protected SQLiteDatabase db;

	public ProviderAdapterBase(Context context) {
		this.ctx = context;
	}

	protected Bundle insert(String arg, Bundle extras) {
		long newId = -1;
		@SuppressWarnings("unchecked")
		T item = (T) extras.getSerializable(this.getItemKey());
		this.db.beginTransaction();
		try {
			newId = this.adapter.insert(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " + e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		Bundle result = new Bundle();
		result.putLong("result", newId);
		return result;
	}

	public Bundle delete(String arg, Bundle extras) {
		int adaptResult = 0;
		@SuppressWarnings("unchecked")
		T item = (T) extras.getSerializable(this.getItemKey());
		this.db.beginTransaction();
		try {
			adaptResult = this.adapter.delete(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " + e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		result.putInt("result", adaptResult);
		return result;
	}

	public Bundle update(String arg, Bundle extras) {
		int adaptResult = 0;
		@SuppressWarnings("unchecked")
		T item = (T) extras.getSerializable(this.getItemKey());
		this.db.beginTransaction();
		try {
			adaptResult = this.adapter.update(item);
			this.db.setTransactionSuccessful();
		} catch(Exception e) {
			Log.e(TAG, "Error while inserting T into database : " + e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		result.putInt("result", adaptResult);
		return result;
	}

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
			Log.e(TAG, "Error while inserting T into database : " + e.getMessage());
		} finally {
			this.db.endTransaction();
		}
		
		Bundle result = new Bundle();
		if (itemList != null) {
			result.putSerializable(this.getItemKey(), itemList);
		}
		return result;
	}

	public SQLiteDatabase getDb() {
		return this.db;
	}

	public abstract String getItemKey();
}
