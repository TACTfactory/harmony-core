package ${local_namespace};

import ${local_namespace}.base.${project_name?cap_first}ProviderBase;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * ${project_name?cap_first}Provider to handle DB operations.
 */
public class ${project_name?cap_first}Provider 
				extends ${project_name?cap_first}ProviderBase {
	
	/**
	 * Deletes matching tokens with the given URI.
	 * @param uri URI
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return The number of tokens deleted
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int result = 0;
		switch (uriMatcher.match(uri)) {
			default:
				result = super.delete(uri, selection, selectionArgs);
				break;
		}
		return result;
	}

	/**
	 * Insert ContentValues with the given URI.
	 * @param uri URI
	 * @param values ContentValues to insert
	 * @return The URI to the inserted ContentValue
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri result = null;

		switch (uriMatcher.match(uri)) {
			default:
				result = super.insert(uri, values);
				break;
		}

		return result;
	}

	/**
	 * Query the table given by the uri parameter.
	 * @param uri URI
	 * @param projection Columns to work with
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @param sortOrder ORDER BY clause
	 * @return A cursor pointing to the result of the query
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor result = null;

		switch (uriMatcher.match(uri)) {
			default:
				result = super.query(uri,
					projection, 
					selection, 
					selectionArgs, 
					sortOrder);
				break;
		}

		return result;
	}
	/**
	 * Update the entities matching with uri from the DB.
	 * @param uri URI
	 * @param values ContentValues to update
	 * @param selection SELECT clause for SQL
	 * @param selectionArgs SELECT arguments for SQL
	 * @return how many token update
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int result = 0;

		switch (uriMatcher.match(uri)) {
			default:
				result = super.update(uri, values, selection, selectionArgs);
				break;
		}
		return result;
	}
}
