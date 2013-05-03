<#assign curr = entities[current_entity] />
package ${local_namespace}.base;

import ${local_namespace}.${project_name?cap_first}Provider;

import ${entity_namespace}.${curr.name};
import ${data_namespace}.${curr.name}SQLiteAdapter;

import android.content.Context;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

public abstract class ${curr.name?cap_first}ProviderAdapterBase extends ProviderAdapterBase<${curr.name?cap_first}> {
	protected static String TAG = "${curr.name?cap_first}ProviderAdapter";

	public	  static Uri ${curr.name?upper_case}_URI;

	public static String ITEM_KEY = "${curr.name?cap_first}";

	protected static final String ${curr.name?uncap_first}Type = "${curr.name?lower_case}";

	public static final String METHOD_INSERT_${curr.name?upper_case} = "insert${curr.name?cap_first}";
	public static final String METHOD_UPDATE_${curr.name?upper_case} = "update${curr.name?cap_first}";
	public static final String METHOD_DELETE_${curr.name?upper_case} = "delete${curr.name?cap_first}";
	public static final String METHOD_QUERY_${curr.name?upper_case} = "query${curr.name?cap_first}";

	protected static final int ${curr.name?upper_case}_ALL 		= ${provider_id};
	protected static final int ${curr.name?upper_case}_ONE 		= ${provider_id + 1};

	static {
		${curr.name?upper_case}_URI = ${project_name?cap_first}Provider.generateUri(${curr.name?uncap_first}Type);
		${project_name?cap_first}Provider.getUriMatcher().addURI(${project_name?cap_first}Provider.authority, ${curr.name?uncap_first}Type, 		${curr.name?upper_case}_ALL);
		${project_name?cap_first}Provider.getUriMatcher().addURI(${project_name?cap_first}Provider.authority, ${curr.name?uncap_first}Type + "/#", 	${curr.name?upper_case}_ONE);
	}

	public ${curr.name?cap_first}ProviderAdapterBase(Context context, SQLiteDatabase db) {
		this.adapter = new ${curr.name?cap_first}SQLiteAdapter(context);
		if (db != null) {
			this.db = this.adapter.open(db);
		} else {
			this.db = this.adapter.open();
		}
	}

	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				int id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ${curr.name?cap_first}SQLiteAdapter.COL_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				result = this.adapter.delete(
						selection, 
						selectionArgs);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.delete(
							selection, 
							selectionArgs);
				break;
		}
		return result;
	}

	public Uri insert(Uri uri, ContentValues values) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher().match(uri);
		Uri result = null;
		int id = 0;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ALL:
				id = (int) this.adapter.insert(null, values);
				if (id > 0) {
					result = ContentUris.withAppendedId(${curr.name?upper_case}_URI, id);
				}
				break;
		}
		return result;
	}

	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher().match(uri);
		Cursor result = null;
		int id = 0;

		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				id = Integer.parseInt(uri.getPathSegments().get(1));
				selection = ${curr.name?cap_first}SQLiteAdapter.COL_ID + " = ?";
				selectionArgs = new String[1];
				selectionArgs[0] = String.valueOf(id);
				projection = this.adapter.getCols();
				result = this.adapter.query(
							projection, 
							selection,
							selectionArgs, 
							sortOrder,
							null,
							null);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.query(
							projection, 
							selection,
							selectionArgs, 
							sortOrder,
							null,
							null);
				break;
		}

		return result;
	}

	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int matchedUri = ${project_name?cap_first}ProviderBase.getUriMatcher().match(uri);
		int result = -1;
		switch (matchedUri) {
			case ${curr.name?upper_case}_ONE:
				String id = uri.getPathSegments().get(1);
				result = this.adapter.update(
						values, 
						${curr.name?cap_first}SQLiteAdapter.COL_ID + " = " + id, 
						selectionArgs);
				break;
			case ${curr.name?upper_case}_ALL:
				result = this.adapter.update(
							values, 
							selection, 
							selectionArgs);
				break;
		}
		return result;
	}

	@Override
	public String getItemKey() {
		return ITEM_KEY;
	}

	public Bundle query(String arg, Bundle extras) {
		Bundle result = new Bundle();

		${curr.name?cap_first} ${curr.name?uncap_first} = ((${curr.name?cap_first}SQLiteAdapter) this.adapter).getByID(extras.getInt("id"));
		result.putSerializable(ITEM_KEY, ${curr.name?uncap_first});

		return result;
	}
}

